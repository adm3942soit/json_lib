package com.json.serializer;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.json.bean.DataBean;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by oksdud on 17.06.2016.
 */
public class AdditionalDataSerializer extends JsonSerializer<DataBean> {

    @Override
    public void serialize(DataBean value, JsonGenerator jgen,
                          SerializerProvider provider) throws IOException,
            JsonProcessingException {
        if (value != null) {
            Map<String, Map<String, String>> mapAdditionalData= new HashMap();
            if (value.additionalData != null) {
                mapAdditionalData.put("sender", value.additionalData.senderToJsonMap());
            }
            if (value.additionalData.data != null) {
                mapAdditionalData.put("data", value.additionalData.dataToJsonMap());
            }
            jgen.writeStartObject();
            jgen.writeObjectField("additionalData", mapAdditionalData);
            jgen.writeEndObject();
        }
    }

}