package ggoggoDB_WEB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	final private Connection conn;
	private String ID;
	
	public User(Connection conn, String iD, String pASSWORD) {
		super();
		this.conn = conn;
		ID = Login(iD, pASSWORD);
	}
	
	private String Login(String iD, String pASSWORD) {
		
		String result = null;
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		
		String sql = ""; // an SQL statement
		
		sql = "SELECT * from PJUSER where pjuserid=? and password=?"; //작은 따옴표여야 함
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, iD);
			pstmt.setString(2, pASSWORD);
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = iD;
			}
			rs.close();
			pstmt.close();	
		}catch(SQLException e){
			e.printStackTrace();
		}
		return result;
	}

	public String getID() {
		return ID;
	}

	
	
	
}
