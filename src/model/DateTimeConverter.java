/**
 *
 */
package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * This class is used to convert/parse/transform a date or time from a datetime variable or date variable.
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
	 * This method will parse the date and convert it to MMMM dd, yyyy hh:mm a format from a date in yyyy-MM-dd format
	 * @return converted date from mySQL format to "Month day, year" format.  Ex. January 23, 2015.
	 */
	public String convertDateLong(){
		
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
	 * This method will parse the date and convert it to MMMM dd, yyyy hh:mm a format from a date in yyyy-MM-dd format
	 * @param date String date
	 * @return converted date from mySQL format to "Month day, year" format.  Ex. January 23, 2015.
	 */
	public String convertDateLong(String date){
		
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
	 * This method will parse the date and convert it to MMMM dd, yyyy hh:mm a format from a date time in yyyy-MM-dd HH:mm:ss format
	 * @return parsed Date from datetime in Month date, year format.
	 */
	public String parseDateLong(){
	
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
	 * This method will parse the date and convert it to MMMM dd, yyyy hh:mm a format from a date time in yyyy-MM-dd HH:mm:ss format
	 * @param datetime String date time
	 * @return parsed Date from datetime in Month date, year format.
	 */
	public String parseDateLong(String datetime){
		
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
	 * This method will convert it to MMMM dd, yyyy hh:mm a (12-hour) format from a date time in yyyy-MM-dd HH:mm:ss format
	 * @return String datetime in long date format (Month dd, yyyy) with 12-hour time
	 */
	public String dateTimeTo12Long(){
		try{
			SimpleDateFormat unformatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // used to convert String datetime to Simple Date Format
			SimpleDateFormat formatted = new SimpleDateFormat("MMMM dd, yyyy hh:mm a"); // format date to ex. January 12, 2015
			Date parsedDate = unformatted.parse(this.datetime); // parse the date from the datetime
			return formatted.format(parsedDate); // return parsed date in String format
		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to convert datetime to long format.**";
		}
	}
	
	/**
	 * This method will convert it to MMMM dd, yyyy hh:mm a (12-hour) format from a date time in yyyy-MM-dd HH:mm:ss format
	 * @param datetime String date Time
	 * @return String datetime in long date format (Month dd, yyyy) with 12-hour time
	 */
	public String dateTimeTo12Long(String datetime){
		
		this.datetime = datetime.trim(); // removes leading and trailing whitespace
		try{
			SimpleDateFormat unformatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // used to convert String datetime to Simple Date Format
			SimpleDateFormat formatted = new SimpleDateFormat("MMMM dd, yyyy hh:mm a"); // format date to ex. January 12, 2015
			Date parsedDate = unformatted.parse(this.datetime); // parse the date from the datetime
			return formatted.format(parsedDate); // return parsed date in String format
		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to convert datetime to long format.**";
		}
	}
	
	/**
	 * This method will parse the date and convert it to yyyy-MM-dd format from a date time in yyyy-MM-dd HH:mm:ss format
	 * @return parsed date from a datetime string
	 */
	public String parseDate(){
		
		try{
			SimpleDateFormat unformatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // used to convert String datetime to Simple Date Format 
			SimpleDateFormat formatted = new SimpleDateFormat("yyyy-MM-dd"); 
			Date parsedDate = unformatted.parse(this.datetime); // parse the date from the datetime
			return formatted.format(parsedDate); // return parsed date in String format
		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to parse date.**";
		}
	}
	
	/**
	 * This method will parse the date and convert it to yyyy-MM-dd format from a date time in yyyy-MM-dd HH:mm:ss format
	 * @param datetime String Date Time
	 * @return parsed Date from datetime in Month date, year format.
	 */
	public String parseDate(String datetime){
		
		this.datetime = datetime.trim(); // removes leading and trailing whitespace
		try{
			SimpleDateFormat unformatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // used to convert String datetime to Simple Date Format
			SimpleDateFormat formatted = new SimpleDateFormat("yyyy-MM-dd"); 
			Date parsedDate = unformatted.parse(this.datetime); // parse the date from the datetime
			return formatted.format(parsedDate); // return parsed date in String format
		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to parse date.**";
		}
	}
	
	/**
	 * This method will parse the time and convert it to hh:mm a (12-hour) format from a date time in format yyyy-MM-dd HH:mm:ss
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
	 * This method will parse the time and convert it to hh:mm a (12-hour) format from a date time in format yyyy-MM-dd HH:mm:ss
	 * @param datetime String datetime
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
	/**
	 * This method will parse the time and convert it to HH:mm:ss (24-hour) format from a date time in format yyyy-MM-dd HH:mm:ss
	 * @return parses the time from a date time string.  The date time should be already in 24-hour format.
	 */
	public String parsedTimeTo24(){
		
		this.datetime = datetime.trim(); // removes any leading or trailing whitespace
		try {
			SimpleDateFormat _24HourSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // format of datetime being parsed
	        SimpleDateFormat _24HourSDF2 = new SimpleDateFormat("HH:mm:ss"); // format of parsed time
	        Date _24HourDt = _24HourSDF.parse(this.datetime); // parse time from DateTime
	        return _24HourSDF2.format(_24HourDt); // returns time in 24-hour format
        
		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to convert time.**";
		}
		
	}
	/**
	 * This method will parse the time and convert it to HH:mm:ss (24-hour) format from a date time in format yyyy-MM-dd HH:mm:ss
	 * @param datetime String date time
	 * @return parses the time from a date time string.  The date time should be already in 24-hour format.
	 */
	public String parsedTimeTo24(String datetime){
		
		this.datetime = datetime.trim(); // removes any leading or trailing whitespace
		try {
			SimpleDateFormat _24HourSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // format of datetime being parsed
	        SimpleDateFormat _24HourSDF2 = new SimpleDateFormat("HH:mm:ss"); // format of parsed time
	        Date _24HourDt = _24HourSDF.parse(this.datetime); // parse time from DateTime
	        return _24HourSDF2.format(_24HourDt); // returns time in 24-hour format
        
		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to convert time.**";
		}
		
	}
	/**
	 * This method will convert the date from MM/dd/yyyy to yyyy-MM-dd (SQL format)
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
	 * This method will convert the date from MM/dd/yyyy to yyyy-MM-dd (SQL format)
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
	 * This method will create a date time stamp in the format yyyy-MM-dd HH:mm:ss
	 * @return current datetime in mySQL Format
	 */
	public String datetimeStamp(){
		
		Date stamp = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //MySQL format
		String currentDateTime = sdf.format(stamp);
		return currentDateTime;
	}
	/**
	 * Method will print dates between range inclusive.  Parameters can be in MM/dd/yyy format
	 * for both start and end or yyyy-MM-dd for both start and end.  If using a combination for start
	 * and end, the methdo will throw an error.
	 * @param stringStartDate String start date
	 * @param stringEndDate String end date
	 * @return an arraylist of all dates in range of start date and end date inclusive.
	 */
	public List<String> dateRangeList(String stringStartDate, String stringEndDate){
		
		// convert string date to date object
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		List<String> dates = new ArrayList<String>();
		
		Date startdate;
		Date enddate;
		
		try {
			if(stringStartDate.contains("/") && stringEndDate.contains("/")){
				startdate = format.parse(stringStartDate);
				enddate = format.parse(stringEndDate);
				Calendar calendar = new GregorianCalendar();
			    calendar.setTime(startdate);

			    while (calendar.getTime().getTime() <= enddate.getTime())
			    {
			        Date result = calendar.getTime();
			        String sqlFormattedResult = sqlFormat.format(result); 
			        dates.add(sqlFormattedResult);
			        calendar.add(Calendar.DATE, 1);
			    }
			    return dates;
			    
			} else if (stringStartDate.contains("-") && stringEndDate.contains("-")){
				startdate = format.parse(format.format(sqlFormat.parse(stringStartDate)));
				enddate = format.parse(format.format(sqlFormat.parse(stringEndDate)));
				
				
				Calendar calendar = new GregorianCalendar();
			    calendar.setTime(startdate);

			    while (calendar.getTime().getTime() <= enddate.getTime())
			    {
			        Date result = calendar.getTime();
			        String sqlFormattedResult = sqlFormat.format(result); 
			        dates.add(sqlFormattedResult);
			        calendar.add(Calendar.DATE, 1);
			    }
			    return dates;
			    
			} else {
				System.out.println("ERROR in DateTimeConverter.dateRangeList. Please use the same date format.");
			}
			
		    
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println("DateTimeConverter.dateRangeList - Error printing date range: " + dates);
		}
		return null;
	}
	/**
	 * This method will compare a starting date to see if its greater than the ending date.
	 * If the starting date is greater, true is returned.
	 * @param to String ending date in MM/dd/yyyy format
	 * @param from String starting date in MM/dd/yyyy format
	 * @return boolean true if from > to
	 */
	public static boolean isAfter (String from, String to){
		SimpleDateFormat slashedFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date1;
		Date date2;
		try {
			date1 = slashedFormat.parse(from);
			date2 = slashedFormat.parse(to);
			
			if(date1.after(date2)){
	             return true;
	         }
		} catch (ParseException e) {
			e.printStackTrace();
			System.err.println("**Error parsing date in DateTimeConverter.comapreDate**");
		}

		return false;
	}
	
// Main method used for testing. Will output to console.
	
	public static void main (String [] args){
		System.out.println("*******DateTimeConverter.java*******");
		System.out.println();
		DateTimeConverter dtc = new DateTimeConverter();
		dtc.setDatetime("2015-09-22 20:00:00");
		System.out.println("Print datetime: " + dtc.getDatetime());
		System.out.println();
		
		System.out.println("Parse time 12 hour: " + dtc.parsedTimeTo12());
		System.out.println("Parse time 24 hour: " + dtc.parsedTimeTo24());
		System.out.println("Parse date: " + dtc.parseDate());
		System.out.println("Current Datetime: " + dtc.datetimeStamp());
		
		System.out.println("LONG FORMAT of 2015-09-22 20:00:00 = " + dtc.dateTimeTo12Long("2015-09-22 20:00:00"));
		
		// date range
		List<String> dates = dtc.dateRangeList("2016-02-27", "2016-02-27");
		for(int i=0; i<dates.size(); i++){
		    String date = dates.get(i);
		    System.out.println(" Date is ..." + date);
		}
		
		boolean isAfter = DateTimeConverter.isAfter("03/03/2016", "03/12/2016");
		System.out.println(isAfter);
	}
}
