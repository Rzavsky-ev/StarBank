package org.skypro.starbank.model.recommendation;

import java.util.UUID;

public record RecommendationDTO(UUID id,
                                String name,
                                String description) {
}
