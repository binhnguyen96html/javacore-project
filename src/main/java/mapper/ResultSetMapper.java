package mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import annotation.Column;

public class ResultSetMapper<T> {

	public List<T> mapRow(ResultSet rs, Class<T> tClass) {

		List<T> results = new ArrayList<>();

		try {
			Field[] fields = tClass.getDeclaredFields();
			ResultSetMetaData resultSetMetaData = rs.getMetaData();

			while (rs.next()) {
				//System.out.println("rs: " + rs.getString("district_name"));
				T object = tClass.newInstance();
				
				//System.out.println("resultSetMetaData.getColoumnCount(): "+resultSetMetaData.getColumnCount());

				for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
					String columnName = resultSetMetaData.getColumnName(i + 1);
					Object columnValue = rs.getObject(i + 1);
					
					//System.out.println("columnName: " + columnName + ", columnValue: " + columnValue);

					for (Field field : fields) {
						//System.out.println("field: " + field);
						if (field.isAnnotationPresent(Column.class)) {
							
							Column column = field.getAnnotation(Column.class);
							//System.out.println("column.name(): "+column.name());
							
							if (column.name().equals(columnName) && columnValue != null) {
								//System.out.println("column.name(): "+column.name());
								BeanUtils.setProperty(object, field.getName(), columnValue);
								break;
							}
						}
					} // for

					Class<?> parentClass = tClass.getSuperclass();
					while (parentClass != null) {
						for (Field field : parentClass.getDeclaredFields()) {
							if (field.isAnnotationPresent(Column.class)) {
								Column column = field.getAnnotation(Column.class);
								if (column.name().equals(columnName) && columnValue != null) {
									BeanUtils.setProperty(object, field.getName(), columnValue);
									break;
								}
							}
						} // for
						parentClass = parentClass.getSuperclass();
					} // while
				} // for
				results.add(object);
			}//while
			return results;
		} catch (SQLException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
			System.out.println(e);
		}

		return results;
	}
}
