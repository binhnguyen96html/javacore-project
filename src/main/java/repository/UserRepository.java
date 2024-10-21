package repository;

import java.util.List;

import repository.enity.UserEntity;

public interface UserRepository extends JdbcRepository<UserEntity> {
	
	List<UserEntity> getInfoByRole(String role);
}
