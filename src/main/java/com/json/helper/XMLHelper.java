package com.json.helper;

import com.json.bean.DataBean;
import com.json.converter.XMLtoJsonConverter;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by oksdud on 13.06.2016.
 */
@Slf4j
public class XMLHelper {

    public static DataBean unmarshal(final String nameXMLFile) throws JAXBException {
        File file = new File(nameXMLFile);
        JAXBContext jaxbContext = JAXBContext.newInstance(DataBean.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        DataBean payment = (DataBean) jaxbUnmarshaller.unmarshal(file);
        System.out.println("payment.AutoDeposit" + payment.AutoDeposit);
        System.out.println("payment.additionalData" +
                (payment.additionalData != null ? payment.additionalData.sender : "") + "\n"
                + (payment.additionalData != null ? payment.additionalData.data : ""));
        return payment;
    }

    public static DataBean dataFromXML(String path) throws Exception {
        DataBean dataBean = null;
        Unmarshaller unmarshaller = XMLHelper.unmarshal(DataBean.class);
        dataBean = (DataBean) unmarshaller.unmarshal(new File(path));

        //xml-string in file for sender
        if (
                (
                        dataBean.additionalData.sender == null
                                ||
                                dataBean.additionalData.sender.trim().isEmpty()
                )
                        &&
                        !JsonHelper.isJson(dataBean.additionalData.sender)
                ) {

            String additionalData = XMLtoJsonConverter.getJsonFromXMLFileByKey(path, "AdditionalData");
            String sender = JsonHelper.getPartJsonStringFromJsonStringByKey(additionalData, "Sender");
            dataBean.additionalData.sender = sender;
            System.out.println("!!" + dataBean.additionalData.sender);
            log.debug("dataFromXML" + dataBean.additionalData.sender);

        }
        //xml-string in file for data
        if (
                (
                        dataBean.additionalData.data == null
                                ||
                                dataBean.additionalData.data.trim().isEmpty()
                )
                        &&
                        !JsonHelper.isJson(dataBean.additionalData.data)
                ) {
            String additionalData = XMLtoJsonConverter.getJsonFromXMLFileByKey(path, "AdditionalData");
            String data = JsonHelper.getPartJsonStringFromJsonStringByKey(additionalData, "Data");

            dataBean.additionalData.data = data;
            System.out.println("!!!" + dataBean.additionalData.data);
            log.debug("!dataFromXML" + dataBean.additionalData.data);

        }

        return dataBean;
    }

    public static Unmarshaller unmarshal(Class clazz) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return jaxbUnmarshaller;
    }

}
