package com.json.helper;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.json.bean.DataBean;
import com.json.serializer.AdditionalDataSerializer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.StringWriter;

/**
 * Created by oksdud on 20.06.2016.
 */
public class HelperSerialization {
    /*
       example result {"additionalData":{{"sender":{"old":"15","name":"Mitchell Slater"}},{"data":{"carColor":"red","carName":"opel"}}}}
    */
    public static String getJsonStringAdditionalData(DataBean data) {
        try {
            if (data != null) {
                if (data.additionalData != null) {
                    ObjectMapper mapper = new ObjectMapper();
                    SimpleModule simpleModule = new SimpleModule("SimpleModule",
                            new Version(1, 0, 0, null));
                    simpleModule.addSerializer(DataBean.class,
                            new AdditionalDataSerializer());

                    mapper.registerModule(simpleModule);
                    StringWriter writer = new StringWriter();
                    mapper.writeValue(writer, data);
                    String result = writer.toString();
                    return result;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return null;
    }
    public static Boolean checkExistKey(String jsonStrAdditionalData, String key) {
        if (jsonStrAdditionalData != null) {
            if (!jsonStrAdditionalData.isEmpty()) {
                if(jsonStrAdditionalData.contains(key)){
                    String value=getPartAdditionalDataByKeyFromJsonObject(jsonStrAdditionalData, key);
                    if(value!=null && !value.isEmpty()) return true;
                }
            }
        }
        return false;
    }

    /*
        you must transform jsonStrAdditionalData into array
        {"additionalData":[{"sender":{"old":"15","name":"Mitchell Slater"}},{"data":{"carColor":"red","carName":"opel"}}]}
        get such result {"sender":{"old":"15","name":"Mitchell Slater"}}
    */
    public static String getPartAdditionalDataByKeyFromArray(String jsonStrAdditionalData, String key) {
        try {
            if (jsonStrAdditionalData != null) {
                if (!jsonStrAdditionalData.isEmpty()) {
                    JSONObject objectMain = new JSONObject(jsonStrAdditionalData);
                    JSONArray array = (JSONArray) objectMain.get("additionalData");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject objSender = (JSONObject) array.get(i);
                        StringWriter writer2 = new StringWriter();
                        objSender.write(writer2);
                        String resultByKey = writer2.toString();
                        if (resultByKey.contains(key)) {
                            return resultByKey;
                        }
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return null;
    }

    /*
        you must transform jsonStrAdditionalData into array
        {"additionalData":{"sender":{"old":"15","name":"Mitchell Slater"},"data":{"carColor":"red","carName":"opel"}}}
        get such result {"old":"15","name":"Mitchell Slater"}
    */
    public static String getPartAdditionalDataByKeyFromJsonObject(String jsonStrAdditionalData, String key) {
        try {
            if (jsonStrAdditionalData != null) {
                if (!jsonStrAdditionalData.isEmpty()) {
                    JSONObject objectMain = new JSONObject(jsonStrAdditionalData);
                    JSONObject object = (JSONObject) objectMain.get("additionalData");

                    JSONObject objSender = (JSONObject) object.get(key);
                    StringWriter writer2 = new StringWriter();
                    objSender.write(writer2);
                    String resultByKey = writer2.toString();
                    return resultByKey;

                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return null;
    }
/*
    jsonStr example {"old":"15","name":"Mitchell Slater"}
    key "old" or "name"
*/
    public static String getValueByKey(String jsonStr, String key) {
        try {
            if (jsonStr != null) {
                if (!jsonStr.isEmpty()) {
                    JSONObject objectMain = new JSONObject(jsonStr);
                    String recipient = (String) objectMain.get(key);
                    System.out.println("resultByKey = " + recipient);
                    return recipient;
                }
            }
        } catch (Exception ex) {
            System.out.println("ex = " + ex.getMessage());
            return null;
        }
        return null;
    }

}
