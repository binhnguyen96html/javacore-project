package com.example.exercise_1.service.impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.example.exercise_1.constant.SystemConstant;
import com.example.exercise_1.dao.BuildingDao;
import com.example.exercise_1.dao.buildingModule.BuildingDaoModule;
import com.example.exercise_1.dao.impl.BuildingDaoImpl;
import com.example.exercise_1.input.BuildingInputModule;
import com.example.exercise_1.output.BuildingOutputModule;
import com.example.exercise_1.service.BuildingService;
import com.example.exercise_1.utils.BuildingTypeUtil;


public class BuildingServiceImpl implements BuildingService {
	
	private BuildingDao buildingDao = new BuildingDaoImpl();
	
	@Override
	public List<BuildingOutputModule> findBuilding(BuildingInputModule buildingModule){


		//use for convert building code to name
		HashMap<String, String> building_type_map = new HashMap<>();
		building_type_map.put(SystemConstant.TANG_TRET_TYPE_CODE, SystemConstant.TANG_TRET_NAME);
		building_type_map.put(SystemConstant.NGUYEN_CAN_TYPE_CODE, SystemConstant.NGUYEN_CAN_NAME);
		building_type_map.put(SystemConstant.NOI_THAT_TYPE_CODE, SystemConstant.NOI_THAT_NAME);
		

		List<BuildingOutputModule> buildingOutputs = new ArrayList<>();
		
		List<BuildingDaoModule> buildingDaoModels = buildingDao.findBuilding(
			buildingModule.getName(),
			buildingModule.getStreet(),
			buildingModule.getDistrict(),
			buildingModule.getWard(),
			buildingModule.getFloorarea()
		);
		
		for(BuildingDaoModule item: buildingDaoModels) {
			BuildingOutputModule buildingOutput = new BuildingOutputModule();
			
			buildingOutput.setName(item.getName());
			buildingOutput.setAddress(item.getStreet() + " ," + item.getDistrict() + " ," + item.getWard());
			

			/*
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
			*/
			
			
			buildingOutput.setType(BuildingTypeUtil.getType(item.getType()));
			buildingOutputs.add(buildingOutput);
		}
		
		return buildingOutputs;
			
	}
	
//	private void buildBuildingType(BuildingOutputModule buildingOutput, String oldType) {
//		List<String> newTypes = new ArrayList<>();
//		
//		for(String str: oldType.split(",")) {
//			newTypes.add(BuildingTypeUtil.convertCodeToName(str.trim()));;
//		}
//		buildingOutput.setType(String.join(",", newTypes));
//	}
	
}

