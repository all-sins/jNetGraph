package jnetgraph.handler;

import jnetgraph.dto.ErrorResponse;
import jnetgraph.exception.MeasuringException;
import jnetgraph.exception.SpeedtestCLIProcessingException;
import jnetgraph.exception.UserAdministrationException;
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

    @ExceptionHandler(value = {SpeedtestCLIProcessingException.class})
    public ErrorResponse handleSpeedtestCLIProcessingException(SpeedtestCLIProcessingException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setCode(ex.getCode());
        error.setMessage(ex.getMessage());
        error.setOperation("Error encounter during running Ookla speedtest and/or acquiring data received!");
        return error;
    }

    @ExceptionHandler(value = {UserAdministrationException.class})
    public ErrorResponse handleUserAdministrationException(UserAdministrationException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setCode(ex.getCode());
        error.setMessage(ex.getMessage());
        error.setOperation("Error encounter during registering/editing/locating user entries!");
        return error;
    }

}
