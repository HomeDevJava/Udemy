package io.codemalone.springboot.aop.springboot_aop;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
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

    //creamos un punto de corte para reutilizarla en otros métodos
    @Pointcut("execution(* io.codemalone.springboot.aop.springboot_aop.services.GreetingServiceImpl.*(..))")
    private void greetingLoggerPointcut() {}

    @Before("greetingLoggerPointcut()")
    public void beforeGreeting(JoinPoint joinPoint) {
        
        logger.info("------Before Execution------");
        String method= joinPoint.getSignature().getName();
        logger.info("Class invoked in Before: " + joinPoint.getSignature().getDeclaringTypeName());
        logger.info("Method invoked in Before: " + method);
        
        //forma sencilla de obtener los argumentos
        String argsString = Arrays.toString(joinPoint.getArgs());
        logger.info("Arguments passed in Before: " + argsString);
    }
    

    
    //el método afterReturning se ejecuta cuando el método finaliza con éxito
    @AfterReturning("greetingLoggerPointcut()")
    public void afterReturningGreeting(JoinPoint joinPoint) {
        logger.info("------afterReturning Execution ok------");
        logger.info("Class invoked in AfterReturning: " + joinPoint.getSignature().getDeclaringTypeName());
        logger.info("Method invoked in AfterReturning: " + joinPoint.getSignature().getName());
        
        //3a forma de obtener los argumentos
        String argsString = Stream.of(joinPoint.getArgs()).map(Object::toString).collect(Collectors.joining(", "));
        logger.info("Arguments passed in AfterReturning: " + argsString);
    }
    
    //el método afterThrowing se ejecuta cuando se produce una excepción
    @AfterThrowing("greetingLoggerPointcut()")
    public void afterThrowingGreeting(JoinPoint joinPoint) {
        logger.info("------afterThrowing Error------");
        logger.info("Class invoked: " + joinPoint.getSignature().getDeclaringTypeName());
        logger.info("Method invoked: " + joinPoint.getSignature().getName());
        
        //forma
        String argsString =Arrays.toString(joinPoint.getArgs());
        logger.info("Arguments passed: " + argsString);
    }
    
    //el método after se ejecuta cuando el método finaliza
    @After("greetingLoggerPointcut()")
    public void afterGreeting(JoinPoint joinPoint) {
        logger.info("------after greeting-----");
        logger.info("Class invoked in After: " + joinPoint.getSignature().getDeclaringTypeName());
        
        
        //2a forma de obtener los argumentos utilizando stream y Arrays
        String argumentos= Arrays.stream(joinPoint.getArgs()).map(Object::toString).collect(Collectors.joining(", "));
        logger.info("Arguments passed in After: " + argumentos);
    }
    
    //el método around se ejecuta antes y después del método envolviendo el punto de corte Before y After
    @Around("greetingLoggerPointcut()")
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
