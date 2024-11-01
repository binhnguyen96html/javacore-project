package repository.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.DriverManager;
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

public class SimpleJdbcRepository<T> implements JdbcRepository<T> {

	protected Class<T> tClass;
	protected ResultSetMapper<T> resultSetMapper = new ResultSetMapper<T>();

	@SuppressWarnings("unchecked")
	public SimpleJdbcRepository() {
		tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public List<T> findAll() {
		String tableName = getTableName();
		if (tableName == null) {
			return null;
		}

		List<T> results = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			String sql = "SELECT * FROM " + tableName;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			results = resultSetMapper.mapRow(rs, tClass);

			return results;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, stmt, rs);
		}

		return null;
	}

	@Override
	public T findById(Long id) {
		String tableName = getTableName();
		if (tableName == null) {
			return null;
		}

		List<T> results = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			String sql = "SELECT * FROM " + tableName + "  WHERE id = " + id;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			results = resultSetMapper.mapRow(rs, tClass);

			return results.size() > 0 ? results.get(0) : null;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, stmt, rs);
		}

		return null;
	}

	@Override
	public List<T> findByCondition(String sql) {
		List<T> results;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			results = resultSetMapper.mapRow(rs, tClass);

			return results;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, stmt, rs);
		}

		return null;
	}

	@Override
	public void delete(Long id) {
		String tableName = getTableName();
		if (tableName == null) {
			return;
		}
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = getConnection();
			String sql = "DELETE FROM " + tableName + "  WHERE id = " + id;
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, stmt, null);
		}
	}

	@Override
	public void update(Object object) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			StringBuilder sql = createSqlUpdate();
			pstmt = conn.prepareStatement(sql.toString());

			Class<?> zClass = object.getClass();
			Field[] fields = zClass.getDeclaredFields();
			int paramIndex = 1;
			for (Field field : fields) {
				if (!field.isAnnotationPresent(Column.class))
					continue;
				field.setAccessible(true);
				pstmt.setObject(paramIndex++, field.get(object));
			}

			// Scan parent's fields
			Class<?> parentClass = tClass.getSuperclass();
			Field[] parentFields = parentClass.getDeclaredFields();
			Long id = null;
			while (parentClass != null && parentClass.isAnnotationPresent(Entity.class)) {
				for (Field field : parentFields) {
					field.setAccessible(true);
					if (!field.isAnnotationPresent(Column.class))
						continue;
					if (!field.getName().equals("id")) {
						pstmt.setObject(paramIndex++, field.get(object));
					} else {
						id = (Long) field.get(object);
					}
				}
				parentClass = parentClass.getSuperclass();
			}
			// set ID
			if (id != null) {
				pstmt.setObject(paramIndex, id);
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, null);
		}
	}

	@Override
	public Long insert(Object object) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			StringBuilder sql = createSqlInsert();
			if (sql == null) {
				return null;
			}
			pstmt = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

			Class<?> zClass = object.getClass();
			Field[] fields = zClass.getDeclaredFields();
			int paramIndex = 1;
			for (Field field : fields) {
				field.setAccessible(true);
				pstmt.setObject(paramIndex++, field.get(object));
			}

			// Scan parent's fields
			Class<?> parentClass = tClass.getSuperclass();
			Field[] parentFields = parentClass.getDeclaredFields();
			int paramParentIndex = fields.length + 1;
			while (parentClass != null && parentClass.isAnnotationPresent(Entity.class)) {
				for (Field field : parentFields) {
					if (!field.isAnnotationPresent(Column.class) || field.getName().equals("id"))
						continue;
					field.setAccessible(true);
					pstmt.setObject(paramParentIndex++, field.get(object));
				}
				parentClass = parentClass.getSuperclass();
			}

			int flag = pstmt.executeUpdate();
			if (flag > 0) {
				rs = pstmt.getGeneratedKeys();
				return rs.next() ? rs.getLong(1) : null;
			}

		} catch (SQLException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}

		return null;
	}

	@Override
	public void saveAll(List<T> objects) {
		if (objects.isEmpty())
			return;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			StringBuilder query = createInsertQuery(objects.size());

			if (query == null)
				return;

			pstmt = conn.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);

			//Class<?> zClass = objects.getFirst().getClass();
			Class<?> zClass = objects.get(0).getClass();
			Field[] fields = zClass.getDeclaredFields();
			int paramIndex = 1;

			for (Object obj : objects) {
				for (Field field : fields) {
					field.setAccessible(true);
					pstmt.setObject(paramIndex++, field.get(obj));
				}

				// Scan parent's fields
				Class<?> parentClass = tClass.getSuperclass();
				Field[] parentFields = parentClass.getDeclaredFields();

				while (parentClass != null && parentClass.isAnnotationPresent(Entity.class)) {
					for (Field field : parentFields) {
						if (!field.isAnnotationPresent(Column.class) || field.getName().equals("id"))
							continue;
						field.setAccessible(true);
						pstmt.setObject(paramIndex++, field.get(obj));
					}
					parentClass = parentClass.getSuperclass();
				}
			}

			pstmt.executeUpdate();
		} catch (SQLException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private StringBuilder createInsertQuery(int size) {
		String tableName = getTableName();

		if (tableName == null) {
			return null;
		}

		StringBuilder fields = new StringBuilder();
		StringBuilder params = new StringBuilder();

		for (Field field : tClass.getDeclaredFields()) {
			if (!field.isAnnotationPresent(Column.class))
				continue;
			if (fields.length() > 1) {
				fields.append(", ");
				params.append(", ");
			}
			Column column = field.getAnnotation(Column.class);
			fields.append(column.name());
			params.append("?");
		}

		// Scan parent's fields
		Class<?> parentClass = tClass.getSuperclass();
		Field[] parentFields = parentClass.getDeclaredFields();
		while (parentClass != null && parentClass.isAnnotationPresent(Entity.class)) {
			for (Field field : parentFields) {
				if (!field.isAnnotationPresent(Column.class) || field.getName().equals("id"))
					continue;
				fields.append(", ");
				params.append(", ");
				Column column = field.getAnnotation(Column.class);
				fields.append(column.name());
				params.append("?");
			}
			parentClass = parentClass.getSuperclass();
		}

		StringBuilder query = new StringBuilder().append(" insert into ").append(tableName).append("(").append(fields)
				.append(")").append(" values (").append(params).append(")");

		for (int i = 0; i < size - 1; i++) {
			query.append(", ").append("(").append(params).append(")");
		}

		return query;
	}

	private StringBuilder createSqlInsert() {
		String tableName = getTableName();
		if (tableName == null) {
			return null;
		}

		StringBuilder fields = new StringBuilder();
		StringBuilder params = new StringBuilder();

		for (Field field : tClass.getDeclaredFields()) {
			if (!field.isAnnotationPresent(Column.class))
				continue;
			if (fields.length() > 1) {
				fields.append(", ");
				params.append(", ");
			}
			Column column = field.getAnnotation(Column.class);
			fields.append(column.name());
			params.append("?");
		}

		// Scan parent's fields
		Class<?> parentClass = tClass.getSuperclass();
		Field[] parentFields = parentClass.getDeclaredFields();
		while (parentClass != null && parentClass.isAnnotationPresent(Entity.class)) {
			for (Field field : parentFields) {
				if (!field.isAnnotationPresent(Column.class) || field.getName().equals("id"))
					continue;
				fields.append(", ");
				params.append(", ");
				Column column = field.getAnnotation(Column.class);
				fields.append(column.name());
				params.append("?");
			}
			parentClass = parentClass.getSuperclass();
		}

		return new StringBuilder("INSERT INTO " + tableName + "(" + fields + ") VALUES(" + params + ")");
	}

	private StringBuilder createSqlUpdate() {
		String tableName = getTableName();
		if (tableName == null) {
			return null;
		}

		StringBuilder fieldAndParams = new StringBuilder("");
		for (Field field : tClass.getDeclaredFields()) {
			if (!field.isAnnotationPresent(Column.class))
				continue;
			if (fieldAndParams.length() > 1) {
				fieldAndParams.append(", ");
			}
			Column column = field.getAnnotation(Column.class);
			fieldAndParams.append(column.name()).append(" = ?");
		}

		// Scan parent's fields
		Class<?> parentClass = tClass.getSuperclass();
		Field[] parentFields = parentClass.getDeclaredFields();
		while (parentClass != null && parentClass.isAnnotationPresent(Entity.class)) {
			for (Field field : parentFields) {
				if (!field.isAnnotationPresent(Column.class) || field.getName().equals("id"))
					continue;
				Column column = field.getAnnotation(Column.class);
				fieldAndParams.append(", ").append(column.name()).append(" = ?");
			}
			parentClass = parentClass.getSuperclass();
		}
		return new StringBuilder("UPDATE " + tableName + " SET " + fieldAndParams + " WHERE id = ?");
	}

	protected String getTableName() {
		if (tClass.isAnnotationPresent(Entity.class) && tClass.isAnnotationPresent(Table.class)) {
			Table table = tClass.getAnnotation(Table.class);
			return table.name();
		}
		return null;
	}

	private static final String DB_URL = "jdbc:mysql://localhost/estate_ex3_javacore";
	private static final String USER = "root";
	private static final String PASS = "Ilovejob123@";

	private static Connection connection = null;

	public static Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				connection = DriverManager.getConnection(DB_URL, USER, PASS);
			}
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	protected void close(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if (conn != null)
				conn.close();
			if (stmt != null)
				stmt.close();
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
