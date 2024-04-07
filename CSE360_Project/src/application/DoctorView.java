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
        // Adding a test message
        inboxList.getItems().add("From: Test - Subject: Test");

        TextField replyToField = new TextField("Test");
        TextField subjectField = new TextField("RE: Test");
        TextArea messageArea = new TextArea();
        messageArea.setPromptText("Write your message here");

        Button sendButton = new Button("Send");
        sendButton.setOnAction(event -> {
            // Logic to send the message
            String recipient = replyToField.getText();
            String subject = subjectField.getText();
            String message = messageArea.getText();
            // Here you would implement the actual send logic
            // For now, we will just print it to the console
            if ("Test".equalsIgnoreCase(recipient)) {
                System.out.println("Message sent to Test:");
                System.out.println("Subject: " + subject);
                System.out.println("Message: " + message);
                // Clear fields after sending
                replyToField.clear();
                subjectField.clear();
                messageArea.clear();
                // Show confirmation
                Alert confirmation = new Alert(Alert.AlertType.INFORMATION, "Message sent successfully to Test.");
                confirmation.showAndWait();
            } else {
                // Show error if the recipient is not "Test"
                Alert error = new Alert(Alert.AlertType.ERROR, "Can only send message to Test.");
                error.showAndWait();
            }
        });
        
        Button homeButton = new Button("Home");
        homeButton.setOnAction(event -> backToDoctorHome()); // Implement backToDoctorHome() to return to the doctor's main menu

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

        // Prompt for the patient's account name
        Label patientNameLabel = new Label("Patient's Account Name:");
        TextField patientNameField = new TextField();
        patientNameField.setPromptText("Enter Patient Account Name");

        // GUI elements for exam notes
        Label examLabel = new Label("Patient Exam:");
        TextArea physicalExamNotesArea = new TextArea();
        physicalExamNotesArea.setPromptText("Physical Exam Notes");

        // GUI elements for medication prescribed
        VBox medicationPrescribedBox = new VBox(5);
        medicationPrescribedBox.getChildren().add(new Label("Medication Prescribed:"));
        for (int i = 0; i < 3; i++) { // Three text fields for medication
            medicationPrescribedBox.getChildren().add(new TextField());
        }

        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> saveExamInfo(patientNameField.getText(), physicalExamNotesArea, medicationPrescribedBox));

        Button menuButton = new Button("Menu");
        menuButton.setOnAction(event -> backToDoctorHome());

        // Layout for patient name input and buttons
        HBox patientNameBox = new HBox(10, patientNameLabel, patientNameField);
        HBox buttonBox = new HBox(10, saveButton, menuButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT); // Align to the bottom-right

        // Layout for exam info
        HBox examInfoBox = new HBox(10, 
            new VBox(5, examLabel, physicalExamNotesArea), 
            medicationPrescribedBox
        );

        // Add all components to the centerBox
        centerBox.getChildren().addAll(patientNameBox, examInfoBox, buttonBox);
        stage.sizeToScene(); // Adjust size if needed
    }

    private void saveExamInfo(String patientAccountName, TextArea physicalExamNotesArea, VBox medicationPrescribedBox) {
        if (patientAccountName.isEmpty()) {
            // Show an error message or alert if the patient account name is not entered
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter the patient's account name.");
            alert.showAndWait();
            return;
        }
        
        // Continue with saving the exam information
        String physicalExamNotes = physicalExamNotesArea.getText();
        StringBuilder medications = new StringBuilder();
        for (Node node : medicationPrescribedBox.getChildren()) {
            if (node instanceof TextField) {
                String medication = ((TextField) node).getText();
                if (!medication.isEmpty()) {
                    medications.append(medication).append("\n");
                }
            }
        }

        String filename = patientAccountName + "_exam_info.txt";

        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            writer.println("Patient Account Name: " + patientAccountName);
            writer.println("Physical Exam Notes:");
            writer.println(physicalExamNotes);
            writer.println("Medications Prescribed:");
            writer.print(medications.toString());
            writer.close();
            System.out.println("Information saved to " + filename);
            
            // Show confirmation message
            Alert confirmation = new Alert(Alert.AlertType.INFORMATION, "Information saved successfully.");
            confirmation.showAndWait();
            
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

