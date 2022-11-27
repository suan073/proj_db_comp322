package ggoggoDB_WEB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User {
	final private Connection conn;
	private String ID;
	private InterestedGenre iGenre;
	
	public User(Connection conn, String iD, String pASSWORD) {
		super();
		this.conn = conn;
		ID = Login(iD, pASSWORD);
		iGenre = null;
		if(ID != null) { iGenre = new InterestedGenre(conn,ID); }
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
	
	public ArrayList<String> get_ArrayList_of_Interested_Genre (boolean reverse) {
		return iGenre.get_ArrayList_of_Interested_Genre(reverse);
	}
	
	public boolean edit_interested_genre (String inputed_genre) {
		int gID = -1;

		gID = Genre.genre_name_to_id(conn, inputed_genre);
		if(gID == -1) { return false; }
		if( iGenre.is_your_interest(gID) ){
			iGenre.delete_interested_table(gID);
		}else{
			iGenre.insert_interested_table(gID);
		}
		return true;
	}
	
	
}
