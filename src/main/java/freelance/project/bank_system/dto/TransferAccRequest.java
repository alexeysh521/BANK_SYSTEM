package freelance.project.bank_system.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferAccRequest(
        @NotNull(message = "This field must not be empty") UUID fromAccId,
        @NotNull(message = "This field must not be empty") UUID toAccId,
        @NotNull(message = "This field must not be empty") BigDecimal amount
) {}
