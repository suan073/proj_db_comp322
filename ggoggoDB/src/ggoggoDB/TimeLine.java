package ggoggoDB;

import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class TimeLine {
    /*
     * 시간순서대로 정렬
     * 들어갈거
     * -팔로우의 로그들
     * -like이 일정 수를 넘은 로그 (유저가 좋아하는 장르만)
     */
	public static void show(Connection conn, String userID) {
		PreparedStatement pstmt = null;
		StringBuffer sql = new StringBuffer();
		sql.append("(select writerid, pjlogtitle, pjcontents, pjlogdate, pjlogid\n");
		sql.append("from pjlog\n");
		sql.append("where pjpublic='Y' and writerid in (select pjuserid\n");
		sql.append("                from follow\n");
		sql.append("                where followerid=?))\n");
		sql.append("union\n");
		sql.append("(select writerid, pjlogtitle, pjcontents, pjlogdate, pjlogid\n");
		sql.append("from pjlog L, have H\n");
		sql.append("where pjpublic='Y' and L.writeabout = H.wssn\n");
		sql.append("and H.genreid in (select I.genreid \n");
		sql.append("                from interested I\n");
		sql.append("                where I.pjuserid = ?)) \n");
		sql.append("order by pjlogdate desc");
		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, userID);
			pstmt.setString(2, userID);
			ResultSet rs = pstmt.executeQuery();
			showBoard(rs);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static int[] showBoard(ResultSet rs) {
		int[] logs = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
		try {
			int logNum = 0;
			
			System.out.println();
			System.out.println("* 타임라인 *");
			System.out.println("--------------------");
			while (rs.next() && logNum < 10) {
				int logid = rs.getInt(5);
				logs[logNum] = logid;
				String writerID = rs.getString(1);
				Date date = rs.getDate(4);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				System.out.println("글번호 #" + logid + "  제목: " + title);
				System.out.println("작성자:" + writerID + "\t    " + date);
				System.out.println(contents);
				System.out.println();
				logNum++;
			}
			rs.close();
		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
		return logs;
	}
	
     
}
