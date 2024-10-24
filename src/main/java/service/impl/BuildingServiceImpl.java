package service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import enums.DistrictsEnum;
import model.request.BuildingSearchRequest;
import model.request.BuildingSearchRequestRepository;
import model.response.BuildingResponse;
import repository.AssignmentBuildingRepository;
import repository.BuildingRepository;
import repository.DistrictRepository;
import repository.RentAreaRepository;
import repository.RentTypeRepository;
import repository.UserRepository;
import repository.entity.AssignmentBuildingEntity;
import repository.entity.BuildingEntity;
import repository.entity.RentAreaEntity;
import repository.entity.RentTypeEntity;
import repository.entity.UserEntity;
import repository.impl.AssignmentBuildingRepositoryImpl;
import repository.impl.BuildingRepositoryImpl;
import repository.impl.DistrictRepositoryImpl;
import repository.impl.RentAreaRepositoryImpl;
import repository.impl.RentTypeRepositoryImpl;
import repository.impl.UserRepositoryImpl;
import service.BuildingService;
import utils.ConverterUtils;

public class BuildingServiceImpl implements BuildingService {

	private BuildingRepository buildingRepository = new BuildingRepositoryImpl();
	private DistrictRepository districtRepository = (DistrictRepository) new DistrictRepositoryImpl();
	private UserRepository userRepository = new UserRepositoryImpl();
	private RentTypeRepository rentTypeRepository = new RentTypeRepositoryImpl();
	private RentAreaRepository rentAreaRepository = new RentAreaRepositoryImpl();
	
	private AssignmentBuildingRepository assingmentBuildingRepository = new AssignmentBuildingRepositoryImpl();


	@Override
	public List<BuildingResponse> findBuilding(BuildingSearchRequest buildingSearchRequest) {

		List<BuildingResponse> results = new ArrayList<>();
		
		BuildingSearchRequestRepository bsrr = ConverterUtils.covertBsrToBsrr(buildingSearchRequest);
		List<BuildingEntity> buildingEntities = buildingRepository.findBuilding(bsrr);
		
		for(BuildingEntity item: buildingEntities) {
			BuildingResponse buildingResponse = new BuildingResponse();
			buildingResponse.setCreatedDate(item.getCreatedDate());
			buildingResponse.setName(item.getName());
			
			//Address
			String districtCodeOfFoundBuilding = districtRepository.findById((long) item.getDistrictId()).getCode();
			buildingResponse.setAddress(item.getStreet()+", "+item.getWard()+", "+DistrictsEnum.valueOf(districtCodeOfFoundBuilding));
		
			//Manager Info
			UserEntity managerInfo = userRepository.getInfoByRole("manager").get(0);
			buildingResponse.setManagerName(managerInfo.getFullname());;
			buildingResponse.setManagerPhoneNumber(managerInfo.getPhone());
			
			buildingResponse.setFloorArea(item.getFloorArea());
			buildingResponse.setRentPrice(item.getRentPrice());
			buildingResponse.setServiceFee(item.getServiceFee());
			
			//Rent Area
			List<String> rentAreas = rentAreaRepository.getListRentAreas(item.getId());
			String areas = String.join(", ", rentAreas);
			buildingResponse.setRentalArea(areas);
			
			//Rent Type
			List<String> rentTypes = rentTypeRepository.getListOfRentTypes(item.getId());
			String types = String.join(", ", rentTypes);
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
		
		//buildingsToInsert when id when new staffId not in curStaffIds
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

		//buildingIdsToDelete: find id when current staffId not in new staffIds
		List<Long> staffIdsToDelete = new ArrayList<>();
		for (Long item : curStaffIds) {
			if (!staffIds.contains(item)) {
				staffIdsToDelete.add(item);
			}
		}

	   assingmentBuildingRepository.assignBuilding(buildingId, staffIdsToDelete, staffsToInsert);
	   
	}// assignBuilding
	
	

}
