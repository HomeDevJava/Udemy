package com.cursospring.app;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.cursospring.app.auth.handler.LoginSuccessHandler;
import com.cursospring.app.models.service.JpaUserDetailsService;

@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired private LoginSuccessHandler successHandler;
	@Autowired DataSource dataSource;
	@Autowired BCryptPasswordEncoder passwordEncoder;
	@Autowired private JpaUserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/* AUTENTICACION EN MEMORIA---------------------------------------------------------
		 PasswordEncoder encoder= PasswordEncoderFactories.createDelegatingPasswordEncoder();
		 UserBuilder users= User.builder().passwordEncoder(encoder::encode);
		
		 auth.inMemoryAuthentication()
		.withUser(users.username("admin").password("123456").roles("ADMIN", "USERS"))
		.withUser(users.username("abel").password("123456").roles("USERS"))
		.passwordEncoder(encoder);
		*/
		
		/* AUTENTICACION USANDO JDBC----------------------------------------------------------
		 auth.jdbcAuthentication()
		.dataSource(dataSource)
		.passwordEncoder(passwordEncoder)
		.usersByUsernameQuery("select username, password, enabled from users where username=?")
		.authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on(a.user_id=u.id) where u.username=?");
		*/
		
		//AUTENTICACION CON JPA ---------------------------------------------------------------
		
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder);
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/listar**","/locale","/api**" ).permitAll()
		/*  comentamos ya que habilitamos la seguridad por medio de anotaciones @secured en los Controllers
		 .antMatchers("/ver/**").hasAnyRole("USERS")		 
		.antMatchers("/upload").hasAnyRole("USERS")
		.antMatchers("/form/**").hasAnyRole("ADMIN")
		.antMatchers("/eliminar/**").hasAnyRole("ADMIN")
		.antMatchers("/factura/**").hasAnyRole("ADMIN")*/
		.anyRequest().authenticated()
		.and()
		.formLogin().successHandler(successHandler).loginPage("/login").permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/error_403");		
	}
	
}