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
* A handler for Admin Panel webpage.
* <p>
* This handler will create a HTML webpage listing all books in inventory, plus further 
* admin-only accessed controls (Add new book; Update book; Delete Book) 
* <p>
* @author Alasdair Craig
*/  
public class AdminHandler implements HttpHandler{

  /**
  * Creates a handler for Admin Panel webpage.
  * <p>
  * This handler will create a HTML webpage listing all books in inventory, plus further 
  * admin-only accessed controls (Add new book; Update book; Delete Book) 
  * <p>
  * @param HttpExchange  exchange for handling http requests
  * <p>
  * @author Alasdair Craig
  */  
  public void handle(HttpExchange he) throws IOException{
    he.sendResponseHeaders(200,0);
    
    System.out.println("Admin Panel:");
    System.out.println("------------------");
    
    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody() ));
    
    BookDao books = new BookDao();

    try{
      ArrayList<Book> allBooks = books.getAllBooks();
      System.out.println("Retrieving all books ...");
      System.out.println("--------------------");
   
      out.write(
        "<html>" +
        " <head> <title>Book Inventory Admin</title>" +
        "   <link rel=\"stylesheet\"" +
        "   href=\"https://style.AlasdairCraig.repl.co/style.css\">" +
        " </head>" +
        " <body>" +
        "   <h1> Books Inventory (Admin Panel)</h1>" +
        "   <div class=\"buttons\">" +
        "     <a class=\"newBookButton\" href=\"/newBook\"> +New Book </a>" +
        "     <a class=\"searchBookButton\" href=\"/searchBook\"> Search for Book </a>" +
        "     <a class=\"logButton\" href=\"/sales\"> Sales Records </a>" +
        "     <a class=\"logButton\" href=\"/\"> Log Out </a>" +
        "   <br>" +
        "   <br>" +
        "   </div>" +
        "   <table class=\"table\">" +
        "     <thead>" +
        "       <tr>" +
        "         <th>ID</th>" +
        "         <th>Title</th>" +
        "         <th>Author</th>" +
        "         <th>Year</th>" +
        "         <th>Edition</th>" +
        "         <th>Publisher</th>" +
        "         <th>ISBN</th>" +
        "         <th>Cover</th>" +
        "         <th>Condition</th>" +
        "         <th>Price (£)</th>" +
        "         <th>Notes</th>" +
        "         <th></th>" +
        "         <th></th>" +
        "       </tr>" +
        "     </thead>" +
        "     <tbody>");
    
    for (Book b : allBooks){
      out.write(
        "       <tr>" +
        "         <td>"+ b.getID() + "</td>" +
        "         <td>"+ b.getTitle() + "</td>" +
        "         <td>"+ b.getAuthor() + "</td>" +
        "         <td>"+ b.getYear() + "</td>" +
        "         <td>"+ b.getEdition() + "</td>" +
        "         <td>"+ b.getPublisher() + "</td>" +
        "         <td>"+ b.getISBN() + "</td>" +
        "         <td>"+ b.getCover() + "</td>" +
        "         <td>"+ b.getCondition() + "</td>" +
        "         <td>"+ b.getPrice() + "</td>" +
        "         <td>"+ b.getNotes() + "</td>" +
        "         <td><form action=\"/deleteBook\" method=\"post\">" +
        "             <button name=\"deleteID\" value=\""+ b.getID() +"\"" +   
        "             type=\"submit\">Delete</button></form></td>" + 
        "         <td><form action=\"/updateBook\" method=\"post\">" +
        "             <button name=\"updateID\" value=\"" + b.getID() + "\"" +
        "             type=\"submit\">Update</button></form></td>" +
        "        </tr>");
    } out.write(
      "      </tbody>" +
      "    </table>" +
      "  </body>" +
      "</html>");
      out.close();
    } catch (SQLException se){
      System.out.println(se.getMessage());
    }
  }
}