package repository.impl;

import java.util.List;

import repository.UserRepository;
import repository.entity.UserEntity;

public class UserRepositoryImpl extends SimpleJdbcRepository<UserEntity> implements UserRepository {

	@Override
	public List<UserEntity> getInfoByRole(String role) {
		StringBuilder sql = new StringBuilder("");
				sql.append("SELECT user.fullname, user.phone FROM USER "
				+ "INNER JOIN user_role ON user_role.userid=user.id "
				+ "INNER JOIN role ON role.id=user_role.roleid "
				+ "WHERE role.code LIKE '%"+role+"%'"
				);
				//System.out.println("getInfoByRole sql: "+sql);
		return findByCondition(sql.toString());
	}

	@Override
	public List<UserEntity> getAllWorkingStaff() {
		StringBuilder sql = new StringBuilder("");
		sql.append("SELECT * FROM USER WHERE status=1");
		
		return findByCondition(sql.toString());
	}

	@Override
	public List<UserEntity> getAllStaffAssignmentByBuildingId(Long buildingId) {
		StringBuilder sql = new StringBuilder("");
		sql.append("SELECT user.id, user.fullname FROM user "
				+ "INNER JOIN assignmentbuilding ON assignmentbuilding.staffid = user.id "
				+ "INNER JOIN building ON building.id = assignmentbuilding.buildingid "
				+ "WHERE user.status=1 AND building.id="
				+ buildingId
				);
		
		//System.out.println("getAllStaffAssignmentByBuildingId: " + sql);
		
		return findByCondition(sql.toString());
	}

}
