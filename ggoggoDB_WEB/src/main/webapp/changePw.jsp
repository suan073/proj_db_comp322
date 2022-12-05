<%@ page import="common.JdbcConnect"%>
<%@ page import="common.MyPage"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>change password</title>
</head>
<body>
	<h2>비밀번호 변경</h2>
	<%
	MyPage mypage = (MyPage) session.getAttribute("mypage");
	
	String newpw = request.getParameter("new_pw");
	String prevpw = request.getParameter("prev_pw");
	if (newpw != null) {
		String res = mypage.updatePassword(newpw);
		out.print("<h4>"+res+"</h4>");
		out.print("<button type=\"button\" onclick=\"location='myPage.jsp'\">확인</button>");
	} else if (prevpw != null) {
		boolean check = mypage.checkPassword(prevpw);
		if (check) {
			out.print("<form method=\"post\">");
			out.print("<label>새 비밀번호: </label> <input type=\"text\" name=\"new_pw\">");
			out.print("<button type=\"submit\">변경</button>");
			out.print("</form>");
		} else {
			out.print("비밀번호가 올바르지 않습니다");
			out.print("<form method=\"post\">");
			out.print("<label>기존 비밀번호: </label> <input type=\"text\" name=\"prev_pw\">");
			out.print("<button type=\"submit\">확인</button>");
			out.print("</form>");
		}
	} else {
		out.print("<form method=\"post\">");
		out.print("<label>기존 비밀번호: </label> <input type=\"text\" name=\"prev_pw\">");
		out.print("<button type=\"submit\">확인</button>");
		out.print("</form>");
	}
	%>
</body>
</html>