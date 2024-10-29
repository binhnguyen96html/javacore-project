package repository.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import constant.SystemConstant;
import repository.BuildingRepository;
import repository.entity.BuildingEntity;
import utils.QueryBuilderUtils;

public class BuildingRepositoryImpl extends SimpleJdbcRepository<BuildingEntity> implements BuildingRepository {

	@Override
	public List<BuildingEntity> findBuilding(Map<String, Object> buildingSearchParams, List<String> buildingTypes) {

		StringBuilder query = new StringBuilder(
				"SELECT building.id, building.name, building.street, building.ward, building.districtid, "
						+ "building.structure, building.numberofbasement, building.floorarea, building.direction,"
						+ "building.level, building.rentprice, building.rentpricedescription,"
						+ "building.servicefee, building.carfee, building.motorbikefee,building.overtimefee,"
						+ "building.waterfee, building.electricityfee, building.deposit, building.payment,"
						+ "building.renttime, building.decorationtime,building.brokeragefee, building.note,"
						+ "building.linkofbuilding, building.map, building.image, "
						+ "building.managername, building.managerphone, "
						+ "building.createddate, building.modifieddate,building.createdby,building.modifiedby "
						+ " FROM building "
						+ QueryBuilderUtils.buildingQueryWithJoin("district", "id", "building", "districtid")
						+ QueryBuilderUtils.buildingQueryWithJoin("buildingrenttype", "buildingid", "building", "id")
						+ QueryBuilderUtils.buildingQueryWithJoin("renttype", "id", "buildingrenttype", "renttypeid")
						+ QueryBuilderUtils.buildingQueryWithJoin("rentarea", "buildingid", "building", "id")
						+ SystemConstant.ONE_EQUAL_ONE);

		// NAME
		query.append(
				QueryBuilderUtils.buildQueryUsingLike("building", "name", buildingSearchParams.get("name").toString()));
		// FLOORAREA
		query.append(QueryBuilderUtils.buildQueryUsingEqualOperator("building", "floorarea",
				buildingSearchParams.get("floorarea")));
		// DISTRICT
		query.append(QueryBuilderUtils.buildQueryUsingLike("district", "name",
				buildingSearchParams.get("district").toString()));
		// WARD
		query.append(
				QueryBuilderUtils.buildQueryUsingLike("building", "ward", buildingSearchParams.get("ward").toString()));
		// STREET
		query.append(QueryBuilderUtils.buildQueryUsingLike("building", "street",
				buildingSearchParams.get("street").toString()));
		// NUMBER OF BASEMENT
		query.append(QueryBuilderUtils.buildQueryUsingEqualOperator("building", "numberofbasement",
				buildingSearchParams.get("numberofbasement")));
		// DIRECTION
		query.append(QueryBuilderUtils.buildQueryUsingLike("building", "direction",
				buildingSearchParams.get("direction").toString()));
		// LEVEL
		query.append(QueryBuilderUtils.buildQueryUsingLike("building", "level",
				buildingSearchParams.get("level").toString()));
		// RENTAREA FROM - TO
		if (buildingSearchParams.get("arearentfrom") != null) {
			query.append(" AND rentarea.value >= " + buildingSearchParams.get("arearentfrom"));
		}
		if (buildingSearchParams.get("arearentto") != null) {
			query.append(" AND rentarea.value <= " + buildingSearchParams.get("arearentto"));
		}
		// RENTCOST FROM - TO
		if (buildingSearchParams.get("costRentfrom") != null) {
			query.append(" AND building.rentprice >= " + buildingSearchParams.get("costRentfrom"));
		}
		if (buildingSearchParams.get("costrentto") != null) {
			query.append(" AND building.rentprice <= " + buildingSearchParams.get("costrentto"));
		}
		// MANAGER NAME
		buildingSearchParams.get("managername").toString();
		query.append(QueryBuilderUtils.buildQueryUsingLike("building", "managername",
				buildingSearchParams.get("managername").toString()));
		// MANAGER PHONE
		query.append(QueryBuilderUtils.buildQueryUsingLike("building", "managerphone",
				buildingSearchParams.get("managerphone").toString()));
		// ASSIGNED STAFF ID
		if (buildingSearchParams.get("assignedstaffid") != null) {
			query.append(" AND building.id IN (" + "SELECT b.id FROM building b "
					+ QueryBuilderUtils.buildingQueryWithJoin("assignmentbuilding", "buildingid", "building", "id")
					+ QueryBuilderUtils.buildingQueryWithJoin("user", "id", "assignmentbuilding", "staffid")
					+ "WHERE user.id = " + buildingSearchParams.get("assignedstaffid") + ")");
		}

		// BUILDING TYPE
		if (buildingTypes.size() > 0) {
			// convert [nguyen-can,tang-tret,noi-that] =>'nguyen-can','tang-tret','noi-that'
			String buildingTypes_String = buildingTypes.stream().map(type -> "'" + type + "'")
					.collect(Collectors.joining(","));
			//System.out.println("buildingTypes_String: " + buildingTypes_String);
			query.append(" AND building.id IN (SELECT building.id from building "
					+ QueryBuilderUtils.buildingQueryWithJoin("buildingrenttype", "buildingid", "building", "id")
					+ QueryBuilderUtils.buildingQueryWithJoin("renttype", "id", "buildingrenttype", "renttypeid")
					+ "WHERE renttype.code IN (" + buildingTypes_String + ") )");
		}

		query.append(" GROUP BY building.id");
		//System.out.println("query: " + query);

		return findByCondition(query.toString());
	}

}
