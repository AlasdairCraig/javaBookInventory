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
* A handler for processing new book request.
* <p>
* This handler will process new book request, and also publish HTML page displaying if 
* insert has been successful or not, and values for book inserted
* <p>
* @author Alasdair Craig
*/
public class NewBookProcessHandler implements HttpHandler{

  /**
  * Creates a handler for processing new book request.
  * <p>
  * This handler will will process new book request, and also publish HTML page displaying 
  * if insert has been successful or not, and values for book inserted
  * <p>
  * @param HttpExchange  exchange for handling http requests. 
  * <p>
  * @author Alasdair Craig
  */
  public void handle(HttpExchange he) throws IOException{

    //System.out.println("In NewBookProcessHandler");

    Integer newBookID = NewBookHandler.newBookID;

    //System.out.println(newBookID);

    BufferedReader in = new BufferedReader(new InputStreamReader(he.getRequestBody()));

    String line;
    String request = "";
    while( (line = in.readLine()) !=null){
      request = request + line;
    }

    //System.out.println(request);

    HashMap<String,String> map = Util.requestStringToMap(request);

    //System.out.println(map);

    BookDao books = new BookDao();

    String isbn = map.get("ISBN");

    if (books.checkIsbnLength(isbn) == true || isbn.equals(""))

    try{

      String title = map.get("Title");
      String author = map.get("Author");
      String strYear = map.get("Year");
      Integer year;
        if (strYear.equals(""))
          year = null;
        else
          year = Integer.parseInt(strYear);  
      String strEdition = map.get("Edition");
      Integer edition;
        if (strEdition.equals(""))
          edition = null;
        else
          edition = Integer.parseInt(strEdition);
      String publisher = map.get("Publisher");
      String cover = map.get("Cover");
      String condition = map.get("Condition");
      String strPrice = map.get("Price");
      Integer price;
        if (strPrice.equals(""))
          price = null;
        else
          price = Integer.parseInt(strPrice);
      String notes = map.get("Notes");

      Book newBook = new Book(newBookID,title,author,year,edition,publisher,isbn,cover,condition,price,notes);

      System.out.println("Adding new book");
      System.out.println("--------------------");

      books.insertBook(newBook);
      
      int nID = newBook.getID();
      System.out.println(nID);
      Book b = books.getBookByID(nID);
    
      System.out.println("Book added");
      System.out.println("--------------------");
    

      BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

      he.sendResponseHeaders(200,0);
      out.write(
        "<html>" +
        " <head>" +
        "   <title>New Book Added</title>" +
        "   <link rel=\"stylesheet\"" +
        "   href=\"https://style.AlasdairCraig.repl.co/style.css\">" +
        " </head>" +
        " <body>" +
        "   <h1> Book added to inventory</h1>"+
        "   <div class=\"buttons\">" +
        "    <a class=\"searchBookButton\" href=\"/newBook\"> +Add another book </a>" +
        "    <a class=\"searchBookButton\" href=\"/adminLogin\">Go back to Admin Panel</a>" +
        "    <a class=\"logButton\" href=\"/\"> Log Out </a>" +
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
        "       </tr>" +
        "     </thead>" +
        "     <tbody>" +
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
        "       </tr>" + 
        "     </tbody>" +
        "   </table>" +
        " </body>" +
        "</html>");
      out.close();
    }catch(SQLException se){
      System.out.println(se.getMessage());
    }else{
      System.out.println("ISBN Code is invalid format. Please try again.");
      System.out.println("--------------------");
      
      BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

      he.sendResponseHeaders(200,0);
      out.write(
        "<html>" +
        " <head>" +
        "   <title>ISBN Incorrect</title>" +
        "   <link rel=\"stylesheet\"" +
        "   href=\"https://style.AlasdairCraig.repl.co/style.css\">" +
        " </head>" +
        " <body>" +
        "   <h1>ISBN number invalid, please enter a valid 10 or 13-digit ISBN code</h1>"+
        "   <div class=\"buttons\">" +
        "    <a class=\"searchBookButton\" href=\"/newBook\"> Go back </a>" +
        "    <a class=\"searchBookButton\" href=\"/adminLogin\">Admin Panel</a>" +
        "    <a class=\"logButton\" href=\"/\"> Log Out </a>" +
        "   <br>" +
        "   <br>" +
        "   </div>" +
        " </body>" +
        "</html>");
      out.close();
    }
  }
}