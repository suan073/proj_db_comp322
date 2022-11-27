<%@page import="java.util.ArrayList"%>
<%@ page import="ggoggoDB_WEB.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>let's set your interested Genre!</title>
</head>
<body>
	<h1>당신의 선호 장르는 무엇입니까?</h1>
	<%
	User user = (User)session.getAttribute("user");
	ArrayList<String> unpreferList = user.get_ArrayList_of_Interested_Genre (true);
	ArrayList<String> preferList = user.get_ArrayList_of_Interested_Genre (false);
	
	int lineItemMax = 4;
	out.print("<form action=\"loginCheck.jsp\" method=\"post\">");
	out.print("<p>당신의 선호 장르를 체크해주세요. <br> 현재까지 선호 장르는 "+ (preferList.size()-1) +"개 입니다.<br>");
	int cnt = 0;
	for (int i=1; i<preferList.size(); i++){
		out.print( "&nbsp;&nbsp;<input type=\"checkbox\" name=\"IGcheckbox\" checked>"+"<label>"+ preferList.get(i)+"</label>");
		cnt++;
		if(cnt == lineItemMax){cnt=0;out.print("<br>");}
	}
	for (int i=1; i<unpreferList.size(); i++){
		out.print( "&nbsp;&nbsp;<input type=\"checkbox\" name=\"IGcheckbox\">"+"<label>"+ unpreferList.get(i)+"</label>");
		cnt++;
		if(cnt == lineItemMax){cnt=0;out.print("<br>");}
	}
	out.print("</p>");
	out.print("");
	
	%>
	
</body>
</html>