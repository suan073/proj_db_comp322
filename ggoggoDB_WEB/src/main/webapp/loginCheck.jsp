<%@ page import="ggoggoDB_WEB.JdbcConnect"%>
<%@ page import="ggoggoDB_WEB.Login"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
Login login = null;
session.setAttribute("login", login);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login check</title>
</head>
<body>
	<h2>로그인 체크 </h2>
	<%
	String id = request.getParameter("user_id");
	String pw = request.getParameter("user_pw");
	JdbcConnect jdbc = (JdbcConnect)session.getAttribute("jdbc");
	login = new Login(jdbc.getConn(),id,pw);
	if(login.isValid()){
		response.sendRedirect("settingInterestedGenre.jsp");
	}else{
		request.getRequestDispatcher("login.jsp?loginErr=1").forward(request, response);
	}
	%>
</body>
</html>