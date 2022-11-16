package ggoggoDB;

import java.sql.*;
import java.util.Scanner;

public class part1_bySuin {
	public static boolean login(Connection conn) { // 로그인 성공시 true, 실패시 false를 반환한다.
		
		
		String sql = ""; // an SQL statement 
		String ID = "";
		String inputed_pw = "";
		String password = "";
		boolean login = false;
		
		Scanner scan = new Scanner(System.in);
		System.out.print("********* 로그인 정보를 입력하시오. *********");
		System.out.print("ID       : ");
		ID = scan.nextLine();
		System.out.print("password : ");
		inputed_pw = scan.nextLine();	
		
		System.out.print("********* 로그인 정보를 확인하겠습니다. *********");
		try {
			sql = "SELECT PASSWORD from PJUSER where pjuserid=?"; //작은 따옴표여야 함
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, ID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				password = rs.getString("password");
				System.out.println("correct Password : " + password);
			}
			// 무조건 커밋 돌려놓자...
			if (inputed_pw.equals(password)) {
				System.out.println("login success!");
				login = true;
			}
			else {
				System.out.println("login fail..");
			}
			rs.close();
			ps.close();			
		}catch(SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
		scan.close();
		
		return login;
	}
	
	public static void edit_interested_genre (Connection conn) {
		
		
	}
}
