package xyz.kuilei.spring.demo.common.config.jackson.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;

/**
 * @author JiaKun Xu, 2023-11-28 09:58:34
 */
public class ClobToStringSerializer extends StdSerializer<Clob> {
    public static final ClobToStringSerializer instance = new ClobToStringSerializer();

    public ClobToStringSerializer() {
        super(Clob.class);
    }

    /**
     * @see com.alibaba.fastjson.serializer.ClobSerializer
     */
    @Override
    public void serialize(Clob clob, JsonGenerator gen, SerializerProvider provider) throws IOException {
        StringBuilder buf = new StringBuilder();

        try (Reader reader = clob.getCharacterStream()) {
            char[] chars = new char[2048];

            while (true) {
                int len = reader.read(chars, 0, chars.length);
                if (len < 0) {
                    break;
                }

                buf.append(chars, 0, len);
            }
        } catch (IOException | SQLException ignored) {
        }

        gen.writeString(buf.toString());
    }
}
