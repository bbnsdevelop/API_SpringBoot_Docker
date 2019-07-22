package br.com.restWithSpringBoot.service;

import static br.com.restWithSpringBoot.mapper.MapperDozer.parseObject;
import static br.com.restWithSpringBoot.mapper.MapperDozer.parseListObjects;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.restWithSpringBoot.exception.ResourceOperationException;
import br.com.restWithSpringBoot.mapper.UserVO;
import br.com.restWithSpringBoot.model.Permission;
import br.com.restWithSpringBoot.model.User;
import br.com.restWithSpringBoot.repositories.UserRepository;
import br.com.restWithSpringBoot.security.AccountCredentialsVO;
import br.com.restWithSpringBoot.security.jwt.JwtTokeProvider;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokeProvider jwtTokeProvider;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = userRepository.findByUsername(username);		
		return user.orElseThrow(() -> new UsernameNotFoundException("username " +username+" not found"));
	}
	
	public Map<Object, Object> sigin(AccountCredentialsVO data){
		Map<Object, Object> model = new HashMap<>();
		var username = data.getUsername();
		var password = data.getPassword();
		
		try {
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			var user = this.userRepository.findByUsername(username);
			var token = "";
			user.orElseThrow(() -> new UsernameNotFoundException("Username " + username +" not found"));
			token = this.jwtTokeProvider.createToken(user.get().getUsername(), user.get().getRoles());
			model.put("username", user.get().getUsername());
			model.put("token", token);			
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid username/password suplied");
		}
		
		return model;
	}
	
	
	public UserVO save(UserVO user) {
		setupInitial(user);
		var roles = parseListObjects(user.getPermissions(), Permission.class);
		var entity = parseObject(user, User.class);
		entity.setPermissions(roles);
		return parseObject(userRepository.save(entity), UserVO.class);
	}
	
	private void setupInitial(UserVO user) {
		user.setAccountNomExpired(false);
		user.setAccountNonLocked(false);
		user.setCredentialsNonExpired(false);
		user.setEnabled(true);
		
	}

	public UserVO update(UserVO user, Long id) {
		this.userRepository.findById(id).orElseThrow(() -> new ResourceOperationException("Person not found id =" + id));
		user.setKey(id);
		var entity = parseObject(user, User.class);
		return parseObject(userRepository.save(entity), UserVO.class);
	}
	
	
	
}
