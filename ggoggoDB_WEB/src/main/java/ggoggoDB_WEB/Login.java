package ggoggoDB_WEB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
	final private Connection conn;
	private String ID;
	private String PASSWORD;
	private boolean Valid;
	
	public Login(Connection conn, String iD, String pASSWORD) {
		super();
		this.conn = conn;
		ID = iD;
		PASSWORD = pASSWORD;
		Valid = isCorrect();
	}

	public String getID() {
		return ID;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public boolean isValid() {
		return Valid;
	}

	private boolean isCorrect() {

		boolean result = false;
		PreparedStatement pstmt = null; 
		ResultSet rs = null;

		String sql = ""; // an SQL statement

		sql = "SELECT * from PJUSER where pjuserid=? and password=?"; //작은 따옴표여야 함
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ID);
			pstmt.setString(2, PASSWORD);
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = true;
			}
			rs.close();
			pstmt.close();	
		}catch(SQLException e){
			e.printStackTrace();
		}
		return result;
	}
	
}
