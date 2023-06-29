package s3.algorithalliance.domain;

public enum Role {
    USER(1);
    private final int index;
    Role(int index){ this.index = index; }
    public int getValue(){ return index; }
    public static Role getRole(int value){

        return USER;
    }
}
