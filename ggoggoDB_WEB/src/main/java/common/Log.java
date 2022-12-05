package common;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Log {
	private int logid;
	private String title;
	private int likes;
	private String contents;
	private Date date;
	private String writerid;
	private int commentNum;

	public int getLogid() {
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

	public Log(Connection conn, int logid) {
		this.logid = logid;
		try {
			String sql = "select * from pjlog where pjlogid=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, logid);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				title = rs.getString(2);
				likes = rs.getInt(4);
				contents = rs.getString(5);
				date = rs.getDate(6);
				writerid = rs.getString(7);
				setCommentNum(conn);
			}
			ps.close();
			rs.close();
		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}

	public Log(ResultSet rs) {
		try {
			this.logid = rs.getInt(1);
			this.title = rs.getString(2);
			this.likes = rs.getInt(4);
			this.contents = rs.getString(5);
			this.date = rs.getDate(6);
			this.writerid = rs.getString(7);
			this.commentNum = rs.getInt(10);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Log(ResultSet rs, Connection conn) {
		try {
			this.logid = rs.getInt(1);
			this.title = rs.getString(2);
			this.likes = rs.getInt(4);
			this.contents = rs.getString(5);
			this.date = rs.getDate(6);
			this.writerid = rs.getString(7);
			setCommentNum(conn);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	void setCommentNum(Connection conn) {
		try {
			String sql = "select * from pjcomment where targetposting=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, logid);
			ResultSet rs = ps.executeQuery();
			commentNum = 0;
			while (rs.next())
				commentNum++;
			ps.close();
			rs.close();
		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}

	public List<Comment> showComments(Connection conn) {
		List<Comment> comments = new ArrayList<Comment>();
		try {
			String sql = "select * from pjcomment where targetposting=? order by commdate asc";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, logid);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Comment c = new Comment(rs);
				comments.add(c);
			}
			ps.close();
			rs.close();
		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
		return comments;
	}
	
	int getNextCommentid(Connection conn) {
		int commentId = 0;
		try {
			String sql = "select pjcommentid from pjcomment where targetposting=? order by pjcommentid desc";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, logid);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				commentId = rs.getInt(1) + 1;
			} else {
				System.out.println("오류!");
			}

			ps.close();
		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
		return commentId;
	}

	public void writeComment(Connection conn, String userId, String text) {
		try {
			java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
			String sql = "insert into pjcomment values (?, ?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, getNextCommentid(conn));
			ps.setString(2, text);
			ps.setDate(3, today);
			ps.setString(4, userId);
			ps.setInt(5, logid);

			int res = ps.executeUpdate();
			if (res == 1) {
				System.out.println("댓글이 작성되었습니다.");
				conn.commit();
			} else {
				System.out.println("오류!");
			}
			ps.close();
		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}
	
	public void pushLike(Connection conn) {
		try {
			String sql = "update pjlog set likes=? where pjlogid=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, getLikes() + 1);
			likes++;
			ps.setInt(2, logid);

			int res = ps.executeUpdate();
			if (res == 1) {
				System.out.println("좋아요!");
				conn.commit();
			} else {
				System.out.println("오류!");
			}
			ps.close();
		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}

	public String show(int type) { // openboard :0, mypage :1, timeline :2,
		StringBuffer result = new StringBuffer();
		result.append("<table width=\"100%\">");

		result.append("<tr>");
		result.append("<td>");

		result.append("<form action=\"otherUserLog.jsp\" method=\"post\">");
		result.append("작성자 : " + writerid);

		result.append("</td>");
		result.append("<td align=\"right\">");
		
		result.append("<input type=\"hidden\" name=\"type\" value="+type+">");
		result.append("<button type=\"submit\" name=\"oUserId\" value=\"" + writerid
				+ "\"> 해당 글쓴이의 모든 글 구경하러 가기! </button><br>");
		result.append("</form>");

		result.append("</td>");
		result.append("</tr>");

		result.append("<tr>");
		result.append("<td>");
		result.append("제목 : " + title);
		result.append("</td>");
		result.append("<td align=\"right\">");
		result.append("날짜 : " + date);
		result.append("</td>");
		result.append("</tr>");

		result.append("<td colspan='2'>");
		result.append(contents);

		result.append("</td>");
		result.append("</tr>");

		result.append("<tr>");
		result.append("<td  width=\"50%\">");
		
		result.append("<form action=\"logComment.jsp\" method=\"post\">");
		result.append("<input type=\"hidden\" name=\"type\" value="+type+">");
		result.append("<button type=\"submit\" name=\"logId\" value=\"" + logid + "\">댓글</button> " + commentNum);
		result.append("</form>");

		result.append("</td>");
		result.append("<td  width=\"50%\" align=\"right\">");
		
		result.append("<form method=\"post\">");
		result.append("<button type=\"submit\" name=\"like\" value=\"" + logid + "\">♡</button> " + likes);
		result.append("</form>");

		result.append("</td>");
		result.append("</tr>");
		result.append("</table>");


		return result.toString();
	}
}
