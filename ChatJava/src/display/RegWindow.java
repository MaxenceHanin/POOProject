package display;
import java.io.IOException;

import database.Access;
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

public class RegWindow extends Parent {
	/*private int btnWidth = 0;*/
	static GridPane grid3 = new GridPane();
	
	public void setBtn(Button btn, String txt){
        btn.setText(txt);
        btn.setMinWidth(80);
	}

    public RegWindow() {
    	
    	 
        TextField txtLog = new TextField("Entrez l'identifiant souhaité");
        txtLog.setMinWidth((int)(0.6*DisplayLogin.X));
    	Button btn = new Button();
    	setBtn(btn,"Inscription");
    	GridPane grid2 = new GridPane();
    	
        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                /*test success login*/
            	String Log = String.valueOf(txtLog.getCharacters());
            	if (!LoginWindow.BDD.userExists(Log)) {
            		LoginWindow.BDD.setUserConnected(Log);
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
            	else {
            		Label errLog = new Label("L'enregistrement a échoué pour " +"'" + Log + "'"+": "+"pseudo déjà utilisé");
            	    grid2.add(errLog, 0, 4);
            		//errLog.setVisible(true);
                }
            }
        });
        
        //grid2.prefWidthProperty().bind(DisplayLogin.root.widthProperty());
        //grid2.prefHeightProperty().bind(DisplayLogin.root.heightProperty());
    	//errLog.setVisible(false);
        grid2.setPadding(new Insets(20));
        grid2.setHgap(25);
        grid2.setVgap(15);
        grid2.add(txtLog,0,1);
        grid2.add(btn, 0, 2);
        this.getChildren().add(grid2);
    }

}
