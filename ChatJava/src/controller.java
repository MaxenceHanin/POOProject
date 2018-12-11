
public class controller {
public String ListConnectedUsers[];
public String ListUsedNicknames[];
public message history[] = new message[1000];
public boolean boolHistory;

public void updateHistory(message message){
	int i =0;
	int j=999;
	if (history[j] != null){
		while (j>0){
		history[j]=history[j-1];
		j-=1;
		}
		history[999]=message;
	}
	else {
	while (history[i]!= null){
		i+=1;
	}
	history[i]=message;
}
}

public void displayHistory(){
	
	
}
}