package jnetgraph.exception;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public class SpeedtestCLIProcessingException extends RuntimeException {
    private String code;


    public SpeedtestCLIProcessingException(String code, String message) {
        super(message);
        this.code = code;

    }

    public SpeedtestCLIProcessingException(String code, String message, Throwable cause ) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
