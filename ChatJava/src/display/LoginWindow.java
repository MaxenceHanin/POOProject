package display;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class LoginWindow extends Parent {
	/*private int btnWidth = 0;*/
	static GridPane grid3 = new GridPane();
	
	public void setBtn(Button btn, String txt){
        btn.setText(txt);
        btn.setMinWidth(80);
	}

    public LoginWindow() {
    	
    	 
        TextField txtLog = new TextField("Entrez votre identifiant");
        PasswordField txtPwd = new PasswordField();
        txtLog.setMinWidth((int)(0.6*DisplayLogin.X));
        txtPwd.setMinWidth((int)(0.6*DisplayLogin.X));

    	Button btn = new Button();
    	setBtn(btn,"login");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                /*test success login*/
            	StringBuilder sb = new StringBuilder(txtLog.getCharacters().length());
                sb.append(txtLog);
                if ("requette pour chercher le login entré".equals(sb.toString())) {
            	
            	StackPane rootChat = new StackPane();
                	ChatWindow chatwindow = new ChatWindow();
                    rootChat.getChildren().add(chatwindow);
                    
                    Stage stage = new Stage();
                    grid3.getChildren().add(chatwindow);
                    grid3.setAlignment(Pos.CENTER);
                    grid3.prefWidthProperty().bind(DisplayLogin.root.widthProperty());
                    grid3.prefHeightProperty().bind(DisplayLogin.root.heightProperty());
                    rootChat.getChildren().add(grid3);
                    stage.setTitle("Chat Window");
                    stage.setScene(new Scene(rootChat, 800, 600));
                    stage.show();
                    // Hide this current window 
                    ((Node)(event.getSource())).getScene().getWindow().hide();
            }
                else if ("Entrez votre identifiant".equals(sb.toString()) | "".equals(sb.toString())) {
                	
                }
            }
        });
        
        GridPane grid2 = new GridPane();
        //grid2.prefWidthProperty().bind(DisplayLogin.root.widthProperty());
        //grid2.prefHeightProperty().bind(DisplayLogin.root.heightProperty());
        grid2.setPadding(new Insets(20));
        grid2.setHgap(25);
        grid2.setVgap(15);
        grid2.add(txtLog,0,1);
        grid2.add(txtPwd, 0, 2);
        grid2.add(btn, 0, 3);
        this.getChildren().add(grid2);
    }

}
