package com.binhnguyen.ex_1.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.binhnguyen.ex_1.constant.SystemConstant;
import com.binhnguyen.ex_1.dao.BuildingDao;
import com.binhnguyen.ex_1.dao.buildingModule.BuildingDaoModule;
import com.binhnguyen.ex_1.dao.impl.BuildingDaoImpl;
import com.binhnguyen.ex_1.input.BuildingInputModel;
import com.binhnguyen.ex_1.output.BuildingOutputModel;
import com.binhnguyen.ex_1.service.BuildingService;


public class BuildingServiceImpl implements BuildingService {
	
	private BuildingDao buildingDao = new BuildingDaoImpl();
	
	@Override
	public List<BuildingOutputModel> findBuilding(BuildingInputModel buildingModel){


		//use for convert building code to name
		HashMap<String, String> building_type_map = new HashMap<>();
		building_type_map.put(SystemConstant.TANG_TRET_TYPE_CODE, SystemConstant.TANG_TRET_NAME);
		building_type_map.put(SystemConstant.NGUYEN_CAN_TYPE_CODE, SystemConstant.NGUYEN_CAN_NAME);
		building_type_map.put(SystemConstant.NOI_THAT_TYPE_CODE, SystemConstant.NOI_THAT_NAME);
		

		List<BuildingOutputModel> buildingOutputs = new ArrayList<>();
		
		List<BuildingDaoModule> buildingDaoModels = buildingDao.findBuilding(
			buildingModel.getName(),
			buildingModel.getStreet(),
			buildingModel.getDistrict(),
			buildingModel.getWard(),
			buildingModel.getFloorarea(),
			buildingModel.getType()
		);
		
		for(BuildingDaoModule item: buildingDaoModels) {
			BuildingOutputModel buildingOutput = new BuildingOutputModel();
			
			buildingOutput.setName(item.getName());
			buildingOutput.setAddress(item.getStreet() + " ," + item.getDistrict() + " ," + item.getWard());
			

			//use to convert string to array
			 List<String> typeList = Arrays.stream(item.getType().split(","))
                                      .map(String::trim)
                                      .collect(Collectors.toList());

			// use for concat string
			StringBuilder convertedTypes = new StringBuilder();

			for(String type: typeList){
				convertedTypes.append(building_type_map.getOrDefault(type, "unknown type"));

				if(typeList.indexOf(type) != typeList.size() - 1){
					convertedTypes.append(", ");
				}
			}

			buildingOutput.setType(convertedTypes.toString());
			
			buildingOutputs.add(buildingOutput);
		}
		
		return buildingOutputs;
			
	}
	
}
