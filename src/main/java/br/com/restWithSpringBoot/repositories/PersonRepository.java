package br.com.restWithSpringBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.restWithSpringBoot.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

	
	@Transactional(readOnly = false)
	@Modifying
	@Query("UPDATE Person p SET p.enabled = :enabled WHERE p.id =:id")
	int disableOrEnablePerson(@Param("id") Long id, @Param("enabled") Boolean enabled);
}
