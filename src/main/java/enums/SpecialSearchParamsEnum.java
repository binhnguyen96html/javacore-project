package enums;

import constant.SystemConstant;

public enum SpecialSearchParamsEnum {
	
	   DISTRICT(SystemConstant.DISTRICT_CODE),
	    STAFF_ID(SystemConstant.STAFF_ID),
	    RENT_PRICE_FROM(SystemConstant.RENT_PRICE_FROM),
	    RENT_PRICE_TO(SystemConstant.RENT_PRICE_TO),
	    RENT_AREA_FROM(SystemConstant.RENT_AREA_FROM),
	    RENT_AREA_TO(SystemConstant.RENT_AREA_TO);

	    private final String value;

	    SpecialSearchParamsEnum(String value) {
	        this.value = value;
	    }

	    public String getValue() {
	        return value;
	    }
}
