package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

public class patientUser extends CSE360_Main{
	//setting up global variables
		

	
	public String userName; //holds unique user ID for current user
	HBox mainPane1 = new HBox();
	VBox loginBox1 = new VBox(8);
	
	private  TextArea doctorRecommendationsArea;	
	private TextField firstNameField, lastNameField, emailField,
						phoneNumberField, birthdayField, 
						insuranceField, pharmacyField, heightField, 
						weightField, bodyTempField, bloodPressureField,
						allergyField,healthConcernsField, prescriptionField,
						passwordField, examDate;
	VBox centerBox1 = new VBox(20);
	
Button
	showRecords = new Button("View Records"),
	updateInfo = new Button("Update Contact Information"),
	message = new Button("Messages"),
	logout = new Button("Logout"),
	login = new Button("Login"),
	apply = new Button("Apply Changes"),
	cancel = new Button("Cancel");
	
	Label header = new Label("Welcome to Patient Portal");
	Label patientHeader = new Label("Welcome, "+userName);
	

	  public patientUser(Stage primaryStage) {
	       super(); 
	       this.primaryStage = primaryStage;
	       
	       buildLoginPatient();
	    }

    //---- MAIN PAGE OPTIONS [CREATE ACCOUNT/EXISTING LOGIN] -----//

/* NAME     : buildLogin()
*  PURPOSE  : This function shows the patient an option to either
* 			  create an account or login to an existing account
* 
* 	{ comes from selecting patient view from main page }*/
private void buildLoginPatient() {
	
	 if (this.primaryStage == null) {
         throw new IllegalStateException("Primary stage has not been initialized.");
     }

	 primaryStage.setWidth(800);
	 primaryStage.setHeight(600); 
	Label welcomeLabel = new Label("Welcome to the Patient Portal");
	
	Button  //set up our buttons
		existingUserLogin = new Button("Existing Patient"),
		createAccount = new Button("Create Account");
 	

 	
 	 examDate = new TextField();
 	 heightField = new TextField();
 	 weightField = new TextField();
 	 bodyTempField = new TextField();
 	 bloodPressureField = new TextField();
 	 allergyField = new TextField();
 	 healthConcernsField = new TextField();
 	 prescriptionField = new TextField();
 	 doctorRecommendationsArea = new TextArea();
 	  
 	 firstNameField = new TextField();
 	 lastNameField = new TextField();
 	 emailField = new TextField();
 	 birthdayField = new TextField();
 	 phoneNumberField = new TextField();
 	 insuranceField = new TextField();
 	 pharmacyField = new TextField();
 	 passwordField = new TextField();
	
	
 
 	centerBox1.getChildren().clear();
 	centerBox1.setAlignment(Pos.CENTER);
 	centerBox1.getChildren().addAll(welcomeLabel,createAccount,existingUserLogin,logout);
 	
 	
  if (primaryStage.getScene() == null) { 
	        Scene scene = new Scene(centerBox1, 500, 250);
	        primaryStage.setScene(scene);
	    } else { 
	        primaryStage.getScene().setRoot(centerBox1);
	    }

	logout.setOnAction(e -> {
		
		primaryStage.setScene(new Scene(mainPane,500,250));
		primaryStage.show();
		buildLogin();
		
	});
	
	createAccount.setOnAction(e-> patientInfoForm());
 	existingUserLogin.setOnAction(e-> setupUserLogin());
	primaryStage.show();	
}

/* NAME    : patientInfoForm()
 * PURPOSE : This function takes in new user information and 
 * 			saves it to a file with their new unique patientID
 * 		    (last name + first name + birthday). All fields must
 * 			be entered or it will not allow the user to continue.
 * 			
 * 				{ comes from clicking create account }*/
private void patientInfoForm() {
    centerBox1.getChildren().clear();
    clearPatientInfoFields();
    centerBox1.setPadding(new Insets(20));

    // Title
    Label formTitle = new Label("New Patient Information");
    formTitle.setFont(new Font("Arial", 24));
    formTitle.setAlignment(Pos.CENTER); // Align the title
    formTitle.setMaxWidth(Double.MAX_VALUE); // Ensure it spans the container width

    // Adding the title at the top
    centerBox1.getChildren().add(formTitle);

    // Fields
    centerBox1.getChildren().addAll(
        createFieldBox("First Name: ", firstNameField),
        createFieldBox("Last Name: ", lastNameField),
        createFieldBox("Email: ", emailField),
        createFieldBox("Date of Birth (MM/DD/YR): ", birthdayField),
        createFieldBox("Phone Number: ", phoneNumberField),
        createFieldBox("Insurance ID: ", insuranceField),
        createFieldBox("Pharmacy Address: ", pharmacyField),
        createFieldBox("Create Password:  ", passwordField));

    // Button Box
    HBox buttonBox = new HBox(10); 
    buttonBox.setAlignment(Pos.CENTER); 

    apply.setOnAction(e -> savePatientInfoToFile());
    cancel.setOnAction(e -> buildLoginPatient());

    buttonBox.getChildren().addAll(apply, cancel);
    centerBox1.getChildren().add(buttonBox);
    centerBox1.setAlignment(Pos.CENTER); 
}

/* NAME    : setupUserLogin()
 * PURPOSE : this function allows the patient to enter their ID
 * 			 and will allow access to their account if the login 
 * 			 is able to be validated. If it cannot be validated,
 * 			 it will produce an error message saying so and allow
 * 			 the user to try again. The user can also cancel and 
 * 			 go back to the general patient view. 
 * 			{ comes from clicking existing user login }*/
private void setupUserLogin() {
    centerBox1.getChildren().clear();
    centerBox1.setPadding(new Insets(20));
    centerBox1.setAlignment(Pos.CENTER);

    Label label1 = new Label("Please enter your Patient ID:");
    TextField userIdField = new TextField();
    HBox userIdFieldBox = new HBox(userIdField);
    userIdFieldBox.setAlignment(Pos.CENTER);
    userIdField.setPrefWidth(100);
    userIdField.setMaxWidth(100); 

    Label label22 = new Label("Please enter your password:");
    PasswordField passwordField = new PasswordField();
    HBox passwordFieldBox = new HBox(passwordField);
    passwordFieldBox.setAlignment(Pos.CENTER);
    passwordField.setPrefWidth(100);
    passwordField.setMaxWidth(100);

  
    Button cancel = new Button("Cancel");
    cancel.setOnAction(e -> buildLoginPatient());
    Button login = new Button("Login");
    login.setOnAction(e -> loginValidation(userIdField.getText(), passwordField.getText()));
    HBox buttonBox = new HBox(10, login, cancel);
    buttonBox.setAlignment(Pos.CENTER);

    centerBox1.getChildren().addAll(label1, userIdFieldBox, label22, passwordFieldBox, buttonBox);
}


