package goBackNFrame;

import java.util.ArrayList;

/*
public class dataStructure{
	static ArrayList<String> frameresult = new ArrayList<>();
	
}*/

class DataProcess{
	public static String RandomResult() {
		float result = (float) Math.random();
		if(result <= 0.6)
			return "ACK";
		if(result > 0.6 && result <= 0.95)
			return "bad";
		else if(result > 0.95)
			return "miss";
		return "";
	}
	
}


class Frame{
	int seq;
	String content;
	
	
	public Frame(int seq, String content) {
		this.seq = seq;
		this.content = content;
	}
	
	public String toString() {
		return "[seq:"+this.seq+" content:"+this.content+"]";
	}
}