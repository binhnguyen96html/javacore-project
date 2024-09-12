package com.binhnguyen.ex_1.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TypeConverterUtils {

    public static String typeConverter(String types) {
        // Split the string by comma, trim each part, and collect them into a list
        List<String> typeList = Arrays.stream(types.split(","))
                                      .map(String::trim)
                                      .collect(Collectors.toList());
        
//        System.out.println("typeList: " + typeList);
        

        StringBuilder convertedTypes = new StringBuilder();
        

        for (String type : typeList) {
            if (type.equals("tang_tret")) {
                convertedTypes.append("Tầng trệt");
            } else if (type.equals("nguyen_can")) {
                convertedTypes.append("Nguyên Căn");
            } else if (type.equals("noi_that")) {
                convertedTypes.append("Nội thất");
            } else {
                convertedTypes.append(type);
            }
            
            if (typeList.indexOf(type) != typeList.size() - 1) {
                convertedTypes.append(", ");
            }
        }
        
//        System.out.println("convertedTypes: " + convertedTypes);
        
        return convertedTypes.toString();
    }
}
