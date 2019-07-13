package br.com.restWithSpringBoot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import br.com.restWithSpringBoot.exception.UnsuporteOperationException;
import br.com.restWithSpringBoot.model.Person;

@Service
public class PersonService {

	private final AtomicLong counter = new AtomicLong();
	
	private List<Person> persons = new ArrayList<>();
	private List<Person> listToUpdate = new ArrayList<>();
	private Person per = new Person();
	
	public List<Person> listAllPerson(){
		return this.persons;
	}
	
	public Person save(Person person) {
		person.setId(counter.addAndGet(1));
		persons.add(person);
		return person;
	}
	
	public Person update(Person person, Long id) {
		per = persons.stream().filter(p -> id == p.getId()).findAny().orElse(null);
		if(per == null) {
			throw new UnsuporteOperationException("Person not found");
		}else {
			person.setId(id);
			per = person;
			listToUpdate = new ArrayList<>();
			persons.forEach(p -> {
				if(p.getId() == id) {
					p = per;
				}
				listToUpdate.add(p);
			});
			updateList(listToUpdate);
		}
		return per;
	}
	
	private void updateList(List<Person> listToUpdate) {
		persons = listToUpdate;
		
	}

	public Person getPersonById(Long id) {
		per = persons.stream().filter(p -> id == p.getId()).findAny().orElse(null);
		if(per == null) {
			throw new UnsuporteOperationException("Person not found");
		}
		return per;
	}
	
	
}
