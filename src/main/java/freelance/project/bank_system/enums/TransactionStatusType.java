package freelance.project.bank_system.enums;

public enum TransactionStatusType {

    SUCCESSFULLY,
    PROCESSING,
    UNSUCCESSFULLY;

    public static TransactionStatusType getTranStatType(String type){
        try{
            return TransactionStatusType.valueOf(type.toUpperCase().trim());
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Invalid transaction status status");
        }
    }
}
