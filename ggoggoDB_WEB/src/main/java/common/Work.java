package common;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Work {
    private String work_title;
    private String media;
    private String is_adult;
    private String introduction;
    private String language;
    private int num_of_episode;
    private String status;
    private String origin_ssn;
    private String origin_title;
    private String connected_type;

    public Work(ResultSet rs){
        try {

            this.work_title = rs.getString(2);
			this.media = rs.getString(3);
            this.is_adult = rs.getString(4);
            this.introduction = rs.getString(5);
            this.language = rs.getString(6);
            this.num_of_episode = rs.getInt(7);
            this.status = rs.getString(8);
            this.origin_ssn = rs.getString(9);
            this.origin_title = rs.getString(13);
            this.connected_type = rs.getString(11);
            

		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    public String show(){
        StringBuffer result = new StringBuffer();
        result.append("<table border=1>");
        result.append("<tr><th colspan='2'>"+this.work_title+"</th></tr>");
        result.append("<tr><td>매체</td>");
        result.append("<td>"+this.media+"</td></tr>");
        result.append("<tr><td>미성년자 감상 제한 여부</td>");
        result.append("<td>"+this.is_adult+"</td></tr>");
        result.append("<tr><td colspan='2'>작품소개</td></tr>");
        result.append("<tr><td colspan='2'>"+this.introduction+"</td></tr>");
        result.append("<tr><td>언어</td>");
        result.append("<td>"+this.language+"</td></tr>");

        if(num_of_episode != 1){
        	result.append("<tr><td>에피소드 수 </td>");
            result.append("<td>"+this.num_of_episode+"</td></tr>");
        }
        result.append("<tr><td>현재 상태 </td>");
        result.append("<td>"+this.status+"</td></tr>");
        if(this.origin_ssn != null){
        	result.append("<tr><td>추가 정보 </td>");
            result.append("<td>해당 작품은 " + this.origin_title+ "의 "+this.connected_type+"입니다.</td></tr>");
        }
        result.append("</table>");
        result.append("<p><button type=\"button\" class=\"navyBtn\" onClick=\"location.href='search.jsp'\"> 돌아가기 </button></p>");
        return result.toString();

    }

}
