package freelance.project.bank_system.enums;

public enum RolesType {
    USER, ADMIN, MANAGER;

    public static RolesType getRoleType(String role){
        try{
            return RolesType.valueOf(role.toUpperCase().trim());
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Invalid role status.");
        }
    }

    public static String fromString(RolesType role){
        try{
            return role.name();
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Invalid role status.");
        }
    }
}
