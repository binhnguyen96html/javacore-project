package repository.impl;

import java.util.List;

import constant.SystemConstant;
import repository.RentAreaRepository;
import repository.enity.RentAreaEntity;
import utils.QueryBuilderUtils;

public class RentAreaRepositoryImpl extends SimpleJdbcRepository<RentAreaEntity> implements RentAreaRepository {

	@Override
	public List<RentAreaEntity> getListRentAreas(Long buildingId) {
		
		StringBuilder query = new StringBuilder("SELECT rentarea.value, rentarea.buildingid from rentarea "
				+ QueryBuilderUtils.buildingQueryWithJoin("building", "id", "rentarea", "buildingid")
				+ SystemConstant.ONE_EQUAL_ONE
				+ " AND building.id="
				+ buildingId
				);
		//System.out.println("getRentTypeNames, query: "+query);
		
		return findByCondition(query.toString());
	}

}
