package display;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;


public class DisplayLogin extends Application {
	static int X = 400;
	static int Y = 300;
	static StackPane root = new StackPane();


    public static void main(String[] args) {
    	//requete pour mettre base de données à jour
    	Application.launch(DisplayLogin.class, args);

    }
    
    @Override

    public void start(Stage primaryStage) {

        primaryStage.setTitle("Chat application");

        

        Scene scene = new Scene(root, X, Y, Color.BLANCHEDALMOND); 
        GridPane grid = new GridPane();
        LoginWindow logWindow = new LoginWindow();
        grid.getChildren().add(logWindow);
        grid.setAlignment(Pos.CENTER);
        root.getChildren().add(grid);
        
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}