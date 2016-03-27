/**
 * 
 */
package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
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
	 *
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
	 *
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
	 *
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
	 *
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
	 * 
	 * @param reserveStartTime start time
	 * @param hourIncrement the number to add to the start time
	 * @return endTime the result of adding the hour increment and the time
	 * This method is used to add an hour increment to a starting time probably in the most complicated way.
	 * This is used for student/user reservations where reservations can only be made
	 * for one day.
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return 0;
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
		
	}

}
