package io.codemalone.springboot.aop.springboot_aop.aspect;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/* Este aspecto se encarga de registrar el log de ejecución de un método
 * se debe anotar con @Aspect y @Component
 * 
 * la anotacion @Before se encarga de registrar el log antes de ejecutar el método
 * utiliza expresiones regulares para seleccionar el método
 */

@Order(1)
@Component
@Aspect
public class GreetingAspect {

    private Logger logger = LoggerFactory.getLogger(GreetingAspect.class);

    private void log(JoinPoint joinPoint) {
        String metodo = new Throwable().getStackTrace()[1].getMethodName();
        logger.info("------log execution of: " + metodo + "------");
        logger.info("Method invoked: " + joinPoint.getSignature().getName());
        logger.info("Class invoked: " + joinPoint.getThis().getClass().getNestHost().getSimpleName());
    
        String argsString = Arrays.toString(joinPoint.getArgs());
        logger.info("Arguments passed in "+ metodo + ": " + argsString);
    }

    @Before("GreetingServicePointcuts.greetingLoggerPointcut()")
    public void beforeGreeting(JoinPoint joinPoint) {
        log(joinPoint);
        
        //logger.info("------Before Execution------"+ Thread.currentThread().getStackTrace()[1].getMethodName());
        //String method= joinPoint.getSignature().getName();
        //logger.info("Class invoked in Before: " + joinPoint.getSignature().getDeclaringTypeName());
        //logger.info("Method invoked in Before: " + method);
        
        //forma sencilla de obtener los argumentos
        //String argsString = Arrays.toString(joinPoint.getArgs());
        //logger.info("Arguments passed in Before: " + argsString);
    }
    

    
    //el método afterReturning se ejecuta cuando el método finaliza con éxito
    @AfterReturning("GreetingServicePointcuts.greetingLoggerPointcut()")
    public void afterReturningGreeting(JoinPoint joinPoint) {
        log(joinPoint);
        /* logger.info("------afterReturning Execution ok------");
        logger.info("Class invoked in AfterReturning: " + joinPoint.getSignature().getDeclaringTypeName());
        logger.info("Method invoked in AfterReturning: " + joinPoint.getSignature().getName());
        
        //3a forma de obtener los argumentos
        String argsString = Stream.of(joinPoint.getArgs()).map(Object::toString).collect(Collectors.joining(", "));
        logger.info("Arguments passed in AfterReturning: " + argsString); */
    }

    
    //el método afterThrowing se ejecuta cuando se produce una excepción
    @AfterThrowing("GreetingServicePointcuts.greetingLoggerPointcut()")
    public void afterThrowingGreeting(JoinPoint joinPoint) {
        log(joinPoint);
       /*  logger.info("------afterThrowing Error------");
        logger.info("Class invoked: " + joinPoint.getSignature().getDeclaringTypeName());
        logger.info("Method invoked: " + joinPoint.getSignature().getName());
        
        //forma
        String argsString =Arrays.toString(joinPoint.getArgs());
        logger.info("Arguments passed: " + argsString); */
    }

    
    //el método after se ejecuta cuando el método finaliza
    @After("GreetingServicePointcuts.greetingLoggerPointcut()")
    public void afterGreeting(JoinPoint joinPoint) {
        log(joinPoint);
       /*  logger.info("------after greeting-----");
        logger.info("Class invoked in After: " + joinPoint.getSignature().getDeclaringTypeName());
        
        
        //2a forma de obtener los argumentos utilizando stream y Arrays
        String argumentos= Arrays.stream(joinPoint.getArgs()).map(Object::toString).collect(Collectors.joining(", "));
        logger.info("Arguments passed in After: " + argumentos); */
    }

    
    //el método around se ejecuta antes y después del método envolviendo el punto de corte Before y After
    @Around("GreetingServicePointcuts.greetingLoggerPointcut()")
    public Object aroundGreeting(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("------Around Execution------");
        logger.info("Class invoked: " + joinPoint.getSignature().getDeclaringTypeName());
        logger.info("Method invoked: " + joinPoint.getSignature().getName());
        Object result = null;
        try {
            result = joinPoint.proceed();
            String argsString = Arrays.toString(joinPoint.getArgs());
            logger.info("Argumentos pasados: " + argsString);
            logger.info("Result of method Around: " + result.toString());
            return result;
        } catch (Throwable e) {
            logger.error("Error en la llamada del metodo: " + e.getMessage());           
        }
        
       return result;
    }

}
