package org.skypro.starbank.dynamicRule;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "request_types")
public class RequestType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "query_type", nullable = false)
    private QueryType query;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "rule_arguments",
            joinColumns = @JoinColumn(name = "request_type_id"))
    @Column(name = "argument")
    private List<String> arguments = new ArrayList<>();

    @Column(name = "negate", nullable = false)
    private boolean negate;

    public RequestType() {
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, query);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestType other)) return false;
        return Objects.equals(id, other.id) &&
                query == other.query;
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
