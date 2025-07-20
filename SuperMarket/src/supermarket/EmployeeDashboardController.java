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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
//import java.sql.SQLException;


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
    private TableView<customerData> purchas_tableView;
    @FXML
    private TableColumn<customerData, String> purchas_col_brand;
    @FXML
    private TableColumn<customerData, String> purchas_col_productName;
    @FXML
    private TableColumn<customerData, String> purchas_col_quantity;
    @FXML
    private TableColumn<customerData, String> purchas_col_price;
    @FXML
    private TextField purchas_brand;
    @FXML
    private ComboBox<String> purchas_productName; 
    @FXML
    private Spinner<Integer> purchas_quantity;
    @FXML
    private Button purchas_add;
    @FXML
    private Label purchas_total;
    @FXML
    private Button purchas_pay;
    @FXML
    private Button purchas_recipt;
    @FXML
    private void onBrandTyped() {
    purchaseSearchBrand();
     }
    
    
   private Connection connect;
   private PreparedStatement prepare;
   private Statement statement;
   private ResultSet result;
   
  public void purchaseSearchBrand() {
    String searchB = "SELECT * FROM product WHERE brand = ? AND status = 'Available'";

    connect = database.connectionDb();

    try {
        prepare = connect.prepareStatement(searchB);
        prepare.setString(1, purchas_brand.getText().trim());

        result = prepare.executeQuery();

        ObservableList<String> listProduct = FXCollections.observableArrayList();

        while (result.next()) {
            listProduct.add(result.getString("product_name"));
        }

        if (listProduct.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText(purchas_brand.getText() + " not found!");
            alert.showAndWait();
        } else {
            purchas_productName.getItems().clear();
            purchas_productName.setItems(listProduct);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}


   
   
   
   
   private SpinnerValueFactory spinner;
   
   public void purchaseSpinner(){
       spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20,0);
       
        purchas_quantity.setValueFactory(spinner);
   }
   
   private int qty;
    @FXML
   public void purchaseSpinnerValue(){
       qty = purchas_quantity.getValue();
       
       //System.out.println(qty);
   }
    
   public ObservableList<customerData>purchaseListData(){
       
       purchaseCustomerId();
       ObservableList<customerData> customerList = FXCollections.observableArrayList();
       
        String sql = "SELECT * FROM customer WHERE customer_id='"+customerId+"'";
        
        try{
            
            customerData custD;
            prepare = connect.prepareStatement(sql);
           result = prepare.executeQuery();
           
           while(result.next()){
              
        
              custD = new customerData(result.getInt("customer_id")
                       ,result.getString("brand")
                       ,result.getString("productName")
                       ,result.getInt("quantity")
                      ,result.getDouble("price")
                       ,result.getDate("date"));
               
              customerList.add( custD);
           
           } 
        }catch(Exception e){e.printStackTrace();}
           return customerList;
   }
   
   
    public ObservableList<customerData>purchaseList;
     public void purchaseShowListData(){ //to show the data tableview
       purchaseList =  purchaseListData();
       
       purchas_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
       purchas_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        purchas_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
       purchas_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
       
       
      purchas_tableView.setItems(purchaseList);
   }
     
     
     private int customerId;
     
     public void purchaseCustomerId(){
         
         String cID = "SELECT customer_id FROM customer";
         
         connect = database.connectionDb();
         
         try{
             
             prepare = connect.prepareStatement(cID);
              result = prepare.executeQuery();
             
              while (result.next()){
                  customerId = result.getInt("customer_id");
              }
              
              int checkNum =0;
              
              String checkCustomerId = "SELECT customer_id FROM customer_receipt";
              
              statement = connect.createStatement();
             result = statement.executeQuery(checkCustomerId);
              
              while (result.next()){
                  checkNum = result.getInt("customer_id");
              }
              
              if(customerId==0){
                  customerId+=1;
              }else if(checkNum == customerId ){
                   customerId+=1;
              }
         
         }catch(Exception e){e.printStackTrace();}
         
     }
        
   
   
   
    
    public void displayEmployeeId(){
        purchas_employeeId.setText(getData.employeeId);
    }
    
    private double x = 0;
    private double y =0;
    
    
    
    
    @FXML
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
       displayEmployeeId();
        purchaseShowListData();
        purchaseSpinner();
        //purchaseSpinnerValue();
    }
    
}
