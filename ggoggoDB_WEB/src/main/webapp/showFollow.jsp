<%@ page import="common.JdbcConnect"%>
<%@ page import="common.MyPage"%>
<%@ page import="common.OtherUser"%>
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
<title>show my follow</title>
</head>
<body>
	<%
	JdbcConnect jdbc = (JdbcConnect) session.getAttribute("jdbc");
	MyPage mypage = (MyPage) session.getAttribute("mypage");
	
	out.print("<h2>내 팔로잉 목록 <button type=\"button\" onclick=\"location='myPage.jsp'\">마이페이지 돌아가기</button></h2>");
	
	String oUserId= request.getParameter("unfollow");
	if(oUserId != null){
		String userId = (String) session.getAttribute("userId");
		OtherUser oUser = new OtherUser(oUserId);
		oUser.unfollow(jdbc.getConn(), userId);
	}
	
	List<String> follows = mypage.getFollowing();
	out.print("<table border=1>");
	for(String f : follows){
		out.print("<tr width=200><td width=\"20%\">");
		out.print("<form method=\"post\">");
		out.print("<input type=\"hidden\" name=\"type\" value="+3+">");
		out.print("<button type=\"submit\" name=\"unfollow\" value=\""+f+"\">언팔로우</button></form>");
		out.print("</td><td width=\"60%\"> ");
		out.print(f);
		out.print("</td><td width=\"20%\">");
		out.print("<form action=\"otherUserLog.jsp\" method=\"post\" style=\"float:left;margin:0;\">");
		out.print("<input type=\"hidden\" name=\"type\" value="+3+">");
		out.print("<button type=\"submit\" name=\"oUserId\" value=\""+f+"\">게시물보기</button></form>");
		out.print("</td></tr>");
	}
	out.print("</table>");

	%>
</body>
</html>