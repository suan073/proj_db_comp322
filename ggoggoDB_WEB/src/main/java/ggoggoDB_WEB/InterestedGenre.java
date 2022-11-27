package ggoggoDB_WEB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InterestedGenre {
	
	final private Connection conn;
	final private String ID;
	
	public InterestedGenre(Connection conn, String iD) {
		super();
		this.conn = conn;
		ID = iD;
	}
	
	public ArrayList<String> get_ArrayList_of_Interested_Genre (boolean reverse) {
		/*
		 * 	0번째에는 해당 리스트의 설명이 들어가 있음 
		 */
		ArrayList<String> resultArrayList = new ArrayList<>(); 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		String print_memo = "";

		if(reverse){
			sql = "(select g.genrename \n"
				+ "from Genre G, interested I\n"
				+ "where G.Genreid = I.Genreid)\n"
				+ "minus\n"
				+ "(select g.genrename \n"
				+ "from Genre G, interested I\n"
				+ "where pjuserID = ?\n"
				+ "and G.Genreid = I.Genreid)";
			print_memo = "<< Your Not Interested Genre >>";
		}else{
			sql = "select g.genrename \n"
				+ "from Genre G, interested I\n"
				+ "where pjuserID = ?\n"
				+ "and G.Genreid = I.Genreid";
			print_memo = "<< Your Interested Genre >>";
		}
		try {
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,ID);
			rs = pstmt.executeQuery();
			resultArrayList.add(print_memo);
			while(rs.next()) {
				String Genre = rs.getString(1);
				resultArrayList.add(Genre);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultArrayList;
	}
	
	public boolean is_your_interest (int gID) {
		boolean result = false;

		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		
		String sql = "select * \n"
				+ "from interested \n"
				+ "where pjuserID = ?\n"
				+ "and Genreid = ?";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ID);
			pstmt.setInt(2, gID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = true;
			}
			pstmt.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void insert_interested_table (int gID) {
		PreparedStatement pstmt = null; 
		String sql = "Insert into INTERESTED (pjuserID,GENREID) values (?,?)";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ID);
			pstmt.setInt(2, gID);
			pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void delete_interested_table (int gID) {
		PreparedStatement pstmt = null; 
		String sql = "delete from INTERESTED where pjuserID=? and GENREID=?";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ID);
			pstmt.setInt(2, gID);
			pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	

}
