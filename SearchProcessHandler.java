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
* A handler for processing search request.
* <p>
* This handler will process search request, and also publish HTML page displaying if 
* search has been successful or not, and list values of books in search results
* <p>
* @author Alasdair Craig
*/
public class SearchProcessHandler implements HttpHandler{

  /**
  * Creates a handler for processing search request.
  * <p>
  * This handler will process search request, and also publish HTML page displaying if 
  * search has been successful or not, and list values of books in search results
  * <p>
  * @param HttpExchange  exchange for handling http requests. 
  * <p>
  * @author Alasdair Craig
  */
  public void handle(HttpExchange he) throws IOException {

    he.sendResponseHeaders(200,0);   
    //System.out.println("In SearchProcessHandler");

    BufferedReader in = new BufferedReader(new InputStreamReader(he.getRequestBody()));
   
    String line;
    String request = "";
    while( (line = in.readLine()) != null ){
      request = request + line;
    }

    //System.out.println("request is " + request); 

    HashMap<String,String> map = Util.requestStringToMap(request);

    String strID = map.get("ID");
    String title = map.get("Title");
    String author = map.get("Author");
    String priceRange = map.get("PriceRange");
    
    //System.out.println("Price range is " + priceRange);
    //System.out.println(map);

    System.out.println("Searching books for:");
    System.out.println("--------------------");

    System.out.print("ID: " + strID + " ");
    System.out.print("Title: " + title + " ");
    System.out.print("Author: " + author + " ");
    
    if (priceRange == null){
      priceRange = "";
    }
    System.out.println("Price Range: " + priceRange + " ");
    System.out.println("--------------------");

    
    String searchReq = "";

    if (!strID.equals(""))
      searchReq += "ID =" + Integer.parseInt(strID); 

    if (strID.length() > 0 && title.length() > 0 || strID.length() > 0 && author.length() > 0 || strID.length() > 0 && priceRange.length() > 0)
        searchReq += " AND ";
    
    if (!title.equals(""))
      searchReq += "Title LIKE '" + title + "'"; 

    if (title.length() > 0 && author.length() > 0 || title.length() > 0 && priceRange.length() > 0)
        searchReq += " AND ";
    
    if (!author.equals(""))
      searchReq += "Author LIKE '" + author + "'";

    if (author.length() > 0 && priceRange.length() > 0)
        searchReq += " AND "; 

    if (!priceRange.equals(""))
      searchReq += "Price " + priceRange;

    //System.out.println("Search req is: " + "[" + searchReq + "]");

    BookDao books = new BookDao();
            
    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody() ));

    try{
      ArrayList<Book> foundBooks = books.searchBooks(searchReq);
      System.out.println("Books found:");
      System.out.println("--------------------");

      for (int i = 0; i < foundBooks.size(); i++){
            System.out.println(foundBooks.get(i));
          }
    
      out.write(
        "<html>" +
        " <head> <title>Book Found</title> "+
      "   <link rel=\"stylesheet\"" +
      "   href=\"https://style.AlasdairCraig.repl.co/style.css\">" +
      " </head>" +
      " <body>" +
      " <h1> Book(s) found</h1>"+
      " <div class=\"buttons\">" +
      "   <a class=\"searchBookButton\" href=\"/searchBook\">Search for another book</a>" +
      "   <a class=\"searchBookButton\" href=\"/\"> Back to full list </a>" +
      "   <a class=\"logButton\" href=\"/login\"> Log in </a>" +
      "   <br>" +
      "   <br>" +
      " </div>" +
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

        for (Book b : foundBooks){
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
