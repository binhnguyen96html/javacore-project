package utils;

import com.mysql.cj.util.StringUtils;

public class BuildQueryUsing {

	public static <T> String withoutJoin(String table, String field, T input) {
		if( (input instanceof String && !StringUtils.isNullOrEmpty((String) input))
				|| (input instanceof Integer && input != null) ) {
			
			if(!StringUtils.isNullOrEmpty(table)
					&& !StringUtils.isNullOrEmpty(field)) {
				return " AND "+table+"."+field+" LIKE '%"+input+"%' ";
			}
		}
		
		return "";
	}
	
	public static <T> String join(
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
