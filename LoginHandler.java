//craig_20104588

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedWriter;
import java.io.IOException;

/**
* A handler for obtaining User login information.
* <p>
* This handler will publish HTML page requesting User login information(userName; Password)
* <p>
* @author Alasdair Craig
*/
public class LoginHandler implements HttpHandler{

  /**
  * Creates a handler for obtaining User login information.
  * <p>
  * This handler will publish HTML page requesting User login information(userName; 
  * Password)    
  * <p>
  * @param HttpExchange  exchange for handling http requests. 
  * <p>
  * @author Alasdair Craig
  */
  public void handle(HttpExchange he) throws IOException {
    he.sendResponseHeaders(200,0);
    
    System.out.println("Waiting for login information....");
    System.out.println("--------------------");
    
    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
    
    out.write(
      "<html>" +
      " <head>" +
	    "   <meta charset=\"utf-8\">" +
	    "   <title>Login Form</title>" +
	    "   <meta name=\"Login Form\" content=\"Login Form\">" +
	    "   <link rel=\"stylesheet\"" +
      "   href=\"https://style.AlasdairCraig.repl.co/style.css\">" +
      " </head>" +
      " <body>" +
      "   <h1>Login</h1>" +
	    "   <div>" +
		  "     <form action=\"/loginProcess\" method=\"post\">" +
			"       <table width=\"100%\" border=\"0\" cellpadding=\"6\">" +
      "         <tbody>" +
      "           <tr>" +
      "             <td width=\"50%\" style=\"text-align: right\">" +
      "             <label for=\"userName\">User Name:</label></td>" +
      "             <td width=\"50%\" style=\"text-align: left\">" +
      "             <input name=\"userName\" type=\"text\" id=\"userName\"" +
      "             title=\"userName\" size=\"35\" maxlength=\"50\" required></td>" +
      "           </tr>" +
      "           <tr>" +
      "             <td style=\"text-align: right\">" +
      "             <label for=\"password\">Password:</label></td>" +
      "             <td style=\"text-align: left\">" +
      "             <input name=\"password\" type=\"password\" id=\"password\"" +
      "             size=\"35\" maxlength=\"50\" required></td>" +
      "           </tr>" +
      "           <tr>" +
      "             <td colspan=\"2\" style=\"text-align: center\">" +
      "             <input type=\"submit\" name=\"submit\" id=\"submit\"" + 
      "             value=\"Submit\"></td>" + 
      "           </tr>" +
      "         </tbody>" +
      "     </table>" +
      "   </form>" +
      "  </div>" +
      "  <br>" +
      "  <br>" +
      "  <br>" +
      "  <div class=\"buttons\">" +
      "     <a class=\"newUserButton\" href=\"/newUser\"> +New User </a>" +
      "  </div>" +
      " </body>" +
      "</html>");
    out.close();
  }
}