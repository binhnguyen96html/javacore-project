package utils;

public class StringUtils {

	public static boolean isNullOrEmpty(String value) {
		if(value != null && !value.isEmpty()) {
			return false;
		}
		return true;
	}
}
