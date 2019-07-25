package br.com.restWithSpringBoot.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.restWithSpringBoot.security.jwt.JwtConfigure;
import br.com.restWithSpringBoot.security.jwt.JwtTokeProvider;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	

	@Autowired
	private JwtTokeProvider jwtTokeProvider;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.httpBasic().disable()
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.authorizeRequests()
				.antMatchers("/api/v1/auth/**", "/api/v1/file/**","/api-docs/**", "/swagger-ui.html**").permitAll()
				.antMatchers("/api/**").authenticated()
				//.antMatchers("/api/**").denyAll()
				.and()
				.apply(new JwtConfigure(jwtTokeProvider));
				
	}
	
}
