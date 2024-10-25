package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import controller.BuildingController;
import model.response.BuildingResponse;
import utils.StringUtils;

public class BuildingView {
	public static void main(String[] args) {
		BuildingController buildingController = new BuildingController();

		// Input from client
		Map<String, String> params = new HashMap<>();
		params.put("name", "");
		params.put("floorarea", "");
		params.put("districtcode","");
		params.put("ward","");
		params.put("street","");
		params.put("numberofbasement","");
		params.put("direction","");
		params.put("level","");
		params.put("arearentfrom","");
		params.put("arearentto","");
		params.put("costrentfrom","");
		params.put("costrentto","");
		params.put("managername","");
		params.put("managerphone","");
		params.put("assignedstaffid","");
		
		List<String> buildingTypes = new ArrayList<>(Arrays.asList("noi-that","nguyen-can"));
		
		// convert Input from client to right input passed in controller, service, repo
		Map<String, Object> buildingSearchParams = new HashMap<>();
		 buildingSearchParams.put("name", params.get("name"));
		   buildingSearchParams.put( "floorarea", getParseInteger(params.get("floorarea")));
		   buildingSearchParams.put( "districtcode", params.get("districtcode"));
		   buildingSearchParams.put( "ward", params.get("ward"));
		   buildingSearchParams.put( "street", params.get("street"));
		   buildingSearchParams.put( "numberofbasement", getParseInteger(params.get("numberofbasement")));
		   buildingSearchParams.put( "direction", params.get("direction"));
		   buildingSearchParams.put( "level", params.get("level"));
		   buildingSearchParams.put( "arearentfrom", getParseInteger(params.get("arearentfrom")));
		   buildingSearchParams.put( "arearentto", getParseInteger(params.get("arearentto")));
		   buildingSearchParams.put( "costrentfrom", getParseInteger(params.get("costrentfrom")));
		   buildingSearchParams.put( "costrentto", getParseInteger(params.get("costrentto")));
		   buildingSearchParams.put( "managername", params.get("managername"));
		   buildingSearchParams.put( "managerphone", params.get("managerphone"));
		   buildingSearchParams.put( "assignedstaffid", getParseLong(params.get("assignedstaffid")));
		
		
		System.out.println("find building --------------------");
		List<BuildingResponse> results = buildingController.findBuilding(buildingSearchParams, buildingTypes);

		for (BuildingResponse item : results) {
			System.out.println("Building: --------------------------");
			System.out.println("Building createdDate: " + item.getCreatedDate());
			System.out.println("Building name: " + item.getName());
			System.out.println("Building address: " + item.getAddress());
			System.out.println("Building manager name: " + item.getManagerName());
			System.out.println("Building manager phone: " + item.getManagerPhoneNumber());
			System.out.println("Building floorarea: " + item.getFloorArea());
			System.out.println("Building rentPrice: " + item.getRentPrice());
			System.out.println("Building serviceFee: " + item.getServiceFee());
			System.out.println("Building rentAreas: " + item.getRentalArea());
			System.out.println("Building rentTypes: " + item.getRentTypes());
		}

		System.out.println("assign building for staffs--------------------");
		List<Long> staffIdsList = Arrays.asList(2L,4L,3L);
		Set<Long> staffIds = new HashSet<>(staffIdsList);
		buildingController.assignBuilding(4L, staffIds);
	}

	private static Integer getParseInteger(String value) {
		return StringUtils.isNullOrEmpty(value) ? null : Integer.parseInt(value);
	}

	private static Long getParseLong(String value) {
		return StringUtils.isNullOrEmpty(value) ? null : Long.parseLong(value);
	}
}