	//------- PATIENT LOGGED IN [SHOW RECORDS/ UPDATE INFO / MESSAGE ] ------ //    

/* NAME     : buildPatientOptions()
 * PURPOSE  : This function shows the patient all the options
 * 			  they can enjoy inside their personal account
 * 	{ comes from an account creation or from a successful existing login }*/
private void buildPatientOptions() {
	centerBox1.getChildren().clear();	
	showRecords.setOnAction(e-> showPatientRecords());
	updateInfo.setOnAction(e-> updatePatientInfo());
	message.SetOnAction(e -> viewMessages());
	logout.setOnAction(e->buildLoginPatient());
	
	centerBox1.getChildren().clear();
	centerBox1.setAlignment(Pos.CENTER);
	centerBox1.getChildren().addAll(showRecords,updateInfo,message,logout);
	
	
}

/* NAME     : showPatientRecords()
 * PURPOSE  : This function allows the user to see their recorded exam 
 * 			  information and a summary of their last visit. If there is
 * 			  previous records recorded for the patient, we will be shown an 
 * 			  error message and be re-directed back to the patient options.
 * 			  Patient can click cancel and go back to the patient options
 * 			  or click View Previous visit to see their last visit. If there
 * 			  were no previous visits, they will be shown an error message.
 *				{ comes clicking show records } */
private void showPatientRecords() {
    centerBox1.getChildren().clear();

    String recordPath = "Patients" + File.separator + userName + File.separator + "PatientRecords";


    doctorRecommendationsArea.setPrefSize(200, 400);

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
        createFieldBox("Health Concerns:", healthConcernsField),
        createFieldBox("Prescriptions:", prescriptionField)
    );

