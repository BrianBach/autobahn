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
String filename = request.getParameter("datafile");
String command = "psql autobahn_2 -f " + filename ;
String s = null;

out.println("<br />Command to be executed: " + command + "<br />");

	Runtime r = Runtime.getRuntime();
	Process p = null;

//BufferedReader input = null;

//try {
//	input = new BufferedReader(new FileReader(filename));
//	String line = "";
//	while (true) {
//		line = input.readLine();
//		out.println("** " + filename + "  line: " + line + "<br /><br />");
	
		//if(line != null){
			try{
				p = r.exec(command);
			
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

				BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

				out.println("<br />PostgreSQL returned:<br /><br /> ");
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
	//	}
//	}
//} catch (EOFException e) {
//	input.close();
//} catch (Exception e) {
//	out.println("Exception: " + e.getMessage() + "<br />");
//}finally {
//	input.close();
//}
out.println("<br /><br /><h2>sql file executed!</h2><br />");

%>
<div align:left>
	<a href="installer.jsp">&lt;&lt; Back </a>
</div>

</body>
</html>





