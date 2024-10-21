package controller;

import java.util.List;
import java.util.Set;

import model.request.BuildingSearchRequest;
import model.response.BuildingResponse;
import service.BuildingService;
import service.impl.BuildingServiceImpl;

public class BuildingController {

	 BuildingService buildingService = new BuildingServiceImpl();
	 
	
	public List<BuildingResponse> findBuilding(BuildingSearchRequest buildingSearchRequest){
		
		List<BuildingResponse> results = buildingService.findBuilding(buildingSearchRequest);
		
		return results;
	}
	
	
	public void assignBuilding(Long buildingId, Set<Long> staffIds) {
		buildingService.assignBuilding(buildingId, staffIds);
	}
	
}
