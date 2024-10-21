package repository.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import repository.AssignmentBuildingRepository;
import repository.enity.AssignmentBuildingEntity;

public class AssignmentBuildingRepositoryImpl extends SimpleJdbcRepository<AssignmentBuildingEntity>
		implements AssignmentBuildingRepository {

	@Override
	public void assignBuilding(Long buildingId, Set<Long> staffIds) {

		try {
			// get staffId and buildingId from assignmentbuilding
			String sql = "SELECT * FROM assignmentbuilding a WHERE a.buildingid=" + buildingId;
			List<AssignmentBuildingEntity> ass = findByCondition(sql.toString());

			Set<Long> curStaffIds = new HashSet<>();
			for (AssignmentBuildingEntity item : ass) {
				curStaffIds.add(item.getStaffId());
			}
			
			// insert new when new staffId not in current staffIds
			for (Long item : staffIds) {
				if (!curStaffIds.contains(item)) {
					AssignmentBuildingEntity assB = new AssignmentBuildingEntity();
					assB.setBuildingId(buildingId);
					assB.setStaffId(item);
					insert(assB);
				}
			}
			
			// delete rows when current staffId not in new staffIds
			for (Long item : curStaffIds) {
				if (!staffIds.contains(item)) {
					//System.out.println("staffId: "+item);
					delete(buildingId, "buildingid",item, "staffid");
				}
			}
			
			System.out.println("Successfully assign the building to staffs");

		} catch (Exception e) {
			throw new RuntimeException("Failed to assign building: " + e.getMessage(), e);
		}
	}//assignBuilding

}
