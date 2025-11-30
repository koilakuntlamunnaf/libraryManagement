package com.example.demo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.demo.model.Author;
import com.example.demo.repository.AuthorRepository;

@Service
public class AuthorService {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);

	@Autowired
	private AuthorRepository repo;
	
	public Page<Author> getAllAuthors(int page, int size){
		Page<Author> authors = repo.findAll(PageRequest.of(page, size));
		if(authors.isEmpty()) {
			logger.info("no authors are present in database");
		}
		return authors;
	}
	
	public void deleteAuthor(int id) {
		Author checkAuth = repo.findById(id).orElse(null);
		if(checkAuth == null){
			logger.info("authorid {} is not present in database", id);
		}
		else {
			repo.deleteById(id);
		}
	}
	
	public Author addAuthor(Author author) {
		return repo.save(author);
	}
}
