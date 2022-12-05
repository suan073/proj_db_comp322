<%@ page import="common.JdbcConnect"%>
<%@ page import="common.OtherUser"%>
<%@ page import="common.Log"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>show other user's logs</title>
</head>
<body>
	<%
	JdbcConnect jdbc = (JdbcConnect) session.getAttribute("jdbc");
	String oUserId = request.getParameter("oUserId");
	OtherUser oUser = new OtherUser(oUserId);
	out.print("<h2>" + oUserId + "<button type=\"button\">follow</button><button type=\"button\" onclick=\"location='openBoard.jsp'\">게시판 돌아가기</button></h2>");
	List<Log> logs = oUser.showUserLog(jdbc.getConn());
	/* for(Log e : logs){
		out.print("<form action=\"otherUserLog.jsp\" method=\"post\">");
		out.print("<button type=\"submit\" name=\"oUserId\" value=\"" + e.getWriterid() + "\">" + e.getWriterid() + "</button><br>");
		out.print("</form>");		
		out.print(e.getTitle() + "\t" + e.getDate() + "<br>");
		out.print(e.getContents() + "<br>");
		out.print("<button type=\"button\">댓글</button>" + "<button type=\"button\">♡</button>" + e.getLikes() + "<br>");
		out.print("--------------------------------------------------<br>");
	} */
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