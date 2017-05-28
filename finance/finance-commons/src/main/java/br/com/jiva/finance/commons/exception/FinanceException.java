package br.com.jiva.finance.commons.exception;

import br.com.jiva.finance.commons.enuns.ExceptionCode;

/**
 * Created by igor on 25/05/17.
 */
public class FinanceException extends RuntimeException {

    private ExceptionCode exceptionCode;

    public FinanceException(ExceptionCode exceptionCode, String message) {
        super(message);
        this.exceptionCode = exceptionCode;
    }

    public FinanceException(String message, Throwable cause) {
        super(message, cause);
    }

    public FinanceException(Throwable cause) {
        super(cause);
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}
