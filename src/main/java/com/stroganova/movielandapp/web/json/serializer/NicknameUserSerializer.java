package com.stroganova.movielandapp.web.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.stroganova.movielandapp.entity.User;

import java.io.IOException;

public class NicknameUserSerializer extends StdSerializer<User> {

    public NicknameUserSerializer() {
        super(User.class);
    }

    @Override
    public void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(user.getNickname());
    }
}
