/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket;

//import com.sun.jdi.connect.spi.Connection;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
//import java.sql.PreparedStatement;

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
    private TableColumn<productData, String> addProducts_col_branchName;

    @FXML
    private TableColumn<productData, String> addProducts_col_price;

    @FXML
    private TableColumn<productData, String> addProducts_col_productID;

    @FXML
    private TableColumn<productData, String> addProducts_col_productName;

    @FXML
    private TableColumn<productData, String> addProducts_col_status;

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
    private TableView<productData> addProducts_tableView;

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
    private Button logout;

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

    //database tools
   private Connection connect;
   private PreparedStatement prepare;
   private Statement statement;
   private ResultSet result;
    
    //lets create table for products
   
   public void addProductsAdd(){
       String insertProduct = "INSERT INTO product"
               +" (product_id,brand,product_name,status,price)"
               +"VALUES(?,?,?,?,?)";
       
       
       connect =database.connectionDb();
       
       try{
           
           Alert alert;
           
           if(addProducts_productID.getText().isEmpty()||addProducts_brandName.getText().isEmpty()
                   ||addProducts_productName.getText().isEmpty()
                   ||addProducts_status.getSelectionModel().getSelectedItem()==null
                   ||addProducts_price.getText().isEmpty()){
               alert = new Alert (AlertType.ERROR);
               alert.setTitle("error message");
               alert.setHeaderText(null);
               alert.setContentText("PLEASE fill all the blank");
               alert.showAndWait();
           }
           else{
               
               String check = "SELECT product_id FROM product WHERE product_id ='"
               +addProducts_productID.getText()+"' ";
               
               statement = connect.createStatement();
               result = statement.executeQuery(check);
               
               if(result.next()){
               alert = new Alert (AlertType.ERROR);
               alert.setTitle("error message");
               alert.setHeaderText(null);
               alert.setContentText("product ID:" +addProducts_productID.getText() + "was already exist!");
               alert.showAndWait();
               }
               
               else{
              prepare = connect.prepareStatement(insertProduct);
              prepare.setString(1, addProducts_productID.getText());
               prepare.setString(2, addProducts_brandName.getText());
               prepare.setString(3, addProducts_productName.getText());
                prepare.setString(4,(String) addProducts_status.getSelectionModel().getSelectedItem());
                prepare.setString(5, addProducts_price.getText());
                
                prepare.executeUpdate();
                alert = new Alert (AlertType.INFORMATION);
               alert.setTitle("information message");
               alert.setHeaderText(null);
               alert.setContentText("sucessfully added!");
               alert.showAndWait();
               
               addProductsShowData();
                addProductsClear();
               
               }
           }
       }catch(Exception e){e.printStackTrace();}
   }
   
   
   public void addProductsUpdate() {
    String updateProduct = "UPDATE product SET brand = ?, product_name = ?, status = ?, price = ? WHERE product_id = ?";

    connect = database.connectionDb();

    try {
        Alert alert;

        if (addProducts_productID.getText().isEmpty()
                || addProducts_brandName.getText().isEmpty()
                || addProducts_productName.getText().isEmpty()
                || addProducts_status.getSelectionModel().getSelectedItem() == null
                || addProducts_price.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            prepare = connect.prepareStatement(updateProduct);
            prepare.setString(1, addProducts_brandName.getText());
            prepare.setString(2, addProducts_productName.getText());
            prepare.setString(3, (String) addProducts_status.getSelectionModel().getSelectedItem());
            prepare.setString(4, addProducts_price.getText());
            prepare.setString(5, addProducts_productID.getText());

            prepare.executeUpdate();

            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Updated!");
            alert.showAndWait();

            addProductsShowData();
            addProductsClear();
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

   public void addProductsDelete() {
    String deleteProduct = "DELETE FROM product WHERE product_id = ?";

    connect = database.connectionDb();

    try {
        Alert alert;

        if (addProducts_productID.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select the product you want to delete");
            alert.showAndWait();
        } else {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE product ID: " + addProducts_productID.getText() + "?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                prepare = connect.prepareStatement(deleteProduct);
                prepare.setString(1, addProducts_productID.getText());
                prepare.executeUpdate();

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Deleted!");
                alert.showAndWait();

                addProductsShowData();
                addProductsClear();
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

   
   public void addProductsClear(){
       addProducts_productID.setText("");
       addProducts_brandName.setText("");
       addProducts_productName.setText("");
        addProducts_status.getSelectionModel().clearSelection();
         addProducts_price.setText("");
       
   }
   
   private String[] statusList = {"Available" ," Not Available"};
   public void addProductsStatusList(){
       List<String> listS = new ArrayList<>();
       
       for (String data: statusList){
           listS.add(data);
       }
       
       ObservableList statusData = FXCollections.observableArrayList( listS);
       addProducts_status.setItems(statusData);
   }
   
   public ObservableList<productData> addProductsListData(){
       ObservableList<productData> prodList = FXCollections.observableArrayList();
       
       String sql = "SELECT * FROM product";
       connect = database.connectionDb();
       
       try{
          
           productData prod;
           
           
           prepare = connect.prepareStatement(sql);
           result = prepare.executeQuery();
           while(result.next()){
               prod = new productData(result.getString("product_id")
                       ,result.getString("brand")
                       ,result.getString("product_name")
                       ,result.getString("status")
                       ,result.getDouble("price"));
               
               prodList.add(prod);
               
           }
       }catch(Exception e){e.printStackTrace();}
       return  prodList;
   }
   
   private ObservableList<productData> addproductsList;
   public void addProductsShowData(){ //to show the data tableview
       addproductsList = addProductsListData();
       
       addProducts_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
       addProducts_col_branchName.setCellValueFactory(new PropertyValueFactory<>("brand"));
       addProducts_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
       addProducts_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
       addProducts_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
       
       addProducts_tableView.setItems( addproductsList);
   }
   
   
   public void addProductSelect(){
       productData prod = addProducts_tableView.getSelectionModel().getSelectedItem();
       int num =addProducts_tableView.getSelectionModel().getSelectedIndex();
       
       if((num -1) < -1){
           return;
       }
       
       addProducts_productID.setText(prod.getProductId());
       addProducts_brandName.setText(prod.getBrand());
       addProducts_productName.setText(prod.getProductName());
       addProducts_price.setText(String.valueOf(prod.getPrice()));
   }
   
   
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
    
    
    public void displayUsername(){
        username.setText(getData.username);
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            addProducts_form.setVisible(false);
            employees_from.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color: linear-gradient(to top right, #896b34, #b8a536);");
            addProducts_btn.setStyle("-fx-background-color:transparent");
            employees_btn.setStyle("-fx-background-color:transparent");

        } else if (event.getSource() == addProducts_btn) {
            dashboard_form.setVisible(false);
            addProducts_form.setVisible(true);
            employees_from.setVisible(false);

            addProducts_btn.setStyle("-fx-background-color: linear-gradient(to top right, #896b34, #b8a536);");
            dashboard_btn.setStyle("-fx-background-color:transparent");
            employees_btn.setStyle("-fx-background-color:transparent");
            
            addProductsShowData();
            addProductsStatusList();

        } else if (event.getSource() == employees_btn) {
            dashboard_form.setVisible(false);
            addProducts_form.setVisible(false);
            employees_from.setVisible(true);

            employees_btn.setStyle("-fx-background-color: linear-gradient(to top right, #896b34, #b8a536);");
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
      
        addProductsShowData();
        addProductsStatusList();
        displayUsername();
    }

}
