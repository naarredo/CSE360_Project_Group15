import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class NurseView extends CSE360_Main {
	
	private BorderPane nursePane = new BorderPane();
	private BorderPane recordsPane = new BorderPane();
	private BorderPane vitalsPane = new BorderPane();
	private BorderPane preVitalPane = new BorderPane();
	private BorderPane additionalPane = new BorderPane();
	private BorderPane preRecordsPane = new BorderPane();
	
	private Scene nurseMain = new Scene(nursePane,500,250);
	private Scene preVital = new Scene(preVitalPane,500,250);
	private Scene records = new Scene(recordsPane,600,275);
	private Scene vitals = new Scene(vitalsPane,500,250);
	private Scene additionalInfo = new Scene(additionalPane,500,250);
	private Scene preRecords = new Scene(preRecordsPane,500,250);

	private String firstName;
	private String lastName;
	private String dateOfB;
	private String weight;
	private String height;
	private String bodyTemp;
	private String bPressure;
	private Boolean over12;
	private String knownAllergies;
	private String healthConcerns;
	private String prescribedMed;
	private String immunizationRecord;
	private String patientID;
	private String date;
	
	/*
	 * constructor for main class to access nurseView
	 */
	public NurseView(Stage primaryStage) {
		super();
		this.primaryStage = primaryStage;
		nurseMainPage();
	}
	
	/*
	 *Main nurse page after login, contains all options: enter vitals, view records, messaging, logout 
	 */
	private void nurseMainPage() {
		
		primaryStage.setScene(nurseMain);
		vitalsPrePage();
		vitalsPage();
		additionalPage();
		
		try {
			recordsPrePage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
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
		
		//sizing buttons to be of equal size
		logoutButton.setPrefSize(70, 20);
		messagePortal.setPrefSize(70, 20);
		viewRecords.setPrefSize(90,20);
		enterVitals.setPrefSize(90,20);
		
		
		headLabels.getChildren().addAll(welcome,optionSelect); 
		msgLogHolder.getChildren().addAll(messagePortal,logoutButton); 
		recordsVitalsHolder.getChildren().addAll(viewRecords,enterVitals); 
		leftHolder.getChildren().addAll(headLabels,recordsVitalsHolder); //adding head labels and button VBoxs to a different VBox holder
		msgLogHolder.setSpacing(5);
		headLabels.setSpacing(10);
		recordsVitalsHolder.setSpacing(20);
		leftHolder.setSpacing(30);
		headLabels.setAlignment(Pos.CENTER);
		recordsVitalsHolder.setAlignment(Pos.CENTER);
		msgLogHolder.setAlignment(Pos.TOP_RIGHT);
		
		mainHolder.setAlignment(Pos.CENTER);
		mainHolder.setSpacing(25);
		mainHolder.getChildren().addAll(leftHolder,msgLogHolder); //adding previous label and button holder, message and logout holder to main VBox
		mainHolder.setPadding(new Insets(10,0,10,130));
		msgLogHolder.setPadding(new Insets(0,0,0,50));
		
		nursePane.setCenter(mainHolder);
		
		viewRecords.setOnAction(e -> {
			primaryStage.setScene(preRecords);
		});
		
		enterVitals.setOnAction(e -> primaryStage.setScene(preVital));
		logoutButton.setOnAction(e -> {
			buildLogin();
			primaryStage.setScene(new Scene(mainPane,500,250));
		});
        
	}

	private void vitalsPrePage() {
		
		Label patInfo = new Label("Patient Information:"), fName = new Label("First Name:"), lName = new Label("Last Name:"), dob = new Label("Date of Birth:"), visitDate = new Label("Date of Visit:");
		Label errorMsg = new Label("Empty/Invalid Inputs");
		
		HBox infoHoldBox = new HBox(), contButtonHold = new HBox();
		
		VBox contBox = new VBox(), infoLabelBox = new VBox(),infoTextFieldBox = new VBox(); 

		Button cont = new Button("Continue");
		Button menu = new Button("Menu");
		
		TextField nameField = new TextField(), nameField2 = new TextField(), birthField  = new TextField(),dateField = new TextField();
		
		errorMsg.setTextFill(Color.RED);
		cont.setPrefSize(70, 20);
		menu.setPrefSize(70, 20);
		infoLabelBox.getChildren().addAll(fName,lName,dob,visitDate); //VBox containing labels
		infoTextFieldBox.getChildren().addAll(nameField,nameField2,birthField,dateField); //VBox containing textfields
		infoHoldBox.getChildren().addAll(infoLabelBox,infoTextFieldBox); //HBOx containing previous VBox
		contButtonHold.getChildren().addAll(menu,cont);
		contButtonHold.setSpacing(15);
		contBox.getChildren().addAll(patInfo,infoHoldBox,contButtonHold); //Main VBox containing all previous created Boxes
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
			date = dateField.getText();
			if (firstName.isBlank() || lastName.isBlank() || dateOfB.isBlank() || date.isBlank()) { //checking if inputs are empty
				if(!contButtonHold.getChildren().contains(errorMsg)) //check if error message is already being displayed
					contButtonHold.getChildren().add(errorMsg);
			}
			else  {
				if(contButtonHold.getChildren().contains(errorMsg))
					contButtonHold.getChildren().remove(errorMsg);
				primaryStage.setScene(vitals);
			}
			nameField.clear();
			nameField2.clear();
			birthField.clear();
			dateField.clear();
		});
		
		menu.setOnAction(e -> {
			nameField.clear();
			nameField2.clear();
			birthField.clear();
			dateField.clear();
			if(contButtonHold.getChildren().contains(errorMsg)) //clears error message
				contButtonHold.getChildren().remove(errorMsg);
			primaryStage.setScene(nurseMain);
		});
	}
	
	private void vitalsPage() {
		
		Label weightLabel = new Label("Weight:"), heightLabel = new Label("Height:"), bodyTempLabel = new Label("Body Temp:"),bPressureLabel = new Label("Blood Pressure:"),
		lbsLabel = new Label("lbs"), ftLabel = new Label("ft"),fahrenLabel = new Label("F°"), mmHgLabel = new Label("mmHg"), headLabel = new Label("Enter Patient Vitals:");
		Label errorMsg = new Label("Empty/Invalid inputs");
		
		HBox infoHoldBox3 = new HBox(), buttonBox = new HBox();
		
		VBox infoLabelBox3 = new VBox(), infoTextFieldBox3 = new VBox(), infoLabelBox4 = new VBox(),vitalsBottomBox = new VBox();
		
		TextField weightField = new TextField(), heightField = new TextField(),bTempField = new TextField(), bPressureField = new TextField();
		
		CheckBox check12 = new CheckBox("Patient is over 12 years old");

		Button continueButton = new Button("Continue"), menu = new Button("Menu");

		errorMsg.setTextFill(Color.RED);
		headLabel.setFont(new Font("Arial",20));
		headLabel.setPadding(new Insets(0,0,10,0));
		continueButton.setPrefSize(70, 20);
		menu.setPrefSize(70, 20);
		infoLabelBox3.getChildren().addAll(heightLabel,weightLabel,bodyTempLabel,bPressureLabel);
		infoTextFieldBox3.getChildren().addAll(heightField,weightField,bTempField,bPressureField);
		infoLabelBox4.getChildren().addAll(ftLabel,lbsLabel,fahrenLabel,mmHgLabel);
		infoHoldBox3.getChildren().addAll(infoLabelBox3,infoTextFieldBox3,infoLabelBox4);
		buttonBox.getChildren().add(continueButton);
		vitalsBottomBox.getChildren().addAll(check12,buttonBox);
		infoLabelBox3.setSpacing(25);
		infoTextFieldBox3.setSpacing(15);
		infoLabelBox4.setSpacing(25);
		infoHoldBox3.setSpacing(10);
		buttonBox.setSpacing(15);
		vitalsBottomBox.setSpacing(10);
		vitalsBottomBox.setAlignment(Pos.CENTER);
		vitalsBottomBox.setPadding(new Insets(10,0,0,0));
		
		vitalsPane.setPadding(new Insets(10,10,10,10));
		vitalsPane.setTop(headLabel);
		vitalsPane.setLeft(infoHoldBox3);
		vitalsPane.setBottom(vitalsBottomBox);
		
		continueButton.setOnAction(e -> {
			height = heightField.getText();
			weight = weightField.getText();
			bodyTemp = bTempField.getText();
			bPressure = bPressureField.getText();
			if(check12.isSelected())
				over12 = true;
			if (weight.isBlank() || height.isBlank() || bodyTemp.isBlank() || bPressure.isBlank()) { //checking if inputs are empty
				if(!buttonBox.getChildren().contains(errorMsg)) //check if error message is already being displayed
					buttonBox.getChildren().add(errorMsg);
			}
			else {
				if(buttonBox.getChildren().contains(errorMsg)) //clear error message for next vitalPage instance.
					buttonBox.getChildren().remove(errorMsg);
				primaryStage.setScene(additionalInfo);
			}
			heightField.clear();
			weightField.clear();
			bTempField.clear();
			bPressureField.clear();
			check12.setSelected(false);
		});
	}

	private void additionalPage() {
		
		Label additionalInfoLabel = new Label("Addtional Information"), allergiesLabel = new Label("Allergies"), concernsLabel = new Label("Health Concerns");
		
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
			patientID = lastName.substring(0,3) + firstName.substring(0,2) + dateOfB.substring(0,2);
			String tempDate = date.replace("/", "");
			String year = tempDate.substring(4,6);
			String month = tempDate.substring(2,4);
			String day = tempDate.substring(0,2);
			File patientRecords = new File("Patients\\" + patientID + "\\PatientRecords"); //makes sub folder to store patient visits into
			patientRecords.mkdirs();
			
			File patientFile = new File(patientRecords, patientID + "_" + year + month + day + ".txt");
			try {
					FileWriter fw = new FileWriter(patientFile);
					BufferedWriter bw = new BufferedWriter(fw);

					bw.write("Height: " + height);bw.newLine();
					bw.write("Weight: " + weight);bw.newLine();
					bw.write("Body Temperature: " + bodyTemp);bw.newLine();
					bw.write("Blood Pressure: " + bPressure);bw.newLine();
					bw.write("Known Allergies: " + knownAllergies);bw.newLine();
					bw.write("Health Concerns: " + healthConcerns);bw.newLine();

					//if (over12 == true)
					//	bw.write("Is over 12");
					bw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			allergyField.clear();
			concernField.clear();
			primaryStage.setScene(nurseMain);
		});
	}

	private void recordsPrePage() throws IOException{
		Label lookup = new Label("Patient Lookup:"), fName = new Label("First Name:"), lName = new Label("Last Name:"), dob = new Label("Date of Birth:");
		Label errorInputMsg = new Label("Empty/Invalid Inputs"), errorFileMsg = new Label("Record doesn't exist");
		
		HBox infoHoldBox = new HBox(), searchButtonHold = new HBox();
		
		VBox searchBox = new VBox(), infoLabelBox = new VBox(),infoTextFieldBox = new VBox();

		Button search = new Button("Search"), menu = new Button("Menu");
		
		TextField nameField = new TextField(), nameField2 = new TextField(),birthField = new TextField();
		
		errorInputMsg.setTextFill(Color.RED);
		errorFileMsg.setTextFill(Color.RED);
		infoLabelBox.getChildren().addAll(fName,lName,dob);
		infoTextFieldBox.getChildren().addAll(nameField,nameField2,birthField);
		infoHoldBox.getChildren().addAll(infoLabelBox,infoTextFieldBox);
		searchButtonHold.getChildren().addAll(menu,search);
		searchButtonHold.setSpacing(15);
		searchBox.getChildren().addAll(lookup,infoHoldBox,searchButtonHold);
		searchBox.setPadding(new Insets(10,10,10,10));
		searchBox.setSpacing(15);
		infoTextFieldBox.setSpacing(15);
		infoLabelBox.setSpacing(25);
		search.setPrefSize(70, 20);
		menu.setPrefSize(70, 20);
		
		preRecordsPane.setCenter(searchBox);
		preRecordsPane.setPadding(new Insets(10,10,10,10));
		
		search.setOnAction(e -> {
			firstName = nameField.getText();
			lastName = nameField2.getText();
			dateOfB = birthField.getText();
			patientID = lastName.substring(0,3) + firstName.substring(0,2) + dateOfB.substring(0,2);
			File patientRecords = new File("Patients\\" + patientID + "\\PatientRecords");
			if (firstName.isBlank() || lastName.isBlank() || dateOfB.isBlank()) {//checks if values inputed are blank
				if(!searchButtonHold.getChildren().contains(errorInputMsg) && !searchButtonHold.getChildren().contains(errorFileMsg)) //check that neither error message is being displayed.
					searchButtonHold.getChildren().add(errorInputMsg);
				else if (!searchButtonHold.getChildren().contains(errorInputMsg)) { //check that input error message is not being displayed knowing that file error message is being displayed.
					searchButtonHold.getChildren().remove(errorFileMsg);
					searchButtonHold.getChildren().add(errorInputMsg);
				}
			} else { //checks whether input has a file
				
				if (patientRecords.exists()) {
					recordsPage();
					if(searchButtonHold.getChildren().contains(errorInputMsg)) //clears error message
						searchButtonHold.getChildren().remove(errorInputMsg);
					if(searchButtonHold.getChildren().contains(errorFileMsg))
						searchButtonHold.getChildren().remove(errorFileMsg);
					primaryStage.setScene(records);
				} else { //file error message implementation
					if(!searchButtonHold.getChildren().contains(errorInputMsg) && !searchButtonHold.getChildren().contains(errorFileMsg)) //check that neither error message is being displayed.
						searchButtonHold.getChildren().add(errorFileMsg);
					else if (!searchButtonHold.getChildren().contains(errorFileMsg)) { //check that file error message is not being displayed knowing that the input message is being displayed..
						searchButtonHold.getChildren().remove(errorInputMsg);
						searchButtonHold.getChildren().add(errorFileMsg);
					}
				}
			}
			
			nameField.clear();
			nameField2.clear();
			birthField.clear();
			
		});
		menu.setOnAction(e -> {
			
			nameField.clear();
			nameField2.clear();
			birthField.clear();
			
			if(searchButtonHold.getChildren().contains(errorInputMsg)) //clears errors
				searchButtonHold.getChildren().remove(errorInputMsg);
			if(searchButtonHold.getChildren().contains(errorFileMsg))
				searchButtonHold.getChildren().remove(errorFileMsg);
			primaryStage.setScene(nurseMain);
		});
	}
	
	private void recordsPage() {

		File patientRecords = new File("Patients\\" + patientID + "\\PatientRecords");
		
		//File patientReport = new File(patientFolder, patientID + "_" + year + month + day + ".txt");
		Button menuButton = new Button("Menu"), viewButton = new Button("View Visit");
		VBox mainBox = new VBox(),labelsBox = new VBox(), valueBox = new VBox(),centerBox = new VBox(),rightBox = new VBox();
		HBox buttonBox = new HBox(),leftBox = new HBox(),hBox = new HBox();
		
		Label docRec = new Label("Doctor Recommendations:");
		Label weightLabel = new Label("Weight:"), heightLabel = new Label("Height:"), bodyTempLabel = new Label("Body Temp:"),bPressureLabel = new Label("Blood Pressure:"),
		allergyLabel = new Label("Allergies"),concernsLabel = new Label("Health Concerns:"),medLabel = new Label("Prescriptions:");
		Label heightInfo = new Label(), weightInfo = new Label(), tempInfo = new Label(),pressureInfo = new Label(),allergyInfo = new Label(),concernInfo = new Label(),medInfo = new Label();
		
		TextArea recArea = new TextArea();
		
		ListView<String> list = new ListView<>();
		String[] fileList = patientRecords.list();
		if(fileList != null) 
			list.getItems().addAll(fileList);
		
		rightBox.setPrefSize(200, 225);
		centerBox.setPrefSize(200, 225);
		leftBox.setPrefSize(200, 225);
		labelsBox.getChildren().addAll(heightLabel,weightLabel,bodyTempLabel,bPressureLabel,allergyLabel,concernsLabel,medLabel);
		valueBox.getChildren().addAll(heightInfo,weightInfo,tempInfo,pressureInfo,allergyInfo,concernInfo,medInfo);
		leftBox.getChildren().addAll(labelsBox,valueBox);
		centerBox.getChildren().addAll(docRec,recArea);
		rightBox.getChildren().add(list);
		buttonBox.getChildren().addAll(viewButton,menuButton);
		hBox.getChildren().addAll(leftBox,centerBox,rightBox);
		mainBox.getChildren().addAll(hBox,buttonBox);
		hBox.setSpacing(20);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setSpacing(5);
		leftBox.setSpacing(10);
		mainBox.setPadding(new Insets(20,20,20,20));
		labelsBox.setSpacing(15);
		valueBox.setSpacing(15);
		allergyInfo.setWrapText(true);
		concernInfo.setWrapText(true);
		recArea.setEditable(false);
		recordsPane.setCenter(mainBox);
		
		viewButton.setOnAction(e -> {
			String file = list.getSelectionModel().getSelectedItem();
			if (file != null) {
				File patientFile = new File(patientRecords, file);
				if(patientFile.isFile())
				try {
					BufferedReader br = new BufferedReader(new FileReader(patientFile));
					String heightText = br.readLine();
					height = heightText.substring(heightText.lastIndexOf(":") + 1);
					String weightText = br.readLine();
					weight = weightText.substring(weightText.lastIndexOf(":") + 1);
					String bodyTempText = br.readLine();
					bodyTemp = bodyTempText.substring(bodyTempText.lastIndexOf(":") + 1);
					String bPressureText = br.readLine();
					bPressure = bPressureText.substring(bPressureText.lastIndexOf(":") + 1);
					String knownAllergiesText = br.readLine();
					knownAllergies = knownAllergiesText.substring(knownAllergiesText.lastIndexOf(":") + 1);
					String healthConcernsText = br.readLine();
					healthConcerns = healthConcernsText.substring(healthConcernsText.lastIndexOf(":") + 1);
					br.close();
					heightInfo.setText(height);
					weightInfo.setText(weight);
					tempInfo.setText(bodyTemp);
					pressureInfo.setText(bPressure);
					allergyInfo.setText(knownAllergies);
					concernInfo.setText(healthConcerns);
					medInfo.setText(prescribedMed);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}	
		});
		menuButton.setOnAction(e -> {
			
			primaryStage.setScene(nurseMain);
		});
	}
}