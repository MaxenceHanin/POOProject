package display;
import database.Access;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

import client.*;

public class ConnectBDDWindow extends Parent {
	/*private int btnWidth = 0;*/
	static GridPane grid3 = new GridPane();
	static String currentLogin;
	
	public void setBtn(Button btn, String txt){
        btn.setText(txt);
        btn.setMinWidth(80);
	}

    public ConnectBDDWindow() {
    	
        TextField txtLog = new TextField("Enter your database login");
        PasswordField pswd = new PasswordField();
        txtLog.setMinWidth((int)(0.6*Display.X));
        pswd.setMinWidth((int)(0.6*Display.X));

    	Button btn = new Button();
    	setBtn(btn,"login");
    	GridPane grid2 = new GridPane();
    	
        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {             
            	String Log = String.valueOf(txtLog.getCharacters());
            	String pwd = String.valueOf(pswd.getCharacters());
            		try {
            			Access BDD = new Access(Log,pwd);
            			StackPane rootReg = new StackPane();
                    	Stage stage = new Stage();
                    	stage.setTitle("Login Window");      

                        Scene scene = new Scene(rootReg, Display.X, Display.Y, Color.BLANCHEDALMOND); 
                        GridPane grid = new GridPane();
                        LoginWindow LogWindow = new LoginWindow(BDD);
                        grid.getChildren().add(LogWindow);
                        grid.setAlignment(Pos.CENTER);
                        rootReg.getChildren().add(grid);
                        
                        stage.setScene(scene);
                        stage.show();
                        
                        // Hide this current window 
                        ((Node)(event.getSource())).getScene().getWindow().hide();
            		} catch(SQLException e) {
            			Label err = new Label("Could not connect to //localhost:3306/chat_app");
            			grid2.add(err, 1, 4);
            		}
            }
        });
        
        //grid2.prefWidthProperty().bind(Display.root.widthProperty());
        //grid2.prefHeightProperty().bind(Display.root.heightProperty());
        grid2.setPadding(new Insets(20));
        grid2.setHgap(25);
        grid2.setVgap(15);
        Label loginlbl = new Label("Login");
		grid2.add(loginlbl, 0, 1);
        Label password = new Label("�Password");
		grid2.add(password, 0, 2);
        grid2.add(txtLog,1,1);
        grid2.add(pswd,1,2);
        grid2.add(btn, 1, 3);
        this.getChildren().add(grid2);
    }

}
