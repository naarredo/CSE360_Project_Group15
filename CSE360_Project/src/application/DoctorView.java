package application;

import java.io.*;
import java.util.*;

import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DoctorView {
    private Stage stage;
    private String userName;
    private VBox centerBox = new VBox(20);
    private Button logout = new Button("Logout");

    public DoctorView(Stage stage, String userName) {
        this.stage = stage;
        this.userName = userName;
        buildDoctorView();
    }

    private void buildDoctorView() {
        Label welcomeLabel = new Label("Welcome Doctor " + userName);
        Button viewRecordsBtn = new Button("View Records");
        Button sendMessageBtn = new Button("Send Message");
        Button enterExamInfoBtn = new Button("Enter Exam Info");

        viewRecordsBtn.setOnAction(event -> viewRecords());
        sendMessageBtn.setOnAction(event -> sendMessage());
        enterExamInfoBtn.setOnAction(event -> enterExamInfo());
        logout.setOnAction(event -> logout());

        centerBox.getChildren().clear();
        centerBox.getChildren().addAll(welcomeLabel, viewRecordsBtn, sendMessageBtn, enterExamInfoBtn, logout);
        centerBox.setAlignment(Pos.CENTER);

        if (stage.getScene() == null) {
            // If there's no scene, create one and set it on the stage
            Scene scene = new Scene(centerBox, 500, 250);
            stage.setScene(scene);
        } else {
            // If a scene already exists, just update its root
            stage.getScene().setRoot(centerBox);
        }

        stage.show();
    }
    
    private void backToDoctorHome() {
    	buildDoctorView();
    }

    private void viewRecords() {
        // Clear the center box and prepare to display records
        centerBox.getChildren().clear();

        // GUI elements
        Label patientRecordsLabel = new Label("Patient Records for: 'patient name'");
        TextArea previousHealthIssuesArea = new TextArea();
        TextArea previouslyPrescribedMedicationArea = new TextArea();
        TextArea immunizationRecordArea = new TextArea();
        TextArea doctorsRecommendationsArea = new TextArea();
        
        previousHealthIssuesArea.setPromptText("Previous Health Issues");
        previouslyPrescribedMedicationArea.setPromptText("Previously Prescribed Medication");
        immunizationRecordArea.setPromptText("Immunization Record");
        doctorsRecommendationsArea.setPromptText("Doctor's Recommendations");

        Button menuButton = new Button("Menu");
        menuButton.setOnAction(event -> backToDoctorHome()); // Implement backToDoctorHome() to return to the doctor's main menu
        
        // Layout
        VBox recordsBox = new VBox(10, 
            patientRecordsLabel, 
            previousHealthIssuesArea, 
            previouslyPrescribedMedicationArea, 
            immunizationRecordArea, 
            doctorsRecommendationsArea, 
            menuButton
        );
        recordsBox.setAlignment(Pos.CENTER);

        centerBox.getChildren().add(recordsBox);
        stage.sizeToScene(); // Adjust size if needed
    }


    private void sendMessage() {
        centerBox.getChildren().clear();

        // GUI elements
        Label messageCenterLabel = new Label("Welcome to the Message Center");
        ListView<String> inboxList = new ListView<>();
        inboxList.getItems().addAll("From: Email Subject Line", "From: Email Subject Line"); // Dummy data

        TextField replyToField = new TextField();
        replyToField.setPromptText("Reply to");
        TextField subjectField = new TextField();
        subjectField.setPromptText("Subject");
        TextArea messageArea = new TextArea();
        messageArea.setPromptText("Write your message here");

        Button sendButton = new Button("Send");
        sendButton.setOnAction(event -> {
            // Logic to send the message
            // For example, sending the message to an email service or database
        });
        
        Button homeButton = new Button("Home");
        homeButton.setOnAction(event -> backToDoctorHome()); 

        // Layout
        HBox messageBox = new HBox(10, 
            new VBox(5, messageCenterLabel, inboxList), 
            new VBox(5, new Label("Message"), replyToField, subjectField, messageArea, sendButton)
        );
        messageBox.setAlignment(Pos.CENTER);

        centerBox.getChildren().addAll(messageBox, homeButton);
        stage.sizeToScene(); // Adjust size if needed
    }


    private void enterExamInfo() {
        centerBox.getChildren().clear();

        // GUI elements
        Label examLabel = new Label("Patient Exam:");
        TextArea physicalExamNotesArea = new TextArea();
        physicalExamNotesArea.setPromptText("Physical Exam Notes");

        VBox medicationPrescribedBox = new VBox(5);
        medicationPrescribedBox.getChildren().add(new Label("Medication Prescribed:"));
        for (int i = 0; i < 3; i++) { // Three text fields for medications
            medicationPrescribedBox.getChildren().add(new TextField());
        }

        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> saveExamInfo(physicalExamNotesArea, medicationPrescribedBox));

        Button menuButton = new Button("Menu");
        menuButton.setOnAction(event -> backToDoctorHome());

        // Layout for buttons
        HBox buttonBox = new HBox(10, saveButton, menuButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT); // Align to the bottom-right

        HBox examInfoBox = new HBox(10, 
            new VBox(5, examLabel, physicalExamNotesArea), 
            medicationPrescribedBox
        );
        examInfoBox.setAlignment(Pos.CENTER);

        // Add all components to the centerBox
        centerBox.getChildren().addAll(examInfoBox, buttonBox);
        stage.sizeToScene(); // Adjust size if needed
    }

    private void saveExamInfo(TextArea physicalExamNotesArea, VBox medicationPrescribedBox) {
        // Extract the text from the TextArea and TextFields
        String physicalExamNotes = physicalExamNotesArea.getText();
        StringBuilder medications = new StringBuilder();
        for (Node node : medicationPrescribedBox.getChildren()) {
            if (node instanceof TextField) {
                medications.append(((TextField) node).getText()).append("\n");
            }
        }

        // Generate a random ID for the filename, could be replaced with a patient ID
        String randomId = UUID.randomUUID().toString();
        String filename = "exam_info_" + randomId + ".txt";

        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            writer.println("Physical Exam Notes:");
            writer.println(physicalExamNotes);
            writer.println("Medications Prescribed:");
            writer.print(medications.toString());
            writer.close();
            System.out.println("Information saved to " + filename);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }



    private void logout() {
        // Logic to go back to the login screen
        // Assuming there's a method in the main class to build the login screen
        CSE360_Main mainApp = new CSE360_Main();
        mainApp.buildLogin(); // Call the method to build the login screen
    }
}

