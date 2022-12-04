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
String userId = "XoOoOong";
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
	List<Log> logs = openboard.allBoard(jdbc.getConn());
	
	out.print("<h2>게시판<button onclick=\"location='writeLog.jsp'\">write</button> </h2>");
	
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