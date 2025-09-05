package freelance.project.bank_system.dto;

import freelance.project.bank_system.enums.CurrencyType;
import jakarta.validation.constraints.NotNull;

public record NewAccountRequest(
        @NotNull(message = "This field must not be empty") CurrencyType currency
) {}
