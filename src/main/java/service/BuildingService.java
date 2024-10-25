package service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import model.response.BuildingResponse;

public interface BuildingService {

	List<BuildingResponse> findBuilding(Map<String, Object> buildingSearchParams, List<String> buildingTypes);
	
	void assignBuilding(Long buildingId,  Set<Long> staffIds);
}
