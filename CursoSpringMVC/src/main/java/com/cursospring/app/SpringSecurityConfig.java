package com.cursospring.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
	//@Autowired
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder encoder= PasswordEncoderFactories.createDelegatingPasswordEncoder();
		UserBuilder users= User.builder().passwordEncoder(encoder::encode);
		
		
		auth.inMemoryAuthentication()
		.withUser(users.username("admin").password("123456").roles("ADMIN", "USERS"))
		.withUser(users.username("abel").password("123456").roles("USERS"))
		.passwordEncoder(encoder)
		.withUser("nakamura").password("123456").roles("USERS");
		
		
		//super.configure(auth);
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/listar" ).permitAll()
		.antMatchers("/ver/**").hasAnyRole("USERS")
		.antMatchers("/upload").hasAnyRole("USERS")
		.antMatchers("/form/**").hasAnyRole("ADMIN")
		.antMatchers("/eliminar/**").hasAnyRole("ADMIN")
		.antMatchers("/factura/**").hasAnyRole("ADMIN")
		.anyRequest().authenticated()
		.and()
		.formLogin().loginPage("/login").permitAll()
		.and()
		.logout().permitAll();
		//super.configure(http);
	}
	

}
