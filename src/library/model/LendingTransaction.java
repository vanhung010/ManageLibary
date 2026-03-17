package library.model;

import java.time.LocalDate;

public class LendingTransaction {
private String transactionID;
private String memberId;
private LocalDate creationDate;
private LocalDate dueDate;
private LocalDate returnDate;
private int  renewalCount;
private String barcodeBook;
private Fine fine;
public Fine getFine() {
	return fine;
}
public void setFine(Fine fine) {
	this.fine = fine;
}
public LendingTransaction(String transactionID, String memberId, LocalDate creationDate, LocalDate dueDate, String barcodeBook) {
	super();
	this.transactionID = transactionID;
	this.memberId = memberId;
	this.creationDate = LocalDate.now();
	this.dueDate = creationDate.plusDays(14);
	;
	this.renewalCount = 0;
	this.barcodeBook = barcodeBook;
}
public String getTransactionID() {
	return transactionID;
}
public void setTransactionID(String transactionID) {
	this.transactionID = transactionID;
}
public String getMemberId() {
	return memberId;
}
public void setMemberId(String memberId) {
	this.memberId = memberId;
}
public LocalDate getCreationDate() {
	return creationDate;
}
public void setCreationDate(LocalDate creationDate) {
	this.creationDate = creationDate;
}
public LocalDate getDueDate() {
	return dueDate;
}
public void setDueDate(LocalDate dueDate) {
	this.dueDate = dueDate;
}
public LocalDate getReturnDate() {
	return returnDate;
}
public void setReturnDate(LocalDate returnDate) {
	this.returnDate = returnDate;
}
public int getRenewalCount() {
	return renewalCount;
}
public void setRenewalCount(int renewalCount) {
	this.renewalCount = renewalCount;
}
public String getBarcodeBook() {
	return barcodeBook;
}
public void setBarcodeBook(String barcodeBook) {
	this.barcodeBook = barcodeBook;
}

public static void renewableLending(LendingTransaction lt) throws Exception {
	if(lt.getRenewalCount() >=2 ) {
		throw new Exception("Bạn đã quá lần gia hạn cuốn sách này");
	}
	lt.setDueDate(lt.getDueDate().plusDays(5));
	lt.setRenewalCount(lt.getRenewalCount()+1);
}
}
