package Search;

public class ResultItem {
	String ssn;
	String content;
	public ResultItem(String ssn, String content) {
		super();
		this.ssn = ssn;
		this.content = content;
	}
	public String getSsn() {
		return ssn;
	}
	public String getContent() {
		return content;
	}
}
