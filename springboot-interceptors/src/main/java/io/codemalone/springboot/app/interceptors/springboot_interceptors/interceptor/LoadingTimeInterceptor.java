package io.codemalone.springboot.app.interceptors.springboot_interceptors.interceptor;

import java.util.Random;
import java.util.Map;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

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
        HandlerMethod controller = (HandlerMethod) handler;

        logger.info("LoadingTimeInterceptor: preHandle --Entrando..." + controller.getMethod().getName());

        /* creamos una variable para el tiempo de inicio y la guardamos en el request */
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        /* simulamos el tiempo de carga de la peticion */
        Thread.sleep(new Random().nextInt(500));

        /*
         * cuando el return es false, el controlador no se ejecuta, y el request no se
         * enviara al cliente. por lo tanto se realiza una respuesta de error.
         * Map<String, String> error = new HashMap<>();
         * error.put("message", "Error al intentar accesar a la peticion");
         * error.put("time", String.valueOf(startTime));
         * 
         * ObjectMapper mapper = new ObjectMapper();
         * 
         * response.setContentType("application/json");
         * response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
         * mapper.writeValue(response.getWriter(), error);
         */

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

        /* obtenemos el tiempo de inicio y lo guardamos en una variable */
        long startTime = (Long) request.getAttribute("startTime");

        /* obtenemos el tiempo de finalizacion y lo guardamos en una variable */
        long endTime = System.currentTimeMillis();
        /* calculamos el tiempo de ejecucion y lo guardamos en una variable */
        long totalTime = endTime - startTime;

        /* imprimimos el tiempo de ejecucion */
        logger.info("LoadingTimeInterceptor: postHandle --Total time: " + totalTime + "ms");

        logger.info(
                "LoadingTimeInterceptor: postHandle --Saliendo..." + ((HandlerMethod) handler).getMethod().getName());

    }

}
