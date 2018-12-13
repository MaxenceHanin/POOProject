/*import a voir*/
import java.net.InetAddress;
public class User {
	private String nickname;
	public InetAddress addrUser;
	private String pswd;
	public boolean registered;

	public int checkPassword(String pwd, String username){
		
		if ((pwd==this.pswd)&&(username==this.nickname)){
			controller.updateListConnectedUser(nickname,1);
			return 0;
		}
		return -1;
	}

	public void logout(String username){
		controller.updateListConnectedUser(nickname,0);
	}

	public void changeNickname(String newname){
		if(Agent.checkUnicityNickname()){
			this.nickname = newname;
			controller.updateListUsedNicknames();
		}
	}

	
	public User(InetAddress addrUser) {
		this.addrUser = addrUser;
		this.registered = false;
	}
	
	public void register(String username){
		while (not registered) loop{
			message.trynickname(username);
			message.isConnected();/*user connectés*/
			controller.updateListConnectedUsers();
			controller.updateListUsedNicknames();
				if (checkUnicityNickname(controller.listUsedNicknames)) {
					this.registered=true;
					chatWindow.display();
				}
		}
	}
	
	public String getNickname(){
		return this.nickname;
	}
	
	public InetAddress getAddr(){
		return this.addrUser;
	}
	
}