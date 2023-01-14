<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Job Details and Status</title>

<Script>
function Export(){
	document.getElementById("myForm").submit();
}

</Script>
</head>
<style>
.tftable {font-size:12px;width:100%;border-width: 1px;border-color: #729ea5;border-collapse: collapse;}
.tftable th {font-size:12px;border-width: 2px;padding: 8px;border-style: solid;border-color: #729ea5;text-align:left;}
.tftable td {font-size:14px;border-width: 1px;padding: 6px;border-style: solid;border-color: #729ea5;}
.buttonn {font-size:15px;align:center;border-width: 1px;border-color: #729ea5;background-color: #00FFFF;}
</style>
<body>
<form id="myForm" action="ExportServlet" method=post>
<div><img align="left" alt="" src="shell-logo.gif" width="50" height="50"><img align="right" alt="" src="HPE_Logo.jpg"></h2></div>

<%  
HashMap jobMap=(HashMap)request.getAttribute("jobdetails");  

out.print("<table class=tftable>");
out.print("<h2><div align=center>Result:</div></h2>");
out.print("<br>");
out.print("<div align=right><INPUT class=buttonn TYPE=button  VALUE='Take Me Home!' onClick=history.go(-1);></div>");

ArrayList columnList=(ArrayList)jobMap.get("columnList");
out.print("<tr bgcolor='#FFA500'>");
for (int i = 0; i < columnList.size(); i++) {
	out.print("<th>" + columnList.get(i) + "</th>");
}

out.print("</tr>");

ArrayList joblist=(ArrayList)jobMap.get("joblist");
for(int i=0; i < joblist.size(); i++){
	ArrayList rowList=(ArrayList) joblist.get(i);
	//System.out.println("job roll"+ rowList) ;
	
	if(i%2 ==0){
	out.print("<tr bgcolor='#FFEFD5'>");
	for(int k=0; k < rowList.size(); k++ ){
		if(rowList.get(k).equals("FINISHED"))
		out.print("<td><font color=green>" + rowList.get(k) + "</font></td>");
		else if(rowList.get(k).equals("FAILED"))
			out.print("<td><font color=red>" + rowList.get(k) + "</font></td>");
		else
		out.print("<td>" + rowList.get(k) + "</td>");
		
	}
	out.print("</tr>");
	}
	else{
	out.print("<tr bgcolor='#E0FFFF'>");
	for(int k=0; k < rowList.size(); k++ ){
		if(rowList.get(k).equals("FINISHED"))
			out.print("<td><font color=green>" + rowList.get(k) + "</font></td>");
			else if(rowList.get(k).equals("FAILED"))
				out.print("<td><font color=red>" + rowList.get(k) + "</font></td>");
			else
			out.print("<td>" + rowList.get(k) + "</td>");
		}
	out.print("</tr>");
	
	}

}
 
%> 		
	<button name=export onclick="Export">Export To Excel</button>
	</form>
	</body>
</html>