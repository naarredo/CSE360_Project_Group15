package asuHelloWorldJavaFX;

//Kara Melvin
/* PURPOSE: This is part of a larger system that handles
			functions for a doctor's office. This is the 
			patient view. 
				MAIN PAGE: 
					-CREATE ACCOUNT:
						[redirected to patient info form]
								patient enters personal details
								and has a unique ID associated with
								their account. This ID is a combination
								of first and last name and birthday.
					-LOGIN TO EXISTING ACCOUNT:
						[redirected to login form]
								patient enters their unique ID (password?)
								and is able to view PATIENT OPTIONS.
					
				PATIENT OPTIONS:
					-SHOW RECORDS:
						[redirected to showPatientRecords()]
							patient is show their recorded information and
							the summary of their last visit
							[patient can click done to return to PATIENT OPTIONS]
					-UPDATE CONTACT INFORMATION:
						[redirected to updatePatientInfo()]
							patient is shown their current contact information
							and is allowed to update it
							[to return, patient can click update to save info
							 or cancel to return to PATIENT OPTIONS]
					-MESSAGE:
						[redirected to messageSystem]
							patient will be directed to messaging system
					-LOGOUT:
						[redirected to main login page]
 * */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

public class ASUHelloWorldJavaFX extends Application{
	
	//setting up global variables
	
	//private int userType;
	public String userName;
	HBox mainPane = new HBox();
	VBox loginBox = new VBox(8),centerBox = new VBox(20);
	
	private TextField firstNameField, lastNameField, emailField,
						phoneNumberField, patientIDField, birthdayField, 
						insuranceField, pharmacyField, heightField, 
						weightField, bodyTempField, bloodPressureField,
						allergyField,healthConcernsField, prescriptionField,
						reccomendationField, passwordField;
	
	//set up our buttons
	Button 
	existingUserLogin = new Button("Existing Patient"),
	createAccount = new Button("Create Account"),
	showRecords = new Button("View Records"),
	updateInfo = new Button("Update Contact Information"),
	message = new Button("Messages"),
	logout = new Button("Logout"),
	login = new Button("Login"),
	apply = new Button("Apply Changes"),
	cancel = new Button("Cancel");
	
	Label header = new Label("Welcome to Patient Portal");
	Label patientHeader = new Label("Welcome, "+userName);
	
	TextArea textArea1 = new TextArea(),
		     textArea2 = new TextArea();
	
	
  public static void main(String[] args) {
		launch(args);
	}
	
  
  
  
	/*
	 * NAME: start
	 * INPUT: Stage primaryStage
	 * OUTPUT: Main Scene
	 * PURPOSE: This 
	 */
  public void start(Stage primaryStage) {
		primaryStage.setTitle("Welcome to the Patient Portal");
		
		buildLogin();
		login.setOnAction(e-> userName = textArea1.getText());
		createAccount.setOnAction(e-> patientInfoForm());
		existingUserLogin.setOnAction(e-> setupUserLogin());
		
		

		  heightField = new TextField();
		  weightField = new TextField();
		  bodyTempField = new TextField();
		  bloodPressureField = new TextField();
		  allergyField = new TextField();
		  healthConcernsField = new TextField();
		  prescriptionField = new TextField();
		  reccomendationField = new TextField();
		  
		   firstNameField = new TextField();
		    lastNameField = new TextField();
		    emailField = new TextField();
		    birthdayField = new TextField();
		    phoneNumberField = new TextField();
		    insuranceField = new TextField();
		    pharmacyField = new TextField();
		    passwordField = new TextField();
		  
		  
		
		 
        primaryStage.setScene(new Scene(mainPane, 1000, 600));
        primaryStage.show();
	}
	
	
  
  
  
	// ---- MAIN PAGE OPTIONS [CREATE ACCOUNT/EXISTING LOGIN] -----//
	
	
	
