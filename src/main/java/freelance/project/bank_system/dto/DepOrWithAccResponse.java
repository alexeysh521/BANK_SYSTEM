package freelance.project.bank_system.dto;

import freelance.project.bank_system.enums.CurrencyType;

import java.math.BigDecimal;

public record DepOrWithAccResponse(
        String message,
        BigDecimal balance,
        CurrencyType currency
) {}
