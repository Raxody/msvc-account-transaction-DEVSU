package com.devsu.msvc_account_movement.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.http.HttpStatus;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String ERROR = "Error:";
    private static final String HTTP_STATUS = "HttpStatus";
    private static final String DESCRIPTION = "Description";

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
        Map<String, Object> bodyResponse = Map.of(ERROR, ex.getCodes().getCode(),
                HTTP_STATUS, ex.getCodes().getHttpStatus(),
                DESCRIPTION + ": ", ex.getMessage());
        return new ResponseEntity<>(bodyResponse, ex.getCodes().getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        Map<String, Object> bodyResponse = Map.of(ERROR, "00000",
                HTTP_STATUS, HttpStatus.INTERNAL_SERVER_ERROR,
                DESCRIPTION + ": ", ex.getMessage());
        return new ResponseEntity<>(bodyResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}