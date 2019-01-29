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


public class Display extends Application {
	static int X = 300;
	static int Y = 200;
	static StackPane root = new StackPane();


    public static void main(String[] args) {
    	//requete pour mettre base de donn�es � jour
    	Application.launch(Display.class, args);

    }
    
    @Override

    public void start(Stage primaryStage) {

        primaryStage.setTitle("Database window");

        

        Scene scene = new Scene(root, X, Y, Color.BLANCHEDALMOND); 
        GridPane grid = new GridPane();
        ConnectBDDWindow ConnectWindow = new ConnectBDDWindow();
        grid.getChildren().add(ConnectWindow);
        grid.setAlignment(Pos.CENTER);
        root.getChildren().add(grid);
        
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}