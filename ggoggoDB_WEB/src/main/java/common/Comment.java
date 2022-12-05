package common;

import java.sql.*;
import java.util.Date;

public class Comment {
	private int commentid;
	private String text;
	private Date date;
	private String writerid;
	
	public int getCommentid() {
		return commentid;
	}

	public String getText() {
		return text;
	}

	public Date getDate() {
		return date;
	}

	public String getWriterid() {
		return writerid;
	}

	Comment(ResultSet rs){
		try {
			this.commentid = rs.getInt(1);
			this.text = rs.getString(2);
			this.date = rs.getDate(3);
			this.writerid = rs.getString(4);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
