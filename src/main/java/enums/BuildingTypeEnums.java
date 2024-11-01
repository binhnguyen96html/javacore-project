package enums;

public enum BuildingTypeEnums {
	
    tang_tret("Tầng trệt", "tang-tret"),
    nguyen_can("Nguyên căn", "nguyen-can"),
    noi_that("Nội thất", "noi-that");

    private final String name;
    private final String code;

    BuildingTypeEnums(String name, String code) {

        this.name = name;
        this.code = code;

    }

    public String getTypeBuildingName() {
        return name;
    } 
    
    public String getTypeBuildingCode() {
    	return code;
    }

}
