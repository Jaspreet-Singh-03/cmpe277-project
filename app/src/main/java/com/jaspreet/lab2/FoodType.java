package com.jaspreet.lab2;

import java.util.Arrays;

public enum FoodType {
    INDIAN, KOREAN, JAPANESE, THAI, ITALIAN, MEXICAN, CHINESE, PACKAGED, OTHER;

    public static String[] names() {
        return Arrays.toString(FoodType.values()).replaceAll("^.|.$", "").split(", ");
    }
}
