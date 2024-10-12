package io.codemalone.springboot.app.interceptors.springboot_interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*creamos la clase para configurar los interceptores que implementa la interfaz WebMvcConfigurer
 * sobrescribiendo el metodo addInterceptors.
 * 
 * esta clase debe estar anotada con @Configuration para que Spring sepa que es una clase de configuracion
 */

@Configuration
public class AppConfig implements WebMvcConfigurer {

    /*Creamos una variable de tipo HandlerInterceptor y le agregamos el calificador @Qualifier para inyectar la clase LoadingTimeInterceptor 
     * ya que en el contenedor de spring existen varios interceptores y podria marcar error si 
    */
    @Autowired
    @Qualifier("loadingTimeInterceptor")
    private HandlerInterceptor loadingTimeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       
        /* agregamos las rutas que queremos interceptar */
        //registry.addInterceptor(loadingTimeInterceptor).addPathPatterns("/api/bar","/api/tar");

        /* agregamos la rutas que no queremos interceptar */
        registry.addInterceptor(loadingTimeInterceptor).excludePathPatterns("/api/bar","/api/tar");
    }

    
}
