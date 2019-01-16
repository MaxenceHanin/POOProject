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

public class LoginWindow extends Parent {
	/*private int btnWidth = 0;*/
	static GridPane grid3 = new GridPane();
	
	public void setBtn(Button btn, String txt){
        btn.setText(txt);
        btn.setMinWidth(80);
	}

    public LoginWindow() {
    	
    	Access BDD = new Access();
        TextField txtLog = new TextField("Entrez votre identifiant");
        Button btnReg = new Button();
        setBtn(btnReg,"Pas encore inscrit ? inscivez-vous en cliquant");
        txtLog.setMinWidth((int)(0.6*DisplayLogin.X));
        btnReg.setMinWidth((int)(0.6*DisplayLogin.X));

    	Button btn = new Button();
    	setBtn(btn,"login");
    	GridPane grid2 = new GridPane();
    	
    	btnReg.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	StackPane rootReg = new StackPane();
            	Stage stage = new Stage();
            	stage.setTitle("Register Window");      

                Scene scene = new Scene(rootReg, DisplayLogin.X, DisplayLogin.Y, Color.BLANCHEDALMOND); 
                GridPane grid = new GridPane();
                RegWindow RegWindow = new RegWindow();
                grid.getChildren().add(RegWindow);
                grid.setAlignment(Pos.CENTER);
                rootReg.getChildren().add(grid);
                
                stage.setScene(scene);
                stage.show();
                
                // Hide this current window 
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }
        });
    	
        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                /*test success login*/
            	String Log = String.valueOf(txtLog.getCharacters());
                if (!(BDD.isConnected(Log))&& BDD.userExists(Log)) {
                	BDD.setUserConnected(Log);
                	ChatWindow chatwindow = new ChatWindow();
                    //rootChat.getChildren().add(chatwindow);
                    
                    Stage stage = new Stage();
                    grid3.getChildren().add(chatwindow);
                    grid3.setAlignment(Pos.CENTER);
                    //grid3.prefWidthProperty().bind(DisplayLogin.root.widthProperty());
                    //grid3.prefHeightProperty().bind(DisplayLogin.root.heightProperty());
                    stage.setTitle("Chat Window");
                    stage.setScene(new Scene(grid3, 600, 400));
                    stage.show();
                    // Hide this current window 
                    ((Node)(event.getSource())).getScene().getWindow().hide();
            }
                else {
                	Label errLog = new Label("Le login a échoué pour " +"'" + Log + "'");
                	grid2.add(errLog, 0, 4);
                	
                }
            }
        });
        
        //grid2.prefWidthProperty().bind(DisplayLogin.root.widthProperty());
        //grid2.prefHeightProperty().bind(DisplayLogin.root.heightProperty());
        grid2.setPadding(new Insets(20));
        grid2.setHgap(25);
        grid2.setVgap(15);
        grid2.add(txtLog,0,1);
        grid2.add(btnReg, 0, 2);
        grid2.add(btn, 0, 3);
        this.getChildren().add(grid2);
    }

}
