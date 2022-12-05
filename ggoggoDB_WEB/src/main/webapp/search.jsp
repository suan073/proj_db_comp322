<%@page import="java.util.ArrayList"%>
<%@ page import="Search.*"%>
<%@ page import="common.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>search</title>
</head>
<body>
<%	
	String SearchFlag = request.getParameter("SearchFlag");
	String writingFlag = request.getParameter("writingFlag");
	String nextJsp = null;
	String pageTitle = null;
	String subText = null;
	if(writingFlag != null){
		nextJsp = "writeLog.jsp";
		pageTitle = "작품 탐색 (게시글 작성용)";
		subText = "선택하기";
	}else{
		nextJsp = "workShow.jsp";
		pageTitle = "작품 탐색";
		subText = "상세보기";
		out.print("<h3><button type=\"button\" onclick=\"location='Menu.jsp'\">메인 메뉴로 돌아가기</button></h3>");
	}
	out.print("<h2>"+pageTitle+"</h2>");
%>
<h3>- 필터 -</h3>
<form action="searchCheck.jsp" method="post">
<%
	
	JdbcConnect jdbc = (JdbcConnect)session.getAttribute("jdbc");
	User user = (User)session.getAttribute("user");
	

	if(writingFlag != null){
		out.print("<input type=\"hidden\" name=\"writingFlag\" value="+writingFlag+">");
	}
	// 필
	out.print("<h5>언어 : ");
	ArrayList<String> langList = FilterForSearch.getFilterExample(jdbc.getConn(), "language"); 
	for(int i = 0 ; i <langList.size(); i++){
		String item = langList.get(i);
		out.print("<label><input type=\"checkbox\" value="+item+" name=\"langcheckbox\">"+item+"</label> &nbsp&nbsp");
	}
	out.print("</h5>");
	
	out.print("<h5>연령 제한 : ");
	ArrayList<String> AdultList = FilterForSearch.getFilterExample(jdbc.getConn(), "isAdult"); 
	for(int i = 0 ; i <AdultList.size(); i++){
		String item = AdultList.get(i);
		out.print("<label><input type=\"checkbox\" value="+item+" name=\"adulcheckbox\">"+item+"</label> &nbsp&nbsp");
	}	
	out.print("</h5>");
	
	out.print("<h5>매체 : ");
	ArrayList<String> mediaList = FilterForSearch.getFilterExample(jdbc.getConn(), "media"); 
	for(int i = 0 ; i <mediaList.size(); i++){
		String item = mediaList.get(i);
		out.print("<label><input type=\"checkbox\" value="+item+" name=\"medicheckbox\">"+item+"</label> &nbsp&nbsp");
	}
	out.print("</h5>");
	
	out.print("<h5>상태 : ");
	ArrayList<String> statusList = FilterForSearch.getFilterExample(jdbc.getConn(), "status"); 
	for(int i = 0 ; i <statusList.size(); i++){
		String item = statusList.get(i);
		out.print("<label><input type=\"checkbox\" value="+item+" name=\"statcheckbox\">"+item+"</label> &nbsp&nbsp");
	}
	out.print("</h5>");
%>
<h3>- 검색할 범위 -</h3>
<label><input type="radio" name="sradio" value="TITLE" required> 제목 </label>
<label><input type="radio" name="sradio" value="CREATOR"> 창작자 </label>
<label><input type="radio" name="sradio" value="KEYWORD"> 키워드 </label>

<h3>- 검색어 입력 -</h3>
<input type="text" name="inputWord" >
<input type="submit" value="검색">
</form>
<%
	if(SearchFlag != null){
		ArrayList<ResultItem> result = null;
		if(session.getAttribute("searchResult") != null){
			 result = (ArrayList<ResultItem>)session.getAttribute("searchResult");
		}
		out.print("<table border=1>");
		for(int index = 0; index<result.size(); index++){
			out.print("<tr>");
			out.print("<td width=1000 height=50>"+index+". "+result.get(index).getContent()+"</td>");
			out.print("<td><form action=\""+nextJsp+"\" method=\"post\">");
			out.print("<input type=\"hidden\" name=\"ssn\" value="+result.get(index).getSsn()+">");
			out.print("<input type=\"hidden\" name=\"sContent\" value=\""+result.get(index).getTitle()+"\">");
			out.print("<input type=\"submit\" value=\""+subText+"\"> </form></td>");
			out.print("</tr>");
		}
		out.print("</table>");
	}
%>

</body>
</html>