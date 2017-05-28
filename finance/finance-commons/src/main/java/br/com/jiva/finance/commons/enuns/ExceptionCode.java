package br.com.jiva.finance.commons.enuns;


public enum ExceptionCode {

    NOT_FOUND_OBJECT("Not_found", HttpStatus.NOT_FOUND),
    INVALID_OBJECT("Invalid_object", HttpStatus.BAD_REQUEST);

    ExceptionCode(String code, HttpStatus status) {
        this.code = code;
        this.status = status;
    }

    private final String code;
    private final HttpStatus status;

    public String getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
