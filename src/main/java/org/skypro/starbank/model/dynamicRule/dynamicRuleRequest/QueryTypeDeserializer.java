package org.skypro.starbank.model.dynamicRule.dynamicRuleRequest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Arrays;

/**
 * Кастомный десериализатор для преобразования JSON-значений в элементы перечисления {@link QueryType}.
 * <p>
 * Обеспечивает обработку строковых значений и их преобразование в соответствующие значения перечисления,
 * а также обработку ошибок при некорректных значениях.
 */
public class QueryTypeDeserializer extends StdDeserializer<QueryType> {

    /**
     * Конструктор по умолчанию.
     * Указывает, что десериализатор работает с типом {@link QueryType}.
     */
    public QueryTypeDeserializer() {
        super(QueryType.class);
    }

    /**
     * Выполняет десериализацию JSON-значения в элемент перечисления {@link QueryType}.
     *
     * @param p парсер JSON, предоставляющий доступ к данным
     * @param ctxt контекст десериализации
     * @return значение перечисления {@link QueryType}, соответствующее строке в JSON,
     *         или {@code null}, если получено null-значение
     * @throws IOException если возникает ошибка ввода/вывода при чтении JSON
     * @throws IllegalArgumentException если переданное значение не соответствует ни одному элементу перечисления
     */
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
