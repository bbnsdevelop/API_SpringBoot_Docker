package br.com.restWithSpringBoot.controllers;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.restWithSpringBoot.mapper.PersonVO;
import br.com.restWithSpringBoot.service.PersonService;

@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {
	
	@Autowired
	private PersonService service;
	
	@GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
	public ResponseEntity<List<PersonVO>> getAllPerson() {
		List<PersonVO> persons = this.service.listAllPerson();
		persons.forEach(p -> p.add(linkTo(methodOn(PersonController.class).getPersonById(p.getKey())).withSelfRel()));
		return ResponseEntity.status(HttpStatus.OK).body(persons);
	}
	
	@GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
	public ResponseEntity<PersonVO> getPersonById(@PathVariable("id") Long id) {
		PersonVO person = this.service.getPersonById(id);
		person.add(linkTo(methodOn(PersonController.class).getPersonById(id)).withSelfRel());
		return ResponseEntity.status(HttpStatus.OK).body(person);
	}
	
	@PostMapping(produces = {"application/json", "application/xml", "application/x-yaml"}, consumes = {"application/json", "application/xml", "application/x-yaml"})
	public ResponseEntity<PersonVO> savePerson(@RequestBody PersonVO person) {
		PersonVO personVo = this.service.save(person);
		person.add(linkTo(methodOn(PersonController.class).getPersonById(personVo.getKey())).withSelfRel());
		return ResponseEntity.status(HttpStatus.OK).body(personVo);
	}
	
	@PutMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"}, consumes = {"application/json", "application/xml", "application/x-yaml"})
	public ResponseEntity<PersonVO> updatePerson(@PathVariable("id") Long id, @RequestBody PersonVO person) {
		PersonVO personVo = this.service.update(person, id);
		person.add(linkTo(methodOn(PersonController.class).getPersonById(personVo.getKey())).withSelfRel());
		return ResponseEntity.status(HttpStatus.OK).body(personVo);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<PersonVO> deletePerson(@PathVariable("id") Long id) {
		this.service.delete(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	

}
