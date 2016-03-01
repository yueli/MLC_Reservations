package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.UserHelper;
import model.PasswordService;
import model.User;

/**
 * Servlet implementation class QRLoginController
 */
@WebServlet("/QRLoginController")
public class QRLoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private HttpSession session; 
    private String url;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QRLoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	
    	
    	System.out.println("GQLoginController = beginning");
    	// called from qrLogin.jsp
    	
        //get our current session
        session = request.getSession();
        session.removeAttribute("message");
    	
    	String message = "";
    	
        String building = (String) session.getAttribute("building");
        String room = (String) session.getAttribute("room");
        
        System.out.println("GQLoginController resv building = " + building);
        System.out.println("GQLoginController resv room = " + room);
    	
        //pull the fields from the form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
      
        System.out.println("GQLoginController username = " + username);
    	
        // have the username and password
        // authenticate credentials to see if uga
        
		//encrypt the password 
		PasswordService pws = new PasswordService();
		String encryptedPass = pws.encrypt(password);
		
		//set up connection to the database
		UserHelper uh = new UserHelper(); 
		
		//see if they are UGA affiliated
		User loginUser = uh.authenticateUser(username, encryptedPass); //see if the person is UGA affiliated
		//NOTE: the authenticate method will not return the user's record ID
	
		
		String userMyID = loginUser.getMyID();

		//user will come back null if not authenticated and come back w/ data if authenticated
		System.out.println("QR Login Controller returned from authentication login user myID =  " + userMyID);	

		

		
        // if they are not valid, send back to QR login with message
		if (loginUser.getMyID() == null){ //if a non-empty object sent back (has user data meaning they were authenticated)
					
		    session.removeAttribute("message");
			message = "Error: Not Valid UGA Credentials";
			session.setAttribute("message", message);

			System.out.println("QR Login Controller: user doesn't exist!");

			url = "user/qrLogin2.jsp?building=" + building + "&room=" + room;
			
		}else{
			System.out.println("QR Login Controller: user DOES	 exist!");
			
			session.removeAttribute("message");
			message = "";
			
			// create a Student User object using the info we got back from authentication
			User user = new User();
			
			user.setMyID(loginUser.getMyID());
			user.setUserFirstName(loginUser.getUserFirstName());
			user.setUserLastName(loginUser.getUserLastName());
			user.setUserEmail(loginUser.getUserEmail());
			
			// if they are valid, get their user id from the user table using their MyID
			
			//get the user record id to look up reservations
			int recordID = uh.getRecordID(user.getMyID());
			user.setUserRecordID(recordID);
			System.out.println("QR Login Controller get user Record ID =  " + recordID);

			// check to see if there is a reservation with this person
			// as primary or secondary on a reservation at this current time
		
			//if (userHasReservation){ //CHANGE TO METHOD AFTER TALKING TO VICTORIA

				// if there is a reservation and they are within the time to check in,
				// check them in and send to "Checked In" page and log them out
				
				//checkInUser(); // CHANGE TO METHOD AFTER TALKING TO VICTORIA
				
				//url = "user/qrCheckInSuccess.jps";
				
				
				
			//}else{ // if there is no reservation, send back to login with message
				
			//	message = "There is no reservation available for check-in.";
			//	url = "users/qrCheckInFailure.jsp";
			//}
        
			
        
			// if there is a reservation, but they are too late to check in, send back to login with message ???
        
			
			
			
			url = "user/qrConfirmation.jsp";	
	        
        
		}
		//session.setAttribute("building",building);
        
        
		request.setAttribute("message", message);

        //forward our request along
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
        
        
        
    }
}
