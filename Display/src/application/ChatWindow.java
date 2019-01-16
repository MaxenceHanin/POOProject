package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class ChatWindow extends Parent {
	
	static int X = 800;
	static int Y = 600;
	
	public void setBtn(Button btn, int x, int y, String txt){
        btn.setLayoutX((int)(0.85*X));
        btn.setLayoutY((int)(Y-35));
        btn.setText(txt);
        btn.setMinWidth(80);
	}
	
	public void setRect(Rectangle r, int x, int y){
		r.setX(x);
		r.setY(y);
		/*r.setArcWidth(20);
		r.setArcHeight(20);*/
		r.setFill(Color.GREY);
	}

    public ChatWindow() {

    	Button btn = new Button();
    	setBtn(btn, (int)(X*0.7), Y/2,"send");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                /*a definir*/
            }
        });
        
        
        Rectangle r = new Rectangle();
        setRect(r,0,0);
		r.setWidth((int)(0.3*X));
		r.setHeight(Y);
		
		Rectangle rmenu = new Rectangle();
        setRect(rmenu,(int)(0.3*X),(int)(0.9*Y));
		rmenu.setWidth((int)(0.7*X));
		rmenu.setHeight((int)(0.1*Y));
        
        Rectangle notif = new Rectangle();
        setRect(notif,(int)(0.3*X),0);
		notif.setWidth((int)(0.7*X));
		notif.setHeight((int)(0.1*Y));
        
        TextField txtLog = new TextField();
        PasswordField txtPwd = new PasswordField();
        
        txtLog.setLayoutX((int)(X)-530);
        txtLog.setLayoutY((int)(Y-35));
        txtLog.setMinWidth((int)(0.45*X));
        
        Rectangle line = new Rectangle();
        setRect(line,(int)(0.3*X)-1,0);
		line.setWidth(1);
		line.setHeight(Y);
		line.setFill(Color.BLACK);
       
        this.getChildren().add(r);
        this.getChildren().add(rmenu);
        this.getChildren().add(notif);
        this.getChildren().add(btn);
        this.getChildren().add(txtLog);
        this.getChildren().add(line);
    }

}
