//craig_20104588

import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.security.NoSuchAlgorithmException; 

/**
* A Data Access Object for accessing User Collection database in "books.sqlite".
* <p>
* This class includes methods for connecting with database and using prepared SQL 
* statements.
* <p>
* @author Alasdair Craig
*/ 
public class UserDao {

  public UserDao() {}

	private static Connection getDBConnection() {  //Connection
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
  * Gets a single User in database.
  * <p>
  * @param user_ID  User Name inputted by user
  * @param pass  Password inputted by user
  * <p>
  * @return User  a user stored in database
  * <p>
  * @author Alasdair Craig
  */
  public User getUser(String user_ID, String pass) throws SQLException, NoSuchAlgorithmException {

    User temp = null;
    Connection dbConnection = null;
    Statement statement = null;
    ResultSet result = null;

    String query = "SELECT * FROM users WHERE userName =\'" + user_ID + "\' AND password =\'" + ShaUtil.toHexString(ShaUtil.getSHA(pass)) + "\';";

    try {
      dbConnection = getDBConnection();
      statement = dbConnection.createStatement();
      //System.out.println("DBQuery: " + query);
      //execute SQL query
      result = statement.executeQuery(query);

      while (result.next()) {
 
        String userName = result.getString("userName");
        String password = result.getString("Password");
      
        temp = new User(userName,password);
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
  * Checks login details for User
  * <p>
  * @param userName  User Name inputted by user
  * @param password  Password inputted by user
  * <p>
  * @author Alasdair Craig
  */
  public Boolean checkLoginCredentials(String userName, String password) throws SQLException,NoSuchAlgorithmException{

    User temp = getUser(userName,password);
      
      if (temp == null){
        return false;
      }else{
        return true;
      }
  }

  /**
  * Inserts new user into database
  * <p>
  * @param in  user to be inserted into database
  * <p>
  * @author Alasdair Craig
  */
  public boolean insertUser(User in) throws SQLException,NoSuchAlgorithmException{
		Connection dbConnection = null;
		Statement statement = null;
		
    String update = "INSERT INTO users (userName, password) VALUES ('"+in.getUserName()+"','"+ ShaUtil.toHexString(ShaUtil.getSHA(in.getPassword()))+ "');";
		
    boolean ok = false;
		
    	try {
					dbConnection = getDBConnection();
					statement = dbConnection.createStatement();
		//System.out.println(update);
		// execute SQL query
					statement.executeUpdate(update);
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
  * Gets User Name.
  * <p>
  * @param user_ID  User Name inputted by user
  * <p>
  * @author Alasdair Craig
  */
  public User getUserName(String user_ID) throws SQLException, NoSuchAlgorithmException {

    User temp = null;
    Connection dbConnection = null;
    Statement statement = null;
    ResultSet result = null;

    String query = "SELECT * FROM users WHERE userName =\'" + user_ID + "\';";

    try {
      dbConnection = getDBConnection();
      statement = dbConnection.createStatement();
      //System.out.println("DBQuery: " + query);
      //execute SQL query
      result = statement.executeQuery(query);

      while (result.next()) {
 
        String userName = result.getString("userName");
        String password = result.getString("Password");
      
        temp = new User(userName,password);
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
}