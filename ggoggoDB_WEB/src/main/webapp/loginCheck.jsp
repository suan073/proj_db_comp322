<%@ page import="ggoggoDB_WEB.JdbcConnect"%>
<%@ page import="ggoggoDB_WEB.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

User user = null;
session.setAttribute("user", user);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>user check</title>
</head>
<body>
	<h2>로그인 체크 </h2>
	<%
		String id = request.getParameter("user_id");
		String pw = request.getParameter("user_pw");
		JdbcConnect jdbc = (JdbcConnect)session.getAttribute("jdbc");
		user = new User(jdbc.getConn(),id,pw);
		if(user.getID() != null){
			response.sendRedirect("settingInterestedGenre.jsp");
		}else{
			request.getRequestDispatcher("login.jsp?loginErr=1").forward(request, response);
		}
	%>
</body>
</html>