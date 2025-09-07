package freelance.project.bank_system.dto;


import java.time.Instant;

public record LoginResponse(
       String token,
       String role,
       String message,
       Instant timestamp
) {}
