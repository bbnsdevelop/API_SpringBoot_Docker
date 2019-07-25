package br.com.restWithSpringBoot.service;

import static br.com.restWithSpringBoot.mapper.MapperDozer.parseListObjects;
import static br.com.restWithSpringBoot.mapper.MapperDozer.parseObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.restWithSpringBoot.exception.ResourceOperationException;
import br.com.restWithSpringBoot.mapper.UserVO;
import br.com.restWithSpringBoot.model.Permission;
import br.com.restWithSpringBoot.model.User;
import br.com.restWithSpringBoot.repositories.PermissionRepository;
import br.com.restWithSpringBoot.repositories.UserRepository;
import br.com.restWithSpringBoot.security.AccountCredentialsVO;
import br.com.restWithSpringBoot.security.jwt.JwtTokeProvider;

@Service
public class UserService implements UserDetailsService{
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PermissionRepository permissionRepository;
	
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
			var user = this.userRepository.findByUsername(username);
			if(decryptPassword(password, user.get().getPassword())) {
				this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));				
			}else {
				throw new BadCredentialsException("Password is invalid");
			}
			var token = "";
			user.orElseThrow(() -> new UsernameNotFoundException("Username " + username +" not found"));
			token = this.jwtTokeProvider.createToken(user.get().getUsername(), user.get().getRoles());
			model.put("username", user.get().getUsername());
			model.put("token", token);			
		} catch (AuthenticationException e) {			
			throw new BadCredentialsException(e.getMessage());
		}
		
		return model;
	}
	
	
	public UserVO save(UserVO user) {
		user = setupInitial(user);
		var roles = parseListObjects(user.getPermissions(), Permission.class);
		var entity = parseObject(user, User.class);
		entity.setPermissions(roles);
		return parseObject(saveOrUpdate(entity), UserVO.class);
	}
	

	public UserVO update(UserVO user, Long id) {
		this.userRepository.findById(id).orElseThrow(() -> new ResourceOperationException("Person not found id =" + id));
		user.setKey(id);
		var entity = parseObject(user, User.class);
		var roles = parseListObjects(user.getPermissions(), Permission.class);
		entity.setPermissions(roles);
		return parseObject(saveOrUpdate(entity), UserVO.class);
	}
	
	private User saveOrUpdate(User user) {		
		List<Permission> permissions = this.permissionRepository.saveAll(user.getPermissions());
		user.setPermissions(permissions);
		user.setPassword(encodePassword(user.getPassword()));
		return this.userRepository.save(user);
	}
	private UserVO setupInitial(UserVO user) {
		user.setAccountNomExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setEnabled(true);
		return user;
	}
	
	private String encodePassword(String password) {
		logger.info("encoding Password completed");
		return this.passwordEncoder.encode(password);
	}
	private boolean decryptPassword(String rawPassword, String encodedPassword) {
		return this.passwordEncoder.matches(rawPassword, encodedPassword);
	}
	
}
