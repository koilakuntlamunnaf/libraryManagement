package com.example.demo.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Borrow;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;


@RestController
public class UserController {

	private UserService userservice;
	
	public UserController(UserService userservice) {
		this.userservice = userservice;
	}
	
	@PostMapping("/add-user")
	public ResponseEntity<User> addUser(@RequestBody @Valid User user) {
		User userAdded = userservice.addUser(user);
		return new ResponseEntity<>(userAdded, HttpStatus.CREATED);
	}
	
	@GetMapping("/all-users")
	public ResponseEntity<Page<User>> getAllUsers(
			@RequestParam(defaultValue="0")int page,
			@RequestParam(defaultValue="5") int size
			){
		Page<User> users = userservice.getAllUsers(page, size);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@PostMapping("/user/{userid}/borrow-book/{bookid}")
	public ResponseEntity<String> borrowBook(
			@PathVariable int userid, 
			@PathVariable int bookid
			) {
		int update = userservice.borrowBook(userid, bookid);
		
		return ResponseEntity.ok(update> 0 ? "Borrowed is completed":"book is already borrowed");
		
	}
	@PostMapping("/user/{userid}/return-book/{bookid}")
	public ResponseEntity<String> returnBook(
			@PathVariable int userid, 
			@PathVariable int bookid
			){
		int update = userservice.returnBook(userid, bookid);
		
		return ResponseEntity.ok(update >0 ?"return is completed":"return is not possible");
	}

}
