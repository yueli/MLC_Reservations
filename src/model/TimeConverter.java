/**
 * 
 */
package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This class will be used to convert time from 24 hour format to 12 hour format. This class will also be used to convert from 12 hour format to 24 hour format.
 * @author Brian Olaogun
 */
public class TimeConverter {
	// Fields
	private String time;

	// Constructors
	public TimeConverter(){
		this.time = "0:00";

	}

	/**
	 *
	 * @param time String
	 */
	public TimeConverter(String time){
		this.time = time.trim(); // removes leading or trailing whitespace
	}


    // Accessors and Mutators
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time: the time to set
	 */
	public void setTime(String time) {
		this.time = time.trim(); // removes leading or trailing whitespace
	}


	// Methods to convert 24-hour time format to 12-hour time format

	/**
	 * Convert time from 24-hour HH:mm format to 12- hour h:mm a format.
	 * @return String: the converted time from 24-hour format
	 */
	public String convertTimeTo12(){

		try {
			SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm"); // used to convert String time to 24-hour DateTime
	        SimpleDateFormat _12HourSDF = new SimpleDateFormat("h:mm a"); // used to convert 24-hour DateTime to 12-hour DateTime
	        Date _24HourDt = _24HourSDF.parse(this.time); // parse time from DateTime
	        return _12HourSDF.format(_24HourDt); // returns time in 12-hour format

		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to convert time to 12 hours.**";
		}
	}

	/**
	 * Convert time from 24-hour HH:mm format to 12- hour h:mm a format.
	 * @param time String in 24-hour format
	 * @return String: the converted time from 24-hour format
	 */
	public String convertTimeTo12(String time){
		time = time.trim(); // removes leading and trailing whitespace
		this.time = time;

		try {
			SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm"); // used to convert String time to 24-hour DateTeime
	        SimpleDateFormat _12HourSDF = new SimpleDateFormat("h:mm a"); // used to convert 24-hour DateTime to 12-hour DateTime
	        Date _24HourDt = _24HourSDF.parse(this.time); // parse time from DateTime
	        return _12HourSDF.format(_24HourDt); // returns time in 12-hour format

		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to convert time to 12 hours.**";
		}
	}

	/**
	 * Convert time from 12-hour hh:mma or hh:mm format to 24-hour HH:mm:ss format.
	 * @return converted time from 12 hour to 24 hour format
	 */
	public String convertTimeTo24(){
		this.time = this.time.replaceAll("\\s+",""); // remove leading and trailing whitespace as well whitespace within string

		try{
			SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm:ss"); // 24 hour date format
	        SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mma"); // 12 hour date format.
	        Date _12HourDt = _12HourSDF.parse(this.time); // parse time from DateTime
	        return _24HourSDF.format(_12HourDt); // returns time in 24-hour format

		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to convert time to 24 hours.**";
		}
	}

