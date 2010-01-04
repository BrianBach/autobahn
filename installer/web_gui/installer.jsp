<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
  <html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
 	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-7" />
	<title>AutoBAHN Installer Beta</title>
	<style media="all" type="text/css">@import 'css/layout.css';</style>
	<style media="all" type="text/css">@import 'css/style.css';</style>
	</head>
	<body>
	<script type="text/javascript">
	    function install(){
		    document.installform.submit();		    
	    }
	    function view_tunnels(){
		    document.viewtunnelsform.submit();
	    }
	</script> 
		<div class="header">
		</div>
		<div class="content">
				<form name="addform" action="add_new.jsp" method="post">
					<table>
					<tr>
						<td>Remote AutoBAHN instance:</td>
						<td><input type="text" name="remote_autobahn"/></td>
					</tr>
					<tr>
						<td>Local AutoBAHN instance:</td>
						<td><input type="text" name="local_autobahn"/></td>
					</tr>
					<tr>
						<td>Local Subnet:</td>
						<td><input type="text" name="local_subnet"/></td>
					</tr>
					<tr>
						<td>Local Router ID:</td>
						<td><input type="text" name="local_router_id"/></td>
					</tr>
					<tr>
						<td>Network:</td>
						<td><input type="text" name="network"/></td>
					</tr>
					<tr>
						<td>Area:</td>
						<td><input type="text" name="area"/></td>
					</tr>
					<tr><td colspan="2"><br /><br /></td></tr>
					<tr><td align="left"><input type="button" value="Install" onClick="install();" />&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="View tunnels" onClick="view_tunnels();" /></td>
					<td align="right"><input type="submit" value="Add New" />
					</td></tr>
				</table>
				</form>
<br /><br />
				<form name="setup_db" action="setup_db.jsp" method="post">
					<p>
						Please specify an sql file on the server:<br>
						<input type="text" name="datafile" size="40">
				
					
						<input type="submit" value="Setup database">
					</p>

				</form>
				<form name="installform" action="install.jsp" method="post"></form>
				<form name="viewtunnelsform" action="view.jsp" method="post"></form>
		</div>
</body>		
</html>
