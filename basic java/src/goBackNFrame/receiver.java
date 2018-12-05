package goBackNFrame;

import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import goBackNFrame.DataProcess;
import goBackNFrame.sender;

/*
 * 服务器端作为发送端
 */

public class receiver{
	static String file_path = "D:\\Study\\JAVA\\My projects\\ecilpse\\2018.9\\basic java\\resources\\result.txt";
	public static void write2File() throws IOException {
		FileWriter ws = new FileWriter(file_path);
		for(String s:sender.frameresult) {
			ws.write(s+" ");//注意文件的格式
			
		}

		ws.close();
	}
	
	public static int parseFrame(String frame) {
		int end = frame.indexOf("content");
		String seqstr = frame.substring(5, end-1);
		return Integer.valueOf(seqstr);
	}
	
	public static void main(String[] args) throws IOException {
	
	
		DatagramSocket socket = new DatagramSocket(8879);
		String server_name = "server_"+InetAddress.getLocalHost().getHostName()+" says ";
		String message = server_name;
		String result = "";
		
		boolean flag = true;
		while(flag){
		
			//从客户端读取数据
			byte[] buffer = new byte[1024];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length );
			socket.receive(packet);
			message = new String(packet.getData());
			result = DataProcess.RandomResult();
			int seq = parseFrame(message);
			if(result.equals("ACK")) {
				
				System.out.println("heard: "+ message);
				
			}else if(result.equals("bad")) {
				System.out.println("packet "+seq+" is bad! ");
			}else if(result.equals("miss")) {
				System.out.println("packet "+seq+" is missing! ");
			}
			//将接受情况写入
			sender.frameresult.add(seq, result);
			write2File();
			System.out.println("frameresult:"+sender.frameresult.toString());
			if(message.equals("bye")) {
				flag = false;
			}
			
		}
		socket.close();
		
	}
	
}