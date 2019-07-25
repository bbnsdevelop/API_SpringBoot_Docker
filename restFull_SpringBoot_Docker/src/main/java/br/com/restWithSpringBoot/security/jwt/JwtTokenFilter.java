package br.com.restWithSpringBoot.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class JwtTokenFilter extends GenericFilterBean {
	
	@Autowired
	private JwtTokeProvider jwtTokeProvider;
	
	

	public JwtTokenFilter(JwtTokeProvider jwtTokeProvider) {
		this.jwtTokeProvider = jwtTokeProvider;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String token = this.jwtTokeProvider.resolveToken((HttpServletRequest) request);
		if(token != null && this.jwtTokeProvider.validateToken(token)) {
			Authentication auth = this.jwtTokeProvider.getAuthentication(token);
			if(auth != null) {
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		chain.doFilter(request, response);
	}

}
