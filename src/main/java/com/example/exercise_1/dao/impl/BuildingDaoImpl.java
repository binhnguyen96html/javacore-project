package com.example.exercise_1.dao.impl;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.exercise_1.utils.ConnectionUtils;
import com.example.exercise_1.utils.StringUtils;
import com.example.exercise_1.constant.SystemConstant;
import com.example.exercise_1.dao.BuildingDao;
import com.example.exercise_1.dao.buildingModule.BuildingDaoModule;



public class BuildingDaoImpl implements BuildingDao {

	@Override
	public List<BuildingDaoModule> findBuilding(String name, String street, String district, String ward,
			Integer floorarea) {
		
		List<BuildingDaoModule> buildings = new ArrayList<>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			StringBuilder query = new StringBuilder(
			"SELECT building.name, building.street, building.district, building.ward, building.floorarea, GROUP_CONCAT(bType.type SEPARATOR ', ') AS types FROM building  "
			+ "INNER JOIN building_bType ON building_bType.building_id = building.id "
			+ "INNER JOIN bType ON building_bType.bType_id = bType.id "
			+ SystemConstant.ONE_EQUAL_ONE
					);
			
			if(!StringUtils.isNullOrEmpty(name)) {
				query.append(" and name like '%" + name + "%'");
			}
			if(!StringUtils.isNullOrEmpty(street)) {
				query.append(" and street like '%" + street + "%'");
			}
			if(!StringUtils.isNullOrEmpty(district)) {
				query.append(" and district like '%" + district + "%'");
			}
			if(!StringUtils.isNullOrEmpty(ward)) {
				query.append(" and ward like '%" + ward + "%'");
			}
			if(floorarea != null) {
				query.append(" and floorarea like '%" + floorarea + "%'");
			}
			
			query.append(" GROUP BY building.name, building.street, building.district, building.ward, building.floorarea");
			
			 System.out.println("query: " + query);
			 
			 // step 1: load driver 
			 Class.forName("com.mysql.cj.jdbc.Driver");
			 // step 2: create connection
			 conn = ConnectionUtils.getConnection();
			 // step 3: initialize statement
			 stmt = conn.createStatement();
			 // step 4: execute sql
			 rs = stmt.executeQuery(query.toString());
			 
			 while(rs.next()) {
				 BuildingDaoModule buildingDaoModule = new BuildingDaoModule();
				 
				 buildingDaoModule.setName(rs.getString("name"));
				 buildingDaoModule.setStreet(rs.getString("street"));
				 buildingDaoModule.setDistrict(rs.getString("district"));
				 buildingDaoModule.setWard(rs.getString("ward"));
				 buildingDaoModule.setFloorArea(rs.getInt("floorarea"));
				 buildingDaoModule.setType(rs.getString("types"));
				 
				 buildings.add(buildingDaoModule);
			 }
			 
			 
//			 System.out.println("buildings: " + buildings);
			 
			 return buildings;
			 
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}finally {
			try {
				if(conn != null) conn.close();
				if(stmt != null) stmt.close();
				if(rs != null) rs.close();
 			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
				e.printStackTrace();
			}
		}

		return new ArrayList<>();
	}
}
