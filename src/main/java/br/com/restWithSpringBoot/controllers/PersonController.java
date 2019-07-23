package br.com.restWithSpringBoot.controllers;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.restWithSpringBoot.mapper.PersonVO;
import br.com.restWithSpringBoot.service.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "Service person", description = "End points of CRUD Person", tags = {"Service Peson"})
@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {
	
	@Autowired
	private PersonService service;
	
	@ApiOperation(value ="Find all person")
	@GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
	public ResponseEntity<List<PersonVO>> getAllPerson(
			@RequestParam(value="page", defaultValue ="0") int page, 
			@RequestParam(value="limit", defaultValue ="15") int limit,
			@RequestParam(value="onderby", defaultValue ="asc") String onderby) {
		var sortDirection = "desc".equalsIgnoreCase(onderby) ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection , "firstName"));
		List<PersonVO> persons = this.service.listAllPerson(pageable);
		persons.forEach(p -> p.add(linkTo(methodOn(PersonController.class).getPersonById(p.getKey())).withSelfRel()));
		return ResponseEntity.status(HttpStatus.OK).body(persons);
	}
	
	@ApiOperation(value ="Find person by id")
	@GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
	public ResponseEntity<PersonVO> getPersonById(@ApiParam(value="code of person") @PathVariable("id") Long id) {
		PersonVO person = this.service.getPersonById(id);
		person.add(linkTo(methodOn(PersonController.class).getPersonById(id)).withSelfRel());
		return ResponseEntity.status(HttpStatus.OK).body(person);
	}
	
	@ApiOperation(value ="Create person")
	@PostMapping(produces = {"application/json", "application/xml", "application/x-yaml"}, consumes = {"application/json", "application/xml", "application/x-yaml"})
	public ResponseEntity<PersonVO> savePerson(@RequestBody PersonVO person) {
		PersonVO personVo = this.service.save(person);
		person.add(linkTo(methodOn(PersonController.class).getPersonById(personVo.getKey())).withSelfRel());
		return ResponseEntity.status(HttpStatus.OK).body(personVo);
	}
	
	@ApiOperation(value ="Update person")
	@PutMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"}, consumes = {"application/json", "application/xml", "application/x-yaml"})
	public ResponseEntity<PersonVO> updatePerson(@ApiParam(value="code of person") @PathVariable("id") Long id, @RequestBody PersonVO person) {
		PersonVO personVo = this.service.update(person, id);
		person.add(linkTo(methodOn(PersonController.class).getPersonById(personVo.getKey())).withSelfRel());
		return ResponseEntity.status(HttpStatus.OK).body(personVo);
	}
	
	@ApiOperation(value ="Disable or enable the person")
	@PatchMapping(value = "/{id}/{disable}", produces = {"application/json", "application/xml", "application/x-yaml"}, consumes = {"application/json", "application/xml", "application/x-yaml"})
	public ResponseEntity<String> disableOrEnablePerson(@ApiParam(value="code of person") @PathVariable("id") Long id, @PathVariable("disable") Boolean disable) {
		return ResponseEntity.status(HttpStatus.OK).body(this.service.disableOrEnablePerson(disable, id));
	}
	
	
	@ApiOperation(value ="Delete person by id")
	@DeleteMapping("/{id}")
	public ResponseEntity<PersonVO> deletePerson(@ApiParam(value="code of person") @PathVariable("id") Long id) {
		this.service.delete(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	

}
