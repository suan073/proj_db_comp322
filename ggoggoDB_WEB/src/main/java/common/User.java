package common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import IG.InterestedGenre;

public class User {
	final private Connection conn;
	final private String ID;
	final private InterestedGenre iGenre;
	
	
	public User(Connection conn, String iD, String pASSWORD) {
		super();
		this.conn = conn;
		ID = Login(iD, pASSWORD);
		iGenre = new InterestedGenre(conn, iD);
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

	public Connection getConn() {
		return conn;
	}

	public InterestedGenre getiGenre() {
		return iGenre;
	}
	
}
