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
 * This class will be used to convert time from 24 hour format to 12 hour format.
 * This class will also be used to convert from 12 hour format to 24 hour format.
 *
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

	// Main method used for testing. Will output to console.

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
	}

}
