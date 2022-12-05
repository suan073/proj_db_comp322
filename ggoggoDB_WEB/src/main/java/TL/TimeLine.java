package TL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import common.Log;

public class TimeLine {
	int logNum;
	String userId;
	Connection conn;

	public TimeLine(Connection conn, String userId) {
		logNum = 0;
		this.conn = conn;
		this.userId = userId;
		try {
			Statement stmt = conn.createStatement();
			String sql = "select * from pjlog";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next())
				logNum++;
			stmt.close();
			rs.close();
		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}

	// new method in Ph4
	public List<Log> allBoard() {
		List<Log> logs = new ArrayList<Log>();
		
		try {
			int logNum = 0;
			PreparedStatement pstmt = null;
			StringBuffer sql = new StringBuffer();
			sql.append("select * from ((select *\n");
			sql.append("from pjlog\n");
			sql.append("where pjpublic='Y' and writerid in (select pjuserid\n");
			sql.append("                from follow\n");
			sql.append("                where followerid=?))\n");
			sql.append("union\n");
			sql.append("(select L.*\n");
			sql.append("from pjlog L, have H\n");
			sql.append("where pjpublic='Y' and L.writeabout = H.wssn\n");
			sql.append("and H.genreid in (select I.genreid \n");
			sql.append("                from interested I\n");
			sql.append("                where I.pjuserid = ?))) \n");
			sql.append("order by pjlogdate desc");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, userId);
			pstmt.setString(2, userId);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next() && logNum < 30) {
				Log log = new Log(rs,conn);
				logs.add(log);
				//System.out.print();
			}
			pstmt.close();
			rs.close();
		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
		
		return logs;
	}
	// New method in Ph4
}
