<%@page import="java.util.ArrayList"%>
<%@page import="common.*"%>
<%@page import="Search.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>search check...</title>
</head>
<body>
<%
	JdbcConnect jdbc = (JdbcConnect)session.getAttribute("jdbc");

	String[] lStrings = request.getParameterValues("langcheckbox");
	ArrayList<String> lArrayList = new ArrayList<>();
	if(lStrings != null){
		for(String item :lStrings){
			lArrayList.add(item);
		}
	}
	
	String[] iStrings = request.getParameterValues("adulcheckbox");
	ArrayList<String> iArrayList = new ArrayList<>();
	if(iStrings != null){
		for(String item :iStrings){
			iArrayList.add(item);
		}
	}
	
	String[] mStrings = request.getParameterValues("medicheckbox");
	ArrayList<String> mArrayList = new ArrayList<>();
	if(mStrings != null){
		for(String item :mStrings){
			mArrayList.add(item);
		}
	}
	
	String[] sStrings = request.getParameterValues("statcheckbox");
	ArrayList<String> sArrayList = new ArrayList<>();
	if(sStrings != null){
		for(String item :sStrings){
			sArrayList.add(item);
		}
	}
	
	Searcher searcher = new Searcher(jdbc.getConn(),
			lArrayList,iArrayList,mArrayList,sArrayList);
	
	String category = request.getParameter("sradio");
	String search_word = request.getParameter("inputWord");
	ArrayList<ResultItem> result = searcher.getSearchResult(category, search_word);
	
	session.setAttribute("searchResult", result);
	
	String writingFlag = request.getParameter("writingFlag");
	if(writingFlag != null){
		request.getRequestDispatcher("search.jsp?SearchFlag=1&&writingFlag=1").forward(request, response);

	}else{
		request.getRequestDispatcher("search.jsp?SearchFlag=1").forward(request, response);
	}
	
%>
</body>
</html>