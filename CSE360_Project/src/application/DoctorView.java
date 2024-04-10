
package application;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/* Class: The Doctor GUI and functions
 * 
 * Author: Devesh Mohanta
 * 
 * Creation: 04/08/2024
 * 
 * Last Revised: 04/09/2024
 * 
 * Version: 1.11 */

public class DoctorView extends CSE360_Main {
    private VBox centerBox = new VBox(20);
    private Button logout = new Button("Logout");

    public DoctorView(Stage primaryStage) {
        super();
        this.primaryStage = primaryStage;

        buildDoctorView();
    }
    
    /* This is the actual doctor home page. To login, type in the password "doctorPassword" to enter the doctor view.
	 * It will show buttons for View Records, Send Message, and Enter Exam Info.
	 * Logout is within the entire class itself. */
	private void buildDoctorView() {
	    // Check for null before accessing primaryStage
	    if (this.primaryStage == null) {
	        throw new IllegalStateException("Primary stage has not been initialized."); //Debug statement
	    }
	    Label welcomeLabel = new Label("Welcome Doctor!");
	
	    Button viewRecordsBtn = new Button("View Records");
	    Button sendMessageBtn = new Button("Send Message");
	    Button enterExamInfoBtn = new Button("Enter Exam Info");
	
	    sendMessageBtn.setOnAction(event -> sendMessage());
	    viewRecordsBtn.setOnAction(event -> promptForPatientIDForViewingRecords());
	    enterExamInfoBtn.setOnAction(event -> promptForPatientIDForExam());
	    logout.setOnAction(event -> logout());
	
	    centerBox.getChildren().clear();
	    centerBox.getChildren().addAll(welcomeLabel, viewRecordsBtn, sendMessageBtn, enterExamInfoBtn, logout); //Adds all the buttons on the home page
	    centerBox.setAlignment(Pos.CENTER);
	
	    if (primaryStage.getScene() == null) { //In case the doctor page has never been initialized, this will show up
	        Scene scene = new Scene(centerBox, 500, 250);
	        primaryStage.setScene(scene);
	    } else { //If the doctor is still logged in, it should stay to the previous instance of the doctorHome.
	        primaryStage.getScene().setRoot(centerBox);
	    }
	
	    primaryStage.show();
	}

	//Prompts for patientID so that the doctor does not go to a blank page when trying to enter exam
    private void promptForPatientIDForExam() {
        // Input dialog for patient ID
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Patient ID");
        dialog.setHeaderText("Enter the Patient ID");

        // Process the response
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(patientID -> {
            if (!patientID.isEmpty() && checkPatientId(patientID)) { //Leads to the patient Exam page
                enterExamInfo(patientID);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Invalid or non-existent Patient ID.");
                alert.showAndWait();
            }
        });
    }
    
  //Prompts for patientID so that the doctor does not go to a blank page when trying to enter the patient records
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
    
    //Through a menu button on either the view record, message, or enter exam windows, this will lead the user back to doctorHome.
    private void backToDoctorHome() {
    	buildDoctorView();
    }

    private void viewRecords(String patientID) {
        centerBox.getChildren().clear();

        // Check if patientID is valid, should be covered by method
        if (patientID.isEmpty() || !checkPatientId(patientID)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Patient ID.");
            alert.showAndWait();
            return;
        }

        // Now that we have a valid patient ID, proceed to display the patient records
        displayPatientRecords(patientID, false);
    }

