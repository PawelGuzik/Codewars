package timeFormatter;

import java.util.ArrayList;
import java.util.List;

public class TimeFormatter {
	
	private static final int SEC_IN_YEAR = 31536000;
	private static final int SEC_IN_DAY = 86400;
	private static final int SEC_IN_HOUR = 3600;
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(formatDuration(100000000));
	}

	public static String formatDuration(int seconds) {
		int years = seconds / SEC_IN_YEAR;
		List<String> resultList = new ArrayList<String>();
		if (years >= 2) {
			resultList.add(years + " years");
		} else if (years == 1) {
			resultList.add("1 year");
		}
		int days = (seconds - years * SEC_IN_YEAR) / SEC_IN_DAY;
		if (days >= 2) {
			resultList.add(days + " days");
		} else if (days == 1) {
			resultList.add("1 day");
		}
		int hours = (seconds - (years * SEC_IN_YEAR + days * SEC_IN_DAY)) / SEC_IN_HOUR;
		if (hours >= 2) {
			resultList.add(hours + " hours");
		} else if (hours == 1) {
			resultList.add("1 hour");
		}
		int minutes = (seconds - (years * SEC_IN_YEAR + days * SEC_IN_DAY + hours * SEC_IN_HOUR)) / 60;
		if (minutes >= 2) {
			resultList.add(minutes + " minutes");
		} else if (minutes == 1) {
			resultList.add("1 minute");
		}
		int secondsLeft = seconds - (years * SEC_IN_YEAR + days * SEC_IN_DAY + hours * SEC_IN_HOUR + minutes * 60);
		if (secondsLeft >= 2) {
			resultList.add(secondsLeft + " seconds");
		} else if (secondsLeft == 1) {
			resultList.add("1 second");
		}
		System.out.println(resultList.toString());
		return toString(resultList);
		
	}

	public static String toString(List<String> resultList) {
		String result = resultList.get(0);
		if (resultList.size() == 1) {
			return result;
		} else {
			for (int i = 1; i < resultList.size() - 1; i++) {

				result = result + ", " + resultList.get(i);
			}
			result = result + " and " + resultList.get(resultList.size() - 1);
		}
		return result;
	}
}
