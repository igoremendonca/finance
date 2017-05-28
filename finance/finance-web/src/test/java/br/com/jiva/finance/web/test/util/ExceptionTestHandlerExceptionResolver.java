package br.com.jiva.finance.web.test.util;


import br.com.jiva.finance.web.exception.GlobalException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import java.lang.reflect.Method;

public class ExceptionTestHandlerExceptionResolver {

    public static ExceptionHandlerExceptionResolver create() {
        ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
            protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod, Exception exception) {
                try {
                    Method method = new ExceptionHandlerMethodResolver(GlobalException.class).resolveMethod(exception);
                    return new ServletInvocableHandlerMethod(new GlobalException(), method);

                }catch (Exception e){
                    return null;
                }
            }
        };
        exceptionResolver.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        exceptionResolver.afterPropertiesSet();
        return exceptionResolver;
    }
}
