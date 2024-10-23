package repository;

import java.util.List;

import repository.enity.RentAreaEntity;

public interface RentAreaRepository extends JdbcRepository<RentAreaEntity> {
	List<RentAreaEntity> getListRentAreas(Long buildingId);
}
