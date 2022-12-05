package common;

import java.sql.*; // import JDBC package
import java.util.ArrayList;
import java.util.List;

public class OpenBoard {
	String userId;
	Connection conn;

	public OpenBoard(Connection conn, String userId) {
		this.conn = conn;
		this.userId = userId;
	}

	public List<Log> allBoard() {
		List<Log> logs = new ArrayList<Log>();
		try {
			int logNum = 0;
			String sql = "select * from pjlog left outer join (select targetposting, count (*) as commentnum\r\n"
					+ "from pjcomment group by targetposting) on pjlogid=targetposting\r\n"
					+ "where pjpublic='Y' or writerid=? order by pjlogdate desc";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next() && logNum < 30) {
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

	public List<Log> searchLog(String search) {
		List<Log> logs = new ArrayList<Log>();
		try {
			String sql = "select * from pjlog left outer join (select targetposting, count (*) as commentnum\r\n"
					+ "from pjcomment group by targetposting) on pjlogid=targetposting\r\n"
					+ "where (pjpublic='Y' or writerid=?) and (pjlogtitle like ? or pjcontents like ? or writerid like ?) order by pjlogdate desc";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, "%" + search + "%");
			ps.setString(3, "%" + search + "%");
			ps.setString(4, "%" + search + "%");
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
	
	int getNextLogid() {
		int logId = 0;
		try {
			String sql = "select pjlogid from pjlog order by pjlogdate desc";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				logId = rs.getInt(1) + 1;
			} else {
				System.out.println("오류!");
			}

			ps.close();
		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
		return logId;
	}

	public void writeLog(String title, String content, String ispublic, String ssn) {
		try {
			java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
			String sql = "insert into pjlog values (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, getNextLogid());
			ps.setString(2, title);
			ps.setString(3, ispublic);
			ps.setInt(4, 0);
			ps.setString(5, content);
			ps.setDate(6, today);
			ps.setString(7, userId);
			ps.setString(8, ssn);
			
			int res = ps.executeUpdate();
			if (res == 1) {
				System.out.println("글이 작성되었습니다.");
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
