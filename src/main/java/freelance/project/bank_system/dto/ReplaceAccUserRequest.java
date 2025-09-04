package freelance.project.bank_system.dto;

import freelance.project.bank_system.enums.AccountStatusType;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReplaceAccUserRequest(
        @NotNull(message = "This field must not be empty") UUID user_id,
        @NotNull(message = "This field must not be empty") UUID acc_id,
        @NotNull(message = "This field must not be empty") AccountStatusType status
)
{}
