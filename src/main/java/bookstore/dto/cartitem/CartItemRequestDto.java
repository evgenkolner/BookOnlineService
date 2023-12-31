package bookstore.dto.cartitem;

import jakarta.validation.constraints.Positive;

public record CartItemRequestDto(
        @Positive
        Long bookId,

        @Positive
        int quantity) {
}
