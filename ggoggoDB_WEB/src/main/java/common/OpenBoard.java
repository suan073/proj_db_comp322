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

	void executeOpenBoard(Connection conn, Scanner scanner, String myUserId) {
		while (true) {
			String choice;

			System.out.println();
			System.out.println("*** 게시판 ***");
			System.out.println("1. 전체 게시판 보기");
			System.out.println("2. 게시글 검색하기");
			System.out.println("3. 게시글 작성하기");
			System.out.println("0. 돌아가기");

			choice = scanner.nextLine();

			if (choice.equals("1")) {
//				while (true) {
//					int logs[] = showBoard(conn);
//
//					System.out.println("자세히 보고 싶은 글의 글번호를 입력하세요");
//					System.out.print("뒤로 가려면 'n'을 입력하세요: ");
//					String input = scanner.nextLine();
//
//					if (input.equals("n"))
//						break;
//					int targetLog = Integer.parseInt(input);
//					if (!inlogs(targetLog, logs))
//						System.out.println("잘못된 입력입니다.");
//					else {
//						while (true) {
//							int cNum = showLogComments(conn, targetLog);
//							System.out.print("댓글을 작성하시겠습니까? (y/n): ");
//							choice = scanner.nextLine();
//
//							if (choice.equals("y")) {
//								System.out.print("작성할 댓글을 입력하세요: ");
//								String comment = scanner.nextLine();
//								writeComment(conn, cNum, comment, myUserId, targetLog);
//							} else if (choice.equals("n")) {
//								System.out.println("이전으로 돌아갑니다.");
//								break;
//							} else {
//								System.out.println("잘못된 입력입니다.");
//							}
//						}
//					}
//				}

			} else if (choice.equals("2")) {
				while (true) {
					System.out.print("게시글 검색: ");
					String search = scanner.nextLine();

					int logs[] = searchLog(conn, search);

					if (logs[0] == -1)
						continue;
					System.out.println("자세히 보고 싶은 글의 글번호를 입력하세요");
					System.out.print("뒤로 가려면 'n'을 입력하세요: ");
					String input = scanner.nextLine();

					if (input.equals("n"))
						break;
					int targetLog = Integer.parseInt(input);
//					if (!inlogs(targetLog, logs))
//						System.out.println("잘못된 입력입니다.");
//					else {
//						while (true) {
//							int cNum = showLogComments(conn, targetLog);
//							System.out.print("댓글을 작성하시겠습니까? (y/n): ");
//							choice = scanner.nextLine();
//
//							if (choice.equals("y")) {
//								System.out.print("작성할 댓글을 입력하세요: ");
//								String comment = scanner.nextLine();
//								writeComment(conn, cNum, comment, myUserId, targetLog);
//							} else if (choice.equals("n")) {
//								System.out.println("이전으로 돌아갑니다.");
//								break;
//							} else {
//								System.out.println("잘못된 입력입니다.");
//							}
//						}
//						break;
//					}
				}

			} else if (choice.equals("3")) {
				// writeLog(conn, scanner, myUserId);
			} else if (choice.equals("0")) {
				break;
			} else {
				System.out.println("잘못된 입력입니다.\n");
				continue;
			}
		}
	}

	// new method in Ph4
	public List<Log> allBoard(Connection conn) {
		List<Log> logs = new ArrayList<Log>();
		try {
			int logNum = 0;
			Statement stmt = conn.createStatement();
			String sql = "select * from pjlog where pjpublic='Y' order by pjlogdate desc";
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

	int[] searchLog(Connection conn, String search) {
		int[] logs = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
		try {
			int logNum = 0;
			String sql = "select pjlogid, writerid, pjlogdate, pjlogtitle, pjcontents from pjlog\r\n"
					+ "where pjpublic='Y' and (pjlogtitle like ? or pjcontents like ? or writerid like ?) order by pjlogdate desc";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + search + "%");
			ps.setString(2, "%" + search + "%");
			ps.setString(3, "%" + search + "%");
			ResultSet rs = ps.executeQuery();
			System.out.println();
			System.out.println("'" + search + "' 검색 결과");
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
			if (logNum == 0)
				System.out.println("검색 결과가 없습니다 ...\n");
			ps.close();
			rs.close();
		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
		return logs;
	}

	int showLogComments(Connection conn, int logid) {
		int commentNum = 0;
		try {
			String sql = "select writerid, pjlogdate, pjlogtitle, pjcontents from pjlog where pjlogid=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, logid);
			ResultSet rs = ps.executeQuery();

			System.out.println();
			if (rs.next()) {
				String writerID = rs.getString(1);
				Date date = rs.getDate(2);
				String title = rs.getString(3);
				String contents = rs.getString(4);
				System.out.println("글번호 #" + logid + "  제목: " + title);
				System.out.println("작성자:" + writerID + "\t    " + date);
				System.out.println(contents);
				System.out.println();
			}
			System.out.println("--------------------");
			System.out.println();

			sql = "select writerid, commdate, pjtext from pjcomment where targetposting=? order by commdate asc";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, logid);
			rs = ps.executeQuery();

			while (rs.next()) {
				commentNum++;
				String writerID = rs.getString(1);
				Date date = rs.getDate(2);
				String text = rs.getString(3);
				System.out.println("작성자: " + writerID + "\t" + date);
				System.out.println(text);
				System.out.println();
			}
			if (commentNum == 0)
				System.out.println("댓글이 없습니다, 댓글을 작성해보세요!");
			ps.close();
			rs.close();

		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
		return commentNum;
	}

	void writeComment(Connection conn, int cnum, String text, String userid, int logid) {
		try {
			java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
			String sql = "insert into pjcomment values (?, ?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, cnum + 1);
			ps.setString(2, text);
			ps.setDate(3, today);
			ps.setString(4, userid);
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
