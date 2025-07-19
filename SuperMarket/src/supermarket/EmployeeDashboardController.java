/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author user
 */
public class EmployeeDashboardController implements Initializable {

    @FXML
    private AnchorPane main_form;
    @FXML
    private Button close;
    @FXML
    private Button minimize;
    @FXML
    private Label purchas_employeeId;
    @FXML
    private Button logout;
    @FXML
    private TableView<?> purchas_tableView;
    @FXML
    private TableColumn<?, ?> purchas_col_brand;
    @FXML
    private TableColumn<?, ?> purchas_col_productName;
    @FXML
    private TableColumn<?, ?> purchas_col_quantity;
    @FXML
    private TableColumn<?, ?> purchas_col_price;
    @FXML
    private TextField purchas_brand;
    @FXML
    private ComboBox<?> purchas_productName;
    @FXML
    private Spinner<?> purchas_quantity;
    @FXML
    private Button purchas_add;
    @FXML
    private Label purchas_total;
    @FXML
    private Button purchas_pay;
    @FXML
    private Button purchas_recipt;

    @FXML
    private void close(ActionEvent event) {
        System.exit(0);
    }
    

    @FXML
    private void minimize(ActionEvent event) {
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
