package jnetgraph.exception;

public class UserAdministrationException extends RuntimeException {
    private String code;

    public UserAdministrationException(String code, String message) {
        super(message);
        this.code = code;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
