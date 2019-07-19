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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "Service book", description = "End points of CRUD book", tags = {"Service book"})
@RestController
@RequestMapping("/api/v1/book")
public class BookController {

	@Autowired
	private BookService service;
	
	@ApiOperation(value ="Find all book")
	@GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
	public ResponseEntity<List<BookVO>> getAllBook() {
		List<BookVO> books = this.service.listAllPerson();
		books.forEach(b -> b.add(linkTo(methodOn(BookController.class).getBookById(b.getKey())).withSelfRel()));
		return ResponseEntity.status(HttpStatus.OK).body(books);
	}
	
	@ApiOperation(value ="Find book by id")
	@GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
	public ResponseEntity<BookVO> getBookById(@ApiParam(value="code of book") @PathVariable("id") Long id) {
		BookVO book = this.service.getPersonById(id);
		book.add(linkTo(methodOn(BookController.class).getBookById(id)).withSelfRel());
		return ResponseEntity.status(HttpStatus.OK).body(book);
	}
	
	@ApiOperation(value ="Create book")
	@PostMapping(produces = {"application/json", "application/xml", "application/x-yaml"}, consumes = {"application/json", "application/xml", "application/x-yaml"})
	public ResponseEntity<BookVO> saveBook(@RequestBody BookVO book) {
		BookVO bookVO = this.service.save(book);
		book.add(linkTo(methodOn(BookController.class).getBookById(bookVO.getKey())).withSelfRel());
		return ResponseEntity.status(HttpStatus.OK).body(bookVO);
	}
	
	@ApiOperation(value ="Create book")
	@PutMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"}, consumes = {"application/json", "application/xml", "application/x-yaml"})
	public ResponseEntity<BookVO> updateBook(@ApiParam(value="code of book") @PathVariable("id") Long id, @RequestBody BookVO book) {
		BookVO bookVO = this.service.update(book, id);
		book.add(linkTo(methodOn(BookController.class).getBookById(bookVO.getKey())).withSelfRel());
		return ResponseEntity.status(HttpStatus.OK).body(bookVO);
	}
	
	@ApiOperation(value ="Delete book by id")
	@DeleteMapping("/{id}")
	public ResponseEntity<BookVO> deleteBook(@ApiParam(value="code of book") @PathVariable("id") Long id) {
		this.service.delete(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
