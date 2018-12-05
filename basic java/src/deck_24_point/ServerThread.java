package deck_24_point;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import deck_24_point.client_ui;

public class ServerThread extends Thread{
	
	public ArrayList<Socket> clientlist;
	public final int clientIdx;
	public String playername = "null";
	public BufferedReader br;//br 用于从连接客户端的socket里读取信息
	PrintWriter pw0 ;
	PrintWriter pw1 ;
	int[] results;
	boolean[] submit;
	
	public ServerThread(ArrayList<Socket> clientlist, int[] result, boolean[] submit, int clientIdx) throws IOException {
		this.clientlist = clientlist;
		this.results = result;
		this.submit = submit;
		this.clientIdx = clientIdx;
		this.br = new BufferedReader(new InputStreamReader( clientlist.get(clientIdx).getInputStream()));
	}
	
	public void run() {
		
		while(true) {
		try {
			
			
			while( clientlist.size() < 2 ){
				//如果只有一个用户登录了则会一直阻塞在这里等另外一个用户登录
				System.out.println(" is waiting for another client");
				Thread.sleep(2000);
			}
			
			
			pw0 = new PrintWriter( clientlist.get(0).getOutputStream() );
			pw1 = new PrintWriter( clientlist.get(1).getOutputStream() );
			
			 
			String msg = "player:";
			
			while(true) {
					/*
					pw0.println("test");
					pw0.flush();
					pw1.println("test");
					pw1.flush();*/
					
					
					//不断从clientlist[cliendIdx] 这个客户端接收消息，并进行处理
					//one_more:如果这个玩家的牌数小于4则再发一张牌给他，否则不发
					if(br == null) {
						System.out.println("socket is closed");
						System.exit(-1);
					}
					msg = br.readLine();
					System.out.println("server received: " + msg);
					
					if(msg.startsWith("one_more:") ) {
						int num = Integer.valueOf(msg.substring(9));
						System.out.println(String.valueOf(clientIdx) + " pressed one_more button...cardnum:"+num);
						if(num >= 4) {
							send2Client("player1Num:"+4);
							msg = "";
							break;
						}else {
							num++;
							send2Client("player1Num:"+num);
							int cardpoint = ((int) Math.round(Math.random()*100) ) % 54 + 1;
							//给所有的客户端更新牌堆的信息
							sendCard2Client(cardpoint);
						}
						
					}else if(msg.startsWith("submit:") ) {
						System.out.println("one client submit");
						submit[clientIdx] = true;
						results[clientIdx] = Integer.valueOf(msg.substring(7));
						if(submit[0] && submit[1]) {
							System.out.println("all clients submit");
							
							if( Math.abs( results[0]-24 ) < Math.abs( results[1] - 24 ) ) {
								pw0.println("result:win");
								pw0.flush();
								pw1.println("result:lose");
								pw1.flush();
							}else if(Math.abs( results[0]-24 ) > Math.abs( results[1] - 24 )) {
								pw1.println("result:win");
								pw1.flush();
								pw0.println("result:lose");
								pw0.flush();
							}else {
								pw0.println("result:same");
								pw0.flush();
								pw1.println("result:same");
								pw1.flush();
							}
							msg = "";
						}else {
							System.out.println("wait another submit ...");
							msg = "";
						}
						System.out.println(String.valueOf(clientIdx) + " pressed submit button...");
					}else if(msg.startsWith("player:")) {
						System.out.println(msg+" send a message...");
						this.send2Client(msg);
					}else {
						System.out.println("litter msg...:"+msg);
						msg = "";
					}
					
					//Thread.sleep(10);
				}
			}catch (SocketException e) {
				System.out.println("client "+ clientIdx +" has closed!");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
	}
	
	public void sendCard2Client(int cardpoint) throws IOException {
		if(clientIdx == 0) {
			System.out.println(clientIdx+" asked for one_more, gets " + cardpoint);
			pw0.println("card:"+cardpoint);
			pw0.flush();
			//pw1.println("card:-1");
			//pw1.flush();
		}else if(clientIdx == 1) {
			System.out.println(clientIdx+" asked for one_more, gets " + cardpoint);
			pw1.println("card:"+cardpoint);
			pw1.flush();
			//pw0.println("card:-1");
			//pw0.flush();
		}
		
	}
	
	public void send2Client(String msg) throws IOException {
		//System.out.println("in send2client");
		if(clientIdx == 0) {
			System.out.println(clientIdx + "send " + msg +" to " + "client1");
			pw1.println(msg);
			pw1.flush();
		}else if(clientIdx == 1) {
			System.out.println(clientIdx + "send " + msg +" to " + "client0");
			pw0.println(msg);
			pw0.flush();
		}
		
		msg = "";
	}
	
	
}