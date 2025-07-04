-- Таблица для хранения динамических правил
CREATE TABLE dynamic_rules
(
    id           SERIAL PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    product_id   UUID         NOT NULL,
    product_text TEXT         NOT NULL
);

-- Таблица для хранения условий правил
CREATE TABLE request_types
(
    id              SERIAL PRIMARY KEY,
    query_type      VARCHAR(50) NOT NULL,
    negate          BOOLEAN     NOT NULL,
    dynamic_rule_id INTEGER     NOT NULL,
    FOREIGN KEY (dynamic_rule_id) REFERENCES dynamic_rules (id) ON DELETE CASCADE
);

-- Таблица для хранения аргументов условий
CREATE TABLE rule_arguments
(
    request_type_id INTEGER      NOT NULL,
    argument        VARCHAR(255) NOT NULL,
    argument_order  INTEGER      NOT NULL DEFAULT 0, -- порядок аргументов
    FOREIGN KEY (request_type_id) REFERENCES request_types (id) ON DELETE CASCADE
);

-- Таблица для хранения статистики срабатывания правил рекомендаций
CREATE TABLE dynamic_rules_counter
(
    id              SERIAL PRIMARY KEY,
    dynamic_rule_id INTEGER NOT NULL UNIQUE,
    counter         INTEGER NOT NULL DEFAULT 0,
    FOREIGN KEY (dynamic_rule_id) REFERENCES dynamic_rules (id) ON DELETE CASCADE
);

-- Таблица для хранения фамилии и имени клиента
CREATE TABLE clients
(
    chatId     BIGINT      NOT NULL PRIMARY KEY,
    id         UUID        NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL
);