package br.com.restWithSpringBoot.service;

import static br.com.restWithSpringBoot.mapper.MapperDozer.parseObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.restWithSpringBoot.exception.ResourceOperationException;
import br.com.restWithSpringBoot.mapper.PersonVO;
import br.com.restWithSpringBoot.model.Person;
import br.com.restWithSpringBoot.repositories.PersonRepository;

@Service
public class PersonService {
	
	@Autowired
	private PersonRepository personRepository;
	
	
	public Page<PersonVO> listAllPerson(Pageable pageable){
		var page = this.personRepository.findAll(pageable);
		return page.map(this::convertToPersonVo);
	}
	
	public Page<PersonVO> listPersonByFirstName(String firstName, Pageable pageable){
		var page = this.personRepository.findByFirstName(firstName, pageable);
		return page.map(this::convertToPersonVo);
	}
	
	private PersonVO convertToPersonVo(Person entity) {
		return parseObject(entity, PersonVO.class);
	}
	
	public PersonVO save(PersonVO person) {
		var entity = parseObject(person, Person.class);
		return parseObject(personRepository.save(entity), PersonVO.class);
	}
	
	public PersonVO update(PersonVO person, Long id) {
		this.personRepository.findById(id).orElseThrow(() -> new ResourceOperationException("Person not found id =" + id));
		person.setKey(id);
		var entity = parseObject(person, Person.class);
		return parseObject(personRepository.save(entity), PersonVO.class);
	}
	public void delete(Long id) {
		this.personRepository.deleteById(id);
	}
	
	public PersonVO getPersonById(Long id) {
		return parseObject(this.personRepository.findById(id).orElseThrow(() -> new ResourceOperationException("Person not found id =" + id)), PersonVO.class);
	}

	public String disableOrEnablePerson(Boolean disable, Long id) {
		
		this.personRepository.findById(id).orElseThrow(() -> new ResourceOperationException("Person not found id =" + id));
		int row = this.personRepository.disableOrEnablePerson(id, disable);
		if(row >= 1 && disable) {
			return "Person has been successfully disabled"; 
		}else if(row >= 1 && !disable) {
			return "Person has been successfully enabled";
		}
		else {
			return "it was not possible to do the operation";
		}
	}
	
}
