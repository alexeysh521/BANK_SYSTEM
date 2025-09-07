package freelance.project.bank_system.dto;

import java.util.List;
import java.util.UUID;

public record ViewAllTranResponse(
        List<UUID> transactionId
)
{}
