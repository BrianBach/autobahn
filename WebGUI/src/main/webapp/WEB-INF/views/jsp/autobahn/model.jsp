<?xml version="1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ include file="../common/includes.jsp" %>
<%@ page import="org.springframework.security.ui.AbstractProcessingFilter" %>
<%@ page import="org.springframework.security.ui.webapp.AuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.AuthenticationException" %>
<%@ taglib prefix='security' uri='http://www.springframework.org/security/tags' %>

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title><spring:message code="${htitle}" text=""/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   	<link rel="stylesheet" href="<c:url value="/themes/style/style.css"/>" type="text/css" />
  	<link rel="stylesheet" href="<c:url value="/scripts/x2/css/xc2_default.css"/>" type="text/css" />
  	<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/scripts/jscalendar/css/jscal2.css"/>" />
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/scripts/jscalendar/css/border-radius.css"/>" />
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/scripts/jscalendar/css/steel/steel.css"/>" />
  	<script type="text/javascript" src="<c:url value="/scripts/x2/config/xc2_default.js"/>"></script>
  	<script type="text/javascript" src="<c:url value="/scripts/x2/script/xc2_inpage.js"/>"></script>
  	<script type="text/javascript" src="<c:url value="/scripts/x2/script/mod_time.js"/>"></script>
  	<script type="text/javascript" src="<c:url value="/scripts/prototype-1.6.0.3.js"/>"></script>  
  	<script language="javascript">
		xcDateFormat="yyyy-mm-ddThr:mi:ss";
		xcMods[9].order=1;
		xcFootTagSwitch=[0, 0, 0, 0, 0, 0, 0, 0];
	</script>	
  </head>
  <body>
    <div id="pageContent">
    <div id="header">&nbsp;</div>
    
    <div id="inContent">
    <div id="nav">
    <tiles:insertAttribute name="nav"/>
    </div>
    <div id="content">
<div id="login">
    	<security:authorize ifAnyGranted="ROLE_USER"> 
			Welcome, <security:authentication property="name"/> | <a style="color:red" href='<c:url value="/j_spring_security_logout"/>'>Logout</a>
		</security:authorize>  
    </div>
    	<tiles:insertAttribute name="content"/>
    </div>
    <div id="clearer">
    </div>
    </div>
    <div id="footer">
    	<p>Copyrights@ GEANT AutoBAHN</p>
    	<p>Designed and Developed by <a href="http://www.geant.net">GEANT</a></p> 
    </div>
    </div>
  </body>
</html>
