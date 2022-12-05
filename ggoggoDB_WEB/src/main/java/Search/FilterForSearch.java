package Search;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class FilterForSearch {

    private ArrayList<String> language;
    private ArrayList<String> isAdult; 
    private ArrayList<String> media; 
    private ArrayList<String> status; 

	public static ArrayList<String> getFilterExample (Connection conn, String filter_name){
		ArrayList<String> reArrayList = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "Select distinct " + filter_name + " from WORK";
        try {
        
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                String item = rs.getString(1);
                reArrayList.add(item);            
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reArrayList;
    }

	public ArrayList<String> getLanguage() {
		return language;
	}

	public void setLanguage(ArrayList<String> language) {
		this.language = language;
	}

	public ArrayList<String> getIsAdult() {
		return isAdult;
	}

	public void setIsAdult(ArrayList<String> isAdult) {
		this.isAdult = isAdult;
	}

	public ArrayList<String> getMedia() {
		return media;
	}

	public void setMedia(ArrayList<String> media) {
		this.media = media;
	}

	public ArrayList<String> getStatus() {
		return status;
	}

	public void setStatus(ArrayList<String> status) {
		this.status = status;
	}

	private int count_filtered(){
        int result = 0;
       
        ArrayList<ArrayList<String>> types = new ArrayList<ArrayList<String>>(); 
        types.add(this.language);
        types.add(this.isAdult);
        types.add(this.media);
        types.add(this.status);

        for (int j = 0; j < types.size(); j++ ){
            result += types.get(j).size();
        }

        return result;
    }

    public String make_where_sql(){
        StringBuffer sql = new StringBuffer();
        sql.append("select * from work");

        if(count_filtered() == 0){
            return sql.toString();
        }

        String[] type_names = {"language","isAdult","media","status"};
        ArrayList<ArrayList<String>> types = new ArrayList<ArrayList<String>>(); 
        types.add(this.language);
        types.add(this.isAdult);
        types.add(this.media);
        types.add(this.status);
        
        sql.append(" where ");
        
        boolean startedTypes = false;
        for (int j = 0; j < types.size(); j++ ){
            ArrayList<String> type = types.get(j);
            if(type.size() == 0){
                continue;
            }
            if(startedTypes) {
            	sql.append(" and ");
            }
            startedTypes = true;
            sql.append(" (");
            
            for(int i = 0; i < type.size(); i++){
                String item = type.get(i);
                if(i != 0){
                    sql.append(" or ");
                }
                sql.append(type_names[j] + "= '" +item + "' ");
            }
            
            sql.append(") ");
        }
        System.out.println(sql.toString());
        return sql.toString();
    }

}