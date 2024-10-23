package service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import enums.DistrictsEnum;
import model.request.BuildingSearchRequest;
import model.response.BuildingResponse;
import repository.AssignmentBuildingRepository;
import repository.BuildingRepository;
import repository.DistrictRepository;
import repository.RentTypeRepository;
import repository.UserRepository;
import repository.enity.AssignmentBuildingEntity;
import repository.enity.BuildingEntity;
import repository.enity.RentTypeEntity;
import repository.enity.UserEntity;
import repository.impl.AssignmentBuildingRepositoryImpl;
import repository.impl.BuildingRepositoryImpl;
import repository.impl.DistrictRepositoryImpl;
import repository.impl.RentTypeRepositoryImpl;
import repository.impl.UserRepositoryImpl;
import service.BuildingService;

public class BuildingServiceImpl implements BuildingService {

	private BuildingRepository buildingRepository = new BuildingRepositoryImpl();
	private DistrictRepository districtRepository = (DistrictRepository) new DistrictRepositoryImpl();
	private UserRepository userRepository = new UserRepositoryImpl();
	private RentTypeRepository rentTypeRepository = new RentTypeRepositoryImpl();
	
	
	private AssignmentBuildingRepository assingmentBuildingRepository = new AssignmentBuildingRepositoryImpl();


	@Override
	public List<BuildingResponse> findBuilding(BuildingSearchRequest buildingSearchRequest) {

		List<BuildingResponse> results = new ArrayList<>();
		List<BuildingEntity> buildingEntities = buildingRepository.findBuilding(buildingSearchRequest);
		
		
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
			
			//Rent Type
			List<RentTypeEntity> rentTypes = rentTypeRepository.getListOfRentTypes(item.getId());
			String types = "";
			for(RentTypeEntity typeName: rentTypes) {
				types += typeName.getName();
				if(rentTypes.indexOf(typeName) != rentTypes.size()-1) {
					types += ", ";
				}
			}
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
