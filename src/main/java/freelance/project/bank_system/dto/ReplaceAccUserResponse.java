package freelance.project.bank_system.dto;

import java.util.UUID;

public record ReplaceAccUserResponse(
        UUID user_id,
        UUID account_id,
        String message
)
{}
