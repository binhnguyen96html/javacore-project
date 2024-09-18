package com.example.exercise_1.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.exercise_1.constant.SystemConstant;
import com.example.exercise_1.enums.BuildingTypeEnums;

public class BuildingTypeUtil {
	
//	public static Map<String, String> initBuildingType(){
//		Map<String, String> results = new HashMap<>();
//		results.put(SystemConstant.TANG_TRET_TYPE_CODE, SystemConstant.TANG_TRET_NAME);
//		results.put(SystemConstant.NGUYEN_CAN_TYPE_CODE, SystemConstant.NGUYEN_CAN_NAME);
//		results.put(SystemConstant.NOI_THAT_TYPE_CODE, SystemConstant.NOI_THAT_NAME);
//		
//		return results;
//	}

	public static String getType(String oldType) {
		List<String> newTypes = new ArrayList<>();

		//TRUONG HOP DUNG HASHMAP
		if (oldType != null) {
			for (String item : oldType.split(",")) {
				newTypes.add(BuildingTypeEnums.valueOf(item.trim()).getValue());			
			}
			return String.join(", ", newTypes);
		}
		
		return null;
		
		
		//TRUONG HOP DUNG HASHMAP
		/*
		if (oldType != null) {
			for (String item : oldType.split(",")) {
				Map<String, String> mapType = initBuildingType();
				String code = mapType.get(item.trim());
				newTypes.add(code);				
			}
			return String.join(", ", newTypes);
		}
		
		return null;
		*/
		
		
		//TRUONG HOW DUNG SWTICH
		/*
		if (oldType != null) {
			for (String item : oldType.split(",")) {

				switch (item) {
				
				case SystemConstant.TANG_TRET_TYPE_CODE: {
					newTypes.add(SystemConstant.TANG_TRET_NAME);
					break;
				}

				case SystemConstant.NOI_THAT_TYPE_CODE: {
					newTypes.add(SystemConstant.NOI_THAT_NAME);
					break;
				}

				case SystemConstant.NGUYEN_CAN_TYPE_CODE: {
					newTypes.add(SystemConstant.NGUYEN_CAN_NAME);
					break;
				}

				//default:
					//return null;
				}
			}
			return String.join(", ", newTypes);
		}
			return null;
		*/
		
		
		//TRUONG HOP DUNG IF- ELSE 
		/*
		 * if(oldType != null) { for(String item: oldType.split(",")) {
		 * if(item.equals(SystemConstant.TANG_TRET_TYPE_CODE)) {
		 * newTypes.add(SystemConstant.TANG_TRET_NAME); //continue; }else
		 * if(item.equals("noi_that")) { newTypes.add("nội thất"); }else
		 * if(item.equals("nguyen_can")) { newTypes.add("nguyên căn"); } } return
		 * String.join(",", newTypes); }
		 * 
		 * return null;
		 */

	}
	
	

	
}









