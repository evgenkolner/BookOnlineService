package bookstore.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

public record CreateBookRequestDto(@NotNull String title,
                                   @NotNull String author,
                                   @NotNull @Size(min = 5, max = 10)
                                   String isbn,
                                   @NotNull @PositiveOrZero BigDecimal price,
                                   String description,
                                   String coverImage,

                                   @NotEmpty
                                   List<@Positive Long> categoryIds) {
}
