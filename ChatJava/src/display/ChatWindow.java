package display;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class ChatWindow extends Parent {
	

    public ChatWindow() {

    	Button btn = new Button();
    	btn.setText("send");
        btn.setMinWidth(80);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                /*a definir*/
            }
        });
		
	
        TextField txt = new TextField();
        txt.setMinWidth((int)(0.3*Display.X));
        
        StackPane btntxt = new StackPane();
        btntxt.getChildren().add(txt);
        btntxt.getChildren().add(btn);
   
		
		GridPane grid4 = new GridPane();
        //grid2.prefWidthProperty().bind(DisplayLogin.root.widthProperty());
        //grid2.prefHeightProperty().bind(DisplayLogin.root.heightProperty());
		grid4.setPadding(new Insets(20));
	    ColumnConstraints Conv = new ColumnConstraints();
	    Conv.setPercentWidth(58);
	    ColumnConstraints UserLogged = new ColumnConstraints();
	    UserLogged.setPercentWidth(38);
	    ColumnConstraints Hgap1 = new ColumnConstraints();
	    Hgap1.setPercentWidth(2);
	    grid4.getColumnConstraints().addAll(UserLogged,Hgap1, Conv);
		grid4.setVgap(15);
		grid4.add(btntxt,2,1);

        this.getChildren().add(grid4);
    }

}
