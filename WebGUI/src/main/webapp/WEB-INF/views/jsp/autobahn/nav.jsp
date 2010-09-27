<%@ include file="../common/includes.jsp" %>

<h1><span style="margin-left:50px;">Menu</span></h1>
<fmt:message key="lnk.home" var="lnk"/>
<span class="imageMenu" id="imageMenu">&nbsp;</span><a href="<c:url value="/${lnk}"/>"><fmt:message key="nav.home"/></a>

<fmt:message key="lnk.request" var="lnk"/>
<span class="imageMenu" id="imageMenu">&nbsp;</span><a href="<c:url value="/${lnk}"/>"><fmt:message key="nav.request"/></a>
  
<fmt:message key="lnk.reservations" var="lnk"/>
<span class="imageMenu" id="imageMenu">&nbsp;</span><a href="<c:url value="/${lnk}"/>"><fmt:message key="nav.reservations"/></a>
  
  <fmt:message key="lnk.reservationsMap" var="lnk"/>
<span class="imageMenu" id="imageMenu">&nbsp;</span><a href="<c:url value="/${lnk}"/>"><fmt:message key="nav.reservationsMap"/></a>
	
  <fmt:message key="lnk.domainSettings" var="lnk"/>
<span class="imageMenu" id="imageMenu">&nbsp;</span><a href="<c:url value="/${lnk}"/>"><fmt:message key="nav.domainSettings"/></a>	
 
  <fmt:message key="lnk.domainLogs" var="lnk"/>
<span class="imageMenu" id="imageMenu">&nbsp;</span><a href="<c:url value="/${lnk}"/>"><fmt:message key="nav.domainLogs"/></a>	  

<%@ include file="../common/endnav.jsp" %>

<br/><br/>
