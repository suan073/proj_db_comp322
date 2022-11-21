package ggoggoDB;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkInfo {
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

    public WorkInfo(ResultSet rs){
        try {

            this.work_title = rs.getString("WORKTITLE");
			this.media = rs.getString("MEDIA");
            this.is_adult = rs.getString("ISADULT");
            this.introduction = rs.getString("INTRODUCTION");
            this.language = rs.getString("LANGUAGE");
            this.num_of_episode = rs.getInt("NUMOFEPISODE");
            this.status = rs.getString("STATUS");
            this.origin_ssn = rs.getString("ORIGINSSN");
            this.origin_title = rs.getString("ORIGINTITLE");
            this.connected_type = rs.getString("CONNECTTYPE");
            

		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    public void show(){
        StringBuffer result = new StringBuffer();
        result.append("\n*----------------------------------------");
        result.append("\n|   "+this.work_title);
        result.append("\n*------------------------------------------");
        result.append("\n| 매체 : "+this.media);
        result.append("\n| 미성년자 감상 제한 여부 : "+this.is_adult);
        result.append("\n| 작품 소개 \n"+this.introduction);
        result.append("\n| 언어 : "+this.language);
        if(num_of_episode != 1){
            result.append("\n| 에피소드 수 : " + this.num_of_episode);
        }
        result.append("\n| 현재 상태 : "+this.status);
        if(this.origin_ssn != null){
            result.append("\n| 해당 작품은 " + this.origin_title+ "의 "+this.connected_type+"입니다.");
        }
        result.append("\n*-----------------------------------------");
        System.out.println(result.toString());

    }

}
