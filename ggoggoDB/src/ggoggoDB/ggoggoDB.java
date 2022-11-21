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
 		UserInfo login = Login.login(conn, scan);
		System.out.println(login.isVaild());
		System.out.println(login.getID());
		
		/* 2. 장르 설정*/
		if (login.isVaild()) {
			System.out.println("로그인 완료");
			System.out.println("장르설정");
			InterestedGernesSetting.edit_interested_genre(conn, login, scan);

			OpenBoard openboard = new OpenBoard(conn);
			MyPage mypage = new MyPage(conn, login.getID());

			while(true){
				System.out.println("0.선호 장르 변경 1. 검색, 2. 타임라인 확인, 3. 게시판 확인, 4.마이페이지 5.종료");
				int select = scan.nextInt();
				scan.nextLine();
				switch(select){
					case 0:
						InterestedGernesSetting.edit_interested_genre(conn, login, scan);
						break;
					case 1:
						FilterInfo temp = Searching.settingFilter(conn, scan);
						Searching.search(conn, scan, temp);
						break;
					case 2:
						//
						break;
					case 3:
						openboard.executeOpenBoard(conn, scan, login.getID());
						break;
					case 4:
						mypage.executeMyPage(conn, scan);

						break;
					case 5:
						break;
				}
				if(select==5){
					break;
				}
			}
			
		}
		System.out.println("프로그램 종료");

		try {
			scan.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}


