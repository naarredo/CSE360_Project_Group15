/* Creator: Group 15
 * Publish Date: March 20, 2024 */

package application;
	
import java.text.DecimalFormat;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label; 
import javafx.scene.control.RadioButton; 
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class CSE360_Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    private int userType;
    private String userName;
    protected Stage primaryStage;
    HBox mainPane = new HBox();
	VBox loginBox = new VBox(8), centerBox = new VBox(20);
	Button logout = new Button();
	TextArea textArea1 = new TextArea(), textArea2 = new TextArea();
    
    public void start(Stage primaryStage) {
    	this.primaryStage = primaryStage;
    	primaryStage.setTitle("Some Health Site");
    	logout.setText("Logout");
    	
    	buildLogin(); 
    	
    	logout.setOnAction(new EventHandler<>() {
    		public void handle(ActionEvent event) {
    			mainPane.getChildren().clear();
    			centerBox.getChildren().clear();
    			loginBox.getChildren().clear();

    			buildLogin();
    		}
    	});

    	/*opens the scene in a JavaFX window */
    	primaryStage.setScene(new Scene(mainPane,500,250));
    	primaryStage.show();    	
    }
    
    public void buildLogin() {
    	Button choosePatient = new Button(), chooseDoctor = new Button(), chooseNurse = new Button(), login = new Button();
    	Label header = new Label("Welcome To _____"), subheader = new Label("Please choose what you would like to login in as:");
    	Label label1 = new Label("User ID:"), label2 = new Label("Password:"); 
    	
    	userType = 0; 
    	
    	choosePatient.setText("Patient");
    	chooseDoctor.setText("Doctor");
    	chooseNurse.setText("Nurse");
    	login.setText("Login");
    	
    	//set up of text box
    	textArea1.setPrefWidth(160);
    	textArea1.setPrefHeight(5);
    	textArea2.setPrefWidth(160);
    	textArea2.setPrefHeight(5);
    	
    	/*set up of the main panel, including the locations and spaces
    	 *  between the previously made panels */
    	header.setFont(new Font("Arial", 20));
    	
    	loginBox.setAlignment(Pos.CENTER);
    	loginBox.getChildren().add(choosePatient);
    	loginBox.getChildren().add(chooseDoctor);
    	loginBox.getChildren().add(chooseNurse);
    	
    	centerBox.setAlignment(Pos.CENTER);
    	centerBox.getChildren().add(header);
    	centerBox.getChildren().add(subheader);
    	centerBox.getChildren().add(loginBox);
    	
    	mainPane.setAlignment(Pos.CENTER);
    	mainPane.getChildren().add(centerBox);
    	
    	choosePatient.setOnAction(new EventHandler<>() {
    		public void handle(ActionEvent event) {
    			userType = 1; 
    			centerBox.getChildren().clear();
    			loginBox.getChildren().clear();
    			
    			loginBox.setAlignment(Pos.CENTER_LEFT);
    			loginBox.getChildren().add(label1);
    			loginBox.getChildren().add(textArea1);
    			loginBox.getChildren().add(label2);
    			loginBox.getChildren().add(textArea2);
    			
    			centerBox.getChildren().add(loginBox);
    			centerBox.getChildren().add(login);
    		}
    	});
    	
    	chooseDoctor.setOnAction(new EventHandler<>() {
    		public void handle(ActionEvent event) {
    			userType = 2; 
    			centerBox.getChildren().clear();
    			loginBox.getChildren().clear();
    			
    			loginBox.setAlignment(Pos.CENTER_LEFT);
    			loginBox.getChildren().add(label1);
    			loginBox.getChildren().add(textArea1);
    			loginBox.getChildren().add(label2);
    			loginBox.getChildren().add(textArea2);
    			
    			centerBox.getChildren().add(loginBox);
    			centerBox.getChildren().add(login);
    		}
    	});
    	
    	chooseNurse.setOnAction(new EventHandler<>() {
    		public void handle(ActionEvent event) {
    			userType = 3; 
    			centerBox.getChildren().clear();
    			loginBox.getChildren().clear();
    			
    			loginBox.setAlignment(Pos.CENTER_LEFT);
    			loginBox.getChildren().add(label1);
    			loginBox.getChildren().add(textArea1);
    			loginBox.getChildren().add(label2);
    			loginBox.getChildren().add(textArea2);
    			
    			centerBox.getChildren().add(loginBox);
    			centerBox.getChildren().add(login);
    		}
    	});
    	
    	login.setOnAction(new EventHandler<>() {
    		public void handle(ActionEvent event) {
    			userName = textArea1.getText();
    			System.out.print(userType);
    			switch(userType) {
    			case 1:
    				patientHome();
    				break;
    			case 2:
    				doctorHome(); 
    				break;
    			case 3: 
    				nurseHome(); 
    				break;
    			}
    		}
    	});
    }
    
    private void patientHome() {
    	Button button1 = new Button(), button2 = new Button(), button3 = new Button();
    	Label someLabel = new Label("Welcome " + userName);
    	button1.setText("View Records");
    	button2.setText("Send Message");
    	button3.setText("Edit Patient Details");
    	
    	centerBox.getChildren().clear();
    	centerBox.getChildren().addAll(someLabel, button1, button2, button3, logout); 
    }
    
    private void doctorHome() {
        new DoctorView(userName, primaryStage);
    }

    
    private void nurseHome() {
    	Button button1 = new Button(), button2 = new Button(), button3 = new Button(); 
    	Label someLabel = new Label("Welcome Nurse");
    	button1.setText("View Records");
    	button2.setText("Send Message");
    	button3.setText("Enter Vitals");
    	
    	centerBox.getChildren().clear();
    	centerBox.getChildren().addAll(someLabel, button1, button2, button3, logout); 
    }
}