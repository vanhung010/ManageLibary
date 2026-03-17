package library.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	private static void initData() {
		Author author1 = new Author("J.K. Rowling");
        Author author2 = new Author("George Orwell");
        Author author3 = new Author("J.R.R. Tolkien");

        // 2. Create 1 Librarian
        Librarain headLibrarian = new Librarain(
            "LIB-001", "Alice Smith", "alice@library.com", "securePass123", "EMP-99"
        );

        // 3. Create 1 Member
        
        // 4. Create 5 Books
        // Note: Using empty ArrayLists for BookItem for the sake of this example
    
       
        
	}
	
	
	@Override
	public String toString() {
		return "DataCenter [listMember=" + listMember + ", listBook=" + listBook + "]";
	}

	
}
