package display;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import client.Client;
import database.Access;

public class RegWindow extends Parent {
	/*private int btnWidth = 0;*/
	static GridPane grid3 = new GridPane();
	String currentLogin;
	
	public void setBtn(Button btn, String txt){
        btn.setText(txt);
        btn.setMinWidth(80);
	}

    public RegWindow(Client client, Access BDD) {
    	
    	 
        TextField txtLog = new TextField("Enter the desired login");
        txtLog.setMinWidth((int)(0.6*DisplayLogin.X));
    	Button btn = new Button();
    	setBtn(btn,"Register");
    	GridPane grid2 = new GridPane();
    	
        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	String Log = String.valueOf(txtLog.getCharacters());
            	if(client.register(Log, BDD)) {
            		currentLogin = Log;
                	StackPane rootChat = new StackPane();
                    ChatWindow chatwindow = new ChatWindow(BDD);
                    rootChat.getChildren().add(chatwindow);
                       
                    Stage stage = new Stage();
                    grid3.getChildren().add(chatwindow);
                    grid3.setAlignment(Pos.CENTER);
                    grid3.prefWidthProperty().bind(DisplayLogin.root.widthProperty());
                    grid3.prefHeightProperty().bind(DisplayLogin.root.heightProperty());
                    rootChat.getChildren().add(grid3);
                    stage.setTitle("LavaChat");
                    stage.setScene(new Scene(rootChat, 800, 600));
                    stage.show();
                    // Hide this current window 
                    ((Node)(event.getSource())).getScene().getWindow().hide();
                }
            	else {
            		Label errLog = new Label(Log + "failed to register : login already in use");
            	    grid2.add(errLog, 0, 4);
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
