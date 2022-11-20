package ggoggoDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import oracle.security.o3logon.a;

class sub_func_1{
    // 3-1-1 sub_functions : fil
     static String spel_2_String(String spel){
        String filt_string = "";
        switch(spel){
            case "l":
                filt_string = "LANGUAGE";
                break;
            case "i":
                filt_string = "ISADULT";
                break;
            case "m":
                filt_string = "MEDIA";
                break;
            case "s":
                filt_string = "STATUS";
                break;
            case "end":
                filt_string = "END";
                break;
            default:
                filt_string = "Fail";
                break;
        }
        return filt_string;
    }

     static void show_table_list (Connection conn, String filter_name){
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "";

        int cnt = 0;
        int oneline_max = 6;
        
        System.out.println("<< " + filter_name + " category list >>");
        sql = "Select distinct " + filter_name + " from WORK";
        try {
        
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                String item = rs.getString(1);
                System.out.printf("%20s  ",item);
                cnt++;
                if(cnt == oneline_max){
                    cnt = 0;
                    System.out.println();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

     static boolean isin_table_list (Connection conn, String filter_name, String input){
        boolean result = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "Select * from WORK where "+ filter_name +"= ?";
        try {
        
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, input);
            rs = pstmt.executeQuery();
            if(rs.next()){
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

     static void set_one_category_filter (Connection conn, Scanner scan, String filter_name, FilterInfo userfilter){
        String input;

        System.out.println(filter_name+"에 대한 필터를 걸겠습니다.");
        
        System.out.println("필터 걸고 싶은 또는 제거 하고 싶은 것을 하나씩 입력해주세요");
        System.out.println("(해당 카테고리에 대한 필터 설정 변경의 종료를 원한다면 'end'를 입력하세요.)");
        
        while(true){
            show_table_list(conn,filter_name);
            userfilter.show(filter_name);
            input = scan.nextLine();
            if(input.equals("end")){
                break;
            }
            if(isin_table_list(conn,filter_name,input)){
                userfilter.change(filter_name, input);
            }
            else{
                System.out.println("잘못된 입력입니다. 다시입력해주세요");
            }
        }
    }
}

class sub_func_2{
    // 3-1-2 sub-functions
    public static String spel_2_String(String spel){
        String filt_string = "";
        switch(spel){
            case "t":
                filt_string = "TITLE";
                break;
            case "c":
                filt_string = "CREATOR";
                break;
            case "k":
                filt_string = "KEYWORD";
                break;
            case "end":
                filt_string = "END";
                break;
            default:
                filt_string = "Fail";
                break;
        }
        return filt_string;
    }
    
    public static ArrayList<String> search_the_word(Connection conn, Scanner scan, FilterInfo filter, String category,String search_word){
        StringBuffer sql = new StringBuffer();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<String> result = new ArrayList<>();

        sql.append("select W.ssn, Worktitle, sumoflikes from ( select * from ( ");
        sql.append(filter.make_where_sql());
        switch(category){
            case "TITLE":
                sql.append(") where WORKTITLE like '%");
                break;
            case "CREATOR":
                sql.append(") W, creator C, made M where W.ssn = M.wssn and M.wssn = C.creatorid and crname like '%");
                break;
            case "KEYWORD":
                sql.append(") W, keyword K where W.ssn =  K.wssn and keyword like '%");
                break;
        }
        sql.append(search_word);
        sql.append("%' ");
        sql.append(") W, (select Writeabout, sum(likes) as sumoflikes from pjlog ");
        sql.append("group by Writeabout) O where W.ssn = O.Writeabout order by sumoflikes desc ");

        try {
			pstmt = conn.prepareStatement(sql.toString());
            rs = pstmt.executeQuery();

            int cnt = 0;
            while(rs.next()){
                String workTitle = rs.getString("WORKTITLE");
                System.out.println(cnt+". "+workTitle);
                result.add(rs.getString("SSN"));
                cnt++;
            }

		} catch (SQLException e) {
			e.printStackTrace();
		}
        return result;
    }

    public static void show_work_detail(Connection conn, ResultSet res, )

}
public class part3_1_bySuin {
    /*
        1. 필터 설정(language, isAdult, Media, Status)
        2. 입력어 검색(title,creator,keyword)
            이때, 보여주는 순서는 work를 각 log의 like 합이 높은 순서대로 보여줌
            검색 결과 중에서 워크를 선택하면 해당 워크의 상세 정보를 보여줌
    */

    // 1. 필터 설정(1 : language, 2 : isAdult, 3: Media, 4 : Status)
    public static FilterInfo settingFilter (Connection conn, Scanner scan){
        String select = "";
        String filter_name = "";
        
        System.out.println("********* 3-1-1. 검색 전 필터 설정 *********");
        
        
        FilterInfo youFilterInfo = new FilterInfo();

        while(true){
            System.out.println(">> 현재 필터 설정");
            youFilterInfo.show_all();

            System.out.println(">> 필터 설정을 시작합니다.");
            System.out.println(">> [l : language, i : isAdult, m: Media, s : Status]");
            System.out.println(">> 중 필터를 걸기를 원하는 카태고리의 '첫 소문자'를 입력해주세요");
		    System.out.println(">> (설정 변경의 종료를 원한다면 'end'를 입력하세요.)");
            select = scan.nextLine();
            filter_name = sub_func_1.spel_2_String(select);
            if(filter_name.equals("Fail")){
                System.out.println("잘못 입력하셨습니다 다시 입력해주세요.");
                continue;
            }
            if(filter_name.equals("END")){
                break;
            }
            sub_func_1.set_one_category_filter(conn, scan, filter_name, youFilterInfo);
        }
		System.out.println("********* 검색 전 필터 설정 완료 *********");

        return youFilterInfo;
    }

    // 2. 입력어 검색(title, creator, keyword) (3. 보여주는 순서는 work를 각 log의 like 합이 높은 순서대로 보여줌)
    public static ArrayList<String> search(Connection conn, Scanner scan, FilterInfo filter){
        ArrayList<String> result = null;
        String category_spel;
        String category_full;
        System.out.println(".\n.\n.\n");
        System.out.println("********* 3-1-2. 필터가 적용된 입력어 검색 시작 *********");
        
        while(true){
            System.out.println(">> 검색 파트를 선택해주세요.");
            System.out.println(">> [t : title, c: creator, k: keyword]");
            System.out.println(">> 중 검색하기를 원하는 카태고리의 '첫 소문자'를 입력해주세요");
            System.out.println(">> (검색의 종료를 원한다면 'end'를 입력하세요.)");
            category_spel = scan.nextLine();
            category_full = sub_func_2.spel_2_String(category_spel);
            if(category_full.equals("Fail")){
                System.out.println("잘못 입력하셨습니다 다시 입력해주세요.");
                continue;
            }
            if(category_full.equals("END")){
                break;
            }
            String input;
            System.out.println(category_full+"에서 검색하겠습니다.");
            System.out.println("검색할 단어를 입력해주세요");
            
            input = scan.nextLine();
            if(input.equals("end")){
                break;
            }
            System.out.println("검색어를 포함하는 "+category_full+"의 작품은 다음과 같습니다");

            result = sub_func_2.search_the_word( conn, scan, filter, category_full, input);

            System.out.println();
            System.out.println("해당 검색 결과 중 자세히 살펴보고 싶으시면 (Y)를 누르세요.");
            String YesOrNo = scan.nextLine();
            if(YesOrNo.equals("Y")){
                return result;
            }

            System.out.println();
            System.out.println("현재 검색 결과 기록을 지우고 새로운 검색을 시작합니다.");
            System.out.println();
            System.out.println(".\n.\n.\n");
        }
        return null;
    } 


}