package freelance.project.bank_system.dto;


import java.time.Instant;
import java.util.UUID;

public record RegisterResponseDto(
        String token,
        UUID id,
        String username,
        Instant timestamp
)
{}