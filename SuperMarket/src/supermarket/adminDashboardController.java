/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket;

//import com.sun.jdi.connect.spi.Connection;
import java.awt.Color;
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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
//import java.sql.PreparedStatement;
import java.sql.Date;

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
    private AreaChart<String,Number> dashboard_chart;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Label dashboard_totalIncome;

    @FXML
    private Button employees_btn;

    @FXML
    private Button employees_clearBtn;

    @FXML
    private TableColumn<employeeData, String> employees_col_date;

    @FXML
    private TableColumn<employeeData, String> employees_col_employeeID;

    @FXML
    private TableColumn<employeeData, String> employees_col_firstName;

    @FXML
    private TableColumn<employeeData, String> employees_col_gender;

    @FXML
    private TableColumn<employeeData, String> employees_col_lastName;

    @FXML
    private TableColumn<employeeData, String> employees_col_password;

    @FXML
    private Button employees_deleteBtn;

    @FXML
    private TextField employees_employeeID;

    @FXML
    private TextField employees_firstName;

    @FXML
    private AnchorPane employees_from;

    @FXML
    private ComboBox<String> employees_gender;

    @FXML
    private TextField employees_lastName;

    @FXML
    private TextField employees_password;

    @FXML
    private Button employees_saveBtn;

    @FXML
    private TableView<employeeData> employees_tableView;

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
    @FXML
    private Button expiringSoonButton;
    @FXML
    private TableColumn<productData, Date> addProducts_col_expirationDate;
    
    //lets create table for products
   
    @FXML
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
   
   
    @FXML
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

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            // Get values
            String productId = addProducts_productID.getText().trim();
            String brand = addProducts_brandName.getText().trim();
            String productName = addProducts_productName.getText().trim();
            String status = (String) addProducts_status.getSelectionModel().getSelectedItem();
            double price = Double.parseDouble(addProducts_price.getText().trim());

            // ✅ Debug Product ID
            System.out.println("👉 DEBUG: Product ID to update = '" + productId + "'");

            prepare = connect.prepareStatement(updateProduct);
            prepare.setString(1, brand);
            prepare.setString(2, productName);
            prepare.setString(3, status);
            prepare.setDouble(4, price);
            prepare.setString(5, productId);

            int rowsAffected = prepare.executeUpdate();

            if (rowsAffected > 0) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Updated!");
                alert.showAndWait();

                addProductsShowData();
                addProductsClear();
            } else {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("⚠ No product matched the given Product ID.");
                alert.showAndWait();
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}



    @FXML
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


   
    @FXML
   public void addProductsClear(){
       addProducts_productID.setText("");
       addProducts_brandName.setText("");
       addProducts_productName.setText("");
        addProducts_status.getSelectionModel().clearSelection();
         addProducts_price.setText("");
       
   }
   
   private String[] statusList = {"Available" ," Not Available"};
    @FXML
   public void addProductsStatusList(){
       List<String> listS = new ArrayList<>();
       
       for (String data: statusList){
           listS.add(data);
       }
       
       ObservableList statusData = FXCollections.observableArrayList( listS);
       addProducts_status.setItems(statusData);
   }
   
   public void addProductsSearch() {
    FilteredList<productData> filter = new FilteredList<>(addproductsList, e -> true);

    addProducts_search.textProperty().addListener((Observable, oldValue, newValue) -> {
        filter.setPredicate(product -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String searchKey = newValue.toLowerCase();

            if (product.getProductId().toLowerCase().contains(searchKey)) {
                return true;
            } else if (product.getBrand().toLowerCase().contains(searchKey)) {
                return true;
            } else if (product.getProductName().toLowerCase().contains(searchKey)) {
                return true;
            } else if (product.getStatus().toLowerCase().contains(searchKey)) {
                return true;
            } else if (String.valueOf(product.getPrice()).contains(searchKey)) {
                return true;
            }

            return false;
        });
    });

    SortedList<productData> sortedList = new SortedList<>(filter);
    sortedList.comparatorProperty().bind(addProducts_tableView.comparatorProperty());
    addProducts_tableView.setItems(sortedList);
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
              prod = new productData(
                result.getString("product_id"),
                result.getString("brand"),
                result.getString("product_name"),
                result.getString("status"),
                result.getDouble("price"),
                result.getDate("expiration_date") // ✅
            );
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
       addProducts_col_expirationDate.setCellValueFactory(new PropertyValueFactory<>("expirationDate")); 
       addProducts_tableView.setItems( addproductsList);
       addProductsSearch();
   }
   
   
    @FXML
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
   
   
   
   
   
   public ObservableList<employeeData> employeesListData() {
    ObservableList<employeeData> emData = FXCollections.observableArrayList();

    String sql = "SELECT * FROM employee";
    connect = database.connectionDb();

    try {
        
        employeeData employeeD;
        prepare = connect.prepareStatement(sql);
        result = prepare.executeQuery();

        

        while (result.next()) {
            employeeD = new employeeData(
                result.getString("employee_id"),
                result.getString("password"),
                result.getString("firstName"),
                result.getString("lastName"),
                result.getString("gender"),
                result.getDate("date"));
                    emData.add(employeeD);
        }

    } catch (Exception e) {e.printStackTrace(); }
    return emData;
}
   
    private ObservableList<employeeData> employeesList;
   public void employeesShowData(){ //to show the data tableview
      employeesList = employeesListData();
       
       employees_col_employeeID.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
       employees_col_password.setCellValueFactory(new PropertyValueFactory<>("password"));
       employees_col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        employees_col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
       employees_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
       employees_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
       
       employees_tableView.setItems(employeesList);
       
      
   }
   
    @FXML
  public void employeesSelect() {
    employeeData employeeD = employees_tableView.getSelectionModel().getSelectedItem();
    int num = employees_tableView.getSelectionModel().getSelectedIndex();

    if (employeeD == null || num < 0) {
        System.out.println("No row selected.");
        return;
    }

    // Fill all form fields with selected row's values
    employees_employeeID.setText(employeeD.getEmployeeId().trim());
    employees_password.setText(employeeD.getPassword().trim());
    employees_firstName.setText(employeeD.getFirstName().trim());
    employees_lastName.setText(employeeD.getLastName().trim());
    employees_gender.getSelectionModel().select(employeeD.getGender().trim());

    System.out.println("Selected employee ID: " + employeeD.getEmployeeId());
}





   public void employeesGenderList() {
    List<String> genderL = new ArrayList<>();

    genderL.add("Male");
    genderL.add("Female");

    ObservableList listG = FXCollections.observableArrayList(genderL);
    employees_gender.setItems(listG);
}

    @FXML
   public void employeesAdd() {
    String insertEmp = "INSERT INTO employee (employee_id, password, firstName, lastName, gender, date) VALUES (?, ?, ?, ?, ?, ?)";

    connect = database.connectionDb();

    try {
        Alert alert;

        if (employees_employeeID.getText().isEmpty()
                || employees_password.getText().isEmpty()
                || employees_firstName.getText().isEmpty()
                || employees_lastName.getText().isEmpty()
                || employees_gender.getSelectionModel().getSelectedItem() == null) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all fields!");
            alert.showAndWait();
        } else {
            // Date now
            java.sql.Date date = new java.sql.Date(System.currentTimeMillis());

            prepare = connect.prepareStatement(insertEmp);
            prepare.setString(1, employees_employeeID.getText());
            prepare.setString(2, employees_password.getText());
            prepare.setString(3, employees_firstName.getText());
            prepare.setString(4, employees_lastName.getText());
            prepare.setString(5, (String) employees_gender.getSelectionModel().getSelectedItem());
            prepare.setDate(6, date);

            prepare.executeUpdate();

            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Employee added successfully!");
            alert.showAndWait();

            employeesShowData(); // refresh table
            employeesClear();    // clear form
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    @FXML
   public void employeesClear() {
    employees_employeeID.setText("");
    employees_password.setText("");
    employees_firstName.setText("");
    employees_lastName.setText("");
    employees_gender.getSelectionModel().clearSelection();
}

    @FXML
 public void updateEmployee() {
    String sql = "UPDATE employee SET password = ?, firstName = ?, lastName = ?, gender = ? WHERE employee_id = ?";

    connect = database.connectionDb();

    try {
        Alert alert;

        // Check if any field is empty
        if (employees_employeeID.getText().isEmpty()
                || employees_password.getText().isEmpty()
                || employees_firstName.getText().isEmpty()
                || employees_lastName.getText().isEmpty()
                || employees_gender.getSelectionModel().getSelectedItem() == null) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all fields before updating!");
            alert.showAndWait();
            return;
        }

        // Prepare SQL statement
        prepare = connect.prepareStatement(sql);
        prepare.setString(1, employees_password.getText().trim());
        prepare.setString(2, employees_firstName.getText().trim());
        prepare.setString(3, employees_lastName.getText().trim());
        prepare.setString(4, employees_gender.getSelectionModel().getSelectedItem().trim());
        prepare.setString(5, employees_employeeID.getText().trim()); // Important: trimmed ID

        // Execute update
        int rows = prepare.executeUpdate();
        System.out.println("Rows affected: " + rows); // Debugging

        if (rows > 0) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Employee updated successfully!");
            alert.showAndWait();

            employeesShowData();  // Refresh table
            employeesClear();     // Clear input fields
        } else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Update Failed");
            alert.setHeaderText(null);
            alert.setContentText("No record found with the given Employee ID.");
            alert.showAndWait();
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}
 
    @FXML
 public void employeesDelete() {
    String deleteSql = "DELETE FROM employee WHERE employee_id = ?";

    connect = database.connectionDb();

    try {
        Alert alert;

        if (employees_employeeID.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select an employee to delete!");
            alert.showAndWait();
            return;
        }

        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete Employee ID: " + employees_employeeID.getText() + "?");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK) {
            prepare = connect.prepareStatement(deleteSql);
            prepare.setString(1, employees_employeeID.getText().trim());
            int affectedRows = prepare.executeUpdate();

            if (affectedRows > 0) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Deleted");
                alert.setHeaderText(null);
                alert.setContentText("Employee deleted successfully!");
                alert.showAndWait();

                employeesShowData();  // Refresh table after deletion
                employeesClear();     // Clear input fields
            } else {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Delete Failed");
                alert.setHeaderText(null);
                alert.setContentText("No employee found with this ID.");
                alert.showAndWait();
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

  
   
   public void dashboardDisplayCounts() {
    connect = database.connectionDb();

    // Use 'id' to count employees
    String sqlActiveEmployees = "SELECT COUNT(id) FROM employee";
  String sqlTodayIncome = "SELECT SUM(price) FROM customer WHERE date = CURDATE()";
   String sqlTotalIncome = "SELECT SUM(price) FROM customer";

    try {
        // --- Active Employees ---
        prepare = connect.prepareStatement(sqlActiveEmployees);
        result = prepare.executeQuery();
        if (result.next()) {
            int count = result.getInt(1);
            System.out.println("Active Employee (by ID): " + count);
            dashboard_activeEmployee.setText(String.valueOf(count));
        }
        // --- Today's Income ---
        prepare = connect.prepareStatement(sqlTodayIncome);
        result = prepare.executeQuery();
        if (result.next()) {
            Object todayObj = result.getObject(1);
            double todayIncome = (todayObj != null) ? ((Number) todayObj).doubleValue() : 0.0;
            System.out.println("Today's Income: " + todayIncome);
            dashboard_IncomToday.setText("$" + String.format("%.2f", todayIncome));
        }

        // --- Total Income ---
        prepare = connect.prepareStatement(sqlTotalIncome);
        result = prepare.executeQuery();
        if (result.next()) {
            Object totalObj = result.getObject(1);
            double totalIncome = (totalObj != null) ? ((Number) totalObj).doubleValue() : 0.0;
            System.out.println("Total Income: " + totalIncome);
            dashboard_totalIncome.setText("$" + String.format("%.2f", totalIncome));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

   
   

  public void loadIncomeChart() {
    String sql = "SELECT date, SUM(total) AS total_income FROM customer_receipt GROUP BY date ORDER BY date ASC";

    connect = database.connectionDb();

    try {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Daily Income");

        prepare = connect.prepareStatement(sql);
        result = prepare.executeQuery();

        while (result.next()) {
            String date = result.getString("date");
            double income = result.getDouble("total_income");

            series.getData().add(new XYChart.Data<>(date, income));
        }

        dashboard_chart.getData().clear();
        dashboard_chart.getData().add(series);

    } catch (Exception e) {
        e.printStackTrace();
    }
}

  
 public void defaultNavBtn(){
     dashboard_btn.setStyle("-fx-background-color: linear-gradient(to top right, #896b34, #b8a536);");
 }
 
 
 public ObservableList<productData> getExpiringProducts() {
    ObservableList<productData> list = FXCollections.observableArrayList();
    String sql = "SELECT * FROM product WHERE expiration_date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 7 DAY)";

    connect = database.connectionDb();

    try {
        prepare = connect.prepareStatement(sql);
        result = prepare.executeQuery();

        while (result.next()) {
            list.add(new productData(
                result.getString("product_id"),
                result.getString("brand"),
                result.getString("product_name"),
                result.getString("status"),
                result.getDouble("price"),
                result.getDate("expiration_date")
            ));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

 
 public void showExpiringSoonProducts(ActionEvent event) {
    ObservableList<productData> expiringList = getExpiringProducts();

    addProducts_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
    addProducts_col_branchName.setCellValueFactory(new PropertyValueFactory<>("brand"));
    addProducts_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
    addProducts_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
    addProducts_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
    addProducts_col_expirationDate.setCellValueFactory(new PropertyValueFactory<>("expirationDate")); // ✅

    addProducts_tableView.setItems(expiringList);
}

 
 
 
   
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
    
    
    public void displayUsername(){
        username.setText(getData.username);
    }

    @FXML
    public void switchForm(ActionEvent event) {
        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            addProducts_form.setVisible(false);
            employees_from.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color: linear-gradient(to top right, #896b34, #b8a536);");
            addProducts_btn.setStyle("-fx-background-color:transparent");
            employees_btn.setStyle("-fx-background-color:transparent");
            
            dashboardDisplayCounts();
             loadIncomeChart();

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
            
            employeesShowData();

        }
    }


    @FXML
    public void close() {
        System.exit(0);
    }

    @FXML
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
        employeesShowData();
        employeesGenderList();
        employees_tableView.setOnMouseClicked(event -> {
        employeesSelect();
        dashboardDisplayCounts();
         loadIncomeChart();
         defaultNavBtn();
    });
       
       
    }

    
}

