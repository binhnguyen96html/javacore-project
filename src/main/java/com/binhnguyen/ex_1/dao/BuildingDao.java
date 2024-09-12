package com.binhnguyen.ex_1.dao;

import java.util.List;

import com.binhnguyen.ex_1.dao.buildingModel.BuildingDaoModel;

public interface BuildingDao {
	
	List<BuildingDaoModel> findBuilding(
			String name,
			String street,
			String district,
			String ward,
			Integer floorarea,
			String type
	);
}
