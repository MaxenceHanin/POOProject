package display;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import database.Access;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class OldConvOrUserConnected extends Button{
	public OldConvOrUserConnected(String currentDestUser, Access BDD) {
			Button UseLog = new Button(currentDestUser);
	    	UseLog.setText(currentDestUser);
	        //UseLog.setMinWidth(UserLogged.getPrefWidth());
	    	UseLog.setMinWidth((100/60)*ChatWindow.getGridmsg().getPrefWidth());
	        UseLog.setOnAction(new EventHandler<ActionEvent>() {
	            public void handle(ActionEvent event) {
	            	//on efface la conversation précédente de la fenetre ou s'affiche la conv courante :
	            	ChatWindow.getGridmsg().getChildren().clear();
	                /*affichage de la conversation entre user actuel et user cliqué*/
	            	String ActualConv = BDD.databaseAlreadyExists(currentDestUser,LoginWindow.currentLogin);
	            	List<agent.HistoryMessage> myRs = BDD.extractMsg(BDD.ReturnsOtherUser(ActualConv, LoginWindow.currentLogin));
	            	int i =0;
	            	//System.out.println(myRs.getString("snick")+" -> "+myRs.getString("dnick")+" @"+myRs.getTime("time")+" : "+myRs.getString("text"));
						while (!myRs.isEmpty()) {
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
	                /*affichage de la conversation entre user actuel et user selectionné*/
	            	BDD.extractMsg(BDD.databaseAlreadyExists(currentDestUser,LoginWindow.currentLogin));
	            }
	        });

	       //UseLog.setStyle("-fx-border-color: #979797; -fx-border-width: 1px;"
			//		+"-fx-background-color: #c9c9c9"
			//		+ "-fx-font-size: 2em; "
			//		+"-fx-text-fill: #282828");
	        
	        
	        //grid4.add(UseLog,0,0);
	}
}
