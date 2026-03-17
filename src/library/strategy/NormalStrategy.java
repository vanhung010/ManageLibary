package library.strategy;

public class NormalStrategy implements BorrowingStrategy {

	@Override
	public int getBorrrowingLimit() {
	return 3;
	}

}
