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
// 		UserInfo login = part1_bySuin.login(conn, scan);
		
		// test를 위한 hard coding
		UserInfo login = new UserInfo("momomo", "abcdef", true); 
//		System.out.println(login.isVaild());
//		System.out.println(login.getID());
		
		/* 2. 장르 설정*/
//		if (login.isVaild()) {
//			System.out.println("로그인 완료");
//			System.out.println("장르설정");
//			part2_bySuin.edit_interested_genre(conn, login, scan);
//		}
		/* 3. 검색 (1) 필터 설정 */
		//FilterInfo temp = part3_1_bySuin.settingFilter(conn, scan);
		//temp.show_all();
		
		System.out.println(part3_1_bySuin.search2(conn, scan));
		//part3_1_bySuin.search(conn, scan, temp);

		/* main work */
		String myUserId = "XoOoOong"; // arbitrary id
		OpenBoard openboard = new OpenBoard(conn);
		MyPage mypage = new MyPage(conn, myUserId);

		/* Main3. OpenBoard */
		openboard.executeOpenBoard(conn, scan, myUserId);

		/* Main4. MyPage */
		mypage.executeMyPage(conn, scan);

		System.out.println("프로그램 종료");

		try {
			scan.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}


