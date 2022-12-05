package common;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OtherUser {
	private String oUserId;

	public OtherUser(String userid) {
		this.oUserId = userid;
	}

	public List<Log> showUserLog(Connection conn) {
		List<Log> logs = new ArrayList<Log>();
		try {
			String sql = "select * from pjlog where writerid=? and pjpublic='Y' order by pjlogdate desc";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, oUserId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Log log = new Log(rs);
				logs.add(log);
			}
			ps.close();
			rs.close();
		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
		return logs;
	}

	public boolean infollowing(Connection conn, String myId) {
		boolean yn = false;
		try {
			String sql = "select * from follow where pjuserid=? and followerid=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, oUserId);
			ps.setString(2, myId);
			ResultSet rs = ps.executeQuery();

			if (rs.next())
				yn = true;

			ps.close();
			rs.close();
			
		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
		return yn;
	}

	public boolean unfollow(Connection conn, String myId) {
		if(!infollowing(conn, myId)) {
			System.out.println("팔로우하고 있지 않습니다");
			return false;
		}
		try {
			String sql = "delete from follow where pjuserid=? and followerid=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, oUserId);
			ps.setString(2, myId);

			int res = ps.executeUpdate();
			if (res == 1) {
				System.out.println(oUserId + "를 언팔로우했습니다.");
				conn.commit();

			} else {
				System.out.println("오류!");
				return false;
			}

			ps.close();

		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
		return true;
	}

	public boolean follow(Connection conn, String myId) {
		if(infollowing(conn, myId)) {
			System.out.println("이미 팔로우 중입니다");
			return false;
		}
		try {
			String sql = "insert into follow values (?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, myId);
			ps.setString(2, oUserId);

			int res = ps.executeUpdate();
			if (res == 1) {
				System.out.println(oUserId + "를 팔로우했습니다.");
				conn.commit();

			} else {
				System.out.println("오류!");
				return false;
			}
			ps.close();

		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
		return true;
	}

}
