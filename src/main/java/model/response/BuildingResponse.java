package model.response;

import java.util.Date;

public class BuildingResponse {
	private Long id;
	private Date createdDate;
	private String name;
	private Integer numberOfBasement;
	private String address;
	private String managerName;
	private String managerPhoneNumber;
	private Integer floorArea;
	private String rentalArea;
	private String rentTypes;
	private Integer rentPrice;
	private String serviceFee;
//	private phi mg;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
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
	public String getManagerPhoneNumber() {
		return managerPhoneNumber;
	}
	public void setManagerPhoneNumber(String managerPhoneNumber) {
		this.managerPhoneNumber = managerPhoneNumber;
	}
	public Integer getFloorArea() {
		return floorArea;
	}
	public void setFloorArea(Integer floorArea) {
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
	public Integer getRentPrice() {
		return rentPrice;
	}
	public void setRentPrice(Integer rentPrice) {
		this.rentPrice = rentPrice;
	}
	public String getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}
	
	
}
