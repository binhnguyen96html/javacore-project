package repository;

import java.util.List;

import repository.entity.RentAreaEntity;

public interface RentAreaRepository extends JdbcRepository<RentAreaEntity> {
	List<RentAreaEntity> getListRentAreasById(Long buildingId);
}
