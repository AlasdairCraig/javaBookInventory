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
import java.util.ArrayList;

/**
* A handler for deleting books from database.
* <p>
* This handler will process the deletion of a single book, and also publish HTML page 
* displaying deleted book.  
* <p>
* @author Alasdair Craig
*/  
public class DeleteBookHandler implements HttpHandler{

  public static int newSoldBookID;

  /**
  * Creates a handler for deleting books from database.
  * <p>
  * This handler will process the deletion of a single book, and also publish HTML page 
  * displaying deleted book.
  * <p>
  * @param HttpExchange  exchange for handling http requests. 
  * <p>
  * @author Alasdair Craig
  */  
  public void handle(HttpExchange he) throws IOException {
   
    //System.out.println("In DeleteBookHandler");

    BufferedReader in = new BufferedReader(new InputStreamReader(he.getRequestBody()));
   
    String line;
    String request = "";
    while( (line = in.readLine()) != null ){
      request = request + line;
    }

    //System.out.println("request is " + request); 

    HashMap<String,String> map = Util.requestStringToMap(request);

    int dID = DeleteConfirmHandler.dID;
    String deleteChoice = map.get("deleteChoice");

    BookDao books = new BookDao();
        
    //System.out.println(map); 

    try{
    ArrayList<Book> soldBooks = books.getAllSoldBooks();
    Book lastSoldBookEntry = soldBooks.get(soldBooks.size() - 1);

    //System.out.println(lastBookEntry);

    newSoldBookID = (lastSoldBookEntry.getID() + 1);
    }catch(SQLException se){
      System.out.println(se.getMessage());
    }
  
    if (deleteChoice.equals("delete"))
    try{
      Book deletedBook = books.getBookByID(dID);
      books.deleteBook(dID);
      System.out.println("Book deleted:");
      System.out.println("--------------------");
      System.out.println(deletedBook);
      
      BufferedWriter out= new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

      //System.out.println(request);

      he.sendResponseHeaders(200,0);
      out.write(
        "<html>" +
        " <head> <title>Book Deleted</title> "+
        "   <link rel=\"stylesheet\"" +
        "   href=\"https://style.AlasdairCraig.repl.co/style.css\">" +
        " </head>" +
        " <body>" +
        "   <h1> Book deleted</h1>"+
        " <div class=\"buttons\">" +
        "  <a class=\"searchBookButton\" href=\"/adminLogin\">Go back to Admin Panel</a>" +
        "  <a class=\"logButton\" href=\"/\"> Log Out </a>" +
        "<br>" +
        "<br>" +
        " </div>" +
        " <table class=\"table\">" +
        " <thead>" +
        "  <tr>" +
        "    <th>ID</th>" +
        "    <th>Title</th>" +
        "    <th>Author</th>" +
        "    <th>Year</th>" +
        "    <th>Edition</th>" +
        "    <th>Publisher</th>" +
        "    <th>ISBN</th>" +
        "    <th>Cover</th>" +
        "    <th>Condition</th>" +
        "    <th>Price (£)</th>" +
        "    <th>Notes</th>" +
        "  </tr>" +
        " </thead>" +
        " <tbody>" +
        "  <tr>" +
        "    <td>"+ deletedBook.getID() + "</td>" +
        "    <td>"+ deletedBook.getTitle() + "</td>" +
        "    <td>"+ deletedBook.getAuthor() + "</td>" +
        "    <td>"+ deletedBook.getYear() + "</td>" +
        "    <td>"+ deletedBook.getEdition() + "</td>" +
        "    <td>"+ deletedBook.getPublisher() + "</td>" +
        "    <td>"+ deletedBook.getISBN() + "</td>" +
        "    <td>"+ deletedBook.getCover() + "</td>" +
        "    <td>"+ deletedBook.getCondition() + "</td>" +
        "    <td>"+ deletedBook.getPrice() + "</td>" +
        "    <td>"+ deletedBook.getNotes() + "</td>" +
        "  </tr>" + 
        "</tbody>" +
        "</table>" +
        "</body>" +
        "</html>");
      out.close();
    }catch(SQLException se){
      System.out.println(se.getMessage());
    }else{
      try{
      Book soldBook = books.getBookByID(dID);
      books.sellBook(soldBook);
      System.out.println("Book added to sales:");
      System.out.println("--------------------");
      System.out.println(soldBook);
      books.deleteBook(dID);
      
      BufferedWriter out= new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

      //System.out.println(request);

      he.sendResponseHeaders(200,0);
      out.write(
        "<html>" +
        " <head> <title>Book Sold</title> "+
        "   <link rel=\"stylesheet\"" +
        "   href=\"https://style.AlasdairCraig.repl.co/style.css\">" +
        " </head>" +
        " <body>" +
        "   <h1> Book sold</h1>"+
        " <div class=\"buttons\">" +
        "  <a class=\"searchBookButton\" href=\"/adminLogin\">Go back to Admin Panel</a>" +
        "  <a class=\"logButton\" href=\"/\"> Log Out </a>" +
        "<br>" +
        "<br>" +
        " </div>" +
        " <table class=\"table\">" +
        " <thead>" +
        "  <tr>" +
        "    <th>ID</th>" +
        "    <th>Title</th>" +
        "    <th>Author</th>" +
        "    <th>Year</th>" +
        "    <th>Edition</th>" +
        "    <th>Publisher</th>" +
        "    <th>ISBN</th>" +
        "    <th>Cover</th>" +
        "    <th>Condition</th>" +
        "    <th>Price (£)</th>" +
        "    <th>Notes</th>" +
        "  </tr>" +
        " </thead>" +
        " <tbody>" +
        "  <tr>" +
        "    <td>"+ soldBook.getID() + "</td>" +
        "    <td>"+ soldBook.getTitle() + "</td>" +
        "    <td>"+ soldBook.getAuthor() + "</td>" +
        "    <td>"+ soldBook.getYear() + "</td>" +
        "    <td>"+ soldBook.getEdition() + "</td>" +
        "    <td>"+ soldBook.getPublisher() + "</td>" +
        "    <td>"+ soldBook.getISBN() + "</td>" +
        "    <td>"+ soldBook.getCover() + "</td>" +
        "    <td>"+ soldBook.getCondition() + "</td>" +
        "    <td>"+ soldBook.getPrice() + "</td>" +
        "    <td>"+ soldBook.getNotes() + "</td>" +
        "  </tr>" + 
        "</tbody>" +
        "</table>" +
        "</body>" +
        "</html>");
      out.close();
      }catch(SQLException se){
        System.out.println(se.getMessage());
      }
    }
  }
}