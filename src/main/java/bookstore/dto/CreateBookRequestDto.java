package bookstore.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record CreateBookRequestDto(@NotNull String title,
                                   @NotNull String author,
                                   @NotNull @Size(min = 5, max = 10)
                                   String isbn,
                                   @NotNull @PositiveOrZero BigDecimal price,
                                   String description,
                                   String coverImage) {
}
