package atm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import atm.Transaction;

class Account implements Serializable{
	/*
	 * 
	 */
	String name;
	private int id;
	private double balance;
	private double annualInterstRate;
	private Date dateCreated;
	private String passwd;
	
	//为账户存储交易
	ArrayList<Transaction> transactions;
	
	public Account() {
		this.id = 0;
		this.balance = 0.0;
		this.dateCreated = new Date();
		this.setAnnualInterstRate(4.5);
		this.transactions = new ArrayList<>();
	}
	
	public Account(String name, int id, String passwd, double balance, double annual) {
		this.name = name;
		this.id = id;
		this.passwd = passwd;
		this.balance = balance;
		this.dateCreated = new Date();
		this.setAnnualInterstRate(annual);
		this.transactions = new ArrayList<>();
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public void setAnnualInterstRate(double annualinterstrate) {
		this.annualInterstRate = annualinterstrate;
	}
	
	public int getId() {
		return this.id;
	}
	
	public double getBalance(){
		return this.balance;
	}
	
	public double getAnnualInterstRate() {
		return this.annualInterstRate/100.0;
	}
	
	public Date getDateCreated() {
		return this.dateCreated;
	}
	
	public double getMonthlyInterstRate() {
		return this.annualInterstRate/12.0/100.0;
	}
	
	public String getPasswd() {
		return this.passwd;
	}
	
	public double withDraw(double money) {
		if(money < 0.0 || money > this.balance) {
			System.out.print("Sorry, your money is not enough!");
			return -1;
		}
		this.balance -= money;
		this.transactions.add(new Transaction('W', money, this.balance, " "));
		return money;
	}
	
	public void deposit(double money) {
		if(money < 0) {
			System.out.print("Sorry, please enter one positive num!");
			return;
		}
		
		this.balance += money;
		this.transactions.add(new Transaction('D', money, this.balance, " "));
	}
	
	public String toString() {
		String ownerinfo = "[姓名:"+this.name+"\t账号:"+this.id+"\t余额:"+this.balance+/*"\tpasswd:"+this.getPasswd()+*/
				"\n年利率:"+this.annualInterstRate+"\t开户日期:"+this.dateCreated.toLocaleString()+"]";
		String transactionsinfo = "";
		for(Transaction tran:this.transactions) {
			transactionsinfo += tran.toString()+"\n---------------\n";
		}
		
		return ownerinfo+"\n"+transactionsinfo;
	}
	
	
}