<%@ include file="../common/includes.jsp"%>
<h2><spring:message code="service.htitle" text="Request Reservation Service" /></h2>

<form method="post">
<input type="hidden" name="action" value="update"/>
<table>
	<tag:tagSelectedElement name="Requested IDM" path="service.userHomeDomain" list="${idms}"/>
	<tag:tableFormElement name="User name" path="service.userName"/>
	<tag:tableFormElement name="User email" path="service.userEmail"/>
<tr>	
	<spring:bind path="service.justification">
	<td class="label"><spring:message code="service.justification" text="Justification"/></td>
	<td class="value">
		<textarea rows="4" name="justification">${status.value}</textarea>
	</td>
	<td class="error">${status.errorMessage}</td>
	</spring:bind>
</tr>
<tr>
	<td class="label"></td><td><input type="submit" value="<spring:message code="service.update" text="Service update"/>"/>
	</td>
</tr>
</table>
</form>
<div id="collection">
<br>
<form method="POST">
<input type="hidden" name="action" value="add"/>
<h2>Service Reservations <input width="100px" type="submit" value="Add reservation"/></h2>
<br/>
</form>
	
		
	<table>
		<tr>
			<th><spring:message code="service.reservations.action" text="Action" /></th>
			<th><spring:message code="reservation.startTime" text="Start time"/></th>
			<th><spring:message code="reservation.endTime" text="End time"/></th>
			<th><spring:message code="reservation.priority" text="Priority"/></th>
			<th><spring:message code="reservation.startPort" text="Start port"/></th>
			<th><spring:message code="reservation.endPort" text="End port"/></th>
			<th><spring:message code="reservation.delay" text="Delay [ms]"/></th>
			<th><spring:message code="reservation.capacity" text="Capacity [Mbits/s]"/></th>
			<th><spring:message code="reservation.userVlanId" text="Vlan"/></th>
			<th><spring:message code="reservation.resilience" text="Resilience"/></th>
		</tr>  
		<c:forEach items="${service.reservations}" var="item" varStatus="loopStatus">
			<spring:bind path="service.reservations">
				<tr>
					<td>
					<form method="POST">
          				<input type="hidden" name="action" value="remove"/>
          				<input type="hidden" name="id" value="${loopStatus}"/>
          				<input type="submit" value="Remove"/>
          			</form>
        			</td>
					<td>${item.startTime.time}</td>
					<td>${item.endTime.time}</td>
					<td>${item.priority}</td>
					<td>${item.startPort}</td>
					<td>${item.endPort}</td>
					<td>${item.maxDelay}</td>
					<td>${item.capacity/1000000}</td>
					<td>${item.userVlanId}</td>
					<td>${item.resiliency}</td>
				</tr>
			</spring:bind>
		</c:forEach>
	</table>
	</div>
	<br>
	<hr/>
	<form method="POST">
		  <input type="hidden" name="action" value="save"/>
          <input style="width:100%" type="submit" value="Submit" />
    </form>
	<hr/>
