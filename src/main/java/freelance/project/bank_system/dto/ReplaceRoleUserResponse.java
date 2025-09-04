package freelance.project.bank_system.dto;

import freelance.project.bank_system.enums.RolesType;

import java.util.UUID;

public record ReplaceRoleUserResponse(
        UUID id,
        RolesType role,
        String message
) {}
