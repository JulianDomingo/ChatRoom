package assignment7;

import java.io.*;
import java.net.*;

import java.util.ArrayList;

import javafx.event.*;
import javafx.scene.*;
import javafx.geometry.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.application.Platform;

public class ClientMain extends Application {
	// private TextArea incoming;				// message to receive
	private TextFlow incoming;
	private TextField outgoing;				// message to send
	private BufferedReader reader;
	private PrintWriter writer;
	private String message;
	private String clientName;
	private TextArea currentlyMessaging;
	private ArrayList<String> privatelyMessagingClients = new ArrayList<String>();
	
	private Stage emojiStage = new Stage();
	private boolean toggleEmoji = false;
	
	public static ArrayList<String> clients = new ArrayList<String>();
	public static ArrayList<String> oneToOnes = new ArrayList<String>();

	// TODO: Change to something better.
	public MenuButton clientsOnline = new MenuButton();
	public TextArea peopleOnline;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override 
	public void start(Stage primaryStage) throws Exception {
		ArrayList<Node> FXObjects = new ArrayList<Node>();

		// Initialize a new client window.
		// TODO: Randomly display a fixed set of titles.
		
		ScrollPane scrollPane = new ScrollPane();
		primaryStage.setTitle("Messenger 2.0");
		Pane pane = new Pane(); 
		pane.setStyle("-fx-background-color: #3b5998, -fx-background;" 
				+ "-fx-background-insets: 0 0 0 0, 68 0 0 0;");
		
		// Grid pane visual adjustments
//		pane.setPadding(new Insets(20, 20, 20, 20)); 
		Label userText = new Label("Username: ");
		Label IPText = new Label("IP Address: ");
		FXObjects.add(userText);
		FXObjects.add(IPText);
		
		// TextFields
		TextField userName = new TextField();
		TextField IP = new TextField();
		TextField chatBox = new TextField();
		
		FXObjects.add(userName);
		FXObjects.add(IP);
		FXObjects.add(chatBox);
		
		// TextAreas
		peopleOnline = new TextArea();
		currentlyMessaging = new TextArea();
		incoming = new TextFlow();
		scrollPane.setContent(incoming);
		FXObjects.add(currentlyMessaging);
		FXObjects.add(peopleOnline);
		FXObjects.add(scrollPane);
		
		// Buttons 
		Button submitButton = new Button("Submit");
		Button sendButton = new Button("Send");
		Button emojiButton = new Button("Emojis");
		Button logoutButton = new Button("Logout");
		FXObjects.add(logoutButton);
		FXObjects.add(submitButton);
		FXObjects.add(sendButton);
		FXObjects.add(emojiButton);
		FXObjects.add(clientsOnline);
		
		// Labels
		Label currentUser = new Label("");
		Label online = new Label("Online");
		Label messaging = new Label("Messaging");
		FXObjects.add(messaging);
		FXObjects.add(currentUser);
		FXObjects.add(online);
		
		// Alignments & Adjustments
		messaging.setLayoutX(595);
		messaging.setLayoutY(97);
		messaging.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
		messaging.setTextFill(Color.DARKBLUE);
		
		currentlyMessaging.setPrefWidth(147);
		currentlyMessaging.setPrefHeight(379);
		currentlyMessaging.setLayoutX(550);
		currentlyMessaging.setLayoutY(112);
		currentlyMessaging.setEditable(false);
		
		clientsOnline.setLayoutX(550);
		clientsOnline.setLayoutY(70);
		clientsOnline.setPrefWidth(147);
		clientsOnline.setPrefHeight(20);
		clientsOnline.setText("Send private message");
		clientsOnline.setFont(Font.font("Verdana", 10.5));
		clientsOnline.setTextFill(Color.DARKBLUE);
		
		submitButton.setLayoutX(635);
		submitButton.setLayoutY(20); 
		submitButton.setFont(Font.font("Verdana"));
		submitButton.setTextFill(Color.DARKBLUE);
		  
		logoutButton.setLayoutX(30);
		logoutButton.setLayoutY(504); 
		logoutButton.setFont(Font.font("Verdana"));
		logoutButton.setTextFill(Color.DARKBLUE);
		
		sendButton.setLayoutX(550);
		sendButton.setLayoutY(504);
		sendButton.setFont(Font.font("Verdana"));
		sendButton.setTextFill(Color.DARKBLUE);
		
		emojiButton.setLayoutX(605);
		emojiButton.setLayoutY(504);
		emojiButton.setFont(Font.font("Verdana"));
		emojiButton.setTextFill(Color.DARKBLUE);
		
		userName.setLayoutX(460);
		userName.setLayoutY(7);
		userText.setLayoutX(396);
		userText.setLayoutY(11);
		userText.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
		userText.setTextFill(Color.WHITE);
		userName.setPromptText("Enter a username...");
		
		IP.setLayoutX(460);
		IP.setLayoutY(37);
		IPText.setLayoutX(393);
		IPText.setLayoutY(41);
		IPText.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
		IPText.setTextFill(Color.WHITE);
		// IP.setPromptText("Enter an IP address...");
		IP.setText("10.146.100.211");
		
		currentUser.setLayoutX(20);
		currentUser.setLayoutY(3);
		currentUser.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
		currentUser.setTextFill(Color.WHITE);
		
		incoming.setPrefWidth(425);
		incoming.setPrefHeight(420); // (lol)
		incoming.setLayoutX(120);
		incoming.setLayoutY(80);
		incoming.setPadding(new Insets(10));
		incoming.setBorder(new Border(new BorderStroke(Color.DARKSLATEGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		scrollPane.setLayoutX(119);
		scrollPane.setLayoutY(70);
		
		online.setLayoutX(40);
		online.setLayoutY(50);
		online.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));
		online.setTextFill(Color.WHITE);
		
		peopleOnline.setPrefWidth(100);
		peopleOnline.setPrefHeight(422);
		peopleOnline.setLayoutX(10);
		peopleOnline.setLayoutY(70);
		peopleOnline.setEditable(false);
		
		chatBox.setAlignment(Pos.BOTTOM_LEFT); 
		chatBox.setPromptText("Write a reply...");
		chatBox.setPrefWidth(425);
		chatBox.setLayoutX(120);
		chatBox.setLayoutY(504);
		
		// Add all FX objects.
		addFXObjects(FXObjects, pane);
		
		Scene scene = new Scene(pane, 700, 550);
		primaryStage.setScene(scene);
		primaryStage.show();
			
		
		// Event Handlers
		// Registering a user:
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					Socket socket = new Socket(IP.getText(), 4242);
					InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
					reader = new BufferedReader(streamReader);
					writer = new PrintWriter(socket.getOutputStream());
					
					writer.println("unregistered" + userName.getText()); 
					writer.flush(); 
					
					currentUser.setText(userName.getText());		
					Thread readerThread = new Thread(new IncomingReader(userName.getText()));
					readerThread.start();
					// TODO: Clear user entry and disable the text field objects.
				}
				catch (IOException e) {
					e.printStackTrace();	
				}
			} 
		});
		
		logoutButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				clients.remove(clients.indexOf(clientName));
				// System.exit(0); 
			}
		});
		
		
		// Sending a message:
		sendButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (reader != null && writer != null) {
					String finalMessage = "";
					boolean privateMsg = false;
					
					// TODO: Change to something better.
					for (int client = 0; client < clientsOnline.getItems().size(); client += 1) {
						CustomMenuItem person = (CustomMenuItem) clientsOnline.getItems().get(client);
						CheckBox cb = (CheckBox) person.getContent();
						if (cb.isSelected()) {
							if (!privateMsg) {
								// Initialize private message Text area for every private message since it might change.
								privateMsg = true;
								privatelyMessagingClients.clear();
							}
							// Add client to private message list. 
							finalMessage += clients.get(client) + " ";
							privatelyMessagingClients.add(clients.get(client));
						}
					}
					if (privateMsg) {
						finalMessage += userName.getText() + " private:" + chatBox.getText();
					}
					else { // Non-private message.
						finalMessage = "group" + chatBox.getText();
					}
					// Display messaging list.
					if (privateMsg) {
						currentlyMessaging.clear();
						for (String client : privatelyMessagingClients) { 
							currentlyMessaging.appendText(client + "\n");
						}
					}
					else { // Simply add all available clients.
						currentlyMessaging.clear();
						for (String client : clients) {
							currentlyMessaging.appendText(client + "\n");
						}
					}
					chatBox.setText("");
					writer.println(finalMessage);
					writer.flush();
				}	
			}
		});
		
        chatBox.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                sendButton.fire();
            }
        });
        
		
		emojiButton.setOnAction((ActionEvent event)-> {	
			if (!toggleEmoji) {
				GridPane root = new GridPane();
				root.setAlignment(Pos.TOP_LEFT);
				
				Scene worldScene = new Scene(root, 203, 206);
				
				Button smiley = new Button();
				Image okImage = new Image(getClass().getResourceAsStream("Slightly_Smiling_Face_Emoji.png"));
				ImageView temp = new ImageView(okImage);
				temp.setFitHeight(16.0);
				temp.setFitHeight(16.0);
				temp.setPreserveRatio(true);
				smiley.setGraphic(temp);
				
				smiley.setOnAction(e -> { 
					chatBox.appendText("0x0FF00");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF00");
				});
				
				Button smiley1 = new Button();
				Image okImage1 = new Image(getClass().getResourceAsStream("Smiling_Face_Emoji_with_Blushed_Cheeks.png"));
				ImageView temp1 = new ImageView(okImage1);
				temp1.setFitHeight(16.0);
				temp1.setFitHeight(16.0);
				temp1.setPreserveRatio(true);
				smiley1.setGraphic(temp1);
				
				smiley1.setOnAction(e -> { 
					chatBox.appendText("0x0FF01");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF01");
				});
				
				Button smiley2 = new Button();
				Image okImage2 = new Image(getClass().getResourceAsStream("Smiling_Face_Emoji.png"));
				ImageView temp2 = new ImageView(okImage2);
				temp2.setFitHeight(16.0);
				temp2.setFitHeight(16.0);
				temp2.setPreserveRatio(true);
				smiley2.setGraphic(temp2);
				
				smiley2.setOnAction(e -> { 
					chatBox.appendText("0x0FF02");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF02");
				});
				
				Button smiley3 = new Button();
				Image okImage3 = new Image(getClass().getResourceAsStream("Smiling_Emoji_with_Smiling_Eyes.png"));
				ImageView temp3 = new ImageView(okImage3);
				temp3.setFitHeight(16.0);
				temp3.setFitHeight(16.0);
				temp3.setPreserveRatio(true);
				smiley3.setGraphic(temp3);
				
				smiley3.setOnAction(e -> { 
					chatBox.appendText("0x0FF03");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF03");
				});
				
				Button smiley4 = new Button();
				Image okImage4 = new Image(getClass().getResourceAsStream("Smiling_Emoji_with_Eyes_Opened.png"));
				ImageView temp4 = new ImageView(okImage4);
				temp4.setFitHeight(16.0);
				temp4.setFitHeight(16.0);
				temp4.setPreserveRatio(true);
				smiley4.setGraphic(temp4);
				
				smiley4.setOnAction(e -> { 
					chatBox.appendText("0x0FF04");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF04");
				});
				
				Button smiley5 = new Button();
				Image okImage5 = new Image(getClass().getResourceAsStream("Smiling_Face_with_Tightly_Closed_eyes.png"));
				ImageView temp5 = new ImageView(okImage5);
				temp5.setFitHeight(16.0);
				temp5.setFitHeight(16.0);
				temp5.setPreserveRatio(true);
				smiley5.setGraphic(temp5);
				
				smiley5.setOnAction(e -> { 
					chatBox.appendText("0x0FF05");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF05");
				});
				
				Button smiley6 = new Button();
				Image okImage6 = new Image(getClass().getResourceAsStream("Smiling_with_Sweat_Emoji.png"));
				ImageView temp6 = new ImageView(okImage6);
				temp6.setFitHeight(16.0);
				temp6.setFitHeight(16.0);
				temp6.setPreserveRatio(true);
				smiley6.setGraphic(temp6);
				
				smiley6.setOnAction(e -> { 
					chatBox.appendText("0x0FF06");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF06");
				});
				
				Button smiley7 = new Button();
				Image okImage7 = new Image(getClass().getResourceAsStream("Tears_of_Joy_Emoji.png"));
				ImageView temp7 = new ImageView(okImage7);
				temp7.setFitHeight(16.0);
				temp7.setFitHeight(16.0);
				temp7.setPreserveRatio(true);
				smiley7.setGraphic(temp7);
				
				smiley7.setOnAction(e -> { 
					chatBox.appendText("0x0FF07");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF07");
				});
				
				Button smiley8 = new Button();
				Image okImage8 = new Image(getClass().getResourceAsStream("Smirk_Face_Emoji.png"));
				ImageView temp8 = new ImageView(okImage8);
				temp8.setFitHeight(16.0);
				temp8.setFitHeight(16.0);
				temp8.setPreserveRatio(true);
				smiley8.setGraphic(temp8);
				
				smiley8.setOnAction(e -> { 
					chatBox.appendText("0x0FF08");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF08");
				});
				
				Button smiley9 = new Button();
				Image okImage9 = new Image(getClass().getResourceAsStream("Sad_Face_Emoji.png"));
				ImageView temp9 = new ImageView(okImage9);
				temp9.setFitHeight(16.0);
				temp9.setFitHeight(16.0);
				temp9.setPreserveRatio(true);
				smiley9.setGraphic(temp9);
				
				smiley9.setOnAction(e -> { 
					chatBox.appendText("0x0FF09");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF09");
				});
				
				Button smileyA = new Button();
				Image okImageA = new Image(getClass().getResourceAsStream("Face_With_Rolling_Eyes_Emoji.png"));
				ImageView tempA = new ImageView(okImageA);
				tempA.setFitHeight(16.0);
				tempA.setFitHeight(16.0);
				tempA.setPreserveRatio(true);
				smileyA.setGraphic(tempA);
				
				smileyA.setOnAction(e -> { 
					chatBox.appendText("0x0FF0A");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF0A");
				});
				
				Button smileyB = new Button();
				Image okImageB = new Image(getClass().getResourceAsStream("Thinking_Face_Emoji.png"));
				ImageView tempB = new ImageView(okImageB);
				tempB.setFitHeight(16.0);
				tempB.setFitHeight(16.0);
				tempB.setPreserveRatio(true);
				smileyB.setGraphic(tempB);
				
				smileyB.setOnAction(e -> { 
					chatBox.appendText("0x0FF0B");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF0B");
				});
				
				Button smileyC = new Button();
				Image okImageC = new Image(getClass().getResourceAsStream("Surprised_Face_Emoji.png"));
				ImageView tempC = new ImageView(okImageC);
				tempC.setFitHeight(16.0);
				tempC.setFitHeight(16.0);
				tempC.setPreserveRatio(true);
				smileyC.setGraphic(tempC);
				
				smileyC.setOnAction(e -> { 
					chatBox.appendText("0x0FF0C");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF0C");
				});

				Button smileyD = new Button();
				Image okImageD = new Image(getClass().getResourceAsStream("OMG_Face_Emoji.png"));
				ImageView tempD = new ImageView(okImageD);
				tempD.setFitHeight(16.0);
				tempD.setFitHeight(16.0);
				tempD.setPreserveRatio(true);
				smileyD.setGraphic(tempD);
				
				smileyD.setOnAction(e -> { 
					chatBox.appendText("0x0FF0D");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF0D");
				});
				
				Button smileyE = new Button();
				Image okImageE = new Image(getClass().getResourceAsStream("Upside-Down_Face_Emoji.png"));
				ImageView tempE = new ImageView(okImageE);
				tempE.setFitHeight(16.0);
				tempE.setFitHeight(16.0);
				tempE.setPreserveRatio(true);
				smileyE.setGraphic(tempE);
				
				smileyE.setOnAction(e -> { 
					chatBox.appendText("0x0FF0E");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF0E");
				});
				
				Button smileyF = new Button();
				Image okImageF = new Image(getClass().getResourceAsStream("Heart_Eyes_Emoji.png"));
				ImageView tempF = new ImageView(okImageF);
				tempF.setFitHeight(16.0);
				tempF.setFitHeight(16.0);
				tempF.setPreserveRatio(true);
				smileyF.setGraphic(tempF);
				
				smileyF.setOnAction(e -> { 
					chatBox.appendText("0x0FF0F");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF0F");
				});
				
				Button smileyG = new Button();
				Image okImageG = new Image(getClass().getResourceAsStream("Sunglasses_Emoji.png"));
				ImageView tempG = new ImageView(okImageG);
				tempG.setFitHeight(16.0);
				tempG.setFitHeight(16.0);
				tempG.setPreserveRatio(true);
				smileyG.setGraphic(tempG);
				
				smileyG.setOnAction(e -> { 
					chatBox.appendText("0x0FF0G");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF0G");
				});
				
				Button smileyH = new Button();
				Image okImageH = new Image(getClass().getResourceAsStream("Tongue_Out_Emoji.png"));
				ImageView tempH = new ImageView(okImageH);
				tempH.setFitHeight(16.0);
				tempH.setFitHeight(16.0);
				tempH.setPreserveRatio(true);
				smileyH.setGraphic(tempH);
				
				smileyH.setOnAction(e -> { 
					chatBox.appendText("0x0FF0H");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF0H");
				});
				
				Button smileyI = new Button();
				Image okImageI = new Image(getClass().getResourceAsStream("Kissing_Face_with_Smiling_Eyes_Emoji.png"));
				ImageView tempI = new ImageView(okImageI);
				tempI.setFitHeight(16.0);
				tempI.setFitHeight(16.0);
				tempI.setPreserveRatio(true);
				smileyI.setGraphic(tempI);
				
				smileyI.setOnAction(e -> { 
					chatBox.appendText("0x0FF0I");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF0I");
				});
				
				Button smileyJ = new Button();
				Image okImageJ = new Image(getClass().getResourceAsStream("Grinning_Emoji_with_Smiling_Eyes.png"));
				ImageView tempJ = new ImageView(okImageJ);
				tempJ.setFitHeight(16.0);
				tempJ.setFitHeight(16.0);
				tempJ.setPreserveRatio(true);
				smileyJ.setGraphic(tempJ);
				
				smileyJ.setOnAction(e -> { 
					chatBox.appendText("0x0FF0J");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF0J");
				});
				
				Button smileyK = new Button();
				Image okImageK = new Image(getClass().getResourceAsStream("Hungry_Face_Emoji.png"));
				ImageView tempK = new ImageView(okImageK);
				tempK.setFitHeight(16.0);
				tempK.setFitHeight(16.0);
				tempK.setPreserveRatio(true);
				smileyK.setGraphic(tempK);
				
				smileyK.setOnAction(e -> { 
					chatBox.appendText("0x0FF0K");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF0K");
				});
				
				Button smileyL = new Button();
				Image okImageL = new Image(getClass().getResourceAsStream("Wink_Emoji.png"));
				ImageView tempL = new ImageView(okImageL);
				tempL.setFitHeight(16.0);
				tempL.setFitHeight(16.0);
				tempL.setPreserveRatio(true);
				smileyL.setGraphic(tempL);
				
				smileyL.setOnAction(e -> { 
					chatBox.appendText("0x0FF0L");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF0L");
				});
				
				Button smileyM = new Button();
				Image okImageM = new Image(getClass().getResourceAsStream("Blow_Kiss_Emoji.png"));
				ImageView tempM = new ImageView(okImageM);
				tempM.setFitHeight(16.0);
				tempM.setFitHeight(16.0);
				tempM.setPreserveRatio(true);
				smileyM.setGraphic(tempM);
				
				smileyM.setOnAction(e -> { 
					chatBox.appendText("0x0FF0M");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF0M");
				});
				
				Button smileyN = new Button();
				Image okImageN = new Image(getClass().getResourceAsStream("Flushed_Face_Emoji.png"));
				ImageView tempN = new ImageView(okImageN);
				tempN.setFitHeight(16.0);
				tempN.setFitHeight(16.0);
				tempN.setPreserveRatio(true);
				smileyN.setGraphic(tempN);
				
				smileyN.setOnAction(e -> { 
					chatBox.appendText("0x0FF0N");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF0N");
				});
				
				Button smileyO = new Button();
				Image okImageO = new Image(getClass().getResourceAsStream("Smiling_Face_with_Halo.png"));
				ImageView tempO = new ImageView(okImageO);
				tempO.setFitHeight(16.0);
				tempO.setFitHeight(16.0);
				tempO.setPreserveRatio(true);
				smileyO.setGraphic(tempO);
				
				smileyO.setOnAction(e -> { 
					chatBox.appendText("0x0FF0O");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF0O");
				});
				
				Button smileyP = new Button();
				Image okImageP = new Image(getClass().getResourceAsStream("Sleeping_Emoji.png"));
				ImageView tempP = new ImageView(okImageP);
				tempP.setFitHeight(16.0);
				tempP.setFitHeight(16.0);
				tempP.setPreserveRatio(true);
				smileyP.setGraphic(tempP);
				
				smileyP.setOnAction(e -> { 
					chatBox.appendText("0x0FF0P");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF0P");
				});
				
				Button smileyQ = new Button();
				Image okImageQ = new Image(getClass().getResourceAsStream("Very_sad_emoji_icon_png.png"));
				ImageView tempQ = new ImageView(okImageQ);
				tempQ.setFitHeight(16.0);
				tempQ.setFitHeight(16.0);
				tempQ.setPreserveRatio(true);
				smileyQ.setGraphic(tempQ);
				
				smileyQ.setOnAction(e -> { 
					chatBox.appendText("0x0FF0Q");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF0Q");
				});
				
				Button smileyR = new Button();
				Image okImageR = new Image(getClass().getResourceAsStream("Very_Mad_Emoji.png"));
				ImageView tempR = new ImageView(okImageR);
				tempR.setFitHeight(16.0);
				tempR.setFitHeight(16.0);
				tempR.setPreserveRatio(true);
				smileyR.setGraphic(tempR);
				
				smileyR.setOnAction(e -> { 
					chatBox.appendText("0x0FF0R");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF0R");
				});
				
				Button smileyS = new Button();
				Image okImageS = new Image(getClass().getResourceAsStream("Very_Angry_Emoji.png"));
				ImageView tempS = new ImageView(okImageS);
				tempS.setFitHeight(16.0);
				tempS.setFitHeight(16.0);
				tempS.setPreserveRatio(true);
				smileyS.setGraphic(tempS);
				
				smileyS.setOnAction(e -> { 
					chatBox.appendText("0x0FF0S");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF0S");
				});
				
				Button smileyT = new Button();
				Image okImageT = new Image(getClass().getResourceAsStream("Loudly_Crying_Face_Emoji.png"));
				ImageView tempT = new ImageView(okImageT);
				tempT.setFitHeight(16.0);
				tempT.setFitHeight(16.0);
				tempT.setPreserveRatio(true);
				smileyT.setGraphic(tempT);
				
				smileyS.setOnAction(e -> { 
					chatBox.appendText("0x0FF0T");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF0T");
				});
				
				Button smileyU = new Button();
				Image okImageU = new Image(getClass().getResourceAsStream("Disappointed_but_Relieved_Face_Emoji.png"));
				ImageView tempU = new ImageView(okImageU);
				tempU.setFitHeight(16.0);
				tempU.setFitHeight(16.0);
				tempU.setPreserveRatio(true);
				smileyU.setGraphic(tempU);
				
				smileyU.setOnAction(e -> { 
					chatBox.appendText("0x0FF0U");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF0U");
				});
				
				Button smileyV = new Button();
				Image okImageV = new Image(getClass().getResourceAsStream("Crying_Face_Emoji.png"));
				ImageView tempV = new ImageView(okImageV);
				tempV.setFitHeight(16.0);
				tempV.setFitHeight(16.0);
				tempV.setPreserveRatio(true);
				smileyV.setGraphic(tempV);
				
				smileyV.setOnAction(e -> { 
					chatBox.appendText("0x0FF0V");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF0V");
				});
				
				Button smileyW = new Button();
				Image okImageW = new Image(getClass().getResourceAsStream("Poop_Emoji.png"));
				ImageView tempW = new ImageView(okImageW);
				tempW.setFitHeight(16.0);
				tempW.setFitHeight(16.0);
				tempW.setPreserveRatio(true);
				smileyW.setGraphic(tempW);
				
				smileyW.setOnAction(e -> { 
					chatBox.appendText("0x0FF0W");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF0W");
				});
				
				Button smileyX = new Button();
				Image okImageX = new Image(getClass().getResourceAsStream("Smiling_Devil_Emoji.png"));
				ImageView tempX = new ImageView(okImageX);
				tempX.setFitHeight(16.0);
				tempX.setFitHeight(16.0);
				tempX.setPreserveRatio(true);
				smileyX.setGraphic(tempX);
				
				smileyX.setOnAction(e -> { 
					chatBox.appendText("0x0FF0X");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF0X");
				});
				
				Button smileyY = new Button();
				Image okImageY = new Image(getClass().getResourceAsStream("Alien_Emoji.png"));
				ImageView tempY = new ImageView(okImageY);
				tempY.setFitHeight(16.0);
				tempY.setFitHeight(16.0);
				tempY.setPreserveRatio(true);
				smileyY.setGraphic(tempY);
				
				smileyY.setOnAction(e -> { 
					chatBox.appendText("0x0FF0Y");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF0Y");
				});
				
				Button smileyZ = new Button();
				Image okImageZ = new Image(getClass().getResourceAsStream("Ghost_Emoji.png"));
				ImageView tempZ = new ImageView(okImageZ);
				tempZ.setFitHeight(16.0);
				tempZ.setFitHeight(16.0);
				tempZ.setPreserveRatio(true);
				smileyZ.setGraphic(tempZ);
				
				smileyZ.setOnAction(e -> { 
					chatBox.appendText("0x0FF0Z");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF0Z");
				});
				
				Button extraA = new Button();
				Image extraImageA = new Image(getClass().getResourceAsStream("Tongue_Emoji.png"));
				ImageView extraTempA = new ImageView(extraImageA);
				extraTempA.setFitHeight(16.0);
				extraTempA.setFitHeight(16.0);
				extraTempA.setPreserveRatio(true);
				extraA.setGraphic(extraTempA);
				
				extraA.setOnAction(e -> { 
					chatBox.appendText("0x0FF10");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF10");
				});
				
				Button extraB = new Button();
				Image extraImageB = new Image(getClass().getResourceAsStream("Virus_Emoji.png"));
				ImageView extraTempB = new ImageView(extraImageB);
				extraTempB.setFitHeight(16.0);
				extraTempB.setFitHeight(16.0);
				extraTempB.setPreserveRatio(true);
				extraB.setGraphic(extraTempB);
				
				extraB.setOnAction(e -> { 
					chatBox.appendText("0x0FF11");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF11");
				});
				
				Button extraC = new Button();
				Image extraImageC = new Image(getClass().getResourceAsStream("Eyes_Emoji.png"));
				ImageView extraTempC = new ImageView(extraImageC);
				extraTempC.setFitHeight(16.0);
				extraTempC.setFitHeight(16.0);
				extraTempC.setPreserveRatio(true);
				extraC.setGraphic(extraTempC);
				
				extraC.setOnAction(e -> { 
					chatBox.appendText("0x0FF12");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF12");
				});
				
				Button extraD = new Button();
				Image extraImageD = new Image(getClass().getResourceAsStream("Woman_Saying_No_Emoji.png"));
				ImageView extraTempD = new ImageView(extraImageD);
				extraTempD.setFitHeight(16.0);
				extraTempD.setFitHeight(16.0);
				extraTempD.setPreserveRatio(true);
				extraD.setGraphic(extraTempD);
				
				extraD.setOnAction(e -> { 
					chatBox.appendText("0x0FF13");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF13");
				});
				
				Button extraE = new Button();
				Image extraImageE = new Image(getClass().getResourceAsStream("Woman_Hand_Gesture_Emoji.png"));
				ImageView extraTempE = new ImageView(extraImageE);
				extraTempE.setFitHeight(16.0);
				extraTempE.setFitHeight(16.0);
				extraTempE.setPreserveRatio(true);
				extraE.setGraphic(extraTempE);
				
				extraE.setOnAction(e -> { 
					chatBox.appendText("0x0FF14");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF14");
				});
				
				Button extraF = new Button();
				Image extraImageF = new Image(getClass().getResourceAsStream("Woman_Face_Massage_Emoji.png"));
				ImageView extraTempF = new ImageView(extraImageF);
				extraTempF.setFitHeight(16.0);
				extraTempF.setFitHeight(16.0);
				extraTempF.setPreserveRatio(true);
				extraF.setGraphic(extraTempF);
				
				extraF.setOnAction(e -> { 
					chatBox.appendText("0x0FF15");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF15");
				});
				
				Button extraG = new Button();
				Image extraImageG = new Image(getClass().getResourceAsStream("Unknown_Man_Emoji.png"));
				ImageView extraTempG = new ImageView(extraImageG);
				extraTempG.setFitHeight(16.0);
				extraTempG.setFitHeight(16.0);
				extraTempG.setPreserveRatio(true);
				extraG.setGraphic(extraTempG);
				
				extraG.setOnAction(e -> { 
					chatBox.appendText("0x0FF16");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF16");
				});
				
				Button extraH = new Button();
				Image extraImageH = new Image(getClass().getResourceAsStream("White_Santa_Claus_Emoji.png"));
				ImageView extraTempH = new ImageView(extraImageH);
				extraTempH.setFitHeight(16.0);
				extraTempH.setFitHeight(16.0);
				extraTempH.setPreserveRatio(true);
				extraH.setGraphic(extraTempH);
				
				extraH.setOnAction(e -> { 
					chatBox.appendText("0x0FF17");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF17");
				});
				
				Button extraI = new Button();
				Image extraImageI = new Image(getClass().getResourceAsStream("White_Grandpa_Emoji.png"));
				ImageView extraTempI = new ImageView(extraImageI);
				extraTempI.setFitHeight(16.0);
				extraTempI.setFitHeight(16.0);
				extraTempI.setPreserveRatio(true);
				extraI.setGraphic(extraTempI);
				
				extraI.setOnAction(e -> { 
					chatBox.appendText("0x0FF18");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF18");
				});
				
				Button extraJ = new Button();
				Image extraImageJ = new Image(getClass().getResourceAsStream("White_Grandma_Emoji.png"));
				ImageView extraTempJ = new ImageView(extraImageJ);
				extraTempJ.setFitHeight(16.0);
				extraTempJ.setFitHeight(16.0);
				extraTempJ.setPreserveRatio(true);
				extraJ.setGraphic(extraTempJ);
				
				extraJ.setOnAction(e -> { 
					chatBox.appendText("0x0FF19");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF19");
				});
				
				Button extraK = new Button();
				Image extraImageK = new Image(getClass().getResourceAsStream("White_Baby_Emoji.png"));
				ImageView extraTempK = new ImageView(extraImageK);
				extraTempK.setFitHeight(16.0);
				extraTempK.setFitHeight(16.0);
				extraTempK.setPreserveRatio(true);
				extraK.setGraphic(extraTempK);
				
				extraK.setOnAction(e -> { 
					chatBox.appendText("0x0FF1A");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF1A");
				});
				
				Button extraL = new Button();
				Image extraImageL = new Image(getClass().getResourceAsStream("White_Baby_Angel_Emoji.png"));
				ImageView extraTempL = new ImageView(extraImageL);
				extraTempL.setFitHeight(16.0);
				extraTempL.setFitHeight(16.0);
				extraTempL.setPreserveRatio(true);
				extraL.setGraphic(extraTempL);
				
				extraL.setOnAction(e -> { 
					chatBox.appendText("0x0FF1B");
					sendButton.fire();
					System.out.println("emoji sent: 0x0FF1B");
				});
				
				
			    root.add(smiley, 0, 0);
			    root.add(smiley1, 1, 0);
			    root.add(smiley2, 2, 0);
			    root.add(smiley3, 3, 0);
			    root.add(smiley4, 4, 0);
			    root.add(smiley5, 5,  0);
			    root.add(smiley6, 0, 1);
			    root.add(smiley7, 1, 1);
			    root.add(smiley8, 2, 1);
			    root.add(smiley9, 3, 1);
			    root.add(smileyA, 4, 1);
			    root.add(smileyB, 5, 1);
			    root.add(smileyC, 0, 2);
			    root.add(smileyD, 1, 2);
			    root.add(smileyE, 2, 2);
			    root.add(smileyF, 3, 2);
			    root.add(smileyG, 4, 2);
			    root.add(smileyH, 5, 2);
			    root.add(smileyI, 0, 3);
			    root.add(smileyJ, 1, 3);
			    root.add(smileyK, 2, 3);
			    root.add(smileyL, 3, 3);
			    root.add(smileyM, 4, 3);
			    root.add(smileyN, 5, 3);
			    root.add(smileyO, 0, 4);
			    root.add(smileyP, 1, 4);
			    root.add(smileyQ, 2, 4);
			    root.add(smileyR, 3, 4);
			    root.add(smileyS, 4, 4);
			    root.add(smileyT, 5, 4);
			    root.add(smileyU, 0, 5);
			    root.add(smileyV, 1, 5);
			    root.add(smileyW, 2, 5);
			    root.add(smileyX, 3, 5);
			    root.add(smileyY, 4, 5);
			    root.add(smileyZ, 5, 5);
			    root.add(extraA, 0, 6);
			    root.add(extraB, 1, 6);
			    root.add(extraC, 2, 6);
			    root.add(extraD, 3, 6);
			    root.add(extraE, 4, 6);
			    root.add(extraF, 5, 6);
			    root.add(extraG, 0, 7);
			    root.add(extraH, 1, 7);
			    root.add(extraI, 2, 7);
			    root.add(extraJ, 3, 7);
			    root.add(extraK, 4, 7);
			    root.add(extraL, 5, 7);
			    
			    
			    
			    
				emojiStage.setScene(worldScene);
				emojiStage.setX(495);
				emojiStage.setY(503);
				toggleEmoji = true;
				emojiStage.show();
			}
			else {
				toggleEmoji = false;
				emojiStage.close();
			}
	
		});

	}
	
	class IncomingReader implements Runnable {
		// private String name;
		
		public IncomingReader(String name) {
			clientName = name;
		}
		
		public void run() {
			// String message;
			try {
				while ((message = reader.readLine()) != null) {
					if (message.contains("new")) {
						String[] online = message.replace("new", "").split(" ");
						for (int client = 0; client < online.length; client += 1) {
							if (!online[client].equals(clientName) && !clients.contains(online[client])) {
								// Then, client is new. Add to drop down menu.
								CheckBox cb = new CheckBox(online[client]);		
								clients.add(online[client]);
								CustomMenuItem newClient = new CustomMenuItem(cb);
								newClient.setHideOnClick(false); 
								newClient.setText(online[client]);
								clientsOnline.getItems().add(newClient);
								peopleOnline.appendText(online[client] + "\n");
							}
						}
					}
					else {
						Platform.runLater( () -> { 
							// Display the client number 
							Text text = new Text(message);
							
							System.out.println("message recieved: " + message);				            	
				                // Add new line if not the first child
				            text = new Text("\n" + message);

			            	if (message.contains("0x0FF00")) {				            	
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Slightly_Smiling_Face_Emoji.png"));
				                ImageView imageView = new ImageView(okImage1);
				                imageView.setFitHeight(16.0);
								imageView.setFitHeight(16.0);
								imageView.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF00", ""));
				                incoming.getChildren().addAll(text,imageView);
				            } 
			            	else if (message.contains("0x0FF01")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Smiling_Face_Emoji_with_Blushed_Cheeks.png"));
				                ImageView imageView1 = new ImageView(okImage1);
				                imageView1.setFitHeight(16.0);
								imageView1.setFitHeight(16.0);
								imageView1.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF01", ""));
				                incoming.getChildren().addAll(text,imageView1);
				            }
				            else if (message.contains("0x0FF02")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Smiling_Face_Emoji.png"));
				                ImageView imageView2 = new ImageView(okImage1);
				                imageView2.setFitHeight(16.0);
								imageView2.setFitHeight(16.0);
								text.setText(text.getText().replace("0x0FF02", ""));
								imageView2.setPreserveRatio(true);
				                incoming.getChildren().addAll(text,imageView2);
				            }
				            else if (message.contains("0x0FF03")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Smiling_Emoji_with_Smiling_Eyes.png"));
				                ImageView imageView3 = new ImageView(okImage1);
				                imageView3.setFitHeight(16.0);
								imageView3.setFitHeight(16.0);
								imageView3.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF03", ""));
				                incoming.getChildren().addAll(text,imageView3);
				            }
				            else if (message.contains("0x0FF04")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Smiling_Emoji_with_Eyes_Opened.png"));
				                ImageView imageView4 = new ImageView(okImage1);
				                imageView4.setFitHeight(16.0);
								imageView4.setFitHeight(16.0);
								imageView4.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF04", ""));
				                incoming.getChildren().addAll(text,imageView4);
				            }
				            else if (message.contains("0x0FF05")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Smiling_Face_with_Tightly_Closed_eyes.png"));
				                ImageView imageView5 = new ImageView(okImage1);
				                imageView5.setFitHeight(16.0);
								imageView5.setFitHeight(16.0);
								imageView5.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF05", ""));
				                incoming.getChildren().addAll(text,imageView5);
				            }
				            else if (message.contains("0x0FF06")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Smiling_with_Sweat_Emoji.png"));
				                ImageView imageView6 = new ImageView(okImage1);
				                imageView6.setFitHeight(16.0);
								imageView6.setFitHeight(16.0);
								imageView6.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF06", ""));
				                incoming.getChildren().addAll(text, imageView6);
				            }
				            else if (message.contains("0x0FF07")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Tears_of_Joy_Emoji.png"));
				                ImageView imageView7 = new ImageView(okImage1);
				                imageView7.setFitHeight(16.0);
								imageView7.setFitHeight(16.0);
								imageView7.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF07", ""));
				                incoming.getChildren().addAll(text, imageView7);
				            }
				            else if (message.contains("0x0FF08")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Smirk_Face_Emoji.png"));
				                ImageView imageView8 = new ImageView(okImage1);
				                imageView8.setFitHeight(16.0);
								imageView8.setFitHeight(16.0);
								imageView8.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF08", ""));
				                incoming.getChildren().addAll(text, imageView8);
				            }
				            else if (message.contains("0x0FF09")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Sad_Face_Emoji.png"));
				                ImageView imageView9 = new ImageView(okImage1);
				                imageView9.setFitHeight(16.0);
								imageView9.setFitHeight(16.0);
								imageView9.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF09", ""));
				                incoming.getChildren().addAll(text, imageView9);
				            }
				            else if (message.contains("0x0FF0A")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Face_With_Rolling_Eyes_Emoji.png"));
				                ImageView imageViewA = new ImageView(okImage1);
				                imageViewA.setFitHeight(16.0);
								imageViewA.setFitHeight(16.0);
								imageViewA.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF0A", ""));
				                incoming.getChildren().addAll(text, imageViewA);
				            }
				            else if (message.contains("0x0FF0B")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Thinking_Face_Emoji.png"));
				                ImageView imageViewB = new ImageView(okImage1);
				                imageViewB.setFitHeight(16.0);
								imageViewB.setFitHeight(16.0);
								imageViewB.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF0B", ""));
				                incoming.getChildren().addAll(text, imageViewB);
				            }
				            else if (message.contains("0x0FF0C")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Surprised_Face_Emoji.png"));
				                ImageView imageViewC = new ImageView(okImage1);
				                imageViewC.setFitHeight(16.0);
								imageViewC.setFitHeight(16.0);
								imageViewC.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF0C", ""));
				                incoming.getChildren().addAll(text, imageViewC);
				            }
				            else if (message.contains("0x0FF0D")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("OMG_Face_Emoji.png"));
				                ImageView imageViewD = new ImageView(okImage1);
				                imageViewD.setFitHeight(16.0);
								imageViewD.setFitHeight(16.0);
								imageViewD.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF0D", ""));
				                incoming.getChildren().addAll(text, imageViewD);
				            }
				            else if (message.contains("0x0FF0E")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Upside-Down_Face_Emoji.png"));
				                ImageView imageViewE = new ImageView(okImage1);
				                imageViewE.setFitHeight(16.0);
								imageViewE.setFitHeight(16.0);
								imageViewE.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF0E", ""));
				                incoming.getChildren().addAll(text, imageViewE);
				            }
				            else if (message.contains("0x0FF0F")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Heart_Eyes_Emoji.png"));
				                ImageView imageViewF = new ImageView(okImage1);
				                imageViewF.setFitHeight(16.0);
								imageViewF.setFitHeight(16.0);
								imageViewF.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF0F", ""));
				                incoming.getChildren().addAll(text, imageViewF);
				            }
				            else if (message.contains("0x0FF0G")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Sunglasses_Emoji.png"));
				                ImageView imageViewG = new ImageView(okImage1);
				                imageViewG.setFitHeight(16.0);
								imageViewG.setFitHeight(16.0);
								imageViewG.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF0G", ""));
				                incoming.getChildren().addAll(text, imageViewG);
				            }
				            else if (message.contains("0x0FF0H")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Tongue_Out_Emoji.png"));
				                ImageView imageViewH = new ImageView(okImage1);
				                imageViewH.setFitHeight(16.0);
								imageViewH.setFitHeight(16.0);
								imageViewH.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF0H", ""));
				                incoming.getChildren().addAll(text, imageViewH);
				            }
				            else if (message.contains("0x0FF0I")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Kissing_Face_with_Smiling_Eyes_Emoji.png"));
				                ImageView imageViewI = new ImageView(okImage1);
				                imageViewI.setFitHeight(16.0);
								imageViewI.setFitHeight(16.0);
								imageViewI.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF0I", ""));
				                incoming.getChildren().addAll(text, imageViewI);
				            }
				            else if (message.contains("0x0FF0J")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Grinning_Emoji_with_Smiling_Eyes.png"));
				                ImageView imageViewJ = new ImageView(okImage1);
				                imageViewJ.setFitHeight(16.0);
								imageViewJ.setFitHeight(16.0);
								imageViewJ.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF0J", ""));
				                incoming.getChildren().addAll(text, imageViewJ);
				            }
				            else if (message.contains("0x0FF0K")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Hungry_Face_Emoji.png"));
				                ImageView imageViewK = new ImageView(okImage1);
				                imageViewK.setFitHeight(16.0);
								imageViewK.setFitHeight(16.0);
								imageViewK.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF0K", ""));
				                incoming.getChildren().addAll(text, imageViewK);
				            }
				            else if (message.contains("0x0FF0L")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Wink_Emoji.png"));
				                ImageView imageViewL = new ImageView(okImage1);
				                imageViewL.setFitHeight(16.0);
								imageViewL.setFitHeight(16.0);
								imageViewL.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF0L", ""));
				                incoming.getChildren().addAll(text, imageViewL);
				            }
				            else if (message.contains("0x0FF0M")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Blow_Kiss_Emoji.png"));
				                ImageView imageViewM = new ImageView(okImage1);
				                imageViewM.setFitHeight(16.0);
								imageViewM.setFitHeight(16.0);
								imageViewM.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF0M", ""));
				                incoming.getChildren().addAll(text, imageViewM);
				            }
				            else if (message.contains("0x0FF0N")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Flushed_Face_Emoji.png"));
				                ImageView imageViewN = new ImageView(okImage1);
				                imageViewN.setFitHeight(16.0);
								imageViewN.setFitHeight(16.0);
								imageViewN.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF0N", ""));
				                incoming.getChildren().addAll(text, imageViewN);
				            }
				            else if (message.contains("0x0FF0O")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Smiling_Face_with_Halo.png"));
				                ImageView imageViewO = new ImageView(okImage1);
				                imageViewO.setFitHeight(16.0);
								imageViewO.setFitHeight(16.0);
								imageViewO.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF0O", ""));
				                incoming.getChildren().addAll(text, imageViewO);
				            }
				            else if (message.contains("0x0FF0P")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Sleeping_Emoji.png"));
				                ImageView imageViewP = new ImageView(okImage1);
				                imageViewP.setFitHeight(16.0);
								imageViewP.setFitHeight(16.0);
								imageViewP.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF0P", ""));
				                incoming.getChildren().addAll(text, imageViewP);
				            }
				            else if (message.contains("0x0FF0Q")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Very_sad_emoji_icon_png.png"));
				                ImageView imageViewQ = new ImageView(okImage1);
				                imageViewQ.setFitHeight(16.0);
								imageViewQ.setFitHeight(16.0);
								imageViewQ.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF0Q", ""));
				                incoming.getChildren().addAll(text, imageViewQ);
				            }
				            else if (message.contains("0x0FF0R")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Very_Mad_Emoji.png"));
				                ImageView imageViewR = new ImageView(okImage1);
				                imageViewR.setFitHeight(16.0);
								imageViewR.setFitHeight(16.0);
								imageViewR.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF0R", ""));
				                incoming.getChildren().addAll(text, imageViewR);
				            }
				            else if (message.contains("0x0FF0S")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Very_Angry_Emoji.png"));
				                ImageView imageViewS = new ImageView(okImage1);
				                imageViewS.setFitHeight(16.0);
								imageViewS.setFitHeight(16.0);
								imageViewS.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF0S", ""));
				                incoming.getChildren().addAll(text, imageViewS);
				            }
				            else if (message.contains("0x0FF0T")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Loudly_Crying_Face_Emoji.png"));
				                ImageView imageViewT = new ImageView(okImage1);
				                imageViewT.setFitHeight(16.0);
								imageViewT.setFitHeight(16.0);
								imageViewT.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF0T", ""));
				                incoming.getChildren().addAll(text, imageViewT);
				            }
				            else if (message.contains("0x0FF0U")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Disappointed_but_Relieved_Face_Emoji.png"));
				                ImageView imageViewU = new ImageView(okImage1);
				                imageViewU.setFitHeight(16.0);
								imageViewU.setFitHeight(16.0);
								imageViewU.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF0U", ""));
				                incoming.getChildren().addAll(text, imageViewU);
				            }
				            else if (message.contains("0x0FF0V")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Crying_Face_Emoji.png"));
				                ImageView imageViewV = new ImageView(okImage1);
				                imageViewV.setFitHeight(16.0);
								imageViewV.setFitHeight(16.0);
								imageViewV.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF0V", ""));
				                incoming.getChildren().addAll(text, imageViewV);
				            }
				            else if (message.contains("0x0FF0W")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Poop_Emoji.png"));
				                ImageView imageViewW = new ImageView(okImage1);
				                imageViewW.setFitHeight(16.0);
								imageViewW.setFitHeight(16.0);
								imageViewW.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF0W", ""));
				                incoming.getChildren().addAll(text, imageViewW);
				            }
				            else if (message.contains("0x0FF0X")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Smiling_Devil_Emoji.png"));
				                ImageView imageViewX = new ImageView(okImage1);
				                imageViewX.setFitHeight(16.0);
								imageViewX.setFitHeight(16.0);
								imageViewX.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF0X", ""));
				                incoming.getChildren().addAll(text, imageViewX);
				            }
				            else if (message.contains("0x0FF0Y")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Alien_Emoji.png"));
				                ImageView imageViewY = new ImageView(okImage1);
				                imageViewY.setFitHeight(16.0);
								imageViewY.setFitHeight(16.0);
								imageViewY.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF0Y", ""));
				                incoming.getChildren().addAll(text, imageViewY);
				            }
				            else if (message.contains("0x0FF0Z")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Ghost_Emoji.png"));
				                ImageView imageViewZ = new ImageView(okImage1);
				                imageViewZ.setFitHeight(16.0);
								imageViewZ.setFitHeight(16.0);
								imageViewZ.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF0Z", ""));
				                incoming.getChildren().addAll(text, imageViewZ);
				            }
				            else if (message.contains("0x0FF10")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Tongue_Emoji.png"));
				                ImageView imageViewExtraA = new ImageView(okImage1);
				                imageViewExtraA.setFitHeight(16.0);
				                imageViewExtraA.setFitHeight(16.0);
				                imageViewExtraA.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF10", ""));
				                incoming.getChildren().addAll(text, imageViewExtraA);
				            }
				            else if (message.contains("0x0FF11")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Virus_Emoji.png"));
				                ImageView imageViewExtraB = new ImageView(okImage1);
				                imageViewExtraB.setFitHeight(16.0);
				                imageViewExtraB.setFitHeight(16.0);
				                imageViewExtraB.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF11", ""));
				                incoming.getChildren().addAll(text, imageViewExtraB);
				            }
				            else if (message.contains("0x0FF12")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Eyes_Emoji.png"));
				                ImageView imageViewExtraC = new ImageView(okImage1);
				                imageViewExtraC.setFitHeight(16.0);
				                imageViewExtraC.setFitHeight(16.0);
				                imageViewExtraC.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF12", ""));
				                incoming.getChildren().addAll(text, imageViewExtraC);
				            }
				            else if (message.contains("0x0FF13")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Woman_Saying_No_Emoji.png"));
				                ImageView imageViewExtraD = new ImageView(okImage1);
				                imageViewExtraD.setFitHeight(16.0);
				                imageViewExtraD.setFitHeight(16.0);
				                imageViewExtraD.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF13", ""));
				                incoming.getChildren().addAll(text, imageViewExtraD);
				            }
				            else if (message.contains("0x0FF14")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Woman_Hand_Gesture_Emoji.png"));
				                ImageView imageViewExtraE = new ImageView(okImage1);
				                imageViewExtraE.setFitHeight(16.0);
				                imageViewExtraE.setFitHeight(16.0);
				                imageViewExtraE.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF14", ""));
				                incoming.getChildren().addAll(text, imageViewExtraE);
				            }
				            else if (message.contains("0x0FF15")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Woman_Face_Massage_Emoji.png"));
				                ImageView imageViewExtraF = new ImageView(okImage1);
				                imageViewExtraF.setFitHeight(16.0);
				                imageViewExtraF.setFitHeight(16.0);
				                imageViewExtraF.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF15", ""));
				                incoming.getChildren().addAll(text, imageViewExtraF);
				            }
				            else if (message.contains("0x0FF16")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Unknown_Man_Emoji.png"));
				                ImageView imageViewExtraG = new ImageView(okImage1);
				                imageViewExtraG.setFitHeight(16.0);
				                imageViewExtraG.setFitHeight(16.0);
				                imageViewExtraG.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF16", ""));
				                incoming.getChildren().addAll(text, imageViewExtraG);
				            }
				            else if (message.contains("0x0FF17")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("White_Santa_Claus_Emoji.png"));
				                ImageView imageViewExtraH = new ImageView(okImage1);
				                imageViewExtraH.setFitHeight(16.0);
				                imageViewExtraH.setFitHeight(16.0);
				                imageViewExtraH.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF17", ""));
				                incoming.getChildren().addAll(text, imageViewExtraH);
				            }
				            else if (message.contains("0x0FF18")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("White_Grandpa_Emoji.png"));
				                ImageView imageViewExtraI = new ImageView(okImage1);
				                imageViewExtraI.setFitHeight(16.0);
				                imageViewExtraI.setFitHeight(16.0);
				                imageViewExtraI.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF18", ""));
				                incoming.getChildren().addAll(text, imageViewExtraI);
				            }
				            else if (message.contains("0x0FF19")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("White_Grandma_Emoji.png"));
				                ImageView imageViewExtraJ = new ImageView(okImage1);
				                imageViewExtraJ.setFitHeight(16.0);
				                imageViewExtraJ.setFitHeight(16.0);
				                imageViewExtraJ.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF19", ""));
				                incoming.getChildren().addAll(text, imageViewExtraJ);
				            }
				            else if (message.contains("0x0FF1A")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("White_Baby_Emoji.png"));
				                ImageView imageViewExtraK = new ImageView(okImage1);
				                imageViewExtraK.setFitHeight(16.0);
				                imageViewExtraK.setFitHeight(16.0);
				                imageViewExtraK.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF1A", ""));
				                incoming.getChildren().addAll(text, imageViewExtraK);
				            }
				            else if (message.contains("0x0FF1B")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("White_Baby_Angel_Emoji.png"));
				                ImageView imageViewExtraL = new ImageView(okImage1);
				                imageViewExtraL.setFitHeight(16.0);
				                imageViewExtraL.setFitHeight(16.0);
				                imageViewExtraL.setPreserveRatio(true);
								text.setText(text.getText().replace("0x0FF1B", ""));
				                incoming.getChildren().addAll(text, imageViewExtraL);
				            }
				            else if (message.contains("BibleThump")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("BibleThump.png"));
				                ImageView BibleThump = new ImageView(okImage1);
				                BibleThump.setFitHeight(20.0);
				                BibleThump.setFitHeight(20.0);
				                BibleThump.setPreserveRatio(true);
								text.setText(text.getText().replace("BibleThump", ""));
				                incoming.getChildren().addAll(text, BibleThump);
				            }
				            else if (message.contains("Kappa")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("Kappa.png"));
				                ImageView Kappa = new ImageView(okImage1);
				                Kappa.setFitHeight(20.0);
				                Kappa.setFitHeight(20.0);
				                Kappa.setPreserveRatio(true);
								text.setText(text.getText().replace("Kappa", ""));
				                incoming.getChildren().addAll(text, Kappa);
				            }
				            else if (message.contains("PogChamp")) {
				            	Image okImage1 = new Image(getClass().getResourceAsStream("PogChamp.png"));
				                ImageView PogChamp = new ImageView(okImage1);
				                PogChamp.setFitHeight(20.0);
				                PogChamp.setFitHeight(20.0);
				                PogChamp.setPreserveRatio(true);
								text.setText(text.getText().replace("PogChamp", ""));
				                incoming.getChildren().addAll(text, PogChamp);
				            }
				            else { 
				            	try {
				            		incoming.getChildren().add(text);
				            	}
				    			catch(Exception e) {
				    				
				    			}
				            }

						}); 
						
						// incoming.appendText(message + "\n");
					}
				}
			} 
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private void addFXObjects(ArrayList<Node> FX, Pane pane) {
		for (Node obj : FX) {
			pane.getChildren().add(obj);
		}
	}
}