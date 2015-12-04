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
 * This class is used to convert a date or a datetime variable.
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
		this.datetime = datetime.trim(); // removes leading and trailing whitespace
	}

	// Accessors and Mutators for Fields
	
	public String getDatetime() {
		return datetime;
	}

	/**
	 * @param datetime the datetime to set
	 */
	public void setDatetime(String datetime) {
		this.datetime = datetime.trim(); // removes leading or trailing whitespace
	}
	
	
	

// The methods below convert and format mysql date(time) for display in Java & HTML	
	
	
	/**
	 * 
	 * @return converted date from mySQL format to "Month day, year" format.  Ex. January 23, 2015.
	 */
	public String convertDate(){
		
		try{
			SimpleDateFormat unformatted = new SimpleDateFormat("yyyy-MM-dd"); // used to convert String datetime to Simple Date Format
			SimpleDateFormat formatted = new SimpleDateFormat("MMMM dd, yyyy"); // format date to ex. January 12, 2015
			Date parsedDate = unformatted.parse(this.datetime); // parse the date from the datetime
			return formatted.format(parsedDate); // return parsed date in String format
		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to convert date to long format.**";
		}
	}
	
	/**
	 * 
	 * @param date
	 * @return converted date from mySQL format to "Month day, year" format.  Ex. January 23, 2015.
	 */
	public String convertDate(String date){
		
		this.datetime = date.trim(); // removes leading and trailing whitespace
		try{
			SimpleDateFormat unformatted = new SimpleDateFormat("yyyy-MM-dd"); // used to convert String datetime to Simple Date Format
			SimpleDateFormat formatted = new SimpleDateFormat("MMMM dd, yyyy"); // format date to ex. January 12, 2015
			Date parsedDate = unformatted.parse(this.datetime); // parse the date from the datetime
			return formatted.format(parsedDate); // return parsed date in String format
		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to convert date to long format.**";
		}
	}
	
	/**
	 * 
	 * @return parsed Date from datetime in Month date, year format.
	 */
	public String parseDate(){
	
		try{
			SimpleDateFormat unformatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // used to convert String datetime to Simple Date Format 
			SimpleDateFormat formatted = new SimpleDateFormat("MMMM dd, yyyy"); // format date to ex. January 12, 2015
			Date parsedDate = unformatted.parse(this.datetime); // parse the date from the datetime
			return formatted.format(parsedDate); // return parsed date in String format
		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to parse date.**";
		}
	}
	
	/**
	 * 
	 * @param datetime 
	 * @return parsed Date from datetime in Month date, year format.
	 */
	public String parseDate(String datetime){
		
		this.datetime = datetime.trim(); // removes leading and trailing whitespace
		try{
			SimpleDateFormat unformatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // used to convert String datetime to Simple Date Format
			SimpleDateFormat formatted = new SimpleDateFormat("MMMM dd, yyyy"); // format date to ex. January 12, 2015
			Date parsedDate = unformatted.parse(this.datetime); // parse the date from the datetime
			return formatted.format(parsedDate); // return parsed date in String format
		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to parse date.**";
		}
	}
	
	/**
	 * 
	 * @return String: parsed time from datetime. Converted time from 24-hour format to 12-hour format.
	 */
	public String parsedTimeTo12(){
		
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
	 * @return String: parsed time from datetime. Converted time from 24-hour format to 12-hour format.
	 */
	public String parsedTimeTo12(String datetime){
		
		this.datetime = datetime.trim(); // removes any leading or trailing whitespace
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
	 * @return current datetime in mySQL Format
	 */
	public String datetimeStamp(){
		
		Date stamp = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //mySQL format
		String currentDateTime = sdf.format(stamp);
		return currentDateTime;
	}
	/**
	 * 
	 * @return date in mySQL format from MM/dd/yyyy format.
	 */
	public String slashDateConvert (){
		
		try {
			SimpleDateFormat slashedFormat = new SimpleDateFormat("MM/dd/yyyy"); // used to convert String time to 24-hour DateTeime
	        SimpleDateFormat dashedFormat = new SimpleDateFormat("yyyy-MM-dd"); // used to convert 24-hour DateTime to 12-hour DateTime
	        Date d = slashedFormat.parse(this.datetime); // changes from string to date object
	        return dashedFormat.format(d); // returns date in SQL format
	        
		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to convert slashed date format to SQL format.**";
		}
	}
	
	/**
	 * 
	 * @param date in MM/dd/yyyy format
	 * @return date in mySQL format from MM/dd/yyyy format.
	 */
	public String slashDateConvert (String date){
		
		this.datetime = date.trim(); // removes leading and trailing whitespace
		try {
			SimpleDateFormat slashedFormat = new SimpleDateFormat("MM/dd/yyyy"); // used to convert String time to 24-hour DateTeime
	        SimpleDateFormat dashedFormat = new SimpleDateFormat("yyyy-MM-dd"); // used to convert 24-hour DateTime to 12-hour DateTime
	        Date d = slashedFormat.parse(this.datetime); // changes from string to date object
	        return dashedFormat.format(d); // returns date in SQL format
	        
		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to convert slashed date format to SQL format.**";
		}
	}
	
	/**
	 * 
	 * @return Inputted date + current timestamp (24 hr format) in mySQL format.
	 */
	public String slashDateTimeStamp (){
		
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		Date time = new Date();
		String inputtedDateTimeStamp = this.slashDateConvert(this.datetime) + " " + sdf.format(time);
		return inputtedDateTimeStamp;
	}
	
	/**
	 * 
	 * @param date in MM/dd/yyyy format
	 * @return Inputted date + current timestamp (24 hr format) in mySQL format
	 */
	public String slashDateTimeStamp (String date){
		
		this.datetime = date.trim(); // removes leading and trailing whitespace
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		Date time = new Date();
		String inputtedDateTimeStamp = this.slashDateConvert(this.datetime) + " " + sdf.format(time);
		return inputtedDateTimeStamp;
		
	}
	
	
// Main method used for testing. Will output to console.
	
	public static void main (String [] args){
		System.out.println("*******DateTimeConverter.java*******");
		System.out.println();
		DateTimeConverter dtc = new DateTimeConverter();
		dtc.setDatetime("2015-09-22 10:00:00");
		System.out.println("Print datetime: " + dtc.getDatetime());
		System.out.println();
		
		System.out.println("Parse time: " + dtc.parsedTimeTo12());
		System.out.println("Parse date: " + dtc.parseDate());
		System.out.println("Current Datetime: " + dtc.datetimeStamp());
		
		System.out.println();
		System.out.println("Inputted date: 2015-12-03");
		System.out.println("Convert date from mySQL to Long format: " + dtc.convertDate("2015-12-03"));
		
		dtc.setDatetime("12/3/2015");
		System.out.println();
		System.out.println("Convert date from slashed to SQL format: " + dtc.slashDateConvert(dtc.getDatetime()) );
		System.out.println("Convert inputted slashed to SQL + timestamp: " + dtc.slashDateTimeStamp());
		System.out.println();
		
	}
}
