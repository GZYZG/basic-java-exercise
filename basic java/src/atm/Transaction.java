package atm;

import java.io.Serializable;
import java.util.Date;

class Transaction implements Serializable{
	
	Date date;
	char type;
	double amount;
	double balance;
	String description;
	
	public Transaction(char type, double amount, double balance, String description) {
		this.type = type;
		this.amount = amount;
		this.balance = balance;
		this.description = description;
		this.date = new Date();
	}
	
	public String toString() {
		return "交易[日期:"+date.toLocaleString()+
				"\n交易类型:"+this.type+
				"\t金额:"+this.amount+
				"\t余额:"+this.balance+
				"\n描述:"+this.description+"]";
	}
	
	
	
}