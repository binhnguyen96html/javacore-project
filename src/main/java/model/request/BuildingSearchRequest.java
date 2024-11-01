package model.request;

public class BuildingSearchRequest {
	
	private Long id;
	private String name;
	private Integer floorArea;
	private String districtCode;
	private String ward;
	private String street;
	private Integer numberOfBasement;
	private String direction;
	private String level;
	private Integer areaRentFrom;
	private Integer areaRentTo;
	private Integer costRentFrom;
	private Integer costRentTo;
	private String managerName;
	private String managerPhoneNumber;
	private Long assignedStaffId;
	private String rentTypes;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public String getRentTypes() {
		return rentTypes;
	}
	public void setRentTypes(String rentTypes) {
		this.rentTypes = rentTypes;
	}
	
}
