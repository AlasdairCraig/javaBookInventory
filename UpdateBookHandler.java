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
* A handler for obtaining information on existing book to update.
* <p>
* This handler will publish HTML page showing existing book and values, to be overwritten
* <p>
* @author Alasdair Craig
*/
public class UpdateBookHandler implements HttpHandler{
  
  static int uID;
  
  /**
  * Creates a handler for obtaining information on existing book to update.
  * <p>
  * This handler will publish HTML page showing existing book and values, to be overwritten
  * <p>
  * @param HttpExchange  exchange for handling http requests. 
  * <p>
  * @author Alasdair Craig
  */
  public void handle(HttpExchange he) throws IOException {
   
    //System.out.println("In UpdateBookHandler");

    System.out.println("Waiting for updated book information....");
    System.out.println("--------------------");
   
    BufferedReader in = new BufferedReader(new InputStreamReader(he.getRequestBody()));
   
    String line;
    String request = "";
    while( (line = in.readLine()) != null ){
      request = request + line;
    }

    //System.out.println("request is " + request); 

    HashMap<String,String> map = Util.requestStringToMap(request);

    uID = Integer.parseInt(map.get("updateID"));

    BookDao books = new BookDao();
            
    //System.out.println(map); 

    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody() ));

    //System.out.println(request);

    try{
      Book b = books.getBookByID(uID);
      
      he.sendResponseHeaders(200,0);
      out.write(
      "<html>" +
      " <head>" +
      "   <meta charset=\"utf-8\">" +
      "   <title>Update Book Form</title>" +
      "   <meta name=\"Update Book Form\" content=\"Update Book Form\">" +
      "   <link rel=\"stylesheet\"" +
      "   href=\"https://style.AlasdairCraig.repl.co/style.css\">" +
      " </head>" +
      " <body>" +
      "   <h1>Update Book Entry</h1>" +
      "   <div>" +
      "     <form action=\"/updateBookProcess\" method=\"post\">" +
      "       <table width=\"100%\" border=\"0\" cellpadding=\"6\">" +
      "         <tbody>" +
      "           <tr>" +
      "             <td width=\"50%\" style=\"text-align: right\">" +
      "             <label for=\"Book ID\">ID:</label></td>" +
      "             <td width=\"50%\" style=\"text-align: left\">" + uID + "</td>" +
      "           </tr>" +
      "           <tr>" +
      "             <td style=\"text-align: right\">" +
      "             <label for=\"Title\">Title:</label></td>" +
      "             <td style=\"text-align: left\">" +
      "             <input name=\"Title\" type=\"text\" value=\"" + b.getTitle() + "\"" +
      "             id=\"Title\" title=\"Title\" size=\"20\"></td>" +
      "           </tr>" +
      "           <tr>" +
      "             <td width=\"50%\" style=\"text-align: right\">" +
      "             <label for=\"Author\">Author:</label></td>" +
      "             <td width=\"50%\" style=\"text-align: left\">" +
      "             <input name=\"Author\" type=\"text\" value=\"" + b.getAuthor() + "\"" +
      "             id=\"Author\" title=\"Author\" size=\"20\"></td>" +
      "           </tr>" +
      "           <tr>" +
      "             <td style=\"text-align: right\">" +
      "             <label for=\"Year\">Year:</label></td>" +
      "             <td style=\"text-align: left\">" +
      "             <input name=\"Year\" type=\"number\" value=\"" + b.getYear() + "\"" +
      "             id=\"Year\" title=\"Please enter a correct year\" size=\"20\" maxlength=\"4\"></td>" +
      "           </tr>" +
      "           <tr>" +
      "             <td width=\"50%\" style=\"text-align: right\">" +
      "             <label for=\"Edition\">Edition:</label></td>" +
      "             <td width=\"50%\" style=\"text-align: left\">" +
      "             <input name=\"Edition\" type=\"number\" value=\"" + b.getEdition() + "\" id=\"Edition\" title=\"Edition must be numerical\" size=\"20\"></td>" +
      "           </tr>" +
      "           <tr>" +
      "             <td style=\"text-align: right\">" +
      "             <label for=\"Publisher\">Publisher:</label></td>" +
      "             <td style=\"text-align: left\">" +
      "             <input name=\"Publisher\" type=\"text\" value=\"" + b.getPublisher() + "\" id=\"Publisher\" title=\"Publisher\" size=\"20\"></td>" +
      "           </tr>" +
      "           <tr>" +
      "             <td width=\"50%\" style=\"text-align: right\">" +
      "             <label for=\"ISBN\">ISBN:</label></td>" +
      "             <td width=\"50%\" style=\"text-align: left\">" +
      "             <input name=\"ISBN\" type=\"text\" value=\"" + b.getISBN() + "\"" + 
      "             id=\"ISBN\" title=\"ISBN must be a 10 or 13-digit number.\"" +
      "             pattern=\"(?=.*\\d).{10,}\" maxlength=\"13\"></td>" +
      "           </tr>" +
      "           <tr>" +
      "             <td style=\"text-align: right\">" +
      "             <label for=\"Cover\">Cover:</label></td>" +
      "             <td style=\"text-align: left\">" +
      "             <input name=\"Cover\" type=\"text\" value=\"" + b.getCover() + "\"" +
      "             id=\"Cover\" title=\"Title\" size=\"20\"></td>" +
      "           </tr>" +
      "           <tr>" +
      "             <td width=\"50%\" style=\"text-align: right\">" +
      "             <label for=\"Condition\">Condition</label></td>" +
      "             <td width=\"50%\" style=\"text-align: left\">" +
      "             <input name=\"Condition\" type=\"text\" value=\"" + b.getCondition() + "\" id=\"Condition\" title=\"Condition\" size=\"20\"></td>" +
      "           </tr>" +
      "           <tr>" +
      "             <td style=\"text-align: right\">" +
      "             <label for=\"Price\">Price:</label></td>" +
      "             <td style=\"text-align: left\">" +
      "             <input name=\"Price\" type=\"number\" value=\"" + b.getPrice() + "\" id=\"Price\" title=\"Please enter a price in (£)\" size=\"20\"></td>" +
      "           </tr>" +
      "           <tr>" +
      "             <td width=\"50%\" style=\"text-align: right\">" +
      "             <label for=\"Notes\">Notes:</label></td>" +
      "             <td width=\"50%\" style=\"text-align: left\">" +
      "             <input name=\"Notes\" type=\"text\" value=\"" + b.getNotes() + "\"" +
      "             id=\"Notes\" title=\"Notes\" size=\"20\"></td>" +
      "           </tr>" +
      "           <tr>" +
      "             <td colspan=\"2\" style=\"text-align: center\">" +
      "             <input type=\"submit\" name=\"submit\" id=\"submit\"" + 
      "             value=\"Submit\"></td>" + 
      "           </tr>" +
      "         </tbody>" +
      "       </table>" +
      "     </form>" +
      "   </div>" +
      "  </body>" +
      "</html>");
      out.close();
    }catch(SQLException se){
      System.out.println(se.getMessage());
    }
  }
}