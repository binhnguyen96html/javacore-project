package repository;

import java.util.List;

import model.request.BuildingSearchRequest;
import repository.enity.BuildingEntity;

public interface BuildingRepository extends JdbcRepository<BuildingEntity> {
	List<BuildingEntity> findBuilding(BuildingSearchRequest buildingSearchRequest);
	
}
