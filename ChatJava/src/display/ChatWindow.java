package display;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
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
import database.Access;

public class ChatWindow extends Parent {
	static String currentDestUser;
    static GridPane gridmsg = new GridPane();
    public ChatWindow(Access BDD) {
        TextField txt = new TextField();
        txt.setMinWidth((int)(0.3*Display.X));
        
        FlowPane convscroll = new FlowPane();        
        
        //partie affichage de la conv
        GridPane conv = new GridPane();
        conv.setMinHeight(200);
        conv.setMinWidth(txt.getWidth()+90);
        Background backConv = new Background(new BackgroundFill(Color.BEIGE, new CornerRadii(2), new Insets(2)));
        conv.setBackground(backConv);
        convscroll.setPrefSize(conv.getPrefWidth(),conv.getPrefHeight()); 

        gridmsg.setPrefSize(convscroll.getPrefWidth(),convscroll.getPrefHeight());
        convscroll.getChildren().add(gridmsg);

        conv.add(convscroll, 0, 0);
		
        //grid general qui contient le reste
		GridPane grid4 = new GridPane();
        //grid2.prefWidthProperty().bind(DisplayLogin.root.widthProperty());
        //grid2.prefHeightProperty().bind(DisplayLogin.root.heightProperty());
		grid4.setPadding(new Insets(20));
	    ColumnConstraints Conv = new ColumnConstraints();
	    ColumnConstraints UserLogged = new ColumnConstraints();
	    Conv.setPercentWidth(58);
	    UserLogged.setPercentWidth(38);
	    ColumnConstraints Hgap1 = new ColumnConstraints();
	    Hgap1.setPercentWidth(2);
	    grid4.getColumnConstraints().addAll(UserLogged,Hgap1, Conv);
		grid4.setVgap(15);
		grid4.add(conv,2,0);
		
		//partie affichage des conversations existtantes entre utilisateur local et distant
        //et utilisateurs en ligne (pour pouvoir entamer conversation
        GridPane gridUseConv = new GridPane();
        gridUseConv.setPrefSize(convscroll.getPrefWidth(),convscroll.getPrefHeight());
		grid4.add(gridUseConv,0,0);
		int i = 0;
		int j = 0;
		gridUseConv.getChildren().clear();//normalement inutile mais au cas ou
		
        /*affichage de la conversation entre user actuel et user cliqu�*/
    	ResultSet myRs1 = BDD.extractConv(LoginWindow.currentLogin);
    	ResultSet myRs2 = BDD.UsersConnected();
    	try {
			while (myRs1.next()) {
				OldConvOrUserConnected ocouc = new OldConvOrUserConnected(myRs1.getString(1),BDD);
				//myRs1.getString(6)
				gridUseConv.add(ocouc,0,i);
				i++;
			}
			while (myRs2.next()) {
				OldConvOrUserConnected ocouc = new OldConvOrUserConnected(myRs2.getString("nickname"),BDD);
				gridUseConv.add(ocouc,0,i);
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//------------------------------------------------
		
		
		// a un moment il faut afficher quelque part le nom de la conv affich�e sur la grid
		//Label ConvOuverte = new Label(ActualConv);


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

                String conv = BDD.databaseAlreadyExists(currentDestUser,LoginWindow.currentLogin);
                //Client.msg(LoginWindow.currentLogin, currentDestUser, conv, message);

            }
        });
        this.getChildren().add(grid4);
    }

    public static GridPane getGridmsg() {
    	
    	return gridmsg;
    }
}
