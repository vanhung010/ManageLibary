package library.model;

import java.util.List;

public class BookItem {

	private String barcode;
	private String racklocation;
	private String status;
	private Book book;
	
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public BookItem(String barcode, String racklocation, String status, Book book) {
		super();
		this.barcode = barcode;
		this.racklocation = racklocation;
		this.status = status;
		this.book = book;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getRacklocation() {
		return racklocation;
	}
	public void setRacklocation(String racklocation) {
		this.racklocation = racklocation;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
