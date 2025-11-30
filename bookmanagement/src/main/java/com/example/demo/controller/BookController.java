package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.AuthorNotFoundException;
import com.example.demo.exception.BookNotFoundException;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.model.Borrow;
import com.example.demo.service.AuthorService;
import com.example.demo.service.BookService;

import jakarta.validation.Valid;


@RestController("/api/v1")
public class BookController {
	
	@Autowired
	private BookService bookservice;
	
	@Autowired
	private AuthorService authorservice;
	
	
	@PostMapping("/add-book")
	public ResponseEntity<Book> addBook(@RequestBody @Valid Book book) {
		Book book1 = bookservice.addBook(book);
		return new ResponseEntity<>( book1, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/all-books")
	public ResponseEntity<Page<Book>> getAllBooks(
			@RequestParam(defaultValue="0")int page,
			@RequestParam(defaultValue="5") int size
			){
		Page<Book> all_books = bookservice.getAllBooks(page, size);
		return new ResponseEntity<>(all_books, HttpStatus.OK);	
	}
	
	@GetMapping("/book/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable Integer id) throws BookNotFoundException {
		Book book = bookservice.getBookById(id);
		if(book == null) {
			throw new BookNotFoundException("book "+id+" is not found in database");
		}
		return new ResponseEntity<>(book, HttpStatus.OK);
	}
	
	@DeleteMapping("/book/{id}")
	public ResponseEntity<String> deleteBookById(@PathVariable Integer id) {
		Boolean res = bookservice.getDeleteById(id);
		if(!res) return new ResponseEntity<>("book not found ", HttpStatus.NO_CONTENT);
		return new ResponseEntity<>("book is deleted ",HttpStatus.OK);
		
	}
	
	@PostMapping("/add-author")
	public Author addAuthor(@RequestBody @Valid Author author) {
		return authorservice.addAuthor(author);
	}

	@GetMapping("/all-authors")
	public ResponseEntity<Page<Author>> getAllAuthors(
			@RequestParam(defaultValue="0")int page,
				@RequestParam(defaultValue="5") int size) throws AuthorNotFoundException{
		Page<Author> all_authors =  authorservice.getAllAuthors(page, size);
		if(all_authors.isEmpty()) {
			System.out.println("No Authors");
			throw new AuthorNotFoundException("No Authors found in database");
		}
		return new ResponseEntity<>(all_authors, HttpStatus.OK);
	}
	
	@GetMapping("/borrowed-books")
	public ResponseEntity<Page<Book>> getAllBorrowedBooks(
			@RequestParam(defaultValue="0")int page,
			@RequestParam(defaultValue="5") int size
			){
		Page<Book> books = bookservice.getBorrowedBooks(page, size);
		
		return new ResponseEntity<>(books, HttpStatus.OK);
	}
}
