package com.example.webscraper.utils;

import java.util.ArrayList;

public class ArrayUtils {

    public static String[] GetStringArray(ArrayList<String> arr)
    {
        // declaration and initialise String Array
        String str[] = new String[arr.size()];

        // ArrayList to Array Conversion
        for (int j = 0; j < arr.size(); j++) {

            // Assign each value to String array
            str[j] = arr.get(j);
        }
        return str;
    }
}
