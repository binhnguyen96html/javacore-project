package repository.impl;

import java.util.Arrays;
import java.util.List;

import constant.SystemConstant;
import model.request.BuildingSearchRequestRepository;
import repository.BuildingRepository;
import repository.entity.BuildingEntity;
import utils.QueryBuilderUtils;
import utils.StringUtils;

public class BuildingRepositoryImpl extends SimpleJdbcRepository<BuildingEntity> implements BuildingRepository {

	@Override
	public List<BuildingEntity> findBuilding(BuildingSearchRequestRepository bsrr) {

		StringBuilder query = new StringBuilder(
				"SELECT building.id, building.name, building.street, building.ward, building.districtid, "
						+ "building.structure, building.numberofbasement, building.floorarea, building.direction,"
						+ "building.level, building.level, building.rentprice, building.rentpricedescription,"
						+ "building.servicefee, building.carfee, building.motorbikefee,building.overtimefee,"
						+ "building.waterfee, building.electricityfee, building.deposit, building.payment,"
						+ "building.renttime, building.decorationtime,building.brokeragefee, building.note,"
						+ "building.linkofbuilding, building.map, building.image, "
						+ "building.createddate, building.modifieddate,building.createdby,building.modifiedby "
						+ " FROM building "
						+ QueryBuilderUtils.buildingQueryWithJoin("district", "id", "building", "districtid")
						+ QueryBuilderUtils.buildingQueryWithJoin("buildingrenttype", "buildingid", "building", "id")
						+ QueryBuilderUtils.buildingQueryWithJoin("renttype", "id", "buildingrenttype", "renttypeid")
						+ QueryBuilderUtils.buildingQueryWithJoin("rentarea", "buildingid", "building", "id")
						+ SystemConstant.ONE_EQUAL_ONE);

		// NAME
		query.append(QueryBuilderUtils.buildQueryUsingLike("building", "name", bsrr.getName() ));
		// FLOORAREA
		query.append(QueryBuilderUtils.buildQueryUsingEqualOperator("building", "floorarea", bsrr.getFloorArea()));
		// DISTRICT
		query.append(QueryBuilderUtils.buildQueryUsingLike("district", "code", bsrr.getDistrictCode()));
		// WARD
		query.append(QueryBuilderUtils.buildQueryUsingLike("building", "ward", bsrr.getWard()));
		// STREET
		query.append(QueryBuilderUtils.buildQueryUsingLike("building", "street", bsrr.getStreet()));
		// NUMBER OF BASEMENT
		query.append(QueryBuilderUtils.buildQueryUsingEqualOperator("building", "numberOfBasement", bsrr.getNumberOfBasement()));
		// DIRECTION
		query.append(QueryBuilderUtils.buildQueryUsingLike("building", "direction", bsrr.getDirection()));
		// LEVEL
		query.append(QueryBuilderUtils.buildQueryUsingLike("building", "level", bsrr.getLevel()));
		// RENTAREA FROM - TO
		if (bsrr.getAreaRentFrom() != null) {
			query.append(" AND rentarea.value >= " + bsrr.getAreaRentFrom());
		}
		if (bsrr.getAreaRentTo() != null) {
			query.append(" AND rentarea.value <= " + bsrr.getAreaRentTo());
		}
		// RENTCOST FROM - TO
		if (bsrr.getCostRentFrom() != null) {
			query.append(" AND building.rentprice >= " + bsrr.getCostRentFrom());
		}
		if (bsrr.getCostRentTo() != null) {
			query.append(" AND building.rentprice <= " + bsrr.getCostRentTo());
		}
		// MANAGER NAME
		if (!StringUtils.isNullOrEmpty(bsrr.getManagerName())) {
			query.append(" AND EXISTS (" + "SELECT 1 " + "FROM user "
					+ QueryBuilderUtils.buildingQueryWithJoin("user_role", "userid", "user", "id")
					+ QueryBuilderUtils.buildingQueryWithJoin("role", "id", "user_role", "roleid")
					+ "WHERE user.fullname LIKE '%" + bsrr.getManagerName() + "%' " + "AND role.code = 'manager'" + ")");
		}
		// MANAGER PHONE
		if (!StringUtils.isNullOrEmpty(bsrr.getManagerPhoneNumber())) {
			query.append(" AND EXISTS (" + "SELECT 1 " + "FROM user "
					+ QueryBuilderUtils.buildingQueryWithJoin("user_role", "userid", "user", "id")
					+ QueryBuilderUtils.buildingQueryWithJoin("role", "id", "user_role", "roleid")
					+ " WHERE user.phone LIKE '%" + bsrr.getManagerPhoneNumber() + "%' " + "AND role.code = 'manager'" + ")");
		}
		// ASSIGNED STAFF ID
		if (bsrr.getAssignedStaffId() != null) {
			query.append(" AND building.id IN (" + "SELECT b.id FROM building b "
					+ QueryBuilderUtils.buildingQueryWithJoin("assignmentbuilding", "buildingid", "building", "id")
					+ QueryBuilderUtils.buildingQueryWithJoin("user", "id", "assignmentbuilding", "staffid")
					+ "WHERE user.id = " + bsrr.getAssignedStaffId() + ")");
		}

		// BUILDING TYPE
		if (!StringUtils.isNullOrEmpty(bsrr.getRentTypes())) {
			// convert nguyen-can,tang-tret,noi-that => 'nguyen-can','tang-tret','noi-that'
			String[] rentTypes = bsrr.getRentTypes().split(",");
			String buildingTypes_String = String.join(",", Arrays.stream(rentTypes).map(type -> "'"+type+"'").toArray(String[]::new));
			
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
