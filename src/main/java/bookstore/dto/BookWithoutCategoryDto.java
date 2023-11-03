package bookstore.dto;

import java.math.BigDecimal;

public record BookWithoutCategoryDto(
        Long id,
        String title,
        String author,
        String isbn,
        BigDecimal price,
        String description,
        String coverImage
) {
}
