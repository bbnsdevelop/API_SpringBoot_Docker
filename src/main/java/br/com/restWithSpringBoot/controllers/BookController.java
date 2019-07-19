package br.com.restWithSpringBoot.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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

import br.com.restWithSpringBoot.mapper.BookVO;
import br.com.restWithSpringBoot.service.BookService;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {

	@Autowired
	private BookService service;
	
	@GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
	public ResponseEntity<List<BookVO>> getAllPerson() {
		List<BookVO> books = this.service.listAllPerson();
		books.forEach(b -> b.add(linkTo(methodOn(BookController.class).getBookById(b.getKey())).withSelfRel()));
		return ResponseEntity.status(HttpStatus.OK).body(books);
	}
	
	@GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
	public ResponseEntity<BookVO> getBookById(@PathVariable("id") Long id) {
		BookVO book = this.service.getPersonById(id);
		book.add(linkTo(methodOn(BookController.class).getBookById(id)).withSelfRel());
		return ResponseEntity.status(HttpStatus.OK).body(book);
	}
	
	@PostMapping(produces = {"application/json", "application/xml", "application/x-yaml"}, consumes = {"application/json", "application/xml", "application/x-yaml"})
	public ResponseEntity<BookVO> savePerson(@RequestBody BookVO book) {
		BookVO bookVO = this.service.save(book);
		book.add(linkTo(methodOn(BookController.class).getBookById(bookVO.getKey())).withSelfRel());
		return ResponseEntity.status(HttpStatus.OK).body(bookVO);
	}
	
	@PutMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"}, consumes = {"application/json", "application/xml", "application/x-yaml"})
	public ResponseEntity<BookVO> updateBook(@PathVariable("id") Long id, @RequestBody BookVO book) {
		BookVO bookVO = this.service.update(book, id);
		book.add(linkTo(methodOn(BookController.class).getBookById(bookVO.getKey())).withSelfRel());
		return ResponseEntity.status(HttpStatus.OK).body(bookVO);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<BookVO> deletePerson(@PathVariable("id") Long id) {
		this.service.delete(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
