package s3.algorithalliance.datalayer.entities;

public enum RoleEnum {
    USER(1);
    private final int index;
    RoleEnum(int index){ this.index = index; }
    public int getValue(){ return index; }
    public static RoleEnum getRole(int value){
        return USER;
    }
}
