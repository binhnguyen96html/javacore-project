package com.example.exercise_1.dao.buildingModule;

public class BuildingDaoModule {
	 private Long id;
	  private String name ;
	  private String street ;
	  private String district ;
	  private String ward ;
	  private Integer floorArea ;
	  private String type;
	 

	  public Long getId() {
	    return this.id;
	  }

	  public void setId(Long id) {
	    this.id = id;
	  }

	  public String getName() {
	    return this.name;
	  }

	  public void setName(String name) {
	    this.name = name;
	  }

	  public String getStreet() {
	    return this.street;
	  }

	  public void setStreet(String street) {
	    this.street = street;
	  }

	  public String getDistrict() {
	    return this.district;
	  }

	  public void setDistrict(String district) {
	    this.district = district;
	  }

	  public String getWard() {
	    return this.ward;
	  }

	  public void setWard(String ward) {
	    this.ward = ward;
	  }

	  public Integer getFloorArea() {
	    return this.floorArea;
	  }

	  public void setFloorArea(Integer floorArea) {
	    this.floorArea = floorArea;
	  }
	  

	  public String getType() {
	    return this.type;
	  }

	  public void setType(String type) {
	    this.type = type;
	  }
}
