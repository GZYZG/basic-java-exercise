package deck_24_point;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import deck_24_point.client_ui;
import javafx.application.Application;
import javafx.stage.Stage;
import deck_24_point.Server;

import deck_24_point.ClientThread;

public class Client{

	public static void main(String[] args) {		
		try {
			String ipstr = "169.254.198.49";
			//创建连接到指定ip的socket
			byte[] ipbytes = Server.getIpBytes(ipstr);
			Socket socket = new Socket(InetAddress.getByAddress(ipbytes), 9989);
			client_ui.socket = socket;
			System.out.println("connect to server successfully...socket:"+socket.toString());
			//System.out.println("in play client:"+client.toString());
			//开启一个子线程来接受信息，并解析
			//ClientThread thread=new ClientThread(/*client,*/ socket);
			//thread.start();
			//Thread.sleep(2000);

            //主线程用来发送信息			
			//向服务器发送指令
			//这个游戏发送的消息是玩家的请求，比如再要一张牌，或者提出摊牌的请求		
			client_ui.play(socket);
			//Application.launch(client);
			
	    }catch(Exception e){
	    	System.out.println("服务器异常");
	    }
	}


}