<%@ include file="../common/includes.jsp"%>
<ul>
<c:forEach items="${services}" var="service" varStatus="loopStatus">
	<li><a href="<c:url value ="/portal/secure/services-map.htm?service=${service.key}&domain=${service.value}"/>">${service.key}</a></li>
</c:forEach>
</ul>