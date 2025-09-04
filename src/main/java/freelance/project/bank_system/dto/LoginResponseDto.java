package freelance.project.bank_system.dto;


import java.time.Instant;

public record LoginResponseDto(
       String token,
       String role,
       String message,
       Instant timestamp
) {}
