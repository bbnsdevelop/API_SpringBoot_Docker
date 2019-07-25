package br.com.restWithSpringBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.restWithSpringBoot.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

}
