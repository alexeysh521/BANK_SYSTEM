package freelance.project.bank_system.dto;

import freelance.project.bank_system.enums.UserStatusType;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReplaceUserStatusRequest(
        @NotNull(message = "This field must not be empty") UUID id,
        @NotNull(message = "This field must not be empty") UserStatusType status
){}
