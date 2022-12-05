<%@ page import="common.JdbcConnect"%>
<%@ page import="common.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
JdbcConnect jdbc = (JdbcConnect)session.getAttribute("jdbc");
User user = (User)session.getAttribute("user");
String userId = user.getID();
session.setAttribute("userId", userId);
OpenBoard openboard = new OpenBoard(jdbc.getConn(), userId);
session.setAttribute("openboard", openboard);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>OpenBoard</title>
</head>
<body>
	<%
	out.print("<h3><button type=\"button\" onclick=\"location='Menu.jsp'\">메인 메뉴로 돌아가기</button></h3>");

	List<Log> logs;
	String x= request.getParameter("searchLog");
	
	out.print("<h2>게시판 <button onclick=\"location='writeLog.jsp'\">글쓰기</button> </h2>");
	out.print("<form method=\"post\">");
	out.print("<h3>검색어 입력</h3>"); 
	out.print("<input type=\"text\" name=\"search\" required value=\"\">");
	out.print("<button type=\"submit\" name=\"searchLog\" value=\"doSearch\">검색</button>");
	out.print("</form>");
	
	String like= request.getParameter("like");
	if(like != null){
		Log e = new Log(jdbc.getConn(), Integer.parseInt(like));
		e.pushLike(jdbc.getConn());
		like = null;
	}
	
	if(x != null && x.equals("doSearch")){
		logs = openboard.searchLog(request.getParameter("search"));
		out.print("<h3>" + request.getParameter("search") + " 검색 결과 <button type=\"button\" onclick=\"location='openBoard.jsp'\">게시판 돌아가기</button></h3>");
	} else{
		logs = openboard.allBoard();
	}
	
	out.print("<table border=1>");
	for(Log e : logs){
		out.print("<tr>");
		out.print("<td>");
		
		out.print(e.show(0));
		
		out.print("</td>");
		out.print("</tr>");
	}
	out.print("</table>");
	%>
</body>
</html>