	/**
	 * Convert time from 12-hour hh:mma or hh:mm format to 24-hour HH:mm:ss format.
	 * @param time String in 12-hour format
	 * @return converted time from 12 hour to 24 hour format
	 */
	public String convertTimeTo24(String time){
		time = time.trim().replaceAll("\\s+",""); // remove leading and trailing whitespace as well whitespace within string
		this.time = time;

		try{
			SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm:ss"); // 24 hour date format
	        SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mma"); // 12 hour date format.
	        Date _12HourDt = _12HourSDF.parse(this.time); // parse time from DateTime
	        return _24HourSDF.format(_12HourDt); // returns time in 24-hour format

		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to convert time to 24 hours.**";
		}

	}
	/**
	 * This method is used to add an hour increment to a starting time probably in the most complicated way.
	 * This is used for student/user reservations where reservations can only be made
	 * for one day.
	 * @param reserveStartTime start time
	 * @param hourIncrement the number to add to the start time
	 * @return endTime the result of adding the hour increment and the time
	 */
	public static String addTime(String reserveStartTime, int hourIncrement){
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		
		try {
			// convert start time to date.  Convert increment to integer
			Date startTime = df.parse(reserveStartTime);
			
			// convert hour increment to seconds and add it to the start time
			Date add = new Date(startTime.getTime() + hourIncrement *(3600*1000));
			
			// format to get time only.  The result is the endTime
			// if endTime is 00:00:00, change end time to 23:59:59.
			String endTime = df.format(add);
			if(endTime.equals("0:00") || endTime.equals("00:00") || endTime.equals("00:00:00") || endTime.equals("0:00:00")){
				endTime = "23:59:59";
			}
			return endTime;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * This method will subtract the hours of a start time by the reservation length (hour)
	 * For example, 23:00:00 - 2 = 21:00:00
	 * This mehod subtracts the hour only.
	 * @param time String time in HH:mm:ss (24-hour) SQL format
	 * @param hourIncrement Integer reservation length
	 * @return The difference from of the start time - reservation length (hour increment)
	 */
	public static String subtractTime (String time, int hourIncrement){
		String newTime; // the result of adding a second to inputted time
		int hour; // for calendar object
		int minute; // for calendar object
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH"); // for parsing out hour (in 24-hour format)
		SimpleDateFormat minuteFormat = new SimpleDateFormat("mm"); // for parsing out minutes
		SimpleDateFormat _24HourTimeFormat = new SimpleDateFormat("HH:mm:ss"); // for formatting inputted string & to format result
		
		try {
			// parse out hour to add to calendar
			Date convertStringTimeToDate_Hour = _24HourTimeFormat.parse(time);
			String hourString = hourFormat.format(convertStringTimeToDate_Hour);
			hour = Integer.parseInt(hourString);
	
			// parse out minute to add to calendar
			Date convertStringTimeToDate_Minute = _24HourTimeFormat.parse(time);
			String minuteString = minuteFormat.format(convertStringTimeToDate_Minute);
			minute = Integer.parseInt(minuteString);
			
			// calendar object to correctly add or subtract time
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, hour);
		    calendar.set(Calendar.MINUTE, minute);
		    calendar.set(Calendar.SECOND, 00);
		    
		    // subtract a second
		    calendar.add(Calendar.HOUR_OF_DAY, -hourIncrement); 
		    
		    //convert calendar to string time
		    Date nt = calendar.getTime();
		    newTime = _24HourTimeFormat.format(nt);
		    return newTime;
		    
		} catch (ParseException e) {
			System.out.println("Error parsing time in TimeConverter.addOneMinute. Please check time parameter " + time);
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * This method will count the number of hours between start and end time.
	 * @param reserveStartTime String start time
	 * @param reserveEndTime String end time
	 * @return Int hour increment - the number of hours between start and end time
	 */
	public Integer getHourIncrement(String reserveStartTime, String reserveEndTime){
		List<String> times = timeRangeList(reserveStartTime, reserveEndTime);
		int hourIncrement = (times.size()-1);
	
		return hourIncrement;
	}
	/**
	 * This method will return an array list of all values inclusive between start time and end time
	 * for times on the hour (both start and end).
	 * @param startTime String starting time in 24-hour format
	 * @param endTime String ending time in 24-hour format
	 * @return String array list of all values inclusive between start time and end time
	 */
	public List<String> timeRangeList (String startTime, String endTime){
		// integer to place the parsed hour from the time.
		int startHour;
		int endHour;
		
		// array list to place the time range, inclusive.
		List<String> timeRange = new ArrayList<String>();
		if(startTime.length() == 7){
			startTime = "0" + startTime;
		}
		else if(endTime.length() == 7){
			endTime = "0" + startTime;
		}
		// parse out the hour from time in 00:00:00 format
		startHour = Integer.parseInt(startTime.substring(0, Math.min(startTime.length(), 2)));
		endHour = Integer.parseInt(endTime.substring(0, Math.min(endTime.length(), 2)));
		
		// add range to list.
		/**
		 * Increment hour and if incrementing from 23, start hour over to 0 
		 * increment to end hour.
		 */
		if (startHour > endHour){
			for(int h = startHour;  h <=24; h++){
				//timeRange.add(startHour);
				timeRange.add(startTime);
				startHour++;
				startTime = startHour + ":00:00";
				
				if(startHour == 24){
					startHour = 0;
				}
			}	
		}
		for (int i = startHour; i <= endHour; i++){
			timeRange.add(startTime);
			startHour++;
			startTime = startHour + ":00:00";
			
		}
		
		return timeRange;
	}
	/**
	 * This method is used to make a reservation.  We need to subtract one second
	 * on the end time before adding it to the reservations table so that here is no
	 * time overlap.
	 * @param time in format 00:00:00
	 * @return inputted time with one second subtracted
	 */
	public static String subtractOneSecondToTime (String time){
		String newTime; // the result of adding a second to inputted time
		int hour; // for calendar object
		int minute; // for calendar object
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH"); // for parsing out hour (in 24-hour format)
		SimpleDateFormat minuteFormat = new SimpleDateFormat("mm"); // for parsing out minutes
		SimpleDateFormat _24HourTimeFormat = new SimpleDateFormat("HH:mm:ss"); // for formatting inputted string & to format result
		
		try {
			// parse out hour to add to calendar
			Date convertStringTimeToDate_Hour = _24HourTimeFormat.parse(time);
			String hourString = hourFormat.format(convertStringTimeToDate_Hour);
			hour = Integer.parseInt(hourString);
	
			// parse out minute to add to calendar
			Date convertStringTimeToDate_Minute = _24HourTimeFormat.parse(time);
			String minuteString = minuteFormat.format(convertStringTimeToDate_Minute);
			minute = Integer.parseInt(minuteString);
			
			// calendar object to correctly add or subtract time
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, hour);
		    calendar.set(Calendar.MINUTE, minute);
		    calendar.set(Calendar.SECOND, 00);
		    
		    // subtract a second
		    calendar.add(Calendar.SECOND, -1); 
		    
		    //convert calendar to string time
		    Date nt = calendar.getTime();
		    newTime = _24HourTimeFormat.format(nt);
		    return newTime;
		    
		} catch (ParseException e) {
			System.out.println("Error parsing time in TimeConverter.addOneMinute. Please check time parameter " + time);
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * This method is used to read a reservation. This is so that the end time
	 * is more user friendly when displayed on the website.
	 * @param time in 00:00:00 24-hour format
	 * @return inputted time with one second subtracted
	 */
	public static String addOneSecondToTime (String time){
		String newTime; // the result of adding a second to inputted time
		int hour; // for calendar object
		int minute; // for calendar object
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH"); // for parsing out hour (in 24-hour format)
		SimpleDateFormat minuteFormat = new SimpleDateFormat("mm"); // for parsing out minutes
		SimpleDateFormat _24HourTimeFormat = new SimpleDateFormat("HH:mm:ss"); // for formatting inputted string & to format result
		
		try {
			// parse out hour to add to calendar
			Date convertStringTimeToDate_Hour = _24HourTimeFormat.parse(time);
			String hourString = hourFormat.format(convertStringTimeToDate_Hour);
			hour = Integer.parseInt(hourString);
	
			// parse out minute to add to calendar
			Date convertStringTimeToDate_Minute = _24HourTimeFormat.parse(time);
			String minuteString = minuteFormat.format(convertStringTimeToDate_Minute);
			minute = Integer.parseInt(minuteString);
			
			// calendar object to correctly add or subtract time
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, hour);
		    calendar.set(Calendar.MINUTE, minute);
		    calendar.set(Calendar.SECOND, 59); //the current seconds will be at 59
		   
		    // add second
		    calendar.add(Calendar.SECOND, 1); 
		    
		    // convert calendar to string time
		    Date nt = calendar.getTime();
		    newTime = _24HourTimeFormat.format(nt);
		    return newTime;

		} catch (ParseException e) {
			System.out.println("Error parsing time in TimeConverter.addOneMinute. Please check time parameter " + time);
			e.printStackTrace();
		}
		
		
		
		return null;
	}
	/**
	 * Parse the hour from the current time.
	 * @return the hour of the current time.
	 */
	public Integer currentHour(){
		DateTimeConverter dtc = new DateTimeConverter();
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH"); // for parsing out hour
		SimpleDateFormat _24HourTimeFormat = new SimpleDateFormat("HH:mm:ss"); // for formatting inputted string & to format result
		
		int hour;
		String currentTime = dtc.parsedTimeTo24(dtc.datetimeStamp());
		
		// parse out minute from time in HH:mm:ss format.
		try {
			Date convertCurrentTime = _24HourTimeFormat.parse(currentTime);
			hour = Integer.parseInt(hourFormat.format(convertCurrentTime));
			return hour;
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Get the hour of the inputted time.
	 * @return The hour of the inputted time.
	 */
	public Integer parseHour(String time){
		DateTimeConverter dtc = new DateTimeConverter();
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH"); // for parsing out hour
		SimpleDateFormat _24HourTimeFormat = new SimpleDateFormat("HH:mm:ss"); // for formatting inputted string & to format result
		
		int hour;
		String currentTime = dtc.parsedTimeTo24(dtc.datetimeStamp());
		
		// parse out minute from time in HH:mm:ss format.
		try {
			Date convertCurrentTime = _24HourTimeFormat.parse(currentTime);
			hour = Integer.parseInt(hourFormat.format(convertCurrentTime));
			return hour;
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * This method will only return the minutes from the current time.
	 * @return minutes - parsed minutes from the current time.
	 */
	public int currentMinutes(){
		DateTimeConverter dtc = new DateTimeConverter();
		SimpleDateFormat minuteFormat = new SimpleDateFormat("mm"); // for parsing out minutes
		SimpleDateFormat _24HourTimeFormat = new SimpleDateFormat("HH:mm:ss"); // for formatting inputted string & to format result
		
		int minutes;
		String currentTime = dtc.parsedTimeTo24(dtc.datetimeStamp());
		
		// parse out minute from time in HH:mm:ss format.
		
		try {
			Date convertStringTimeToDate_Minute = _24HourTimeFormat.parse(currentTime);
			minutes = Integer.parseInt(minuteFormat.format(convertStringTimeToDate_Minute));
			return minutes;
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		
		return 0;
	}
	/**
	 * This method will check to see if the start time greater than the end time if
	 * the start and end time are on the hour.
	 * @param startTime
	 * @param endTime
	 * @return true if the start time is > end time.
	 */
	public static boolean isAfter (String startTime, String endTime){
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH"); // for parsing out minutes
		SimpleDateFormat _24HourTimeFormat = new SimpleDateFormat("HH:mm:ss"); // for formatting inputted string & to format result
		
		int startHour;
		int endHour;
		
		try {
			Date convertStartTime = _24HourTimeFormat.parse(startTime);
			String parsedStartHour = hourFormat.format(convertStartTime);
			startHour = Integer.parseInt(parsedStartHour);
			
			Date convertEndTime = _24HourTimeFormat.parse(endTime);
			String parsedEndHour = hourFormat.format(convertEndTime);
			endHour = Integer.parseInt(parsedEndHour);
			
			if (startHour > endHour){
				return true;
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println("**Unable to compare time in TimeConverter.isAfter**");
		}
		
		return false;
			
	}
	
	/**
	 *  Main method used for testing. Will output to console.
	 * @param args String array - this the Java main method.
	 */
	public static void main (String [] args){
		System.out.println("*******TimeConverter.java*******");
		System.out.println();
		TimeConverter tc = new TimeConverter("14:23:23");
		System.out.println("Set time to " + tc.getTime());
		System.out.println("Convert to 12 hour format: " + tc.convertTimeTo12());
		tc.setTime("2:45pm");
		System.out.println();
		System.out.println("Set time to " + tc.getTime());
		System.out.println("Convert to 24 hour format: " + tc.convertTimeTo24());
		System.out.println();
		System.out.println("subtract 1 sec from 23:00:00 = " + TimeConverter.subtractOneSecondToTime("23:00:00"));
		System.out.println("add 1 sec to 22:59:59 = " + TimeConverter.addOneSecondToTime("22:59:59"));
		System.out.println("current minutes = " + tc.currentMinutes());
		System.out.println("TESTING ADD TIME METHOD: " + TimeConverter.addTime("23:00:00", 1));
		// time range
		List<String> times = tc.timeRangeList("09:00:00", "11:00:00");
		for (int i=0; i<times.size(); i++){
			String time = times.get(i);
			System.out.println("Time is ..." + time);
		}
		System.out.println("HOUR INCREMENT METHOD = " + tc.getHourIncrement("09:00:00", "11:00:00"));
		
		System.out.println("TEST subtract time method = " + TimeConverter.subtractTime("00:00:00", 1));
		
		System.out.println("TEST is after Method " + TimeConverter.isAfter("12:00:00", "11:00:00"));
		
	}

}
