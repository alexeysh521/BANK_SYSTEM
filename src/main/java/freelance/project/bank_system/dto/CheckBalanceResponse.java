package freelance.project.bank_system.dto;

import java.math.BigDecimal;

public record CheckBalanceResponse(
       BigDecimal balance
) {}
