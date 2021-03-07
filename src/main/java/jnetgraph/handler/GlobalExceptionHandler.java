package jnetgraph.handler;

import jnetgraph.dto.ErrorResponse;
import jnetgraph.exception.MeasuringException;
import jnetgraph.exception.SpeedtestCLIProcessingException;
import jnetgraph.exception.UserAdministrationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
* This class is responsible for executing code when and if a
* specific exception or exceptions occur. Think of it like
* a listener for errors.
*/

// Allows you to handle exceptions across the whole application, not just to an individual controller
@ControllerAdvice

// @RestController is a convenience annotation for creating Restful controllers. It is a specialization of @Component
// and is autodetected through classpath scanning. It adds the @Controller and @ResponseBody annotations. It converts
// the response to JSON or XML.
@RestController

// TODO: So is the @RestController annotation used to serialize an object and return it as a JSON when and if a specific
// exception occurs?

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
