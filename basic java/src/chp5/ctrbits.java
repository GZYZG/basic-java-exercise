package chp5;

//import java.lang.*;
import java.util.*;

public class ctrbits{
	
	public static void main(String[] arg) {
		Scanner input = new Scanner(System.in);
		byte[] binary = new byte[16];
		short i = 0,count = 0;
		
		System.out.println("please enter one integer:");
		i = input.nextShort();
		while(count < 16) {
			binary[15-count] = (byte)((i>>count) & (1));
			count++;
		}
		for(byte c:binary) {
			System.out.print(c);
		}
		
		System.out.printf("\n%d 正确的16比特形式是：%s",i,Integer.toBinaryString(i));
		input.close();
	}
	
}