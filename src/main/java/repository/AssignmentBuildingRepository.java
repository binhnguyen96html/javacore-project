package repository;

import java.util.List;

import repository.enity.AssignmentBuildingEntity;

public interface AssignmentBuildingRepository extends JdbcRepository<AssignmentBuildingEntity> {
	List<AssignmentBuildingEntity> getAssignmentBuildingListById(Long buildingId);
	void assignBuilding(Long buildingId, List<Long> staffIdsToDelete,  List<AssignmentBuildingEntity> staffsToInsert);
}
