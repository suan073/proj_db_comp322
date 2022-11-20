package ggoggoDB;

import java.sql.*; // import JDBC package

class OtherUser {
	String oUserId;
	
	OtherUser(String userid){
		this.oUserId = userid;
	}

	public boolean ShowUserLog(Connection conn) {
		try {
			int logNum = 0;

			String sql = "select writerid, pjlogdate, pjlogtitle, pjcontents\r\n" + "from pjlog\r\n"
					+ "where writerid=?\r\n" + "order by pjlogdate desc";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, oUserId);
			ResultSet rs = ps.executeQuery();
			System.out.println();
			while (rs.next() && logNum < 10) {
				String writerID = rs.getString(1);
				Date date = rs.getDate(2);
				String title = rs.getString(3);
				String contents = rs.getString(4);
				System.out.println(writerID + "\t" + date);
				System.out.println("[" + title + "]");
				System.out.println(contents);
				System.out.println();
				logNum++;
			}
			if (logNum == 0)
				System.out.println("No Log ...\n");

		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
		return true;
	}

	public boolean unfollow(Connection conn, String myId) {
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
			
		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
		return true;
	}
	
	public boolean follow(Connection conn, String myId) {
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
			
		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
		return true;
	}

}
