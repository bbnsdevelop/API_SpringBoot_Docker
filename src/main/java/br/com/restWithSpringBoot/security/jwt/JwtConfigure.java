package br.com.restWithSpringBoot.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfigure extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>{
	

	@Autowired
	private JwtTokeProvider jwtTokeProvider;

	public JwtConfigure(JwtTokeProvider jwtTokeProvider) {
		this.jwtTokeProvider = jwtTokeProvider;
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		JwtTokenFilter filter = new JwtTokenFilter(jwtTokeProvider);
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
	
	}
	
	

}
