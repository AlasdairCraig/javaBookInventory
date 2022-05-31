//craig_20104588

import java.io.OutputStream;
import java.io.*;
import java.io.OutputStreamWriter;
import java.util.HashMap;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedWriter;
import java.io.IOException;

import java.sql.SQLException;
import java.security.NoSuchAlgorithmException; 

/**
* A handler for confirming password & confirm password inputs match before new User insert.
* <p>
* This handler check for matching password inputs for a new User, and publish HTML page 
* showing whether passwords match or not    
* <p>
* @author Alasdair Craig
*/  
public class PassConfirmHandler implements HttpHandler{

  public static String userName;
  public static String password;

  /**
  * Creates a handler for confirming password & confirm password inputs match before new 
  * User insert.
  * <p>
  * This handler check for matching password inputs for a new User, and publish HTML page 
  * showing whether passwords match or not
  * <p>
  * @param HttpExchange  exchange for handling http requests. 
  * <p>
  * @author Alasdair Craig
  */  
  public void handle(HttpExchange he) throws IOException{

    //System.out.println("In PassConfirmHandler");

    BufferedReader in = new BufferedReader(new InputStreamReader(he.getRequestBody()));

    String line;
    String request = "";
    while( (line = in.readLine()) !=null){
      request = request + line;
    }

    //System.out.print(request);

    HashMap<String,String> map = Util.requestStringToMap(request);

    //System.out.println(map);

    userName = map.get("userName");
    password = map.get("password");
    String password2 = map.get("confirmPassword");

    if (password.equals(password2)){

      BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody() ));

      he.sendResponseHeaders(200,0);
      out.write(
        "<html>" +
        " <head> <title>Password Confirmation</title> "+
        "   <link rel=\"stylesheet\"" +
        "   href=\"https://style.AlasdairCraig.repl.co/style.css\">" +
        " </head>" +
        " <body>" +
        "   <h1> Add new user? </h1>"+
        " <div class=\"buttons\">" +
        "  <a class=\"confirmButton\" href=\"/newUserProcess\">Continue</a>" +
        "  <a class=\"logButton\" href=\"/newUser\">Go back</a>" +
        "</body>" +
        "</html>");
      out.close();
    }else{
      
      BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody() ));

      he.sendResponseHeaders(200,0);
      out.write(
        "<html>" +
        " <head> <title>Password Confirmation</title> "+
        "   <link rel=\"stylesheet\"" +
        "   href=\"https://style.AlasdairCraig.repl.co/style.css\">" +
        " </head>" +
        " <body>" +
        "   <h1>Passwords do not match, please try again.</h1>"+
        " <div class=\"buttons\">" +
        "  <a class=\"confirmButton\" href=\"/newUser\">Go back</a>" +
        "</body>" +
        "</html>");
      out.close();
    }
  }
}