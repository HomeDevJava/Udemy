package io.codemalone.springboot.calendar.interceptor.springboot_horario.interceptors;

import java.util.Calendar;
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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour >= open && hour < close) {
            StringBuilder message = new StringBuilder("Bienmvenidos al horario de atencion al cliente");
            message.append(", atendemos desde las ")
                    .append(open)
                    .append(" a las ")
                    .append(close)
                    .append("horas \n");
            request.setAttribute("message", message.toString());
            return true;
        } else {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> body = new HashMap<>();

            StringBuilder message= new StringBuilder("Cerrado, fuera del horario de atencion al cliente");
            message.append(", visitenos desde las ")
                    .append(open)
                    .append(" hrs, hasta las ")
                    .append(close)
                    .append("vhoras \n");
            
            body.put("message", message.toString());

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(mapper.writeValueAsString(body));

            //mapper.writeValue(response.getWriter(), body);
            return false;
        }

    }


    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable ModelAndView modelAndView) throws Exception {

        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

}
