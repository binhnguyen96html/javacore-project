package enums;

public enum DistrictsEnum {
	
	Q1("Quận 1"),
	Q2("Quận 2"),
	Q4("Quận 4");
	
	private final String districtValue;
	
	DistrictsEnum(String districtValue){
		this.districtValue = districtValue;
	}
	
	public String getDistrictValue() {
		return districtValue;
	}
}
