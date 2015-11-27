/**
 * 
 */
package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Brian Olaogun
 *
 */
public class DateTimeConverter {
	// Fields
	private String datetime;
	
	// Constructors 
	public DateTimeConverter(){
		this.datetime = null;
	}

	public DateTimeConverter(String datetime){
		this.datetime = datetime;
	}

	// Accessors and Mutators for Fields
	
	public String getDatetime() {
		return datetime;
	}

	/**
	 * @param datetime the datetime to set
	 */
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	
	
	

// The methods below convert and format mysql date(time) for display in Java & HTML	
	
	
	
	
	/**
	 * 
	 * @return parsed Date from datetime in Month date, year format
	 */
	public String parseDate(){
		try{
			SimpleDateFormat unformatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // used to convert String datetime to Simple Date Format 
			SimpleDateFormat formatted = new SimpleDateFormat("MMMM dd, yyyy"); // format date to ex. January 12, 2015
			Date parsedDate = unformatted.parse(this.datetime); // parse the date from the datetime
			return formatted.format(parsedDate); // return parsed date in String format
		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to convert date.**";
		}
	}
	
	/**
	 * 
	 * @param datetime 
	 * @return parsed Date from datetime in Month date, year format
	 */
	public String parseDate(String datetime){
		this.datetime = datetime;
		try{
			SimpleDateFormat unformatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // used to convert String datetime to Simple Date Format
			SimpleDateFormat formatted = new SimpleDateFormat("MMMM dd, yyyy"); // format date to ex. January 12, 2015
			Date parsedDate = unformatted.parse(this.datetime); // parse the date from the datetime
			return formatted.format(parsedDate); // return parsed date in String format
		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to convert date.**";
		}
	}
	
	/**
	 * 
	 * @return String: the converted time from 24-hour format to 12-hour format
	 */
	public String convertTime(){
		try {
		SimpleDateFormat _24HourSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // used to convert String time to 24-hour DateTime
        SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a"); // used to convert 24-hour DateTime to 12-hour DateTime
        Date _24HourDt = _24HourSDF.parse(this.datetime); // parse time from DateTime
        return _12HourSDF.format(_24HourDt); // returns time in 12-hour format
        
		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to convert time.**";
		}
	}
	
	/**
	 * 
	 * @param time
	 * @return String: the converted time from 24-hour format to 12-hour format
	 */
	public String convertTime(String datetime){
		this.datetime = datetime;
		try {
		SimpleDateFormat _24HourSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // used to convert String time to 24-hour DateTeime
        SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a"); // used to convert 24-hour DateTime to 12-hour DateTime
        Date _24HourDt = _24HourSDF.parse(this.datetime); // parse time from DateTime
        return _12HourSDF.format(_24HourDt); // returns time in 12-hour format
        
		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to convert time.**";
		}
		
	}
	
	
	
	
// Methods to convert Java date(time) to mySQL date(time).
	
	
	
	/**
	 * 
	 * @return current datetime
	 */
	public String datetimeStamp(){
		Date stamp = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //SQL format
		String currentTime = sdf.format(stamp);
		return currentTime;
	}
	
	
// Main method used for testing. Will output to console
	
	public static void main (String [] args){
		DateTimeConverter dtc = new DateTimeConverter();
		dtc.setDatetime("2009-09-22 10:00:00");
		System.out.println("Print datetime: " + dtc.getDatetime());
		System.out.println();
		
		System.out.println("Parse time: " + dtc.convertTime());
		System.out.println("Parse date: " + dtc.parseDate());
		System.out.println("Current Datetime: " + dtc.datetimeStamp());
		
	}
}
