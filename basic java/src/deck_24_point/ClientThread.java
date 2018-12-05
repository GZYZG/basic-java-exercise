package deck_24_point;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ClientThread extends Thread{
	Socket socket;
	client_ui client;
	BufferedReader br;
	//cardsHBox carddesk;
	
	public ClientThread(client_ui client, Socket socket) throws Exception {
		this.client = client;
		this.socket = socket;
		
		InputStream inputStream = socket.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(
				inputStream);
		this.br = new BufferedReader(inputStreamReader);
		//this.carddesk = carddesk;
		//this.client.socket = socket;
		//this.client.setSocket(socket);
		

	}
	
	public void run() {
		//用来接收服务器的消息
		try {	
				PrintWriter pw = new PrintWriter( this.socket.getOutputStream() );
			
				System.out.println("in clientthread client:"+this.client.playerName2.getText());
			
				String msg = "";
			
				System.out.println("while recieveing msg from server... ");
				//接收服务器的消息,如果无消息则会阻塞
			
				/*while(!br.ready()) {
					//pw.println("test");
					//pw.flush();
					System.out.println("br is not ready");
					Thread.sleep(500);
				}*/
				while(true) {
					pw.println("test");
					pw.flush();
				msg = br.readLine();
				System.out.println("in clientthread recieved msg: " + msg );
				if(msg.startsWith("card:")) {
					if(this.client.carddesk2.cardlist.size() > 4) {
						return;
					}
					int point = Integer.valueOf(msg.substring(5));
					System.out.println("carddesk:"+this.client.carddesk2.toString());
					this.client.addCard2Desk(point);
					System.out.println("you get a card with point "+ point);
					//this.playerName1.setText(msg);
				}else if(msg.startsWith("player:")) {
					this.client.playerName1.setText(msg);
				}
				
				}
				

		}catch (SocketException e) {
			System.out.println("与服务器连接失败...");
			e.printStackTrace();
		}catch (Exception e) {
			System.out.println("其他异常...");
			e.printStackTrace();
		}finally {
			System.out.println("clientthread exit");
		}

	}
	
}