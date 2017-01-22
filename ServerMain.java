package assignment7;

import java.io.*;
import java.net.*;
import java.util.*;

import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.Stage;

public class ServerMain extends Application implements Observer {
	private ArrayList<Observable> clientsToObserve;
	private ArrayList<String> observingClientNames;
	private ArrayList<PrintWriter> incoming;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		// Each client is a unique thread.
		new Thread(() -> {
			try {
				incoming = new ArrayList<PrintWriter>();
				clientsToObserve = new ArrayList<Observable>();
				observingClientNames = new ArrayList<String>();

				// Instantiate the server connection (only once.)
				ServerSocket serverSocket = new ServerSocket(4242);
				while (true) {
					Socket clientSocket = serverSocket.accept();
					PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
					ClientHandler clientHandler = new ClientHandler(clientSocket, writer);
					Thread thread = new Thread(clientHandler);
					clientsToObserve.add(clientHandler);
					thread.start();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
	}

	private void notifyClients(String message) {
		for (PrintWriter writer : incoming) {
			// Output the message for every cilent.
			writer.println(message);
			writer.flush();
		}
	}


	class ClientHandler extends Observable implements Runnable {
		private String clientName;
		private Socket socket;
		private PrintWriter writer;
		private BufferedReader reader;

		public ClientHandler(Socket socket, PrintWriter writer) {
			try {
				this.clientName = "";
				this.socket = socket;
				this.writer = writer;
				this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() { 
			String message;
			try {
				while ((message = reader.readLine()) != null) {
					// System.out.println(message);
					if (message.startsWith("unregistered")) {
						synchronized(incoming) {
						// transferring of messages should not happen simultaneously.
							String userName = message.replace("unregistered", "");
							this.clientName = userName;
							incoming.add(this.writer);
							observingClientNames.add(this.clientName);
							
							// Send message to indicate to the server to update the online list with the new client.
							String online = "new";
							for (String client : observingClientNames) {
								online += client + " ";
							}
							
							// Send message.
							for (PrintWriter writer : incoming) {
								writer.println(online);
								writer.flush();
							}
							
							// Notify all clients if someone joined the chat.
							for (PrintWriter writer : incoming) {
								writer.println(this.clientName + " has joined the chatroom.");
								writer.flush();
							}
						}
					}
					else if (message.startsWith("group")) {
						synchronized(incoming) {
							message = message.replace("group", "");
							for (int client = 0; client < incoming.size(); client += 1) {
								incoming.get(client).println(this.clientName + ": " + message);
								incoming.get(client).flush();
							}
						}
					}
					else if (message.contains("private")) {
						synchronized(incoming) {
							System.out.println(message);
							
							// ":" is delimiter separating the clients to message and the
							// message itself.
							String[] clientsAndMsg = message.split(":"); 
							List<String> clients = Arrays.asList(clientsAndMsg[0].split(" "));
							String chatMessage = clientsAndMsg[1];							
							
							for (int client = 0; client < incoming.size(); client += 1) {
								if (clients.contains(observingClientNames.get(client))) {
									incoming.get(client).println(this.clientName + ": " + chatMessage);
									incoming.get(client).flush();	 
								}
							}
							
						}
					}
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	@Override
	public void update(Observable arg0, Object arg1) {
		clientsToObserve.remove(arg0);
	}
}

