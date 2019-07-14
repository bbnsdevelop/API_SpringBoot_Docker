package br.com.restWithSpringBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.restWithSpringBoot.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

}
