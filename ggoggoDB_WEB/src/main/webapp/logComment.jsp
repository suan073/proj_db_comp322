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
	<h3><button type="button" onclick="location='openBoard.jsp'">게시판 돌아가기</button></h3>
	<%
	JdbcConnect jdbc = (JdbcConnect) session.getAttribute("jdbc");
	String slogId = request.getParameter("writeComment");
	int logId;
	Log e;
	
	if(slogId != null){
		logId = Integer.parseInt(slogId);
		String userId = (String)session.getAttribute("userId");
		e = new Log(jdbc.getConn(), logId);
		e.writeComment(jdbc.getConn(), userId, request.getParameter("text"));
	} else{
		logId = Integer.parseInt(request.getParameter("logId"));
		e = new Log(jdbc.getConn(), logId);
	}

	out.print("<table border=1 width=\"1000\">");
	out.print("<tr>");
	out.print("<td>");
	
	out.print(e.show());
	
	out.print("</td>");
	out.print("</tr>");
	out.print("</table>");
	
	List<Comment> comments = e.showComments(jdbc.getConn());
	
	out.print("<table border=1 width=\"1000\">");
	for(Comment c : comments){
		out.print("<tr>");
		out.print("<td>");
		
		out.print(c.show());
		
		out.print("</td>");
		out.print("</tr>");
	}
	out.print("</table>");
	
	out.print("<br><form method=\"post\">");
	out.print("댓글: <textarea name=\"text\" required value=\"\">댓글 작성하기..</textarea>"); 
	out.print("<button type=\"submit\" name=\"writeComment\" value=\"" + logId + "\">작성</button>");
	out.print("</form>");
	
	%>
</body>
</html>