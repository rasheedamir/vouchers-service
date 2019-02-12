package com.tinyerp.gateway.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.tinyerp.gateway.util.BeanUtil;

import java.io.IOException;

public class SimpleWrapperSerializer extends JsonSerializer<SimpleWrapper> {

    @Override
    public void serialize(
            final SimpleWrapper wrapper,
            final JsonGenerator jsonGenerator,
            final SerializerProvider serializerProvider
    ) throws IOException {
        BeanUtil.getBean(ObjectMapper.class).writeValue(jsonGenerator, wrapper.get());
    }
}

