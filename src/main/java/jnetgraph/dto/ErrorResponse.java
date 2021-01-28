package jnetgraph.dto;

public class ErrorResponse {

    private String message;
    private String code;
    private String operation;

    public ErrorResponse(String message, String code, String operation) {
        this.message = message;
        this.code = code;
        this.operation = operation;
    }

    // Empty constructor useful for instantiating empty
    // object and then setting values manually.
    public ErrorResponse() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
