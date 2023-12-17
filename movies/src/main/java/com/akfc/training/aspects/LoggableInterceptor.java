package com.akfc.training.aspects;

import io.micronaut.aop.InterceptorBean;
import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@InterceptorBean(Loggable.class)
public class LoggableInterceptor implements MethodInterceptor<Object, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggableInterceptor.class);


    @Override
    public Object intercept(MethodInvocationContext<Object, Object> context) {
        //Object p = context.getParameterValueMap().values().stream().findFirst();
        LOGGER.info(context.getMethodName() + " is being called");
        Object res = context.proceed();
        LOGGER.info(context.getMethodName() + " has been called");
        return res;
    }
}
