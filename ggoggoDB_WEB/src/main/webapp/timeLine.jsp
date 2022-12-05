<%@ page import="common.*"%>
<%@ page import="TL.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
JdbcConnect jdbc = (JdbcConnect)session.getAttribute("jdbc");
User user = (User)session.getAttribute("user");
String userId = user.getID();
session.setAttribute("userId", userId);
TimeLine timeLine = new TimeLine(jdbc.getConn(), userId);
session.setAttribute("timeLine", timeLine);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Time Line</title>
</head>
<body>
<%
	String like= request.getParameter("like");
	if(like != null){
		Log e = new Log(jdbc.getConn(), Integer.parseInt(like));
		e.pushLike(jdbc.getConn());
		like = null;
	}
	List<Log> logs;
	String x= request.getParameter("searchLog");
	out.print("<h3><button type=\"button\" onclick=\"location='Menu.jsp'\">메인 메뉴로 돌아가기</button></h3>");
	out.print("<h2> 타임라인 </h2>");
	out.print("<p> 당신이 좋아하는 장르에 관련된 글이나 당신이 팔로우 하는 사람의 글을 시간 순서대로 보여줍니다. </p>");
	logs = timeLine.allBoard();
	
	out.print("<table border=1>");
	for(Log e : logs){
		out.print("<tr>");
		out.print("<td>");
		
		out.print(e.show(2));
		
		out.print("</td>");
		out.print("</tr>");
	}
	out.print("</table>");
%>
</body>
</html>