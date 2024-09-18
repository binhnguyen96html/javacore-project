package com.example.exercise_1.dao;

import java.util.List;

import com.example.exercise_1.dao.buildingModule.BuildingDaoModule;

public interface BuildingDao {
	List<BuildingDaoModule> findBuilding(
			String name,
			String street,
			String district,
			String ward,
			Integer floorarea
//			String type
	);
}
