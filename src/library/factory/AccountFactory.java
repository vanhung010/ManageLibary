package library.factory;

import library.model.Member;
import library.strategy.BorrowingStrategy;
import library.strategy.NormalStrategy;
import library.strategy.StudentStrategy;
import library.strategy.TeacherStrategy;

public class AccountFactory {

	public static Member createMember(String accountID, String name, String email, String password, String membertype, String status, int totalBooksCheckedOut) {
		 BorrowingStrategy borrowingStrategy = null;
		switch(membertype.toUpperCase()) {
		case "STUDENT":
			borrowingStrategy = new StudentStrategy();
			break;
		case "TEACHER":
			borrowingStrategy = new TeacherStrategy();
			break;
		case "NORMAL":
			borrowingStrategy = new NormalStrategy();
			break;
		}
		return new Member(accountID, name, email, password, membertype, status, borrowingStrategy);
	}
}
