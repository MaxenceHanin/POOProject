package display;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class LoginWindow extends Parent {
	/*private int btnWidth = 0;*/
	
	public void setBtn(Button btn, int x, int y, String txt){
        btn.setLayoutX((int)(0.85*DisplayLogin.X)-90);
        btn.setLayoutY((int)(0.6*DisplayLogin.Y));
        btn.setText(txt);
        btn.setMinWidth(80);
	}
	
	public void setRect(Rectangle r, int x, int y){
		r.setX(x);
		r.setY(y);
		r.setWidth((int)(0.7*DisplayLogin.X));
		r.setHeight((int)(0.4*DisplayLogin.Y));
		r.setArcWidth(20);
		r.setArcHeight(20);
		r.setFill(Color.BEIGE);
	}

    public LoginWindow() {

    	Button btn = new Button();
    	setBtn(btn, (int)(DisplayLogin.X*0.7), DisplayLogin.Y/2,"login");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                /*a definir*/
            	
            	Group root = new Group();
                	ChatWindow chatwindow = new ChatWindow();
                    root.getChildren().add(chatwindow);
                    
                    Stage stage = new Stage();
                    stage.setTitle("Chat Window");
                    stage.setScene(new Scene(root, 800, 600));
                    stage.show();
                    // Hide this current window 
                    ((Node)(event.getSource())).getScene().getWindow().hide();
            }
        });
        
        
        Rectangle r = new Rectangle();
        setRect(r,(int)(DisplayLogin.X*0.15),(int)(DisplayLogin.Y*0.3));
        
        TextField txtLog = new TextField();
        PasswordField txtPwd = new PasswordField();
        
        txtLog.setLayoutX((int)(0.2*DisplayLogin.X));
        txtLog.setLayoutY((int)(btn.getLayoutY()-65));
        txtLog.setMinWidth((int)(0.6*DisplayLogin.X));
        txtPwd.setLayoutX((int)(0.2*DisplayLogin.X));
        txtPwd.setLayoutY((int)(btn.getLayoutY()-35));
        txtPwd.setMinWidth((int)(0.6*DisplayLogin.X));
        
        this.getChildren().add(r);
        this.getChildren().add(btn);
        this.getChildren().add(txtLog);
        this.getChildren().add(txtPwd);
    }

}
