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
	List<Log> logs;
	String x= request.getParameter("searchLog");
	
	out.print("<h2>게시판<button onclick=\"location='writeLog.jsp'\">write</button> </h2>");
	out.print("<form method=\"post\">");
	out.print("<h3>검색어: <input type=\"text\" name=\"search\" required value=\"\">");
	out.print("<button type=\"submit\" name=\"searchLog\" value=\"doSearch\">검색</button>");
	out.print("<h3></form>");
	
	if(x != null && x.equals("doSearch")){
		logs = openboard.searchLog(request.getParameter("search"));
		out.print("<h3>" + request.getParameter("search") + " 검색 결과 <button type=\"button\" onclick=\"location='openBoard.jsp'\">게시판 돌아가기</button></h3>");
	} else{
		logs = openboard.allBoard();
	}
	
	for(Log e : logs){
		out.print("<form action=\"otherUserLog.jsp\" method=\"post\">");
		out.print("<button type=\"submit\" name=\"oUserId\" value=\"" + e.getWriterid() + "\">" + e.getWriterid() + "</button><br>");
		out.print("</form>");		
		out.print(e.getTitle() + "\t" + e.getDate() + "<br>");
		out.print(e.getContents() + "<br>");
		out.print("<form action=\"logComment.jsp\" method=\"post\">");
		out.print("<button type=\"submit\" name=\"logId\" value=\"" + e.getLogid() + "\">댓글</button>" + e.getCommentNum() + "<button type=\"button\">♡</button>" + e.getLikes() + "<br>");
		out.print("</form>--------------------------------------------------<br>");
	}
	%>
</body>
</html>