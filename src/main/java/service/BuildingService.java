package service;

import java.util.List;
import java.util.Set;

import model.request.BuildingSearchRequest;
import model.response.BuildingResponse;

public interface BuildingService {

	List<BuildingResponse> findBuilding(BuildingSearchRequest buildingSearchRequest);
	
	void assignBuilding(Long buildingId, Set<Long> staffIds);
}
