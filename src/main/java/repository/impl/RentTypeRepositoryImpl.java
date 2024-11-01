package repository.impl;

import java.util.List;

import constant.SystemConstant;
import repository.RentTypeRepository;
import repository.entity.RentTypeEntity;
import utils.QueryBuilderUtils;

public class RentTypeRepositoryImpl extends SimpleJdbcRepository<RentTypeEntity> implements RentTypeRepository {


	@Override
	public List<RentTypeEntity> getListOfRentTypes(Long buildingId) {
		
		StringBuilder query = new StringBuilder("SELECT renttype.name from renttype "
				+ QueryBuilderUtils.buildingQueryWithJoin("buildingrenttype", "renttypeid", "renttype", "id")
				+ QueryBuilderUtils.buildingQueryWithJoin("building", "id","buildingrenttype", "buildingid")
				+ SystemConstant.WHERE_ONE_EQUALS_ONE
				+ " AND building.id="
				+ buildingId
				);
		//System.out.println("getRentTypeNames, query: "+query);
		
		List<RentTypeEntity> rentTypeEntities =  findByCondition(query.toString());
		
		return rentTypeEntities;
	}
	
}


