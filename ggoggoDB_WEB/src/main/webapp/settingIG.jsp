<%@page import="java.util.ArrayList"%>
<%@page import="common.*"%>
<%@page import="IG.*"%>
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
	<form action="settingIGcheck.jsp" method="post">
	<table border=1>
	<%
		User user = (User)session.getAttribute("user");
		InterestedGenre IGenre = user.getiGenre();
		
		for(int index = 0; index<IGenre.size(); ){
			out.print("<tr>");
			for(int i = 0; i<5 ;i++){
				if(index<IGenre.size()){
					ItemOfIG item = IGenre.getItemOfIG(index);
					out.print("<td width=200 height=50> <label><input type=\"checkbox\" value="+index+" name=\"IGcheckbox\"");
					if(item.isInterest()){
						out.print(" checked ");
					}
					out.print(">"+index+". "+item.getGenreName()+"</label> </td>");
					index++;
				}
				else if (i<4) {
					out.print("<td></td>");
				}
				else{
					out.print("<td><input style=\"width:100%;\" type=\"submit\" value=\"적용하기 \"></td>");
				}
			}
			out.print("</tr>");
		}
	%>
	</table>
	</form>
	
</body>
</html>