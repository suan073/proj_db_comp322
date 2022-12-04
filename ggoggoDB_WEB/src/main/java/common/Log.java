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
	
	public Log(Connection conn, int logid){
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
	
	Log(ResultSet rs){
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
	
	void setCommentNum(Connection conn) {
		try {
			String sql = "select * from pjcomment where targetposting=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, logid);
			ResultSet rs = ps.executeQuery();
			commentNum = 0;
			if (rs.next())
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
	
	public void writeComment(Connection conn, String userId, String text) {
		try {
			java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
			String sql = "insert into pjcomment values (?, ?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, commentNum++);
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
}
