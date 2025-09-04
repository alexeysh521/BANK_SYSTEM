package freelance.project.bank_system.enums;

public enum CurrencyType {

    USD, EUR, GBP, JPY, RUB, CHF, CAD, AUD, CNY;

    public static CurrencyType getCurrType(String type){
        try{
            return CurrencyType.valueOf(type.toUpperCase().trim());
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Invalid currency status");
        }
    }

}
