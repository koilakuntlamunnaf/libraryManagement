package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Borrow {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int borrowid;
	private int userid;
	private int bookid;
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getBookid() {
		return bookid;
	}
	public void setBookid(int bookid) {
		this.bookid = bookid;
	}
	@Override
	public String toString() {
		return "Borrow [userid=" + userid + ", bookid=" + bookid + "]";
	}
	public Borrow(int userid, int bookid) {
		super();
		this.userid = userid;
		this.bookid = bookid;
	}
	
	public Borrow() {}
}
	
