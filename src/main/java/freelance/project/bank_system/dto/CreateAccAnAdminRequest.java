package freelance.project.bank_system.dto;

import freelance.project.bank_system.enums.CurrencyType;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateAccAnAdminRequest(
        @NotNull(message = "This field must not be empty") UUID user_id,
        @NotNull(message = "This field must not be empty") BigDecimal balance,
        @NotNull(message = "This field must not be empty") CurrencyType currency
){}
