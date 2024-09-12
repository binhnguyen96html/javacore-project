package com.binhnguyen.ex_1.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.binhnguyen.ex_1.dao.BuildingDao;
import com.binhnguyen.ex_1.dao.buildingModel.BuildingDaoModel;
import com.binhnguyen.ex_1.dao.impl.BuildingDaoImpl;
import com.binhnguyen.ex_1.input.BuildingInputModel;
import com.binhnguyen.ex_1.output.BuildingOutputModel;
import com.binhnguyen.ex_1.service.BuildingService;
import com.binhnguyen.ex_1.utils.TypeConverterUtils;

public class BuildingServiceImpl implements BuildingService {
	
	private BuildingDao buildingDao = new BuildingDaoImpl();
	
	@Override
	public List<BuildingOutputModel> findBuilding(BuildingInputModel buildingModel){
		
		List<BuildingOutputModel> buildingOutputs = new ArrayList<>();
		
		List<BuildingDaoModel> buildingDaoModels = buildingDao.findBuilding(
			buildingModel.getName(),
			buildingModel.getStreet(),
			buildingModel.getDistrict(),
			buildingModel.getWard(),
			buildingModel.getFloorarea(),
			buildingModel.getType()
		);
		
		for(BuildingDaoModel item: buildingDaoModels) {
			BuildingOutputModel buildingOutput = new BuildingOutputModel();
			
			buildingOutput.setName(item.getName());
			buildingOutput.setAddress(item.getStreet() + " ," + item.getDistrict() + " ," + item.getWard());
			buildingOutput.setType(TypeConverterUtils.typeConverter(item.getType()));
			
			buildingOutputs.add(buildingOutput);
		}
		
		return buildingOutputs;
			
	}
	
}
