/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author user
 */
public class adminDashboardController implements Initializable {

    @FXML
    private Button addProducts_addBtn;

    @FXML
    private TextField addProducts_brandName;

    @FXML
    private Button addProducts_btn;

    @FXML
    private Button addProducts_clearBtn;

    @FXML
    private TableColumn<?, ?> addProducts_col_branchName;

    @FXML
    private TableColumn<?, ?> addProducts_col_price;

    @FXML
    private TableColumn<?, ?> addProducts_col_productID;

    @FXML
    private TableColumn<?, ?> addProducts_col_productName;

    @FXML
    private TableColumn<?, ?> addProducts_col_status;

    @FXML
    private Button addProducts_deleteBtn;

    @FXML
    private AnchorPane addProducts_form;

    @FXML
    private TextField addProducts_price;

    @FXML
    private TextField addProducts_productID;

    @FXML
    private TextField addProducts_productName;

    @FXML
    private TextField addProducts_search;

    @FXML
    private ComboBox<?> addProducts_status;

    @FXML
    private TableView<?> addProducts_tableView;

    @FXML
    private Button addProducts_updateBtn;

    @FXML
    private Label dashboard_IncomToday;

    @FXML
    private Label dashboard_activeEmployee;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AreaChart<?, ?> dashboard_chart;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Label dashboard_totalIncome;

    @FXML
    private Button employees_btn;

    @FXML
    private Button employees_clearBtn;

    @FXML
    private TableColumn<?, ?> employees_col_date;

    @FXML
    private TableColumn<?, ?> employees_col_employeeID;

    @FXML
    private TableColumn<?, ?> employees_col_firstName;

    @FXML
    private TableColumn<?, ?> employees_col_gender;

    @FXML
    private TableColumn<?, ?> employees_col_lastName;

    @FXML
    private TableColumn<?, ?> employees_col_password;

    @FXML
    private Button employees_deleteBtn;

    @FXML
    private TextField employees_employeeID;

    @FXML
    private TextField employees_firstName;

    @FXML
    private AnchorPane employees_from;

    @FXML
    private ComboBox<?> employees_gender;

    @FXML
    private TextField employees_lastName;

    @FXML
    private TextField employees_password;

    @FXML
    private Button employees_saveBtn;

    @FXML
    private TableView<?> employees_tableView;

    @FXML
    private Button employees_updateBtn;

    @FXML
    private Label logout;

    @FXML
    private Label username;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button close;

    @FXML
    private Button minimize;

    private double x = 0;
    private double y = 0;

    public void logout() {

        try {

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("confirmation message");
            alert.setHeaderText(null);
            alert.setContentText("are you sure want to logout?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                logout.getScene().getWindow().hide();
                //back to login form
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {

                    x = event.getSceneX();
                    y = event.getSceneY();

                });

                root.setOnMouseDragged((MouseEvent event) -> {

                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenX() - y);

                    stage.setOpacity(.8);
                });
        root.setOnMouseReleased((MouseEvent event) ->{
       
          stage.setOpacity(1);
    });
   
    stage.initStyle(StageStyle.TRANSPARENT);
                
                stage.setScene(scene);
                stage.show();

            } else return;
            

        } catch (Exception e) {e.printStackTrace();}
        

    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            addProducts_form.setVisible(false);
            employees_from.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color: linear-gradient(to top right, #896b34, #b8a536");
            addProducts_btn.setStyle("-fx-background-color:transparent");
            employees_btn.setStyle("-fx-background-color:transparent");

        } else if (event.getSource() == addProducts_btn) {
            dashboard_form.setVisible(false);
            addProducts_form.setVisible(true);
            employees_from.setVisible(false);

            addProducts_btn.setStyle("-fx-background-color: linear-gradient(to top right, #896b34, #b8a536");
            dashboard_btn.setStyle("-fx-background-color:transparent");
            employees_btn.setStyle("-fx-background-color:transparent");

        } else if (event.getSource() == employees_btn) {
            dashboard_form.setVisible(false);
            addProducts_form.setVisible(false);
            employees_from.setVisible(true);

            employees_btn.setStyle("-fx-background-color: linear-gradient(to top right, #896b34, #b8a536");
            addProducts_btn.setStyle("-fx-background-color:transparent");
            dashboard_btn.setStyle("-fx-background-color:transparent");

        }
    }

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        if (main_form != null && main_form.getScene() != null) {
            Stage stage = (Stage) main_form.getScene().getWindow();
            stage.setIconified(true);
        } else {
            System.out.println("main_form or Scene is null!");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