	  /* { comes from selecting patient view from main page } 
	   * 
			 * NAME     : buildLogin()
			 * INPUT    : none
			 * OUTPUT   : builds the general patient options
			 * PURPOSE  : This function shows the patient an option to either
			 * 			  create an account or login to an existing account
			 */
  private void buildLogin() {
			resetLoginUI();
			setupTextAreas();
			//logout should send us back to the main UI
			//logout.setOnAction(e->buildLogin());
			
			loginBox.getChildren().clear();
			loginBox.setAlignment(Pos.CENTER);
			loginBox.getChildren().addAll(createAccount,existingUserLogin,logout);
			
			setupMainUI();
			
		}
	/*
	 * { comes from clicking create account } 
	 * NAME    : patientInfoForm()
	 * INPUT   : new patient information
	 * OUTPUT  : saves information to file with a unique file name
	 * PURPOSE : This function takes in new user information and 
	 * 			saves it to a file with their new unique patientID
	 * 		    (last name + first name + birthday). All fields must
	 * 			be entered or it will not allow the user to continue.
	 * 
	 */
  private void patientInfoForm() {
		centerBox.getChildren().clear();
	
		//set and position title
	 Label formTitle = new Label("New Patient Information");
	       formTitle.setFont(new Font("Arial", 24));
	     
	       
	 
	      
	  //May add more fields to this     
	  centerBox.getChildren().addAll(
			  createFieldBox("First Name: ", firstNameField),
			  createFieldBox("Last Name: ", lastNameField),
			  createFieldBox("Email: ", emailField),
			  createFieldBox("Date of Birth (MM/DD/YR): ", birthdayField),
			  createFieldBox("Phone Number: ", phoneNumberField),
			  createFieldBox("Insurance ID: ", insuranceField),
			  createFieldBox("Pharmacy Address: ", pharmacyField),
			  createFieldBox("Create Password:  ", passwordField));
	  
	  
	  //apply will save info to file
	  apply.setOnAction(e -> savePatientInfoToFile());
	  cancel.setOnAction(e -> buildLogin());
	  HBox applyChangesBox = new HBox(apply);
	  applyChangesBox.setAlignment(Pos.CENTER_RIGHT);
	  
	  HBox cancelChangesBox = new HBox(cancel);
	  cancelChangesBox.setAlignment(Pos.CENTER_RIGHT);

	  centerBox.getChildren().add(applyChangesBox);
	  centerBox.getChildren().add(cancelChangesBox);
	  
	 mainPane.getChildren().clear();
	 mainPane.getChildren().add(centerBox);

			  
	}
	
	/* { comes from clicking existing user login } 
	 * 
	 * NAME    : setupUserLogin()
	 * INPUT   : unique patient ID
	 * OUTPUT  : allow user access or error message
	 * PURPOSE : this function allows the patient to enter their ID
	 * 			 and will allow access to their account if the login 
	 * 			 is able to be validated. If it cannot be validated,
	 * 			 it will produce an error message saying so and allow
	 * 			 the user to try again. The user can also cancel and 
	 * 			 go back to the general patient view.
	 */
	
	//----- PATIENT LOGIN --------//
  private void setupUserLogin() {
	    resetLoginUI();
	    Label label1 = new Label("Please enter your Patient ID:");
	    // Assuming textArea1 is actually intended to be a TextField for user ID
	    TextField userIdField = new TextField(); // Use TextField for the user ID
	    Label label22 = new Label("Please enter your password:");
	 
	    PasswordField passwordField = new PasswordField();
	    
	    cancel.setOnAction(e -> buildLogin());
	    
	    loginBox.setAlignment(Pos.CENTER_LEFT);
	    loginBox.getChildren().addAll(label1, userIdField, label22, passwordField);
	    
	    HBox cancelChangesBox = new HBox(cancel);
	    cancelChangesBox.setAlignment(Pos.CENTER_LEFT);
	    login.setOnAction(e -> loginValidation(userIdField.getText(), passwordField.getText()));

	    centerBox.getChildren().addAll(loginBox, login);
	    centerBox.getChildren().addAll(cancelChangesBox, cancel);
	}

	  

	/* { validating login } 
	 * 
	 * NAME    : loginValidation()
	 * INPUT   : username and password
	 * OUTPUT  : allow user access or error message
	 * PURPOSE : this is used for the login button action. This function
	 * 			 takes a patient ID and makes sure that it is valid
	 * 
	 */
  
