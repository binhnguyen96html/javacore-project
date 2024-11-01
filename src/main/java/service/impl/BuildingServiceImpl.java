package service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import converter.BuildingConverter;
import model.response.BuildingSearchResponseDTO;
import repository.AssignmentBuildingRepository;
import repository.BuildingRepository;
import repository.DistrictRepository;
import repository.RentAreaRepository;
import repository.RentTypeRepository;
import repository.UserRepository;
import repository.entity.BuildingEntity;
import repository.entity.DistrictEntity;
import repository.entity.RentAreaEntity;
import repository.entity.RentTypeEntity;
import repository.impl.AssignmentBuildingRepositoryImpl;
import repository.impl.BuildingRepositoryImpl;
import repository.impl.DistrictRepositoryImpl;
import repository.impl.RentAreaRepositoryImpl;
import repository.impl.RentTypeRepositoryImpl;
import repository.impl.UserRepositoryImpl;
import service.BuildingService;

public class BuildingServiceImpl implements BuildingService {

	private BuildingRepository buildingRepository = new BuildingRepositoryImpl();
	private DistrictRepository districtRepository = (DistrictRepository) new DistrictRepositoryImpl();
	private RentTypeRepository rentTypeRepository = new RentTypeRepositoryImpl();
	private RentAreaRepository rentAreaRepository = new RentAreaRepositoryImpl();
	private UserRepository userRepository =  new UserRepositoryImpl();
	
	private final BuildingConverter buildingConverter = new BuildingConverter();

	private AssignmentBuildingRepository assingmentBuildingRepository = new AssignmentBuildingRepositoryImpl();

	@Override
	public List<BuildingSearchResponseDTO> findBuilding(Map<String, Object> buildingSearchParams, List<String> buildingTypes) {

		List<BuildingSearchResponseDTO> results = new ArrayList<>();
		
		List<BuildingEntity> buildingEntities = buildingRepository.findBuildingBy(buildingSearchParams, buildingTypes);
		
		Set<Long> districtIds = new HashSet<>();
		Set<Long> buildingIds = new HashSet<>();
		
		buildingEntities.forEach(b -> {
			districtIds.add(b.getDistrictId());
			buildingIds.add(b.getId());
		});
		
		Map<Long, DistrictEntity> districtById = processDistrictMap(districtIds);
		Map<Long, List<RentAreaEntity>> buildingToRentArea = processRentAreaMap(buildingIds);


		for (BuildingEntity building : buildingEntities) {
			String buildingRentTypes = processBuildingRentTypes(building.getId());
			
			results.add(buildingConverter.toSearchResponse(
					building,
					districtById.get(building.getDistrictId()),
					buildingToRentArea.get(building.getId()),
					buildingRentTypes
					)
					);
		}

		return results;
	}


	
	private Map<Long, List<RentAreaEntity>> processRentAreaMap(Set<Long> buildingIds) {
		List<RentAreaEntity> rentAreaEntities = rentAreaRepository.findByBuildingIdIn(buildingIds);

		return rentAreaEntities
				.stream()
				.collect(Collectors.groupingBy(RentAreaEntity::getBuildingId));
	}

	private Map<Long, DistrictEntity> processDistrictMap(Set<Long> districtIds) {
		List<DistrictEntity> districtEntities = districtRepository.findByIdIn(districtIds);

		return Optional.ofNullable(districtEntities)
				.map(result -> result.stream()
						.collect(Collectors.toMap(DistrictEntity::getId, v -> v)))
				.orElse(Collections.emptyMap());
	}
	
	private String processBuildingRentTypes(Long buildingId) {
		List<RentTypeEntity> rentTypesEntities = rentTypeRepository.getListOfRentTypes(buildingId);
		String types = (rentTypesEntities != null && !rentTypesEntities.isEmpty()) 
				? rentTypesEntities.stream().map(type -> type.getName()).collect(Collectors.joining(", "))
				: "building types are not found";
		
		return types;
	}
	
	
//
//	@Override
//	public void assignBuilding(Long buildingId, Set<Long> staffIds) {
//		List<AssignmentBuildingEntity> ass = assingmentBuildingRepository.getAssignmentBuildingListById(buildingId);
//
//		Set<Long> curStaffIds = new HashSet<>();
//		for (AssignmentBuildingEntity item : ass) {
//			curStaffIds.add(item.getStaffId());
//		}
//
//		// buildingsToInsert when id when new staffId not in curStaffIds
//		List<AssignmentBuildingEntity> staffsToInsert = new ArrayList<>();
//		for (Long item : staffIds) {
//			if (!curStaffIds.contains(item)) {
//				AssignmentBuildingEntity assB = new AssignmentBuildingEntity();
//				assB.setBuildingId(buildingId);
//				assB.setStaffId(item);
//				assB.setBuildingId(buildingId);
//				staffsToInsert.add(assB);
//			}
//		}
//
//		// buildingIdsToDelete: find id when current staffId not in new staffIds
//		List<Long> staffIdsToDelete = new ArrayList<>();
//		for (Long item : curStaffIds) {
//			if (!staffIds.contains(item)) {
//				staffIdsToDelete.add(item);
//			}
//		}
//
//		assingmentBuildingRepository.assignBuilding(buildingId, staffIdsToDelete, staffsToInsert);
//
//	}// assignBuilding
//
//	@Override
//	public List<UserWithAssignmentStatus> getAllStaffWithAssingmentStatus(Long buildingId) {
//		List<UserWithAssignmentStatus> results = new ArrayList<>();
//		
//	
//		List<UserEntity> getAllStaff = userRepository.getAllWorkingStaff();
//		List<Long> getAllStaffAssignmentByBuildingId_ids = userRepository.getAllStaffAssignmentByBuildingId(buildingId)
//				.stream().map(item->item.getId()).collect(Collectors.toList());
//		
//		for(UserEntity item: getAllStaff) {
//			UserWithAssignmentStatus staff = new UserWithAssignmentStatus();
//			if(getAllStaffAssignmentByBuildingId_ids.contains(item.getId())) {
//				staff.setChecked(true);
//			}else {
//				staff.setChecked(false);
//			}
//			staff.setId(item.getId());
//			staff.setFullName(item.getFullName());
//			
//			results.add(staff);
//		}
//		
//		return results;
//	}
}
