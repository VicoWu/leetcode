package Q7_05_Online_Book_Reader;

public class Display {
	private Book activeBook;
	private User activeUser;
	private int pageNumber = 0;
	
	public void displayUser(User user) {
		activeUser = user;
		refreshUsername();
	}
	
	public void displayBook(Book book) {
		pageNumber = 0;
		activeBook = book;
		
		refreshTitle();
		refreshDetails();
		refreshPage();
	}
	
	public void refreshUsername() {
		/* updates username display */
	}
	
	public void refreshTitle() {
		/* updates title display */
	}
	
	public void refreshDetails() {
		/* updates details display */
	}

	/**
	 * page代表书籍上的文字，用户在翻页的时候，只有文字发生变化，而书籍的标题、其它详细信息都不变
	 */
	public void refreshPage() {
		/* updated page display */
	}
	
	public void turnPageForward() {
		pageNumber++;
		refreshPage();
	}
	
	public void turnPageBackward() {
		pageNumber--;
		refreshPage();
	}	
}
