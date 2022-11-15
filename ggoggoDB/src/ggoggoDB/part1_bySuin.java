package ggoggoDB;

import java.sql.*;
import java.util.Scanner;

public class part1_bySuin {
	public static void login(Connection conn) {
		String sql = ""; // an SQL statement 
		String ID = "";
		String inputed_pw = "";
		String password = "";
		
		Scanner scan = new Scanner(System.in);
		
		System.out.print("ID       : ");
		ID = scan.nextLine();
		System.out.print("password : ");
		inputed_pw = scan.nextLine();	
		
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
	}
}
