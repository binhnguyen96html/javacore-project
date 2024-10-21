package repository;

import java.util.List;

import repository.enity.DistrictEntity;

public interface DistrictRepository extends JdbcRepository<DistrictEntity> {
	List<DistrictEntity> getDistrictCode(Integer districtId);
}
