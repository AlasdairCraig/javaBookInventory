//craig_20104588

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.security.NoSuchAlgorithmException;

class Main {
  static final private int PORT = 8080;

  public static Integer newID; //Initialise newID for adding book

  /**
  * Main method.
  * <p>
  * This is where the user control menu and/or http servers will be created
  * <p>
  * @param args  an array of command-line arguments for the application  
  * <p>
  * @author Alasdair Craig
  */
  public static void main(String[] args) throws IOException,SQLException, NoSuchAlgorithmException {
    
    Scanner in = new Scanner(System.in);
    String selection;
    Boolean loginCheck;
    String error;
    BookDao books = new BookDao();
    UserDao users = new UserDao();
    
    String strID;
    String title;
    String author;
         
    //Print Out Control Menu
    do{
      System.out.println("--------------------");
      System.out.println("Book Inventory");
      System.out.println("Choose from these options");
      System.out.println("--------------------");

      System.out.println("[1] Retrieve all books");
      System.out.println("[2] Search for book");
      System.out.println("[3] Insert new book into database");
      System.out.println("[4] Update book in database");
      System.out.println("[5] Delete book from database");
      
      System.out.println("--------------------");
      System.out.println("[6] Exit & launch browser");
      System.out.println("--------------------");
      
      System.out.println();
      System.out.println("Enter choice >");

      selection = in.nextLine();
      System.out.println("--------------------");

      switch (selection) { 

        case "1": //List all books
          System.out.println("Listing all books");
          System.out.println("--------------------");
          ArrayList<Book> allbooks = books.getAllBooks();
          for (int i = 0; i < allbooks.size(); i++){
            System.out.println(allbooks.get(i));
          }
          System.out.println();
          break;

        case "2": //Search for a book by ID, Title or Author
          System.out.println("\nEnter book ID (Leave blank if unknown)");
          strID = in.nextLine();
          System.out.println("\nEnter a book title (Leave blank if unknown)");
          title = in.nextLine();
          System.out.println("\nEnter an author (Leave blank if unknown)");
          author = in.nextLine();

          String searchReq = "";
          //following code creates String searchReq based on either filled or empty inputs

            if (!strID.equals(""))
            searchReq += "ID =" + Integer.parseInt(strID); 
          
            if (strID.length() > 0 && title.length() > 0 || strID.length() > 0 && author.length() > 0)
            searchReq += " AND ";
    
            if (!title.equals(""))
            searchReq += "Title LIKE '" + title + "'"; 

            if (title.length() > 0 && author.length() > 0)
            searchReq += " AND ";
    
            if (!author.equals(""))
            searchReq += "Author LIKE '" + author + "'"; 

            //System.out.println("Search request is: " + "[" + searchReq + "]");

          System.out.println("--------------------");
          System.out.println("Book(s) found:");
          System.out.println("--------------------");

          ArrayList<Book> foundBooks = books.searchBooks(searchReq); //Print search results
          for (int i = 0; i < foundBooks.size(); i++){
            System.out.println(foundBooks.get(i));
          } 
          System.out.println();
          break;

        case "3": //Add new Book
          System.out.println("Add a new book");

          Book book = createBook(); // Display inputs for new book 

          System.out.println("--------------------");
          System.out.println("Adding book: ID= " + book.getID() + ", \"" + book.getTitle() + "\", by " + book.getAuthor());
          System.out.println("--------------------");

          String isbn = book.getISBN();
          
          if (books.checkIsbnLength(isbn) == true || isbn.equals("")){ //Check ISBN Validity 
            books.insertBook(book); // Insert Book to DB
            System.out.println("--------------------");
            System.out.println(book); // Print new book.
            System.out.println("--------------------");
            System.out.println("Book added: ID= " + book.getID() + ", " + book.getTitle() + ", by " + book.getAuthor());
            System.out.println("--------------------");
          }else{
            System.out.println("Incorrect ISBN format. Please try again.");
          }
          break;

        case "4": //Update Book
          System.out.println("Update a book");
          System.out.println("\nEnter Book ID to update");
          Integer uID = Integer.parseInt(in.nextLine());
          System.out.println(books.getBookByID(uID)); //Prints book to update
          Book updatedBook = updateBook(books.getBookByID(uID));
          books.updateBook(updatedBook); //Process update
          System.out.println("--------------------");
          System.out.println("Book updated");
          System.out.println("--------------------");
          System.out.println(updatedBook); //Prints updated book
          break;

        case "5": //Delete Book
          System.out.println("Delete book");
          System.out.println("\nEnter Book ID to delete");
          Integer dID = Integer.parseInt(in.nextLine());
          System.out.println("-------------------");
          books.deleteBook(dID); //Process delete
          break;

        case "6": //Launch HttpServer
          System.out.println("Exiting control menu");
          System.out.println("--------------------");
          System.out.println("Launching browser");
          System.out.println("--------------------");
          
          HttpServer server = HttpServer.create(new InetSocketAddress(PORT),0);
    
          server.createContext("/", new RootHandler() );

          server.createContext("/searchBook", new SearchBookHandler());
          server.createContext("/searchProcess", new SearchProcessHandler());

          server.createContext("/login", new LoginHandler());
          server.createContext("/loginProcess", new LoginProcessHandler());
          server.createContext("/adminLogin", new AdminHandler());
          
          server.createContext("/newBook", new NewBookHandler());
          server.createContext("/newBookProcess", new NewBookProcessHandler());
          
          server.createContext("/deleteBook", new DeleteConfirmHandler());
          server.createContext("/deleteConfirm", new DeleteBookHandler());
          
          server.createContext("/updateBook", new UpdateBookHandler()); 
          server.createContext("/updateBookProcess", new UpdateProcessHandler());

          server.createContext("/newUser", new NewUserHandler());
          server.createContext("/passConfirm", new PassConfirmHandler());
          server.createContext("/newUserProcess", new NewUserProcessHandler());

          server.createContext("/sales", new SalesHandler());
          
          server.setExecutor(null);
          server.start();
          
          System.out.println("The server is listening on port " + PORT);

          System.out.println("--------------------");
          System.out.println();
          System.out.println("Welcome to the Book Inventory");
          System.out.println();
          System.out.println("--------------------");
          break;
          default:
          System.out.println("Invalid Selection");
      }
    }while (!selection.equals("6"));
  } 

