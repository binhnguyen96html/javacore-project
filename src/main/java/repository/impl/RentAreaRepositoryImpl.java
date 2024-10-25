package repository.impl;

import java.util.List;

import constant.SystemConstant;
import repository.RentAreaRepository;
import repository.entity.RentAreaEntity;
import utils.QueryBuilderUtils;

public class RentAreaRepositoryImpl extends SimpleJdbcRepository<RentAreaEntity> implements RentAreaRepository {

	@Override
	public List<RentAreaEntity> getListRentAreasById(Long buildingId) {
		
		StringBuilder query = new StringBuilder("SELECT rentarea.value from rentarea "
				+ QueryBuilderUtils.buildingQueryWithJoin("building", "id", "rentarea", "buildingid")
				+ SystemConstant.ONE_EQUAL_ONE
				+ " AND building.id="
				+ buildingId
				);
		//System.out.println("getListRentAreas, query: "+query);
		
		List<RentAreaEntity> rentAreasEntities = findByCondition(query.toString());
	
		return rentAreasEntities;
	}

}
