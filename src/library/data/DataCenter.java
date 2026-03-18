package library.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import library.factory.AccountFactory;
import library.model.Account;
import library.model.Author;
import library.model.Book;
import library.model.BookItem;
import library.model.Librarain;
import library.model.Member;

public class DataCenter {

	private static DataCenter instance; 
	private List<Member> listMember;
	private List <BookItem> listBook;
	private List<Librarain> listLibrarain;

	
	private DataCenter () {
		this.listMember = new ArrayList<Member>();

		this.listBook = new ArrayList<BookItem>();
		this.listLibrarain = new ArrayList<Librarain>();
		initData(); // Khởi tạo dữ liệu mẫu
	}
	
	public static DataCenter getInstace () {
		if (instance == null) {
			instance = new DataCenter();
		}
		return instance;
	}


	
	
	public List<Member> getListMember() {
		return listMember;
	}

	public void setListMember(List<Member> listMember) {
		this.listMember = listMember;
	}

	public List<BookItem> getListBook() {
		return listBook;
	}

	public void setListBook(List<BookItem> listBook) {
		this.listBook = listBook;
	}

	public List<Librarain> getListLibrarain() {
		return listLibrarain;
	}

	public void setListLibrarain(List<Librarain> listLibrarain) {
		this.listLibrarain = listLibrarain;
	}

	private void initData() {
        // 1. Khởi tạo Thành viên bằng Factory
        Member m1 = AccountFactory.createMember("SV001", "Nguyễn Văn Hùng", "hung@nlu.edu.vn", "123", "STUDENT", "ACTIVE");
        Member m2 = AccountFactory.createMember("GV001", "Trần Trọng A", "ttA@nlu.edu.vn", "123", "TEACHER", "ACTIVE");
        listMember.add(m1);
        listMember.add(m2);

        // 2. Khởi tạo Thủ thư
        Librarain lib = new Librarain("LIB01", "Thủ thư B", "lib@nlu.edu.vn", "admin123", "EMP01");
        listLibrarain.add(lib);

        Book book1 = new Book("ISBN001", "Clean Code", "Programming");
        Book book2 = new Book("ISBN002", "Design Patterns", "Programming");
        Book book3 = new Book("ISBN003", "Database System", "Education");

        // BookItem của book1
        BookItem b1 = new BookItem("BC001", "A1", "Available", book1);
        BookItem b2 = new BookItem("BC002", "A1", "Borrowed", book1);
        BookItem b3 = new BookItem("BC003", "A2", "Available", book1);

        // BookItem của book2
        BookItem b4 = new BookItem("BC004", "B1", "Available", book2);
        BookItem b5 = new BookItem("BC005", "B1", "Borrowed", book2);
        BookItem b6 = new BookItem("BC006", "B2", "Available", book2);

        // BookItem của book3
        BookItem b7 = new BookItem("BC007", "C1", "Available", book3);
        BookItem b8 = new BookItem("BC008", "C1", "Borrowed", book3);
        BookItem b9 = new BookItem("BC009", "C2", "Available", book3);
     
		listBook.add(b1);
        listBook.add(b2);
        listBook.add(b3);
        listBook.add(b4);
        listBook.add(b5);
        listBook.add(b6);
        listBook.add(b7);
        listBook.add(b8);
        listBook.add(b9);
    }
	
	@Override
	public String toString() {
		return "DataCenter [listMember=" + listMember + ", listBook=" + listBook + "]";
	}

	
}
