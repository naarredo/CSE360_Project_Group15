package application;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        createDoctorAccountFile();

        primaryStage.setMaxWidth(3840);
        primaryStage.setMaxHeight(2160);

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
        displayPatientRecords(patientID, false);
    }

    private boolean checkPatientId(String patientID) {
        System.out.println("Checking patient ID: " + patientID); // Debug output

        // The special case where "test" is always considered a valid ID
        if (patientID.equals("test")) {
            return true;
        }

        // The directory name is the patient ID itself
        File patientFolder = new File(patientID);

        // Check if the directory exists and is indeed a directory
        if (!patientFolder.exists()) {
            System.out.println("Patient folder does not exist."); // Debug output
            return false;
        }
        if (!patientFolder.isDirectory()) {
            System.out.println("Patient folder path is not a directory."); // Debug output
            return false;
        }

        // Now check for the specific patient info file within this directory
        File patientInfoFile = new File(patientFolder, patientID + "_patientInfo.txt");
        if (!patientInfoFile.exists()) {
            System.out.println("Patient info file does not exist."); // Debug output
            return false;
        }

        return true;
    }

    private void displayPatientRecords(String patientID, boolean isEditable) {
        centerBox.getChildren().clear();

     // Column 1 - Labels
        VBox labelsColumn = new VBox(10);
        Label heightLabel = new Label("Height:");
        Label weightLabel = new Label("Weight:");
        Label bodyTempLabel = new Label("Body Temp:");
        Label bloodPressureLabel = new Label("Blood Pressure:");
        Label allergiesLabel = new Label("Allergies:");
        Label healthConcernsLabel = new Label("Health Concerns:");
        Label prescriptionsLabel = new Label("Prescriptions:");
        labelsColumn.getChildren().addAll(
            heightLabel,
            weightLabel,
            bodyTempLabel,
            bloodPressureLabel,
            allergiesLabel,
            healthConcernsLabel,
            prescriptionsLabel
        );
        labelsColumn.setAlignment(Pos.CENTER_LEFT);

        // Column 2 - TextFields
        VBox fieldsColumn = new VBox(10);
        TextField heightField = new TextField();
        TextField weightField = new TextField();
        TextField bodyTempField = new TextField();
        TextField bloodPressureField = new TextField();
        TextField allergiesField = new TextField();
        TextField healthConcernsField = new TextField();
        TextField prescriptionsField = new TextField();

        // Bind label heights to text fields to ensure alignment
        heightLabel.prefHeightProperty().bind(heightField.heightProperty());
        weightLabel.prefHeightProperty().bind(weightField.heightProperty());
        bodyTempLabel.prefHeightProperty().bind(bodyTempField.heightProperty());
        bloodPressureLabel.prefHeightProperty().bind(bloodPressureField.heightProperty());
        allergiesLabel.prefHeightProperty().bind(allergiesField.heightProperty());
        healthConcernsLabel.prefHeightProperty().bind(healthConcernsField.heightProperty());
        prescriptionsLabel.prefHeightProperty().bind(prescriptionsField.heightProperty());
        
        // Make text fields editable or not based on the isEditable parameter
        heightField.setEditable(isEditable);
        weightField.setEditable(isEditable);
        bodyTempField.setEditable(isEditable);
        bloodPressureField.setEditable(isEditable);
        allergiesField.setEditable(isEditable);
        healthConcernsField.setEditable(isEditable);
        prescriptionsField.setEditable(isEditable);
        

        fieldsColumn.getChildren().addAll(
            heightField,
            weightField,
            bodyTempField,
            bloodPressureField,
            allergiesField,
            healthConcernsField,
            prescriptionsField
        );


        // Column 3 - Doctor Recommendations
        VBox recommendationsAndRecordsColumn = new VBox(10);
        Label recommendationsLabel = new Label("Doctor Recommendations");
        TextArea recommendationsArea = new TextArea();
        recommendationsArea.setPrefWidth(200);
        recommendationsArea.setPrefHeight(300); // Set preferred height for the text area
        recommendationsArea.setEditable(isEditable);
        recommendationsAndRecordsColumn.getChildren().addAll(recommendationsLabel, recommendationsArea);

        // Column 4 - File Selector
        VBox medicalRecordsColumn = new VBox(10);
        ListView<String> medicalRecordsListView = new ListView<>();
        medicalRecordsListView.setPrefHeight(200); // Set preferred height for the list view
        File patientFolder = new File(patientID + "/PatientRecords");
        String[] medicalRecordFiles = patientFolder.list((dir, name) -> name.matches(patientID + "_MedicalRecords_\\d+.txt"));
        if (medicalRecordFiles != null) {
            medicalRecordsListView.getItems().addAll(medicalRecordFiles);
        }
        medicalRecordsColumn.getChildren().addAll(medicalRecordsListView);

        // Buttons
        Button viewVisitButton = new Button("View Visit");
        Button goBackButton = new Button("Go Back");
        if (isEditable) {
            Button saveExamInfoButton = new Button("Save Exam Info");
            saveExamInfoButton.setOnAction(event -> saveExamInfo(patientID, heightField, weightField, bodyTempField, bloodPressureField, allergiesField, healthConcernsField, prescriptionsField, recommendationsArea));
            centerBox.getChildren().add(saveExamInfoButton);
        }

        // Set the action for the "View Visit" button
        viewVisitButton.setOnAction(event -> {
            String selectedRecord = medicalRecordsListView.getSelectionModel().getSelectedItem();
            if (selectedRecord != null && !selectedRecord.isEmpty()) {
                File selectedFile = new File(patientFolder, selectedRecord);
                parseAndDisplayPatientRecord(selectedFile, heightField, weightField, bodyTempField, bloodPressureField, allergiesField, healthConcernsField, prescriptionsField, recommendationsArea);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please select a record to view.");
                alert.showAndWait();
            }
        });

        goBackButton.setOnAction(event -> backToDoctorHome());

        // Layout for buttons
        HBox buttonsBox = new HBox(10, viewVisitButton, goBackButton);
        buttonsBox.setAlignment(Pos.CENTER);

        // Main layout - Horizontal Box
        HBox mainContent = new HBox(20, labelsColumn, fieldsColumn, recommendationsAndRecordsColumn, medicalRecordsColumn);
        mainContent.setAlignment(Pos.CENTER);

        // Add all components to the centerBox
        centerBox.getChildren().addAll(mainContent, buttonsBox);
    }

    private void parseAndDisplayPatientRecord(File recordFile, TextField heightField, TextField weightField, TextField bodyTempField, TextField bloodPressureField, TextField allergiesField, TextField healthConcernsField, TextField prescriptionsField, TextArea recommendationsArea) {
        try (Scanner scanner = new Scanner(recordFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] keyValue = line.split(":", 2);
                if (keyValue.length == 2) {
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();
                    switch (key) {
                        case "Height":
                            heightField.setText(value);
                            break;
                        case "Weight":
                            weightField.setText(value);
                            break;
                        case "Body Temp":
                            bodyTempField.setText(value);
                            break;
                        case "Blood Pressure":
                            bloodPressureField.setText(value);
                            break;
                        case "Allergies":
                            allergiesField.setText(value);
                            break;
                        case "Health Concerns":
                            healthConcernsField.setText(value);
                            break;
                        case "Prescriptions":
                            prescriptionsField.setText(value);
                            break;
                        case "Doctor recommendations":
                            StringBuilder recommendations = new StringBuilder();
                            recommendations.append(value); // First line of recommendations
                            while (scanner.hasNextLine()) {
                                String recommendationLine = scanner.nextLine().trim(); // Trim each line
                                if (!recommendationLine.isEmpty()) {
                                    recommendations.append("\n").append(recommendationLine);
                                }
                            }
                            recommendationsArea.setText(recommendations.toString());
                            break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
        
    }

    private void enterExamInfo(String patientID) {
        centerBox.getChildren().clear();

        // Check if patientID is valid (it should be, as it's already been verified)
        if (patientID.isEmpty() || !checkPatientId(patientID)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Patient ID.");
            alert.showAndWait();
            return;
        }

        // Now that we have a valid patient ID, proceed to enter new exam info
        // The `true` parameter will make the fields editable
        displayPatientRecords(patientID, true);
    }


    private void saveExamInfo(String patientID, TextField heightField, TextField weightField, TextField bodyTempField, TextField bloodPressureField, TextField allergiesField, TextField healthConcernsField, TextField prescriptionsField, TextArea recommendationsArea) {
        // Get today's date in the desired format
        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    	// Gather the data from the fields
        String height = heightField.getText();
        String weight = weightField.getText();
        String bodyTemp = bodyTempField.getText();
        String bloodPressure = bloodPressureField.getText();
        String allergies = allergiesField.getText();
        String healthConcerns = healthConcernsField.getText();
        String prescriptions = prescriptionsField.getText();
        String doctorRecommendations = recommendationsArea.getText();

        // File path for saving the exam information
        String directoryPath = patientID + "/PatientRecords";
        File patientDirectory = new File(directoryPath);
        if (!patientDirectory.exists()) {
            patientDirectory.mkdirs(); // Create directory if it doesn't exist
        }
        int fileNumber = getNextFileNumber(patientDirectory, patientID + "_MedicalRecords_");
        String filename = directoryPath + "/" + patientID + "_MedicalRecords_" + fileNumber + ".txt";

        // Writing the data to the file
        try (PrintWriter writer = new PrintWriter(filename, "UTF-8")) {
            writer.println("Date: " + todayDate);
            writer.println("Height: " + height);
            writer.println("Weight: " + weight);
            writer.println("Body Temp: " + bodyTemp);
            writer.println("Blood Pressure: " + bloodPressure);
            writer.println("Allergies: " + allergies);
            writer.println("Health Concerns: " + healthConcerns);
            writer.println("Prescriptions: " + prescriptions);
            writer.println("Doctor recommendations:");
            writer.println(doctorRecommendations);

            // Display confirmation alert and refresh the patient records
            Alert confirmation = new Alert(Alert.AlertType.INFORMATION, "Exam information saved successfully.");
            confirmation.showAndWait();

            // Recall displayPatientRecords to refresh the file selector
            displayPatientRecords(patientID, true);
        } catch (IOException e) {
            e.printStackTrace();
            // Display error alert
            Alert error = new Alert(Alert.AlertType.ERROR, "Failed to save exam information.");
            error.showAndWait();
        }
    }


    private int getNextFileNumber(File directory, String prefix) {
        int highestNumber = 0;
        File[] files = directory.listFiles((dir, name) -> name.startsWith(prefix) && name.endsWith(".txt"));
        for (File file : files) {
            String name = file.getName();
            int fileNumber = Integer.parseInt(name.substring(name.lastIndexOf('_') + 1, name.lastIndexOf('.')));
            if (fileNumber > highestNumber) highestNumber = fileNumber;
        }
        return highestNumber + 1; // Increment to the next number
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
