<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<title> Installing AutoBAHN  ... </title>
</head>

<body>
<h1> Installing AutoBAHN ... </h1>
<br />
<%@ page import='java.io.*' %>
<%

String command = "/var/lib/tomcat6/webapps/ROOT/installer/do-install.sh /var/lib/tomcat6/webapps/ROOT/installer/routing.conf";
String s = null;

out.println("<br />Command to be executed: " + command + "<br />");

	Runtime r = Runtime.getRuntime();
	Process p = null;
try{
	p = r.exec(command);
	
	BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

	BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

	out.println("<br />Installation scripts returned:<br /><br /> ");
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
out.println("<br /><br /><h2>AutoBAHN installed!</h2><br />");

%>
<div align:left>
	<a href="installer.jsp">&lt;&lt; Back </a>
</div>

</body>
</html>





