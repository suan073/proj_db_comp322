<%@ page import="common.*"%>
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
<title>GGO GGO DB : login</title>
</head>
<body>
	<h2>로그인</h2>
	<%
	String loginErr = request.getParameter("loginErr");
	if(loginErr != null){
		out.print("<p>로그인 실패 </p>");
	}
	%>
	<form action="loginCheck.jsp" method="post">
		<label>ID</label>
		<input type="text" name="user_id">
		<br>
		<label>PASSWORD</label>
		<input type="text" name="user_pw">
		<br>
		<button type="submit">login</button>
	</form>
</body>
</html>