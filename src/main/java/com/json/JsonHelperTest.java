package com.json;

import com.json.helper.JsonHelper;

/**
 * Created by oksdud on 27.06.2016.
 */
public class JsonHelperTest {
    private static String jsonStr="{additionalData\":{\"data\":{\"carName\":\"opel\",\"cardColor\":\"red\"},\"sender\":{\"name\":\"Mitchell Slater\"}}}";
    public static void main(String[] args){
        System.out.println(JsonHelper.isJson(jsonStr));

    }
}
