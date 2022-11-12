package ggoggoDB;

import java.sql.*;

public class ggoggoDB {
	public static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	public static final String USER_UNIVERSITY ="ggoggoDB";
	public static final String USER_PASSWD ="comp322";
	
	public static void main(String[] args) {
		Connection conn = null; // Connection object
		//Statement stmt = null;	// Statement object
		String sql = ""; // an SQL statement 
		String ID = "ginger00";
		
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
		
		try {
			
			sql = "SELECT PASSWORD from PJUSER";
			PreparedStatement ps = conn.prepareStatement(sql);
			//ps.setString(1, ID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String password = rs.getString(1);
				System.out.println("ID = " + ID 
								+  ", Password = " + password);
			}
			ps.close();
			rs.close();			
		}catch(SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}
}
