package Server;

import java.util.ArrayList;

public class Tuple{
	
	String log;
	String pwd;

    Tuple(String s1, String s2, ArrayList<Tuple> arr){

        Tuple t1= new Tuple(s1,s2);
        arr.add(t1);
    }
    
    Tuple(String s1, String s2){

        Tuple t1= new Tuple(s1,s2);
        t1.log=s1;
        t1.pwd=s2;
    }
    
    
}