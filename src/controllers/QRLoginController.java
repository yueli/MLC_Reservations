package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.ReservationQuery;
import helpers.UserHelper;
import model.PasswordService;
import model.Reservation;
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
/*    	
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
		
		//TODO
		//User loginUser = uh.authenticateUser(username, encryptedPass); //see if the person is UGA affiliated
		//NOTE: the authenticate method will not return the user's record ID
			
		//String userMyID = loginUser.getMyID();

		//user will come back null if not authenticated and come back w/ data if authenticated
		//System.out.println("QR Login Controller returned from authentication login user myID =  " + userMyID);	
		
        // if they are not valid, send back to QR login with message
		if (loginUser.getMyID() == null){ //if an empty object sent back (has no user data meaning they were not authenticated)
					
		    session.removeAttribute("message");
			message = "Error: Not Valid UGA Credentials";
			session.setAttribute("message", message);

			System.out.println("QR Login Controller: user doesn't exist!");

			url = "user/qrLogin2.jsp?building=" + building + "&room=" + room;
			
		}else{
			// user authenticated, create user object, and check to see if in our user table
			System.out.println("QR Login Controller: authenticated!");
			
			session.removeAttribute("message");
			message = "";
			
			// create a Student User object using the info we got back from authentication
			User user = new User();
			
			user.setMyID(loginUser.getMyID());
			user.setUserFirstName(loginUser.getUserFirstName());
			user.setUserLastName(loginUser.getUserLastName());
			user.setUserEmail(loginUser.getUserEmail());
					 
			// check to see if this user is banned. If this user is not even in the user table, the record id = 0
			// doing this before checking if in the user table because of the logic below having to do 
			// with getting reservation, checking in, etc.
			
			if(uh.alreadyBanned(user.getUserRecordID())) {
				
				// since they have already been banned, send them to a page telling them 
				
				session.setAttribute("user", user);
				System.out.println("QR Login Controller already banned! " + user.getMyID() + " " + user.getUserFirstName() + " " + user.getUserLastName() + " " + user.getUserEmail());				
				
				url="user/bannedUser.jsp";
				//forward our request along
				RequestDispatcher dispatcher = request.getRequestDispatcher(url);
				dispatcher.forward(request, response);
				
			}else{ // not banned - check if in user table
				boolean inTable = uh.inUserTable(user.getMyID());
				System.out.println("QR Login Controller returned from inUserTable " + inTable);				
			
				if (inTable){ // if in the user table update the last login filed
					
					uh.updateLastLogin(user.getMyID());	
					System.out.println("QR Login Controller in table " + user.getUserFirstName() + user.getUserLastName() + user.getUserEmail());	
				
				}else{ //not in user table so insert
					uh.insertUserTable(user.getMyID(), user.getUserFirstName(), user.getUserLastName(), user.getUserEmail());
					System.out.println("QR Login Controller NOT in user table recd id" + user.getMyID() + " " + user.getUserFirstName() + " " + user.getUserLastName() + " " + user.getUserEmail());	
				}
							
				// We now need to get the record id's of the user, building, and room to check for a reservation
				
				//since we know they are now in the table, get the user record id 
				int userRecordID = uh.getRecordID(user.getMyID());
				user.setUserRecordID(userRecordID);
				System.out.println("QR Login Controller get user Record ID =  " + userRecordID);			
	
				//create the reservation object
				Reservation reservation = new Reservation();		
				
				// get the building id for query for this building name
				ReservationQuery resvQuery = new ReservationQuery();
				int buildingID = 0;
				buildingID = resvQuery.getBuildingID(building);
				
				if (buildingID == 0){
					// ERROR there is no building record with this building QR name
					
					session.setAttribute("user", user);
					System.out.println("QR Login Controller building qr name doesn't exist " + building);
					
					url="user/qrError.jsp"; 
					message = "The building name " + building + " on the QR code doesn't exist. Alert the admins.";
					request.setAttribute("message", message);
					//forward our request along
					RequestDispatcher dispatcher = request.getRequestDispatcher(url);
					dispatcher.forward(request, response);

				}else{
					// the building QR name is good and we have the building record ID :)
					
					// get the room id for query using room number and building id
					int roomID = 0;
					roomID = resvQuery.getRoomID(room, buildingID);
					
					System.out.println("QR Login Controller room qr # " + room + " has recd id = " + roomID);

					// check if room id = 0, and if so send to error page w/ message
					
					if (roomID == 0){ //have an error
						
						session.setAttribute("user", user);
						System.out.println("QR Login Controller room qr name doesn't exist " + room);
						
						url="user/qrError.jsp"; 
						message = "The room number " + room + " for building " + building + " on the QR code doesn't exist. Alert the admins.";
						request.setAttribute("message", message);
						//forward our request along
						RequestDispatcher dispatcher = request.getRequestDispatcher(url);
						dispatcher.forward(request, response);

					}else{ // found room record
						
						// check to see if there is a free reservation with this person as primary or secondary at this current date
						// and time in this building in this room
						int reservationID = 0;
						reservationID = resvQuery.getUserReservation(userRecordID, buildingID, roomID);
						
						if (reservationID == 0){ // didn't find reservation 
							System.out.println("QR Login Controller reservation not found ");
							session.setAttribute("user", user);
							
							url="user/qrError.jsp"; 
							message = "A reservation for room " + room + " in the building " + building + " was not found. "
									+ "This may be caused by: "
									+ "<ul>"
									+ "<li>this is the wrong building or room</li>"
									+ "<li>a reservation was not made for this room under this name</li>"
									+ "<li>you are too late to check in - you must check in within ten minutes past the reservation time</li>"
									+ "</ul>"
									+ "<p>See the FAQ for more information.</p>";
							
							request.setAttribute("message", message);
							
							//forward our request along
							RequestDispatcher dispatcher = request.getRequestDispatcher(url);
							dispatcher.forward(request, response);
						
						}else{ 
							// there is a reservation check them in and send to "Checked In"
							
							System.out.println("QR Login Controller reservation found - checking in ");
							
							if (resvQuery.checkInUser(reservationID, userRecordID)) { //if successfully checked in user
								url = "user/qrCheckInSuccess.jsp";
								
								message = "Successfully checked into the " + building + ", room " + room
										+ " for " + user.getUserFirstName() + " " + user.getUserLastName() + ". ";
								message += "<br /><br />";
								message	+= "<form action='https://cas.uga.edu/cas/logout'>";
								message += "<input type='submit' value='Log Out'>";
								message += "</form>";
								message += "<br /><br />";
								
							}else{
								
								url = "user/qrError.jsp";	
								message = "Error checking in. Contact administrators.";
								
							}
							
							request.setAttribute("message", message);
							
							//forward our request along
							RequestDispatcher dispatcher = request.getRequestDispatcher(url);
							dispatcher.forward(request, response);

							
							
						} // end if reservation found
			        
					} // end if room # QR exists
					
					
				} // end if building QR name exists

		     
			}//end if not banned user
 		
        
		}// end if not authenticated
        
    } // end do post
    
  */  
 //end servlet
}
}