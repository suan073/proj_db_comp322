package ggoggoDB;

import java.sql.*; // import JDBC package
import java.util.ArrayList;
import java.util.List;

class MyPage {
	String userid;
	List<String> following = new ArrayList<String>();
	
	MyPage(Connection conn, String userid) {
		this.userid = userid;
		
		try {
			String sql = "select pjuserid\r\n" + "from follow\r\n" + "where followerid=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userid);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				following.add(rs.getString(1));
			}

		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}

	public void updateFollowing(Connection conn) {
		
	}

	public void printFollowing(Connection conn) {
		System.out.println();
		System.out.println("* ÆÈ·ÎÀ× ¸ñ·Ï *");
		System.out.println("--------------------");
		for (String fuser : following) {
			System.out.println(fuser);
		}
		System.out.println();
	}
}
