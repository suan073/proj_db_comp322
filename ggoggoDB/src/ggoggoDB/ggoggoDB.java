package ggoggoDB;

import java.sql.*;
import java.util.Scanner;

public class ggoggoDB {
	public static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	public static final String USER_UNIVERSITY ="ggoggoDB";
	public static final String USER_PASSWD ="comp322";
	
	public static void main(String[] args) {
		Connection conn = null; // Connection object
		//Statement stmt = null;	// Statement object
		String sql = ""; // an SQL statement 
		String ID = "";
		String inputed_pw = "";
		String password = "";
		
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
	}
}
