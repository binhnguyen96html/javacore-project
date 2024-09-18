package com.example.exercise_1.enums;

public enum BuildingTypeEnums {
	
	tang_tret ("Tầng trệt"),
	nguyen_can ("Nguyên Căn"),
	noi_that ("Nội thất");
	
	private final String value;
	
	//constructor
	private BuildingTypeEnums(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}
