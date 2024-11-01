package utils;

import java.util.Map;

import model.request.BuildingSearchRequest;
import model.request.BuildingSearchRequestRepository;

public class ConverterUtils {
	
	
	public static BuildingSearchRequest convertParamsToBuildingSearchRequest(Map<String, String> params) {
		BuildingSearchRequest bsr = new BuildingSearchRequest();
		bsr.setName(getValueOrNull(params.get("name")));
		bsr.setFloorArea(getParseInteger(params.get("floorarea")));
		bsr.setDistrictCode(getValueOrNull(params.get("districtcode")));
		bsr.setWard(getValueOrNull(params.get("ward")));
		bsr.setStreet(getValueOrNull(params.get("street")));
		bsr.setNumberOfBasement(getParseInteger(params.get("numberofbasement")));
		bsr.setDirection(getValueOrNull(params.get("direction")));
		bsr.setLevel(getValueOrNull(params.get("level")));
		bsr.setAreaRentFrom(getParseInteger(params.get("arearentfrom")));
		bsr.setAreaRentTo(getParseInteger(params.get("arearentto")));
		bsr.setCostRentFrom(getParseInteger(params.get("costrentfrom")));
		bsr.setCostRentTo(getParseInteger(params.get("costrentto")));
		bsr.setManagerName(getValueOrNull(params.get("managername")));
		bsr.setManagerPhoneNumber(getValueOrNull(params.get("managerphonenumber")));
		bsr.setAssignedStaffId(getParseLong(params.get("assignedstaffid")));
		bsr.setRentTypes(getValueOrNull(params.get("buildingtypes")));
		
		return bsr;
	}
	
	private static String getValueOrNull(String value) {
		return StringUtils.isNullOrEmpty(value) ? null : value;
	}
	private static Integer getParseInteger(String value) {
		return StringUtils.isNullOrEmpty(value) ? null : Integer.parseInt(value);
	}
	private static Long getParseLong(String value) {
		return StringUtils.isNullOrEmpty(value) ? null : Long.parseLong(value);
	}
	
	
	public static BuildingSearchRequestRepository covertBsrToBsrr(BuildingSearchRequest bsr) {
		BuildingSearchRequestRepository bsrr = new BuildingSearchRequestRepository();
		
		bsrr.setName(bsr.getName());
		bsrr.setFloorArea(bsr.getFloorArea());
		bsrr.setDistrictCode(bsr.getDistrictCode());
		bsrr.setWard(bsr.getWard());
		bsrr.setStreet(bsr.getStreet());
		bsrr.setNumberOfBasement(bsr.getNumberOfBasement());
		bsrr.setDirection(bsr.getDirection());
		bsrr.setLevel(bsr.getLevel());
		bsrr.setAreaRentFrom(bsr.getAreaRentFrom());
		bsrr.setAreaRentTo(bsr.getAreaRentTo());
		bsrr.setCostRentFrom(bsr.getCostRentFrom());
		bsrr.setCostRentTo(bsr.getCostRentTo());
		bsrr.setManagerName(bsr.getManagerName());
		bsrr.setManagerPhoneNumber(bsr.getManagerPhoneNumber());
		bsrr.setAssignedStaffId(bsr.getAssignedStaffId());
		bsrr.setRentTypes(bsr.getRentTypes());
		
		return bsrr;		
	}
}
