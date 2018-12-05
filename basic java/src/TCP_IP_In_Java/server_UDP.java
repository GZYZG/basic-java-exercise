package TCP_IP_In_Java;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;


public class server_UDP{
	
	
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
	
	public static void main(String[] args) throws IOException {
		String address = "192.168.1.104";
		byte[] ipbytes = getIpBytes(address);
		Scanner input = new Scanner(System.in);
		DatagramSocket ds = new DatagramSocket();
		String server_name = "server_"+InetAddress.getLocalHost().getHostName()+" says ";
		String message = server_name;
		
		//DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(),
			//	InetAddress.getByName("localhost"), 8888);
		boolean flag = true;
		while(flag){
			System.out.print("says:");
			String tmp = input.nextLine();
			message += tmp;
			DatagramPacket packet = new DatagramPacket( message.getBytes(), message.length(),
					InetAddress.getByAddress(ipbytes), 8879);
			
			ds.send(packet);
			
			
			//从客户端读取数据
			byte[] buffer = new byte[1024];
			DatagramPacket packet2 = new DatagramPacket(buffer, buffer.length );
			ds.receive(packet2);
			message = new String(buffer, 0, packet.getLength());
			System.out.println("heard: "+ message);
			if(message.equals("bye")) {
				flag = false;
			}
			message = server_name;
		}
		ds.close();
		
	}
	
}