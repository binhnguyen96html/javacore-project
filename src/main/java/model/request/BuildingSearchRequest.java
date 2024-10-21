package model.request;

import java.util.List;

public class BuildingSearchRequest {
	
	String name ;
	Integer floorArea ;
	String districtCode ;
	String ward ;
	String street ;
	Integer numberOfBasement ;
	String direction ;
	String level ;
	Integer areaRentFrom ;
	Integer areaRentTo ;
	Integer costRentFrom ;
	Integer costRentTo ;
	String managerName ;
	String managerPhoneNumber ;
	Long assignedStaffId;
	List<String> buildingtypes;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getFloorArea() {
		return floorArea;
	}
	public void setFloorArea(Integer floorArea) {
		this.floorArea = floorArea;
	}
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	public String getWard() {
		return ward;
	}
	public void setWard(String ward) {
		this.ward = ward;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public Integer getNumberOfBasement() {
		return numberOfBasement;
	}
	public void setNumberOfBasement(Integer numberOfBasement) {
		this.numberOfBasement = numberOfBasement;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public Integer getAreaRentFrom() {
		return areaRentFrom;
	}
	public void setAreaRentFrom(Integer areaRentFrom) {
		this.areaRentFrom = areaRentFrom;
	}
	public Integer getAreaRentTo() {
		return areaRentTo;
	}
	public void setAreaRentTo(Integer areaRentTo) {
		this.areaRentTo = areaRentTo;
	}
	public Integer getCostRentFrom() {
		return costRentFrom;
	}
	public void setCostRentFrom(Integer costRentFrom) {
		this.costRentFrom = costRentFrom;
	}
	public Integer getCostRentTo() {
		return costRentTo;
	}
	public void setCostRentTo(Integer costRentTo) {
		this.costRentTo = costRentTo;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getManagerPhoneNumber() {
		return managerPhoneNumber;
	}
	public void setManagerPhoneNumber(String managerPhoneNumber) {
		this.managerPhoneNumber = managerPhoneNumber;
	}
	public Long getAssignedStaffId() {
		return assignedStaffId;
	}
	public void setAssignedStaffId(Long assignedStaffId) {
		this.assignedStaffId = assignedStaffId;
	}
	public List<String> getBuildingtypes() {
		return buildingtypes;
	}
	public void setBuildingtypes(List<String> buildingtypes) {
		this.buildingtypes = buildingtypes;
	}
	
	
}
