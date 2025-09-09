package freelance.project.bank_system.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(
        @Size(min = 4, max = 40, message = "The password must be between 4 AND 40 characters")
        @NotNull(message = "This field must not be empty") String newPassword
) {}
