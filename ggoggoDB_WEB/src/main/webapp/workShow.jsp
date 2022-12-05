<%@ page import="Search.*"%>
<%@ page import="common.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>work show</title>
</head>
<body>
<%
	JdbcConnect jdbc = (JdbcConnect)session.getAttribute("jdbc");
	String ssn = request.getParameter("ssn");
	Work work = Searcher.getStringShowOneWorkDetail(jdbc.getConn(), ssn);
	
	out.print(work.show());
	
%>
</body>
</html>