  private void loginValidation(String enteredUserName, String enteredPassword) {
	    try {
	        // Construct the path to the user's directory
	        String userDirectoryPath = "C:/Users/karam/eclipse-workspace/asuHelloWorldJavaFX/" + enteredUserName;

	        // Call readFromUserFile with the specific user's folder path
	        String fileContent = readFromUserFile(enteredUserName, userDirectoryPath);
	        
	        // Assuming the password line is formatted as "Password: actual_password"
	        String[] contentLines = fileContent.split(System.lineSeparator());
	        String storedPassword = "";
	        for (String line : contentLines) {
	            if (line.startsWith("Password: ")) {
	                storedPassword = line.split(": ")[1].trim();
	                break; // Stop looking once the password line is found
	            }
	        }
	        
	        if (enteredPassword.equals(storedPassword)) {
	            userName = enteredUserName; // Update the global userName to the logged-in user
	            buildPatientOptions(); // Proceed to patient options if the password matches
	        } else {
	            showErrorDialog("Invalid username or password."); // Show error if the password does not match
	        }
	    } catch (IOException e) {
	        showErrorDialog("An error occurred: " + e.getMessage());
	    }
	}

	
	//----- PATIENT LOGIN --------//
	   
	    
	    
	// ------- PATIENT OPTIONS [SHOW RECORDS/ UPDATE INFO / MESSAGE ] ------ //    
	    
	/* { comes from an account creation or from a successful existing login } 
	 * 
	 * NAME     : buildPatientOptions()
	 * INPUT    : none 
	 * OUTPUT   : builds the patients options after they login
	 * PURPOSE  : This function shows the patient all the options
	 * 			  they can enjoy inside their personal account
	 */
  private void buildPatientOptions() {
		
		resetLoginUI();
		setupTextAreas();
		
		showRecords.setOnAction(e-> showPatientRecords());
		updateInfo.setOnAction(e-> updatePatientInfo());
		logout.setOnAction(e->buildLogin());
		
		loginBox.getChildren().clear();
		loginBox.setAlignment(Pos.CENTER);
		loginBox.getChildren().addAll(showRecords,updateInfo,message,logout);
		
		setupMainUI();
		
	}
	
