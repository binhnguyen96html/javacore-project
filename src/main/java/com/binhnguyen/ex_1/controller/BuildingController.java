package com.binhnguyen.ex_1.controller;

import java.util.List;

import com.binhnguyen.ex_1.input.BuildingInputModel;
import com.binhnguyen.ex_1.output.BuildingOutputModel;
import com.binhnguyen.ex_1.service.BuildingService;
import com.binhnguyen.ex_1.service.impl.BuildingServiceImpl;

public class BuildingController {

	private BuildingService buildingService = new BuildingServiceImpl();
	
	public List<BuildingOutputModel> findBuilding(BuildingInputModel buildingSearch){
		List<BuildingOutputModel> buildingOutputs = buildingService.findBuilding(buildingSearch);
		
		return buildingOutputs;
	}
}
