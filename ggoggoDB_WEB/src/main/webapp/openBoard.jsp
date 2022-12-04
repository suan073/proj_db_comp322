<%@ page import="common.JdbcConnect"%>
<%@ page import="common.OpenBoard"%>
<%@ page import="common.Log"%>
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
	Log[] logs = openboard.allBoard(jdbc.getConn());
	for (int i = 0; i < logs.length; i++) {
		//out.print("<a href=\"#\" onclick=\"location=\'otherUserLog.jsp\'\">"+logs[i].getWriterid()+"</a><br>");
		//out.print("<button type=\"submit\" onclick=\"location=\'otherUserLog.jsp\'\" name=\"oUserId\" value=\"+logs[i].getWriterid()+\">"+logs[i].getWriterid()+"</button><br>");
		out.print("<form action=\"otherUserLog.jsp\" method=\"post\">");
		out.print("<button type=\"submit\" name=\"oUserId\" value=\"" + logs[i].getWriterid() + "\">" + logs[i].getWriterid() + "</button><br>");
		out.print("</form>");		
		out.print(logs[i].getTitle() + "\t" + logs[i].getDate() + "<br>");
		out.print(logs[i].getContents() + "<br>");
		out.print("<button type=\"button\">댓글</button>" + "<button type=\"button\">♡</button>" + logs[i].getLikes() + "<br>");
		out.print("--------------------------------------------------<br>");
	}
	%>
</body>
</html>