  private static Book createBook(){  

    try{
      BookDao books = new BookDao();
      ArrayList<Book> allBooks = books.getAllBooks();
      Book lastBookEntry = allBooks.get(allBooks.size() - 1);
      newID = (lastBookEntry.getID() + 1);
    }catch (SQLException e) {
			System.out.println(e.getMessage());
		}
    
    String title;
    String author;
    Integer year = null;
    Integer edition;
    String publisher;
    String isbn;
    String cover;
    String condition;
    Integer price;
    String notes;

    Scanner in = new Scanner(System.in);

    System.out.println();
    System.out.println("Please enter a Title");
    title = in.nextLine();
    String titleFix = title.replace("'","''");
    
    System.out.println("Please enter an Author");
    author = in.nextLine();
    
    System.out.println("Please enter a Year");
    String strYear = in.nextLine();
    if (strYear.equals(""))
      year = null;
    else 
      year = Integer.parseInt(strYear);
  
    System.out.println("Please enter an Edition Number");
    String strEdition = in.nextLine();
    if (strEdition.equals(""))
      edition = null;
    else 
      edition = Integer.parseInt(strEdition);
    
    System.out.println("Please enter a Publisher");
    publisher = in.nextLine();
  
    System.out.println("Please enter an ISBN Code");
    isbn = in.nextLine();
    
    System.out.println("Please enter the Cover style");
    cover = in.nextLine();
    
    System.out.println("Please enter a Condition");
    condition = in.nextLine();
    
    System.out.println("Please enter a Price");
    String strPrice = in.nextLine();
    if (strPrice.equals(""))
      price = null;
    else 
      price = Integer.parseInt(strPrice);
    
    System.out.println("Please enter any Notes");
    notes = in.nextLine();

    return new Book(newID, title, author, year, edition, publisher, isbn, cover, condition, price, notes);
  }

  private static Book updateBook(Book book) {
    // TODO Auto-generated method stub
    String title;
    String titleFix;
    String author;
    Integer year;
    Integer edition;
    String publisher;
    String isbn;
    String cover;
    String condition;
    Integer price;
    String notes;

    Scanner in = new Scanner(System.in);
    System.out.println("Updating Book with ID:" + book.getID());
    System.out.println("--------------------");
    System.out.println("Please enter Title");
    title = in.nextLine();
    if (title.equals(""))
      title = book.getTitle();
    else
    titleFix = title.replace("'","''");

    System.out.println("Please enter Author");
    author = in.nextLine();
    if (author.equals(""))
      author = book.getAuthor();

    System.out.println("Please enter Year");
    String strYear = in.nextLine();
    if (strYear.equals(""))
      year = book.getYear();
    else
      year = Integer.parseInt(strYear);

    System.out.println("Please enter Edition");
    String strEdition = in.nextLine();
    if (strEdition.equals(""))
      edition = book.getEdition();
    else
      edition = Integer.parseInt(strEdition);

    System.out.println("Please enter Publisher");
    publisher = in.nextLine();
    if (publisher.equals(""))
      publisher = book.getPublisher();

    System.out.println("Please enter ISBN");
    isbn = in.nextLine();
    if (isbn.equals(""))
      isbn = book.getISBN();

    System.out.println("Please enter Cover");
    cover = in.nextLine();
    if (cover.equals(""))
      cover = book.getCover();

    System.out.println("Please enter Condition");
    condition = in.nextLine();
    if (condition.equals(""))
      condition = book.getCondition();

    System.out.println("Please enter Price");
    String strPrice = in.nextLine();
    if (strPrice.equals(""))
      price = book.getPrice();
    else
      price = Integer.parseInt(strPrice);

    System.out.println("Please enter Notes");
    notes = in.nextLine();
    if (notes.equals(""))
      notes = book.getNotes();

    return new Book(book.getID(),title,author,year,edition,publisher,isbn,cover,condition,price,notes);
  }
}