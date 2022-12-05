<%@ page import="common.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>user check</title>
</head>
<body>
<%
	String id = request.getParameter("user_id");
	String pw = request.getParameter("user_pw");
	JdbcConnect jdbc = (JdbcConnect)session.getAttribute("jdbc");
	User user = new User(jdbc.getConn(),id,pw);
	session.setAttribute("user", user);
	
	if(user.getID() != null){
		response.sendRedirect("Menu.jsp");
	}else{
		request.getRequestDispatcher("login.jsp?loginErr=1").forward(request, response);
	}
%>
</body>
</html>