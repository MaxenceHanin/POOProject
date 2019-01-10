package display;

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
	
	public void setBtn(Button btn, int x, int y, String txt){
        btn.setLayoutX((int)(0.85*Display.X));
        btn.setLayoutY((int)(Display.Y-35));
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
    	setBtn(btn, (int)(Display.X*0.7), Display.Y/2,"send");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                /*a definir*/
            }
        });
        
        
        Rectangle r = new Rectangle();
        setRect(r,0,0);
		r.setWidth((int)(0.3*Display.X));
		r.setHeight(Display.Y);
		
		Rectangle rmenu = new Rectangle();
        setRect(rmenu,(int)(0.3*Display.X),(int)(0.9*Display.Y));
		rmenu.setWidth((int)(0.7*Display.X));
		rmenu.setHeight((int)(0.1*Display.Y));
        
        Rectangle notif = new Rectangle();
        setRect(notif,(int)(0.3*Display.X),0);
		notif.setWidth((int)(0.7*Display.X));
		notif.setHeight((int)(0.1*Display.Y));
        
        TextField txtLog = new TextField();
        PasswordField txtPwd = new PasswordField();
        
        txtLog.setLayoutX((int)(Display.X)-530);
        txtLog.setLayoutY((int)(Display.Y-35));
        txtLog.setMinWidth((int)(0.45*Display.X));
        
        Rectangle line = new Rectangle();
        setRect(line,(int)(0.3*Display.X)-1,0);
		line.setWidth(1);
		line.setHeight(Display.Y);
		line.setFill(Color.BLACK);
       
        this.getChildren().add(r);
        this.getChildren().add(rmenu);
        this.getChildren().add(notif);
        this.getChildren().add(btn);
        this.getChildren().add(txtLog);
        this.getChildren().add(line);
    }

}
