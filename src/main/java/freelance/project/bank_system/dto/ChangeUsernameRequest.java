package freelance.project.bank_system.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ChangeUsernameRequest(
        @Size(min = 5, max = 25, message = "The login must be between 5 AND 25 characters")
        @NotNull(message = "This field must not be empty") String newUsername
) {}
