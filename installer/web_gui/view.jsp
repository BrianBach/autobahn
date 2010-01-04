<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<title> View installed tunnels  ... </title>
</head>

<body>
<h1> Installed tunnels ... </h1>
<br />
<%@ page import='java.io.*' %>
<%

String command = "ip tunnel ls";
String s = null;

//out.println("<br />Command to be executed: " + command + "<br />");

	Runtime r = Runtime.getRuntime();
	Process p = null;
try{
	p = r.exec(command);
	
	BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

	BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

	out.println("<h3>Tunnels installed on server:</h3> ");
	while ((s = stdInput.readLine()) != null){
		out.println(s);
		out.println("<br />");
	}

//	read any errors:
	while ((s = stdError.readLine()) != null){
		out.println("ERROR: " + s);
	}
}
catch(IOException e){
	out.println("Exception happened: " +  e.getMessage());
	e.printStackTrace();
}

%>
<div align:left>
<br />
	<a href="installer.jsp">&lt;&lt; Back </a>
</div>

</body>
</html>





