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

/**
* A handler for confirming book deletion from database.
* <p>
* This handler will publish HTML page asking User to confirm deletion of book.    
* <p>
* @author Alasdair Craig
*/  
public class DeleteConfirmHandler implements HttpHandler{

  public static int dID;

  /**
  * Creates a handler for confirming book deletion from database.
  * <p>
  * This handler will publish HTML page asking User to confirm deletion of book.    
  * <p>
  * @param HttpExchange  exchange for handling http requests. 
  * <p>
  * @author Alasdair Craig
  */  
  public void handle(HttpExchange he) throws IOException {
   
    //System.out.println("In DeleteConfirmHandler");

    System.out.println("Waiting for delete confirmation....");
    System.out.println("--------------------");
   
  
    BufferedReader in = new BufferedReader(new InputStreamReader(he.getRequestBody()));
   
    String line;
    String request = "";
    while( (line = in.readLine()) != null ){
      request = request + line;
    }

    //System.out.println("request is " + request); 

    HashMap<String,String> map = Util.requestStringToMap(request);

    dID = Integer.parseInt(map.get("deleteID"));

    BookDao books = new BookDao();
     
    //System.out.println(map); 

    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

    he.sendResponseHeaders(200,0);
    out.write(
      "<html>" +
      " <head>" +
      "   <meta charset=\"utf-8\">" +
      "   <title>Delete Confirm Page</title>" +
      "   <meta name=\"Delete Confirm Page\" content=\"Delete Confirm Page\">" +
      "   <link rel=\"stylesheet\"" +
      "   href=\"https://style.AlasdairCraig.repl.co/style.css\">" +
      " </head>" +
      " <body>" +
      "   <h1> Are you sure you want to delete book: ID " + dID + " </h1>" +
      "   <div class=\"buttons\">" +
      "   <form action=\"/deleteConfirm\" method=\"post\">" +
      "     <table width=\"100%\" border=\"0\" cellpadding=\"6\">" +
      "       <tbody>" +
      "         <tr>" +
      "           <td style=\"text-align: right\"><label for=\"deleteChoice\">" +
      "           Delete or Add to Sales?</label>></td>" +
      "           <td>" +
      "             <p><label>" +
      "             <input type=\"radio\" name=\"deleteChoice\" value=\"sale\"" + 
      "             id=\"deleteChoice_0\">Record Sale</label>" +
      "             <br><label>" +
      "             <input type=\"radio\" name=\"deleteChoice\" value=\"delete\"" +
      "             id=\"deleteChoice_1\">Permenently delete book entry</label>" +
      "             </p></td>" +
      "         </tr>" +
      "         <tr>" +
      "           <td colspan=\"2\" style=\"text-align: center\">"+
      "           <input type=\"submit\" name=\"submit\" id=\"submit\"" + 
      "           value=\"Submit\"></td>" + 
      "         </tr>" +
      "       </tbody>" +
      "     </table>" +
      "   </form>" +
      "     <a class=\"midButton\" href=\"/adminLogin\"> Return to admin panel </a>" +
      " </body>" +
      "</html>");
    out.close();
  }
}