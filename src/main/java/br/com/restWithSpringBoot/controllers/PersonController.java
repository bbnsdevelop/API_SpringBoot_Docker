package br.com.restWithSpringBoot.controllers;

import java.util.List;

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
	
	@GetMapping(produces = {"application/json", "application/xml"})
	public ResponseEntity<List<PersonVO>> getAllPerson() {
		return ResponseEntity.status(HttpStatus.OK).body(this.service.listAllPerson());
	}
	
	@GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
	public ResponseEntity<PersonVO> getPersonById(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(this.service.getPersonById(id));
	}
	
	@PostMapping(produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml"})
	public ResponseEntity<PersonVO> savePerson(@RequestBody PersonVO person) {
		return ResponseEntity.status(HttpStatus.OK).body(this.service.save(person));
	}
	@PutMapping(value = "/{id}", produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml"})
	public ResponseEntity<PersonVO> updatePerson(@PathVariable("id") Long id, @RequestBody PersonVO person) {
		return ResponseEntity.status(HttpStatus.OK).body(this.service.update(person, id));
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<PersonVO> deletePerson(@PathVariable("id") Long id) {
		this.service.delete(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	

}
