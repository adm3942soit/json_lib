package com.json.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
//import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

/**
 * Created by oksdud on 21.06.2016.
 */
//@Slf4j
public class JsonHelper {

    /*
        transforming json-str to map<string, string>
    */
    public static Map<String, String> jsonStrToMap(String jsonStr) {
        Map<String, String> map = new HashMap<>();
        if (jsonStr == null || jsonStr.isEmpty()) {
            return map;
        }
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            JSONArray array = new JSONArray();
            Iterator iterator = jsonObj.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                JSONObject item = new JSONObject();
                item.put(key, jsonObj.get((String) key));
                array.put(item);
                map.put(key, (String) jsonObj.get((String) key));
            }
            return map;
        } catch (JSONException ex) {
            ex.printStackTrace();
            System.out.println("JSONException" + ex.getMessage());
        }
        return null;
    }

    /*
        transform json string into list of json nodes
    */
    public static List<JsonNode> transformFromJsonStr(String jsonStr) {
        List<JsonNode> pairList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();

        try {
            mapper.writeValue(writer, jsonStr);
            JsonNode root = mapper.readTree(writer.toString());
            return transformToListJsonNodeFromJsonNode(root);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return pairList;
    }
/*
    transform root json tree of node into list of json nodes
*/

    public static List<JsonNode> transformToListJsonNodeFromJsonNode(JsonNode root) {
        List<JsonNode> jsonNodes = new ArrayList<>();
        Iterator iterator = root.fields();
        while (iterator.hasNext()) {
            Map.Entry<String, JsonNode> entry = (Map.Entry<String, JsonNode>) iterator.next();
            ObjectNode pair = JsonNodeFactory.instance.objectNode();
            pair.put(entry.getKey(), entry.getValue());
            jsonNodes.add(pair);
        }
        return jsonNodes;

    }

    /*
        get part of json string by key
    */
    public static String getPartJsonStringFromJsonStringByKey(String jsonString, String key) {
        String resultValue = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            StringWriter stringWriter = new StringWriter();
            JSONObject jsonArray = new JSONObject(jsonString);
            if ((jsonArray.get(key)) instanceof JSONArray) {
                JSONArray array = (JSONArray) jsonArray.get(key);
                for (int i = 0; i < array.length(); i++) {
                    Object object = array.get(i);
                    objectMapper.writeValue(stringWriter, object);
                    String tmp = stringWriter.toString();
                    resultValue = tmp;
                    break;
                }
            } else if ((jsonArray.get(key)) instanceof JSONObject) {
                JSONObject object = (JSONObject) jsonArray.get(key);
                object.write(stringWriter);
                String tmp = stringWriter.toString();
                resultValue = tmp;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultValue;
    }

    /*
        transform json string to JSONArray
    */
    public static JSONArray jsonStrToJsonArray(String jsonStr) {
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            JSONArray array = new JSONArray();
            Iterator iterator = jsonObj.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                JSONObject item = new JSONObject();
                item.put(key, jsonObj.get((String) key));
                array.put(item);
            }
            return array;
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    /*
    transform JSONArray to json-string
    */
    public static String jsonArrayToJsonString(JSONArray jsonArray) {
        String jsonStr = "";
        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        try {
            objectMapper.writeValue(writer, jsonArray);

        } catch (IOException ex) {
            System.out.println("!!Exception transforming data from json " + ex.getMessage());
            return jsonStr;
        }
        jsonStr = writer.toString();
        System.out.println("!!data in json " + jsonStr);
        return jsonStr;
    }

    /*
       check if string is json string
    */
    /*
       check if string is json string
    */
    public static Boolean isJson(String str) {
        if (str == null || str.isEmpty()) return false;
        if (str.contains("{") && str.contains(":") && str.contains("}")) return true;
        else return false;
    }

    public static Boolean isStrJson(String jsonStr) {
        if (jsonStr == null || jsonStr.isEmpty() || !isJson(jsonStr)) return false;
        JSONArray jsonArray = jsonStrToJsonArray(jsonStr);
        if (jsonArray != null && jsonArray.length() != 0) return true;
        return false;
    }

}
