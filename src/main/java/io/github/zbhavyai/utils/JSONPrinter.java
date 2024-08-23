package io.github.zbhavyai.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JSONPrinter {

    private static final ObjectMapper _objectMapper = new ObjectMapper();

    static {
        _objectMapper.registerModule(new JavaTimeModule());
        _objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    private JSONPrinter() {
    }

    public static <T> String prettyPrint(final T value) {
        try {
            return _objectMapper.writeValueAsString(value);
        } catch (Throwable t) {
            return String.format("Serialization error: %s", t.getMessage());
        }
    }
}
