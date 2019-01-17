package display;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import client.*;

public class ChatWindow extends Parent {
	static String currentDestUser;
	static ColumnConstraints UserLogged = new ColumnConstraints();
    public ChatWindow() {
        TextField txt = new TextField();
        txt.setMinWidth((int)(0.3*Display.X));
        
        FlowPane convscroll = new FlowPane();        
        
        GridPane conv = new GridPane();
        conv.setMinHeight(200);
        conv.setMinWidth(200);
        Background backConv = new Background(new BackgroundFill(Color.BEIGE, new CornerRadii(2), new Insets(2)));
        conv.setBackground(backConv);
        convscroll.setPrefSize(conv.getPrefWidth(),conv.getPrefHeight()); 

        conv.add(convscroll, 0, 0);
		
		GridPane grid4 = new GridPane();
        //grid2.prefWidthProperty().bind(DisplayLogin.root.widthProperty());
        //grid2.prefHeightProperty().bind(DisplayLogin.root.heightProperty());
		grid4.setPadding(new Insets(20));
	    ColumnConstraints Conv = new ColumnConstraints();
	    Conv.setPercentWidth(58);
	    UserLogged.setPercentWidth(38);
	    ColumnConstraints Hgap1 = new ColumnConstraints();
	    Hgap1.setPercentWidth(2);
	    grid4.getColumnConstraints().addAll(UserLogged,Hgap1, Conv);
		grid4.setVgap(15);
		grid4.add(conv,2,0);
		// a un moment il faut afficher quelque part le nom de la conv affichée sur la grid
		//Label ConvOuverte = new Label(ActualConv);
//---------------------------------------------------------
		Button UseLog = new Button("Patou");
    	UseLog.setText("Patou");
        UseLog.setMinWidth(UserLogged.getPrefWidth());
        UseLog.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                /*affichage de la conversation entre user actuel et user cliqué*/
            	String ActualConv = LoginWindow.BDD.databaseAlreadyExists(currentDestUser,LoginWindow.currentLogin);
            	ResultSet myRs = LoginWindow.BDD.extractMsg(ActualConv);
            	//System.out.println(myRs.getString("snick")+" -> "+myRs.getString("dnick")+" @"+myRs.getTime("time")+" : "+myRs.getString("text"));
            	try {
					while (myRs.next()) {
						if (myRs.getString("snick").equals(LoginWindow.currentLogin)) {
						convscroll.getChildren().add(new Text(myRs.getString("text")+"( @"+myRs.getTime("time")+")").setLayoutX(100););	
						//ConvSet.add(new Text(myRs.getString("text")+"( @"+myRs.getTime("time")+")"));
					}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                /*affichage de la conversation entre user actuel et user cliquï¿½*/
            	LoginWindow.BDD.extractMsg(LoginWindow.BDD.databaseAlreadyExists(currentDestUser,LoginWindow.currentLogin));
            }
        });

       //UseLog.setStyle("-fx-border-color: #979797; -fx-border-width: 1px;"
		//		+"-fx-background-color: #c9c9c9"
		//		+ "-fx-font-size: 2em; "
		//		+"-fx-text-fill: #282828");
        grid4.add(UseLog,0,0);
//---------------------------------------------------------

    	Button btn = new Button();
    	btn.setText("send");
        btn.setMinWidth(80);
        GridPane btntxt = new GridPane();
        btn.setLayoutX(txt.getWidth()+10);
        btntxt.setHgap(10);
        btntxt.add(txt,0,0);
        btntxt.add(btn,1,0);
		grid4.add(btntxt,2,1);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String message = txt.getText();
                String conv = LoginWindow.BDD.databaseAlreadyExists(currentDestUser,LoginWindow.currentLogin);
                Client.msg(LoginWindow.currentLogin, currentDestUser, conv, message);
            }
        });
        this.getChildren().add(grid4);
    }

}
