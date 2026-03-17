package library.model;

import java.time.LocalDate;

import library.data.DataCenter;

public class Librarain extends Account {

	private String employeeID;

	public Librarain(String accountID, String name, String email, String password, String employeeID) {
		super(accountID, name, email, password);
		this.employeeID = employeeID;
	}

	@Override
	public String toString() {
		return "Librarain [employeeID=" + employeeID + "]";
	}

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}
	public void addBook(BookItem book) {
		DataCenter.getInstace().getListBook().add(book);
	}
	public void deleteBook(Book book) {
		DataCenter.getInstace().getListBook().remove(book);
	}
	public void updateBook(String status, String barcode) {
		
		int index = 0;
		
		for(BookItem bi : DataCenter.getInstace().getListBook()) {
			if(bi.getBarcode().equals(barcode)) {
				DataCenter.getInstace().getListBook().get(index).setStatus(status);
			}
			index++;
		}
		}
	public void addMember(Member m) {
		DataCenter.getInstace().getListMember().add(m);
	}
	public void removerMember(Member m) {
		DataCenter.getInstace().getListMember().remove(m);
	}
	public void updateMember(String idMember, String status) {
		int index = 0;
		for(Member member : DataCenter.getInstace().getListMember()) {
			if(member.getAccountID().equals(idMember)) {
				DataCenter.getInstace().getListMember().get(index).setStatus(status);
			}
			index++;
		}
	}
	//cho doi tuong muon sach
	public void issueBook(Member m, BookItem bi) {
		if(!m.getStatus().equals("ACTIVE")) return;
		if(!m.canBorrowMore()) return;
		bi.setStatus("BORROW");
		m.setTotalBooksCheckedOut(m.getTotalBooksCheckedOut() +1);
		LendingTransaction l = new LendingTransaction("123", m.getAccountID(), LocalDate.now(), LocalDate.now().plusDays(14), bi.getBarcode());
		m.getLending().add(l);
		}
	public void returnBook(BookItem b) {
		int index =0;
		for(BookItem bi : DataCenter.getInstace().getListBook()) {
			if(bi.equals(b)) {
				break;
			}
			index++;
		}
		DataCenter.getInstace().getListBook().get(index).setStatus("AVAIABLE");
	}
	
	public int collectFine(Fine f) {
		return f.calculateAmount(f.getAmount());
	}
	}

