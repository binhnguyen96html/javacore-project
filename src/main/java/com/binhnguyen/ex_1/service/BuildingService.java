package com.binhnguyen.ex_1.service;

import java.util.List;

import com.binhnguyen.ex_1.input.BuildingInputModel;
import com.binhnguyen.ex_1.output.BuildingOutputModel;

public interface BuildingService {
	List<BuildingOutputModel> findBuilding(BuildingInputModel buildingModel);
}
