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

import br.com.restWithSpringBoot.model.Person;
import br.com.restWithSpringBoot.service.PersonService;

@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {
	
	@Autowired
	private PersonService service;
	
	@GetMapping
	public ResponseEntity<List<Person>> getAllPerson() {
		return ResponseEntity.status(HttpStatus.OK).body(this.service.listAllPerson());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Person> getPersonById(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(this.service.getPersonById(id));
	}
	
	@PostMapping
	public ResponseEntity<Person> savePerson(@RequestBody Person person) {
		return ResponseEntity.status(HttpStatus.OK).body(this.service.save(person));
	}
	@PutMapping("/{id}")
	public ResponseEntity<Person> updatePerson(@PathVariable("id") Long id, @RequestBody Person person) {
		return ResponseEntity.status(HttpStatus.OK).body(this.service.update(person, id));
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Person> deletePerson(@PathVariable("id") Long id) {
		this.service.delete(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	

}
