package calendar;

import java.util.Scanner;

class createCalendar{
	String show = "";
	public createCalendar() {
		
	}
	public void printMonth(int year, int month) {
		
		this.show = "";
		printMonthTitle(year, month);
		
		printMonthBody(year, month);
	}
	
	public void printMonthTitle(int year, int month) {
		this.show += "        "+ getMonthName(month) + "  " + year + "\n";
		this.show += "---------------------------------------------\n";
		this.show += "Sun   Mon   Tue   Wed   Thu   Fri    Sat\n";
		System.out.println("        "+ getMonthName(month) + "  " + year);
		System.out.println("---------------------------------------------");
		System.out.println(" Sun Mon Tue Wed Thu Fri Sat");
	}
	
	public String getMonthName(int month) {
		if(month < 1 || month > 12) {
			System.out.println("‘¬∑› ‰»Î¥ÌŒÛ£°");
			System.exit(-1);
		}
		String[] all_months_name = {"Januray", "Feburary", "March", "April", "May", "June", "July",
				"August", "September", "October", "November", "December"};
		return all_months_name[month-1];
		
	}
	
	public void printMonthBody(int year, int month) {
		int start_day = getStartDay(year, month);
		
		int number_of_days_in_month = getNumberOfDaysInMonth(year, month);
		
		//System.out.println("start_day: "+ start_day);
		int i = 0;
		for( i = 0; i < start_day ; i++) {
			this.show += "          ";
			System.out.print("    ");
			
		}
		
		for(i = 1 ; i <= number_of_days_in_month; i++) {
			if( i < 10) {
				this.show = this.show  + i + "        ";
			}else {
				this.show = this.show  + i + "      ";
			}
			
			System.out.printf("%4d",  i);
			
			if(( i + start_day ) % 7 == 0 ) {
				this.show += "\n";
				System.out.println();
			}
		}
		this.show += "\n\n";
		
		
		System.out.println();
		
		return ;
	}
	
	public int getStartDay(int year, int month) {
		final int START_DAY_OF_JAN_1_1800 = 3;
		
		int total_number_of_days = getTotalNumberOfDays(year, month);
		if(year < 1800) {
			return (total_number_of_days - START_DAY_OF_JAN_1_1800 ) % 7;
		}else {
			return (total_number_of_days + START_DAY_OF_JAN_1_1800 ) % 7;
		}
	}
	
	public int getTotalNumberOfDays(int year, int month) {
		int total = 0;
		if(year < 1800) {
			for(int i = year+1; i < 1800; i++) {
				if(isLeapYear(year )) {
					total += 366;
				}else {
					total += 365;
				}
			}
			
			for(int i = month ; i <= 12; i++) {
				total += getNumberOfDaysInMonth(year, month);
			}		
		}else {
			for(int i = 1800; i < year; i++) {
				if(isLeapYear(i )) {
					total += 366;
				}else {
					total += 365;
				}
			}
			
			for(int i = 1; i < month; i++) {
				total += getNumberOfDaysInMonth(year, i);
			}
		}
		//System.out.println("total days: " + total);
		return total;
	}
	
	public int getNumberOfDaysInMonth(int year, int month) {
		if(month == 1 || month ==3 || month == 5 || month ==7 || month == 8 || month == 10 || month == 12) {
			return 31;
		}
		if(month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if(month == 2) {
			return isLeapYear(year)?29: 28;
		}
		
		return 0;
	}
	
	public boolean isLeapYear(int year) {
		return year % 400 == 0 || (year % 4 == 0 && year % 100 != 0);
	}
	
}


public class calendar{
	
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter full year: ");
		int year = input.nextInt();
		System.out.print("Enter month as a number between 1 and 12:  ");
		int month = input.nextInt();
		
		createCalendar calendar = new createCalendar();
		
		calendar.printMonth(year, month);
		input.close();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}