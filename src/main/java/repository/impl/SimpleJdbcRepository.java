package repository.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import annotation.Column;
import annotation.Entity;
import annotation.Table;
import mapper.ResultSetMapper;
import repository.JdbcRepository;
import utils.ConnectionUtils;

public class SimpleJdbcRepository<T> implements JdbcRepository<T> {
	
	private Class<T> tClass;
	
	public SimpleJdbcRepository() {
		tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public List<T> findByCondition(String sql) {
		
		List<T> results = new ArrayList<>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = ConnectionUtils.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			ResultSetMapper<T> resultSetMapper = new ResultSetMapper<>();
			
			//System.out.println("sql: " + sql);
			
			results = resultSetMapper.mapRow(rs, tClass);

		} catch (SQLException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn!=null) conn.close();
				if(stmt!=null) stmt.close();
				if(rs!=null) rs.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return results;
	}//findByCondition

	@Override
	public void insert(Object object) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = ConnectionUtils.getConnection();
			StringBuilder sql = createSQLInsert();
			stmt = conn.prepareStatement(sql.toString());
			Class<?> zClass = object.getClass();
			Field[] fields = zClass.getDeclaredFields();
			int parameterIndex = 1;
			for(Field field: fields) {
				field.setAccessible(true);
				stmt.setObject(parameterIndex, field.get(object));
				parameterIndex++;
			}
			Class<?> parentClass = zClass.getSuperclass();
			Field[] fieldParents = parentClass.getDeclaredFields();
			int indexParent = fields.length + 1;
			while (parentClass != null) {
				for (Field field : parentClass.getDeclaredFields()) {
					field.setAccessible(true);
					stmt.setObject(indexParent, field.get(object));
					indexParent++;
				}
				parentClass = parentClass.getSuperclass();
			}
			//System.out.println("insert, sql: "+sql);
			stmt.executeUpdate();
		} catch (SQLException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
	private StringBuilder createSQLInsert() {
		String tableName = "";
		if (tClass.isAnnotationPresent(Entity.class) && tClass.isAnnotationPresent(Table.class)) {
			Table table = tClass.getAnnotation(Table.class);
			tableName = table.name();
		}
		StringBuilder fields = new StringBuilder("");
		StringBuilder values = new StringBuilder("");
		for(Field field: tClass.getDeclaredFields()) {
			if(fields.length() > 1) {
				fields.append(",");
				values.append(",");
			}
			if(field.isAnnotationPresent(Column.class)) {
				Column column = field.getAnnotation(Column.class);
				fields.append(column.name());
				values.append("?");
			}
		}
		Class<?> parentClass = tClass.getSuperclass();
		while (parentClass != null) {
			for (Field field : parentClass.getDeclaredFields()) {
				if (fields.length() > 1) {
					fields.append(",");
					values.append(",");
				}
				if (field.isAnnotationPresent(Column.class)) {
					Column column = field.getAnnotation(Column.class);
					fields.append(column.name());
					values.append("?");
				}
			}
			parentClass = parentClass.getSuperclass();
		}//while
		
		StringBuilder sql = new StringBuilder(
				"insert into "+tableName+"("+fields.toString()+") values("+values+")"
		);
		//System.out.println("createSQLInsert(), sql: " + sql);
		return sql;
	}// createSQLInsert

	@Override
	public void delete(Long id, String field, Long id2, String field2) {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = ConnectionUtils.getConnection();
			stmt = conn.createStatement();
			String tableName = null;
			if (tClass.isAnnotationPresent(Entity.class) && tClass.isAnnotationPresent(Table.class)) {
				Table table = tClass.getAnnotation(Table.class);
				tableName = table.name();
			}
			String sql = "DELETE from "+tableName+" WHERE "+field+"="+id+" AND "+field2+"="+id2;
			System.out.println("delete, sql: " + sql);
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (conn != null)
					conn.close();
				if (stmt != null)
					stmt.close();
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
		
	}
	
}
