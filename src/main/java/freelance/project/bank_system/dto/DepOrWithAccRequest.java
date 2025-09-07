package freelance.project.bank_system.dto;

import freelance.project.bank_system.enums.CurrencyType;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record DepOrWithAccRequest(
        @NotNull(message = "This field must not be empty") UUID account_id,
        @NotNull(message = "This field must not be empty") BigDecimal amount,
        @NotNull(message = "This field must not be empty") CurrencyType currency
) {}
