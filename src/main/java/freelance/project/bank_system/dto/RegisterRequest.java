package freelance.project.bank_system.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public record RegisterRequest(
    @NotNull(message = "The field must not be empty")
    @Pattern(regexp = "USER|ADMIN|MANAGER|BLOCKED", message = "Role must be one of: USER, ADMIN, MANAGER")
    String role,

    @NotNull(message = "The field must not be empty")
    @Size(min = 5, max = 25, message = "The login must be between 5 AND 25 characters")
    String username,

    @NotNull(message = "The field must not be empty")
    @Size(min = 4, max = 40, message = "The password must be between 4 AND 40 characters")
    String password
){}
