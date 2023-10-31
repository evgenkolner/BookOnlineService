package bookstore.dto.user;

import bookstore.security.constraint.FieldMatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@FieldMatch(
        field = "password", fieldMatch = "repeatPassword", message = "Password is incorrect")
public class UserRegistrationRequest {
    @NotBlank
    @Size(min = 6, max = 50)
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Size(min = 6, max = 50)
    private String password;

    @NotBlank
    @Size(min = 6, max = 50)
    private String repeatPassword;
    private String shippingAddress;
}
