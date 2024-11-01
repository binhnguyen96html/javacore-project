package utils;

import java.util.ArrayList;
import java.util.List;

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
	
	  public static String buildQueryWithLike(String column, String value) {
	        return (!ValidateUtils.isValid(value)) ? ""
	                : String.format("%nAND lower(%s) LIKE %s", column, "'%" + value.toLowerCase() + "%'");
	    }

	    public static String buildQueryWithOperator(String column, Object value, String operator) {
	        if (!ValidateUtils.isValid(value)) {
	            return "";
	        }

	        return (value instanceof String) ? String.format("%nAND %s %s '%s'", column, operator, value)
	                : String.format("%nAND %s %s %s", column, operator, value);
	    }
	    

	    public static String buildQueryWithBetween(String column, Object from, Object to) {
	        if (null == from && null == to) {
	            return "";
	        } else {
	            if (null == from) {
	                from = 0;
	            }
	            if (null == to) {
	                if (from instanceof Integer) {
	                    to = Integer.MAX_VALUE;
	                } else if (from instanceof Double) {
	                    to = Double.MAX_VALUE;
	                }
	            }

	            return String.format("%nAND %s BETWEEN %s AND %s", column, from, to);
	        }
	    }

	    
	    public static <T> String buildQueryWithIn(String column, List<T> values) {
	        List<Object> convertedValues = new ArrayList<>();
	        String joinValues;

	        if (values.get(0) instanceof String) {
	            for (Object item : values) {
	                StringBuilder convertedItem = new StringBuilder();

	                convertedItem.append("'")
	                        .append(item.toString())
	                        .append("'");

	                convertedValues.add(convertedItem.toString());
	            }
	            joinValues = String.join(",", (List<String>) (Object) convertedValues);
	        } else {
	            String listString = values.toString();
	            joinValues = listString.substring(1, listString.length() - 1);
	        }

	        return String.format("%nAND %s IN (%s)", column, joinValues);
	    }

}
