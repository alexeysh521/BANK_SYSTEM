package freelance.project.bank_system.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotNull(message = "The field must not be empty")
        @Size(min = 5, max = 25, message = "The login must be between 5 AND 25 characters")
        String username,

        @NotNull(message = "The field must not be empty")
        @Size(min = 4, max = 40, message = "The password must be between 4 AND 40 characters")
        String password
){}