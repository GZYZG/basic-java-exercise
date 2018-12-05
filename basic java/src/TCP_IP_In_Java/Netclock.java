package TCP_IP_In_Java;

import java.io.*;
import java.net.*;
import java.util.*;

public class Netclock{
	
	public static void main(String[] arg) {
		ServerSocket servsock = null;
		Socket sock;
		OutputStream out;
		String outstr = "This message is from server!";
		System.out.println(outstr);
		int i ;
		try {
			servsock = new ServerSocket(6000,100);
			while(true) {
				sock = servsock.accept();
				out = sock.getOutputStream();
				out.write(outstr.getBytes());
				out.write("\n".getBytes());
				sock.close();
			}
		}catch (Exception e) {
			System.exit(1);
		}
	}
}