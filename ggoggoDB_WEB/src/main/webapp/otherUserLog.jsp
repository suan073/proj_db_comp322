<%@ page import="common.*"%>
<%@ page import="common.Log"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>show other user's logs</title>
</head>
<body>
	<%
	JdbcConnect jdbc = (JdbcConnect) session.getAttribute("jdbc");
	String userId = (String) session.getAttribute("userId");
	String oUserId = request.getParameter("oUserId");
	OtherUser oUser;
	
	if(oUserId != null){
		oUser = new OtherUser(oUserId);
	}else if(request.getParameter("unfollow") != null){
		oUserId = request.getParameter("unfollow");
		oUser = new OtherUser(oUserId);
		oUser.unfollow(jdbc.getConn(), userId);
	}else if(request.getParameter("follow") != null){
		oUserId = request.getParameter("follow");
		oUser = new OtherUser(oUserId);
		oUser.follow(jdbc.getConn(), userId);
	}else{	// error
		oUser = new OtherUser(null);
	}
	
	out.print("<h2>" + oUserId);
	out.print("<form method=\"post\" style=\"float:left;margin:0;\">");
	if(oUserId.equals(userId)){
		
	}else if(oUser.infollowing(jdbc.getConn(), userId)){
		out.print("<button type=\"submit\" name=\"unfollow\" value=\""+oUserId+"\">언팔로우</button></form>");
	}else{
		out.print("<button type=\"submit\" name=\"follow\" value=\""+oUserId+"\">팔로우</button></form>");
	}
	
	int type = Integer.parseInt(request.getParameter("type"));
	switch(type){
		case 0:
			out.print("<button type=\"button\" onclick=\"location='openBoard.jsp'\">게시판 돌아가기</button></h2>");
			break;
		case 1:
			out.print("<button type=\"button\" onclick=\"location='myPage.jsp'\">마이페이지 돌아가기</button></h2>");
			break;
		case 2:
			out.print("<button type=\"button\" onclick=\"location='timeLine.jsp'\">타임라인 돌아가기</button></h2>");
			break;
	}
	List<Log> logs = oUser.showUserLog(jdbc.getConn());

	out.print("<table border=1 width=\"1000\">");
	for(Log e : logs){
		out.print("<tr>");
		out.print("<td>");
		out.print(e.show(type));
		
		out.print("</td>");
		out.print("</tr>");
	}
	out.print("</table>");
	%>
</body>
</html>