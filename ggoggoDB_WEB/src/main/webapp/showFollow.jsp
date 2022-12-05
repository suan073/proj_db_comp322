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
	
	out.print("<h2>내 팔로잉 목록<button type=\"button\" onclick=\"location='myPage.jsp'\">마이페이지 돌아가기</button></h2>");
	
	String oUserId= request.getParameter("unfollow");
	if(oUserId != null){
		String userId = (String) session.getAttribute("userId");
		OtherUser oUser = new OtherUser(oUserId);
		oUser.unfollow(jdbc.getConn(), userId);
	}
	
	List<String> follows = mypage.getFollowing();
	for(String f : follows){
		out.print("<form action=\"otherUserLog.jsp\" method=\"post\">");
		out.print(f+"<button type=\"submit\" name=\"oUserId\" value=\""+f+"\">게시물보기</button></form>");
		out.print("<form method=\"post\">");
		out.print("<button type=\"submit\" name=\"unfollow\" value=\""+f+"\">언팔로우</button></form><br>");
		out.print("--------------------------------------------------<br>");
	}
	%>
</body>
</html>