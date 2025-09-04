package freelance.project.bank_system.enums;

public enum AccountStatusType {

    ACTIVE,
    INACTIVE,
    BLOCKED,
    PREMIUM,
    MINOR,
    PENDING_VERIFICATION;

    public static AccountStatusType getAccStatus(String status){
        try{
            return AccountStatusType.valueOf(status.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid account status status");
        }
    }
}
