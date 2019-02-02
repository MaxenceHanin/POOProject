package display;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
        gridUseConv.setAlignment(Pos.TOP_RIGHT);
        //gridUseConv.setPrefSize(convscroll.getPrefWidth(),convscroll.getPrefHeight());
		grid4.add(gridUseConv,0,0);
		int i;
		int j;
		gridUseConv.getChildren().clear();//normalement inutile mais au cas ou
		
        /*affichage de la conversation entre user actuel et user cliqu�*/
    	List<String> myRs1 = BDD.extractConv(LoginWindow.currentLogin);
    	List<String> myRs2 = BDD.UsersConnected();
			for (i=0; i< myRs1.size();i++) {
				//OldConvOrUserConnected ocouc = new OldConvOrUserConnected(myRs1.get(i),BDD);
				String CurDestUser =myRs1.get(i);
				Button UseLog = new Button();
				UseLog.setText(BDD.ReturnsOtherUser(CurDestUser, LoginWindow.currentLogin));
				//UseLog.setMinWidth(UserLogged.getPrefWidth());
				UseLog.setMinWidth((100/60)*ChatWindow.getGridmsg().getPrefWidth());
				UseLog.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						//on efface la conversation pr�c�dente de la fenetre ou s'affiche la conv courante :
						ChatWindow.getGridmsg().getChildren().clear();
						/*affichage de la conversation entre user actuel et user cliqu�*/
						String ActualConv = BDD.databaseAlreadyExists(CurDestUser,LoginWindow.currentLogin);
						List<agent.HistoryMessage> myRs = BDD.extractMsg(ActualConv);
						int i;
						//System.out.println(myRs.getString("snick")+" -> "+myRs.getString("dnick")+" @"+myRs.getTime("time")+" : "+myRs.getString("text"));
						for (i=0; i< myRs.size();i++) {
							Text msg = new Text(myRs.get(i).getText()+"( @"+myRs.get(i).getTime()+")");
							msg.setWrappingWidth(ChatWindow.gridmsg.getPrefWidth()/2);
							if (myRs.get(i).getUsrSrc().equals(LoginWindow.currentLogin)) {
								//gridmsg.setHalignment(msg,HPos.LEFT);
								ChatWindow.gridmsg.add(msg,0,i);
								//ConvSet.add(new Text(myRs.getString("text")+"( @"+myRs.getTime("time")+")"));
							} else {
								ChatWindow.gridmsg.add(msg,1,i);
								//gridmsg.setHalignment(msg,HPos.LEFT);
							}
							i++;
						}
						/*affichage de la conversation entre user actuel et user selectionn�*/
						//BDD.extractMsg(BDD.databaseAlreadyExists(currentDestUser,LoginWindow.currentLogin));
					}
				});
				//myRs1.getString(6)
				//gridUseConv.add(ocouc,0,i);
				gridUseConv.add(UseLog,0,i);
			}
			for (j=0; j<myRs2.size();j++) {
				if(!(myRs1.contains(myRs2.get(j)))) {
				//OldConvOrUserConnected ocouc2 = new OldConvOrUserConnected(myRs2.get(j),BDD);
				String CurDestUser =myRs2.get(j);
				Button UseLog = new Button(CurDestUser);
				UseLog.setText("Online:"+CurDestUser);
				UseLog.setMinWidth(150);
				//UseLog.setMinWidth(UserLogged.getPrefWidth());
				UseLog.setMinWidth((100/60)*ChatWindow.getGridmsg().getPrefWidth());
				UseLog.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						//on efface la conversation pr�c�dente de la fenetre ou s'affiche la conv courante :
						ChatWindow.getGridmsg().getChildren().clear();
						/*affichage de la conversation entre user actuel et user cliqu�*/
						String ActualConv = BDD.databaseAlreadyExists(CurDestUser,LoginWindow.currentLogin);
						List<agent.HistoryMessage> myRs = BDD.extractMsg(ActualConv);
						int i;
						//System.out.println(myRs.getString("snick")+" -> "+myRs.getString("dnick")+" @"+myRs.getTime("time")+" : "+myRs.getString("text"));
						for (i=0; i< myRs.size();i++) {
							Text msg = new Text(myRs.get(i).getText()+"( @"+myRs.get(i).getTime()+")");
							msg.setWrappingWidth(ChatWindow.gridmsg.getPrefWidth()/2);
							if (myRs.get(i).getUsrSrc().equals(LoginWindow.currentLogin)) {
								//gridmsg.setHalignment(msg,HPos.LEFT);
								ChatWindow.gridmsg.add(msg,0,i);
								//ConvSet.add(new Text(myRs.getString("text")+"( @"+myRs.getTime("time")+")"));
							} else {
								ChatWindow.gridmsg.add(msg,1,i);
								//gridmsg.setHalignment(msg,HPos.LEFT);
							}
							i++;
						}
						/*affichage de la conversation entre user actuel et user selectionn�*/
						BDD.extractMsg(BDD.databaseAlreadyExists(currentDestUser,LoginWindow.currentLogin));
					}
				});
				//gridUseConv.add(ocouc2,0,i);
				gridUseConv.add(UseLog,0,i);
				i++;
			}
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
