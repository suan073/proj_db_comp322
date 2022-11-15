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
		
		part1_bySuin.login(conn);
		
		
	}
}


