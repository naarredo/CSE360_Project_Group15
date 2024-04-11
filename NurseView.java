package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class NurseView extends CSE360_Main {
	//private String userName;
	private BorderPane nursePane = new BorderPane();
	private BorderPane recordsPane = new BorderPane();
	private BorderPane vitalsPane = new BorderPane();
	private BorderPane preVitalPane = new BorderPane();
	private BorderPane additionalPane = new BorderPane();
	private BorderPane preMessagePane = new BorderPane(); 
	private BorderPane preSendPane = new BorderPane();
	private VBox centerBox = new VBox(20);
	
	private Scene nurseMain = new Scene(nursePane, 800,400);
	private Scene preVital = new Scene(preVitalPane,800,400);
	private Scene records = new Scene(recordsPane,800,400);
	private Scene vitals = new Scene(vitalsPane,800,400);
	private Scene additionalInfo = new Scene(additionalPane,800,400);
	private Scene preMessage = new Scene(preMessagePane, 800,400);
	private Scene preSendMessage = new Scene(preSendPane, 800, 400); 

	private String firstName;
	private String lastName;
	private String dateOfB;
	private String weight;
	private String height;
	private String bodyTemp;
	private String bPressure;
	private String knownAllergies;
	private String healthConcerns;
	private String prescribedMed;
	private String immunizationRecord;
	private String patientID;

    File messageFile; 

	public NurseView(Stage primaryStage) {
		super();
		//this.userName = userName;
		this.primaryStage = primaryStage;
		nurseMainPage();
	}

	private void nurseMainPage() {
		
		primaryStage.setScene(nurseMain);
		vitalsPrePage();
		vitalsPage();
		additionalPage();
		messagePrePage(); 
		sendMessagePrePage();
		recordsPane.setCenter(centerBox);
		//messagePrePage(); 
		
		Button viewRecords = new Button("View Records");
		Button enterVitals = new Button("Enter Vitals");
		Button messagePortal = new Button("Messages");
		Button logoutButton = new Button("Logout");

		
		HBox mainHolder = new HBox();

		VBox recordsVitalsHolder = new VBox();	
		VBox headLabels = new VBox();
		VBox msgLogHolder = new VBox();
		VBox leftHolder = new VBox();
		
		Label welcome = new Label("Welcome, Nurse");
		Label optionSelect = new Label("Please select an option:");
		
		if(this.primaryStage == null)
			throw new IllegalStateException("Primary Stage has not been initiated");
		
		logoutButton.setPrefSize(70, 20);
		messagePortal.setPrefSize(70, 20);
		viewRecords.setPrefSize(90,20);
		enterVitals.setPrefSize(90,20);
		
		headLabels.getChildren().addAll(welcome,optionSelect);
		msgLogHolder.getChildren().addAll(messagePortal, logoutButton);
		recordsVitalsHolder.getChildren().addAll(viewRecords,enterVitals);
		leftHolder.getChildren().addAll(headLabels,recordsVitalsHolder);
		msgLogHolder.setSpacing(5);
		headLabels.setSpacing(10);
		recordsVitalsHolder.setSpacing(20);
		leftHolder.setSpacing(30);
		headLabels.setAlignment(Pos.CENTER);
		recordsVitalsHolder.setAlignment(Pos.CENTER);
		msgLogHolder.setAlignment(Pos.TOP_RIGHT);
		
		mainHolder.setAlignment(Pos.CENTER);
		mainHolder.setSpacing(25);
		mainHolder.getChildren().addAll(leftHolder,msgLogHolder);
		mainHolder.setPadding(new Insets(10,0,10,130));
		msgLogHolder.setPadding(new Insets(0,0,0,50));
		
		nursePane.setCenter(mainHolder);
		
	    viewRecords.setOnAction(e -> {
	        promptForPatientIDForViewingRecords();
	    });
		messagePortal.setOnAction(e -> primaryStage.setScene(preMessage));
		enterVitals.setOnAction(e -> primaryStage.setScene(preVital));
		logoutButton.setOnAction(e -> {
			buildLogin();
			primaryStage.setScene(new Scene(mainPane,500,250));
		});
        
	}

    //Method to handle checking patientIDs in the system
    private boolean checkPatientId(String patientID) {
        System.out.println("Checking patient ID: " + patientID); // Debug output

        // The directory name is the patient ID itself
        File patientFolder = new File("Patients", patientID);

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
	
	//Prompts for patientID so that the doctor does not go to a blank page when trying to enter the patient records
    private void promptForPatientIDForViewingRecords() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Patient ID Input");
        dialog.setHeaderText("Enter Patient ID");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(patientID -> {
            System.out.println("Patient ID entered: " + patientID); // Debug output
            if (!patientID.isEmpty() && checkPatientId(patientID)) {
                System.out.println("Valid Patient ID. Proceeding to display records."); // Debug output
                recordsPage(patientID); // Assuming recordsPage method handles displaying records for the given ID
                primaryStage.setScene(records); // Change to the appropriate scene
            } else {
                System.out.println("Invalid or non-existent Patient ID entered."); // Debug output
                Alert alert = new Alert(AlertType.ERROR, "Invalid or non-existent Patient ID.");
                alert.showAndWait();
            }
        });
    }

	
	private void vitalsPrePage() {
		
		Label patInfo = new Label("Patient Information:"), fName = new Label("First Name:"), lName = new Label("Last Name:"), dob = new Label("Date of Birth:");
		
		HBox infoHoldBox = new HBox(), contButtonHold = new HBox();
		
		VBox contBox = new VBox(), infoLabelBox = new VBox(),infoTextFieldBox = new VBox(); 

		Button cont = new Button("Continue");
		
		TextField nameField = new TextField(), nameField2 = new TextField(), birthField  = new TextField();
		
		infoLabelBox.getChildren().addAll(fName,lName,dob);
		infoTextFieldBox.getChildren().addAll(nameField,nameField2,birthField);
		infoHoldBox.getChildren().addAll(infoLabelBox,infoTextFieldBox);
		contButtonHold.getChildren().add(cont);
		contBox.getChildren().addAll(patInfo,infoHoldBox,contButtonHold);
		contBox.setPadding(new Insets(10,10,10,10));
		contBox.setSpacing(15);
		infoTextFieldBox.setSpacing(15);
		infoLabelBox.setSpacing(25);
		
		preVitalPane.setPadding(new Insets(10,10,10,10));
		preVitalPane.setCenter(contBox);
		
	    cont.setOnAction(e -> {
	        firstName = nameField.getText();
	        lastName = nameField2.getText();
	        dateOfB = birthField.getText();

	        if (firstName.isBlank() || lastName.isBlank() || dateOfB.isBlank()) {
	            System.out.println("Blank Values");
	        } else {
	            try {
	                DateTimeFormatter dobFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	                LocalDate birthDate = LocalDate.parse(dateOfB, dobFormatter);
	                LocalDate currentDate = LocalDate.now();
	                int age = Period.between(birthDate, currentDate).getYears();

	                if (age < 12) {
	                    // Show alert for under-aged patient
	                    Alert alert = new Alert(AlertType.INFORMATION);
	                    alert.setTitle("Age Restriction");
	                    alert.setHeaderText(null);
	                    alert.setContentText("Patient is under 12 years old. Not eligible for vitals entry.");

	                    alert.showAndWait(); // Show the alert and wait for it to be closed

	                    primaryStage.setScene(nurseMain); // Redirect to nurse home page
	                } else {
	                    primaryStage.setScene(vitals); // Proceed to vitals page
	                }
	            } catch (DateTimeParseException ex) {
	                Alert errorAlert = new Alert(AlertType.ERROR);
	                errorAlert.setTitle("Date Format Error");
	                errorAlert.setHeaderText(null);
	                errorAlert.setContentText("Invalid date format. Please enter date of birth in MM/dd/yyyy format.");

	                errorAlert.showAndWait(); // Show the error alert

	                // Optionally, redirect to a different scene or take other actions
	            }
	        }
	        nameField.clear();
	        nameField2.clear();
	        birthField.clear();
	    });
	}
	
    //Displays the patient records window. It sets all of the labels, text areas, etc. into a NON-EDITABLE page.
    private void recordsPage(String patientID) {
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
        heightField.setEditable(false);
        weightField.setEditable(false);
        bodyTempField.setEditable(false);
        bloodPressureField.setEditable(false);
        allergiesField.setEditable(false);
        healthConcernsField.setEditable(false);
        prescriptionsField.setEditable(false);

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
        recommendationsArea.setEditable(false);
        recommendationsAndRecordsColumn.getChildren().addAll(recommendationsLabel, recommendationsArea);

     // Column 4 - File Selector, select a file on the right and then press the View Visit button in order for the file's contents to show up on the page
        VBox medicalRecordsColumn = new VBox(10);
        medicalRecordsColumn.setAlignment(Pos.CENTER);
        ListView<String> medicalRecordsListView = new ListView<>();
        medicalRecordsListView.setPrefHeight(200); // Set preferred height for the list view
        File patientFolder = new File("Patients" + File.separator + patientID + File.separator + "PatientRecords");

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

        goBackButton.setOnAction(event -> primaryStage.setScene(nurseMain));

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
	
	private void vitalsPage() {
		
		Label weightLabel = new Label("Weight:"), heightLabel = new Label("Height:"), bodyTempLabel = new Label("Body Temp:"),bPressureLabel = new Label("Blood Pressure:"),
		lbsLabel = new Label("lbs"), cmLabel = new Label("cm"),fahrenLabel = new Label("F°"), mmHgLabel = new Label("mmHg"), headLabel = new Label("Enter Patient Vitals:");
		
		
		HBox infoHoldBox3 = new HBox();
		
		VBox infoLabelBox3 = new VBox(), infoTextFieldBox3 = new VBox(), infoLabelBox4 = new VBox(),vitalsBottomBox = new VBox();
		
		TextField weightField = new TextField(), heightField = new TextField(),bTempField = new TextField(), bPressureField = new TextField();
		

		Button continueButton = new Button("Continue");

		headLabel.setFont(new Font("Arial",20));
		headLabel.setPadding(new Insets(0,0,10,0));
		
		infoLabelBox3.getChildren().addAll(weightLabel,heightLabel,bodyTempLabel,bPressureLabel);
		infoTextFieldBox3.getChildren().addAll(weightField,heightField,bTempField,bPressureField);
		infoLabelBox4.getChildren().addAll(lbsLabel,cmLabel,fahrenLabel,mmHgLabel);
		infoHoldBox3.getChildren().addAll(infoLabelBox3,infoTextFieldBox3,infoLabelBox4);
		vitalsBottomBox.getChildren().addAll(continueButton);
		infoLabelBox3.setSpacing(25);
		infoTextFieldBox3.setSpacing(15);
		infoLabelBox4.setSpacing(25);
		infoHoldBox3.setSpacing(10);
		vitalsBottomBox.setSpacing(15);
		vitalsBottomBox.setAlignment(Pos.CENTER);
		vitalsBottomBox.setPadding(new Insets(10,0,0,0));
		
		vitalsPane.setPadding(new Insets(10,10,10,10));
		vitalsPane.setTop(headLabel);
		vitalsPane.setLeft(infoHoldBox3);
		vitalsPane.setBottom(vitalsBottomBox);
		
		continueButton.setOnAction(e -> {
			weight = weightField.getText();
			height = heightField.getText();
			bodyTemp = bTempField.getText();
			bPressure = bPressureField.getText();
			
			weightField.clear();
			heightField.clear();
			bTempField.clear();
			bPressureField.clear();
			primaryStage.setScene(additionalInfo);
		});
	}

	private void additionalPage() {
		
		Label additionalInfoLabel = new Label("Addtional Information"), allergiesLabel = new Label("Known Allergies"), concernsLabel = new Label("Health Concerns");
		
		TextArea allergyField = new TextArea(), concernField = new TextArea();

		VBox infoLabelBox5 = new VBox(), infoTextFieldBox4 = new VBox(),bottomEnterBox = new VBox();
		
		HBox additionalInfoBox = new HBox();
		
		Button enterButton = new Button("Enter");
		
		infoLabelBox5.getChildren().addAll(allergiesLabel,concernsLabel);
		infoTextFieldBox4.getChildren().addAll(allergyField,concernField);
		additionalInfoBox.getChildren().addAll(infoLabelBox5,infoTextFieldBox4);
		bottomEnterBox.getChildren().add(enterButton);
		bottomEnterBox.setAlignment(Pos.BOTTOM_RIGHT);
		infoLabelBox5.setSpacing(75);
		infoTextFieldBox4.setSpacing(15);
		allergyField.setPrefSize(300, 75);
		concernField.setPrefSize(300, 75);
		additionalInfoLabel.setFont(new Font("Arial",20));
		additionalInfoLabel.setPadding(new Insets(0,0,10,0));
		allergiesLabel.setPadding(new Insets(25,0,0,0));
		bottomEnterBox.setPadding(new Insets(0,35,0,0));
		
		additionalPane.setTop(additionalInfoLabel);
		additionalPane.setCenter(additionalInfoBox);
		additionalPane.setBottom(bottomEnterBox);
		additionalPane.setPadding(new Insets(10,10,10,10));
		
		// get text from textAreas and store into patient file.
		enterButton.setOnAction(e -> {
		    knownAllergies = allergyField.getText();
		    healthConcerns = concernField.getText();

		    // Generate the current date in yy.MM.dd format
		    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy.MM.dd");
		    LocalDate localDate = LocalDate.now();
		    String currentDate = dtf.format(localDate);
		    
		    // Generate the patientID
		    patientID = lastName.substring(0, Math.min(3, lastName.length())) 
		                + firstName.substring(0, Math.min(2, firstName.length())) 
		                + dateOfB.substring(0, Math.min(2, dateOfB.length()));

		    // Create the patient folder
		    File patientFolder = new File("Patients", patientID);
		    if (!patientFolder.exists()) {
		        patientFolder.mkdirs();
		    }

		    // Create the PatientVisits folder within the patient folder
		    File patientVisitsFolder = new File(patientFolder, "PatientRecords");
		    if (!patientVisitsFolder.exists()) {
		        patientVisitsFolder.mkdirs();
		    }

		    // Create the patient file within the PatientVisits folder
		    File patientFile = new File(patientVisitsFolder, patientID + "_" + currentDate + ".txt");

		    try {
		        FileWriter fw = new FileWriter(patientFile);
		        BufferedWriter bw = new BufferedWriter(fw);

		        // Write data to the file
		        bw.write("Weight: " + weight); bw.newLine();
		        bw.write("Height: " + height); bw.newLine();
		        bw.write("Body Temp: " + bodyTemp); bw.newLine();
		        bw.write("Blood Pressure: " + bPressure); bw.newLine();
		        bw.write("Allergies: " + knownAllergies); bw.newLine();
		        bw.write("Health Concerns: " + healthConcerns); bw.newLine();
		        bw.write("Prescriptions: "); bw.newLine();
		        bw.write("Doctor recommendations: "); bw.newLine();
		        bw.close();
		    } catch (IOException e1) {
		        e1.printStackTrace();
		    }
		    primaryStage.setScene(nurseMain);    
		});
	}
	
	private void messagePrePage() {
		Button sendMessage = new Button("Send Message"), home = new Button("Return Home"), viewMessage = new Button("View Message");
		TextArea sender = new TextArea(), re = new TextArea(), message = new TextArea(); 
        ListView<String> messagesListView = new ListView<>(); // ListView for displaying visits
        String userDirectoryPath; 
        Label recieved = new Label("Recieved Messages:"), from = new Label("From:"), regarding = new Label("RE:");
        VBox messageList = new VBox(), textList = new VBox(), leftPane = new VBox(); 
        HBox buttonList = new HBox(20), mainPane = new HBox(30);
        
        preMessagePane.getChildren().clear();
        
        sender.setEditable(false);
        re.setEditable(false);
        message.setEditable(false);
        sender.setPrefSize(200, 10);
        re.setPrefSize(200, 10);
        message.setPrefSize(200, 300);
        // Read the directory and list available records
        userDirectoryPath = "Nurse" + File.separator + "RecievedMessages" + File.separator;
        
        // Read the directory and list available records
        File recordsDir = new File(userDirectoryPath);
        File[] recordsFiles = recordsDir.listFiles();
        
        if (recordsFiles != null) {
            for (File file : recordsFiles) {
                String fileName = file.getName();
                messagesListView.getItems().add(fileName.substring(0, fileName.lastIndexOf('.')));
            }
        }
        
        buttonList.setAlignment(Pos.CENTER);
        buttonList.getChildren().addAll(sendMessage, viewMessage, home); 
        
        textList.setAlignment(Pos.CENTER_LEFT);
        textList.getChildren().addAll(from, sender, regarding, re, message);
        
        leftPane.setAlignment(Pos.CENTER);
        leftPane.getChildren().addAll(textList, buttonList);
        
        messageList.setAlignment(Pos.CENTER);
        messageList.getChildren().addAll(recieved, messagesListView);
        
        mainPane.setAlignment(Pos.CENTER);
        mainPane.getChildren().addAll(leftPane, messageList);

        preMessagePane.setPadding(new Insets(10,10,10,10));
        preMessagePane.setCenter(mainPane);
        
        viewMessage.setOnAction(e -> {
            String selectedRecordName = messagesListView.getSelectionModel().getSelectedItem();
            String messageContents = "", line; 
            
            if (selectedRecordName != null && !selectedRecordName.isEmpty()) {
                String fullPath = userDirectoryPath + File.separator + selectedRecordName + ".txt";
                
                try(BufferedReader reader = new BufferedReader(new FileReader(fullPath))) {
                    sender.setText(reader.readLine());
                    re.setText(reader.readLine());
                    line = reader.readLine();
                    
                    while(line != null) {
                    	messageContents = messageContents + line; 
                    	line = reader.readLine(); 
                    }
                    
                    message.setText(messageContents);
                } catch (IOException ex) {
                    System.out.print("Failed to read record: " + ex.getMessage());
                }
            }
        });
        
        home.setOnAction(e -> primaryStage.setScene(nurseMain));
        sendMessage.setOnAction(e -> primaryStage.setScene(preSendMessage));
	}
	
	private void sendMessagePrePage() {
		Label messageLabel = new Label("Inbox Messages");
		ListView<String> outbox = new ListView<>();	
		TextField RecipientField = new TextField();
		TextField SubjectField = new TextField();
		TextArea messageArea = new TextArea();
		Button sendButton = new Button("Send"), home = new Button("Return Home"), viewMessage = new Button("View Message");
		VBox messageList = new VBox(), textList = new VBox(), leftPane = new VBox(); 
		HBox buttonList = new HBox(20), mainPane = new HBox(30);
		String userDirectoryPath;
		
		preSendPane.getChildren().clear();
		
		RecipientField.setPromptText("Send to: ");
		SubjectField.setPromptText("Re: ");
		messageArea.setPromptText("Write your message here");
		RecipientField.setPrefSize(200, 10);
        SubjectField.setPrefSize(200, 10);
        messageArea.setPrefSize(200, 300);
		
        userDirectoryPath = "Nurse" + File.separator + "SentMessages" + File.separator;
        
        // Read the directory and list available records
        File recordsDir = new File(userDirectoryPath);
        File[] recordsFiles = recordsDir.listFiles();
        
        if (recordsFiles != null) {
            for (File file : recordsFiles) {
                String fileName = file.getName();
                outbox.getItems().add(fileName.substring(0, fileName.lastIndexOf('.')));
            }
        }
        
        buttonList.setAlignment(Pos.CENTER);
        buttonList.getChildren().addAll(sendButton, viewMessage, home); 
        
        textList.setAlignment(Pos.CENTER_LEFT);
        textList.getChildren().addAll(RecipientField, SubjectField, messageArea);
        
        leftPane.setAlignment(Pos.CENTER);
        leftPane.getChildren().addAll(textList, buttonList);
        
        messageList.setAlignment(Pos.CENTER);
        messageList.getChildren().addAll(messageLabel, outbox);
        
        mainPane.setAlignment(Pos.CENTER);
        mainPane.getChildren().addAll(leftPane, messageList);
		
		preSendPane.setPadding(new Insets(10,10,10,10));
        preSendPane.setCenter(mainPane);
		
		sendButton.setOnAction(event-> {	
		//to make sure all the text boxes are filled
			String Recipient = RecipientField.getText().trim();
			String subject = SubjectField.getText().trim();
			String message = messageArea.getText().trim();
			String directory, directory2;
			Boolean flag = false;
			int msgNumber = 0; 
			
			if(Recipient.isEmpty()) {
				Alert recipientEmpty = new Alert(Alert.AlertType.ERROR, "TO field isempty!");
				recipientEmpty.showAndWait();
			} else if(subject.isEmpty()) {
				Alert subjectEmpty = new Alert(Alert.AlertType.ERROR, "Subject is empty!");
				subjectEmpty.showAndWait();
			} else if(message.isEmpty()) {
				Alert messageEmpty = new Alert(Alert.AlertType.ERROR, "Message field is empty!");
				messageEmpty.showAndWait();
			} else {
				//logic for send button goes here.
				File patientDirectory = new File("Patients" + File.separator + Recipient);
				String messageInfo = "Nurse\n" + 
									 subject + "\n" +
									 message; 
				
				if(patientDirectory != null) {
					while(!flag) {
						directory = "Patients" + File.separator + Recipient + File.separator + "RecievedMessages" + File.separator +
    							    Recipient + "msg" + String.format("%03d", msgNumber) + ".txt";
						directory2 = "Nurse" + File.separator + "SentMessages" + File.separator +
							    Recipient + "msg" + String.format("%03d", msgNumber) + ".txt";
    					patientDirectory = new File(directory);
    					
    					if(!patientDirectory.exists()) {
    						flag = true;
    						
    						try(BufferedWriter writer = new BufferedWriter(new FileWriter(directory))) {
    							writer.write(messageInfo);
    						} catch (IOException e) {
    							System.out.print("Should not catch");
    						}
    						
    						try(BufferedWriter writer = new BufferedWriter(new FileWriter(directory2))) {
    							writer.write(messageInfo);
    						} catch (IOException e) {
    							System.out.print("Should not catch");
    						}
    					} else {
    						msgNumber++;
    					}
    				}
				}
				
				//confirmation for message sent
				Alert confirmation = new Alert(Alert.AlertType.INFORMATION, "Message sent!");
				confirmation.showAndWait();
				
				//clear and reset all the text fields
				RecipientField.clear();
				SubjectField.clear();
				messageArea.clear();
				RecipientField.setPromptText("send to: ");
				SubjectField.setPromptText("sub: ");
				messageArea.setPromptText("Write your message here");
			}		
		});
		
		viewMessage.setOnAction(e -> {
            String selectedRecordName = outbox.getSelectionModel().getSelectedItem();
            String messageContents = "", line; 
            
            if (selectedRecordName != null && !selectedRecordName.isEmpty()) {
                String fullPath = userDirectoryPath + File.separator + selectedRecordName + ".txt";
                
                try(BufferedReader reader = new BufferedReader(new FileReader(fullPath))) {
                	RecipientField.setText(selectedRecordName.substring(0, 7));
                    reader.readLine();
                    SubjectField.setText(reader.readLine());
                    line = reader.readLine();
                    
                    while(line != null) {
                    	messageContents = messageContents + line; 
                    	line = reader.readLine(); 
                    }
                    
                    messageArea.setText(messageContents);
                } catch (IOException ex) {
                    System.out.print("Failed to read record: " + ex.getMessage());
                }
            }
        });
		
		//menu button
		home.setOnAction(event -> primaryStage.setScene(nurseMain));		
	}
}
