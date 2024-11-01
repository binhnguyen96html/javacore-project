package repository;

import java.util.List;

import repository.entity.AssignmentBuildingEntity;

public interface AssignmentBuildingRepository extends JdbcRepository<AssignmentBuildingEntity> {
	
	 List<AssignmentBuildingEntity> findByBuildingId(Long buildingId);
	 AssignmentBuildingEntity findByStaffIdAndBuildingId(Long staffId, Long buildingId);
	 void assign(List<Long> deletedStaffIds, List<Long> addedStaffIds, Long buildingId);
	 void deleteByStaffIdIn(Long buildingId, List<Long> staffIds);
	 
	 
}