    // Layout for doctor recommendations
    VBox recommendationsBox = new VBox(10,
        new Label("Doctor recommendations:"),
        doctorRecommendationsArea
    );

    Label headerLabel = new Label("Previous Visits: yy.mm.dd");

    // ListView for displaying visits
    ListView<String> visitsListView = new ListView<>();
    // Read the directory and list available records
    File recordsDir = new File(recordPath);
    File[] recordsFiles = recordsDir.listFiles();
    if (recordsFiles != null) {
        for (File file : recordsFiles) {
            String fileName = file.getName();
            visitsListView.getItems().add(fileName.substring(0, fileName.lastIndexOf('.')));
        }
    }
    
    VBox visitsListWithHeader = new VBox(5); 
    visitsListWithHeader.getChildren().addAll(headerLabel, visitsListView);

    Button viewVisitButton = new Button("View Visit");
    Button goBackButton = new Button("Go Back");

    viewVisitButton.setOnAction(e -> {
        String selectedRecordName = visitsListView.getSelectionModel().getSelectedItem();
        if (selectedRecordName != null && !selectedRecordName.isEmpty()) {
            String fullPath = recordPath + "/" + selectedRecordName + ".txt";
            try {
                String fileContent = new String(Files.readAllBytes(Paths.get(fullPath)));
                updateUIWithRecord(fileContent);
            } catch (IOException ex) {
                showErrorDialog("Failed to read record: " + ex.getMessage());
            }
        }
    });

    goBackButton.setOnAction(e -> buildPatientOptions());

    HBox buttonBox = new HBox(10, viewVisitButton, goBackButton);
    buttonBox.setAlignment(Pos.CENTER);

    HBox mainContentBox = new HBox(20,
        recordsBox,
        recommendationsBox,
        visitsListWithHeader 
    );

    VBox mainLayout = new VBox(20, mainContentBox, buttonBox);
    mainLayout.setAlignment(Pos.CENTER);

    centerBox1.getChildren().add(mainLayout);

}

 /* NAME     : updatePatientInfo()
 * PURPOSE  : This function brings up a page showing the patient's current
 * 			  information and allows them to update their phone number and 
 * 			  email address. The fields must be filled out if deleted or the 
 * 			  user will be shown an error message and not allowed to continue.
 * 			  The user can click apply to save changes and will be sent back to
 * 			  their options or they can click cancel and go back to their options. 
 * 						{ comes clicking update contact information }*/
private void updatePatientInfo() {
    centerBox1.getChildren().clear();


    Label greetingLabel = new Label("Hello " + userName); 
    greetingLabel.setFont(new Font("Arial", 16));

    String fileContent = null;
    String userDirectoryPath = "Patients" + File.separator + userName;


    try {
        fileContent = readFromUserFile(userName, userDirectoryPath); 
    } catch (IOException e) {
        showErrorDialog(e.getMessage());
        buildLoginPatient();
        return; 
    }

    String[] contentLines = fileContent.split(System.lineSeparator());
    if (contentLines.length >= 8) { 
        passwordField.setText(contentLines[0].split(": ")[1]);
        firstNameField.setText(contentLines[1].split(": ")[1]);
        lastNameField.setText(contentLines[2].split(": ")[1]);
        emailField.setText(contentLines[3].split(": ")[1]);
        birthdayField.setText(contentLines[4].split(": ")[1]);
        phoneNumberField.setText(contentLines[5].split(": ")[1]);
        insuranceField.setText(contentLines[6].split(": ")[1]);
        pharmacyField.setText(contentLines[7].split(": ")[1]);
    }

    passwordField.setEditable(false); 
    firstNameField.setEditable(false);
    lastNameField.setEditable(false);
    birthdayField.setEditable(false);

    centerBox1.getChildren().addAll(
        greetingLabel,
        createFieldBox("First Name: ", firstNameField),
        createFieldBox("Last Name: ", lastNameField),
        createFieldBox("Email: ", emailField),
        createFieldBox("Date of Birth (MM/DD/YR): ", birthdayField),
        createFieldBox("Phone Number: ", phoneNumberField),
        createFieldBox("Insurance ID: ", insuranceField),
        createFieldBox("Pharmacy Address: ", pharmacyField),
        new HBox(apply), new HBox(cancel)
    );

    apply.setOnAction(e -> updateUserInfo());
    cancel.setOnAction(e -> buildPatientOptions());

    
}

