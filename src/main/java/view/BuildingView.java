package view;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import controller.BuildingController;
import model.request.BuildingSearchRequest;
import model.response.BuildingResponse;
import utils.ConverterUtils;

public class BuildingView {
	public static void main(String[] args) {
		BuildingController buildingController = new BuildingController();

		// Input
		Map<String, String> params = new HashMap<>();
		params.put("name", null);
		params.put("floorarea", null);
		params.put("districtcode",null);
		params.put("ward",null);
		params.put("street",null);
		params.put("numberofbasement",null);
		params.put("direction",null);
		params.put("level",null);
		params.put("arearentfrom",null);
		params.put("arearentto",null);
		params.put("costrentfrom",null);
		params.put("costrentto",null);
		params.put("managername",null);
		params.put("managerphonenumber",null);
		params.put("assignedstaffid",null);
		params.put("buildingtypes",null);
		
		BuildingSearchRequest bsr = ConverterUtils.convertParamsToBuildingSearchRequest(params);
		
		System.out.println("find building --------------------");
		List<BuildingResponse> results = buildingController.findBuilding(bsr);

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
		List<Long> staffIdsList = Arrays.asList(2L);
		Set<Long> staffIds = new HashSet<>(staffIdsList);
		buildingController.assignBuilding(4L, staffIds);
	}
}
