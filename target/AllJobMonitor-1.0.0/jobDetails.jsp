<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%  
HashMap jobMap=(HashMap)request.getAttribute("jobdetails");  

out.print("<table width=50% border=1 align:center>");
out.print("<caption>Result:</caption>");

ArrayList columnList=(ArrayList)jobMap.get("columnList");
out.print("<tr>");
for (int i = 0; i < columnList.size(); i++) {
	out.print("<th>" + columnList.get(i) + "</th>");
}
out.print("</tr>");

ArrayList joblist=(ArrayList)jobMap.get("joblist");
for(int i=0; i < joblist.size(); i++){
	ArrayList rowList=(ArrayList) joblist.get(i);
	out.print("<tr>");
	for(int k=0; k < rowList.size(); k++ ){
		out.print("<td>" + rowList.get(k) + "</td>");
	}
	out.print("</tr>");
}

%>  

<INPUT TYPE="button" VALUE="Take Me Home!" onClick="history.go(-1);">

</body>
</html>