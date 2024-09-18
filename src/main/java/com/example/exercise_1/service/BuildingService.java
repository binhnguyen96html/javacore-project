package com.example.exercise_1.service;




import java.util.List;

import com.example.exercise_1.input.BuildingInputModule;
import com.example.exercise_1.output.BuildingOutputModule;


public interface BuildingService {
	List<BuildingOutputModule> findBuilding(BuildingInputModule buildingModule);
}
