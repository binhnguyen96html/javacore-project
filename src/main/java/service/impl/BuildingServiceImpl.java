package service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import enums.DistrictsEnum;
import model.request.BuildingSearchRequest;
import model.response.BuildingResponse;
import repository.AssignmentBuildingRepository;
import repository.BuildingRepository;
import repository.DistrictRepository;
import repository.UserRepository;
import repository.enity.AssignmentBuildingEntity;
import repository.enity.BuildingEntity;
import repository.enity.UserEntity;
import repository.impl.AssignmentBuildingRepositoryImpl;
import repository.impl.BuildingRepositoryImpl;
import repository.impl.DistrictRepositoryImpl;
import repository.impl.UserRepositoryImpl;
import service.BuildingService;

public class BuildingServiceImpl implements BuildingService {

	private BuildingRepository buildingRepository = new BuildingRepositoryImpl();
	private DistrictRepository districtRepository = (DistrictRepository) new DistrictRepositoryImpl();
	private UserRepository userRepository = new UserRepositoryImpl();
	
	private AssignmentBuildingRepository assingmentBuildingRepository = new AssignmentBuildingRepositoryImpl();


	@Override
	public List<BuildingResponse> findBuilding(BuildingSearchRequest buildingSearchRequest) {

		List<BuildingResponse> results = new ArrayList<>();
		List<BuildingEntity> buildingEntities = buildingRepository.findBuilding(buildingSearchRequest);
		
		for(BuildingEntity item: buildingEntities) {
			BuildingResponse buildingResponse = new BuildingResponse();
			buildingResponse.setCreatedDate(item.getCreatedDate());
			buildingResponse.setName(item.getName());
			
			String districtCodeOfFoundBuilding = districtRepository.getDistrictCode(item.getDistrictId()).get(0).getCode();
			buildingResponse.setAddress(item.getStreet()+", "+item.getWard()+", "+DistrictsEnum.valueOf(districtCodeOfFoundBuilding));
		
			UserEntity managerInfo = userRepository.getInfoByRole("manager").get(0);
			buildingResponse.setManagerName(managerInfo.getFullname());;
			buildingResponse.setManagerPhoneNumber(managerInfo.getPhone());
			buildingResponse.setFloorArea(item.getFloorArea());
			buildingResponse.setRentPrice(item.getRentPrice());
			buildingResponse.setServiceFee(item.getServiceFee());
			
			results.add(buildingResponse);
		}
		
		return results;
	}


	@Override
	public void assignBuilding(Long buildingId, Set<Long> staffIds) {
		
		for(Long item: staffIds) {
			AssignmentBuildingEntity assignmentBuildingEntity = new AssignmentBuildingEntity();
			assignmentBuildingEntity.setStaffId(item);
			assignmentBuildingEntity.setBuildingId(buildingId);
			
			assingmentBuildingRepository.assignBuilding(buildingId, staffIds);
		}
	}// assignBuilding
	
	

}
