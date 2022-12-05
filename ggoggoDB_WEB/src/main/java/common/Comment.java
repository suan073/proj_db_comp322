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
	
	public String show() {
		StringBuffer result = new StringBuffer();
		result.append("<table width=\"100%\">");

		result.append("<tr>");
		result.append("<td>");

		result.append("<form action=\"otherUserLog.jsp\" method=\"post\">");
		result.append(writerid);

		result.append("</td>");
		result.append("<td align=\"right\">");

		result.append(" <button type=\"submit\" name=\"oUserId\" value=\"" + writerid
				+ "\"> 해당 쓴이의 모든 글 구경하러 가기! </button><br>");
		result.append("</form>");

		result.append("</td>");
		result.append("</tr>");

		result.append("<tr>");
		result.append("<td>");
		result.append("</td>");
		result.append("<td align=\"right\">");
		result.append("날짜 : " + date);
		result.append("</td>");
		result.append("</tr>");

		result.append("<td colspan='2'>");
		result.append(text);

		result.append("</td>");
		result.append("</tr>");
		result.append("</table>");

		return result.toString();
	}
	
}
