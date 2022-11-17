package ggoggoDB;

import java.sql.*;
import java.util.Scanner;

public class ggoggoDB {
	public static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	public static final String USER_UNIVERSITY ="ggoggoDB";
	public static final String USER_PASSWD ="comp322";
	
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
			conn.setAutoCommit(false); // 
		}catch(SQLException ex) {
			ex.printStackTrace();
			System.err.println("Cannot get a connection: " + ex.getLocalizedMessage());
			System.err.println("Cannot get a connection: " + ex.getMessage());
			System.exit(1);
		}
		
		
		UserInformation login = part1_bySuin.login(conn, scan);
//		UserInformation login = new UserInformation("momomo", "abcdef", true); // test를 위한 hard coding
		System.out.println(login.isVaild());
		System.out.println(login.getID());
		
		if (login.isVaild()) {
			System.out.println("로그인 완료");
			System.out.println("장르설정");
			part2_bySuin.edit_interested_genre(conn, login, scan);
		}
		
		
		System.out.println("프로그램 종료");
	}
}


