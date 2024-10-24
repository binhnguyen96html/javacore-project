package repository;

import java.util.List;

import repository.entity.UserEntity;

public interface UserRepository extends JdbcRepository<UserEntity> {
	
	List<UserEntity> getInfoByRole(String role);
}
