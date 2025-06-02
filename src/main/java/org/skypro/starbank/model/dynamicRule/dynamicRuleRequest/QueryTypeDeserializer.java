package org.skypro.starbank.model.dynamicRule.dynamicRuleRequest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Arrays;

public class QueryTypeDeserializer extends StdDeserializer<QueryType> {
    public QueryTypeDeserializer() {
        super(QueryType.class);
    }

    @Override
    public QueryType deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        if (p.getCurrentToken() == JsonToken.VALUE_NULL) {
            return null;
        }

        String value = p.getText().trim().toUpperCase();
        try {
            return QueryType.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Неизвестный тип запроса: '" + value +
                    "'. Доступные значения: " + Arrays.toString(QueryType.values()));
        }
    }
}
