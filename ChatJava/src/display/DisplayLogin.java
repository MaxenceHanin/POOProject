package display;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;


public class DisplayLogin extends Application {
	static int X = 400;
	static int Y = 300;


    public static void main(String[] args) {
    	Application.launch(DisplayLogin.class, args);

    }
    
    @Override

    public void start(Stage primaryStage) {

        primaryStage.setTitle("Chat application");

        Group root = new Group();

        Scene scene = new Scene(root, X, Y, Color.BLANCHEDALMOND); 
        LoginWindow logWindow = new LoginWindow();
        root.getChildren().add(logWindow);
        
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}