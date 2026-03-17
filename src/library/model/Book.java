package library.model;

import java.util.ArrayList;
import java.util.List;

public class Book {
private String isbn;
private String title;
private String type;
private List<BookItem> listBook;
private List<Author> author;
public Book(String isbn, String title, String type, List<BookItem> listBook, List<Author> author) {
	super();
	this.isbn = isbn;
	this.title = title;
	this.type = type;
	this.listBook = new ArrayList<BookItem>();
	this.author = new ArrayList<Author>();
}
@Override
public String toString() {
	return "Book [title=" + title + ", type=" + type + ", author=" + author + "]";
}
public String getIsbn() {
	return isbn;
}
public void setIsbn(String isbn) {
	this.isbn = isbn;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public List<BookItem> getListBook() {
	return listBook;
}
public void setListBook(List<BookItem> listBook) {
	this.listBook = listBook;
}
public List<Author> getAuthor() {
	return author;
}
public void setAuthor(List<Author> author) {
	this.author = author;
}




}
