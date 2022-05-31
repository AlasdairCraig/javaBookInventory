//craig_20104588

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.util.ArrayList;
import java.sql.SQLException;
import java.io.BufferedWriter;
import java.io.IOException;

/**
* A handler for Book Inventory Sales Records page.
* <p>
* This handler will create a HTML webpage listing all books sold from inventory
* <p>
* @author Alasdair Craig
*/  
public class SalesHandler implements HttpHandler{

  /**
  * Creates a handler for Book Inventory Sales Records page.
  * <p>
  * This handler will create a HTML webpage listing all books sold from inventory
  * <p>
  * @param HttpExchange  exchange for handling http requests. 
  * <p>
  * @author Alasdair Craig
  */  
  public void handle(HttpExchange he) throws IOException {
    he.sendResponseHeaders(200,0);

    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
    
    BookDao books = new BookDao();
    
    try{
      ArrayList<Book> soldBooks = books.getAllSoldBooks();
      System.out.println("Retrieving all sold books ...");
      System.out.println("--------------------");
    
      out.write(
        "<html>" +
        " <head> <title>Book Inventory Sales Record</title> "+
        "   <link rel=\"stylesheet\"" +
        "   href=\"https://style.AlasdairCraig.repl.co/style.css\">" +
        " </head>" +
        " <body>" +
        "   <h1> Books sold from inventory</h1>"+
        "   <div class=\"buttons\">" +
        "     <a class=\"logButton\" href=\"/adminLogin\"> Back to Admin Panel </a>" +
        "   <br>" +
        "   <br>" +
        "   </div>" +
        "   <table class=\"table\">" +
        "   <thead>" +
        "     <tr>" +
        "       <th>ID</th>" +
        "       <th>Title</th>" +
        "       <th>Author</th>" +
        "       <th>Year</th>" +
        "       <th>Edition</th>" +
        "       <th>Publisher</th>" +
        "       <th>ISBN</th>" +
        "       <th>Cover</th>" +
        "       <th>Condition</th>" +
        "       <th>Price (£)</th>" +
        "       <th>Notes</th>" +
        "     </tr>" +
        "   </thead>" +
        "   <tbody>");

        for (Book b : soldBooks){
          out.write(
            "     <tr>"       +
            "       <td>"+ b.getID() + "</td>" +
            "       <td>"+ b.getTitle() + "</td>" +
            "       <td>"+ b.getAuthor() + "</td>" +
            "       <td>"+ b.getYear() + "</td>" +
            "       <td>"+ b.getEdition() + "</td>" +
            "       <td>"+ b.getPublisher() + "</td>" +
            "       <td>"+ b.getISBN() + "</td>" +
            "       <td>"+ b.getCover() + "</td>" +
            "       <td>"+ b.getCondition() + "</td>" +
            "       <td>"+ b.getPrice() + "</td>" +
            "       <td>"+ b.getNotes() + "</td>" +
            "     </tr>");
        }
        out.write(
        "   </tbody>" +
        " </table>" +
        " </body>" +
        "</html>");
    }catch(SQLException se){
      System.out.println(se.getMessage());
    }
    out.close();
  }
}