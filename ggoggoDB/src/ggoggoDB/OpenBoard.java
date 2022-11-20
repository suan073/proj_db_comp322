package ggoggoDB;

import java.sql.*; // import JDBC package
import java.util.Date;

class OpenBoard {

	int[] showBoard(Connection conn) {
		int[] logs = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
		try {
			int logNum = 0;
			Statement stmt = conn.createStatement();
			String sql = "select pjlogid, writerid, pjlogdate, pjlogtitle, pjcontents\r\n" + "from pjlog\r\n"
					+ "order by pjlogdate desc";
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println();
			System.out.println("* 전체 게시판 *");
			System.out.println("--------------------");
			while (rs.next() && logNum < 10) {
				int logid = rs.getInt(1);
				logs[logNum] = logid;
				String writerID = rs.getString(2);
				Date date = rs.getDate(3);
				String title = rs.getString(4);
				String contents = rs.getString(5);
				System.out.println("글번호 #" + logid + "  제목: " + title);
				System.out.println("작성자:" + writerID + "\t    " + date);
				System.out.println(contents);
				System.out.println();
				logNum++;
			}
			stmt.close();
		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
		return logs;
	}

	void searchLog(Connection conn, String search) {
		try {
			int logNum = 0;
			String sql = "select writerid, pjlogdate, pjlogtitle, pjcontents\r\n" + "from pjlog\r\n"
					+ "where pjlogtitle like ? or pjcontents like ? or writerid like ?\r\n" + "order by pjlogdate desc";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + search + "%");
			ps.setString(2, "%" + search + "%");
			ps.setString(3, "%" + search + "%");
			ResultSet rs = ps.executeQuery();
			System.out.println();
			System.out.println("'" + search + "' 검색 결과");
			System.out.println("--------------------");

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
				System.out.println("검색 결과가 없습니다 ...\n");
		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}

	int showLogComments(Connection conn, int logid) {
		int commentNum = 0;
		try {
			//
			System.out.println("--------------------");
			System.out.println();

			String sql = "select writerid, commdate, pjtext\r\n" + "from pjcomment\r\n" + "where targetposting=?\r\n"
					+ "order by commdate asc";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, logid);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				commentNum++;
				String writerID = rs.getString(1);
				Date date = rs.getDate(2);
				String text = rs.getString(3);
				System.out.println("작성자: " + writerID + "\t" + date);
				System.out.println(text);
				System.out.println();
			}
			if(commentNum == 0)
				System.out.println("댓글이 없습니다, 댓글을 작성해보세요!");

		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
		return commentNum;
	}

	void writeComment(Connection conn, int cnum, String text, String userid, int logid) {
		try {
			Date today = new Date();
			String sql = "insert into follow values (?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, cnum);
			ps.setString(2, text);
			ps.setDate(3, (java.sql.Date) today);
			ps.setString(4, userid);
			ps.setInt(5, logid);

			int res = ps.executeUpdate();
			if (res == 1) {
				System.out.println("댓글이 작성되었습니다.");
				conn.commit();

			} else {
				System.out.println("오류!");
//				return false;
			}

		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
//		return true;
	}

}
