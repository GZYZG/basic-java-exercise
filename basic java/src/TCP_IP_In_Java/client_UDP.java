package TCP_IP_In_Java;

import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class client_UDP{
	
	
	public static void main(String[] args) throws IOException {
		String client_name = "client_"+InetAddress.getLocalHost().getHostName()+" says ";
		String message = client_name;
		DatagramSocket ds = new DatagramSocket(8879);
		byte[] buffer = new byte[1024];
		boolean flag = true;
		Scanner input = new Scanner(System.in);
		String tmp = "";
		
		while(flag){
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			ds.receive(packet);
			message = new String(buffer, 0, packet.getLength());
			System.out.println("heard: "+ message);
			if(message.equals("bye")){
				flag = false;
			}
			
			message = client_name;
			//向服务器发送数据
			System.out.print("says: ");
			tmp = input.nextLine();
			message += tmp;
			
			
			DatagramPacket packet2 = new DatagramPacket(message.getBytes(), message.length(), packet.getAddress(), packet.getPort());
			ds.send(packet2);
			message = client_name;
		}
		ds.close();
		
	}
}