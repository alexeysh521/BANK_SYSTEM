package freelance.project.bank_system.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UUIDIdOperationRequest(
        @NotNull(message = "This field must not be empty") UUID id
) {}
