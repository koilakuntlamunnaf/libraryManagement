package com.example.demo.repository;

import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Book;

import jakarta.transaction.Transactional;
@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{

	
	@Query(value = " select b.* from book b inner join borrow br where b.book_id= br.bookid ",
			nativeQuery = true)
	public Page<Book> getAllBorrowedBooks(PageRequest pageRequest);
	
	@Transactional
	@Modifying
	@Query("update Book b set b.isAvailable= :updateBook where b.bookId = :bookid")
	public int updateBookAvailablity(
			@Param("bookid") int bookid,
			@Param("updateBook") boolean updateBook);
	
}
