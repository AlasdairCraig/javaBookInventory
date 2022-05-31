//craig_20104588

/**
* A Book object.
* <p>
* This class represents a book
* <p>
* @author Alasdair Craig
*/
public class Book {

  private Integer id;
  private String title;
  private String author;
  private Integer year;
  private Integer edition;
  private String publisher;
  private String isbn;
  private String cover;
  private String condition;
  private Integer price; 
  private String notes;

  /**
  * This creates a single book.
  * <p>
  * An instance to create a single book
  * <p>
  * @param id  ID number for book  not null, unique
  * @param title  title of book
  * @param author  author of book
  * @param year  year of publication of book
  * @param edition  edition number of book
  * @param publisher  name of the publiser of book   
  * @param isbn  ISBN number for book.
  * @param cover  style of cover for book, eg. hardback, softback
  * @param condition  physical condition of book
  * @param price  current market value of book
  * @param notes  any additional information on book
  * <p>
  * @return Book  a single book
  * <p>
  * @author Alasdair Craig
  */  
  public Book (Integer id, String title, String author, Integer year, Integer edition, String publisher, String isbn, String cover, String condition, Integer price, String notes){
    this.id = id;
    this.title = title;
    this.author = author;
    this.year = year;
    this.edition = edition;
    this.publisher = publisher;
    this.isbn = isbn;
    this.cover = cover;
    this.condition = condition;
    this.price = price;
    this.notes = notes;  
  }

  /**
  * Gets book ID.
  * <p>
  * @return id  ID number for book  not null, unique
  * <p>
  * @author Alasdair Craig
  */
  public Integer getID() {
		return this.id;
	}

  /**
  * Set book ID.
  * <p>
  * @param id  ID number for book  not null, unique   
  * <p>
  * @author Alasdair Craig
  */
	public void setID(Integer id) {
		this.id = id;
	}

  /**
  * Gets book title.
  * <p>
  * @return title  title of book  
  * <p>
  * @author Alasdair Craig
  */
  public String getTitle() {
    return this.title;
	}
  
  /**
  * Sets book title.
  * <p>
  * @param title  title of book   
  * <p>
  * @author Alasdair Craig
  */
	public void setTitle(String title) {
		this.title = title;
	}

  /**
  * Gets book author.
  * <p>
  * @return author  author of book  
  * <p>
  * @author Alasdair Craig
  */
  public String getAuthor() {
		return this.author;
	}

  /**
  * Sets book author.
  * <p>
  * @param author  author of book   
  * <p>
  * @author Alasdair Craig
  */
	public void setAuthor(String author) {
		this.author = author;
	}

  /**
  * Gets book year.
  * <p>
  * @return year  year of publication  
  * <p>
  * @author Alasdair Craig
  */
  public Integer getYear() {
    if (this.year == null)
		return 0;
    else
    return this.year;
	}

  /**
  * Sets book year.
  * <p>
  * @param year  year of publication   
  * <p>
  * @author Alasdair Craig
  */
	public void setYear(Integer year) {
		this.year = year;
	}

  /**
  * Gets book edition.
  * <p>
  * @return edition  edition number of book  
  * <p>
  * @author Alasdair Craig
  */
  public Integer getEdition() {
		if (this.edition == null)
		return 0;
    else
    return this.edition;
	}

  /**
  * Sets book edition.
  * <p>
  * @param edition  edition number of book   
  * <p>
  * @author Alasdair Craig
  */
	public void setEdition(Integer edition) {
		this.edition = edition;
	}

  /**
  * Gets book publisher.
  * <p>
  * @return publisher  publisher of book  
  * <p>
  * @author Alasdair Craig
  */
  public String getPublisher() {
		return this.publisher;
	}

  /**
  * Sets book publisher.
  * <p>
  * @param publisher  publisher of book   
  * <p>
  * @author Alasdair Craig
  */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

  /**
  * Gets book ISBN.
  * <p>
  * @return isbn  ISBN of book  
  * <p>
  * @author Alasdair Craig
  */
  public String getISBN() {
		return this.isbn;
	}

  /**
  * Sets book ISBN.
  * <p>
  * @param isbn  ISBN of book   
  * <p>
  * @author Alasdair Craig
  */
	public void setISBN(String isbn) {
		this.isbn = isbn;
	}

  /**
  * Gets book cover.
  * <p>
  * @return cover  cover style of book  
  * <p>
  * @author Alasdair Craig
  */
  public String getCover() {
		return this.cover;
	}

  /**
  * Sets book cover.
  * <p>
  * @param cover  cover style of book   
  * <p>
  * @author Alasdair Craig
  */
	public void setCover(String cover) {
		this.cover = cover;
	}

  /**
  * Gets book condition.
  * <p>
  * @return condition  physical condition of book  
  * <p>
  * @author Alasdair Craig
  */
  public String getCondition() {
		return this.condition;
	}

  /**
  * Sets book condition.
  * <p>
  * @param condition  physical condition of book   
  * <p>
  * @author Alasdair Craig
  */
	public void setCondition(String condition) {
		this.condition = condition;
	}

  /**
  * Gets book price.
  * <p>
  * @return price  price of book in (£)  
  * <p>
  * @author Alasdair Craig
  */
  public Integer getPrice() {
		if (this.price == null)
		return 0;
    else
    return this.price;
	}

  /**
  * Sets book price.
  * <p>
  * @param price  price of book in (£)   
  * <p>
  * @author Alasdair Craig
  */
	public void setPrice(Integer price) {
		this.price = price;
	}

  /**
  * Gets book notes.
  * <p>
  * @return notes  any additional notes for book  
  * <p>
  * @author Alasdair Craig
  */
  public String getNotes() {
		return this.notes;
	}

  /**
  * Sets book notes.
  * <p>
  * @param notes  any additional notes for book   
  * <p>
  * @author Alasdair Craig
  */
	public void setNotes(String notes) {
		this.notes = notes;
	}
  
  /**
  * Returns values for book as String.
  * <p>
  * @returns String  all stored book values in single String    
  * <p>
  * @author Alasdair Craig
  */
  public String toString() {
		return "Book ID=" + this.id + "\nTitle=" + this.title + "\nAuthor=" + this.author + "\nYear=" + this.year + "\nEdition=" + this.edition + "\nPublisher=" + this.publisher + "\nISBN=" + this.isbn + "\nCover=" + this.cover + "\nCondition=" + this.condition + "\nPrice=" + this.price + "\nNotes=" + this.notes + "\n--------------------";
	}
}