package licenta.project.Struct;

public enum Roles {
    ADMIN("A"), USER("U");

    private final String code;

    Roles(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
