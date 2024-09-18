package com.example.exercise_1.controller;

import java.util.List;

import com.example.exercise_1.input.BuildingInputModule;
import com.example.exercise_1.output.BuildingOutputModule;
import com.example.exercise_1.service.BuildingService;
import com.example.exercise_1.service.impl.BuildingServiceImpl;

public class BuildingController {
	private BuildingService buildingService = new BuildingServiceImpl();
	

	public List<BuildingOutputModule> findBuilding(BuildingInputModule buildingSearch) {
		List<BuildingOutputModule> buildingOutputs = buildingService.findBuilding(buildingSearch);

		return buildingOutputs;
	}
}
