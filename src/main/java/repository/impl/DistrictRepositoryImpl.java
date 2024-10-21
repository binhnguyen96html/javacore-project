package repository.impl;

import java.util.List;

import constant.SystemConstant;
import repository.DistrictRepository;
import repository.enity.DistrictEntity;

public class DistrictRepositoryImpl extends SimpleJdbcRepository<DistrictEntity> implements DistrictRepository {
	@Override
	public List<DistrictEntity> getDistrictCode(Integer districtId) {
		StringBuilder query = new StringBuilder("SELECT district.code from district " + SystemConstant.ONE_EQUAL_ONE);
		query.append(" AND id="+districtId);
		return findByCondition(query.toString());
	}
}

