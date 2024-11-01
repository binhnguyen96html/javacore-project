package repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import repository.DistrictRepository;
import repository.entity.DistrictEntity;

public class DistrictRepositoryImpl extends SimpleJdbcRepository<DistrictEntity> implements DistrictRepository {
	 
	 @Override
	    public List<DistrictEntity> findByIdIn(Set<Long> districtIds) {
	        if (districtIds.isEmpty()) {
	            return new ArrayList<>();
	        }

	        StringBuilder query = new StringBuilder();

	        query.append("select * from district where id IN (").append(StringUtils.join(districtIds, ",")).append(")");

	        return super.findByCondition(query.toString());
	    }
}

