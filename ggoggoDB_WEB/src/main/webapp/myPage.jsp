<%@ page import="common.*"%>
<%@ page import="common.Log"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
JdbcConnect jdbc = (JdbcConnect)session.getAttribute("jdbc");
User user = (User)session.getAttribute("user");
String userId = user.getID();
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
	<%
	out.print("<h3><button type=\"button\" onclick=\"location='Menu.jsp'\">메인 메뉴로 돌아가기</button></h3>");
	out.print("<h2>마이페이지</h2>");
	out.print("<h3>" + userId + "<button type=\"button\" onclick=\"location='changePw.jsp'\">비밀번호변경</button>");
	out.print("<button type=\"button\" onclick=\"location='showFollow.jsp'\">팔로잉목록</button>");
	out.print("<button type=\"button\" onclick=\"location='settingIG.jsp'\">선호장르변경</button></h3>");
	List<Log> logs = mypage.showUserLog();

	out.print("<table border=1 width=\"1000\">");
	for(Log e : logs){
		out.print("<tr>");
		out.print("<td>");
		
		out.print(e.show(1));
		
		out.print("</td>");
		out.print("</tr>");
	}
	out.print("</table>");
	%>
</body>
</html>