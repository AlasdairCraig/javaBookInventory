//craig_20104588

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedWriter;
import java.io.IOException;

/**
* A handler for obtaining information on new User
* <p>
* This handler will publish HTML page requesting information for new User
* <p>
* @author Alasdair Craig
*/
public class NewUserHandler implements HttpHandler{

  /**
  * Creates a handler for obtaining information on new User.
  * <p>
  * This handler will publish HTML page requesting information for new User
  * <p>
  * @param HttpExchange  exchange for handling http requests. 
  * <p>
  * @author Alasdair Craig
  */
  public void handle(HttpExchange he) throws IOException {
    he.sendResponseHeaders(200,0);

    //System.out.println("In the NewUserHandler");
    
    System.out.println("Waiting for new user information....");
    System.out.println("--------------------");
    
    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
    
    out.write(
      "<html>" +
      " <head>" +
	    "   <meta charset=\"utf-8\">" +
	    "   <title>New User Form</title>" +
	    "   <meta name=\"New User Form\" content=\"New User Form\">" +
	    "   <link rel=\"stylesheet\"" +
      "   href=\"https://style.AlasdairCraig.repl.co/style.css\">" +
      " </head>" +
      " <body>" +
      "   <h1>New User</h1>" +
	    "   <div>" +
		  "     <form action=\"/passConfirm\" method=\"post\">" +
			"       <table width=\"100%\" border=\"0\" cellpadding=\"6\">" +
      "         <tbody>" +
      "           <tr>" +
      "             <td width=\"50%\" style=\"text-align: right\">" +
      "             <label for=\"userName\">User Name:</label></td>" +
      "             <td width=\"50%\" style=\"text-align: left\">" +
      "             <input name=\"userName\" type=\"text\" id=\"userName\"" +
      "             size=\"35\" maxlength=\"50\" pattern=\"(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{5,}\" title=\"Must contain at least one number and one uppercase and lowercase letter, and at least 5 or more characters\" required></td>" +
      "           </tr>" +
      "           <tr>" +
      "             <td style=\"text-align: right\">" +
      "             <label for=\"password\">Password:</label></td>" +
      "             <td style=\"text-align: left\">" +
      "             <input name=\"password\" type=\"password\" id=\"password\"" +
      "             size=\"35\"" +
      "             maxlength=\"50\" pattern=\"(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{5,}\" title=\"Must contain at least one number and one uppercase and lowercase letter, and at least 5 or more characters\" required></td>" +
      "           </tr>" +
      "           <tr>" +
      "             <td style=\"text-align: right\">" +
      "             <label for=\"confirmPassword\">Confirm Password:</label></td>" +
      "             <td style=\"text-align: left\">" +
      "             <input name=\"confirmPassword\" type=\"password\"" +
      "             id=\"confirmPassword\"" +
      "             size=\"35\"" +
      "             maxlength=\"50\" pattern=\"(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{5,}\" title=\"Must contain at least one number and one uppercase and lowercase letter, and at least 5 or more characters\" required></td>" +
      "           </tr>" +
      "           <tr>" +
      "             <td colspan=\"2\" style=\"text-align: center\">" +
      "             <input type=\"submit\" name=\"submit\" id=\"submit\"" + 
      "             value=\"Submit\"></td>" + 
      "           </tr>" +
      "         </tbody>" +
      "     </table>" +
      "   </form>" +
      " </body>" +
      "</html>");
    out.close();
  }
}