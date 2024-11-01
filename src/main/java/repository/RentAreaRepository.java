package repository;

import java.util.List;
import java.util.Set;

import repository.entity.RentAreaEntity;


public interface RentAreaRepository extends JdbcRepository<RentAreaEntity> {
//	List<RentAreaEntity> getListRentAreasById(Long buildingId);
	List<RentAreaEntity> findByBuildingIdIn(Set<Long> buildingIds);
}
