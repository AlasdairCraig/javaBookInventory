//craig_20104588

import java.math.BigInteger; 
import java.nio.charset.StandardCharsets; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 

/**
* Java program to calculate SHA hash value
* <p>
* This class holds the methods for hashing a String input using SHA-256
* <p>
* @author Bilal Hungund
* <p>
* @link https://www.geeksforgeeks.org/sha-256-hash-in-java/  
*/
class ShaUtil { 

  /**
  * 
  * <p>
  * This method retrieves SHA-256 algorithm.
  * <p>
  * @param input  takes user password input from NewUserHandler
  * <p>
  * @author Bilal Hungund
  * <p>
  * @link https://www.geeksforgeeks.org/sha-256-hash-in-java/  
  */
	public static byte[] getSHA(String input) throws NoSuchAlgorithmException 
	{ 
		// Static getInstance method is called with hashing SHA 
		MessageDigest md = MessageDigest.getInstance("SHA-256"); 

		// digest() method called 
		// to calculate message digest of an input 
		// and return array of byte 
		return md.digest(input.getBytes(StandardCharsets.UTF_8)); 
	} 
	
  /**
  * 
  * <p>
  * This method converts String input for hashing
  * <p>
  * @param hash 
  * <p>
  * @author Bilal Hungund
  * <p>
  * @link https://www.geeksforgeeks.org/sha-256-hash-in-java/  
  */
	public static String toHexString(byte[] hash) 
	{ 
		// Convert byte array into signum representation 
		BigInteger number = new BigInteger(1, hash); 

    //System.out.println(number);

		// Convert message digest into hex value 
		StringBuilder hexString = new StringBuilder(number.toString(16)); 

		// Pad with leading zeros 
		while (hexString.length() < 32) 
		{ 
			hexString.insert(0, '0'); 
		} 

		return hexString.toString(); 
	} 
} 
