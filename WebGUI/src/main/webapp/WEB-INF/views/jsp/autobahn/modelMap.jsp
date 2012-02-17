<?xml version="1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ include file="../common/includes.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  
    <title><spring:message code="${htitle}" text=""/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" href="<c:url value="/themes/style/style.css"/>" type="text/css" />
  	<link rel="stylesheet" href="<c:url value="/scripts/x2/css/xc2_default.css"/>" type="text/css" />
  	<script type="text/javascript">
		function isIP(url){
		  var url = url.replace(/^(http:\/\/)(.*)(:\d{4}.*)$/, "$2");
		  var isIP = url.match(/^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}(\:\d{4})?$/);
		  if(isIP){
		    return true;
		  }
		  return false;
		}
		
		var googlekey;
		if (isIP(window.location.host)) {
			googlekey = "<spring:message code="google.maps.key.IP"/>";
		} else {
		    googlekey = "<spring:message code="google.maps.key"/>";
		}
		
		document.write([
		                '<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key='+
		                googlekey+
		                '" type="text/javascript"><\/script>'
		              ].join(''));
    </script>  	
   	<link rel="stylesheet" href="<c:url value="/scripts/openlayers/theme/default/style.css"/>" type="text/css" />
    <script type="text/javascript" src="<c:url value="/scripts/openlayers/OpenLayers.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/openLayersTopology.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/prototype-1.6.0.3.js"/>"></script>  
  </head>
  <body onload="init();"> 
    <div id="pageContent">
    <div id="header"><p></p></div>
    <div id="login"> 
		<security:authorize ifAnyGranted="ROLE_USER,ROLE_ADMINISTRATOR"> 
			Welcome, <security:authentication property="name"/> | <a style="color:red" href='<c:url value="/j_spring_security_logout"/>'>Logout</a>
		</security:authorize>  
    </div>
    <div id="inContent">
    <div id="nav">
    	<tiles:insertAttribute name="nav"/>
		<!--    	Here was info about Reservation states - but is removed and info is on the path -->
    </div>
    <div id="top"><c:import url="top.jsp"/></div>
    <div id="content">
    	<tiles:insertAttribute name="content"/>
    </div>
    <div id="clearer">
    </div>
    </div>
    <div id="footer">
    <%
    java.util.Properties prop = new java.util.Properties();
    prop.load(pageContext.getServletContext().getResourceAsStream("/META-INF/MANIFEST.MF"));
    %>
    	<p>Copyrights@ GEANT AutoBAHN, version <%=prop.getProperty("Implementation-Version")%></p>
    	<p>Designed and Developed by <a href="http://www.geant.net">GEANT</a></p> 
    </div>
    </div>
  </body>
</html>

