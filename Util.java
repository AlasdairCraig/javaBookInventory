//craig_20104588

import java.util.HashMap;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays; 


/**
* Java program to return hashmap from URL request line.
* <p>
* This class holds the methods for creating hashmap from URL request line
* <p>
* @author Darren Dancey
* <p> 
*/
public class Util {

  /**
  * Java program to return hashmap from URL request line.
  * <p>
  * This method creates hashmap from URL request line using "=" to denote splits. 
  * <p>
  * @author Darren Dancey
  * <p> 
  */
  public static HashMap<String, String> requestStringToMap(String request) {
    HashMap<String, String> map = new HashMap<String, String>();
    String[] pairs = request.split("&");
 

    
    for (int i = 0; i < pairs.length; i++) {
      String pair = pairs[i];

      try {
        String[] keyvalue = pair.split("=");
        
        String key = keyvalue[0];
        key = URLDecoder.decode(key, "UTF-8"); 
        
        String value ="";
        if (keyvalue.length > 1){
          value = pair.split("=")[1];
          value = URLDecoder.decode(value, "UTF-8");
        } 
 
        map.put(key, value);
      } catch (UnsupportedEncodingException e) {
        System.out.println(e.getMessage());
      }
    }
    //System.out.println("The Map in RequestString to Map:" +  map);
    return map;
  }
}