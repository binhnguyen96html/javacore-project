package repository;

import java.util.Set;

import repository.enity.AssignmentBuildingEntity;

public interface AssignmentBuildingRepository extends JdbcRepository<AssignmentBuildingEntity> {
	void assignBuilding(Long buildingId, Set<Long> staffIds);
}
