package com.example.demo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.demo.model.Book;
import com.example.demo.model.Borrow;
import com.example.demo.model.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.BorrowRepository;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepo;
	private BookRepository bookRepo;
	private BorrowRepository borrowRepo;
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public UserService(UserRepository userRepo, BookRepository bookRepo, BorrowRepository borrowRepo) {
		this.userRepo = userRepo;
		this.bookRepo = bookRepo;
		this.borrowRepo	= borrowRepo;
	}
	

	
	
	public User addUser(User user) {
		User checkUser = userRepo.findById(user.getId()).orElse(null);
		if(checkUser == null) {
			logger.info("User {} is saved in database ", user.getName());
			userRepo.save(user);
		}
		else {
			logger.info("User {} is already exist ", user.getName());
		}
		return user;
	}
	
	public boolean checkBookAvailablity(Book book) {
		boolean available = false;
		Book isbookExist = bookRepo.findById(book.getBookId()).orElse(null);
		if(isbookExist!=null) {
			available = isbookExist.getIsAvailable();
			logger.info("book is exist in database and book availablity is {}", available);
			
		}
		else {
			logger.info("book is not available");
		}
		return available;
		
	}
	public Book getBook(int bookid) {
		return bookRepo.findById(bookid).orElse(null);
	}
	
	
	
	public int returnBook(int userid, int bookid) {
		boolean updateBook = true;
		Book checkBook = getBook(bookid);
		if(checkBook!= null) {
			if(!checkBookAvailablity(checkBook)) {
				int k = bookRepo.updateBookAvailablity(bookid,updateBook);
				if(k >0) {
					logger.info("update is completed for book {}",checkBook.getBookName());
					return k;
				}
				else {
					logger.info("update got error ");
				}
			}
		}
		return -1;
	}
	
	public int borrowBook(int userid, int bookid) {
		boolean updateBook = true;
		Book checkBook = getBook(bookid);
		if(checkBook!= null) {
			if(checkBookAvailablity(checkBook)) {
				Borrow borrow = new Borrow(userid, bookid);
				borrowRepo.save(borrow);
				updateBook = false;
				logger.info("borrowing for the book {} is completed ", checkBook.getBookName());
				
				if(!updateBook ) {
					//checkBook.setAvailable(false);
					logger.info("making the book {} unavailable as it is borrowed ", checkBook.getBookName());
					int k = bookRepo.updateBookAvailablity(bookid,updateBook);
					if(k >0) {
						logger.info("update is completed for book ",checkBook.getBookName());
						return k;
					}
					else {
						logger.info("update got error ");
					}
					
				}
				
			}
		}
		else {
			logger.info("book id {} is not present in database", bookid);
		}


		return -1;

	}




	public Page<User> getAllUsers(int page, int size) {
	
		return userRepo.findAll(PageRequest.of(page, size));
	}
}
