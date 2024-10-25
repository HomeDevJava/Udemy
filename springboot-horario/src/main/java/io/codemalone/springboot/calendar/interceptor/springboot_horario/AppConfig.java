package io.codemalone.springboot.calendar.interceptor.springboot_horario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer{

    //inyectamos el interceptor
    @Autowired
    private HandlerInterceptor calendarInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registramos el interceptor y las rutas
       registry.addInterceptor(calendarInterceptor).addPathPatterns( "/","/**");
    }

    
}
