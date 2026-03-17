package library.service;

import java.util.List;

import library.model.BookItem;

public class CalculatorIndexBook {
public static int calculatorIndexBook(List<BookItem> lb, BookItem b) throws Exception {
	int index = 0;
	for(BookItem bi : lb) {
		if(bi.getBarcode().equals(b.getBarcode())) {
			return index;
		}
		index++;
	}
	throw new Exception("Không tìm thấy sách");
}
}
