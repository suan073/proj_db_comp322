<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@page import="common.*"%>
<%@page import="IG.*"%>
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
	JdbcConnect jdbc = (JdbcConnect)session.getAttribute("jdbc");
	User user = (User)session.getAttribute("user");
	InterestedGenre iGenre = (InterestedGenre)user.getiGenre();
	boolean[] checkbox = new boolean[iGenre.size()];
	Arrays.fill(checkbox, false);
	
	String[] IndexStrings = request.getParameterValues("IGcheckbox");
	
	for(String index : IndexStrings){
		int INDEX = Integer.parseInt(index);
		checkbox[INDEX] = true;
	}
	
	for(int index = 0; index<iGenre.size();index++){
		iGenre.changeInterest(index, checkbox[index]);
	}
	
	iGenre.updateAll();
	
	response.sendRedirect("search.jsp");
	
%>
</body>
</html>