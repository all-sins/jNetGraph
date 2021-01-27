package jnetgraph.exception;

public class MeasuringException extends RuntimeException {

    private String code;

    public MeasuringException(String code, String message) {
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
