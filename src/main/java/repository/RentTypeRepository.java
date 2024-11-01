package repository;

import java.util.List;

import repository.entity.RentTypeEntity;

public interface RentTypeRepository extends JdbcRepository<RentTypeEntity> {
	List<RentTypeEntity> getListOfRentTypes(Long buildingId);
}
