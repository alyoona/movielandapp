package com.stroganova.movielandapp.entity.jsonserializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.DecimalFormat;

public class DoubleSerializer extends StdSerializer<Double> {

    public DoubleSerializer() {
        super(Double.class);
    }


    @Override
    public void serialize(Double aDouble, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        jsonGenerator.writeString(decimalFormat.format(aDouble).replace(",","."));
    }




}
