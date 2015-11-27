/**
 * 
 */
package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Brian Olaogun
 * This class will be used to convert time from 24 hour format to 12 hour format.
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
	 * @param time 
	 */
	public TimeConverter(String time){
		this.time = time;
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
		this.time = time;
	}
	
	
	// Methods to convert 24-hour time format to 12-hour time format
	
	/**
	 * 
	 * @return String: the converted time from 24-hour format
	 */
	public String convertTime(){
		try {
		SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm"); // used to convert String time to 24-hour DateTime
        SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a"); // used to convert 24-hour DateTime to 12-hour DateTime
        Date _24HourDt = _24HourSDF.parse(this.time); // parse time from DateTime
        return _12HourSDF.format(_24HourDt); // returns time in 12-hour format
        
		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to convert time.**";
		}
	}
	
	/**
	 * 
	 * @param time
	 * @return String: the converted time from 24-hour format
	 */
	public String convertTime(String time){
		this.time = time;
		try {
		SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm"); // used to convert String time to 24-hour DateTeime
        SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a"); // used to convert 24-hour DateTime to 12-hour DateTime
        Date _24HourDt = _24HourSDF.parse(this.time); // parse time from DateTime
        return _12HourSDF.format(_24HourDt); // returns time in 12-hour format
        
		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to convert time.**";
		}
	}
	
}
