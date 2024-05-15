package xyz.kuilei.spring.demo.common.config;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import xyz.kuilei.spring.demo.common.config.jackson.deser.StringTrimDeserializer;
import xyz.kuilei.spring.demo.common.config.jackson.ser.ClobToStringSerializer;

import java.math.BigDecimal;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * @author JiaKun Xu, 2023-05-24 10:35
 */
@Configuration
public class HttpMessageConverterConfig {
    @Bean
    public MappingJackson2HttpMessageConverter demoHttpMessageConverter() {
        final ObjectMapper mapper = new ObjectMapper();

        mapper // JSON 字符串某个属性在 Java POJO 对象中未定义时，不抛异常
               .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
               // 日期格式转换
               .setDateFormat(new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN))
               .setTimeZone(TimeZone.getTimeZone("GMT+8"));

        final SimpleModule module = new SimpleModule();

        /* 接口输入 */
        module // String trim()
               .addDeserializer(String.class, StringTrimDeserializer.instance);

        /* 接口输出 */
        module // long, Long -> String
               .addSerializer(Long.TYPE, ToStringSerializer.instance)
               .addSerializer(Long.class, ToStringSerializer.instance)
               // BigDecimal -> String
               .addSerializer(BigDecimal.class, ToStringSerializer.instance)
               // Clob -> String
               .addSerializer(Clob.class, ClobToStringSerializer.instance);

        mapper.registerModule(module);

        // null -> ""
        // SerializerProvider provider = mapper.getSerializerProvider();
        // provider.setNullValueSerializer(Null2EmptyStringSerializer.instance);

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(mapper);
        return converter;
    }
}
