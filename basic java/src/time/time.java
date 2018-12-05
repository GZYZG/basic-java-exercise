package time;

import java.util.Calendar;

public class time{
	public static String  getTime() {
		long totalMilliseconds = System.currentTimeMillis();
		long currentMilliseconds = totalMilliseconds % 1000;
		long totalSeconds = totalMilliseconds / 1000;
		long currentSeconds = totalSeconds % 60;
		long totalMinutes = totalSeconds / 60;
		long currentMinutes = totalMinutes % 60;
		long totalHours = totalMinutes / 60;
		//因为时东八区，所以要加8
		long currentHours = totalHours % 24 + 8;
		
		String nowtime = currentHours+":"+currentMinutes+":"+currentSeconds+":"+currentMilliseconds;
		
		return nowtime;
		
	}
	public static String getDate() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int date = c.get(Calendar.DATE);
	
		String nowdate = year+"/"+month+"/"+date;
		return nowdate;
		
	}
}