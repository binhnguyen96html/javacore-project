package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import controller.BuildingController;
import model.request.BuildingSearchRequest;
import model.response.BuildingResponse;

public class BuildingView {
	public static void main(String[] args) {
		BuildingController buildingController = new BuildingController();

		// Input
		BuildingSearchRequest bsr = new BuildingSearchRequest();
		bsr.setName(null);
		bsr.setFloorArea(null);
		bsr.setDistrictCode(null);
		bsr.setWard(null);
		bsr.setStreet(null);
		bsr.setNumberOfBasement(null);
		bsr.setDirection(null);
		bsr.setLevel(null);
		bsr.setAreaRentFrom(null);
		bsr.setAreaRentTo(null);
		bsr.setCostRentFrom(null);
		bsr.setCostRentTo(null);
		bsr.setManagerName(null);
		bsr.setManagerPhoneNumber(null);
		bsr.setAssignedStaffId(null);
		
		List<String> buildingTypes = new ArrayList<>();
		buildingTypes.add("tang-tret");
		buildingTypes.add("nguyen-can");
		buildingTypes.add("noi-that");
		bsr.setBuildingtypes(buildingTypes);
		
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
		}

		System.out.println("assign building for staffs--------------------");
		List<Long> staffIdsList = Arrays.asList(2L, 3L);
		Set<Long> staffIds = new HashSet<>(staffIdsList);
		buildingController.assignBuilding(2L, staffIds);
	}
}
