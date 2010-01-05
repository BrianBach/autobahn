<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<title> Adding new AutoBAHN tunnel ... </title>
</head>

<body>
<h1> Please wait ... </h1>
<br />
<%@ page import='java.io.*' %>
<%

//This jsp page adds new entries to routing.conf
String remote_autobahn = new String(request.getParameter("remote_autobahn"));
String local_autobahn = new String(request.getParameter("local_autobahn"));
String local_subnet = new String(request.getParameter("local_subnet"));
String local_router_id = new String(request.getParameter("local_router_id"));
String network = new String(request.getParameter("network"));
String area    = new String(request.getParameter("area"));


//Now add configuration to routing.conf
String s = null;


String append_record = "Link " +  remote_autobahn + " " + local_autobahn + " " + local_subnet + " " + local_router_id + " " + network + " " + area ;

BufferedWriter bw = null;

try{
	bw = new BufferedWriter(new FileWriter("/var/lib/tomcat6/webapps/ROOT/installer/routing.conf",true));
	bw.write(append_record);
	bw.newLine();
	bw.flush();
} catch (IOException ioe) {
	out.println("Exception happened: " + ioe.getMessage());
} finally {
	if (bw != null) try {
		bw.close();
	} catch (IOException ioe2){
		//ignore
	}
}
	

out.println("<br />Tunnel configuration file written successfully...");

%>
<br />
<div align:left>
	<a href="installer.jsp">&lt;&lt; Add new record </a>
</div>
</body>
</html>





