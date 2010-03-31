<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ include file="/WEB-INF/views/jsp/common/includes.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Autobahn client portal</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="/autobahn-gui/themes/spring/spring.css" type="text/css"/>
</head>

<body>

<div id="pageContent">
	<div id="inContent">
		<h1 style="width:900px">AutoBahn GUI</h1>
		<div style="margin:20px 300px 20px 300px; width=100%">
			<h3>Internal WFAYF Service</h3>
			<form method="get" action="<%=request.getParameter("return")%>"/>
       			<p>Please select your Home Site from the following list and press the <b>Go!</b> button</p>
       			<select name="returnIDParam">
       				<c:forEach var="entry" items="${authenticators}">
  						<option value="${entry.value}">${entry.key}</option>
					</c:forEach>
       			</select>
 				<input type="submit" value="Go!"/>
			</form>
		</div>
		<div id="footer"></div>
	<div/>
</div>
</body>
</html>
