package repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import repository.RentAreaRepository;
import repository.entity.RentAreaEntity;
import org.apache.commons.lang3.StringUtils;


public class RentAreaRepositoryImpl extends SimpleJdbcRepository<RentAreaEntity> implements RentAreaRepository {
//
//	@Override
//	public List<RentAreaEntity> getListRentAreasById(Long buildingId) {
//
//		StringBuilder query = new StringBuilder("SELECT rentarea.value from rentarea "
//				+ QueryBuilderUtils.buildingQueryWithJoin("building", "id", "rentarea", "buildingid")
//				+ SystemConstant.WHERE_ONE_EQUALS_ONE + " AND building.id=" + buildingId);
//
//		// System.out.println("getListRentAreas, query: "+query);
//
//		List<RentAreaEntity> rentAreasEntities = findByCondition(query.toString());
//
//		return rentAreasEntities;
//	}

	@Override
	public List<RentAreaEntity> findByBuildingIdIn(Set<Long> buildingIds) {
		if (buildingIds.isEmpty()) {
			return new ArrayList<>();
		}

		 StringBuilder query = new StringBuilder("");

	        query.append("select * from rentarea where buildingid IN (").append(StringUtils.join(buildingIds, ", ")).append(")");

		return super.findByCondition(query.toString());
	}

}
