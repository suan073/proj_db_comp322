package ggoggoDB;

import java.util.ArrayList;
import java.util.Arrays;


public class FilterInfo {
    private final ArrayList<String> language;
    private final ArrayList<String> isAdult; 
    private final ArrayList<String> media; 
    private final ArrayList<String> status; 

    public FilterInfo(){
        this.language = new ArrayList<>();
        this.isAdult = new ArrayList<>();
        this.media = new ArrayList<>();
        this.status = new ArrayList<>();
    }
    
    public ArrayList<String> get_info(String select){
        ArrayList<String> result = null;
        switch(select){
            case "LANGUAGE":
                result = this.language;
                break;
            case "ISADULT":
                result = this.isAdult;
                break;
            case "MEDIA":
                result = this.media;
                break;
            case "STATUS":
                result = this.status;
                break;
            default:
                break;
        }
        return result;
    }

    public void change (String select, String input){
        ArrayList<String> selected_list = null;
        switch(select){
            case "LANGUAGE":
            selected_list = this.language;
            break;
            case "ISADULT":
            selected_list = this.isAdult;
            break;
            case "MEDIA":
            selected_list = this.media;
            break;
            case "STATUS":
            selected_list = this.status;
            break;
            default:
            break;
        }
        if(selected_list.contains(input)){
            //delete
            selected_list.remove(input);
        }else{
            //insert
            selected_list.add(input);
        }
    }

    public void show_all (){
        String[] type_names = {"language","isAdult","media","status"};
        ArrayList<ArrayList<String>> types = new ArrayList<ArrayList<String>>(); 
        types.add(this.language);
        types.add(this.isAdult);
        types.add(this.media);
        types.add(this.status);
        System.out.println("<< 필터 >>");

        int cnt = 0;
		int oneline_max = 6;

        for (int j = 0; j < types.size(); j++ ){
            System.out.println("* "+type_names[j]+" *");
            ArrayList<String> type = types.get(j);
            if(type.size() == 0){
                System.out.println("there are not any filtered item yet.");
                continue;
            }
            for(int i = 0; i < type.size(); i++){
                String item = type.get(i);
				System.out.printf("%20s  ",item);
				cnt++;
				if(cnt == oneline_max){
					cnt = 0;
					System.out.println();
				}
            }
            System.out.println();
        }

    }

    public void show (String selected){
        ArrayList<String> type = get_info(selected); 
        System.out.println("<< 필터 about "+selected+" >>");

        int cnt = 0;
		int oneline_max = 6;    
        
        if(type.size() == 0){
            System.out.println("there are not any filtered item yet.");
            return;
        }
        for(int i = 0; i < type.size(); i++){
            String item = type.get(i);
            System.out.printf("%20s  ",item);
            cnt++;
            if(cnt == oneline_max){
                cnt = 0;
                System.out.println();
            }
        }
        System.out.println();
    }
}