	/* { comes clicking show records } 
	 * 
	 * NAME     : showPatientRecords()
	 * INPUT    : none 
	 * OUTPUT   : builds the patient records page
	 * PURPOSE  : This function allows the user to see their recorded exam 
	 * 			  information and a summary of their last visit. If there is
	 * 			  previous records recorded for the patient, we will be shown an 
	 * 			  error message and be re-directed back to the patient options.
	 * 			  Patient can click cancel and go back to the patient options
	 * 			  or click View Previous visit to see their last visit. If there
	 * 			  were no previous visits, they will be shown an error message.
	 * 
	 * NOTES    : I want the list to preview the name of each visit (or date) so
	 * 			  a patient can select a visit to view and the appropriate data will 
	 * 			  be shown in the corresponding fields. Still needs functionality
	 */
  private void showPatientRecords() {
	    centerBox.getChildren().clear();

	    // Assume these fields are already defined as member variables
	    heightField = new TextField();
	    weightField = new TextField();
	    bodyTempField = new TextField();
	    bloodPressureField = new TextField();
	    allergyField = new TextField();
	    healthConcernsField = new TextField();
	    prescriptionField = new TextField();
	    TextArea doctorRecommendationsArea = new TextArea();
	    doctorRecommendationsArea.setPrefSize(200, 400);

	    // Set the editable state
	    heightField.setEditable(false);
	    weightField.setEditable(false);
	    bodyTempField.setEditable(false);
	    bloodPressureField.setEditable(false);
	    allergyField.setEditable(false);
	    healthConcernsField.setEditable(false);
	    prescriptionField.setEditable(false);
	    doctorRecommendationsArea.setEditable(false);

	    // Layout for the records section
	    VBox recordsBox = new VBox(10,
	        createFieldBox("Height:", heightField),
	        createFieldBox("Weight:", weightField),
	        createFieldBox("Body Temp:", bodyTempField),
	        createFieldBox("Blood Pressure:", bloodPressureField),
	        createFieldBox("Allergies:", allergyField),
	        createFieldBox("Prescriptions:", prescriptionField)
	    );
	    
	    // Layout for doctor recommendations
	    VBox recommendationsBox = new VBox(10,
	        new Label("Doctor recommendations:"),
	        doctorRecommendationsArea
	    );
	    
	    // Layout for the list of visits
	    ListView<String> visitsListView = new ListView<>();
	    visitsListView.getItems().addAll("Visit 1", "Visit 2", "Visit 3", "Current Visit");
	    
	    // Button setup
	    Button viewVisitButton = new Button("View Visit");
	    Button goBackButton = new Button("Go Back");
	    viewVisitButton.setOnAction(e -> {/* Handler code here */});
	    goBackButton.setOnAction(e -> buildPatientOptions());

	    // Layout for the buttons
	    HBox buttonBox = new HBox(10, viewVisitButton, goBackButton);
	    buttonBox.setAlignment(Pos.CENTER);
	    
	    // Main layout
	    HBox mainContentBox = new HBox(20,
	        recordsBox,
	        recommendationsBox,
	        visitsListView
	    );

	    // Add the button box to the bottom
	    VBox mainLayout = new VBox(20, mainContentBox, buttonBox);
	    mainLayout.setAlignment(Pos.CENTER);

	    // Set the main layout to the center of the scene or a designated area
	    centerBox.getChildren().add(mainLayout);
	    mainPane.getChildren().clear();
	    mainPane.getChildren().add(centerBox);
	 
	}

	
	/* { comes clicking update contact information } 
	 * 
	 * NAME     : updatePatientInfo()
	 * INPUT    : none 
	 * OUTPUT   : builds the patient contact information
	 * PURPOSE  : This function brings up a page showing the patient's current
	 * 			  information and allows them to update their phone number and 
	 * 			  email address. The fields must be filled out if deleted or the 
	 * 			  user will be shown an error message and not allowed to continue.
	 * 			  The user can click apply to save changes and will be sent back to
	 * 			  their options or they can click cancel and go back to their options. 
	 * 			  
	 */
  private void updatePatientInfo() {
	    centerBox.getChildren().clear();

	    Label greetingLabel = new Label("Hello " + userName); // Use the patient's name variable
	    greetingLabel.setFont(new Font("Arial", 16));

	    String fileContent = null;
	    String userDirectoryPath = "C:/Users/karam/eclipse-workspace/asuHelloWorldJavaFX/" + userName;

	    try {
	        fileContent = readFromUserFile(userName, userDirectoryPath); // userName is the unique ID entered by the user
	        // Proceed with showing the patient results using fileContent
	    } catch (IOException e) {
	        // Show error message to the user
	        showErrorDialog(e.getMessage());
	        buildLogin();
	        return; // Important: Exit the method if an error occurs
	    }

	    // Assuming contentLines array has the expected format and length
	    String[] contentLines = fileContent.split(System.lineSeparator());
	    if (contentLines.length >= 8) { // Check to ensure you have all the lines you expect
	        passwordField.setText(contentLines[0].split(": ")[1]);
	        firstNameField.setText(contentLines[1].split(": ")[1]);
	        lastNameField.setText(contentLines[2].split(": ")[1]);
	        emailField.setText(contentLines[3].split(": ")[1]);
	        birthdayField.setText(contentLines[4].split(": ")[1]);
	        phoneNumberField.setText(contentLines[5].split(": ")[1]);
	        insuranceField.setText(contentLines[6].split(": ")[1]);
	        pharmacyField.setText(contentLines[7].split(": ")[1]);
	    }

	    // Set editable states
	    passwordField.setEditable(false); 
	    firstNameField.setEditable(false);
	    lastNameField.setEditable(false);
	    birthdayField.setEditable(false);

	    // Add fields to the layout
	    centerBox.getChildren().addAll(
	        greetingLabel,
	        createFieldBox("First Name: ", firstNameField),
	        createFieldBox("Last Name: ", lastNameField),
	        createFieldBox("Email: ", emailField),
	        createFieldBox("Date of Birth (MM/DD/YR): ", birthdayField),
	        createFieldBox("Phone Number: ", phoneNumberField),
	        createFieldBox("Insurance ID: ", insuranceField),
	        createFieldBox("Pharmacy Address: ", pharmacyField),
	        //createFieldBox("Password: ", passwordField), // Consider the security implications
	        new HBox(apply), new HBox(cancel)
	    );

	    apply.setOnAction(e -> saveOrUpdatePatientInfo());
	    cancel.setOnAction(e -> buildPatientOptions());

	    mainPane.setAlignment(Pos.TOP_CENTER);
	    mainPane.getChildren().clear();
	    mainPane.getChildren().add(centerBox);
	}

	
	
	
	
	// ------ FUNCTIONALITY FUNCTIONS --------- // 
	
	/*
	 * NAME     : resetLoginUI()
	 * INPUT    : none
	 * OUTPUT   : reset center boxes
	 * PURPOSE  :
	 */ 
  private void resetLoginUI() {
	        loginBox.getChildren().clear();
	        centerBox.getChildren().clear();
	    }
	
