package ggoggoDB;

import java.sql.*;
import java.util.Scanner;

public class InterestedGenresSetting {

    private static void show_interested_genre (Connection conn, UserInfo info, boolean reverse) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int cnt = 0;
		int oneline_max = 6;
		String sql = "";
		String print_memo = "";

		if(reverse){
			sql = "(select g.genrename \n"
				+ "from Genre G, interested I\n"
				+ "where G.Genreid = I.Genreid)\n"
				+ "minus\n"
				+ "(select g.genrename \n"
				+ "from Genre G, interested I\n"
				+ "where pjuserid = ?\n"
				+ "and G.Genreid = I.Genreid)";
			print_memo = "<< Your Not Interested Genre >>";
		}else{
			sql = "select g.genrename \n"
				+ "from Genre G, interested I\n"
				+ "where pjuserid = ?\n"
				+ "and G.Genreid = I.Genreid";
			print_memo = "<< Your Interested Genre >>";
		}
		try {
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, info.getID());
			rs = pstmt.executeQuery();
			
			System.out.println(print_memo);
			while(rs.next()) {
				String Genre = rs.getString(1);
				System.out.printf("%25s  ",Genre);
				cnt++;
				if(cnt == oneline_max){
					cnt = 0;
					System.out.println();
				}
			}
			
			System.out.println();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static int genre_name_to_id (Connection conn, String gname) {
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
	
	private static boolean is_there_tuple (Connection conn, String userID, int gID) {
		boolean result = false;

		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		
		String sql = "select * \n"
				+ "from interested \n"
				+ "where pjUserid = ?\n"
				+ "and Genreid = ?";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
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
	
	private static void insert_interested_table (Connection conn, String userID, int gID) {
		PreparedStatement pstmt = null; 
		String sql = "Insert into INTERESTED (PJUSERID,GENREID) values (?,?)";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			pstmt.setInt(2, gID);
			pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	private static void delete_interested_table (Connection conn, String userID, int gID) {
		PreparedStatement pstmt = null; 
		String sql = "delete from INTERESTED where PJUSERID=? and GENREID=?";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			pstmt.setInt(2, gID);
			pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public static void edit_interested_genre (Connection conn, UserInfo info, Scanner scan ) {
		String genre = "start";
		int gID = -1;
		
		while (true) {
			System.out.println("********* 2 선호장르 변경 *********");
			
			// 1. 선호 장르 보여주기
			System.out.println("다음은 당신의 선호 장르입니다.");
			show_interested_genre(conn, info ,false);

			// 2. 비선호 장르 보여주기
			System.out.println("다음은 당신의 비선호 장르입니다.");
			show_interested_genre(conn, info, true);
			
			// 3. 선호도를 바꾸기를 원하는 장르를 string 형태로 받아들이도록 함
			System.out.println("위의 리스트의 장르 중 선호유무를 변경시킬 장르를 입력하세요.");
			System.out.println("(설정 변경의 종료를 원한다면 'end'를 입력하세요.)");
			genre = scan.nextLine();
			if(genre.equals("end")){
				break;
			}

			// 4. 바꾸기로 한 장르의 id를 찾고 interested에 유저가 id 장르를 선호하는지 확인한다.
			gID = genre_name_to_id(conn, genre);
			while(gID == -1){
				System.out.println("잘못 입력하셨습니다 다시 입력해주세요.");
				genre = scan.nextLine();
				if(genre.equals("end")){
					break;
				}
				gID = genre_name_to_id(conn, genre);
			}
			
			if(genre.equals("end")){
				break;
			}

			// 5. 해당 tuple이 있다면 table에서 삭제하고, 없다면 삽입한다. 
			String userID = info.getID();
			if( is_there_tuple(conn, userID, gID) ){
				delete_interested_table(conn, userID, gID);
			}else{
				insert_interested_table(conn, userID, gID);
			}
		}

		System.out.println("********* 장르설정 종료 *********");
	}

}
