package repository.impl;

import java.util.ArrayList;
import java.util.List;

import constant.SystemConstant;
import repository.RentTypeRepository;
import repository.entity.RentTypeEntity;
import utils.QueryBuilderUtils;

public class RentTypeRepositoryImpl extends SimpleJdbcRepository<RentTypeEntity> implements RentTypeRepository {

	@Override
	public List<String> getListOfRentTypes(Long buildingId) {
		
		List<String> rentTypes = new ArrayList<>();
		
		StringBuilder query = new StringBuilder("SELECT renttype.name from renttype "
				+ QueryBuilderUtils.buildingQueryWithJoin("buildingrenttype", "renttypeid", "renttype", "id")
				+ QueryBuilderUtils.buildingQueryWithJoin("building", "id","buildingrenttype", "buildingid")
				+ SystemConstant.ONE_EQUAL_ONE
				+ " AND building.id="
				+ buildingId
				);
		//System.out.println("getRentTypeNames, query: "+query);
		
		List<RentTypeEntity> rentTypeEntities =  findByCondition(query.toString());
		for(RentTypeEntity item: rentTypeEntities) {
			rentTypes.add(item.getName());
		}
		
		return rentTypes;
	}

}


