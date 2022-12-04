<%@ page import="common.JdbcConnect"%>
<%@ page import="common.OpenBoard"%>
<%@ page import="common.Log"%>
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
<title>show other user's logs</title>
</head>
<body>
	<%
	String id = request.getParameter("oUserId");
	out.print("<h2>" + id +"</h2>");
	%>
</body>
</html>