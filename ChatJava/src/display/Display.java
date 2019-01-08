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


public class Display extends Application {
	/*static int X = 400;
	static int Y = 300;*/
	static int X = 800;
	static int Y = 600;


    public static void main(String[] args) {
    	Application.launch(Display.class, args);

    }
    
    @Override

    public void start(Stage primaryStage) {

        primaryStage.setTitle("Chat application");

        Group root = new Group();

        Scene scene = new Scene(root, X, Y, Color.BLANCHEDALMOND); 
        /*LoginWindow logWindow = new LoginWindow();
        root.getChildren().add(logWindow);*/
        ChatWindow chatwindow = new ChatWindow();
        root.getChildren().add(chatwindow);
        
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}