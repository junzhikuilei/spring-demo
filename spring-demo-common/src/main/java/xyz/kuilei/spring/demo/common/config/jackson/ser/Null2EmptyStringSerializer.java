package xyz.kuilei.spring.demo.common.config.jackson.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class Null2EmptyStringSerializer extends JsonSerializer<Object> {
    public static final Null2EmptyStringSerializer instance = new Null2EmptyStringSerializer();

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(StringUtils.EMPTY);
    }
}
