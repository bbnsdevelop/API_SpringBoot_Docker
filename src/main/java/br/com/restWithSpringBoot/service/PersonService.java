package br.com.restWithSpringBoot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.restWithSpringBoot.exception.ResourceOperationException;
import br.com.restWithSpringBoot.model.Person;
import br.com.restWithSpringBoot.repositories.PersonRepository;

@Service
public class PersonService {
	
	@Autowired
	private PersonRepository personRepository;
	
	public List<Person> listAllPerson(){
		return this.personRepository.findAll();
	}
	
	public Person save(Person person) {
		return personRepository.save(person);
	}
	
	public Person update(Person person, Long id) {
		this.personRepository.findById(id).orElseThrow(() -> new ResourceOperationException("Person not found id =" + id));
		person.setId(id);
		return personRepository.save(person);
	}
	public void delete(Long id) {
		this.personRepository.deleteById(id);
	}
	
	public Person getPersonById(Long id) {
		return this.personRepository.findById(id).orElseThrow(() -> new ResourceOperationException("Person not found id =" + id));
	}
	
}
