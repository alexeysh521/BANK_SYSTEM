package freelance.project.bank_system.dto;

import freelance.project.bank_system.enums.AccountStatusType;
import freelance.project.bank_system.enums.CurrencyType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record DataAccountResponse(
    UUID account_id,
    UUID user_id,
    BigDecimal balance,
    CurrencyType currency,
    AccountStatusType status,
    LocalDateTime registration_date
) {}
