//craig_20104588

import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;

/**
* A Data Access Object for accessing Book Collection database "books.sqlite".
* <p>
* This class includes methods for connecting with database and using prepared SQL 
* statements.
* <p>
* @author Alasdair Craig
*/ 
public class BookDao {

  public BookDao(){}
	
	private static Connection getDBConnection() { //Connection to database
		Connection dbConnection = null;
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			String dbURL = "jdbc:sqlite:books.sqlite";
			dbConnection = DriverManager.getConnection(dbURL);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}

  /**
  * Gets all books in database.
  * <p>
  * @return ArrayList<Book>  an array list of all books and book values in database
  * <p>
  * @author Alasdair Craig
  */
	public ArrayList<Book> getAllBooks() throws SQLException {
		Connection dbConnection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		
  	ArrayList<Book> books = new ArrayList<>();
    
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.prepareStatement("SELECT * FROM collection;");
			//System.out.println("DBQuery = " + statement);
      result = statement.executeQuery(); //Execute SQL query & record response to string
			while (result.next()) {

				Integer id = result.getInt("ID");
				String title = result.getString("Title");
				String author = result.getString("Author");
				Integer year = result.getInt("Year");
        Integer edition = result.getInt("Edition");
        String publisher = result.getString("Publisher");
        String isbn = result.getString("ISBN");
        String cover = result.getString("Cover");
        String condition = result.getString("Condition");
        Integer price = result.getInt("Price");
        String notes = result.getString("Notes");
				
				books.add(new Book(id,title,author,year,edition,publisher,isbn,cover,condition,price,notes));
			}
		} catch(Exception e) {
			System.out.println("get all books: "+e);
		} finally {
			if (result != null) {
				result.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		return books;
	}

  /**
  * Gets a single book by ID.
  * <p>
  * @param book_id  ID number for book
  * <p>
  * @return Book  a single book  
  * <p>
  * @author Alasdair Craig
  */
	public Book getBookByID(Integer book_id) throws SQLException {
		Book temp = null;
		Connection dbConnection = null;
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.prepareStatement("SELECT * FROM collection WHERE ID = ?;");
			//System.out.println("DBQuery: " + statement);
      statement.setInt(1, book_id);
			result = statement.executeQuery(); //Execute SQL query and record response to string

			while (result.next()) {

				Integer id = result.getInt("ID");
				String title = result.getString("Title");
				String author = result.getString("Author");
				Integer year = result.getInt("Year");
        Integer edition = result.getInt("Edition");
        String publisher = result.getString("Publisher");
        String isbn = result.getString("ISBN");
        String cover = result.getString("Cover");
        String condition = result.getString("Condition");
        Integer price = result.getInt("Price");
        String notes = result.getString("Notes");
				
				temp = new Book(id,title,author,year,edition,publisher,isbn,cover,condition,price,notes);
			}
		} finally {
			if (result != null) {
				result.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		return temp;
	}

  /**
  * Gets all books defined by search details.
  * <p>
  * @param searchReq  a string compiling one to three parameters (ID; Title; Author)
  * <p>
  * @return ArrayList<Book>  an array list of all books and book values in search results  
  * <p>
  * @author Alasdair Craig
  */
	public ArrayList<Book> searchBooks(String searchReq) throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;
		ResultSet result = null;
	
    String query = "SELECT * FROM collection WHERE " + searchReq + ";";

		ArrayList<Book> books = new ArrayList<>();
    
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			//System.out.println("DBQuery = " + query);
      result = statement.executeQuery(query); // Execute SQL query and record response to string
			while (result.next()) {

				Integer id = result.getInt("ID");
				String title = result.getString("Title");
				String author = result.getString("Author");
				Integer year = result.getInt("Year");
        Integer edition = result.getInt("Edition");
        String publisher = result.getString("Publisher");
        String isbn = result.getString("ISBN");
        String cover = result.getString("Cover");
        String condition = result.getString("Condition");
        Integer price = result.getInt("Price");
        String notes = result.getString("Notes");
				
				books.add(new Book(id,title,author,year,edition,publisher,isbn,cover,condition,price,notes));
			}
		} catch(Exception e) {
			System.out.println("Search error: Please enter more information into search.");
      System.out.println("--------------------");
		} finally {
			if (result != null) {
				result.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		return books;
	}

  /**
  * Deletes a single book from database.
  * <p>
  * @param book_id  ID number for book  
  * <p>
  * @author Alasdair Craig
  */
	public Boolean deleteBook(Integer book_id) throws SQLException {
		System.out.println("Deleting book from inventory: ID= " + book_id);
    System.out.println("--------------------");
		Connection dbConnection = null;
		PreparedStatement statement = null;
		Integer result = 0;
			
    try {
			dbConnection = getDBConnection();
			statement = dbConnection.prepareStatement("DELETE FROM collection WHERE ID = ?;");
		  //System.out.println(query);
			//execute SQL query
      statement.setInt(1, book_id);
			result = statement.executeUpdate();
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		if (result == 1) {
			return true;
		} else {
			return false;
		}
	}

  /**
  * Inserts a single book into database
  * <p>
  * @param in  a single book to be inserted into database  
  * <p>
  * @author Alasdair Craig
  */
  public boolean insertBook(Book in) throws SQLException{
		Connection dbConnection = null;
		PreparedStatement statement = null;
			
    boolean ok = false;
		
    try {
      dbConnection = getDBConnection();
      statement = dbConnection.prepareStatement("INSERT INTO collection (ID, Title, Author, Year, Edition, Publisher, ISBN, Cover, Condition, Price, Notes) VALUES (?,?,?,?,?,?,?,?,?,?,?);");
      //System.out.println(statement);
      // execute SQL query
      statement.setInt(1, in.getID());
      statement.setString(2, in.getTitle());
      statement.setString(3, in.getAuthor());
      statement.setInt(4, in.getYear());
      statement.setInt(5, in.getEdition());
      statement.setString(6, in.getPublisher());
      statement.setString(7, in.getISBN());
      statement.setString(8, in.getCover());
      statement.setString(9, in.getCondition());
      statement.setInt(10, in.getPrice());
      statement.setString(11, in.getNotes());
      statement.executeUpdate();
      ok = true;
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      if (statement != null) {
        statement.close();
      }
      if (dbConnection != null) {
        dbConnection.close();
      }		
    }
    return ok;
	}

  /**
  * Updates a single book into database
  * <p>
  * @param book  a single book to be updated in database  
  * <p>
  * @author Alasdair Craig
  */
	public Boolean updateBook(Book book) throws SQLException {
		Connection dbConnection = null;
		PreparedStatement statement = null;

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.prepareStatement("UPDATE collection SET ID = ?, Title = ?, Author= ?, Year= ?, Edition=?, Publisher= ?, ISBN= ?, Cover= ?, Condition= ?, Price= ?, Notes= ? WHERE ID = ?;");
		  //System.out.println(statement);
			//execute SQL update

      statement.setInt(1, book.getID());
      statement.setString(2, book.getTitle());
      statement.setString(3, book.getAuthor());
      statement.setInt(4, book.getYear());
      statement.setInt(5, book.getEdition());
      statement.setString(6, book.getPublisher());
      statement.setString(7, book.getISBN());
      statement.setString(8, book.getCover());
      statement.setString(9, book.getCondition());
      statement.setInt(10, book.getPrice());
      statement.setString(11, book.getNotes());
      statement.setInt(12, book.getID());
      
			statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;    
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		return true;
	}

  /**
  * Checks for correct 10 or 13-digit entry for ISBN. 
  * <p>
  * @param isbn  the isbn for book being added or updated 
  * <p>
  * @author Alasdair Craig
  */
  public Boolean checkIsbnLength(String isbn) {

    isbn = isbn.replace("-", ""); // remove all hyphens
    isbn = isbn.replace(" ", ""); // remove all spaces

    //System.out.println("In check ISBN Length: " + isbn);
    //System.out.println(isbn.length());
    
    int isbnLength = isbn.length();
    if (isbnLength == 10){
      return isISBN10(isbn);
    } else if (isbnLength == 13) {
      return isISBN13(isbn);
    } else {
      return false;
    }
  }

  /**
  * Checks for correct checkdigit sum for 10-digit ISBN.  
  * <p>
  * @param isbn  the isbn for book being added or updated 
  * <p>
  * @author Alasdair Craig
  */
  public Boolean isISBN10(String isbn){
    int int1 = Character.getNumericValue(isbn.charAt(0));
    int int2 = Character.getNumericValue(isbn.charAt(1));
    int int3 = Character.getNumericValue(isbn.charAt(2));
    int int4 = Character.getNumericValue(isbn.charAt(3));
    int int5 = Character.getNumericValue(isbn.charAt(4));
    int int6 = Character.getNumericValue(isbn.charAt(5));
    int int7 = Character.getNumericValue(isbn.charAt(6));
    int int8 = Character.getNumericValue(isbn.charAt(7));
    int int9 = Character.getNumericValue(isbn.charAt(8));
    int int10;
    char char10 = isbn.charAt(9); 
    char10 = Character.toUpperCase(char10);
    if (char10 == 'X'){
     int10 = 10;
    }else{
      int10 = Character.getNumericValue(isbn.charAt(9));
    }
    
    int checkSum = ((int1*10) + (int2*9) + (int3*8) + (int4*7) + (int5*6) + (int6*5) + (int7*4) + (int8*3) + (int9*2));

    //System.out.println("In checkSum");
    //System.out.println(checkSum);

    if ((checkSum + int10) % 11 == 0) { 
      return true;
    }else{
      return false;
    }
  }

  /**
  * Checks for correct checkdigit sum for 13-digit ISBN.  
  * <p>
  * @param isbn  the isbn for book being added or updated 
  * <p>
  * @author Alasdair Craig
  */
  public Boolean isISBN13(String isbn){
    int int1 = Character.getNumericValue(isbn.charAt(0));
    int int2 = Character.getNumericValue(isbn.charAt(1));
    int int3 = Character.getNumericValue(isbn.charAt(2));
    int int4 = Character.getNumericValue(isbn.charAt(3));
    int int5 = Character.getNumericValue(isbn.charAt(4));
    int int6 = Character.getNumericValue(isbn.charAt(5));
    int int7 = Character.getNumericValue(isbn.charAt(6));
    int int8 = Character.getNumericValue(isbn.charAt(7));
    int int9 = Character.getNumericValue(isbn.charAt(8));
    int int10 = Character.getNumericValue(isbn.charAt(9));
    int int11 = Character.getNumericValue(isbn.charAt(10));
    int int12 = Character.getNumericValue(isbn.charAt(11));
    int int13 = Character.getNumericValue(isbn.charAt(12));
    
    int checkSum = ((int1*1) + (int2*3) + (int3*1) + (int4*3) + (int5*1) + (int6*3) + (int7*1) + (int8*3) + (int9*1) + (int10*3) + (int11*1) + (int12*3));

    //System.out.println("In checkSum");
    //System.out.println(checkSum);

    if ((checkSum + int13) % 10 == 0) { 
      return true;
    }else{
      return false;
    }
  }

  /**
  * Inserts a sold book into Sales Records database
  * <p>
  * @param in  a single book to be inserted into sales database  
  * <p>
  * @author Alasdair Craig
  */
  public boolean sellBook(Book sold) throws SQLException{
		Connection dbConnection = null;
		PreparedStatement statement = null;   
			
    boolean ok = false;
		
    try {
      dbConnection = getDBConnection();
      statement = dbConnection.prepareStatement("INSERT INTO sales (ID, Title, Author, Year, Edition, Publisher, ISBN, Cover, Condition, Price, Notes) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
      //System.out.println(statement);
      // execute SQL query

      statement.setInt(1, DeleteBookHandler.newSoldBookID);
      statement.setString(2, sold.getTitle());
      statement.setString(3, sold.getAuthor());
      statement.setInt(4, sold.getYear());
      statement.setInt(5, sold.getEdition());
      statement.setString(6, sold.getPublisher());
      statement.setString(7, sold.getISBN());
      statement.setString(8, sold.getCover());
      statement.setString(9, sold.getCondition());
      statement.setInt(10, sold.getPrice());
      statement.setString(11, sold.getNotes());

      statement.executeUpdate();
      ok = true;
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      if (statement != null) {
        statement.close();
      }
      if (dbConnection != null) {
        dbConnection.close();
      }		
    }
    return ok;
	}

  /**
  * Gets all sold books in sales database.
  * <p>
  * @return ArrayList<Book>  an array list of all books and book values in sales database
  * <p>
  * @author Alasdair Craig
  */
	public ArrayList<Book> getAllSoldBooks() throws SQLException {
		Connection dbConnection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		
		ArrayList<Book> books = new ArrayList<>();
    
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.prepareStatement("SELECT * FROM sales;");
			//System.out.println("DBQuery = " + query);
      result = statement.executeQuery(); //Execute SQL query & record response to string
			while (result.next()) {

				Integer id = result.getInt("ID");
				String title = result.getString("Title");
				String author = result.getString("Author");
				Integer year = result.getInt("Year");
        Integer edition = result.getInt("Edition");
        String publisher = result.getString("Publisher");
        String isbn = result.getString("ISBN");
        String cover = result.getString("Cover");
        String condition = result.getString("Condition");
        Integer price = result.getInt("Price");
        String notes = result.getString("Notes");
				
				books.add(new Book(id,title,author,year,edition,publisher,isbn,cover,condition,price,notes));
			}
		} catch(Exception e) {
			System.out.println("get all books: "+e);
		} finally {
			if (result != null) {
				result.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		return books;
	}
}