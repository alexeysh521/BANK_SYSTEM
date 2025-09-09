package freelance.project.bank_system.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AddDataRequest(
        @NotNull(message = "This field must not be empty") String firstName,
        @NotNull(message = "This field must not be empty") String averageName,
        @NotNull(message = "This field must not be empty") String lastName,
        @NotNull(message = "This field must not be empty") String email,
        @NotNull(message = "This field must not be empty") String passportSeriesAndNumber,
        @NotNull(message = "This field must not be empty") LocalDateTime dateOfBirth
) {}
