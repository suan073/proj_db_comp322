package common;

import java.sql.*; // import JDBC package
import java.util.ArrayList;
import java.util.List;

public class MyPage {
	private String userid;
	private Connection conn;

	public MyPage(Connection conn, String userid) {
		this.userid = userid;
		this.conn = conn;
	}

	public List<Log> showUserLog() {
		List<Log> logs = new ArrayList<Log>();
		try {
			String sql = "select * from pjlog left outer join (select targetposting, count (*) as commentnum\r\n"
					+ "from pjcomment group by targetposting) on pjlogid=targetposting\r\n"
					+ "where writerid=? order by pjlogdate desc";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userid);
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

	public List<String> getFollowing() {
		List<String> following = new ArrayList<String>();
		try {
			String sql = "select pjuserid from follow where followerid=?";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userid);
			ResultSet rs = ps.executeQuery();

			while (rs.next())
				following.add(rs.getString(1));

			ps.close();
			rs.close();
		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
		return following;
	}

	public boolean checkPassword(String pw) {
		try {
			String sql = "select pjuser.password from pjuser where pjuserid=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userid);
			ResultSet rs = ps.executeQuery();

			if (!rs.next())
				System.out.print("오류!");
			else if (rs.getString(1).equals(pw)) {
				return true;
			}

			ps.close();
			rs.close();
		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
		return false;
	}

	public String updatePassword(String newPw) {
		try {
			String sql = "update pjuser set password=? where pjuserid=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, newPw);
			ps.setString(2, userid);
			int res = ps.executeUpdate();

			if (res == 1) {
				conn.commit();
				return "비밀번호가 변경되었습니다.";
			} else
				System.out.println("오류!");

			ps.close();
		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
		return "오류!";
	}
}