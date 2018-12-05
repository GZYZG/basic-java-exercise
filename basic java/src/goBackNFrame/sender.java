package goBackNFrame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * 发送方作为客户端
 */


public class sender{
	static int N = 3;
	static ArrayList<Integer> notACK = new ArrayList<>();
	public static ArrayList<String> frameresult = new ArrayList<>();
	static String file_path = "D:\\Study\\JAVA\\My projects\\ecilpse\\2018.9\\basic java\\resources\\result.txt";
	
	public static void readfromFile() throws IOException {
		BufferedReader rs = new BufferedReader(new FileReader(file_path));
		String recoder;
		while( (recoder = rs.readLine()) != null && !recoder.equals("")  ) {
			String[] allresults = recoder.split(" ");
			for(String res:allresults) {
				sender.frameresult.add(res);
			}
			
		}
		rs.close();
	}
	
	public static void readSendResult(int seq) {
		if(sender.frameresult.isEmpty()) {
			return;
		}
		String result = "";
		for(int i=seq; i < seq+3; i++) {
			result = sender.frameresult.get(i);
			if(result.equals("ACK")) {
				System.out.println("packet "+i+" send successfully!");
			}else if(result.equals("bad")) {
				System.out.println("packet "+i+" is corrucpted!");
				notACK.add(i);
			}else if(result.equals("miss")) {
				System.out.println("packet "+i+" is missing!");
				notACK.add(i);
			}
		}
		
	}
	
	
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		
		
		String client_name = "client_"+InetAddress.getLocalHost().getHostName()+" says ";
		String message = client_name;
		DatagramSocket ds = new DatagramSocket();
		byte[] buffer = new byte[1024];
		boolean flag = true;
		Scanner input = new Scanner(System.in);
		String tmp = "";
		
		int seq = 0;
		ArrayList<Frame> allframes = new ArrayList<>();
		
		
		while(flag){
			
			message = client_name;
			//向服务器发送数据
			System.out.print("send seq="+seq+" frame: ");
			tmp = input.nextLine();
			message += tmp;
			
			Frame frame = new Frame(seq,message);
			allframes.add(frame);		
			DatagramPacket packet = new DatagramPacket(frame.toString().getBytes(), frame.toString().length(),
					InetAddress.getByName("localhost"), 8879);
			ds.send(packet);
			//System.out.println("frameresult:"+sender.frameresult.toString());
			message = client_name;
			//读取接收情况
			if(seq % 3 == 2) {
				Thread.sleep(500);
				readfromFile();
				readSendResult(seq-2);
				if(!notACK.isEmpty()) {
					for(int i:notACK) {
						//重发没有ACK的帧
						DatagramPacket packet2 = new DatagramPacket(allframes.get(i).toString().getBytes(),
								allframes.get(i).toString().length(),
								InetAddress.getByName("localhost"), 8879);
						ds.send(packet2);
					}
					notACK.clear();
				}
			}
			seq++;
			
			if(tmp.equals("bye"))
				flag = false;
		}
		ds.close();
		
	}
}