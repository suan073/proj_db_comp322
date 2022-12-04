<%@ page import="common.JdbcConnect"%>
<%@ page import="common.Comment"%>
<%@ page import="common.Log"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>show log and comment</title>
</head>
<body>
	<%
	JdbcConnect jdbc = (JdbcConnect) session.getAttribute("jdbc");
	int logId = Integer.parseInt(request.getParameter("logId"));
	Log e = new Log(jdbc.getConn(), logId);
	out.print("<h4><form action=\"otherUserLog.jsp\" method=\"post\">");
	out.print("<button type=\"submit\" name=\"oUserId\" value=\"" + e.getWriterid() + "\">" + e.getWriterid() + "</button><br>");
	out.print("</form>");		
	out.print(e.getTitle() + "\t" + e.getDate() + "<br>");
	out.print(e.getContents() + "<br>");
	out.print("<button type=\"submit\" onclick=\"location='logComment.jsp'\" name=\"logId\" value=e.getLogid()>댓글</button>" + e.getCommentNum() + "<button type=\"button\">♡</button>" + e.getLikes() + "<br>");
	out.print("--------------------------------------------------</h4><br>");
	
	List<Comment> comments = e.showComments(jdbc.getConn());
	for(Comment c : comments){
		out.print("<form action=\"otherUserLog.jsp\" method=\"post\">");
		out.print("<button type=\"submit\" name=\"oUserId\" value=\"" + c.getWriterid() + "\">" + c.getWriterid() + "</button>");
		out.print("</form>");
		out.print(c.getDate() + "<br>");
		out.print(c.getText() + "<br>");
		out.print("--------------------------------------------------<br>");
	}
	%>
</body>
</html>