package library.model;

public class Fine {
private String fineID;
private double amount;
private boolean ispaid;
public Fine(String fineID, double amount, boolean ispaid) {
	super();
	this.fineID = fineID;
	this.amount = amount;
	this.ispaid = ispaid;
}
public String getFineID() {
	return fineID;
}
public void setFineID(String fineID) {
	this.fineID = fineID;
}
public double getAmount() {
	return amount;
}
public void setAmount(int amount) {
	this.amount = amount;
}
public boolean isIspaid() {
	return ispaid;
}
public void setIspaid(boolean ispaid) {
	this.ispaid = ispaid;
}

public int calculateAmount(double d) {
	return (int) (d*5000);
}
public void markAsPaid() {
	ispaid = true;
}
}
