package repository;

import java.util.List;

import repository.entity.UserEntity;

public interface UserRepository extends JdbcRepository<UserEntity> {
	
	List<UserEntity> findByRole(String role);
	
	List<UserEntity> findAllStaff();
	
	List<UserEntity> findStaffBy(Long buildingId);
}
