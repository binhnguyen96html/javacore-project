package repository.impl;

import java.sql.Connection;
import java.util.List;

import repository.AssignmentBuildingRepository;
import repository.entity.AssignmentBuildingEntity;
import utils.ConnectionUtils;

public class AssignmentBuildingRepositoryImpl extends SimpleJdbcRepository<AssignmentBuildingEntity>
		implements AssignmentBuildingRepository {

	@Override
	public List<AssignmentBuildingEntity> getAssignmentBuildingListById(Long buildingId) {
		// get staffId and buildingId from assignmentbuilding
		String sql = "SELECT * FROM assignmentbuilding a WHERE a.buildingid=" + buildingId;
		return findByCondition(sql.toString());
	}
	
	
	@Override
	public void assignBuilding(Long buildingId, List<Long> staffIdsToDelete,  List<AssignmentBuildingEntity> staffsToInsert) {
		Connection conn = null;
		try {
			try {
				conn = ConnectionUtils.getConnection();
				conn.setAutoCommit(false);

				// insert new when new staffId not in current staffIds
				if(staffsToInsert.size() > 0) {
					insertMany(staffsToInsert, conn);
				}
				
				// delete rows when current staffId not in new staffIds
				if(staffIdsToDelete.size() > 0) {
					deleteMany(buildingId, "buildingid", staffIdsToDelete, "staffid", conn);
				}

				// when committing successfully
				conn.commit();
				System.out.println("Transaction committed successfully.");
			} catch (Exception e) {
				e.printStackTrace();
				if (conn != null) {
					try {
						// Rollback when error occur
						conn.rollback();
						System.out.println("Transaction rolled back.");
					} catch (Exception e2) {
						System.out.println("assignBuilding1: " + e2);
					}
				}
			} finally {
				if (conn != null) {
					try {
						conn.close();
					} catch (Exception e2) {
						System.out.println("assignBuilding2:" + e2);
						e2.printStackTrace();
					}
				}
			}

		} catch (Exception e) {
			throw new RuntimeException("Failed to assign building: " + e.getMessage(), e);
		}
	}// assignBuilding
	
}
