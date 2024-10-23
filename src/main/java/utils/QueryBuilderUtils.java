package utils;

import com.mysql.cj.util.StringUtils;

public class QueryBuilderUtils {

	public static String buildQueryUsingLike(String table, String field, String input) {
		if(!StringUtils.isNullOrEmpty(input)) {
			if(!StringUtils.isNullOrEmpty(table)
					&& !StringUtils.isNullOrEmpty(field)) {
				return " AND "+table+"."+field+" LIKE '%"+input+"%' ";
			}
		}
		
		return "";
	}
	
	public static <T> String buildQueryUsingEqualOperator(String table, String field, T input) {
		if(input != null){
			if(!StringUtils.isNullOrEmpty(table)
					&& !StringUtils.isNullOrEmpty(field)) {
				return " AND "+table+"."+field+"="+input+" ";
			}
		}
		
		return "";
	}
	
	public static <T> String buildingQueryWithJoin(
			String table1, 
			String field1, 
			String table2, 
			String field2) {
		
			if(!StringUtils.isNullOrEmpty(table1)
					&& !StringUtils.isNullOrEmpty(field1)
					&& !StringUtils.isNullOrEmpty(table2)
					&& !StringUtils.isNullOrEmpty(field2)
					) {
				return " INNER JOIN "+table1+" ON "+table1+"."+field1+"="+table2+"."+field2+" ";
			}

			return "";
	}

}
