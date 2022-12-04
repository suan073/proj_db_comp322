<%@ page import="ggoggoDB_WEB.OpenBoard"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>write log</title>
</head>
<body>
	<%
	String userId = session.getAttribute("userId").toString();
	OpenBoard openboard = (OpenBoard)session.getAttribute("openboard");
	%>
	<h2>글 작성하기</h2>
	<form action="saveLog.jsp" method="post">
		<h3>
			제목: <input type="text" name="title" required value="">
		</h3>
		<br>
		<h3>
			내용: <textarea name="content" rows="10" cols="50" required value=""></textarea>
		</h3>
		<br>
		<h3>
			공개설정: 
			<input type="radio" name="public" value="Y" required value="">공개
			<input type="radio" name="public" value="N">비공개
		</h3>
		<button type="submit" >작성</button>
	</form>
</body>
</html>