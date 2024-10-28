package service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import model.response.BuildingResponse;
import repository.AssignmentBuildingRepository;
import repository.BuildingRepository;
import repository.DistrictRepository;
import repository.RentAreaRepository;
import repository.RentTypeRepository;
import repository.entity.AssignmentBuildingEntity;
import repository.entity.BuildingEntity;
import repository.entity.DistrictEntity;
import repository.entity.RentAreaEntity;
import repository.entity.RentTypeEntity;
import repository.impl.AssignmentBuildingRepositoryImpl;
import repository.impl.BuildingRepositoryImpl;
import repository.impl.DistrictRepositoryImpl;
import repository.impl.RentAreaRepositoryImpl;
import repository.impl.RentTypeRepositoryImpl;
import service.BuildingService;

public class BuildingServiceImpl implements BuildingService {

	private BuildingRepository buildingRepository = new BuildingRepositoryImpl();
	private DistrictRepository districtRepository = (DistrictRepository) new DistrictRepositoryImpl();
	private RentTypeRepository rentTypeRepository = new RentTypeRepositoryImpl();
	private RentAreaRepository rentAreaRepository = new RentAreaRepositoryImpl();

	private AssignmentBuildingRepository assingmentBuildingRepository = new AssignmentBuildingRepositoryImpl();

	@Override
	public List<BuildingResponse> findBuilding(Map<String, Object> buildingSearchParams, List<String> buildingTypes) {

		List<BuildingResponse> results = new ArrayList<>();

		List<BuildingEntity> buildingEntities = buildingRepository.findBuilding(buildingSearchParams, buildingTypes);

		for (BuildingEntity item : buildingEntities) {
			BuildingResponse buildingResponse = new BuildingResponse();
			buildingResponse.setCreatedDate(item.getCreatedDate());
			buildingResponse.setName(item.getName());
			buildingResponse.setManagerName(item.getManagername());
			buildingResponse.setManagerPhoneNumber(item.getManagerphone());

			// Address
			DistrictEntity districtEntity = districtRepository.findById((long) item.getDistrictId());
			String districtNameOfFoundBuilding = districtEntity == null ? "district name is not found" : districtEntity.getName();
			buildingResponse.setAddress(item.getStreet() + ", " + item.getWard() + ", " + districtNameOfFoundBuilding);

			buildingResponse.setFloorArea(item.getFloorArea());
			buildingResponse.setRentPrice(item.getRentPrice());
			buildingResponse.setServiceFee(item.getServiceFee());

			// Rent Area
			List<RentAreaEntity> rentAreas = rentAreaRepository.getListRentAreasById(item.getId());
			String areas = (rentAreas != null && !rentAreas.isEmpty()) ? 
					rentAreas.stream().map(area -> area.getValue().toString()).collect(Collectors.joining(", "))
					: "rent areas are not found";
			buildingResponse.setRentalArea(areas);

			// Rent Type
			List<RentTypeEntity> rentTypesEntities = rentTypeRepository.getListOfRentTypes(item.getId());
			String types = (rentTypesEntities != null && !rentTypesEntities.isEmpty()) 
					? rentTypesEntities.stream().map(type -> type.getName()).collect(Collectors.joining(", "))
					: "building types are not found";
			buildingResponse.setRentTypes(types);

			results.add(buildingResponse);
		}

		return results;
	}

	@Override
	public void assignBuilding(Long buildingId, Set<Long> staffIds) {
		List<AssignmentBuildingEntity> ass = assingmentBuildingRepository.getAssignmentBuildingListById(buildingId);

		Set<Long> curStaffIds = new HashSet<>();
		for (AssignmentBuildingEntity item : ass) {
			curStaffIds.add(item.getStaffId());
		}

		// buildingsToInsert when id when new staffId not in curStaffIds
		List<AssignmentBuildingEntity> staffsToInsert = new ArrayList<>();
		for (Long item : staffIds) {
			if (!curStaffIds.contains(item)) {
				AssignmentBuildingEntity assB = new AssignmentBuildingEntity();
				assB.setBuildingId(buildingId);
				assB.setStaffId(item);
				assB.setBuildingId(buildingId);
				staffsToInsert.add(assB);
			}
		}

		// buildingIdsToDelete: find id when current staffId not in new staffIds
		List<Long> staffIdsToDelete = new ArrayList<>();
		for (Long item : curStaffIds) {
			if (!staffIds.contains(item)) {
				staffIdsToDelete.add(item);
			}
		}

		assingmentBuildingRepository.assignBuilding(buildingId, staffIdsToDelete, staffsToInsert);

	}// assignBuilding

}
