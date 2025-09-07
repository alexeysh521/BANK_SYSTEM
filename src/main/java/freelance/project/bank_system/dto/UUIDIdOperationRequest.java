package freelance.project.bank_system.dto;

import java.util.UUID;

public record UUIDIdOperationRequest(
        UUID user_id,
        UUID account_id
) {}