	/*
	 * NAME     : setupTextAreas()
	 * INPUT    : none
	 * OUTPUT   : pretty text boxes
	 * PURPOSE  :
	 */
	
  private void setupTextAreas() {
	        textArea1.setPrefWidth(160);
	        textArea1.setPrefHeight(5);
	        textArea2.setPrefWidth(160);
	        textArea2.setPrefHeight(5);
	    }

	   /*
		 * NAME:
		 * INPUT:
		 * OUTPUT:
		 * PURPOSE:
		 */
  private void setupMainUI() {
	        header.setFont(new Font("Arial", 16));
	        centerBox.setAlignment(Pos.CENTER);
	        centerBox.getChildren().addAll(header, loginBox);
	        mainPane.setAlignment(Pos.CENTER);
	        mainPane.getChildren().add(centerBox);
	    }
	    
	    
	    /*
		 * NAME     : createFieldBox()
		 * INPUT    : a string and a TextField 
		 * OUTPUT   : HBox called fieldBox
		 * PURPOSE  : This function just conveniently puts a label
		 * 			  on a text field to keep them organized and easy
		 * 			  to align on the page.
		 */  
   private HBox createFieldBox(String labelText, TextField textField) {
	        Label label = new Label(labelText);
	        HBox fieldBox = new HBox(10, label, textField);
	        fieldBox.setAlignment(Pos.CENTER_LEFT);
	        textField.setMaxWidth(Double.MAX_VALUE);
	        HBox.setHgrow(textField, Priority.ALWAYS);
	        return fieldBox;
	    }
	    
	    /*
  		 * NAME     : savePatientInfoToFile())
  		 * INPUT    : patientInfoForm data
  		 * OUTPUT   : produces a save file with a unique patient ID to directory
  		 * PURPOSE  : This function takes in the information from patientInfoForm()
  		 * 			  and saves it to a text file with the correct headers. This
  		 * 			  saves the file as the patient's unique ID a combo of:
  		 * 			  (last name + first name + birthday)		  
  		 */ 
    private void savePatientInfoToFile() {
    	
        // Generate the userName based on the patient's first and last name
        if (!lastNameField.getText().trim().isEmpty() && !firstNameField.getText().trim().isEmpty()) {
            // Ensure there's enough length in the names to substring without error
            String partOfLastName = lastNameField.getText().trim().length() >= 3 ? lastNameField.getText().trim().substring(0, 3) : lastNameField.getText().trim();
            String partOfFirstName = firstNameField.getText().trim().length() >= 2 ? firstNameField.getText().trim().substring(0, 2) : firstNameField.getText().trim();
            String partOfBirthday = birthdayField.getText().trim().length() >= 2 ? birthdayField.getText().trim().substring(0,2): birthdayField.getText().trim();
            // Update userName
            userName = partOfLastName + partOfFirstName + partOfBirthday;
        } 
        
        if (!areFieldsFilledNewUser()) {
            // If any field is empty, show an error dialog and stop the operation
            showErrorDialog("All fields must be filled out before saving.");
            return; // Stop execution if validation fails
        }

        // Continue with saving the file using the updated userName
        String folderPath = "C:/Users/karam/eclipse-workspace/asuHelloWorldJavaFX/" + userName;    

        
        File patientDirectory = new File(folderPath);
        if (!patientDirectory.exists() && !patientDirectory.mkdirs()) {
            showErrorDialog("Failed to create a directory for the patient.");
            return;
        }
        
        String fileName = folderPath + "/" + userName + "_patientInfo.txt";
        
        String content = String.format(
            "Password: %s%nFirst Name: %s%nLast Name: %s%nEmail: %s%nBirthday: %s%nPhone Number: %s%nInsurance ID: %s%nPharmacy Address: %s%n",
            passwordField.getText(),
            firstNameField.getText(),
            lastNameField.getText(),
            emailField.getText(),
            birthdayField.getText(),
            phoneNumberField.getText(),
            insuranceField.getText(),
            pharmacyField.getText()
        );

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
            System.out.println("Information saved to " + fileName);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        buildLogin();
    }
    
    
    /* { used in updating contact information }
  		 * NAME     : saveOrUpdatePatientInfo()
  		 * INPUT    : updateContactInfo form
  		 * OUTPUT   : overwrites current patient data
  		 * PURPOSE  : This function is used when a patient updates their 
  		 * 			  contact information.
    */
    private void saveOrUpdatePatientInfo() {
        // Assume userName is set to the unique identifier for the patient
        if (userName == null || userName.trim().isEmpty()) {
            showErrorDialog("User name is not set. Cannot save the information.");
            return;
        }

        // Define the directory path for the patient using the userName
        String directoryPath = "C:/Users/karam/eclipse-workspace/asuHelloWorldJavaFX/" + userName;
        
        // Ensure the patient's directory exists
        File patientDirectory = new File(directoryPath);
        if (!patientDirectory.exists()) {
            showErrorDialog("Patient directory does not exist. Cannot update the information.");
            return;
        }

        // Define the file path for the patient's info file
        String filePath = directoryPath + "/" + userName + "_patientInfo.txt";
        
        // Content to be written (updated with current field values)
        String content = String.format(
            "Password: %s%nFirst Name: %s%nLast Name: %s%nEmail: %s%nBirthday: %s%nPhone Number: %s%nInsurance ID: %s%nPharmacy Address: %s%n",
            passwordField.getText(),
            firstNameField.getText(),
            lastNameField.getText(),
            emailField.getText(),
            birthdayField.getText(),
            phoneNumberField.getText(),
            insuranceField.getText(),
            pharmacyField.getText()
        );

        // Write (or overwrite) the content to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
            System.out.println("Information saved or updated in " + filePath);
        } catch (IOException ex) {
            ex.printStackTrace();
            showErrorDialog("Failed to update information: " + ex.getMessage());
        }

