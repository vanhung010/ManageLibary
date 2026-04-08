package library.model;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import library.data.DataCenter;

public class Catalog {

	LocalDate lastUpdate = null;

	public List<BookItem> searchBookByName(String name) {
		DataCenter db = DataCenter.getInstace();
		String keyword = name == null ? "" : name.trim().toLowerCase();
		return db.getListBook().stream()
				.filter(bookItem -> bookItem.getBook() != null
						&& bookItem.getBook().getTitle() != null
						&& bookItem.getBook().getTitle().toLowerCase().contains(keyword))
				.collect(Collectors.toList());
	}

	public List<BookItem> searchBookByAuthorName(String name) {
		DataCenter db = DataCenter.getInstace();
		return db.getListBook().stream()
				.filter(bookitem -> bookitem.getBook().getAuthor().stream()
						.anyMatch(author -> author.getAuthorName().equals(name)))
				.collect(Collectors.toList());
	}
}
