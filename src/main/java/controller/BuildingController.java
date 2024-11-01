package controller;

import java.util.List;
import java.util.Map;

import model.response.BuildingSearchResponseDTO;
import service.BuildingService;
import service.impl.BuildingServiceImpl;


public class BuildingController {

	 BuildingService buildingService = new BuildingServiceImpl();
	 
	
	public List<BuildingSearchResponseDTO> findBuildingBy(Map<String, Object> buildingSearchParams, List<String> buildingTypes){
		
		List<BuildingSearchResponseDTO> results = buildingService.findBuilding(buildingSearchParams, buildingTypes);
		
		return results;
	}
	
	
//	public void assignBuilding(Long buildingId, Set<Long> staffIds) {
//		buildingService.assignBuilding(buildingId, staffIds);
//	}
//	
//	public List<UserWithAssignmentStatus> getAllStaffWithAssingmentStatus(Long buildingId){
//		return buildingService.getAllStaffWithAssingmentStatus(buildingId);
//	}
	
}