// --------------- FUNCTIONALITY FUNCTIONS -----------------------//

/* NAME    : loginValidation()
 * INPUT   : username and password
 * OUTPUT  : allow user access or error message
 * PURPOSE : this is used for the login button action. This function
 * 			 takes a patient ID and makes sure that it is valid 
 * 			{ validating login }*/
private void loginValidation(String enteredUserName, String enteredPassword) {
    try {
        // try to construct the path to the user's directory
    	String userDirectoryPath = "Patients" + File.separator + enteredUserName;


        String fileContent = readFromUserFile(enteredUserName, userDirectoryPath);
        
        // password line is formatted as "Password: actual_password" 
        String[] contentLines = fileContent.split(System.lineSeparator());
        String storedPassword = "";
        for (String line : contentLines) {
            if (line.startsWith("Password: ")) {
                storedPassword = line.split(": ")[1].trim();
                break; 
            }
        }
        
        if (enteredPassword.equals(storedPassword)) {
            userName = enteredUserName; 
            buildPatientOptions(); 
        } else {
            showErrorDialog("Invalid username or password.");
        }
    } catch (IOException e) {
        showErrorDialog("An error occurred: " + e.getMessage());
    }
}

/* NAME    : updateUIWithRecords()
 * PURPOSE : This function is used with showPatientRecords(). It parses the 
 * 			 text file which stores the patient's exam data and then displays
 * 			 it in the appropriate fields.
 * 			{ updates which record to view } */
private void updateUIWithRecord(String fileContent) {
    String[] contentLines = fileContent.split(System.lineSeparator());
    
    // create variables to hold the parsed data
    String date = "", height = "", weight = "", bodyTemp = "", bloodPressure = "", allergies = "", healthConcerns = "", prescriptions = "";
    StringBuilder doctorRecs = new StringBuilder();
    boolean recsStarted = false; // Flag to track when doctor recommendations start
    
    for (String line : contentLines) {
        if (recsStarted) {
            // Append the line to doctor recommendations, including line breaks for readability
            doctorRecs.append(line).append(System.lineSeparator());
            continue;
        }

        // Splitting each line by the first occurrence of ": "
        // *** if we want the data header in the file to change
        // 	   we must update it here in this case statement for correct parsing
        String[] parts = line.split(": ", 2);
        if (parts.length == 2) {
            switch (parts[0]) {
                case "Date":
                    date = parts[1];
                    break;
                case "Height":
                    height = parts[1];
                    break;
                case "Weight":
                    weight = parts[1];
                    break;
                case "Body Temp":
                    bodyTemp = parts[1];
                    break;
                case "Blood Pressure":
                    bloodPressure = parts[1];
                    break;
                case "Allergies":
                    allergies = parts[1];
                    break;
                case "HealthConcerns":
                    healthConcerns = parts[1];
                    break;
                case "Prescriptions":
                    prescriptions = parts[1];
                    break;
                case "Doctor recommendations": // Marks the beginning of doctor recommendations
                    recsStarted = true;
                    doctorRecs.append(parts[1]).append(System.lineSeparator());
                    break;
            }
        }
    }

    // Update UI with the parsed data
    examDate.setText(date);
    heightField.setText(height);
    weightField.setText(weight);
    bodyTempField.setText(bodyTemp);
    bloodPressureField.setText(bloodPressure);
    allergyField.setText(allergies);
    healthConcernsField.setText(healthConcerns);
    prescriptionField.setText(prescriptions);
    doctorRecommendationsArea.setText(doctorRecs.toString()); 
}

