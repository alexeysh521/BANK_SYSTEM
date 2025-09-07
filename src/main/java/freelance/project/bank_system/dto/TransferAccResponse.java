package freelance.project.bank_system.dto;

import freelance.project.bank_system.enums.CurrencyType;

import java.math.BigDecimal;

public record TransferAccResponse(
       String message,
       BigDecimal balance,
       CurrencyType currency
) {}
