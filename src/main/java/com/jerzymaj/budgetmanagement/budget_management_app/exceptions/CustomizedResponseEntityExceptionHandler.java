package com.jerzymaj.budgetmanagement.budget_management_app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllException(Exception ex, WebRequest request) throws Exception {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleUserNotFoundException(Exception ex, WebRequest request) throws Exception{

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MonthlyCostsNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleMonthlyCostsNotFoundException(Exception ex, WebRequest request)
            throws Exception{

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MonthlyCostsSummaryNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleMonthlyCostsSummaryNotFoundException(Exception ex, WebRequest request){

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MonthlyCostsSumNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleMonthlyCostsSumNotFoundException(Exception ex, WebRequest request){

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NetSalaryAfterCostsNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleNetSalaryAfterCostsNotFoundException(Exception ex, WebRequest request){

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
    }
}
