package supermarket;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXMLDocumentController implements Initializable {

    @FXML
    private AnchorPane main_from;
    @FXML
    private AnchorPane admin_from;
    @FXML
    private TextField admin_username;
    @FXML
    private PasswordField admin_password;
    @FXML
    private Button admin_loginBtn;
    @FXML
    private Hyperlink admin_hyperLink;
    @FXML
    private TextField employee_id;
    @FXML
    private PasswordField employee_password;
    @FXML
    private Button employee_loginBtn;
    @FXML
    private Hyperlink employee_hyperLink;
    @FXML
    private AnchorPane employee_from;

    private Connection connect;
    private ResultSet result;
    private PreparedStatement prepare;
    
    private double x=0;
     private double y=0;
    
    
    
    
    
    @FXML
    public void employeeLogin(){
      
        String employeeData = "SELECT * FROM employee WHERE employee_id=? and  password = ?";
        
        connect = database.connectionDb();
        
        try{
            
            Alert alert;
            prepare =connect.prepareStatement(employeeData);
            
            if (employee_id.getText().isEmpty() || employee_password.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields.");
                alert.showAndWait();
            }
            
            else{
                prepare.setString(1,employee_id.getText());
                prepare.setString(2,employee_password.getText());
                
                result = prepare.executeQuery();
                
                if(result.next()){
                    //lets create for employee
                    
                    getData.employeeId = employee_id.getText();
                    
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("information message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Loggin!");
                    alert.showAndWait();
                    
                    employee_loginBtn.getScene().getWindow().hide();
                
                
                     Parent root = FXMLLoader.load(getClass().getResource("employeeDashboard.fxml"));
                     Stage stage = new Stage();
                     Scene scene = new Scene(root);
                     
                     root.setOnMousePressed((MouseEvent event) ->{
                         
                         x = event.getSceneX();
                         y = event.getSceneY();
                         
                     });
                     
                     
                      root.setOnMouseDragged((MouseEvent event) ->{
                          
                      stage.setX(event.getScreenX() - x);
                      stage.setX(event.getScreenX() - x);
                      });
                     
                     stage.initStyle(StageStyle.TRANSPARENT);
                     
                     
                     stage.setScene(scene);
                     stage.show();
                }
                
                else{
                     alert = new Alert(Alert.AlertType.ERROR);
                     alert.setTitle("Error");
                     alert.setHeaderText(null);
                     alert.setContentText("Wrong employee id/password.");
                     alert.showAndWait();
                }
            }
            
        }catch(Exception e) {e.printStackTrace();}
    
    }

    @FXML
    public void adminLogin() {
        String adminData = "SELECT * FROM admin WHERE username = ? AND password = ?";
        connect = database.connectionDb();

        try {
            Alert alert;

            if (admin_username.getText().isEmpty() || admin_password.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields.");
                alert.showAndWait();
                return;
            }
            
            prepare = connect.prepareStatement(adminData);
            prepare.setString(1, admin_username.getText());
            prepare.setString(2, admin_password.getText());
            result = prepare.executeQuery();

            if (result.next()) {
                
                getData.username = admin_username.getText();
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login Successful");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Logged In!");
                alert.showAndWait();

                // Hide login form
                admin_loginBtn.getScene().getWindow().hide();

                // Load admin dashboard
                Parent root = FXMLLoader.load(getClass().getResource("adminDashboard.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                
                root.setOnMousePressed((MouseEvent event)->{
                    x= event.getSceneX();
                    y= event.getSceneY();
                    
                });
                
                root.setOnMouseDragged((MouseEvent event)->{
                    stage.setX(event.getScreenX() -x);
                    stage.setY(event.getScreenY() -y);
                });
                
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.show();

            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Wrong username or password.");
                alert.showAndWait();
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void switchForm(ActionEvent event) {
        if (event.getSource() == admin_hyperLink) {
            admin_from.setVisible(false);
            employee_from.setVisible(true);
        } else if (event.getSource() == employee_hyperLink) {
            admin_from.setVisible(true);
            employee_from.setVisible(false);
        }
    }
    
    @FXML
    public void close() {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization if needed
    }
}
