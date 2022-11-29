package ggoggoDB_WEB;

import java.sql.*;

public class Log {
	private String logid;
	private String title;
	private int likes;
	private String contents;
	private Date date;
	private String writerid;
	private int commentNum;
	
	public String getLogid() {
		return logid;
	}

	public String getTitle() {
		return title;
	}

	public int getLikes() {
		return likes;
	}

	public String getContents() {
		return contents;
	}

	public Date getDate() {
		return date;
	}

	public String getWriterid() {
		return writerid;
	}

	public int getCommentNum() {
		return commentNum;
	}

	Log(ResultSet rs){
		try {
			this.logid = rs.getString(1);
			this.title = rs.getString(2);
			this.likes = rs.getInt(4);
			this.contents = rs.getString(5);
			this.date = rs.getDate(6);
			this.writerid = rs.getString(7);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	void setCommentNum() {
		
	}
}
