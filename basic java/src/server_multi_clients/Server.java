package server_multi_clients;

import server_multi_clients.ServerThread;
import server_multi_clients.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Server{
	
	public static void main(String[] args) throws IOException {
		List<User> user_list = new ArrayList<User>();
		
		ServerSocket serversocket = new ServerSocket(9999);
		System.out.println("server is starting...");
		
		//循环监听客户端连接
		while(true) {
			Socket socket = serversocket.accept();
			//每接受一个线程就生成一个用户
			User user = new User("user"+Math.round(Math.random()*100),socket);
			System.out.println(user.getName()+"is signing...");
			user_list.add(user);
			
			//创建一个新进程，接收信息并转发
			ServerThread thread = new ServerThread(user, user_list);
			thread.start();
			
			
		}
		
	}
	
	
	
}