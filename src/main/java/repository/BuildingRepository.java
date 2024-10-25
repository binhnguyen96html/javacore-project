package repository;

import java.util.List;
import java.util.Map;

import repository.entity.BuildingEntity;

public interface BuildingRepository extends JdbcRepository<BuildingEntity> {
	List<BuildingEntity> findBuilding(Map<String, Object> buildingSearchParams, List<String> buildingTypes);
}
