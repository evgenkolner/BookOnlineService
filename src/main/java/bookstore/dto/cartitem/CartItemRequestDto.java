package bookstore.dto.cartitem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public record CartItemRequestDto(
        @Positive
        Long bookId,

        @Min(1)
        int quantity) {
}
