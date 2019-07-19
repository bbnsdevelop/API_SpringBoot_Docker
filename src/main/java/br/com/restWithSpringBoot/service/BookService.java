package br.com.restWithSpringBoot.service;

import static br.com.restWithSpringBoot.mapper.MapperDozer.parseListObjects;
import static br.com.restWithSpringBoot.mapper.MapperDozer.parseObject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.restWithSpringBoot.exception.ResourceOperationException;
import br.com.restWithSpringBoot.mapper.BookVO;
import br.com.restWithSpringBoot.model.Book;
import br.com.restWithSpringBoot.repositories.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	
	public List<BookVO> listAllPerson(){
		return parseListObjects(this.bookRepository.findAll(), BookVO.class);
	}
	
	public BookVO save(BookVO book) {
		var entity = parseObject(book, Book.class);
		return parseObject(bookRepository.save(entity), BookVO.class);
	}
	
	public BookVO update(BookVO book, Long id) {
		this.bookRepository.findById(id).orElseThrow(() -> new ResourceOperationException("Book not found id =" + id));
		book.setKey(id);
		var entity = parseObject(book, Book.class);
		return parseObject(bookRepository.save(entity), BookVO.class);
	}
	public void delete(Long id) {
		this.bookRepository.deleteById(id);
	}
	
	public BookVO getPersonById(Long id) {
		return parseObject(this.bookRepository.findById(id).orElseThrow(() -> new ResourceOperationException("Book not found id =" + id)), BookVO.class);
	}
}
