package bookstore.dto.user;

import bookstore.security.constraint.FieldMatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@FieldMatch(
        field = "password", fieldMatch = "repeatPassword", message = "Password is incorrect")
public record UserRegistrationRequestDto(
        @NotBlank
        @Size(min = 6, max = 255)
        String email,

        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @NotBlank
        @Size(min = 6, max = 255)
        String password,

        @NotBlank
        @Size(min = 6, max = 255)
        String repeatPassword,
        String shippingAddress) {
}
