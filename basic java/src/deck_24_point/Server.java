package deck_24_point;

import deck_24_point.client_ui;

import deck_24_point.ServerThread;
import server_multi_clients.User;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Server{
	
	final static int MAX_CONNECTION_NUM = 2;
	static byte[] ipbytes ;
	static ArrayList<Socket> clients;
	static int[] results;
	static boolean[] submit;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		String ipstr = "169.254.198.49";
		ipbytes = getIpBytes(ipstr);
		InetAddress address = InetAddress.getByAddress(ipbytes);
		int port = 9989;
		int count = 0;
		boolean flag = true;
		ServerSocket serversocket;
		clients = new ArrayList<Socket>();
		results = new int[] {0, 0};
		submit = new boolean[] {false, false};
		while(flag) {
			try {
				serversocket = new ServerSocket(port, MAX_CONNECTION_NUM+1, address);
				System.out.println("server is starting...\nport: "+port+"   ip: "+ipstr);
				flag = false;
				

				
				//循环监听客户端连接，只允许两个客户端连接
				while(count < MAX_CONNECTION_NUM) {
					System.out.println("is binding client...");
					Socket socket = serversocket.accept();
					System.out.println("accept successfully...");
					//将socket保存，以便创建线程时使用
					clients.add(count, socket);
					//创建一个新线程，接收信息并转发
					ServerThread serverthread = new ServerThread(clients, results, submit, count);
					count++;
					System.out.println("the NO." + String.valueOf(count) + " is signed...");
					serverthread.start();
					//System.out.println(count+" serverthread start...");
			
				}
				//System.out.println(clients.toString());
				
			}catch (BindException e) {
				port += 1;
				flag = true;
			}
		}
		/*
		while(true) {
			System.out.println("server is running");
			Thread.sleep(10000);
		}*/
		
		//System.out.println("server finish");
		
	}
	
	
	
	public static byte[] getIpBytes(String ipstr) {
		String[] ip_fields = ipstr.split("\\.");

		if(ip_fields.length != 4 ) {
			System.out.println("please enter right IPV4 address!");
			System.exit(-1);
		}
		byte[] ipbytes = new byte[4];		
		for(int i = 0; i < 4; i++) {
			ipbytes[i] = (byte)(Integer.parseInt(ip_fields[i])&0xff);
			//System.out.println(ipbytes[i]);
		}
		
		return ipbytes;
		
	}
	
	
	
}