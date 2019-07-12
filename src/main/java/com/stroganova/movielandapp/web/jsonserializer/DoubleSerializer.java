package com.stroganova.movielandapp.web.jsonserializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.DecimalFormat;

public class DoubleSerializer extends StdSerializer<Double> {

    private final static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    public DoubleSerializer() {
        super(Double.class);
    }


    @Override
    public void serialize(Double aDouble, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(DECIMAL_FORMAT.format(aDouble).replace(",","."));
    }




}
