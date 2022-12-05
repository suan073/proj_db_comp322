<%@ page import="common.JdbcConnect"%>
<%@ page import="common.MyPage"%>
<%@ page import="common.Log"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
JdbcConnect jdbc = new JdbcConnect();
session.setAttribute("jdbc", jdbc);
String userId = "XoOoOong";
session.setAttribute("userId", userId);
MyPage mypage = new MyPage(jdbc.getConn(), userId);
session.setAttribute("mypage", mypage);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MyPage</title>
</head>
<body>
<h2>마이페이지</h2>
	<%
	out.print("<h3>" + userId + "<button type=\"button\" onclick=\"location='changePw.jsp'\">비밀번호변경</button><button type=\"button\" onclick=\"location='showFollow.jsp'\">팔로잉목록</button></h3>");
	List<Log> logs = mypage.showUserLog();

	out.print("<table border=1 width=\"1000\">");
	for(Log e : logs){
		out.print("<tr>");
		out.print("<td>");
		
		out.print(e.show());
		
		out.print("</td>");
		out.print("</tr>");
	}
	out.print("</table>");
	%>
</body>
</html>