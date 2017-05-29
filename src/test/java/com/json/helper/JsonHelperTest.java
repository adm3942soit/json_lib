package com.json.helper;

import com.json.bean.DataBean;
import com.json.converter.XMLtoJsonConverter;
import lombok.extern.slf4j.Slf4j;
import orderTest.Order;
import org.junit.Test;

import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Random;

/**
 * Created by oksdud on 27.06.2016.
 */
@Slf4j
public class JsonHelperTest {
    private static String jsonStr="{additionalData\":{\"data\":{\"carName\":\"opel\",\"cardColor\":\"red\"},\"sender\":{\"name\":\"Mitchell Slater\"}}}";
    private static String current;
    private static String resources;

    static {
        try {
            current = new File(".").getCanonicalPath();
            current=current.replaceAll("\\\\", "/");
            resources = current + "/src/test/resources";
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
//    @Test
//    @Order(order = 0)
    public void checkXMLToJsonConverter() {
        System.out.println(JsonHelper.isJson(jsonStr));
        String path = resources + File.separator + "data.xml";
        String xml = XMLtoJsonConverter.getJsonFromXMLFile(path);
        String additionalData = XMLtoJsonConverter.getJsonFromXMLFileByKey(path, "AdditionalData");
        String sender = JsonHelper.getPartJsonStringFromJsonStringByKey(additionalData, "Sender");
        String data = JsonHelper.getPartJsonStringFromJsonStringByKey(additionalData, "Data");
        System.out.println("checkXMLToJconConverter sender: " + sender);
        System.out.println("checkXMLToJconConverter data: " + data);
    }
    @Test
    @Order(order = 1)
    public void checkTransformingFromXML(){
        DataBean dataBean = prepareData();
        String resultJsonString = HelperSerialization.getJsonStringAdditionalData(dataBean);
        System.out.println("test1 " + resultJsonString);
        DataBean dataBean1 = prepareData();

        String result = recordPaymentAdditionalDataFromXML(dataBean1);
        System.out.println("payment.additionalData " + result);
/*
                    check if correct transform from json additional data sender
*/

        String sender = HelperSerialization.getPartAdditionalDataByKeyFromJsonObject(result, "sender");
        System.out.println("sender " + sender);


    }

    public DataBean data;
    private String nameXmlFile=resources+"/data.xml";
    public DataBean prepareData() {
        try {
            data = dataFromXML(nameXmlFile);
            StringBuffer id = new StringBuffer("");
            Random r = new Random(System.currentTimeMillis());
            for (int i = 0; i <= 24; i++) {
                Integer digit = r.nextInt(9);
                id.append(digit.toString().toCharArray()[0]);
            }
            data.Order.ID = id.toString();
            System.out.println("data.payment.mode " + data.Payment.Mode);
            System.out.println("data.additionalData.sender " + data.additionalData.sender);
            System.out.println("data.additionalData.data " + data.additionalData.data);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
      return data;
    }
    private DataBean dataFromXML(String path) {
        DataBean dataBean = null;
        try {
            Unmarshaller unmarshaller = XMLHelper.unmarshal(DataBean.class);
            dataBean = (DataBean) unmarshaller.unmarshal(new File(path));

            //xml-string in file for sender
            if ((dataBean.additionalData.sender == null || (dataBean.additionalData.sender.trim().isEmpty()))
                    && !JsonHelper.isJson(dataBean.additionalData.sender)
                    ) {

                String additionalData = XMLtoJsonConverter.getJsonFromXMLFileByKey(path, "AdditionalData");
                String sender = JsonHelper.getPartJsonStringFromJsonStringByKey(additionalData, "Sender");
                dataBean.additionalData.sender = sender;
                System.out.println("!!" + dataBean.additionalData.sender);

            }
            //xml-string in file for data
            if ((dataBean.additionalData.data == null || (dataBean.additionalData.data.trim().isEmpty()))
                    && !JsonHelper.isJson(dataBean.additionalData.data)
                    ) {
                String additionalData = XMLtoJsonConverter.getJsonFromXMLFileByKey(path, "AdditionalData");
                String data = JsonHelper.getPartJsonStringFromJsonStringByKey(additionalData, "Data");

                dataBean.additionalData.data = data;
                System.out.println("!!!" + dataBean.additionalData.data);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dataBean;
    }


    public String recordPaymentAdditionalDataFromXML(DataBean data) {
        String resultJsonString = null;
        if (data != null && data.additionalData != null) {
            try {
                resultJsonString = HelperSerialization.getJsonStringAdditionalData(data);
                log.debug("resultJsonString" + resultJsonString);
            } catch (Exception ex) {
                ex.printStackTrace();
                return resultJsonString;
            }
        }
        return resultJsonString;
    }

}
