package ggoggoDB_WEB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Genre {

	public static int genre_name_to_id (Connection conn, String gname) {
		int id = -1;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select genreid\n"
					+ "from genre\n"
					+ "where genrename = ?";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, gname);
			rs = pstmt.executeQuery();
			if(rs.next()){
				id = rs.getInt(1);
			}
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}

		return id;
	}

}
