package com.example.demo.service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;

@Service
public class BookService {
	
	private static final Logger logger = LoggerFactory.getLogger(BookService.class);
	
	@Autowired
	private BookRepository bookrepo;
	
	@Autowired
	private AuthorRepository autrepo;
	
	public Page<Book> getBorrowedBooks(int page, int size){
		
		Page<Book> books = bookrepo.getAllBorrowedBooks(PageRequest.of(page, size));
		
		if(books.isEmpty()) {
			logger.info("No borrowed books are present");
		}
		
		return books;	
	}
	
	@Transactional
	public Book addBook(Book book) {
		
		Author auth = book.getAuthor();
		Author checkAuth = autrepo.findById(auth.getAuthorId()).orElse(null);
		logger.info("getting the book availablity {}" , book.getIsAvailable());
		if(checkAuth == null) {
			logger.warn("author : {} is not found in database", auth.getAuthorName());
			Author newAuth = autrepo.save(auth);
			book.setAuthor(newAuth);
			logger.info("author : {} is saved in database ", auth.getAuthorName());
		}
		
		return bookrepo.save(book);
	}
	
	public Book getBookById(Integer id) {
		Book book =  bookrepo.findById(id).orElse(null);
		if(book == null) {
			logger.info("book id {} is not found in database ", id);
		}
		return book;
	}
	
	public Boolean getDeleteById(Integer Id) {
			Book book = bookrepo.findById(Id).orElse(null);
			if(book == null) {
				logger.info("bookid : {} is not present in database ", Id);
				return false;
			}
			else {
				bookrepo.deleteById(Id);
				logger.info("Book : {} is deleted ", book.getBookName());
			}
			
			return true;
		
	}
	
	public Page<Book> getAllBooks(int page, int size){
		
		Page<Book> list_books = bookrepo.findAll(PageRequest.of(page, size));
		if(list_books.isEmpty()) {
			logger.info("No books are present in database ");
		}
		return list_books;
	}

}
