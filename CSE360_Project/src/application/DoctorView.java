package application;

import java.io.*;
import java.util.*;

import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DoctorView extends CSE360_Main {
    private String userName;
    private VBox centerBox = new VBox(20);
    private Button logout = new Button("Logout");

    public DoctorView(String userName, Stage primaryStage) {
    	super();
    	this.primaryStage = primaryStage;
        this.userName = userName;
        createDoctorAccountFile(); // Create a file when the doctor logs in
        buildDoctorView();
    }

    private void createDoctorAccountFile() {
        String filename = userName + "_account.txt";
        try (PrintWriter writer = new PrintWriter(new File(filename))) {
            writer.println("Doctor: " + userName);
            writer.println("Login Time: " + new Date());
            System.out.println("Account file created for Doctor: " + userName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void promptForPatientIDForExam() {
        // Input dialog for patient ID
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Patient ID");
        dialog.setHeaderText("Enter the Patient ID");

        // Process the response
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(patientID -> {
            if (!patientID.isEmpty() && checkPatientId(patientID)) {
                enterExamInfo(patientID);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Invalid or non-existent Patient ID.");
                alert.showAndWait();
            }
        });
    }
    
    private void promptForPatientIDForViewingRecords() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Patient ID");
        dialog.setHeaderText("Enter the Patient ID");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(patientID -> {
            if (!patientID.isEmpty() && checkPatientId(patientID)) {
                viewRecords(patientID);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Invalid or non-existent Patient ID.");
                alert.showAndWait();
            }
        });
    }
    
    private void buildDoctorView() {
        // Check for null before accessing primaryStage
        if (this.primaryStage == null) {
            throw new IllegalStateException("Primary stage has not been initialized.");
        }
        String accountData = loadDoctorAccountData();

        Label welcomeLabel = new Label("Welcome Doctor " + userName);
        Label accountInfoLabel = new Label(accountData); 

        Button viewRecordsBtn = new Button("View Records");
        Button sendMessageBtn = new Button("Send Message");
        Button enterExamInfoBtn = new Button("Enter Exam Info");

        sendMessageBtn.setOnAction(event -> sendMessage());
        viewRecordsBtn.setOnAction(event -> promptForPatientIDForViewingRecords());
        enterExamInfoBtn.setOnAction(event -> promptForPatientIDForExam());
        logout.setOnAction(event -> logout());

        centerBox.getChildren().clear();
        centerBox.getChildren().addAll(welcomeLabel, accountInfoLabel, viewRecordsBtn, sendMessageBtn, enterExamInfoBtn, logout);
        centerBox.setAlignment(Pos.CENTER);

        if (primaryStage.getScene() == null) {
            Scene scene = new Scene(centerBox, 500, 250);
            primaryStage.setScene(scene);
        } else {
            primaryStage.getScene().setRoot(centerBox);
        }

        primaryStage.show();
    }

    private String loadDoctorAccountData() {
        String filename = userName + "_account.txt";
        File accountFile = new File(filename);
        if (accountFile.exists()) {
            try (Scanner scanner = new Scanner(accountFile)) {
                StringBuilder data = new StringBuilder();
                while (scanner.hasNextLine()) {
                    data.append(scanner.nextLine()).append("\n");
                }
                return data.toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return "No account data found";
    }

    
    private void backToDoctorHome() {
    	buildDoctorView();
    }

    private void viewRecords(String patientID) {
        centerBox.getChildren().clear();

        // Check if patientID is valid (it should be, as it's already been verified)
        if (patientID.isEmpty() || !checkPatientId(patientID)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Patient ID.");
            alert.showAndWait();
            return;
        }

        // Now that we have a valid patient ID, proceed to display the patient records
        displayPatientRecords(patientID);
        primaryStage.sizeToScene(); // Adjust size if needed
    }

    private boolean checkPatientId(String patientID) {
        // The special case where "test" is always considered a valid ID
        if (patientID.equals("test")) {
            return true;
        }

        // Directory where the patient files are stored (adjust if needed)
        File directory = new File("."); // Current working directory

        // Filename filter to find files that match the patient ID
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith(patientID + "_") && name.endsWith("_records.txt");
            }
        };

        // Get all files that match the filter
        File[] files = directory.listFiles(filter);

        // Check if there are any files that match the patient ID
        return files != null && files.length > 0;
    }
    
    private void displayPatientRecords(String patientID) {
        // Clear the center box and prepare to display records
        centerBox.getChildren().clear();

        // GUI elements
        Label patientRecordsLabel = new Label("Patient Records for: " + patientID);
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
        primaryStage.sizeToScene(); // Adjust size if needed
    }

    private void sendMessage() {
        // Placeholder implementation
        System.out.println("sendMessage functionality will be implemented by another team member.");
        
        // You might still want to clear the centerBox or display a placeholder message
        centerBox.getChildren().clear();
        
        Label placeholderLabel = new Label("Messaging feature coming soon.");
        centerBox.getChildren().add(placeholderLabel);
        
        // Optionally, you can provide a button to go back to the doctor home
        Button homeButton = new Button("Home");
        homeButton.setOnAction(event -> backToDoctorHome());
        centerBox.getChildren().add(homeButton);
        
        primaryStage.sizeToScene(); // Adjust size if needed
    }

    private void enterExamInfo(String patientID) {
        centerBox.getChildren().clear();

        // Prompt for the patient's account name (already verified)
        Label patientNameLabel = new Label("Patient's Account Name: " + patientID);

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
        saveButton.setOnAction(event -> saveExamInfo(patientID, physicalExamNotesArea, medicationPrescribedBox));

        Button menuButton = new Button("Menu");
        menuButton.setOnAction(event -> backToDoctorHome());

        // Layout for patient name label and buttons
        VBox patientNameBox = new VBox(10, patientNameLabel);
        HBox buttonBox = new HBox(10, saveButton, menuButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT); // Align to the bottom-right

        // Layout for exam info
        HBox examInfoBox = new HBox(10, 
            new VBox(5, examLabel, physicalExamNotesArea), 
            medicationPrescribedBox
        );

        // Add all components to the centerBox
        centerBox.getChildren().addAll(patientNameBox, examInfoBox, buttonBox);
        primaryStage.sizeToScene(); // Adjust size if needed
    }

    private void displayExamInfoInput(String patientID) {
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
        primaryStage.sizeToScene(); // Adjust size if needed
    }

    private void saveExamInfo(String patientID, TextArea physicalExamNotesArea, VBox medicationPrescribedBox) {
        
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

        String filename = patientID + "_exam_info.txt";

        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            writer.println("Patient Account Name: " + patientID);
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
        // Clear any existing content in the main pane
        mainPane.getChildren().clear();
        centerBox.getChildren().clear();
        loginBox.getChildren().clear();

        // Build the login view again using the method from CSE360_Main
        // Since DoctorView extends CSE360_Main, it has access to its methods
        buildLogin();

        // Set the primary scene back to the main pane which now has the login view
        primaryStage.setScene(new Scene(mainPane, 500, 250));
        primaryStage.show();
    }
}

