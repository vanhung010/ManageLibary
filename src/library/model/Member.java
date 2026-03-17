package library.model;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import library.data.DataCenter;
import library.service.CalculatorIndexBook;
import library.strategy.BorrowingStrategy;

public class Member extends Account {

	private String membertype;
	private String status;
	private int totalBooksCheckedOut;// tổng số sách mượn
	private List<LendingTransaction> lending;
	private List<Reservation> listReservation;
	private BorrowingStrategy borrowingStrategy;
	public Member(String accountID, String name, String email, String password, String membertype, String status, BorrowingStrategy borrowingStrategy) {
		super(accountID, name, email, password);
		this.membertype = membertype;
		this.status = "Active";
		this.totalBooksCheckedOut = 0;
		this.borrowingStrategy = borrowingStrategy;
	}
	
	public String getMembertype() {
		return membertype;
	}

	public void setMembertype(String membertype) {
		this.membertype = membertype;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTotalBooksCheckedOut() {
		return totalBooksCheckedOut;
	}

	public void setTotalBooksCheckedOut(int totalBooksCheckedOut) {
		this.totalBooksCheckedOut = totalBooksCheckedOut;
	}

	public List<LendingTransaction> getLending() {
		return lending;
	}

	public void setLending(List<LendingTransaction> lending) {
		this.lending = lending;
	}

	@Override
	public String toString() {
		return "Member [membertype=" + membertype + ", status=" + status + ", totalBooksCheckedOut="
				+ totalBooksCheckedOut + ", getAccountID()=" + getAccountID() + ", getName()=" + getName()
				+ ", getEmail()=" + getEmail() + ", getPassword()=" + getPassword();
	}
	
	public boolean canBorrowMore() {
		return totalBooksCheckedOut < borrowingStrategy.getBorrrowingLimit();
	}
	
	public List<BookItem> searchBookByName(String name) {
		DataCenter db = DataCenter.getInstace();
		return db.getListBook().stream().filter(bookitem -> bookitem.getBook().getTitle().equals(name)).collect(Collectors.toList());
	}
	public List<BookItem> searchBookByAuthorName(String name) {
		DataCenter db = DataCenter.getInstace();
		return db.getListBook().stream().filter(bookitem -> bookitem.getBook().getAuthor().stream().anyMatch(author -> author.getAuthorName().equals(name))).collect(Collectors.toList());
				
	}
	
	public void viewProfile() {
		System.out.println(this);
	}
	
	public LendingTransaction findLending(String barcode) {
		for(LendingTransaction lt : lending) {
			if(lt.getBarcodeBook().equals(barcode)) {
				return lt;
			}
		}
		return null;
	}
	public void requestRenewable(BookItem b) throws Exception {
		
		int index = CalculatorIndexBook.calculatorIndexBook(DataCenter.getInstace().getListBook(), b);
		//kiểm tra sách đã được mượn hay chưa
		if(DataCenter.getInstace().getListBook().get(index).getStatus().equals("Reserved")) {
			throw new Exception("Sách đã được đặt trước");
		}
		
		LendingTransaction lt = findLending(b.getBarcode());
		LendingTransaction.renewableLending(lt);
		
	}
	
	public void reserveBook(BookItem bi) {
		if(!bi.getStatus().equals("AVAIBLE")) {
			return;
		}
		Reservation re = new Reservation("123", LocalDate.now(), "PENDING");
		listReservation.add(re);
		
	}
	
	public int getBorrowLimit() {
		return borrowingStrategy.getBorrrowingLimit();
	}
}
