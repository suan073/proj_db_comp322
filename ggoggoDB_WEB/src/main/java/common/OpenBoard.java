package common;

import java.sql.*; // import JDBC package
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class OpenBoard {
	int logNum;
	String userId;
	Connection conn;

	public OpenBoard(Connection conn, String userId) {
		logNum = 0;
		this.conn = conn;
		this.userId = userId;
		try {
			Statement stmt = conn.createStatement();
			String sql = "select * from pjlog";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next())
				logNum++;
			stmt.close();
			rs.close();
		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}

	// new method in Ph4
	public List<Log> allBoard() {
		List<Log> logs = new ArrayList<Log>();
		try {
			int logNum = 0;
			Statement stmt = conn.createStatement();
			String sql = "select * from pjlog left outer join (select targetposting, count (*) as commentnum\r\n"
					+ "from pjcomment group by targetposting) on pjlogid=targetposting\r\n"
					+ "where pjpublic='Y' order by pjlogdate desc";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next() && logNum < 30) {
				Log log = new Log(rs);
				logs.add(log);
			}
			stmt.close();
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
					+ "where pjpublic='Y' and (pjlogtitle like ? or pjcontents like ? or writerid like ?) order by pjlogdate desc";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + search + "%");
			ps.setString(2, "%" + search + "%");
			ps.setString(3, "%" + search + "%");
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

	// New method in Ph4
	public void writeLog(String title, String content, String ispublic) {
		try {
			java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
			String sql = "insert into pjlog values (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, logNum++);
			ps.setString(2, title);
			ps.setString(3, ispublic);
			ps.setInt(4, 0);
			ps.setString(5, content);
			ps.setDate(6, today);
			ps.setString(7, userId);
			ps.setString(8, null);
			
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
