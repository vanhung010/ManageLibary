package library.model;

import java.time.LocalDate;

public class Reservation {
private String reservationID;
private LocalDate creationDate;
String status;
public Reservation(String reservationID, LocalDate creationDate, String status) {
	super();
	this.reservationID = reservationID;
	this.creationDate = creationDate;
	this.status = status;
}
public String getReservationID() {
	return reservationID;
}
public void setReservationID(String reservationID) {
	this.reservationID = reservationID;
}
public LocalDate getCreationDate() {
	return creationDate;
}
public void setCreationDate(LocalDate creationDate) {
	this.creationDate = creationDate;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}

}
