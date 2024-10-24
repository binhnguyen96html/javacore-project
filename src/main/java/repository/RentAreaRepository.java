package repository;

import java.util.List;

import repository.entity.RentAreaEntity;

public interface RentAreaRepository extends JdbcRepository<RentAreaEntity> {
	List<String> getListRentAreas(Long buildingId);
}
