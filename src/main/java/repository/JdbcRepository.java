package repository;

import java.util.List;

public interface JdbcRepository<T> {

	List<T> findByCondition(String sql);
	
	void insert(Object object);
	
	void delete(Long id, String field, Long id2, String field2);
}
