package xyz.kuilei.spring.demo.common.config.jackson.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * @author JiaKun Xu, 2023-06-15 15:30
 */
public class StringTrimDeserializer extends StdDeserializer<String> {
    public static final StringTrimDeserializer instance = new StringTrimDeserializer();

    public StringTrimDeserializer() {
        super(String.class);
    }

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String text = p.getText();
        return (text == null) ? null : text.trim();
    }
}