/* NAME     : savePatientInfoToFile()
 * PURPOSE  : This function makes sure all fields were filled out
 * 			  and creates a new directory for each patient */ 
private void savePatientInfoToFile() {
    userName = generateUserName();

    if (!areFieldsFilledNewUser()) {
        showErrorDialog("All fields must be filled out before saving.");
        return;
    }

    String patientDirectoryPath = "Patients" + File.separator + userName;
    createPatientDirectories(patientDirectoryPath);

    String patientInfoFilePath = patientDirectoryPath + File.separator + userName + "_patientInfo.txt";
    savePatientDataToFile(patientInfoFilePath);

    buildLoginPatient();
}

/* NAME     : savePatientDataToFile()
 * INPUT    : file path to save to 
 * PURPOSE  : This function writes the given information
 * 			  to a text file in the appropriate format */
private void savePatientDataToFile(String filePath) {
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

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
        writer.write(content);
        System.out.println("Information saved to " + filePath);
    } catch (IOException ex) {
        showErrorDialog("Failed to save patient information: " + ex.getMessage());
    }
}

/* NAME     : updateUserInfo()
 * PURPOSE  : This function is used when a patient updates their 
 * 			  contact information.
 *  { used in updating contact information }*/
private void updateUserInfo() {
if (userName == null || userName.trim().isEmpty()) {
  showErrorDialog("User name is not set. Cannot save the information.");
  return;
}

String directoryPath = "Patients" + File.separator + userName;

//check if directory exists
File patientDirectory = new File(directoryPath);
if (!patientDirectory.exists()) {
  showErrorDialog("Patient directory does not exist. Cannot update the information.");
  return;
}

// file path for the patient's info file
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

// Write (or overwrite) the content
try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
  writer.write(content);
  System.out.println("Information saved or updated in " + filePath);
} catch (IOException ex) {
  ex.printStackTrace();
  showErrorDialog("Failed to update information: " + ex.getMessage());
}

buildPatientOptions();
}  

/* NAME     : readFromUserFile()
 * INPUT    : a Patient ID and a directory path
 * PURPOSE  : This function reads information from a particular file name. 
 * 			  The file name must match the uniqueID to be read. For example,
 * 			  a user file may be named melKar95_patientFile.txt so the uniqueId
 * 			  must be melKar95 to access it. If there is no matching ID, it will
 * 			  throw an exception saying so. */ 

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

/* NAME     : readFromFile()
 * INPUT    : a file path 
 * OUTPUT   : a String of file content
 * PURPOSE  : This function gives us the functionality to read from a text file
 * 			  from the given file path	*/  
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
    
/* NAME     : showErrorDialog()
 * INPUT    : an error message String
 * PURPOSE  : This function takes in an error message and displays it 
 * 			  when the condition for an error occurs	*/ 
private void showErrorDialog(String message) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}

/* NAME     : areFieldsFilledNewUser()
 * OUTPUT   : returns true if all fields are filled, false if not
 * PURPOSE  : This function just checks and makes sure all text fields in
 * 			  the new patient information form are filled. 		  */
private boolean areFieldsFilledNewUser() {
    if (firstNameField.getText().trim().isEmpty() ||
    	lastNameField.getText().trim().isEmpty() ||
    	emailField.getText().trim().isEmpty() ||
    	birthdayField.getText().trim().isEmpty() ||
    	phoneNumberField.getText().trim().isEmpty() ||
    	insuranceField.getText().trim().isEmpty() ||
    	pharmacyField.getText().trim().isEmpty()||
    	passwordField.getText().trim().isEmpty()) {
        
        return false;
    }
    return true;
}

 
/* NAME     : createFieldBox()
 * INPUT    : a string and a TextField 
 * OUTPUT   : HBox called fieldBox
 * PURPOSE  : This function just conveniently puts a label
 * 			  on a text field to keep them organized and easy
 * 			  to align on the page. */  
