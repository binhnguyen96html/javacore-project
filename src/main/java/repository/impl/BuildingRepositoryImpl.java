package repository.impl;

import java.util.List;

import constant.SystemConstant;
import model.request.BuildingSearchRequest;
import repository.BuildingRepository;
import repository.enity.BuildingEntity;
import utils.BuildQueryUsing;
import utils.StringUtils;

public class BuildingRepositoryImpl extends SimpleJdbcRepository<BuildingEntity> implements BuildingRepository {

	@Override
	public List<BuildingEntity> findBuilding(BuildingSearchRequest buildingSearchRequest) {

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
						+ BuildQueryUsing.join("district", "id", "building", "districtid")
						+ BuildQueryUsing.join("buildingrenttype", "buildingid", "building", "id")
						+ BuildQueryUsing.join("renttype", "id", "buildingrenttype", "renttypeid")
						+ BuildQueryUsing.join("rentarea", "buildingid", "building", "id")
						+ SystemConstant.ONE_EQUAL_ONE);

		// NAME
		query.append(BuildQueryUsing.withoutJoin("building", "name", buildingSearchRequest.getName() ));
		// FLOORAREA
		query.append(BuildQueryUsing.withoutJoin("building", "floorarea", buildingSearchRequest.getFloorArea()));
		// DISTRICT
		query.append(BuildQueryUsing.withoutJoin("district", "code", buildingSearchRequest.getDistrictCode()));
		// WARD
		query.append(BuildQueryUsing.withoutJoin("building", "ward", buildingSearchRequest.getWard()));
		// STREET
		query.append(BuildQueryUsing.withoutJoin("building", "street", buildingSearchRequest.getStreet()));
		// NUMBER OF BASEMENT
		query.append(BuildQueryUsing.withoutJoin("building", "numberOfBasement", buildingSearchRequest.getNumberOfBasement()));
		// DIRECTION
		query.append(BuildQueryUsing.withoutJoin("building", "direction", buildingSearchRequest.getDirection()));
		// LEVEL
		query.append(BuildQueryUsing.withoutJoin("building", "level", buildingSearchRequest.getLevel()));
		// RENTAREA FROM - TO
		if (buildingSearchRequest.getAreaRentFrom() != null) {
			query.append(" AND rentarea.value >= " + buildingSearchRequest.getAreaRentFrom());
		}
		if (buildingSearchRequest.getAreaRentTo() != null) {
			query.append(" AND rentarea.value <= " + buildingSearchRequest.getAreaRentTo());
		}
		// RENTCOST FROM - TO
		if (buildingSearchRequest.getCostRentFrom() != null) {
			query.append(" AND building.rentprice >= " + buildingSearchRequest.getCostRentFrom());
		}
		if (buildingSearchRequest.getCostRentTo() != null) {
			query.append(" AND building.rentprice <= " + buildingSearchRequest.getCostRentTo());
		}
		// MANAGER NAME
		if (!StringUtils.isNullOrEmpty(buildingSearchRequest.getManagerName())) {
			query.append(" AND EXISTS (" + "SELECT 1 " + "FROM user "
					+ BuildQueryUsing.join("user_role", "userid", "user", "id")
					+ BuildQueryUsing.join("role", "id", "user_role", "roleid")
					+ "WHERE user.fullname LIKE '%" + buildingSearchRequest.getManagerName() + "%' " + "AND role.code = 'manager'" + ")");
		}
		// MANAGER PHONE
		if (!StringUtils.isNullOrEmpty(buildingSearchRequest.getManagerPhoneNumber())) {
			query.append(" AND EXISTS (" + "SELECT 1 " + "FROM user "
					+ BuildQueryUsing.join("user_role", "userid", "user", "id")
					+ BuildQueryUsing.join("role", "id", "user_role", "roleid")
					+ " WHERE user.phone LIKE '%" + buildingSearchRequest.getManagerPhoneNumber() + "%' " + "AND role.code = 'manager'" + ")");
		}
		// ASSIGNED STAFF ID
		if (buildingSearchRequest.getAssignedStaffId() != null) {
			query.append(" AND building.id IN (" + "SELECT b.id FROM building b "
					+ BuildQueryUsing.join("assignmentbuilding", "buildingid", "building", "id")
					+ BuildQueryUsing.join("user", "id", "assignmentbuilding", "staffid")
					+ "WHERE user.id = " + buildingSearchRequest.getAssignedStaffId() + ")");
		}

		// BUILDING TYPE
		if (buildingSearchRequest.getBuildingtypes().size() > 0) {
			// convert List to string
			String buildingTypes_String = "";
			for (String item : buildingSearchRequest.getBuildingtypes()) {
				buildingTypes_String += "'" + item + "'";
				if (buildingSearchRequest.getBuildingtypes().indexOf(item) != buildingSearchRequest.getBuildingtypes().size() - 1) {
					buildingTypes_String += ",";
				}
			}
			query.append(" AND building.id IN (SELECT building.id from building "
					+ BuildQueryUsing.join("buildingrenttype", "buildingid", "building", "id")
					+ BuildQueryUsing.join("renttype", "id", "buildingrenttype", "renttypeid")
					+ "WHERE renttype.code IN (" + buildingTypes_String + ") )");
		}

		query.append(" GROUP BY building.id");
		//System.out.println("query: " + query);

		return findByCondition(query.toString());
	}

}
