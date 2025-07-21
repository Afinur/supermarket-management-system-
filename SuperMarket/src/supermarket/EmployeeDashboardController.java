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
 import java.sql.Date;


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
  
  
  // Controller class এর ভিতরে রাখো নিচের মেথডটি
private int generateCustomerId() {
    int id = 1;
    try {
        String query = "SELECT MAX(customer_id) AS max_id FROM customer";
        connect = database.connectionDb();
        prepare = connect.prepareStatement(query);
        result = prepare.executeQuery();
        if (result.next()) {
            id = result.getInt("max_id") + 1;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return id;
}


  
@FXML
private void addToTable() {
    String brand = purchas_brand.getText().trim();
    String product = purchas_productName.getValue();
    int quantity = purchas_quantity.getValue();

    if (brand.isEmpty() || product == null || quantity <= 0) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText("Please enter all fields correctly.");
        alert.showAndWait();
        return;
    }

    double price = 0;
    Date date = new Date(System.currentTimeMillis());
    int customerId = generateCustomerId();

    try {
        connect = database.connectionDb();

        String sql = "SELECT price FROM product WHERE brand = ? AND product_name = ?";
        prepare = connect.prepareStatement(sql);
        prepare.setString(1, brand);
        prepare.setString(2, product);
        result = prepare.executeQuery();

        if (result.next()) {
            price = result.getDouble("price") * quantity;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Product not found!");
            alert.showAndWait();
            return;
        }

        customerData item = new customerData(customerId, brand, product, quantity, price, date);
        purchaseList.add(item);
        purchas_tableView.setItems(purchaseList); // update TableView

        String insertCustomer = "INSERT INTO customer (customer_id, brand, productName, quantity, price, date) VALUES (?, ?, ?, ?, ?, ?)";
        prepare = connect.prepareStatement(insertCustomer);
        prepare.setInt(1, customerId);
        prepare.setString(2, brand);
        prepare.setString(3, product);
        prepare.setInt(4, quantity);
        prepare.setDouble(5, price);
        prepare.setDate(6, date);
        prepare.executeUpdate();

        System.out.println("Added to customer table with date: " + date);
        calculateTotal();
        resetForm();

    } catch (Exception e) {
        e.printStackTrace();
    }
}


public void calculateTotal() {
    double total = 0;
    for (customerData item : purchaseList) {
        total += item.getPrice();
    }
    purchas_total.setText(String.format("৳ %.2f", total));
}

public void resetForm() {
    purchas_brand.clear();
    purchas_productName.getItems().clear();
    purchas_productName.setValue(null);
    purchas_quantity.getValueFactory().setValue(0);
}


private void showAlert(Alert.AlertType alertType, String message) {
    Alert alert = new Alert(alertType);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}


  @FXML
private void pay() {
    if (purchaseList.isEmpty()) {
        showAlert(Alert.AlertType.WARNING, "Cart is empty. Add items before payment.");
        return;
    }

    double total = 0;
    int customerId = purchaseList.get(0).getCustomerId(); // customer_id
    Date date = new Date(System.currentTimeMillis());

    for (customerData item : purchaseList) {
        total += item.getPrice();
    }

    try {
        connect = database.connectionDb();
        String sql = "INSERT INTO customer_receipt (customer_id, total, date) VALUES (?, ?, ?)";
        prepare = connect.prepareStatement(sql);
        prepare.setInt(1, customerId);
        prepare.setDouble(2, total);
        prepare.setDate(3, new java.sql.Date(date.getTime()));
        prepare.executeUpdate();

        showAlert(Alert.AlertType.INFORMATION, "Payment successful! You can now generate receipt.");

        paymentDone = true;  // mark payment done

        // **DO NOT CLEAR purchaseList or table here**

    } catch (Exception e) {
        e.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Something went wrong during payment.");
    }
}

private boolean paymentDone = false;
   
@FXML
private void generateReceipt() {
    if (!paymentDone) {
        showAlert(Alert.AlertType.WARNING, "Please complete payment before generating receipt.");
        return;
    }

    if (purchaseList == null || purchaseList.isEmpty()) {
        showAlert(Alert.AlertType.WARNING, "No purchases to generate receipt.");
        return;
    }

    StringBuilder receipt = new StringBuilder();
    double total = 0;

    receipt.append("\t     SUPERMARKET RECEIPT\n");
    receipt.append("========================================\n");
    receipt.append("Customer ID : ").append(customerId).append("\n");
    receipt.append("Date        : ").append(new Date(System.currentTimeMillis())).append("\n");
    receipt.append("----------------------------------------\n");
    receipt.append(String.format("%-10s %-15s %-5s %-7s\n", "Brand", "Product", "Qty", "Price"));
    receipt.append("----------------------------------------\n");

    for (customerData data : purchaseList) {
        receipt.append(String.format("%-10s %-15s %-5d %-7.2f\n",
                data.getBrand(),
                data.getProductName(),
                data.getQuantity(),
                data.getPrice()));
        total += data.getPrice();
    }

    receipt.append("========================================\n");
    receipt.append(String.format("Total Amount : %.2f\n", total));
    receipt.append("========================================\n");
    receipt.append("       THANK YOU FOR SHOPPING!\n");

    try {
        String desktopPath = System.getProperty("user.home") + "/Desktop";
        String filename = desktopPath + "/receipt_" + customerId + ".txt";

        java.io.FileWriter writer = new java.io.FileWriter(filename);
        writer.write(receipt.toString());
        writer.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Receipt saved on Desktop:\n" + filename);
        alert.showAndWait();

        // Open in Notepad (Windows)
        java.awt.Desktop.getDesktop().open(new java.io.File(filename));

        System.out.println("Receipt saved and opened: " + filename);

        // Optional: clear cart and reset after receipt generation
        purchaseList.clear();
        purchas_tableView.getItems().clear();
        purchas_total.setText("৳ 0.00");
        paymentDone = false;  // reset flag for next customer

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