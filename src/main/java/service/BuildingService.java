package service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import model.response.BuildingResponse;
import model.response.UserWithAssignmentStatus;

public interface BuildingService {

	List<BuildingResponse> findBuilding(Map<String, Object> buildingSearchParams, List<String> buildingTypes);
	
	void assignBuilding(Long buildingId,  Set<Long> staffIds);
	
	List<UserWithAssignmentStatus> getAllStaffWithAssingmentStatus(Long buildingId);
}
