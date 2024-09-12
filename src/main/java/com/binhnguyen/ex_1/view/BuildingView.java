package com.binhnguyen.ex_1.view;

import java.util.List;

import com.binhnguyen.ex_1.controller.BuildingController;

import com.binhnguyen.ex_1.input.BuildingInputModel;
import com.binhnguyen.ex_1.output.BuildingOutputModel;

public class BuildingView {

	
	public static void main(String[] args) {
		
	    String name = null;
	    String street = null;
	    String district = null;
	    String ward = null;
	    Integer floorarea = null;
	    String type = null;
	    
	    BuildingInputModel buildingSearch = new BuildingInputModel();
	    buildingSearch.setName(name);
	    buildingSearch.setStreet(street);
	    buildingSearch.setDistrict(district);
	    buildingSearch.setWard(ward);
	    buildingSearch.setFloorarea(floorarea);
	    buildingSearch.setType(type);
	    
      
        BuildingController buildingController = new BuildingController();
        List<BuildingOutputModel> results = buildingController.findBuilding(buildingSearch);
        
        
        for(BuildingOutputModel item: results) {
        	System.out.print(item.getName().toString() + ", ");
        	System.out.print(item.getAddress() + ", ");
        	System.out.println(item.getType());
        }
	}
}
