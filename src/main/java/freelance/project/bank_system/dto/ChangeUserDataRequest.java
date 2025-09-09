package freelance.project.bank_system.dto;

import java.time.LocalDateTime;

public record ChangeUserDataRequest(
        String firstName,
        String averageName,
        String lastName,
        String email,
        String passportSeriesAndNumber,
        LocalDateTime dateOfBirth
) {}
