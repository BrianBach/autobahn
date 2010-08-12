<%@ include file="../common/includes.jsp"%>
<h2><spring:message code="reservations.htitle" text="Submited Services"/></h2>

<form:form commandName="services">
<input type="hidden" name="action" value="change"/>
<table>
	<tr>
		<td>
			<form:select path="currentIdm">
				<form:options items="${services.idms}"/>	
			</form:select>
		</td>
	<td>
		<input type="submit" name="_eventId_change" value="Change IDM" style="width:100px"/>
	</td>
	</tr>
</table>
	
<div id="servicesList">
<c:forEach items="${services.services}" var="element" varStatus="loopStatus">
	<hr>
	<h3><a name="${element.bodID}">Service: ${element.bodID}</a></h3>
	<table>
		<tr>
			<td class="label"><spring:message code="action" text="Action" /></td>
			<td>
				<a href="${flowExecutionUrl}&_eventId=cancel&id=${element.bodID}">Cancel</a>
				<a href="<c:url value="/portal/secure/services-map.htm"/>?service=${element.bodID}&domain=${element.user.homeDomain.bodID}">View map</a>
			</td>
		</tr>
		<tr>
			<td class="label"><spring:message code="service.user.homeDoamin" text="Home Domain" /></td>
			<td class="value">${element.user.homeDomain.name}</td>
		</tr>
		<tr>
			<td class="label"><spring:message code="service.user.name" text="User" /></td>
			<td class="value">${element.user.name} ${element.user.email}</td>
		</tr>
		<tr>
			<td class="label"><spring:message code="service.jastification" text="Justification" /></td>
			<td class="value">${element.justification}</td>
		</tr>
		<tr>
			<td class="label"><spring:message code="service.priority" text="Priority" /></td>
			<td class="value">${element.priority}</td>
		</tr>
	</table>
	<div id="collection">
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
			<th><spring:message code="reservation.resiliency" text="Resiliency"/></th>
		</tr>  
		<c:forEach items="${element.reservations}" var="item" varStatus="loopStatus">
				<tr>
					<td>${reservationStates[item.state]}(${item.state})</td>
					<td>${item.startTime.time}</td>
					<td>${item.endTime.time}</td>
					<td>${item.priority}</td>
					<td>${item.startPort.bodID}</td>
					<td>${item.endPort.bodID}</td>
					<td>${item.capacity/1000000}</td>
					<td>${item.userVlanId}</td>
					<td>${item.resiliency}</td>
				</tr>
		</c:forEach>
	</table>
	</div>
	<br>
	<h2>&nbsp;</h2>
</c:forEach>
</div>
</form:form>
