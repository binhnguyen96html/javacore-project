package repository;

import java.util.List;

import repository.enity.RentTypeEntity;

public interface RentTypeRepository extends JdbcRepository<RentTypeEntity> {
	List<RentTypeEntity>  getListOfRentTypes(Long buildingId);
}
