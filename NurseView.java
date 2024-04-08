import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
	private BorderPane createPane = new BorderPane();
	private BorderPane additionalPane = new BorderPane();
	
	private Scene nurseMain = new Scene(nursePane,500,250);
	private Scene createLookup = new Scene(createPane,500,250);
	private Scene records = new Scene(recordsPane,500,275);
	private Scene vitals = new Scene(vitalsPane,500,250);
	private Scene additionalInfo = new Scene(additionalPane,500,250);

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

	public NurseView(String userName, Stage primaryStage)
	{
		super();
		this.userName = userName;
		this.primaryStage = primaryStage;
		nurseMainPage();
	}
	private void nurseMainPage() {
		
		primaryStage.setScene(nurseMain);
		createLookupPage();
		vitalsPage();
		recordsPage();
		additionalPage();
		
		Button viewRecords = new Button("View Records");
		Button enterVitals = new Button("Enter Vitals");
		Button messagePortal = new Button("Messages");
		Button logoutButton = new Button("Logout");

		
		HBox mainHolder = new HBox();

		VBox recordsVitalsHolder = new VBox();	
		VBox headLabels = new VBox();
		VBox msgLogHolder = new VBox();
		VBox leftHolder = new VBox();
		VBox prevHealthBox = new VBox(), prevMedBox = new VBox(), immunRecordBox = new VBox();
		
		Label welcome = new Label("Welcome, Nurse");
		Label optionSelect = new Label("Please select an option:");
		Label lookup = new Label("Patient Lookup:"), fName = new Label("First Name:");
		Label prevHealthInfo = new Label(healthConcerns), prevMedInfo = new Label(prescribedMed), immunRecordInfo = new Label(immunizationRecord);
		
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
			prevHealthBox.getChildren().add(prevHealthInfo);
			prevMedBox.getChildren().add(prevHealthInfo);
			immunRecordBox.getChildren().add(immunRecordInfo);
			primaryStage.setScene(records);
		});
		enterVitals.setOnAction(e -> primaryStage.setScene(createLookup));
        
	}
	private void createLookupPage() {
		
		Label lookup = new Label("Patient Lookup:"), fName = new Label("First Name:"), lName = new Label("Last Name:"), fName2 = new Label("First Name:"),
		lName2 = new Label("Last Name:"), dob = new Label("Date of Birth:"), dob2 = new Label("Date of Birth:"), createP = new Label("Create New Patient:"),
		head = new Label("What would you like to do?");
		
		HBox infoHoldBox = new HBox(), infoHoldBox2 = new HBox(), createButtonHold = new HBox(), searchButtonHold = new HBox();
		
		VBox lookupBox = new VBox(), createBox = new VBox(), infoLabelBox = new VBox(),infoTextFieldBox = new VBox(), infoLabelBox2 = new VBox(),
		infoTextFieldBox2 = new VBox(); 

		Button search = new Button("Search"), create = new Button("Create");
		
		TextField nameField = new TextField(), nameField2 = new TextField(), nameField3 = new TextField(), nameField4 = new TextField(),
		birthField = new TextField(), birthField2 = new TextField();
		
		
		
		

		head.setFont(new Font("Arial",20));
		head.setPadding(new Insets(0,0,10,0));
		
		infoLabelBox.getChildren().addAll(fName,lName,dob);
		infoTextFieldBox.getChildren().addAll(nameField,nameField2,birthField);
		infoHoldBox.getChildren().addAll(infoLabelBox,infoTextFieldBox);
		searchButtonHold.getChildren().add(search);
		lookupBox.getChildren().addAll(lookup,infoHoldBox,searchButtonHold);
		lookupBox.setPadding(new Insets(10,10,10,10));
		lookupBox.setSpacing(15);
		infoTextFieldBox.setSpacing(15);
		infoLabelBox.setSpacing(25);
		searchButtonHold.setAlignment(Pos.BOTTOM_RIGHT);
		
		infoLabelBox2.getChildren().addAll(fName2,lName2,dob2);
		infoTextFieldBox2.getChildren().addAll(nameField3,nameField4,birthField2);
		infoHoldBox2.getChildren().addAll(infoLabelBox2,infoTextFieldBox2);
		createButtonHold.getChildren().add(create);
		createBox.getChildren().addAll(createP,infoHoldBox2,createButtonHold);
		createBox.setPadding(new Insets(10,10,10,10));
		createBox.setSpacing(15);
		infoTextFieldBox2.setSpacing(15);
		infoLabelBox2.setSpacing(25);
		createButtonHold.setAlignment(Pos.BOTTOM_RIGHT);
		
		createPane.setTop(head);
		createPane.setPadding(new Insets(10,10,10,10));
		createPane.setLeft(lookupBox);
		createPane.setCenter(createBox);
		
		search.setOnAction(e -> {
			firstName = nameField.getText();
			lastName = nameField2.getText();
			dateOfB = birthField.getText();
			/*
			 * insert database search
			 */
			nameField.clear();
			nameField2.clear();
			birthField.clear();
			primaryStage.setScene(vitals);
		});
		create.setOnAction(e -> {
			firstName = nameField.getText();
			lastName = nameField2.getText();
			dateOfB = birthField.getText();
			/*
			 * insert database patient creation
			 */
			nameField.clear();
			nameField2.clear();
			birthField.clear();
			primaryStage.setScene(vitals);
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
			/*
			 * add info to patient in database
			 */
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
	}
	
	private void recordsPage() {
		
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
