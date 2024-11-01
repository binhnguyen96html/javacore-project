package repository;

import java.util.List;

import repository.entity.AssignmentBuildingEntity;

public interface JdbcRepository<T> {
	
	List<T> findAll();

	T findById(Long id);

	List<T> findByCondition(String sql);

	void delete(Long id);

	void update(Object object);

	Long insert(Object object);

	void saveAll(List<T> objects);

}
