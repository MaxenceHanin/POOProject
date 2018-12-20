/*import a voir*/
import java.net.InetAddress;

public class LocalUser {
	private String localNickname;
	private InetAddress addrUser;
	private String pswd;
	public boolean registered;

	public int checkPassword(String pwd, String nickname){
		
		if ((pwd==this.pswd)&&(nickname==this.localNickname)){
			Controller.updateListConnectedUser(nickname,1);
			return 0;
		}
		return -1;
	}

	public void logout(String username){
		Controller.updateListConnectedUser(nickname,0);
	}

	public void changeNickname(String newname){
		//il manque le trynickname pour demander aux autres users si le nickname est deja pris
		if(Controller.checkUnicityNickname()){
			Controller.updateListUsedNicknames();
			this.localNickname = newname;
			Controller.updateListUsedNicknames();
		}
	}

	
	public LocalUser(InetAddress addrUser) {
		this.addrUser = addrUser;
		this.registered = false;
	}
	
	public void register(String nickname){
		while (!(registered)) loop{
			message.trynickname(nickname);
			message.isConnected();/*user connectés*/
			Controller.updateListConnectedUsers();
			Controller.updateListUsedNicknames();
				if (checkUnicityNickname(Controller.listUsedNicknames)) {
					this.registered=true;
					chatWindow.display();
				}
		}
	}
	
	public String getNickname(){
		return this.localNickname;
	}
	
	public InetAddress getAddr(){
		return this.addrUser;
	}
	
}