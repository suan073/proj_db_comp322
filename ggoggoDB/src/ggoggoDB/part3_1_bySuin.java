package ggoggoDB;

import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;

import javax.security.auth.callback.LanguageCallback;

public class part3_1_bySuin {
    /*
        1. 필터 설정(language, isAdult, Media, Status)
        2. 입력어 검색(title,creator,keyword)
        3. 보여주는 순서는 work를 각 log의 like 합이 높은 순서대로 보여줌
    */


    // 3-1-1 sub-functions
    private static String fil_spel_2_String(String spel){
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

    private static void fil_show_table_list (Connection conn, String filter_name){
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

    private static boolean fil_isin_table_list (Connection conn, String filter_name, String input){
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

    private static void fil_set_one_category_filter (Connection conn, Scanner scan, String filter_name, FilterInfo userfilter){
        String input;

        System.out.println(filter_name+"에 대한 필터를 걸겠습니다.");
        
        System.out.println("필터 걸고 싶은 또는 제거 하고 싶은 것을 하나씩 입력해주세요");
        System.out.println("(해당 카테고리에 대한 필터 설정 변경의 종료를 원한다면 'end'를 입력하세요.)");
        
        while(true){
            fil_show_table_list(conn,filter_name);
            userfilter.show(filter_name);
            input = scan.nextLine();
            if(input.equals("end")){
                break;
            }
            if(fil_isin_table_list(conn,filter_name,input)){
                userfilter.change(filter_name, input);
            }
            else{
                System.out.println("잘못된 입력입니다. 다시입력해주세요");
            }
        }
    }


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
            filter_name = fil_spel_2_String(select);
            if(filter_name.equals("Fail")){
                System.out.println("잘못 입력하셨습니다 다시 입력해주세요.");
                continue;
            }
            if(filter_name.equals("END")){
                break;
            }
            fil_set_one_category_filter(conn, scan, filter_name, youFilterInfo);
        }
		System.out.println("********* 검색 전 필터 설정 완료 *********");

        return youFilterInfo;
    }

    /*
    private static String sear_spel_2_String(String spel){
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

    private static void sear_one_input_search(Connection conn, Scanner scan, String filter_name, FilterInfo userfilter){
        String input;

        System.out.println(filter_name+"에 대한 필터를 걸겠습니다.");
        System.out.println("필터 걸고 싶은 또는 제거 하고 싶은 것을 하나씩 입력해주세요");
        System.out.println("(해당 카테고리에 대한 필터 설정 변경의 종료를 원한다면 'end'를 입력하세요.)");
        
        while(true){
            fil_show_table_list(conn,filter_name);
            userfilter.show(filter_name);
            input = scan.nextLine();
            if(input.equals("end")){
                break;
            }
            if(fil_isin_table_list(conn,filter_name,input)){
                userfilter.change(filter_name, input);
            }
            else{
                System.out.println("잘못된 입력입니다. 다시입력해주세요");
            }
        }

        userfilter.show_all();
    }

    // 2. 입력어 검색(title, creator, keyword)
    public static void search(Connection conn, Scanner scan, FilterInfo filter){
        String select;
        String category_name;
        System.out.println("********* 3-1-2. 필터가 적용된 입력어 검색 시작 *********");

        while(true){
            System.out.println(">> 검색 파트 설정을 시작합니다.");
            System.out.println(">> [t : title, c: creator, k: keyword]");
            System.out.println(">> 중 필터를 걸기를 원하는 카태고리의 '첫 소문자'를 입력해주세요");
		    System.out.println(">> (설정 변경의 종료를 원한다면 'end'를 입력하세요.)");
            select = scan.nextLine();
            category_name = sear_spel_2_String(select);
            if(category_name.equals("Fail")){
                System.out.println("잘못 입력하셨습니다 다시 입력해주세요.");
                continue;
            }
            if(category_name.equals("END")){
                break;
            }
            sear_one_input_search
        }

    }



    */
}
