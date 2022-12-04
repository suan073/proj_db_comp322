package common;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OtherUser {
	String oUserId;

	public OtherUser(String userid) {
		this.oUserId = userid;
	}

	// new method in Ph4
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

	boolean unfollow(Connection conn, String myId) {
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

	boolean follow(Connection conn, String myId) {
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
