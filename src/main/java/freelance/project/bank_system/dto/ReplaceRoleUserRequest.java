package freelance.project.bank_system.dto;

import freelance.project.bank_system.enums.RolesType;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReplaceRoleUserRequest(
        @NotNull(message = "This field must not be empty") UUID id,
        @NotNull(message = "This field must not be empty") RolesType role
) {}
