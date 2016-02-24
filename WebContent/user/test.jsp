
<%@page contentType="text/html"%>

<%@page pageEncoding="UTF-8"%>

<%@ page import="java.util.Map" %>

<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Enumeration" %>

<%@ page import="org.jasig.cas.client.authentication.AttributePrincipal" %>
<%@ page import="org.jasig.cas.client.validation.Saml11TicketValidator" %>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"

   "http://www.w3.org/TR/html4/loose.dtd">

 

<html>

    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>CAS Test</title>

    </head>

    <body>

 

    <h1>CAS Test</h1>

 

    <p><%= request.getRemoteUser() %></p>
    
    
    
    
    
    
    
    
<%
Enumeration keys = session.getAttributeNames();
while (keys.hasMoreElements())
{
  String key = (String)keys.nextElement();
  out.println(key + ": " + session.getValue(key) + "<br>");
}

%>
<br> <br>

 <%

AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();



//Saml11TicketValidator  AbstractUrlBasedTicketValidator = (Saml11TicketValidator)request.getAbstractUrlBasedTicketValidator(); 

Map attributes = principal.getAttributes();
 
//List list = AbstractUrlBasedTicketValidator.getClass();
 

Iterator attributeNames = attributes.keySet().iterator();

 

out.println("<table>");

 

for (; attributeNames.hasNext();) {

out.println("<tr><th>");

//String attributeName = (String) attributeNames.next();
String attributeName = (String) attributeNames.next();


      out.println(attributeName);

      out.println("</th><td>");

      Object attributeValue = attributes.get(attributeName);

      out.println(attributeValue);

      out.println("</td></tr>");

}

 

out.println("</table>");

 

%>

    </body>

</html>