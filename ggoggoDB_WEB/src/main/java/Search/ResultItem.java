package Search;

public class ResultItem {
	String ssn;
	String title;
	String content;
	public ResultItem(String ssn, String title, String content) {
		super();
		this.ssn = ssn;
		this.title = title;
		this.content = content;
	}
	public String getSsn() {
		return ssn;
	}
	public String getContent() {
		return content;
	}
	public String getTitle() {
		return title;
	}
	
}
