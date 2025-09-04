package freelance.project.bank_system.dto;


import freelance.project.bank_system.enums.UserStatusType;

import java.util.UUID;

public record ReplaceStatusUserResponse(
        UUID id,
        UserStatusType status,
        String message
)
{}
