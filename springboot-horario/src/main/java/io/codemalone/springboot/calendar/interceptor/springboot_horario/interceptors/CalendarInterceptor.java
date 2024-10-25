package io.codemalone.springboot.calendar.interceptor.springboot_horario.interceptors;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CalendarInterceptor implements HandlerInterceptor {

    @Value("${config.calendar.open}")
    private Integer open;

    @Value("${config.calendar.close}")
    private Integer close;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //obtenemos una instancia de calendar y extraemos la hora
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        //validamos la hora, si esta en el rango establecido en el config.properties, retornamos true  
        if (hour >= open && hour < close) {
            //creamos un StringBuilder para construir el mensaje
            StringBuilder message = new StringBuilder("Bienvenidos al horario de atencion al cliente");
            message.append(", atendemos desde las ")
                    .append(open)
                    .append(" hrs. hasta las ")
                    .append(close)
                    .append("hrs");

            //agregamos el mensaje al request y retornamos true, en el lado del controller se obtiene el mensaje
            request.setAttribute("message", message.toString());
            return true;
        } else {  //si la hora no se encuentra en el rango establecido, retornamos un false
            //creamos un objeto mapper para devolver un json
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> body = new HashMap<>();

            //creamos un StringBuilder para construir el mensaje
            StringBuilder message = new StringBuilder("Cerrado, fuera del horario de atencion al cliente");
            message.append(", visitenos desde las ")
                    .append(open)
                    .append(" hrs, hasta las ")
                    .append(close)
                    .append(" hrs.");

            //agregamos el mensaje y fecha al Map, el objeto mapper convierte el Map a json y lo escribe en el response
            body.put("message", message.toString());
            body.put("time", new Date().toString());

            //escribimos el json en el response
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(mapper.writeValueAsString(body));

            // mapper.writeValue(response.getWriter(), body);
            return false;
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable ModelAndView modelAndView) throws Exception {

        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

}
