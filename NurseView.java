import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class NurseView extends CSE360_Main
{
	private String userName;
	private BorderPane nursePane = new BorderPane();
	private BorderPane recordsPane = new BorderPane();
	private BorderPane vitalsPane = new BorderPane();
	private BorderPane preVitalPane = new BorderPane();
	private BorderPane additionalPane = new BorderPane();
	private BorderPane preRecordsPane = new BorderPane();
	
	private Scene nurseMain = new Scene(nursePane,500,250);
	private Scene preVital = new Scene(preVitalPane,500,250);
	private Scene records = new Scene(recordsPane,500,275);
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

	public NurseView(String userName, Stage primaryStage)
	{
		super();
		this.userName = userName;
		this.primaryStage = primaryStage;
		nurseMainPage();
	}
	private void nurseMainPage() {
		
		primaryStage.setScene(nurseMain);
		vitalsPrePage();
		vitalsPage();
		additionalPage();
		recordsPrePage();
		
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
		msgLogHolder.getChildren().addAll(messagePortal,logoutButton);
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
			primaryStage.setScene(preRecords);
		});
		
		enterVitals.setOnAction(e -> primaryStage.setScene(preVital));
		logoutButton.setOnAction(e -> {
			buildLogin();
			primaryStage.setScene(new Scene(mainPane,500,250));
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
			if (firstName.isBlank() || lastName.isBlank() || dateOfB.isBlank())
				System.out.println("Blank Values");
			else  {
				primaryStage.setScene(vitals);
			}
			nameField.clear();
			nameField2.clear();
			birthField.clear();
		});
		
	}
	private void recordsPrePage(){
		Label lookup = new Label("Patient Lookup:"), fName = new Label("First Name:"), lName = new Label("Last Name:"), dob = new Label("Date of Birth:");
		
		HBox infoHoldBox = new HBox(), searchButtonHold = new HBox();
		
		VBox searchBox = new VBox(), infoLabelBox = new VBox(),infoTextFieldBox = new VBox();

		Button search = new Button("Search");
		
		TextField nameField = new TextField(), nameField2 = new TextField(),birthField = new TextField();
		
		
		infoLabelBox.getChildren().addAll(fName,lName,dob);
		infoTextFieldBox.getChildren().addAll(nameField,nameField2,birthField);
		infoHoldBox.getChildren().addAll(infoLabelBox,infoTextFieldBox);
		searchButtonHold.getChildren().add(search);
		searchBox.getChildren().addAll(lookup,infoHoldBox,searchButtonHold);
		searchBox.setPadding(new Insets(10,10,10,10));
		searchBox.setSpacing(15);
		infoTextFieldBox.setSpacing(15);
		infoLabelBox.setSpacing(25);
		
		preRecordsPane.setCenter(searchBox);
		preRecordsPane.setPadding(new Insets(10,10,10,10));
		
		search.setOnAction(e -> {
			firstName = nameField.getText();
			lastName = nameField2.getText();
			dateOfB = birthField.getText();
			if (firstName.isBlank() || lastName.isBlank() || dateOfB.isBlank()) //checks if nothing has been inputed
				System.out.println("Blank Values");
			else  {
				patientID = lastName.substring(0,3) + firstName.substring(0,2) + dateOfB.substring(0,2);
				File patientReport = new File(patientID + "_PatientReport.txt");
				if (patientReport.isFile())
				{
					try {
						BufferedReader br = new BufferedReader(new FileReader(patientReport));
						//String fNameText = br.readLine();
						//firstName = fNameText.substring(fNameText.lastIndexOf(":") + 1);
						//String lNameText = br.readLine();
						//lastName = lNameText.substring(lNameText.lastIndexOf(":") + 1);
						String weightText = br.readLine();
						weight = weightText.substring(weightText.lastIndexOf(":") + 1);
						String heightText = br.readLine();
						height = heightText.substring(heightText.lastIndexOf(":") + 1);
						String bodyTempText = br.readLine();
						bodyTemp = bodyTempText.substring(bodyTempText.lastIndexOf(":") + 1);
						String bPressureText = br.readLine();
						bPressure = bPressureText.substring(bPressureText.lastIndexOf(":") + 1);
						String knownAllergiesText = br.readLine();
						knownAllergies = knownAllergiesText.substring(knownAllergiesText.lastIndexOf(":") + 1);
						String healthConcernsText = br.readLine();
						healthConcerns = healthConcernsText.substring(healthConcernsText.lastIndexOf(":") + 1);
						br.close();
						recordsPage(firstName,lastName,healthConcerns,prescribedMed,immunizationRecord);
						primaryStage.setScene(records);
					}catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else
					System.out.println("File does not exist");
			}
			nameField.clear();
			nameField2.clear();
			birthField.clear();
		});
	}
	
	private void vitalsPage() {
		
		Label weightLabel = new Label("Weight:"), heightLabel = new Label("Height:"), bodyTempLabel = new Label("Body Temp:"),bPressureLabel = new Label("Blood Pressure:"),
		lbsLabel = new Label("lbs"), cmLabel = new Label("cm"),fahrenLabel = new Label("F°"), mmHgLabel = new Label("mmHg"), headLabel = new Label("Enter Patient Vitals:");
		
		
		HBox infoHoldBox3 = new HBox();
		
		VBox infoLabelBox3 = new VBox(), infoTextFieldBox3 = new VBox(), infoLabelBox4 = new VBox(),vitalsBottomBox = new VBox();
		
		TextField weightField = new TextField(), heightField = new TextField(),bTempField = new TextField(), bPressureField = new TextField();
		
		CheckBox check12 = new CheckBox("Patient is over 12 years old");

		Button continueButton = new Button("Continue");

		headLabel.setFont(new Font("Arial",20));
		headLabel.setPadding(new Insets(0,0,10,0));
		
		infoLabelBox3.getChildren().addAll(weightLabel,heightLabel,bodyTempLabel,bPressureLabel);
		infoTextFieldBox3.getChildren().addAll(weightField,heightField,bTempField,bPressureField);
		infoLabelBox4.getChildren().addAll(lbsLabel,cmLabel,fahrenLabel,mmHgLabel);
		infoHoldBox3.getChildren().addAll(infoLabelBox3,infoTextFieldBox3,infoLabelBox4);
		vitalsBottomBox.getChildren().addAll(check12,continueButton);
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
			if(check12.isSelected())
				over12 = true;
			
			weightField.clear();
			heightField.clear();
			bTempField.clear();
			bPressureField.clear();
			check12.setSelected(false);
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
			patientID = lastName.substring(0,3) + firstName.substring(0,2) + dateOfB.substring(0,2);
			File patientFile = new File(patientID + "_PatientReport.txt");
			try {
				FileWriter fw = new FileWriter(patientFile);
				BufferedWriter bw = new BufferedWriter(fw);
				//bw.write("First Name: " + firstName);bw.newLine();
				//bw.write("Last Name: " + lastName);bw.newLine();
				bw.write("Weight: " + weight);bw.newLine();
				bw.write("Height: " + height);bw.newLine();
				bw.write("Body Temperature: " + bodyTemp);bw.newLine();
				bw.write("Blood Pressure: " + bPressure);bw.newLine();
				bw.write("Known Allergies: " + knownAllergies);bw.newLine();
				bw.write("Health Concerns: " + healthConcerns);bw.newLine();
				if (over12 == true)
					bw.write("Is over 12");
				bw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			primaryStage.setScene(nurseMain);
			
		});
	}

	
	private void recordsPage(String fName, String lName, String concerns, String medicine, String record) {
		
		this.firstName = fName;
		this.lastName = lName;
		this.healthConcerns = concerns;
		this.prescribedMed = medicine;
		this.immunizationRecord = record;
		
		Button menuButton = new Button("Menu");
		
		Label recordsHeadLabel = new Label("Patient Records for: " + firstName + " " + lastName), prevHealthLabel = new Label("Previous\nHealth\nIssues:"), prevMedLabel = new Label("Previously\nPrescribed\nMedication:"),
		immunRecordLabel = new Label("Immunization\nRecord:"), prevHealthInfo = new Label(healthConcerns), prevMedInfo = new Label(prescribedMed), immunRecordInfo = new Label(immunizationRecord);

		VBox infoLabelBox6 = new VBox(),infoTextBox = new VBox(), bottomMenuBox = new VBox(),prevHealthBox = new VBox(), prevMedBox = new VBox(), immunRecordBox = new VBox();
		
		HBox recordsBox = new HBox();
		
		recordsHeadLabel.setFont(new Font("Arial",20));
		recordsHeadLabel.setPadding(new Insets(0,0,10,0));
		prevHealthLabel.setWrapText(true);
		prevMedLabel.setWrapText(true);
		immunRecordLabel.setWrapText(true);
		prevHealthInfo.setWrapText(true);
		prevMedInfo.setWrapText(true);
		immunRecordInfo.setWrapText(true);
		prevHealthBox.setStyle("-fx-border-color: black");
		prevMedBox.setStyle("-fx-border-color: black");
		immunRecordBox.setStyle("-fx-border-color: black");
		infoLabelBox6.getChildren().addAll(prevHealthLabel,prevMedLabel,immunRecordLabel);
		infoTextBox.getChildren().addAll(prevHealthBox,prevMedBox,immunRecordBox);
		bottomMenuBox.getChildren().add(menuButton);
		bottomMenuBox.setAlignment(Pos.BOTTOM_RIGHT);
		bottomMenuBox.setPadding(new Insets(0,30,0,0));
		infoLabelBox6.setSpacing(30);
		infoTextBox.setSpacing(30);
		recordsBox.getChildren().addAll(infoLabelBox6,infoTextBox);
		prevHealthBox.setPrefSize(300, 60);
		prevMedBox.setPrefSize(300, 60);
		immunRecordBox.setPrefSize(300, 60);
		prevHealthBox.getChildren().add(prevHealthInfo);
		prevMedBox.getChildren().add(prevMedInfo);
		immunRecordBox.getChildren().add(immunRecordInfo);
		
		recordsPane.setPadding(new Insets(10,10,10,10));
		recordsPane.setTop(recordsHeadLabel);
		recordsPane.setCenter(recordsBox);
		recordsPane.setBottom(bottomMenuBox);
		
		
		menuButton.setOnAction(e -> {
			prevHealthBox.getChildren().clear();
			prevMedBox.getChildren().clear();
			immunRecordBox.getChildren().clear();
			primaryStage.setScene(nurseMain);
		});
	}

}
