package ggoggoDB;

import java.sql.*;
import java.util.Scanner;

public class ggoggoDB {
	public static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	public static final String USER_UNIVERSITY ="ggoggoDB";
	public static final String USER_PASSWD ="comp322";

	// public static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
	// public static final String USER_DBPROJ = "dbproject";
	// public static final String USER_PASSWD = "db";
	
	public static void main(String[] args) {
		Connection conn = null; // Connection object
		Scanner scan = new Scanner(System.in);
		
		try {
			// Load a JDBC driver for Oracle DBMS
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// Get a Connection object 
			System.out.println("Success!");
		}catch(ClassNotFoundException e) {
			System.err.println("error = " + e.getMessage());
			System.exit(1);
		}

		// Make a connection
		try{
			conn = DriverManager.getConnection(URL, USER_UNIVERSITY, USER_PASSWD); 
			System.out.println("Connected.");
			conn.setAutoCommit(false);
		}catch(SQLException ex) {
			ex.printStackTrace();
			System.err.println("Cannot get a connection: " + ex.getLocalizedMessage());
			System.err.println("Cannot get a connection: " + ex.getMessage());
			System.exit(1);
		}
		
		
		/* 1. 로그인*/
 		UserInformation login = part1_bySuin.login(conn, scan);
		
		// test를 위한 hard coding
//		UserInformation login = new UserInformation("momomo", "abcdef", true); 
		System.out.println(login.isVaild());
		System.out.println(login.getID());
		
		/* 2. 장르 설정*/
		if (login.isVaild()) {
			System.out.println("로그인 완료");
			System.out.println("장르설정");
			part2_bySuin.edit_interested_genre(conn, login, scan);
		}
		/* 3. 검색 (1) 필터 설정 */
		FilterInfo temp = part3_1_bySuin.settingFilter(conn, scan);
		temp.show_all();
<<<<<<< HEAD
		System.out.println();
		part3_1_bySuin.search(conn, scan, temp);
=======

		/* main work */
		Scanner scanner = new Scanner(System.in);
		String myUserId = "XoOoOong"; // arbitrary id
		OpenBoard openboard = new OpenBoard();
		MyPage mypage = new MyPage(conn, myUserId);

		/* Main3. OpenBoard */
		while (true) {
			String choice;

			System.out.println();
			System.out.println("*** 게시판 ***");
			System.out.println("1. 전체 게시판 보기");
			System.out.println("2. 게시글 검색하기");
			System.out.println("0. 돌아가기");

			choice = scanner.nextLine();

			if (choice.equals("1")) {
				while (true) {
					int logs[] = openboard.showBoard(conn);

					System.out.println("자세히 보고 싶은 글의 글번호를 입력하세요");
					System.out.print("뒤로 가려면 'n'을 입력하세요: ");
					String input = scanner.nextLine();
					int targetLog = Integer.parseInt(input);

					if (input.equals("n"))
						break;
					int inlogs = 0;
					for (int log : logs) {
						if (targetLog == log) {
							inlogs = 1;
							while (true) {
								int cNum = openboard.showLogComments(conn, targetLog);
								System.out.print("댓글을 작성하시겠습니까? (y/n): ");
								choice = scanner.nextLine();

								if (choice.equals("y")) {
									System.out.print("작성할 댓글을 입력하세요: ");
									String comment = scanner.nextLine();
									openboard.writeComment(conn, cNum, comment, myUserId, targetLog);
								} else if (choice.equals("n")) {
									System.out.println("이전으로 돌아갑니다.");
									break;
								} else {
									System.out.println("잘못된 입력입니다.");
								}
							}
						}
					}
					if (inlogs == 0)
						System.out.println("잘못된 입력입니다.");
				}

			} else if (choice.equals("2")) {

				System.out.print("게시글 검색: ");
				String search = scanner.nextLine();

				openboard.searchLog(conn, search);

			} else if (choice.equals("0")) {
				break;
			} else {
				System.out.println("잘못된 입력입니다.\n");
				continue;
			}
		}

		/* Main4. MyPage */
		while (true) {
			String choice;

			System.out.println();
			System.out.println("*** 마이페이지 ***");
			System.out.println("1. 비밀번호 변경");
			System.out.println("2. 내 팔로우 목록");
			System.out.println("0. 돌아가기");

			choice = scanner.nextLine();

			if (choice.equals("1")) {
				// not yet
			} else if (choice.equals("2")) {
				while (true) {

					mypage.printFollowing(conn);

					System.out.println("게시물을 보고싶은 사용자의 ID를 입력하세요");
					System.out.print("뒤로 가려면 'n'을 입력하세요: ");
					String targetId = scanner.nextLine();

					if (targetId.equals("n"))
						break;
					int isFollowing = 0;
					for (String f : mypage.following) {
						if (targetId.equals(f)) {

							isFollowing = 1;
							OtherUser targetUser = new OtherUser(targetId);
							targetUser.ShowUserLog(conn);

							System.out.print("이 사용자를 언팔로우 할까요? (y/n): ");
							choice = scanner.nextLine();

							if (choice.equals("y")) {
								targetUser.unfollow(conn, myUserId);
							} else if (choice.equals("n")) {
								System.out.println("목록으로 돌아갑니다.");
							} else {
								System.out.println("잘못된 입력입니다.");
							}
							break;
						}
					}
					if (isFollowing == 0)
						System.out.println("잘못된 ID입니다.");

				}
			} else if (choice.equals("0")) {
				break;
			} else {
				System.out.println("잘못된 입력입니다.");
				continue;
			}
		}

>>>>>>> main
		System.out.println("프로그램 종료");

		scanner.close();

		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}


