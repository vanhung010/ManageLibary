package library.model;

public abstract class  Account {

	private String accountID;
	private String name;
	private String email;
	private String password;
	public Account(String accountID, String name, String email, String password) {
		super();
		this.accountID = accountID;
		this.name = name;
		this.email = email;
		this.password = password;
	}
	public String getAccountID() {
		return accountID;
	}
	public void addAccountID(String accountID) {
		this.accountID = accountID;
	}
	public String getName() {
		return name;
	}
	public void addName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void addEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void addPassword(String password) {
		this.password = password;
	}


}
