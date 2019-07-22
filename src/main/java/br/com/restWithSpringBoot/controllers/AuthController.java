package br.com.restWithSpringBoot.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	
	@ApiOperation(value ="Create book")
	@PostMapping(value ="/sigin", produces = {"application/json", "application/xml", "application/x-yaml"}, consumes = {"application/json", "application/xml", "application/x-yaml"})
	public ResponseEntity<Map<Object, Object>> sigin(@RequestBody AccountCredentialsVO data) {
		return ResponseEntity.status(HttpStatus.OK).body(this.userService.sigin(data));
	}
	

}
