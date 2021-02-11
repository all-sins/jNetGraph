package jnetgraph.handler;

import jnetgraph.dto.ErrorResponse;
import jnetgraph.exception.MeasuringException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

// No clue what these tags mean honestly. Besides the one use-case
// where @RestController means that it's the controller responsible
// for different HTTP requests to the application.
@ControllerAdvice
@RestController

// This class is responsible for executing code when and if a
// specific exception ŗor exceptions occur. Think of it like
// a listener for errors.
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {MeasuringException.class})
    public ErrorResponse handleMeasuringException(MeasuringException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setCode(ex.getCode());
        error.setMessage(ex.getMessage());
        error.setOperation("Measuring operation encountered an error!");
        return error;
    }

}
