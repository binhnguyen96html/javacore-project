package repository.impl;

import java.util.ArrayList;
import java.util.List;

import constant.SystemConstant;
import repository.RentAreaRepository;
import repository.entity.RentAreaEntity;
import utils.QueryBuilderUtils;

public class RentAreaRepositoryImpl extends SimpleJdbcRepository<RentAreaEntity> implements RentAreaRepository {

	@Override
	public List<String> getListRentAreas(Long buildingId) {
		
		List<String> rentAreas = new ArrayList<>();
		
		StringBuilder query = new StringBuilder("SELECT rentarea.value from rentarea "
				+ QueryBuilderUtils.buildingQueryWithJoin("building", "id", "rentarea", "buildingid")
				+ SystemConstant.ONE_EQUAL_ONE
				+ " AND building.id="
				+ buildingId
				);
		//System.out.println("getRentTypeNames, query: "+query);
		
		List<RentAreaEntity> rentAreasEntity = findByCondition(query.toString());
		for(RentAreaEntity item: rentAreasEntity) {
			rentAreas.add(item.getValue().toString());
		}
		
		return rentAreas;
	}

}