        buildPatientOptions();
    }    
	    /*
		 * NAME     : readFromUserFile()
		 * INPUT    : a Patient ID and a directory path
		 * OUTPUT   : reads from a particular user file
		 * PURPOSE  : This function reads information from a particular file name. 
		 * 			  The file name must match the uniqueID to be read. For example,
		 * 			  a user file may be named melKar95_patientFile.txt so the uniqueId
		 * 			  must be melKar95 to access it. If there is no matching ID, it will
		 * 			  throw an exception saying so.
		 */ 
	    
    private String readFromUserFile(String uniqueId, String directoryPath) throws IOException {
	        File dir = new File(directoryPath);
	        String content = "";

	        // Filter the list of files in the directory based on the uniqueId
	        File[] matchingFiles = dir.listFiles((dir1, name) -> name.startsWith(uniqueId) && name.endsWith(".txt"));

	        if (matchingFiles == null || matchingFiles.length == 0) {
	            // Throw an IOException if no matching file is found
	            throw new IOException("No file found starting with the unique ID: " + uniqueId);
	        } else {
	            // Assuming there's only one match or you're only interested in the first match
	            content = readFromFile(matchingFiles[0].getPath());
	        }

	        return content;
	    }
	    
	    /*
		 * NAME     : readFromFile()
		 * INPUT    : a file path 
		 * OUTPUT   : reads from a text file from the given directory
		 * PURPOSE  : This function gives us the functionality to read from a text file
		 * 			  from the given file path		  
		 */  
	 private String readFromFile(String filePath) {
	        StringBuilder contentBuilder = new StringBuilder();
	        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                contentBuilder.append(line).append(System.lineSeparator());
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return contentBuilder.toString();
	    }
	        
	    /*
		 * NAME     : showErrorDialog()
		 * INPUT    : an error message
		 * OUTPUT   : produces an error alert for the user
		 * PURPOSE  : This function takes in an error message and displays it 
		 * 			  when the condition for an error occurs		  
		 */ 
	  private void showErrorDialog(String message) {
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }
	    
	    /*
		 * NAME     : areFieldsFilledNewUser()
		 * INPUT    : none
		 * OUTPUT   : returns true if all fields are filled, false if not
		 * PURPOSE  : This function just checks and makes sure all text fields in
		 * 			  the new patient information form are filled. 		  
		 */
	 private boolean areFieldsFilledNewUser() {
		    // Check each field for empty values
		    if (firstNameField.getText().trim().isEmpty() ||
		    	lastNameField.getText().trim().isEmpty() ||
		    	emailField.getText().trim().isEmpty() ||
		    	birthdayField.getText().trim().isEmpty() ||
		    	phoneNumberField.getText().trim().isEmpty() ||
		    	insuranceField.getText().trim().isEmpty() ||
		    	pharmacyField.getText().trim().isEmpty()||
		    	passwordField.getText().trim().isEmpty()) {
		        
		        // If any field is empty, return false
		        return false;
		    }
		    // If all fields are filled, return true
		    return true;
		}
}
