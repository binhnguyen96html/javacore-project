package com.example.exercise_1.view;


import java.util.List;

import  com.example.exercise_1.controller.BuildingController;
import  com.example.exercise_1.input.BuildingInputModule;
import  com.example.exercise_1.output.BuildingOutputModule;

public class BuildingView {

	
	public static void main(String[] args) {
		
	    String name = null;
	    String street = null;
	    String district = null;
	    String ward = null;
	    Integer floorarea = null;
	    
	    BuildingInputModule buildingSearch = new BuildingInputModule();
	    buildingSearch.setName(name);
	    buildingSearch.setStreet(street);
	    buildingSearch.setDistrict(district);
	    buildingSearch.setWard(ward);
	    buildingSearch.setFloorarea(floorarea);
	    
      
        BuildingController buildingController = new BuildingController();
        List<BuildingOutputModule> results = buildingController.findBuilding(buildingSearch);
        
        
        for(BuildingOutputModule item: results) {
        	System.out.print(item.getName().toString() + ", ");
        	System.out.print(item.getAddress() + ", ");
        	System.out.println(item.getType());
        }
	}
}

