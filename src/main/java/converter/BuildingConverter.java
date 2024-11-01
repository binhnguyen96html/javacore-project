package converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.response.BuildingSearchResponseDTO;
import repository.entity.BuildingEntity;
import repository.entity.DistrictEntity;
import repository.entity.RentAreaEntity;
import utils.DateUtils;
import utils.ValidateUtils;

public class BuildingConverter {
	
	public BuildingSearchResponseDTO toSearchResponse(
			BuildingEntity buildingEntity, 
			DistrictEntity districtEntity,
			List<RentAreaEntity> rentAreaEntities,
			String buildingRentTypes
			) {
		
		BuildingSearchResponseDTO result = new BuildingSearchResponseDTO();

		result.setId(buildingEntity.getId());
		
		//DATE
		result.setCreatedDate(DateUtils.convertDateToString(buildingEntity.getCreatedDate()));

		result.setName(buildingEntity.getName());

		//ADDRESS
		List<String> address = new ArrayList<>();
		address.add(buildingEntity.getStreet());
		address.add(buildingEntity.getWard());
		address.add(districtEntity != null ? districtEntity.getName() : "");
		result.setAddress(address.stream().filter(ValidateUtils::isNotBlank).collect(Collectors.joining(", ")));

		result.setFloorArea(buildingEntity.getFloorArea().toString());
		result.setRentPrice(buildingEntity.getRentPrice().toString());
		result.setServiceFee(buildingEntity.getServiceFee());
		result.setManagerName(buildingEntity.getManagerName());
		result.setManagerPhone(buildingEntity.getManagerPhone());
		
		result.setNumberOfBasement(buildingEntity.getNumberOfBasement());
		result.setServiceFee(buildingEntity.getServiceFee());

		// RENT AREA
		if (rentAreaEntities != null && !rentAreaEntities.isEmpty()) {
			result.setRentAreaDescription(
					rentAreaEntities.stream().map(rentArea -> rentArea.getValue().toString()).collect(Collectors.joining(", ")));
		}
		
		// RENT TYPE
		result.setRentTypes(buildingRentTypes);

		return result;
	}
}
