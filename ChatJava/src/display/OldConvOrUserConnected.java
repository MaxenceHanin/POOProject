package display;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class OldConvOrUserConnected extends Button{
	public OldConvOrUserConnected(String currentDestUser) {
			Button UseLog = new Button("Pitou");
	    	UseLog.setText("Pitou");
	        //UseLog.setMinWidth(UserLogged.getPrefWidth());
	    	UseLog.setMinWidth((100/60)*ChatWindow.getGridmsg().getPrefWidth());
	        UseLog.setOnAction(new EventHandler<ActionEvent>() {
	            public void handle(ActionEvent event) {
	            	//on efface la conversation précédente de la fenetre ou s'affiche la conv courante :
	            	ChatWindow.getGridmsg().getChildren().clear();
	                /*affichage de la conversation entre user actuel et user cliqué*/
	            	String ActualConv = LoginWindow.BDD.databaseAlreadyExists(currentDestUser,LoginWindow.currentLogin);
	            	ResultSet myRs = LoginWindow.BDD.extractMsg(ActualConv);
	            	//System.out.println(myRs.getString("snick")+" -> "+myRs.getString("dnick")+" @"+myRs.getTime("time")+" : "+myRs.getString("text"));
	            	try {
						while (myRs.next()) {
							Text msg = new Text(myRs.getString("text")+"( @"+myRs.getTime("time")+")");
							msg.setWrappingWidth(ChatWindow.gridmsg.getPrefWidth()/2);
							if (myRs.getString("snick").equals(LoginWindow.currentLogin)) {
								//gridmsg.setHalignment(msg,HPos.LEFT);
								ChatWindow.gridmsg.add(msg,0,0);	
							//ConvSet.add(new Text(myRs.getString("text")+"( @"+myRs.getTime("time")+")"));
						} else {
							ChatWindow.gridmsg.add(msg,1,0);
							//gridmsg.setHalignment(msg,HPos.LEFT);
						}
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                /*affichage de la conversation entre user actuel et user selectionné*/
	            	LoginWindow.BDD.extractMsg(LoginWindow.BDD.databaseAlreadyExists(currentDestUser,LoginWindow.currentLogin));
	            }
	        });

	       //UseLog.setStyle("-fx-border-color: #979797; -fx-border-width: 1px;"
			//		+"-fx-background-color: #c9c9c9"
			//		+ "-fx-font-size: 2em; "
			//		+"-fx-text-fill: #282828");
	        
	        
	        //grid4.add(UseLog,0,0);
	}
}
