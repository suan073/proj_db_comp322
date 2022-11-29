package ggoggoDB_WEB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class InterestedGenre {

	final private Connection conn;
	final private String ID;
	private ArrayList<ItemOfIG> listOfIG;
	
	public InterestedGenre(Connection conn, String iD) {
		super();
		this.conn = conn;
		ID = iD;
		listOfIG = new ArrayList<ItemOfIG>();
		resetListOfIGs();
	}
	
	private void resetListOfIGs () {
		listOfIG.clear();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";

		sql = "select g.genreid, g.genrename, i.pjuserid\n"
				+ "from Genre g\n"
				+ "left outer join \n"
				+ "    (select *\n"
				+ "    from interested\n"
				+ "    where pjuserid = ?) i\n"
				+ "on g.Genreid = i.Genreid";
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ID);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				boolean interest = true;
				if(rs.getString(3) == null) {
					interest = false;
				}
				ItemOfIG itemIg = new ItemOfIG(rs.getInt(1), rs.getString(2), interest);
				listOfIG.add(itemIg);
			}
			
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Collections.sort(listOfIG);
	}
	
	public ItemOfIG getItemOfIG(int index) {
		return listOfIG.get(index);
	}
	
	public int size() {
		System.out.println(listOfIG.size());
		return listOfIG.size();
	}
	
	public void changeInterest(int index, boolean interest) {
		listOfIG.get(index).setInterest(interest);
	}
	
	public void updateAll() {
		for (int i = 0; i <size(); i++) {
			if(getItemOfIG(i).isDirty()) {
				update(i);
				//commit해야함.
			}
		}
		resetListOfIGs();
	}
	
	public void update(int index) {
		ItemOfIG curIg = listOfIG.get(index);
		PreparedStatement pstmt = null; 
		String sql = "";
		if(curIg.isInterest()) {
			sql = "Insert into INTERESTED (PJUSERID,GENREID) values (?,?)";
		}
		else {
			sql = "delete from INTERESTED where PJUSERID=? and GENREID=?";
		}
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ID);
			pstmt.setInt(2, curIg.getGenreid());
			pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
