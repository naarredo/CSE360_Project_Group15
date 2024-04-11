/* Creator: Group 15
 * Publish Date: March 20, 2024 */

package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label; 
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class CSE360_Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    private int userType;
    private String userName;
    private String password;
    protected Stage primaryStage;
    HBox mainPane = new HBox();
	VBox loginBox = new VBox(8), centerBox = new VBox(20);
	protected Button logout = new Button();
	TextArea textArea1 = new TextArea(), textArea2 = new TextArea();
	PasswordField pField = new PasswordField();
	Label invalidPass = new Label("Invalid Password");
    
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
    	primaryStage.setScene(new Scene(mainPane,800,400));
    	primaryStage.show();    	
    }
    
    public void buildLogin() {
    	Button choosePatient = new Button(), chooseDoctor = new Button(), chooseNurse = new Button(), login = new Button();
    	Label header = new Label("Welcome To _____"), subheader = new Label("Please choose what you would like to login in as:");
    	Label label1 = new Label("User ID:"), label2 = new Label("Password:");
    	Label label3 = new Label("Doctor Password:"), label4 = new Label("Nurse Password:"); 
    	
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
    	
    	choosePatient.setOnAction(e ->new patientUser(primaryStage));
    	
    	chooseDoctor.setOnAction(new EventHandler<>() {
    		public void handle(ActionEvent event) {
    			userType = 2; 
    			centerBox.getChildren().clear();
    			loginBox.getChildren().clear();
    			
    			loginBox.setAlignment(Pos.CENTER_LEFT);
    			loginBox.getChildren().add(label3);
    			loginBox.getChildren().add(pField);
    			
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
    			loginBox.getChildren().add(label4);
    			loginBox.getChildren().add(pField);
    			
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
    				patientPassCheck();
    				break;
    			case 2:
    				if(loginBox.getChildren().contains(invalidPass)) { 
    					loginBox.getChildren().remove(invalidPass);
    				}
    				
    				doctorPassCheck(); 
    				break;
    				
    			case 3: 
    				if(loginBox.getChildren().contains(invalidPass)) {
    					loginBox.getChildren().remove(invalidPass);
    				}
    				
    				nursePassCheck(); 
    				break;
    			}
    		}
    	});
    }
    
    private void doctorPassCheck() {
    	invalidPass.setTextFill(Color.RED);
    	password = pField.getText();
    	
    	if(password.equals("password")) {
    		new DoctorView(primaryStage);
    	} else { 
    		loginBox.getChildren().add(invalidPass);
    		pField.clear();
    	}
    }

    private void nursePassCheck() {
    	invalidPass.setTextFill(Color.RED);
    	password = pField.getText();
    	
    	if (password.equals("password")) {
    		new NurseView(primaryStage);
    	} else {
    		loginBox.getChildren().add(invalidPass);
    		pField.clear();
    	}		
    }
}
