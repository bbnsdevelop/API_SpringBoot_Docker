package br.com.restWithSpringBoot.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.restWithSpringBoot.mapper.UserVO;
import br.com.restWithSpringBoot.security.AccountCredentialsVO;
import br.com.restWithSpringBoot.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Auth Service", description = "Endpoint to sigin", tags = {"Auth Service"})
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	
	@ApiOperation(value ="Authentication user")
	@PostMapping(value ="/signin", produces = {"application/json", "application/xml", "application/x-yaml"}, consumes = {"application/json", "application/xml", "application/x-yaml"})
	public ResponseEntity<Map<Object, Object>> sigin(@RequestBody AccountCredentialsVO data) {
		return ResponseEntity.status(HttpStatus.OK).body(this.userService.sigin(data));
	}
	
	@ApiOperation(value ="Create user")
	@PostMapping(value ="/register", produces = {"application/json", "application/xml", "application/x-yaml"}, consumes = {"application/json", "application/xml", "application/x-yaml"})
	public ResponseEntity<UserVO> save(@RequestBody UserVO user) {
		return ResponseEntity.status(HttpStatus.OK).body(this.userService.save(user));
	}
	
	@ApiOperation(value ="Update user")
	@PostMapping(value ="/update/{id}", produces = {"application/json", "application/xml", "application/x-yaml"}, consumes = {"application/json", "application/xml", "application/x-yaml"})
	public ResponseEntity<UserVO> update(@PathVariable("id") Long id, @RequestBody UserVO user) {
		return ResponseEntity.status(HttpStatus.OK).body(this.userService.update(user, id));
	}

}