    //Method to handle checking patientIDs in the system
    private boolean checkPatientId(String patientID) {
        System.out.println("Checking patient ID: " + patientID); // Debug output

        // The special debug case where "test" is always considered a valid ID
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

    //Displays the patient records window. It sets all of the labels, text areas, etc. into a NON-EDITABLE page.
    private void displayPatientRecords(String patientID, boolean isEditable) {
        centerBox.getChildren().clear();

        // GridPane for form layout
        GridPane formGrid = new GridPane();
        formGrid.setAlignment(Pos.CENTER);
        formGrid.setHgap(10); // Horizontal gap between columns
        formGrid.setVgap(10); // Vertical gap between rows

        // Column 1 - Labels
        Label heightLabel = new Label("Height:");
        Label weightLabel = new Label("Weight:");
        Label bodyTempLabel = new Label("Body Temp:");
        Label bloodPressureLabel = new Label("Blood Pressure:");
        Label allergiesLabel = new Label("Allergies:");
        Label healthConcernsLabel = new Label("Health Concerns:");
        Label prescriptionsLabel = new Label("Prescriptions:");

        // Column 2 - Textfields
        TextField heightField = new TextField();
        TextField weightField = new TextField();
        TextField bodyTempField = new TextField();
        TextField bloodPressureField = new TextField();
        TextField allergiesField = new TextField();
        TextField healthConcernsField = new TextField();
        TextField prescriptionsField = new TextField();

        // Set text fields to be editable based on the isEditable parameter
        heightField.setEditable(isEditable);
        weightField.setEditable(isEditable);
        bodyTempField.setEditable(isEditable);
        bloodPressureField.setEditable(isEditable);
        allergiesField.setEditable(isEditable);
        healthConcernsField.setEditable(isEditable);
        prescriptionsField.setEditable(isEditable);

        // Add labels and text fields to the grid
        formGrid.add(heightLabel, 0, 0);
        formGrid.add(heightField, 1, 0);
        formGrid.add(weightLabel, 0, 1);
        formGrid.add(weightField, 1, 1);
        formGrid.add(bodyTempLabel, 0, 2);
        formGrid.add(bodyTempField, 1, 2);
        formGrid.add(bloodPressureLabel, 0, 3);
        formGrid.add(bloodPressureField, 1, 3);
        formGrid.add(allergiesLabel, 0, 4);
        formGrid.add(allergiesField, 1, 4);
        formGrid.add(healthConcernsLabel, 0, 5);
        formGrid.add(healthConcernsField, 1, 5);
        formGrid.add(prescriptionsLabel, 0, 6);
        formGrid.add(prescriptionsField, 1, 6);

        // Set column constraints to align labels to the right (end of the cell)
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHalignment(HPos.RIGHT);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHgrow(Priority.ALWAYS); // Allow text fields to grow
        formGrid.getColumnConstraints().addAll(column1, column2);

        // Add the form grid to the center box
        centerBox.getChildren().add(formGrid);


        // Column 3 - Doctor Recommendations text area
        VBox recommendationsAndRecordsColumn = new VBox(10);
        Label recommendationsLabel = new Label("Doctor Recommendations");
        TextArea recommendationsArea = new TextArea();
        recommendationsArea.setPrefWidth(200); //Set preferred width for text area
        recommendationsArea.setPrefHeight(300); // Set preferred height for the text area
        recommendationsArea.setEditable(isEditable);
        recommendationsAndRecordsColumn.getChildren().addAll(recommendationsLabel, recommendationsArea);

     // Column 4 - File Selector, select a file on the right and then press the View Visit button in order for the file's contents to show up on the page
        VBox medicalRecordsColumn = new VBox(10);
        medicalRecordsColumn.setAlignment(Pos.CENTER);
        ListView<String> medicalRecordsListView = new ListView<>();
        medicalRecordsListView.setPrefHeight(200); // Set preferred height for the list view
        File patientFolder = new File(patientID + "/PatientVisits");

        // Update the regex to match the new file naming convention: patientID_yy.MM.dd.txt
        String regexPattern = patientID + "_\\d{2}\\.\\d{2}\\.\\d{2}\\.txt";
        String[] medicalRecordFiles = patientFolder.list((dir, name) -> name.matches(regexPattern));
        if (medicalRecordFiles != null) {
            medicalRecordsListView.getItems().addAll(medicalRecordFiles);
        }
        medicalRecordsColumn.getChildren().addAll(medicalRecordsListView);

        // Buttons for View Visit and Go Back
        Button viewVisitButton = new Button("View Visit");
        Button goBackButton = new Button("Go Back");
        if (isEditable) {
            Button saveExamInfoButton = new Button("Save Exam Info");
            saveExamInfoButton.setOnAction(event -> saveExamInfo(patientID, heightField, weightField, bodyTempField, bloodPressureField, allergiesField, healthConcernsField, prescriptionsField, recommendationsArea));
            centerBox.getChildren().add(saveExamInfoButton);
        }

        // Set the action for the "View Visit" button, this will show the contents of the patient record's file.
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
        HBox mainContent = new HBox(20, formGrid, recommendationsAndRecordsColumn, medicalRecordsColumn);
        mainContent.setAlignment(Pos.CENTER);

        // Add all components to the centerBox
        centerBox.getChildren().addAll(mainContent, buttonsBox);
    }

    /*This handles the parsing of the file for the patient records.
     * Format is like this, and then this method parses the information into the records or exam page
     * Date: 04/09/2024
     * Height: 5'11
     * Weight: 150
     * Body Temp: 400
     * Blood Pressure: 30
     * Allergies: shrimp, rice
     * Health Concerns: N/A
     * Prescriptions: Advil
     * Doctor recommendations: 
     * 
     * - Take 2 more doses of Advil */
    
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

    
    //placeholder until the messaging class is in place (this will redirect to a messaging view)
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

    //This allows the user to enter exam info (so editing the patient Records page and saving it
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

    //The user can save the patient Records with any changes, and it will create a text file with the format patientID_MedicalRecords_NUMBER.txt where NUMBER increments by 1
    private void saveExamInfo(String patientID, TextField heightField, TextField weightField, TextField bodyTempField, TextField bloodPressureField, TextField allergiesField, TextField healthConcernsField, TextField prescriptionsField, TextArea recommendationsArea) {
        // Date format for inside the file - MM/dd/yyyy
        String todayDateForFile = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        // Date format for the filename - yy.MM.dd
        String todayDateForFilename = LocalDate.now().format(DateTimeFormatter.ofPattern("yy.MM.dd"));
    	// Gather the data from the fields
        String height = heightField.getText();
        String weight = weightField.getText();
        String bodyTemp = bodyTempField.getText();
        String bloodPressure = bloodPressureField.getText();
        String allergies = allergiesField.getText();
        String healthConcerns = healthConcernsField.getText();
        String prescriptions = prescriptionsField.getText();
        String doctorRecommendations = recommendationsArea.getText();

        // Directory path for saving the exam information
        String directoryPath = patientID + "/PatientVisits";
        File patientDirectory = new File(directoryPath);
        if (!patientDirectory.exists()) {
            patientDirectory.mkdirs(); // Create the directory if it doesn't exist
        }

        // Filename using the new date format
        String filename = directoryPath + "/" + patientID + "_" + todayDateForFilename + ".txt";

        // Writing the data to the file
        try (PrintWriter writer = new PrintWriter(filename, "UTF-8")) {
            writer.println("Date: " + todayDateForFile);
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

    // Logs out of the doctor view, back into the home page.
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
