<%@ page import="common.JdbcConnect"%>
<%@ page import="common.OpenBoard"%>
<%@ page import="common.Log"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
JdbcConnect jdbc = new JdbcConnect();
session.setAttribute("jdbc", jdbc);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>OpenBoard</title>
</head>
<body>
	<h2>게시판</h2>
	<%
	//JdbcConnect jdbc = (JdbcConnect) session.getAttribute("jdbc");
	OpenBoard openboard = new OpenBoard(jdbc.getConn());
	List<Log> logs = openboard.allBoard(jdbc.getConn());
	for(Log e : logs){
		out.print("<form action=\"otherUserLog.jsp\" method=\"post\">");
		out.print("<button type=\"submit\" name=\"oUserId\" value=\"" + e.getWriterid() + "\">" + e.getWriterid() + "</button><br>");
		out.print("</form>");		
		out.print(e.getTitle() + "\t" + e.getDate() + "<br>");
		out.print(e.getContents() + "<br>");
		out.print("<button type=\"button\">댓글</button>" + "<button type=\"button\">♡</button>" + e.getLikes() + "<br>");
		out.print("--------------------------------------------------<br>");
	}
	%>
</body>
</html>