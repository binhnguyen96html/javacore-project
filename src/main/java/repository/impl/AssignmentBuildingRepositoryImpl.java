package repository.impl;

import java.sql.Connection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import repository.AssignmentBuildingRepository;
import repository.enity.AssignmentBuildingEntity;
import utils.ConnectionUtils;

public class AssignmentBuildingRepositoryImpl extends SimpleJdbcRepository<AssignmentBuildingEntity>
		implements AssignmentBuildingRepository {

	@Override
	public void assignBuilding(Long buildingId, Set<Long> staffIds) {
		//Connection conn = null;
		
		try {
			// get staffId and buildingId from assignmentbuilding
			String sql = "SELECT * FROM assignmentbuilding a WHERE a.buildingid=" + buildingId;
			List<AssignmentBuildingEntity> ass = findByCondition(sql.toString());

			
			Set<Long> curStaffIds = new HashSet<>();
			for (AssignmentBuildingEntity item : ass) {
				curStaffIds.add(item.getStaffId());
			}
	
				Connection conn = null;
				try {
					conn = ConnectionUtils.getConnection();
					conn.setAutoCommit(false);
					
					// insert new when new staffId not in current staffIds
					for (Long item : staffIds) {
						if (!curStaffIds.contains(item)) {
							AssignmentBuildingEntity assB = new AssignmentBuildingEntity();
							assB.setBuildingId(buildingId);
							assB.setStaffId(item);
							insert(assB, conn);
						}
					}
					
					// delete rows when current staffId not in new staffIds
					for (Long item : curStaffIds) {
						if (!staffIds.contains(item)) {
							delete(buildingId, "buildingid",item, "staffid", conn);
						}
					}
					
					// when committing  successfully 
					conn.commit();
					System.out.println("Transaction committed successfully.");
				} catch ( Exception e) {
					e.printStackTrace();
					if(conn != null) {
						try {
							//Rollback when error occur
							conn.rollback();
							System.out.println("Transaction rolled back.");
						} catch (Exception e2) {
							System.out.println("assignBuilding1: " + e2);
						}
					}
				}finally {
					if(conn != null) {
						try {
							conn.close();
						} catch (Exception e2) {
							System.out.println("assignBuilding2:"+e2);
							e2.printStackTrace();
						}
					}
				}
			
		} catch (Exception e) {
			throw new RuntimeException("Failed to assign building: " + e.getMessage(), e);
		}
	}//assignBuilding

}
