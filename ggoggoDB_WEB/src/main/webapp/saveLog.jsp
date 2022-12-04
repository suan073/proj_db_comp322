<%@ page import="common.OpenBoard"%>
<%@ page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>save new log</title>
</head>
<body>
	<%
	OpenBoard openboard = (OpenBoard) session.getAttribute("openboard");
	openboard.writeLog(request.getParameter("title"), request.getParameter("content"), request.getParameter("public"));
	response.sendRedirect("openBoard.jsp");
	%>
</body>
</html>