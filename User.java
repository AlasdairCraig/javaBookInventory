//craig_20104588

/**
* A User object.
* <p>
* This class represents a user
* <p>
* @author Alasdair Craig
*/
public class User {

  private String userName;
  private String password;
  
  /**
  * A User constructor
  * <p>
  * This constructor will create a new User
  * <p>
  * @param userName  userName inputted by user
  * <p>
  * @author Alasdair Craig
  */
  public User (String userName){
    this.userName = userName;
  }

   /**
  * A User constructor
  * <p>
  * This constructor will create a new User
  * <p>
  * @param userName  userName inputted by user
  * @param password  password inputted by user
  * <p>
  * @author Alasdair Craig
  */
  public User (String userName, String password){
    this.userName = userName;
    this.password = password;
  }

  /**
  * Gets User Name.
  * <p>
  * @return userName  User Name for user  not null, unique
  * <p>
  * @author Alasdair Craig
  */
  public String getUserName() {
		return this.userName;
	}

   /**
  * Sets User Name.
  * <p>
  * @param userName  User Name for user  not null, unique
  * <p>
  * @author Alasdair Craig
  */
	public void setID(String userName) {
		this.userName = userName;
	}

  /**
  * Gets User password
  * <p>
  * @return password  Password for user
  * <p>
  * @author Alasdair Craig
  */
  public String getPassword() {
		return this.password;
	}

  /**
  * Sets User password
  * <p>
  * @param password  Password for user
  * <p>
  * @author Alasdair Craig
  */
	public void setPassword(String password) {
		this.password = password;
	}
    
  /**
  * Returns values for User as String.
  * <p>
  * @returns String  all stored User values in single String    
  * <p>
  * @author Alasdair Craig
  */ 
  public String toString() {
		return "User [User Name=" + this.userName + ", Password=" + this.password + " ]";
	}
}