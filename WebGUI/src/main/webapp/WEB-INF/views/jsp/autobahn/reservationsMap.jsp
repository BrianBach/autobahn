<%@ include file="../common/includes.jsp"%>
<h2><spring:message code="reservationMap.htitle" text="AutoBAHN Reservations and Reachability Map"/></h2>
<br/>
<div id="map" style="float:left;width: 700px; height: 600px"></div>
<div style="height:600px;overflow:auto">
	<div id="form">
		<p>To show clear map click link bellow <a href="/autobahn-gui/portal/secure/reservationsMap.htm">Clear map</a></p>
		<hr/>
		<h3>Submitted services</h3>
		<hr/>
		<a href="javascript:makeGetRequest()">Refresh</a>
		<hr/>
		<div id="services"></div>
			<ul>
				<c:forEach items="${services}" var="service" varStatus="loopStatus">
				<li><a href="<c:url value="/portal/secure/reservationsMap.htm?domain=${service.user.homeDomain.bodID}&service=${service.bodID}"/>">${service.bodID}</a></li>
				</c:forEach>
			</ul>
		</div>
</div>