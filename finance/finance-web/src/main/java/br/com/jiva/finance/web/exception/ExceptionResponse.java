package br.com.jiva.finance.web.exception;


import lombok.Builder;

@Builder
public class ExceptionResponse {

    private String code;
    private String message;
}