private HBox createFieldBox(String labelText, TextField textField) {
    Label label = new Label(labelText);
    HBox fieldBox = new HBox(10, label, textField);
    fieldBox.setAlignment(Pos.CENTER_LEFT);
    //textField.setMaxWidth(Double.MAX_VALUE);
    textField.setPrefWidth(200);
    //HBox.setHgrow(textField, Priority.ALWAYS);

    return fieldBox;
 } 

/* NAME     : generateUserName()
 * OUTPUT   : uniqueID or null
 * PURPOSE  : This generates a unique user ID */  
private String generateUserName() {
if (!lastNameField.getText().trim().isEmpty() && !firstNameField.getText().trim().isEmpty()) {
	  String partOfLastName = lastNameField.getText().trim().length() >= 3 ? lastNameField.getText().trim().substring(0, 3) : lastNameField.getText().trim();
	  String partOfFirstName = firstNameField.getText().trim().length() >= 2 ? firstNameField.getText().trim().substring(0, 2) : firstNameField.getText().trim();
	  String partOfBirthday = birthdayField.getText().trim().length() >= 2 ? birthdayField.getText().trim().substring(0,2): birthdayField.getText().trim();

	  return userName = partOfLastName + partOfFirstName + partOfBirthday;
	} 
return null;
}

/* NAME     : createPatientDirectories()
 * INPUT    : base path for the main folder
 * PURPOSE  : This function builds the organization of files for Patient accounts */  
private void createPatientDirectories(String basePath) {
    File patientDirectory = new File(basePath);
    if (!patientDirectory.exists() && !patientDirectory.mkdirs()) {
        showErrorDialog("Failed to create directory for patient.");
        return;
    }
    
    new File(basePath + File.separator + "SentMessages").mkdirs();
    new File(basePath + File.separator + "ReceivedMessages").mkdirs();
    new File(basePath + File.separator + "PatientRecords").mkdirs();
}


/* NAME    : clearPatientInfoFields()
 * PURPOSE : resets text fields for new users */
