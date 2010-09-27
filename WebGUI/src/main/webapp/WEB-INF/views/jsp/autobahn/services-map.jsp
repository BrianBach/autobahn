<%@ include file="../common/includes.jsp"%>
<h2><spring:message code="reservationMap.htitle" text="AutoBAHN Reservations and Reachability Map"/></h2>
<br/>
<div id="map" style="float:left;width: 700px; height: 600px"></div>
<div style="padding-left:20px; height:600px;overflow:auto; border-right:1px dotted #9B9CCE;">
	<div id="form">
		<p>To show clear map click link bellow</p>
		<a  style="background-color: blue; color: white" href="<c:url value="/portal/secure/services-map.htm?service=&domain="/>">Clear map</a>
		<hr/>
		<h3>Submitted services</h3>
<br/>
		<a style="background-color: blue; color: white" href="javascript:makeGetRequest()">Refresh</a>
		<hr/>
		<script type="text/javascript">
			new Ajax.PeriodicalUpdater('services', '<c:url value="/portal/secure/services-list.htm"/>', {
			method: 'get', frequency: 3, decay: 2
			});
		</script>
		
		<div id="services">
			<ul>
				<c:forEach items="${services}" var="service" varStatus="loopStatus">
				<li><a href="<c:url value="/portal/secure/services-map.htm?service=${service.bodID}&domain=${service.user.homeDomain.bodID}"/>">${service.bodID}</a></li>
				</c:forEach>
			</ul>
		</div>
</div>
</div>