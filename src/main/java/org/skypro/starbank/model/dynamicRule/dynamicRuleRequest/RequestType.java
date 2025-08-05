package org.skypro.starbank.model.dynamicRule.dynamicRuleRequest;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс, представляющий тип запроса для динамических правил.
 * Сохраняется в таблице "request_types" базы данных.
 */
@Data
@Entity
@Table(name = "request_types")
public class RequestType {

    /**
     * Уникальный идентификатор типа запроса.
     * Генерируется автоматически при сохранении в базу данных.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Тип запроса, представленный в виде перечисления {@link QueryType}.
     * Сохраняется в колонке "query_type" и не может быть null.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "query_type", nullable = false)
    private QueryType query;

    /**
     * Список аргументов для данного типа запроса.
     * Хранится в отдельной таблице "rule_arguments" с связью по полю "request_type_id".
     * Загружается сразу при загрузке сущности (FetchType.EAGER).
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "rule_arguments",
            joinColumns = @JoinColumn(name = "request_type_id"))
    @Column(name = "argument")
    private List<String> arguments = new ArrayList<>();

    /**
     * Флаг, указывающий на необходимость инвертировать результат проверки правила.
     * Хранится в колонке "negate" и не может быть null.
     */
    @Column(name = "negate", nullable = false)
    private boolean negate;

    public RequestType() {
    }

    @Override
    public String toString() {
        return "RequestType{" +
                "id=" + id +
                ", query='" + query + '\'' +
                ", arguments=" + (arguments != null ? arguments.toString() : "null") +
                '}';
    }
}
