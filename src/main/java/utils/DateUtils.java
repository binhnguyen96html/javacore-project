package utils;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateUtils {
	
	  public static String convertDateToString(Date date) {
	        if (date == null) return null;
	        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
	        return dateFormat.format(date);
	    }
	  
}
