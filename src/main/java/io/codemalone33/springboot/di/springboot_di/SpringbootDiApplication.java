package io.codemalone33.springboot.di.springboot_di;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({
	@PropertySource("classpath:config.properties")
})
public class SpringbootDiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDiApplication.class, args);
	}

}
