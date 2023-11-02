package bookstore.dto.user;

import bookstore.security.constraint.FieldMatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@FieldMatch(
        field = "password", fieldMatch = "repeatPassword", message = "Password is incorrect")
public record UserRegistrationRequestDto(
        @NotBlank
        @Size(min = 6, max = 60)
        String email,

        @NotBlank
        @Size(min = 6, max = 60)
        String firstName,

        @NotBlank
        @Size(min = 6, max = 60)
        String lastName,

        @NotBlank
        @Size(min = 6, max = 60)
        String password,

        @NotBlank
        @Size(min = 6, max = 60)
        String repeatPassword,

        @Size(min = 6, max = 255)
        String shippingAddress) {
}
