package com.vogella.jaxb.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

//This statement means that class "Bookstore.java" is the root-element of our example
@XmlRootElement(namespace = "com.vogella.xml.jaxb.model")
public class Bookstore {

	// XmLElementWrapper generates a wrapper element around XML representation
	@XmlElementWrapper(name = "bookList")
	// XmlElement sets the name of the entities
	@XmlElement(name = "book")
	private List<Book> bookList;
	private String name;
	private String location;

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

	public List<Book> getBooksList() {
		return bookList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
