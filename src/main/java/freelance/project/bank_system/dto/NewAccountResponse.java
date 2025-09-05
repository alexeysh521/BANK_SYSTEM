package freelance.project.bank_system.dto;

import java.util.UUID;

public record NewAccountResponse(
        UUID account_id,
        UUID user_id,
        String message
) {}
