//craig_20104588

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.sql.SQLException;

/**
* A handler for obtaining information on new book.
* <p>
* This handler will publish HTML page requesting information for new book
* <p>
* @author Alasdair Craig
*/
public class NewBookHandler implements HttpHandler{

  public static int newBookID;

  /**
  * Creates a handler for obtaining information on new book.
  * <p>
  * This handler will publish HTML page requesting information for new book
  * <p>
  * @param HttpExchange  exchange for handling http requests. 
  * <p>
  * @author Alasdair Craig
  */
  public void handle(HttpExchange he) throws IOException {

    BookDao books = new BookDao();

    he.sendResponseHeaders(200,0);

    System.out.println("Waiting for new book information....");
    System.out.println("--------------------");

    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

    try{
    ArrayList<Book> allBooks = books.getAllBooks();
    Book lastBookEntry = allBooks.get(allBooks.size() - 1);

    //System.out.println(lastBookEntry);

    newBookID = (lastBookEntry.getID() + 1);

    //System.out.println(newBookID);
    
      out.write(
        "<html>" +
        " <head>" +
        "   <meta charset=\"utf-8\">" +
        "   <title>New Book Form</title>" +
        "   <meta name=\"New Book Form\" content=\"New Book Form\">" +
        "   <link rel=\"stylesheet\"" +
        "   href=\"https://style.AlasdairCraig.repl.co/style.css\">" +
        " </head>" +
        " <body>" +
        "   <h1>New Book Entry</h1>" +
        "   <div id=\"container\">" +
        "     <form action=\"/newBookProcess\" method=\"post\">" +
        "       <table width=\"100%\" border=\"0\" cellpadding=\"6\">" +
        "         <tbody>" +
        "           <tr>" +
        "             <td width=\"50%\" style=\"text-align: right\">" +
        "             <label for=\"Book ID\">ID:</label></td>" +
        "             <td width=\"50%\" style=\"text-align: left\">"+ newBookID + "</td>" +
        "           </tr>" +
        "           <tr>" +
        "             <td style=\"text-align: right\">" +
        "             <label for=\"Title\">Title:</label></td>" +
        "             <td style=\"text-align: left\">" +
        "             <input name=\"Title\" type=\"text\" id=\"Title\" title=\"Title\"" +
        "             size=\"20\"></td>" +
        "           </tr>" +
        "           <tr>" +
        "             <td width=\"50%\" style=\"text-align: right\">" +
        "             <label for=\"Author\">Author:</label></td>" +
        "             <td width=\"50%\" style=\"text-align: left\">" +
        "             <input name=\"Author\" type=\"text\" id=\"Author\" title=\"Author\"" +
        "             size=\"20\"></td>" +
        "           </tr>" +
        "           <tr>" +
        "             <td style=\"text-align: right\">" +
        "             <label for=\"Year\">Year:</label></td>" +
        "             <td style=\"text-align: left\">" +
        "             <input name=\"Year\" type=\"number\" id=\"Year\" title=\"Please enter a correct year\"" +
        "             size=\"20\"></td>" +
        "           </tr>" +
        "           <tr>" +
        "             <td width=\"50%\" style=\"text-align: right\">" +
        "             <label for=\"Edition\">Edition:</label></td>" +
        "             <td width=\"50%\" style=\"text-align: left\">" +
        "             <input name=\"Edition\" type=\"number\" id=\"Edition\"" +
        "             title=\"Edition must be numerical\" size=\"20\"></td>" +
        "           </tr>" +
        "           <tr>" +
        "             <td style=\"text-align: right\">" +
        "             <label for=\"Publisher\">Publisher:</label></td>" +
        "             <td style=\"text-align: left\">" +
        "             <input name=\"Publisher\" type=\"text\" id=\"Publisher\"" +
        "             title=\"Publisher\" size=\"20\"></td>" +
        "           </tr>" +
        "           <tr>" +
        "             <td width=\"50%\" style=\"text-align: right\">" +
        "             <label for=\"ISBN\">ISBN:</label></td>" +
        "             <td width=\"50%\" style=\"text-align: left\">" +
        "             <input name=\"ISBN\" type=\"text\" id=\"ISBN\" title=\"ISBN must be a 10 or 13-digit number.\"" +
        "             pattern=\"(?=.*\\d).{10,}\" maxlength=\"13\"></td>" +
        "           </tr>" +
        "           <tr>" +
        "             <td style=\"text-align: right\">" +
        "             <label for=\"Cover\">Cover:</label></td>" +
        "             <td style=\"text-align: left\">" +
        "             <input name=\"Cover\" type=\"text\" id=\"Cover\" title=\"Title\"" +
        "             size=\"20\"></td>" +
        "           </tr>" +
        "           <tr>" +
        "             <td width=\"50%\" style=\"text-align: right\">" +
        "             <label for=\"Condition\">Condition</label></td>" +
        "             <td width=\"50%\" style=\"text-align: left\">" +
        "             <input name=\"Condition\" type=\"text\" id=\"Condition\"" +
        "             title=\"Condition\" size=\"20\"></td>" +
        "           </tr>" +
        "           <tr>" +
        "             <td style=\"text-align: right\">" +
        "             <label for=\"Price\">Price:</label></td>" +
        "             <td style=\"text-align: left\">" +
        "             <input name=\"Price\" type=\"number\" id=\"Price\" title=\"Please enter a price in (£)\"" + 
        "             size=\"20\"></td>" +
        "           </tr>" +
        "           <tr>" +
        "             <td width=\"50%\" style=\"text-align: right\">" +
        "             <label for=\"Notes\">Notes:</label></td>" +
        "             <td width=\"50%\" style=\"text-align: left\">" +
        "             <input name=\"Notes\" type=\"text\" id=\"Notes\" title=\"Notes\"" +
        "             size=\"20\"></td>" +
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