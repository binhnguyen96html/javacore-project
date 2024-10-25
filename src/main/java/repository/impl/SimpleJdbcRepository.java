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

			// System.out.println("sql: " + sql);

			results = resultSetMapper.mapRow(rs, tClass);

		} catch (SQLException e) {
			System.out.println("SQLException-findByCondition: " + e);
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception-findByCondition: " + e);
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (stmt != null)
					stmt.close();
				if (rs != null)
					rs.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return results;
	}// findByCondition

	@Override
	public void insert(Object object, Connection conn) {
		// Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			// conn = ConnectionUtils.getConnection();
			StringBuilder sql = createSQLInsert();
			pstmt = conn.prepareStatement(sql.toString());

			Class<?> zClass = object.getClass();
			Field[] fields = zClass.getDeclaredFields();
			int parameterIndex = 1;
			for (Field field : fields) {
				field.setAccessible(true);
				pstmt.setObject(parameterIndex, field.get(object));
				parameterIndex++;
			}
			Class<?> parentClass = zClass.getSuperclass();
			Field[] fieldParents = parentClass.getDeclaredFields();
			int indexParent = fields.length + 1;
			while (parentClass != null) {
				for (Field field : parentClass.getDeclaredFields()) {
					field.setAccessible(true);
					pstmt.setObject(indexParent, field.get(object));
					indexParent++;
				}
				parentClass = parentClass.getSuperclass();
			}
			System.out.println("insert, sql: " + sql);
			pstmt.executeUpdate();
		} catch (SQLException | IllegalAccessException e) {
			System.out.println("insert: " + e);
			e.printStackTrace();
		} finally {
			try {
				// if(conn!=null) conn.close();
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}// insert

	private StringBuilder createSQLInsert() {
		String tableName = "";
		if (tClass.isAnnotationPresent(Entity.class) && tClass.isAnnotationPresent(Table.class)) {
			Table table = tClass.getAnnotation(Table.class);
			tableName = table.name();
		}
		StringBuilder fields = new StringBuilder("");
		StringBuilder values = new StringBuilder("");
		for (Field field : tClass.getDeclaredFields()) {
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
		} // while

		StringBuilder sql = new StringBuilder(
				"insert into " + tableName + "(" + fields.toString() + ") values(" + values + ")");
		System.out.println("createSQLInsert(), sql: " + sql);
		return sql;
	}// createSQLInsert

	@Override
	public void insertMany(List<T> objects, Connection conn) {
		PreparedStatement pstmt = null;

		try {
			StringBuilder sql = createSQLInsertMany();

			for (T object : objects) {

				Class<?> zClass = object.getClass();
				Field[] fields = zClass.getDeclaredFields();
				int parameterIndex = 1;

				sql.append(objects.indexOf(object) == 0 ? "(" : ",(");

				for (Field field : fields) {
					field.setAccessible(true);
					// pstmt.setObject(parameterIndex, field.get(object));
					// System.out.println("field.get(object): " + field.get(object));
					sql.append(field.get(object));
					if (parameterIndex != fields.length)
						sql.append(",");
					parameterIndex++;
				}

				Class<?> parentClass = zClass.getSuperclass();
				Field[] fieldParents = parentClass.getDeclaredFields();
				int indexParent = fields.length + 1;
				while (parentClass != null) {
					for (Field field : fieldParents) {
						if (!field.getName().equals("id")) {
							field.setAccessible(true);
							//pstmt.setObject(indexParent, field.get(object));
							if (indexParent != fieldParents.length + indexParent) sql.append(",");
							sql.append(field.get(object));
							indexParent++;
						}
					}
					parentClass = parentClass.getSuperclass();
				}
				sql.append(")");
			}
			pstmt = conn.prepareStatement(sql.toString());
			//System.out.println("sql1: " + sql);
			pstmt.execute();
		} catch (SQLException | IllegalAccessException e) {
			System.out.println("insert: " + e);
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}// insertMany

	private StringBuilder createSQLInsertMany() {
		String tableName = "";
		if (tClass.isAnnotationPresent(Entity.class) && tClass.isAnnotationPresent(Table.class)) {
			Table table = tClass.getAnnotation(Table.class);
			tableName = table.name();
		}
		StringBuilder fields = new StringBuilder("");
		StringBuilder values = new StringBuilder("");
		for (Field field : tClass.getDeclaredFields()) {
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
		Class<?> parentClass = tClass.getSuperclass();
		while (parentClass != null) {
			for (Field field : parentClass.getDeclaredFields()) {
				if (!field.getName().equals("id")) {
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
			}
			parentClass = parentClass.getSuperclass();
		} // while

		StringBuilder sql = new StringBuilder("insert into " + tableName + "(" + fields.toString() + ") values");
		System.out.println("createSQLInsert(), sql: " + sql);
		return sql;
	}// createSQLInsertMany

	@Override
	public void delete(Long id, String field, Long id2, String field2, Connection conn) {

		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.createStatement();
			String tableName = null;
			if (tClass.isAnnotationPresent(Entity.class) && tClass.isAnnotationPresent(Table.class)) {
				Table table = tClass.getAnnotation(Table.class);
				tableName = table.name();
			}
			String sql = "DELETE from " + tableName + " WHERE " + field + "=" + id + " AND " + field2 + "=" + id2;
			// System.out.println("delete, sql: " + sql);
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					if (stmt != null)
						stmt.close();
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}

	}

	@Override
	public void deleteMany(Long id, String field, List<Long> ids, String field2, Connection conn) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String tableName = null;
			if (tClass.isAnnotationPresent(Entity.class) && tClass.isAnnotationPresent(Table.class)) {
				Table table = tClass.getAnnotation(Table.class);
				tableName = table.name();
			}
			StringBuilder sql = new StringBuilder(
					"DELETE from " + tableName + " WHERE " + field + "=" + id + " AND " + field2 + " IN(");
			for (int i = 0; i < ids.size(); i++) {
				sql.append(ids.get(i));
				if (i < ids.size() - 1) {
					sql.append(",");
				}
			}
			sql.append(")");

			// System.out.println("delete, sql: " + sql);

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					// conn.close();
					if (pstmt != null)
						pstmt.close();
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}// deleteMany

	@Override
	public T findById(Long id) {
		List<T> results = new ArrayList<>();

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

			String sql = "select * from " + tableName + " where id = " + id;
			rs = stmt.executeQuery(sql);

			// System.out.println("sql: " + sql);

			ResultSetMapper<T> resultsetMapper = new ResultSetMapper<>();

			results = resultsetMapper.mapRow(rs, tClass);

			return results.size() > 0 ? results.get(0) : null;

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		} finally {
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

		return null;
	}// findById

}
