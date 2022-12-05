<%@ page import="common.JdbcConnect"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
JdbcConnect jdbc = new JdbcConnect();
session.setAttribute("jdbc", jdbc);
String userId = "XoOoOong";
session.setAttribute("userId", userId);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MyPage</title>
</head>
<body>
<h2>마이페이지</h2>
	<%
	

	%>
</body>
</html>