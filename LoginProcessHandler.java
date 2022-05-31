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
* A handler for processing login request.
* <p>
* This handler will process User login request, and also publish HTML page displaying if 
* login has been successful or not
* <p>
* @author Alasdair Craig
*/
public class LoginProcessHandler implements HttpHandler{

  /**
  * Creates a handler for processing login request.
  * <p>
  * This handler will will process User login request, and also publish HTML page 
  * displaying if login has been successful or not
  * <p>
  * @param HttpExchange  exchange for handling http requests. 
  * <p>
  * @author Alasdair Craig
  */
  public void handle(HttpExchange he) throws IOException {
   
    //System.out.println("In FormProcessHandler");
   
  
    BufferedReader in = new BufferedReader(new InputStreamReader(he.getRequestBody()));
   
    String line;
    String request = "";
    while( (line = in.readLine()) != null ){
      request = request + line;
    }

    //System.out.println("request is " +request);
    
    HashMap<String,String> map = Util.requestStringToMap(request);

    String userName = map.get("userName");
    String password = map.get("password");
    
    //System.out.println(map); 

    UserDao users = new UserDao();
    Boolean loginCheck = false;
    String error;

    error = "\n*** Wrong User Name and/or Password. Please try again. ***";   

    try{
      loginCheck = users.checkLoginCredentials(userName,password);    
    }catch(SQLException se){
      System.out.println(se.getMessage());
    }catch(NoSuchAlgorithmException ae){
      System.out.println(ae.getMessage());
    }

    if (loginCheck == false){
      System.out.println(error);
      System.out.println();
      
      BufferedWriter out= new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

      //System.out.println(request);

      he.sendResponseHeaders(200,0);
      out.write(
        "<html>" +
        " <head>" +
        "   <meta charset=\"utf-8\">" +
        "   <title>Login Error Page</title>" +
        "   <meta name=\"Login Error Page\" content=\"Login Error Page\">" +
        "   <link rel=\"stylesheet\"" +
        "   href=\"https://style.AlasdairCraig.repl.co/style.css\">" +
        " </head>" +
        " <body>" +
        "   <h1> Error! </h1>" +
        "   <h1> Wrong User Name and/or Password.</h1>" +
        "   <h2> Please try again.</h2>" +
        "   <div class=\"buttons\">" +
        "     <a class=\"midButton\" href=\"/loginForm\"> Go back </a>" +
        " </body>" +
        "</html>");
      out.close();
    }else{
      System.out.println("Logged on");
      System.out.println("--------------------");
      
      BufferedWriter out= new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

      he.sendResponseHeaders(200,0);
      out.write(
        "<html>" +
        " <head>" + 
        "   <meta charset=\"utf-8\">" +
        "   <title>Login Success Page</title>" +
        "   <meta name=\"Login Success Page\" content=\"Login Success Page\">" +
        "   <link rel=\"stylesheet\"" +
        "    href=\"https://style.AlasdairCraig.repl.co/style.css\">" +
        " </head>" +
        " <body>" +
        "   <h1> Login successful </h1>" +
        "   <h1> Welcome " + userName + "</h1>" +
        "   <div class=\"buttons\">" +
        "     <a class=\"midButton\" href=\"/adminLogin\"> Continue </a>" +
        " </body>" +
        "</html>");
      out.close();
    }
  }
}