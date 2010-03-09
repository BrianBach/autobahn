<?xml version="1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ include file="../common/includes.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title><spring:message code="${htitle}" text=""/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" href="<c:url value="/themes/style/style.css"/>" type="text/css" />
  	<link rel="stylesheet" href="<c:url value="/scripts/x2/css/xc2_default.css"/>" type="text/css" />  	
  	<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=<spring:message code="google.maps.key" text="ABQIAAAAIrQkz2e_DN6EeNh-dsy9BBQ3d_ZNG2YFwgHBa0C2TZZTJ80suxTYsIqmNObRGF6j9-0zEfn3YR27HQ"/>"></script> 
   	<link rel="stylesheet" href="<c:url value="/scripts/openlayers/theme/default/style.css"/>" type="text/css" />
    <script type="text/javascript" src="<c:url value="/scripts/openlayers/OpenLayers.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/openLayersTopology.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/prototype-1.6.0.3.js"/>"></script>  
  </head>
  <body onload="init();"> 
    <div id="pageContent">
    <div id="header"><p></p></div>
    <div id="login"> 
		<security:authorize ifAnyGranted="ROLE_USER"> 
			Welcome, <security:authentication property="name"/> | <a style="color:red" href='<c:url value="/j_spring_security_logout"/>'>Logout</a>
		</security:authorize>  
    </div>
    <div id="inContent">
    <div id="nav">
    	<tiles:insertAttribute name="nav"/>
		<%
		int length = 0;
		int statesLength = 0;
		String[] colors;
		String[] states;
			if (request.getAttribute("reservationLinkColors")!=null &&request.getAttribute("reservationStates")!=null ){
			out.println ("<h2>Reservation states</h2>");
			out.println ("<table>");
			colors =(String[])request.getAttribute("reservationLinkColors");
			states =(String[])request.getAttribute("reservationStates");
			length = colors.length;
			statesLength = states.length;
			for (int i=0;i<length;i++)
			{
				if (colors==null || i >= statesLength|| states[i]==null)
					break;
				if ((i>=0 && i<=11) || (i>20 && i<=23))
				{
				out.println ("<tr>");
				out.println ("<td style=\"width:15;background-color:"+colors[i]+";\">");
				out.println ("&nbsp;&nbsp;");
				out.println ("</td>");
				out.println ("<td>");
				out.println (states[i]);
				out.println ("</td>");
				out.println ("</tr>");
				}
			}
			}else
			{
				length = 0;
				statesLength = 0;
				colors =(String[])request.getAttribute("linkColors");
				states =(String[])request.getAttribute("linkStates");
				if (colors!= null && states !=null){
				out.println ("<h2>Link states</h2>");
				out.println ("<table>");
				length = colors.length;
				statesLength = states.length;
				for (int i=0;i<length;i++)
				{
					if (colors==null || i >= statesLength|| states[i]==null)
						break;
					out.println ("<tr>");
					out.println ("<td style=\"width:15px;background-color:"+colors[i]+";\">");
					out.println ("&nbsp;&nbsp;");
					out.println ("</td>");
					out.println ("<td>");
					out.println (states[i]);
					out.println ("</td>");
					out.println ("</tr>");
				}
				}
			}
			out.println ("</table>");
		%>
    </div>
    <div id="top"><c:import url="top.jsp"/></div>
    <div id="content">
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
