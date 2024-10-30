package io.codemalone.springboot.aop.springboot_aop;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/* Este aspecto se encarga de registrar el log de ejecución de un método
 * se debe anotar con @Aspect y @Component
 * 
 * la anotacion @Before se encarga de registrar el log antes de ejecutar el método
 * utiliza expresiones regulares para seleccionar el método
 */

@Component
@Aspect
public class GreetingAspect {

    private Logger logger = LoggerFactory.getLogger(GreetingAspect.class);

    @Before("execution(* io.codemalone.springboot.aop.springboot_aop.services.IGreeting.sayHello(..))")
    public void beforeGreeting(JoinPoint joinPoint) {
        
        logger.info("before greeting");
        String method= joinPoint.getSignature().getName();
        logger.info("Method invoked: " + method);

        //1 forma de obtener los argumentos
        /* Object[] args = joinPoint.getArgs();
        String argsString = Stream.of(args).map(Object::toString).collect(Collectors.joining(", "));
        logger.info("Arguments passed: " + args); */
        
        //2a forma de obtener los argumentos utilizando stream y Arrays
       /*  String argumentos= Arrays.stream(joinPoint.getArgs()).map(Object::toString).collect(Collectors.joining(", "));
        logger.info("Arguments passed: " + argumentos); */
        
        //forma sencilla de obtener los argumentos
        String argsString = Arrays.toString(joinPoint.getArgs());
        logger.info("Arguments passed: " + argsString);
    }

}