private void clearPatientInfoFields() {
    firstNameField.setText("");
    lastNameField.setText("");
    emailField.setText("");
    birthdayField.setText("");
    phoneNumberField.setText("");
    insuranceField.setText("");
    pharmacyField.setText("");
    passwordField.setText("");

    firstNameField.setEditable(true);
    lastNameField.setEditable(true);
    emailField.setEditable(true);
    birthdayField.setEditable(true);
    phoneNumberField.setEditable(true);
    insuranceField.setEditable(true);
    pharmacyField.setEditable(true);
    passwordField.setEditable(true);

 }


	private void viewMessages() {
		Button sendMessage = new Button("Send New Message"), home = new Button("Return Home"), viewMessage = new Button("View Message");
		TextArea sender = new TextArea(), re = new TextArea(), message = new TextArea(); 
        ListView<String> messagesListView = new ListView<>(); // ListView for displaying visits
        String userDirectoryPath; 
        Label recieved = new Label("Recieved Messages:"), from = new Label("From:"), regarding = new Label("RE:");
        VBox messageList = new VBox(), textList = new VBox(), leftPane = new VBox(); 
        HBox buttonList = new HBox(20), mainPane = new HBox(30);
        
        centerBox1.getChildren().clear();
        
        sender.setEditable(false);
        re.setEditable(false);
        message.setEditable(false);
        sender.setPrefSize(200, 10);
        re.setPrefSize(200, 10);
        message.setPrefSize(200, 300);
        // Read the directory and list available records
        userDirectoryPath = "Patients" + File.separator + userName + File.separator + "RecievedMessages" + File.separator;
        
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

        centerBox1.getChildren().add(mainPane);
        
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
                
                viewMessages();
            }
        });
        
        home.setOnAction(e -> buildPatientOptions());
        sendMessage.setOnAction(e -> sendMessages());
	}

	private void sendMessages() {
		Label messageLabel = new Label("Outgoing Messages"), sendTo = new Label("Send To:");
		ListView<String> outbox = new ListView<>();	
		TextField SubjectField = new TextField();
		TextArea messageArea = new TextArea();
		CheckBox nurseBox = new CheckBox("Nurse"), doctorBox = new CheckBox("Doctor");
		Button sendButton = new Button("Send"), home = new Button("Return Home"), viewMessage = new Button("View Message");
		VBox messageList = new VBox(), textList = new VBox(), leftPane = new VBox(); 
		HBox buttonList = new HBox(20), mainPane = new HBox(30), recipientCheck = new HBox(10);
		String userDirectoryPath;
		
		centerBox1.getChildren().clear();
		
		SubjectField.setPromptText("Re: ");
		messageArea.setPromptText("Write your message here");
        SubjectField.setPrefSize(200, 10);
        messageArea.setPrefSize(200, 300);
		
        userDirectoryPath = "Patients" + File.separator + userName + File.separator + "SentMessages" + File.separator;
        
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
        
        recipientCheck.setAlignment(Pos.CENTER);
        recipientCheck.getChildren().addAll(nurseBox, doctorBox);
        
        textList.setAlignment(Pos.CENTER_LEFT);
        textList.getChildren().addAll(sendTo, recipientCheck, SubjectField, messageArea);
        
        leftPane.setAlignment(Pos.CENTER);
        leftPane.getChildren().addAll(textList, buttonList);
        
        messageList.setAlignment(Pos.CENTER);
        messageList.getChildren().addAll(messageLabel, outbox);
        
        mainPane.setAlignment(Pos.CENTER);
        mainPane.getChildren().addAll(leftPane, messageList);
		
		
        centerBox1.getChildren().add(mainPane);
		
		sendButton.setOnAction(event-> {	
		//to make sure all the text boxes are filled
			String subject = SubjectField.getText().trim();
			String message = messageArea.getText().trim();
			String directory, directory2;
			Boolean flag = false;
			int msgNumber = 0; 
			
			if(!nurseBox.isSelected() && !doctorBox.isSelected()) {
				Alert recipientEmpty = new Alert(Alert.AlertType.ERROR, "Choose who to send to!");
				recipientEmpty.showAndWait();
			} else if(subject.isEmpty()) {
				Alert subjectEmpty = new Alert(Alert.AlertType.ERROR, "Subject is empty!");
				subjectEmpty.showAndWait();
			} else if(message.isEmpty()) {
				Alert messageEmpty = new Alert(Alert.AlertType.ERROR, "Message field is empty!");
				messageEmpty.showAndWait();
			} else {
				//logic for send button goes here.
				File nurseDirectory, doctorDirectory;
				String messageInfo = userName + "\n" + 
									 subject + "\n" +
									 message; 
				
				if(nurseBox.isSelected()) {
					msgNumber = 0; 
					
					while(!flag) {
						directory = "Nurse" + File.separator + "RecievedMessages" + File.separator +
						    	 	"Nurse" + "msg" + String.format("%03d", msgNumber) + ".txt";							
						directory2 = "Patients" + File.separator + userName + File.separator + "SentMessages" + File.separator +
							    	 "Nurse" + "msg" + String.format("%03d", msgNumber) + ".txt";
    					nurseDirectory = new File(directory);
    					
    					if(!nurseDirectory.exists()) {
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
				
				if(doctorBox.isSelected()) {
					msgNumber = 0; 
					
					while(!flag) {
						directory = "Doctor" + File.separator + "RecievedMessages" + File.separator +
						    	 	"Doctor" + "msg" + String.format("%03d", msgNumber) + ".txt";							
						directory2 = "Patients" + File.separator + userName + File.separator + "SentMessages" + File.separator +
							    	 "Doctor" + "msg" + String.format("%03d", msgNumber) + ".txt";
    					doctorDirectory = new File(directory);
    					
    					if(!doctorDirectory.exists()) {
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
				sendMessages(); 
			}		
		});
		
		viewMessage.setOnAction(e -> {
            String selectedRecordName = outbox.getSelectionModel().getSelectedItem();
            String messageContents = "", line; 
            
            if (selectedRecordName != null && !selectedRecordName.isEmpty()) {
                String fullPath = userDirectoryPath + File.separator + selectedRecordName + ".txt";
                
                try(BufferedReader reader = new BufferedReader(new FileReader(fullPath))) {
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
		home.setOnAction(event -> buildPatientOptions());
	}
}
