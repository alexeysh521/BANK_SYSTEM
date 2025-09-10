package freelance.project.bank_system.dto;

import freelance.project.bank_system.enums.CurrencyType;
import freelance.project.bank_system.enums.TransactionStatusType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionInfoResponse(
        UUID fromAccountId,
        UUID toAccountId,
        BigDecimal amount,
        LocalDateTime creationDate,
        CurrencyType currency,
        TransactionStatusType transactionStatus
) {}
