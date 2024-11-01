package repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import constant.SystemConstant;
import enums.SpecialSearchParamsEnum;
import repository.BuildingRepository;
import repository.entity.BuildingEntity;
import utils.QueryBuilderUtils;

public class BuildingRepositoryImpl extends SimpleJdbcRepository<BuildingEntity> implements BuildingRepository{

	@Override
	public List<BuildingEntity> findBuildingBy(Map<String, Object> params, List<String> buildingTypes) {

		StringBuilder finalQuery = new StringBuilder();
		StringBuilder joinQuery = new StringBuilder();
		StringBuilder whereQuery = new StringBuilder();

		finalQuery.append(
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
						);

		buildSpecialQuery(params, buildingTypes, whereQuery, joinQuery);
		buildNormalQuery(params, whereQuery);

		finalQuery.append(joinQuery)
				.append(SystemConstant.WHERE_ONE_EQUALS_ONE)
				.append(whereQuery)
				.append("\nGROUP BY building.id");

		//System.out.println("finalQuery: " + finalQuery);
		
		return super.findByCondition(finalQuery.toString());
	}

	// BUILD NORMAL QUERY
	private void buildNormalQuery(Map<String, Object> params, StringBuilder whereQuery) {
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			if (!getSpecialSearchParams().contains(key)) {
				String column = SystemConstant.BUILDING_ALIAS + key;
				Object value = entry.getValue();

				if (entry.getValue() instanceof String) {
					whereQuery.append(QueryBuilderUtils.buildQueryWithLike(column, value.toString()));
				} else if (entry.getValue() instanceof Integer) {
					whereQuery.append(QueryBuilderUtils.buildQueryWithOperator(column, value, SystemConstant.EQUAL_OPERATOR));
				}
			}
		}
	}

	// GET SPECIAL SERACH PARAMS
	private List<String> getSpecialSearchParams() {
		List<String> params = new ArrayList<>();

		for (SpecialSearchParamsEnum item : SpecialSearchParamsEnum.values()) {
			params.add(item.getValue().toLowerCase());
		}

		return params;
	}

	// BUILD SPECIAL QUERY
	private void buildSpecialQuery(Map<String, Object> params, List<String> types, StringBuilder whereQuery,
								   StringBuilder joinQuery) {
		// rent area
		Integer rentAreaFrom = (Integer) params.getOrDefault(SystemConstant.RENT_AREA_FROM, null);
		Integer rentAreaTo = (Integer) params.getOrDefault(SystemConstant.RENT_AREA_TO, null);

		if (null != rentAreaFrom || null != rentAreaTo) {
			joinQuery.append("\nJOIN rentarea ON rentarea.buildingid = building.id");

			whereQuery.append(QueryBuilderUtils.buildQueryWithBetween("rentarea.value", rentAreaFrom, rentAreaTo));
		}

		// Cost rent
		Integer rentPriceFrom = (Integer) params.getOrDefault(SystemConstant.RENT_PRICE_FROM, null);
		Integer rentPriceTo = (Integer) params.getOrDefault(SystemConstant.RENT_PRICE_TO, null);

		if (null != rentPriceFrom || null != rentPriceTo) {
			whereQuery.append(QueryBuilderUtils.buildQueryWithBetween("building.rentprice", rentPriceFrom, rentPriceTo));
		}

		// district
		String districtCode = (String) params.getOrDefault(SystemConstant.DISTRICT_CODE, null);

		if (null != districtCode) {
			joinQuery.append("\nJOIN district ON district.id = building.districtid");
			whereQuery.append(QueryBuilderUtils.buildQueryWithOperator("district.code", districtCode, SystemConstant.EQUAL_OPERATOR));
		}

		// staff
		Long staffId = (Long) params.getOrDefault(SystemConstant.STAFF_ID, null);

		if (null != staffId) {
			joinQuery.append("\nJOIN assignmentbuilding ON assignmentbuilding.buildingid = building.id");
			whereQuery.append(QueryBuilderUtils.buildQueryWithOperator("assignmentbuilding.staffid", staffId, SystemConstant.EQUAL_OPERATOR));
		}

		// building types
		if (null != types && !types.isEmpty()) {
			joinQuery.append("\nJOIN buildingrenttype ON buildingrenttype.buildingid = building.id")
					.append("\nJOIN renttype ON renttype.id = buildingrenttype.renttypeid");

			whereQuery.append(QueryBuilderUtils.buildQueryWithIn("renttype.code", types));
		}
	}
}
