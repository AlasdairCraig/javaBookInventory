//craig_20104588

import java.io.OutputStream;
import java.io.*;
import java.io.OutputStreamWriter;
import java.util.HashMap;

import com.sun.net.httpserver.HttpHandler;

import org.sqlite.SQLiteException;

import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedWriter;
import java.io.IOException;

import java.sql.SQLException;
import java.security.NoSuchAlgorithmException; 

/**
* A handler for processing new User request.
* <p>
* This handler will process new User request, and also publish HTML page displaying if 
* insert has been successful or not, and values for User inserted
* <p>
* @author Alasdair Craig
*/
public class NewUserProcessHandler implements HttpHandler{

  /**
  * Creates a handler for processing new User request.
  * <p>
  * This handler will will process new User request, and also publish HTML page displaying 
  * if insert has been successful or not, and values for User inserted
  * <p>
  * @param HttpExchange  exchange for handling http requests. 
  * <p>
  * @author Alasdair Craig
  */
  public void handle(HttpExchange he) throws IOException{

    //System.out.println("In NewUserProcessHandler");

    String userName = PassConfirmHandler.userName;
    String password = PassConfirmHandler.password;

    UserDao users = new UserDao();
    User newUser = new User(userName, password);

    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody() ));

    try{
      
      User checkUser = users.getUserName(userName);
      if (checkUser != null) throw new SQLException();
      
      System.out.println("Adding new user");
      System.out.println("--------------------");

      users.insertUser(newUser);

      he.sendResponseHeaders(200,0);
      out.write(
        "<html>" +
        " <head>" +
        "   <title>New user added</title>" +
        "   <link rel=\"stylesheet\"" +
        "   href=\"https://style.AlasdairCraig.repl.co/style.css\">" +
        " </head>" +
        " <body>" +
        "  <h1> New user added</h1>"+
        "  <div class=\"buttons\">" +
        "     <a class=\"logButton\" href=\"/login\"> Log in </a>" +
        "     <br>" +
        "     <br>" +
        "  </div>" +
        " <table class=\"table\">" +
        "   <thead>" +
        "     <tr>" +
        "       <th>User Name</th>" +
        "       <th>Password</th>" +
        "     </tr>" +
        "   </thead>" +
        "   <tbody>" +
        "     <tr>" +
        "       <td>"+ newUser.getUserName() + "</td>" +
        "       <td>*****</td>" +
        "     </tr>" + 
        "   </tbody>" +
        "   </table>" +
        " </body>" +
        "</html>");
      out.close();
    }catch(SQLException se){
      System.out.println("Error: User exists already");
        System.out.println("--------------------");
        //System.out.println(checkUser);
    
        he.sendResponseHeaders(200,0);
        out.write(
        "<html>" +
        " <head> <title>Error User Exists</title> "+
        "   <link rel=\"stylesheet\"" +
        "   href=\"https://style.AlasdairCraig.repl.co/style.css\">" +
        " </head>" +
        " <body>" +
        " <h1>Error: User exists in system already</h1>"+
        " <div class=\"buttons\">" +
        "   <a class=\"searchBookButton\" href=\"/newUser\">Go back</a>" +
        "   <br>" +
        "   <br>" +
        " </div>" +
        " <table class=\"table\">" +
        "   <thead>" +
        "     <tr>" +
        "       <th>User Name</th>" +
        "       <th>Password</th>" +
        "     </tr>" +
        "   </thead>" +
        "   <tbody>" +
        "     <tr>" +
        "       <td>"+ newUser.getUserName() + "</td>" +
        "       <td>*****</td>" +
        "     </tr>" + 
        "   </tbody>" +
        "   </table>" +
        " </body>" +
        "</html>");
      out.close();
    }catch(NoSuchAlgorithmException ae){
      System.out.println(ae.getMessage());
    }
  }
}
