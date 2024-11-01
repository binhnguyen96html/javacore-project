package repository;

import java.util.List;
import java.util.Set;

import repository.entity.DistrictEntity;


public interface DistrictRepository extends JdbcRepository<DistrictEntity> {
	List<DistrictEntity> findByIdIn(Set<Long> districtIds);
}
