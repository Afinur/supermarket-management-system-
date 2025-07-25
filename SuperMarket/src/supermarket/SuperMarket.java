/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package supermarket;

//import java.awt.event.MouseEvent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Arshi 223071172
 * @author user
 */
public class SuperMarket extends Application {
    
    private double x=0;
    private double y=0;
   
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
  //      LETS DESIGN OUR LOGIN FROM USING CSS: )
  
   root.setOnMousePressed((MouseEvent event) ->{
       
       x = event.getSceneX();
       y = event.getSceneY();
       
   });
  
   root.setOnMouseDragged((MouseEvent event) ->{
       
       stage.setX(event.getScreenX() -x);
       stage.setY(event.getScreenX() -y);
       
       stage.setOpacity(.8);
    });
   
   root.setOnMouseReleased((MouseEvent event) ->{
       
          stage.setOpacity(1);
    });
   
  stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
