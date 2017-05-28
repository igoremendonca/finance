package br.com.jiva.finance.web.exception;

import br.com.jiva.finance.commons.exception.FinanceException;
import com.google.common.collect.ImmutableMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Map<String, String>> processMethodArgumentNotValidException (MethodArgumentNotValidException ex) {
        FieldError error = ex.getBindingResult().getFieldError();
        return getErrorMapResponseEntity(error);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Map<String, String>> processBindException (BindException e) {
        return null;
    }

    @ExceptionHandler(FinanceException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ResponseEntity<Map<String, String>> processFinanceException (FinanceException e) {
        return getResponseEntity(e.getExceptionCode().getCode(), e.getMessage(), HttpStatus.valueOf(e.getExceptionCode().getStatus().name()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ResponseEntity<Map<String, String>> processException (Exception e) {
        return getResponseEntity(e.getMessage(), null, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private ResponseEntity<Map<String, String>> getErrorMapResponseEntity(FieldError error) {
        return new ResponseEntity<Map<String, String>>(ImmutableMap.of("errorCode", error.getCode(), "field", error.getField()), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Map<String, String>> getResponseEntity(String code, String message, HttpStatus status) {
        return new ResponseEntity<Map<String, String>>(ImmutableMap.of("errorCode", code, "description", message), status);
    }


}
