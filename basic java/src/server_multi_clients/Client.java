package server_multi_clients;




import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import server_multi_clients.ClientThread;

public class Client{
	/*
	 * 客户端这个程序启动后，本身是用来发送消息的，而启动的子线程用来接收消息
	 */
	
	public static void main(String[] args) {
		
		try {
			Socket socket = new Socket("localhost", 9999);
			//开启一个线程接收信息，并解析
			ClientThread thread=new ClientThread(socket);
			thread.start();
            //主线程用来发送信息	
			//br是用来从控制台读取信息，并将信息写入到与服务器连接的socket输出流中
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			PrintWriter out=new PrintWriter(socket.getOutputStream());
			while(true)
			  {
			   String s=br.readLine();
			   out.println(s);
		//         out.write(s+"\n");
			   out.flush();
			  }
	    }catch(Exception e){
	    	System.out.println("服务器异常");
	    }
	}


}