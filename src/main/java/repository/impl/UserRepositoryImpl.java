package repository.impl;

import java.util.List;

import repository.UserRepository;
import repository.entity.UserEntity;

public class UserRepositoryImpl extends SimpleJdbcRepository<UserEntity> implements UserRepository {

	@Override
	public List<UserEntity> findByRole(String role) {
		StringBuilder sql = new StringBuilder("");
				sql.append("SELECT user.fullname, user.phone FROM user "
				+ "INNER JOIN user_role ON user_role.userid=user.id "
				+ "INNER JOIN role ON role.id=user_role.roleid "
				+ "WHERE role.code LIKE '%"+role+"%'"
				);
				//System.out.println("getInfoByRole sql: "+sql);
		return findByCondition(sql.toString());
	}

	@Override
	public List<UserEntity> findAllStaff() {
		StringBuilder sql = new StringBuilder("");
		sql.append("SELECT * FROM user "
				+ " WHERE user.status=1 "
				+ " AND EXISTS (SELECT 1 FROM user_role "
				+ " INNER JOIN role ON user_role.roleid = role.id "
				+ " WHERE user_role.userid = user.id AND role.code='staff') "
				);
		
		//System.out.println("findAllStaff: " + sql);
		
		return findByCondition(sql.toString());
	}

	@Override
	public List<UserEntity> findStaffBy(Long buildingId) {
		StringBuilder sql = new StringBuilder("");

		sql.append("SELECT * FROM user "
				+ " WHERE user.status=1 "
				+ " AND EXISTS (SELECT 1 FROM user_role "
				+ " INNER JOIN role ON user_role.roleid = role.id "
				+ " WHERE user_role.userid = user.id AND role.code='staff') "
				+ " AND EXISTS (SELECT 1 FROM assignmentbuilding "
				+ " WHERE assignmentbuilding.staffid = user.id AND assignmentbuilding.buildingid = "
				+ buildingId
				+ ")"
				);
		
		//System.out.println("getAllStaffAssignmentByBuildingId: " + sql);
		
		return findByCondition(sql.toString());
	}

}
