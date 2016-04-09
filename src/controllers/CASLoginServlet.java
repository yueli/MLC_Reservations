/* @author: Brian Olaogun and Ginger Nix
 * 
 * This is the servlet that CAS points to after authenticating the user.
 * It determines if the person is coming in from a QR code scan, is an admin, or is a plain ole user.
 * If they are not coming in from the QR scan and not an admin, a check is done to see if they are a student.
 * If not, they are sent to an error page where they can only log out
 * 
 */

package controllers;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Iterator;
import java.util.Enumeration;

import org.jasig.cas.client.authentication.AttributePrincipal;

import org.jasig.cas.client.validation.Saml11TicketValidator;

import helpers.AdminUserHelper;
import helpers.UserHelper;
import model.Admin;
import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jasig.cas.client.authentication.AttributePrincipal;

/**
 * Servlet implementation class CASLoginServlet
 */
@WebServlet("/CASLoginServlet")
public class CASLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String url;
	private HttpSession session; 
	       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CASLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get our current session
		session = request.getSession();
	
		int recordID = 0;

		// CAS stuff
		Enumeration keys = session.getAttributeNames();
		
		while (keys.hasMoreElements())
		{
		  String key = (String)keys.nextElement();
		  //out.println(key + ": " + session.getValue(key) + "<br>"); // getValue deprecated
		  //table += "** " + key + ":" + session.getAttribute(key) + "<br />";		  // had this for testing
		}
		
		User loggedInUser = new User(); // create the user object to pass forward		
		
		String employeeType = ""; /*used for grabbing the employee type from CAS */
		
		AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();
		Map attributes = principal.getAttributes();
		Iterator attributeNames = attributes.keySet().iterator();
		
		for (; attributeNames.hasNext();) {

			String attributeName = (String) attributeNames.next();			      
			String attributeValue = (String) attributes.get(attributeName);

		      //=====================
		      if (Objects.equals("lastName", attributeName)){
		    		loggedInUser.setUserLastName(attributeValue);    
		    		//table += " ** using this attirbute last name ** ";
		      }
		      if (Objects.equals("firstName", attributeName)){
		    		loggedInUser.setUserFirstName(attributeValue);    
		    		//table += " ** using this attirbute first name ** ";
		      }
		      if (Objects.equals("CN", attributeName)){
		    		loggedInUser.setMyID(attributeValue);    
		    		//table += " ** using this attribute CAN ** ";
		      }
		      if (Objects.equals("ugaEmail", attributeName)){
		    	  	System.out.println("************************");
		    	  	System.out.println();
		    	  	System.out.println("CAS VARIABLE LOGGING IN ugaEmail: " + attributeValue); // TEST IN CAS STAGE
		    	  	System.out.println();
		    	  	System.out.println("************************");
		    		
		      }
		      if (Objects.equals("fsCode", attributeName)){
		    	  	System.out.println("************************");
		    	  	System.out.println();
		    	  	System.out.println("CAS VARIABLE LOGGING IN fsCode: " + attributeValue); // TEST IN CAS STAGE
		    	  	System.out.println();
		    	  	System.out.println("************************");
		    		
		      }
		      if (Objects.equals("employeeType", attributeName)){
		    	  	System.out.println("************************");
		    	  	System.out.println();
		    	  	System.out.println("CAS VARIABLE LOGGING IN employeeType: " + attributeValue); // THIS WORKED!!!!
		    	  	System.out.println();
		    	  	System.out.println("************************");
		    		
		    	  	/* grabbing the employee's type to see if they are a student
		    	  	 * not going to store it in the object since only needed during logging in
		    	  	 */
		    	  	
		    	  	employeeType = attributeValue;
		    	  	
		      }
		      //=====================

		}
		
		// used for checking below

		AdminUserHelper adminUserHelper = new AdminUserHelper();
		
		System.out.println("Login Servlet logged in user my id = " + loggedInUser.getMyID());

		System.out.println("Login Servlet logged in user employee type = " + employeeType);
		 
		boolean inAdminUserTable = false;	
		inAdminUserTable = adminUserHelper.inAdminTable(loggedInUser.getMyID());

		// is this request coming from a QR checkin?
		// if so, process as QR, else check to see
		// if this user's myID in the admin table
		// if so, send them to the admin home page
		// else send them to the user home home
				
		// check to see if this url is coming from a QR checkin
		// by looking for a parameter
		
		String building = request.getParameter( "building" );

		String room = request.getParameter( "room" );
				 
		//===== QR LOGIN CHECK =====//
		
		if(building != null && !building.isEmpty()){
			// we have a parameter so we have a QR checkin
			
			User user = new User();			
			user.setMyID(loggedInUser.getMyID());
			user.setUserFirstName(loggedInUser.getUserFirstName());
			user.setUserLastName(loggedInUser.getUserLastName());

			session.setAttribute("user", user);
			session.setAttribute( "building", building );
			session.setAttribute( "room", room );
			
			url = "/QRLoginController";
		
		//===== IN ADMIN TABLE CHECK =====//
			
		}else if (inAdminUserTable){
			url = "AdminHome";	
			//set the logged in user to be an admin logged in user and pass along
			Admin loggedInAdminUser  = new Admin();
			
			// get all the logged in admin's data from the admin table based on their MyID
			loggedInAdminUser = adminUserHelper.getAdminData(loggedInUser.getMyID());
			
			session.setAttribute("loggedInAdminUser", loggedInAdminUser);
			
		//===== USER PROCESS ====//
			
		}else{
			// this is a plain ole user and not an admin
			
			/*------------
			 * THIS MAY CHANGE IF DATA BACK FROM CAS HAS MORE THAN ONE EMPLOYEE TYPE
			 * OR WE USE FSCODE INSTEAD
			 */
			
			/* check to see if this user is a student by looking at the employee type */
			/* the employee type looks like the FSCode so going to check for 00 */
			
			// test1010 should be emp type 01
			
			if (!employeeType.equals(00)) { 
				// they are not a student so send them to a page where all they can do is log out
				System.out.println("CASLogin Servlet logged in employeeType != 00 - " + employeeType);
				
				url = "user/notAStudent.jsp";
				
			}else{ //they are students
		
				// so look in the user table to see if they already exist
				// set up the user object since not an admin
				User user = new User();			
				user.setMyID(loggedInUser.getMyID());
				user.setUserFirstName(loggedInUser.getUserFirstName());
				user.setUserLastName(loggedInUser.getUserLastName());
				
				String userEmail = loggedInUser.getMyID() + "@uga.edu";
				
				/* HARD CODED FOR TESTING */
				
				user.setUserEmail("ganix@uga.edu"); 
				
				
							
				UserHelper userHelper = new UserHelper();			
				boolean inTable = userHelper.inUserTable(user.getMyID());				
				
				if (inTable){ // they are in the user's table
					// get the user's record ID from the user table to check to see if banned
					
					recordID = userHelper.getRecordID(user.getMyID());
	
					user.setUserRecordID(recordID);
		
					System.out.println("CAS login: record ID " + user.getUserRecordID());
					
					if(userHelper.alreadyBanned(recordID)) {
						// since they have already been banned, send them to a page telling them 
						
	
						user.setUserRecordID(recordID);
						session.setAttribute("user", user);
						url="user/bannedUser.jsp";
						
						
					}else{ 	// they are in the table and not banned
							// update the last login date field
						userHelper.updateLastLogin(user.getMyID());	
	
						user.setUserRecordID(recordID);
						session.setAttribute("user", user);
						url = "UserHome";
					}
					
				}else{ //authenticated but not in user table, so add them
					
					//userHelper.insertUserTable(loggedInUser.getMyID(), loggedInUser.getUserFirstName(), loggedInUser.getUserLastName(), loggedInUser.getUserEmail()); 
					userHelper.insertUserTable(user.getMyID(), user.getUserFirstName(), user.getUserLastName(), " " ); 
				
					recordID = userHelper.getRecordID(user.getMyID());
	
					user.setUserRecordID(recordID);
					session.setAttribute("user", user);
					url = "UserHome";
					
				}// end else not in user table
				
				// by this time the user object should have recordID, myID, fname, lname and email
				session.setAttribute("user", user);
				
			}// end else they are students	

		}// end else this is a plan ole user
		
		//forward our request along
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
