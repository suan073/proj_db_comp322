package Search;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.Work;


public class Searcher {
	final private Connection conn;
	final private FilterForSearch filter;

	public Searcher(Connection conn, 
			ArrayList<String> lang, ArrayList<String> adult, 
			ArrayList<String> media, ArrayList<String>status) {
		super();
		this.conn = conn;
		this.filter = new FilterForSearch();

		filter.setLanguage(lang);
		filter.setIsAdult(adult);
		filter.setMedia(media);
		filter.setStatus(status);
	}
	
	public ArrayList<ResultItem> getSearchResult(String category, String search_word) { 
		//짝수자리 제목, 홀수자리 ssn
		StringBuffer sql = new StringBuffer();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<ResultItem> result = new ArrayList<>();

        sql.append("select W.ssn, Worktitle, sumoflikes ");
        switch(category){
            case "TITLE":
                break;
            case "CREATOR":
                sql.append(", crname ");
                break;
            case "KEYWORD":
                sql.append(", keyword ");
                break;
        }
        sql.append("from ( select * from ( ");
        sql.append(filter.make_where_sql());
        switch(category){
            case "TITLE":
                sql.append(") where WORKTITLE like '%");
                break;
            case "CREATOR":
                sql.append(") W, creator C, made M where W.ssn = M.wssn and M.creatorid = C.creatorid and crname like '%");
                break;
            case "KEYWORD":
                sql.append(") W, keyword K where W.ssn =  K.wssn and keyword like '%");
                break;
        }
        sql.append(search_word);
        sql.append("%' ");
        sql.append(") W, (select Writeabout, sum(likes) as sumoflikes from pjlog ");
        sql.append("group by Writeabout) O where W.ssn = O.Writeabout order by sumoflikes desc ");
        System.out.println(sql.toString());
        try {
			pstmt = conn.prepareStatement(sql.toString());
            rs = pstmt.executeQuery();

            while(rs.next()){
                String workTitle = rs.getString(2);
                switch(category){
                    case "TITLE":
                        break;
                    case "CREATOR":
                        workTitle = rs.getString(4) + " - " + workTitle;
                        break;
                    case "KEYWORD":
                        workTitle = rs.getString(4) + " - " + workTitle;
                        break;
                }
                result.add(new ResultItem(rs.getString(1), workTitle));
            }

		} catch (SQLException e) {
			e.printStackTrace();
		}
        return result;
    
	}
	
	public static Work getStringShowOneWorkDetail(Connection conn, String ssn) {
		Work work = null;
        StringBuffer sql = new StringBuffer();
        sql.append("select * \n"
        		+ "    from work N\n"
        		+ "    left outer join\n"
        		+ "    (select ssn, worktitle as origintitle from work) O\n"
        		+ "    on N.originssn = O.ssn\n"
        		+ "where N.ssn = ?");
        try {
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, ssn);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                work = new Work(rs);
            }
            rs.close();
            pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return work;
	}
	
}
