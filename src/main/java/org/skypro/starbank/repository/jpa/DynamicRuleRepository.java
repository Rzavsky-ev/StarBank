package org.skypro.starbank.repository.jpa;

import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.DynamicRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA-репозиторий для работы с динамическими правилами в базе данных.
 *
 * <p>Обеспечивает стандартные CRUD-операции для сущности {@link DynamicRule}
 * через наследование от {@link JpaRepository}. Поддерживает все базовые методы
 * работы с данными, включая сохранение, поиск, обновление и удаление правил.</p>
 *
 * <p>Использует Long в качестве типа идентификатора сущности DynamicRule.</p>
 */
@Repository
public interface DynamicRuleRepository extends JpaRepository<DynamicRule, Long> {

}
