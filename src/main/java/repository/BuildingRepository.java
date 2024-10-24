package repository;

import java.util.List;

import model.request.BuildingSearchRequestRepository;
import repository.entity.BuildingEntity;

public interface BuildingRepository extends JdbcRepository<BuildingEntity> {
	List<BuildingEntity> findBuilding(BuildingSearchRequestRepository buildingSearchRequestRepository);
}
