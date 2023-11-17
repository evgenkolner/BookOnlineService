package bookstore.dto.shoppingcart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ShoppingCartRequestDto(
        @NotNull @Positive int quantity) {
}
