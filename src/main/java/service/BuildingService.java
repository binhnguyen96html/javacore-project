package service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import model.response.BuildingSearchResponseDTO;
import model.response.UserWithAssignmentStatus;

public interface BuildingService {

	List<BuildingSearchResponseDTO> findBuilding(Map<String, Object> buildingSearchParams, List<String> buildingTypes);
	
//	void assignBuilding(Long buildingId,  Set<Long> staffIds);
//	
//	List<UserWithAssignmentStatus> getAllStaffWithAssingmentStatus(Long buildingId);
}
