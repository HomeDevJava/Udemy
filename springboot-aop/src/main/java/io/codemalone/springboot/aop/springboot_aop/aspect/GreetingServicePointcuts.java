package io.codemalone.springboot.aop.springboot_aop.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/* Esta clase se encarga de desaclopar los puntos de corte de los métodos en GreetingAspect */

@Component
@Aspect 
public class GreetingServicePointcuts {

    
    //creamos un punto de corte para reutilizarla en otros métodos
    @Pointcut("execution(* io.codemalone.springboot.aop.springboot_aop.services.GreetingServiceImpl.*(..))")
    public void greetingLoggerPointcut() {}
}
