/**
 *
 */
package model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;




/**
 * @author Brian Olaogun
 * Class used to send confirmation emails.
 * sample source code: https://en.wikipedia.org/wiki/JavaMail
 */
public class Email {
	// Fields for reservation confirmation email
	private String to;
	private String cc;
	private String reserveDate;
	private String startTime;
	private String endTime;
	private String building;
	private String roomNumber;
	
	// Constructors
	/**
	 * No parameter constructor
	 */
	public Email(){
		this.to = "";
		this.cc = "";
		this.reserveDate = "";
		this.startTime = "";
		this.endTime = "";
		this.building = "";
		this.roomNumber = "";
	}
	
	/**
	 * 
	 * @param to
	 * @param cc
	 * @param reserveDate
	 * @param startTime
	 * @param endTime
	 * @param building
	 * @param roomNumber
	 */
	public Email(String to, String cc, String reserveDate, String startTime, String endTime, String building, String roomNumber){
		this.to = to;
		this.cc = cc;  
		this.reserveDate = reserveDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.building = building;
        this.roomNumber = roomNumber;
	}

	// Getters & Setters
	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * @return the cc
	 */
	public String getCc() {
		return cc;
	}

	/**
	 * @param cc the cc to set
	 */
	public void setCc(String cc) {
		this.cc = cc;
	}

	/**
	 * @return the reserveDate
	 */
	public String getReserveDate() {
		return reserveDate;
	}

	/**
	 * @param reserveDate the reserveDate to set
	 */
	public void setReserveDate(String reserveDate) {
		this.reserveDate = reserveDate;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the building
	 */
	public String getBuilding() {
		return building;
	}

	/**
	 * @param building the building to set
	 */
	public void setBuilding(String building) {
		this.building = building;
	}

	/**
	 * @return the roomNumber
	 */
	public String getRoomNumber() {
		return roomNumber;
	}

	/**
	 * @param roomNumber the roomNumber to set
	 */
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	// Email Methods
	/**
	 * 
	 * @param to
	 * @param cc
	 * @param reserveDate
	 * @param startTime
	 * @param endTime
	 * @param building
	 * @param roomNumber
	 * Send Email for reservation confirmation
	 */
	public void sendMail(String to, String cc, String reserveDate, String startTime, String endTime, String building, String roomNumber) {
        // ROOM RESERVATION DETAILS
        this.reserveDate = reserveDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.building = building;
        this.roomNumber = roomNumber;
		
		// EMAIL ADDRESSES
        this.cc = cc;
        this.to = to;
        String from = "example@email.com"; //TODO change from Email
        
        // MAIL SERVER
        String host = "smtp.office365.com";

        // Create properties for the Session
        Properties props = new Properties();

        // If using static Transport.send(),
        // need to specify the mail server here
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "25");
        
        // To see what is going on behind the scene
        props.put("mail.debug", "true");

        // Get a session
        //Session session = Session.getInstance(props);
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
        		   //TODO Set Username & Password
                   private String username = "";
                   private String password = "";

				protected PasswordAuthentication getPasswordAuthentication() {
                      return new PasswordAuthentication(this.username, this.password);
                   }
       	});

        try {
            // Get a Transport object to send e-mail
            Transport bus = session.getTransport("smtp");

            // Connect only once here
            // Transport.send() disconnects after each send
            // Usually, no username and password is required for SMTP
            bus.connect();
            //bus.connect("smtpserver.yourisp.net", "username", "password");

            // Instantiate a message
            Message msg = new MimeMessage(session);

            // Set message attributes
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            
            // Parse a comma-separated list of email addresses. Be strict.
            InternetAddress[] address2 = {new InternetAddress(cc)};
            msg.setRecipients(Message.RecipientType.CC,
                                InternetAddress.parse(cc, true));
            
            // Set the subject and date
            msg.setSubject(getBuilding() + " Room Reservation");
            msg.setSentDate(new Date());


            setHTMLContent(msg, getReserveDate(), getStartTime(), getEndTime(), getBuilding(), getRoomNumber());
            msg.saveChanges();
            bus.sendMessage(msg, address);
            bus.sendMessage(msg, address2);

            bus.close();

        }
        catch (MessagingException mex) {
            // Prints all nested (chained) exceptions as well
            mex.printStackTrace();
            
            // How to access nested exceptions
            while (mex.getNextException() != null) {
                // Get next exception in chain
                Exception ex = mex.getNextException();
                ex.printStackTrace();
                if (!(ex instanceof MessagingException)) break;
                else mex = (MessagingException)ex;
            }
        }
    }
	

    // Set a single part html content.
    // Sending data of any type is similar.
	/**
	 * 
	 * @param msg
	 * @param reserveDate
	 * @param startTime
	 * @param endTime
	 * @param building
	 * @param roomNumber
	 * @throws MessagingException
	 * Set the message of the email.  This is an HTML email message
	 */
    public static void setHTMLContent(Message msg, String reserveDate, String startTime, String endTime, String building, String roomNumber) throws MessagingException {

        String html = "<html><head><title>" +
                        msg.getSubject() +
                        "</title></head><body><h1>" +
                        msg.getSubject() +
                        "</h1><p style='font-size:120%'>Thanks for reserving a room at " + building + "! " +
                        "Your reservation is set for room " + roomNumber + " on " + reserveDate + " from " + startTime + " to " + endTime + ". <br><br>" + 
                        "To check-in, view, or cancel your reservation, please visit [insert website].</body></html>";

        // HTMLDataSource is a static nested class
        msg.setDataHandler(new DataHandler(new HTMLDataSource(html)));
    }

    /*
     * Static nested class to act as a JAF datasource to send HTML e-mail content
     */
    static class HTMLDataSource implements DataSource {
        private String html;

        public HTMLDataSource(String htmlString) {
            html = htmlString;
        }

        // Return html string in an InputStream.
        // A new stream must be returned each time.
        public InputStream getInputStream() throws IOException {
            if (html == null) throw new IOException("Null HTML");
            return new ByteArrayInputStream(html.getBytes());
        }

        public OutputStream getOutputStream() throws IOException {
            throw new IOException("This DataHandler cannot write HTML");
        }

        public String getContentType() {
            return "text/html";
        }

        public String getName() {
            return "JAF text/html dataSource to send e-mail only";
        }
    }
	 
} 
