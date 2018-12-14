package display;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ChatWindow extends Parent {
	
	public void setBtn(Button btn, int x, int y, String txt){
        btn.setLayoutX((int)(0.85*Display.X)-90);
        btn.setLayoutY((int)(0.6*Display.Y));
        btn.setText(txt);
        btn.setMinWidth(80);
	}
	
	public void setRect(Rectangle r, int x, int y){
		r.setX(x);
		r.setY(y);
		r.setWidth((int)(0.3*Display.X));
		r.setHeight(Display.Y);
		r.setArcWidth(20);
		r.setArcHeight(20);
		r.setFill(Color.BEIGE);
	}

    public ChatWindow() {

    	Button btn = new Button();
    	setBtn(btn, (int)(Display.X*0.7), Display.Y/2,"login");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                /*a definir*/
            }
        });
        
        
        Rectangle r = new Rectangle();
        setRect(r,0,0);
        
        TextField txtLog = new TextField();
        PasswordField txtPwd = new PasswordField();
        
        txtLog.setLayoutX((int)(Display.X)-500);
        txtLog.setLayoutY((int)(Display.Y-35));
        txtLog.setMinWidth((int)(0.6*Display.X));
        
        this.getChildren().add(r);
        this.getChildren().add(btn);
        this.getChildren().add(txtLog);
    }

}
