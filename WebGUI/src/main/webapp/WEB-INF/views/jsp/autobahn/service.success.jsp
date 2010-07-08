<%@ include file="../common/includes.jsp"%>
<div id="form">
<hr/>
<h2>Confirmation form</h2>
<hr/>
	<table>
		<tr>
			<td class="label"><spring:message code="service.id" text="Identyfier" /></td>
			<td class="value">${service.id}</td>
		</tr>
		<tr>
			<td class="label"><spring:message code="service.user" text="User" /></td>
			<td class="value">${service.user.name}-${service.user.email}</td>
		</tr>
		<tr>
			<td class="label"><spring:message code="service.jastification" text="Justification" /></td>
			<td class="value">${service.jastification}</td>
		</tr>
	</table>
	<div id="collection">
<h3>Service Reservations</h3>
	<table width="100%">
		<tr>
			<th><spring:message code="reservation.state" text="State"/></th>
			<th><spring:message code="reservation.startTime" text="Start time"/></th>
			<th><spring:message code="reservation.endTime" text="End time"/></th>
			<th><spring:message code="reservation.priority" text="Priority"/></th>
			<th><spring:message code="reservation.startPort" text="Start port"/></th>
			<th><spring:message code="reservation.endPort" text="End port"/></th>
			<th><spring:message code="reservation.capacity" text="Capacity"/></th>
			<th><spring:message code="reservation.userVlanId" text="Vlan"/></th>
			<th><spring:message code="reservation.resilience" text="Recielancy"/></th>
		</tr>  
		<c:forEach items="${service.reservations.list}" var="item" varStatus="loopStatus">
				<tr>
					<td>${item.startTime.time}</td>
					<td>${item.endTime.time}</td>
					<td>${item.priority}</td>
					<td>${item.startPort}</td>
					<td>${item.endPort}</td>
					<td>${item.capacity}</td>
					<td>${item.userVlanId}</td>
					<td>${item.resilience}</td>
				</tr>
		</c:forEach>
	</table>
	</div>
</div>