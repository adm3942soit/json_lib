package com.json.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by oksdud on 21.06.2016.
 */
public class XMLtoJsonConverter {

    private static InputStream inputStream = null;

    public static String getJsonFromXMLFile(String nameXMLFile) {
        try {
            inputStream = new FileInputStream(nameXMLFile);
            String xml = IOUtils.toString(inputStream);
            JSON objJson = new XMLSerializer().read(xml);
            return String.valueOf(objJson);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public static String getJsonFromXMLString(String XMLString) {
        try {
            JSON objJson = new XMLSerializer().read(XMLString);
            return String.valueOf(objJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getJsonFromXMLStringByKey(String XMLString, String key) {
        String resultValue = "";
        try {
            JSON objJson = new XMLSerializer().read(XMLString);
            System.out.println("JSON data : " + objJson);
            ObjectMapper objectMapper = new ObjectMapper();
            String result = String.valueOf(objJson);
            JsonNode root = objectMapper.readTree(result);
            Iterator iterator = root.fields();
            while (iterator.hasNext()) {
                Map.Entry<String, JsonNode> entry = (Map.Entry<String, JsonNode>) iterator.next();
                ObjectNode pair = JsonNodeFactory.instance.objectNode();
                if (key.equals(entry.getKey())) {
                    pair.put(entry.getKey(), entry.getValue());
                    StringWriter stringWriter = new StringWriter();
                    objectMapper.writeValue(stringWriter, entry.getValue());
                    resultValue = stringWriter.toString();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultValue;
    }

    public static String getJsonFromXMLFileByKey(String nameXMLFile, String key) {
        String resultValue = "";
        try {
            inputStream = new FileInputStream(nameXMLFile);//url.openStream();
            String xml = IOUtils.toString(inputStream);

            JSON objJson = new XMLSerializer().read(xml);
            ObjectMapper objectMapper = new ObjectMapper();
            String result = String.valueOf(objJson);
            JsonNode root = objectMapper.readTree(result);
            Iterator iterator = root.fields();
            while (iterator.hasNext()) {
                Map.Entry<String, JsonNode> entry = (Map.Entry<String, JsonNode>) iterator.next();
                ObjectNode pair = JsonNodeFactory.instance.objectNode();
                if (key.equals(entry.getKey())) {
                    pair.put(entry.getKey(), entry.getValue());
                    StringWriter stringWriter = new StringWriter();
                    objectMapper.writeValue(stringWriter, entry.getValue());
                    resultValue = stringWriter.toString();
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return resultValue;
    }

}