package freelance.project.bank_system.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ClosedAccRequest(
        @NotNull(message = "This field must not be empty") UUID account_id
) {}
