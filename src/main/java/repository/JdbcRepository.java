package repository;

import java.sql.Connection;
import java.util.List;

public interface JdbcRepository<T> {

	List<T> findByCondition(String sql);
	
	void insert(Object object, Connection conn);
	
	void delete(Long id, String field, Long id2, String field2, Connection conn);
	
	T findById(Long id);
	
	void insertMany(List<T> objects, Connection conn);
	
	void deleteMany(Long id, String field, List<Long> id2, String field2, Connection conn);
}
