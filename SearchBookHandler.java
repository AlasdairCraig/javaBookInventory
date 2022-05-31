//craig_20104588

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedWriter;
import java.io.IOException;

/**
* A handler for obtaining information to complete search.
* <p>
* This handler will publish HTML page requesting information to complete search
* <p>
* @author Alasdair Craig
*/
public class SearchBookHandler implements HttpHandler{

  /**
  * Creates a handler for obtaining information to complete search.
  * <p>
  * This handler will publish HTML page requesting information to complete search
  * <p>
  * @param HttpExchange  exchange for handling http requests. 
  * <p>
  * @author Alasdair Craig
  */
  public void handle(HttpExchange he) throws IOException {
    he.sendResponseHeaders(200,0);
    
    System.out.println("Waiting for search details....");
    System.out.println("--------------------");
    
    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
    
    out.write(
      "<html>" +
      " <head>" +
	    "   <meta charset=\"utf-8\">" +
	    "   <title>Book Search Form</title>" +
	    "   <meta name=\"Book Search Form\" content=\"Book Search Form\">" +
	    "   <link rel=\"stylesheet\"" +
      "   href=\"https://style.AlasdairCraig.repl.co/style.css\">" +
      " </head>" +
      " <body>" +
      "   <h1>Search for a book</h1>" +
	    "   <div>" +
		  "   <form action=\"/searchProcess\" method=\"post\">" +
			"     <table width=\"100%\" border=\"0\" cellpadding=\"6\">" +
      "       <tbody>" +
      "         <tr>" +
      "           <td width=\"50%\" style=\"text-align: right\">" +
      "           <label for=\"ID\">ID:</label></td>" +
      "           <td width=\"50%\" style=\"text-align: left\">" +
      "           <input name=\"ID\" type=\"number\" id=\"ID\" title=\"ID must be a number.\"" +
      "           size=\"35\"></td>" +
      "         </tr>" +
      "         <tr>" +
      "           <td width=\"50%\" style=\"text-align: right\">" +
      "           <label for=\"Title\">Title:</label></td>" +
      "           <td width=\"50%\" style=\"text-align: left\">" +
      "           <input name=\"Title\" type=\"text\" id=\"title\" title=\"Title\"" +
      "           size=\"20\"></td>" +
      "         </tr>" +
      "         <tr>" +
      "           <td width=\"50%\" style=\"text-align: right\">" +
      "           <label for=\"Author\">Author:</label></td>" +
      "           <td width=\"50%\" style=\"text-align: left\">" +
      "           <input name=\"Author\" type=\"text\" id=\"author\" title=\"Author\"" +
      "           size=\"20\"></td>" +
      "         </tr>" +
      "         <tr>" +
			"           <td style=\"text-align: right\"><label for=\"priceRange\">Price Range" +
      "           </label>></td>" +
			"           <td>" +
			"             <p><label>" +
			"             <input type=\"radio\" name=\"PriceRange\" value=\"BETWEEN 1 AND 9\"" + 
      "             id=\"PriceRange_0\">Less than £10</label>" +
	    "             <br><label>" +
			"             <input type=\"radio\" name=\"PriceRange\" value=\"BETWEEN 10 AND 50\"" +"            id=\"PriceRange_1\">£10 - £50</label>" +
			"             <br><label>" +
			"             <input type=\"radio\" name=\"PriceRange\" value=\"BETWEEN 50 AND 100\""
      +"            id=\"PriceRange_2\">£50 - £100</label>" +
			"             <br><label>" +
			"             <input type=\"radio\" name=\"PriceRange\" value=\">100\"" + 
      "             id=\"PriceRange_3\">Above £100</label>" +
	    "             <br></p></td>" +
			"         </tr>" +
      "         <tr>" +
      "           <td colspan=\"2\" style=\"text-align: center\">"+
      "           <input type=\"submit\" name=\"submit\" id=\"submit\"" + 
      "           value=\"Submit\"></td>" + 
      "         </tr>" +
      "       </tbody>" +
      "     </table>" +
      "   </form>" +
      "   </div>" +
      " </body>" +
      "</html>");
    out.close();
  }
}