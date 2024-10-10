package io.codemalone.springboot.app.interceptors.springboot_interceptors.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/* @author codemalone
 * Los interceptores se ejecutan antes y despues de la peticion para realizar alguna accion de validacion de credenciales
 * logs, redirecciones, calculos, etc.
 * Una clase interceptora debe implementar la interfaz HandlerInterceptor para utilizar los metodos preHandle y postHandle.
 * Debes anotarte con @Component para que Spring sepa que es una clase y se agregue al contenedor de Spring para poderla utilizar con DI.
 */

@Component("loadingTimeInterceptor")
public class LoadingTimeInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoadingTimeInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        logger.info("LoadingTimeInterceptor: preHandle --Entrando...");

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

        logger.info("LoadingTimeInterceptor: postHandle --Saliendo...");

    }

}
