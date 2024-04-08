package application;

import java.io.*;

import java.util.*;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;

public class Messaging {
	private Stage stage;
	private VBox centerBox = new VBox(20);
    private int user_value;
    private Button logoutButton = new Button("Logout");
    private Button menuButton = new Button("Main Menu");
    
    //This is the function other class can call
    public Messaging(Stage stage, int user_value) {
    	this.stage = stage;
        this.user_value = user_value;
        BuildMessageView(user_value);
        stage.getScene().setRoot(centerBox);
        stage.show();
    }
	
    //function that return back to menu based on the user_value (1,2,3)
	private void backToMenu(int value) {
		CSE360_Main mainPage = new CSE360_Main();
		
		if(value == 1) {
			mainPage.patientHome();
			//have to modify. It is raising exception: this.stage = null
		}
		else if(value == 2) {
			System.out.println("Doctor menu triggered");
			mainPage.doctorHome();
			//have to modify.  It is raising exception: this.stage = null
		}
		else if(value == 3)
		{
			mainPage.nurseHome();
			//have to modify.  It is raising exception: this.stage = null
		}
	}
	
	private void logout()
	{
		CSE360_Main mainPage = new CSE360_Main();
		mainPage.start(stage);
	}
	
	
	//functions to see the received message
	/*private void readMessage() {
		
		//Logic to read the message from the inbox list
	}*/
	
	// Add any other functions here
	
	private void BuildMessageView(int choice_value) {
		
		centerBox.getChildren().clear();
		
		Label messageLabel = new Label("Inbox Messages");
		ListView<String> inbox = new ListView<>();
		
		
		TextField RecipientField = new TextField();
		TextField SubjectField = new TextField();
		TextArea messageArea = new TextArea();
		RecipientField.setPromptText("send to: ");
		SubjectField.setPromptText("sub: ");
		messageArea.setPromptText("Write your message here");
		
		Button sendButton = new Button("Send");
		sendButton.setOnAction(event-> {
			
			//to make sure all the text boxes are filled
			String Recipient = RecipientField.getText().trim();
			String subject = SubjectField.getText().trim();
			String message = messageArea.getText().trim();
			
			if(Recipient.isEmpty())
			{
				Alert recipientEmpty = new Alert(Alert.AlertType.ERROR, "TO field isempty!");
				recipientEmpty.showAndWait();
			}
			else if(subject.isEmpty())
			{
				Alert subjectEmpty = new Alert(Alert.AlertType.ERROR, "Subject is empty!");
				subjectEmpty.showAndWait();
			}
			else if(message.isEmpty())
			{
				Alert messageEmpty = new Alert(Alert.AlertType.ERROR, "Message field is empty!");
				messageEmpty.showAndWait();
			}
			
			//logic for send button goes here.
			
			
			//clear and reset all the text fields
			RecipientField.clear();
			SubjectField.clear();
			messageArea.clear();
			RecipientField.setPromptText("send to: ");
			SubjectField.setPromptText("sub: ");
			messageArea.setPromptText("Write your message here");
			
		});
		
		//menu button
		menuButton.setOnAction(event -> backToMenu(choice_value));
		logoutButton.setOnAction(event -> logout());
		
		HBox buttonBox = new HBox(100);
        buttonBox.getChildren().addAll(logoutButton, menuButton, sendButton);
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);
        
		//page layout
		HBox messageBox = new HBox(10,
				new VBox(10, messageLabel, inbox),
				new VBox(10, new Label("Message"), RecipientField, SubjectField, messageArea
		));
		
		messageBox.setAlignment(Pos.CENTER);
		centerBox.getChildren().addAll(messageBox, buttonBox);
		stage.sizeToScene();
	}
}
