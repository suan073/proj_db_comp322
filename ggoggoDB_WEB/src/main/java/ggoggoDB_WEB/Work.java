package ggoggoDB_WEB;

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

//    public void show(){
//        StringBuffer result = new StringBuffer();
//        result.append("\n*----------------------------------------");
//        result.append("\n|   "+this.work_title);
//        result.append("\n*------------------------------------------");
//        result.append("\n| 매체 : "+this.media);
//        result.append("\n| 미성년자 감상 제한 여부 : "+this.is_adult);
//        result.append("\n| 작품 소개 \n"+this.introduction);
//        result.append("\n| 언어 : "+this.language);
//        if(num_of_episode != 1){
//            result.append("\n| 에피소드 수 : " + this.num_of_episode);
//        }
//        result.append("\n| 현재 상태 : "+this.status);
//        if(this.origin_ssn != null){
//            result.append("\n| 해당 작품은 " + this.origin_title+ "의 "+this.connected_type+"입니다.");
//        }
//        result.append("\n*-----------------------------------------");
//        System.out.println(result.toString());
//
//    }

}
