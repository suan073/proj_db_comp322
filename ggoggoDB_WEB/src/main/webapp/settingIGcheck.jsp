<%@page import="common.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>checkIGsetting</title>
</head>
<body>
<%
	String id = request.getParameter("user_id");
	String pw = request.getParameter("user_pw");
	JdbcConnect jdbc = (JdbcConnect)session.getAttribute("jdbc");
	User user = new User(jdbc.getConn(),id,pw);
	session.setAttribute("user", user); 
	
	String[] gidStrings = request.getParameterValues("IGcheckbox");
	
	for(String gid : gidStrings){
		int GID = Integer.parseInt(gid);
		out.print(GID+"<br>");
	}
	
	
	
%>
</body>
</html>