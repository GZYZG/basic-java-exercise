package atm;

import java.io.Console;
import java.util.InputMismatchException;
import java.util.Scanner;

import atm.Account;

public class ATM{
	
	public static void showMenu() {
		System.out.println("Main Menu");
		System.out.println("1:check balance");
		System.out.println("2:withdraw");
		System.out.println("3:deposit");
		System.out.println("4:exit");
		System.out.print("Enter a choice:");
	}
	
	public static void main(String[] args) {
		int selectedId = 0;
		int choice = 0;
		double amount = 0.0;
		Account[] allAccounts = new Account[10];
		for(int i = 0; i < 10; i++) {
			allAccounts[i] = new Account();
		}
		
		
		Scanner input = new Scanner(System.in);
		
		/*
		char[] passwd = new char[20];
		System.out.print("please enter ur passwd:");
		Console console = System.console();
		passwd = console.readPassword();
		System.out.println("u input :"+passwd.toString());
		*/
		
		while(true) {
			do {
				System.out.print("\nEnter an id:");
				selectedId = input.nextInt();
			}while(selectedId < 0 || selectedId > 9);
			
			do {
				System.out.println("\n");
				showMenu();
				
				String tmp = input.next();
				if(Double.valueOf(tmp) - Math.floor(Double.valueOf(tmp))  <= 1e-7) {
					choice = Integer.valueOf(tmp);
				}else {
					choice = 0;
				}
				
				switch(choice) {
				case 1:
					System.out.print("The balance is "+ allAccounts[selectedId].getBalance() );
					break;
				case 2:
					System.out.print("Enter an amount to withdraw:");
					amount = input.nextDouble();
					allAccounts[selectedId].withDraw(amount);
					break;
				case 3:
					System.out.print("Enter an amount to deposit:");
					amount = input.nextDouble();
					allAccounts[selectedId].deposit(amount);
					break;
				}
				
			}while(choice != 4 || (choice < 0 && choice > 4));
				
			
		}
	
	}
}