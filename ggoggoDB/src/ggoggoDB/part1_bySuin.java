package ggoggoDB;

import java.sql.*;
import java.util.Scanner;

public class part1_bySuin {

	private static boolean check_password_correct(Connection conn, String iID, String iPW){
		// 로그인 성공시 true, 실패시 false를 반환한다.

		boolean result = false;
		PreparedStatement pstmt = null; 
		ResultSet rs = null;

		String sql = ""; // an SQL statement

		sql = "SELECT * from PJUSER where pjuserid=? and password=?"; //작은 따옴표여야 함
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, iID);
			pstmt.setString(2, iPW);
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

	public static UserInfo login(Connection conn, Scanner scan) { 
		String ID = "";
		String inputed_pw = "";
		UserInfo result = null;
		
		System.out.println("********* 로그인 정보를 입력하시오. *********");
		System.out.print("ID       : ");
		ID = scan.nextLine();
		System.out.print("password : ");
		inputed_pw = scan.nextLine();	
		
		System.out.println("********* 로그인 정보를 확인하겠습니다. *********");
		
		result = new UserInfo(ID, inputed_pw, check_password_correct(conn, ID, inputed_pw));
	
		
		return result;
	}

}
