package model.response;

public class BuildingSearchResponseDTO {

	private Long id;
	private String createdDate;
	private String name;
	private Integer numberOfBasement;
	private String address;
	private String managerName;
	private String managerPhone;
	private String floorArea;
	private String rentalArea;
	private String rentTypes;
	private String rentPrice;
	private String serviceFee;
	private String brokeragefee;
	private String rentAreaDescription;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNumberOfBasement() {
		return numberOfBasement;
	}
	public void setNumberOfBasement(Integer numberOfBasement) {
		this.numberOfBasement = numberOfBasement;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getManagerPhone() {
		return managerPhone;
	}
	public void setManagerPhone(String managerPhone) {
		this.managerPhone = managerPhone;
	}
	public String getFloorArea() {
		return floorArea;
	}
	public void setFloorArea(String floorArea) {
		this.floorArea = floorArea;
	}
	public String getRentalArea() {
		return rentalArea;
	}
	public void setRentalArea(String rentalArea) {
		this.rentalArea = rentalArea;
	}
	public String getRentTypes() {
		return rentTypes;
	}
	public void setRentTypes(String rentTypes) {
		this.rentTypes = rentTypes;
	}
	public String getRentPrice() {
		return rentPrice;
	}
	public void setRentPrice(String rentPrice) {
		this.rentPrice = rentPrice;
	}
	public String getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}
	public String getBrokeragefee() {
		return brokeragefee;
	}
	public void setBrokeragefee(String brokeragefee) {
		this.brokeragefee = brokeragefee;
	}
	public String getRentAreaDescription() {
		return rentAreaDescription;
	}
	public void setRentAreaDescription(String rentAreaDescription) {
		this.rentAreaDescription = rentAreaDescription;
	}
	
//	@Override
//	public String toString() {
//		return "id=" + id + ", createdDate=" + createdDate + ", name=" + name
//				+ ", numberOfBasement=" + numberOfBasement + ", address=" + address + ", managerName=" + managerName
//				+ ", managerPhone=" + managerPhone + ", floorArea=" + floorArea + ", rentalArea=" + rentalArea
//				+ ", rentTypes=" + rentTypes + ", rentPrice=" + rentPrice + ", serviceFee=" + serviceFee
//				+ ", brokeragefee=" + brokeragefee + ", rentAreaDescription=" + rentAreaDescription;
//	}
	
	
}
