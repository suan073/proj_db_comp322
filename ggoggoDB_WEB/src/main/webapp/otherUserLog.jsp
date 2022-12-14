<%@page import="org.apache.jasper.tagplugins.jstl.core.Out"%>
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
	
	String like= request.getParameter("like");
	if(like != null){
		Log e = new Log(jdbc.getConn(), Integer.parseInt(like));
		e.pushLike(jdbc.getConn());
		like = null;
		oUserId = e.getWriterid();
	}
	
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
	int type = Integer.parseInt(request.getParameter("type"));
	switch(type){
		case 0:
			out.print("<button type=\"button\" onclick=\"location='openBoard.jsp'\">게시판 돌아가기</button>");
			break;
		case 1:
			out.print("<button type=\"button\" onclick=\"location='myPage.jsp'\">마이페이지 돌아가기</button>");
			break;
		case 2:
			out.print("<button type=\"button\" onclick=\"location='timeLine.jsp'\">타임라인 돌아가기</button>");
			break;
		case 3:
			out.print("<button type=\"button\" onclick=\"location='showFollow.jsp'\">내 팔로잉 목록 돌아가기</button>");
			break;
	}
	out.print("<table>");
	out.print("<tr><td>");
	out.print("<h2> " + oUserId +"</td>");
	if(oUserId.equals(userId)){
		out.print("</tr>");
	}else if(oUser.infollowing(jdbc.getConn(), userId)){
		out.print("<td>");
		out.print("<form method=\"post\"> <input type=\"hidden\" name=\"type\" value="+type+"> ");
		out.print("<button type=\"submit\" name=\"unfollow\" value=\""+oUserId+"\">언팔로우</button></form>");
		out.print("</td></tr>");
	}else{
		out.print("<td>");
		out.print("<form method=\"post\">  <input type=\"hidden\" name=\"type\" value="+type+"> ");
		out.print("<button type=\"submit\" name=\"follow\" value=\""+oUserId+"\">팔로우</button> </form>");
		out.print("</td></tr>");
	}
	out.print("</table>");
	List<Log> logs = oUser.showUserLog(jdbc.getConn(), userId);

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