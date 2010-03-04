<%@ include file="../common/includes.jsp"%>
<script language="javascript">

function beforeSetDateValue(ref_field, target_field, date) {
  if (date!="") {
    var startDate=document.forms[0]["startTime"];
    var endDate=document.forms[0]["endTime"];

    if (target_field==endDate &&
        checkDate(getDateValue(startDate))==0 &&
        compareDates(getDateValue(startDate), date)>0) {
      date=getDateValue(endDate);
      alert("End Date should not be earlier than Start Date, please select again.");
    }
  }
  return date;
}
function afterSetDateValue(ref_field, target_field, date) {
  if (date!="") {
    var startDate=document.forms[0]["startTime"];
    var endDate=document.forms[0]["endTime"];

    if (target_field==startDate &&
        checkDate(getDateValue(endDate))==0 &&
        compareDates(date, getDateValue(endDate))>0) {
      setDateValue(endDate, date);
      alert("End Date was earlier than Start Date, it's now set to Start Date.");
    }
  }
}
</script>
<form:form commandName="reservation">
<div id="form">
<hr> 
<h2><spring:message code="reservation.htitle" text="Reservation form" /></h2>
<hr>
<table>
	<tr>
		<td class="label"><spring:message code="reservation.startPort"/></td>
		<td class="value">
			<form:select path="request.startPort">
				<form:options items="${ports_domain}"/>
			</form:select>
		</td>
		<td class="error"><form:errors path="request.startPort"/></td>
	</tr>
	<tr>
		<td class="label"><spring:message code="reservation.endPort"/></td>
		<td class="value">
			<form:select path="request.endPort">
				<form:options items="${ports_all}"/>
			</form:select>
		</td>
		<td class="error"><form:errors path="request.endPort"/></td>
	</tr>
	<tr>
		<td class="label"><spring:message code="reservation.timezone"/></td>
		<td class="value">
			<form:select path="timezone">
				<form:options items="${timezones}"/>
			</form:select>
		</td>
		<td class="error"><form:errors path="timezone"/></td>
	</tr>

	<tr>
	<td class="label"><spring:message code="reservation.startTime"/></td>
	<td class="value">
		<table style="margin:0px;border:0px; text-align:left">
			<tr>
				<td id="holder">&nbsp;</td>
				<td>
					<form:input  path="request.startTime" id="startTime"  onfocus="showCalendar('',this,this,'2006-01-01 00:00 AM','holder',0,30,1)"/>
				</td>
			</tr>
		</table>
	</td>
	<td><form:errors path="request.startTime"/></td>
</tr>
<tr>
	<td class="label"><spring:message code="reservation.endTime"/></td>
	<td class="value">
		<table style="margin:0px;border:0px; text-align:left">
			<tr>
				<td id="holder">&nbsp;</td>
				<td>
					<form:input  path="request.endTime" id="endTime"  onfocus="showCalendar('',this,this,'2006-01-01 00:00 AM','holder',0,30,1)"/>
				</td>
			</tr>
		</table>
	</td>
	<td><form:errors path="request.endTime"/></td>
</tr>

	<tr>
		<td class="label"><spring:message code="reservation.maxDelay"/></td>
		<td class="value">
			<form:input path="request.maxDelay"/>
		</td>
		<td class="error"><form:errors path="request.maxDelay"/></td>
	</tr>
	<tr>
		<td class="label"><spring:message code="reservation.capacity"/></td>
		<td class="value">
			<form:input path="request.capacity"/>
		</td>
		<td class="error"><form:errors path="request.capacity"/></td>
	</tr>
	<tr>
		<td class="label"><spring:message code="reservation.userVlanId"/></td>
		<td class="value">
			<form:input path="request.userVlanId"/>
		</td>
		<td class="error"><form:errors path="request.userVlanId"/></td>
	</tr>
	<tr bgcolor="#48D1CC">
		<td class="label" valign="top"><spring:message code="reservation.userInclude"/></td>
		<td>
			<table>
				<tr>
					<td class="label"><spring:message code="reservation.userIncludeDomains"/></td>
					<td class="value">
						<form:select path="request.userInclude.domains" multiple="true">
							<form:options items="${domains_all}"/>
						</form:select>
					</td>
					<td class="error"><form:errors path="request.userInclude.domains"/></td>
	
					<td class="label"><spring:message code="reservation.userIncludeLinks"/></td>
					<td class="value">
						<form:select path="request.userInclude.links" multiple="true">
							<form:options items="${links_all}"/>
						</form:select>
					</td>
					<td class="error"><form:errors path="request.userInclude.links"/></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr bgcolor="#FFFF00">
		<td class="label" valign="top"><spring:message code="reservation.userExclude"/></td>
		<td>
			<table>
				<tr>
					<td class="label"><spring:message code="reservation.userExcludeDomains"/></td>
					<td class="value">
						<form:select path="request.userExclude.domains" multiple="true">
							<form:options items="${domains_all}"/>
						</form:select>
					</td>
					<td class="error"><form:errors path="request.userExclude.domains"/></td>
	
					<td class="label"><spring:message code="reservation.userExcludeLinks"/></td>
					<td class="value">
						<form:select path="request.userExclude.links" multiple="true">
							<form:options items="${links_all}"/>
						</form:select>
					</td>
					<td class="error"><form:errors path="request.userExclude.links"/></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td class="label"><spring:message code="reservation.resiliency"/></td>
		<td class="value">
			<form:select path="request.resiliency">
				<form:options items="${resiliences}"/>
			</form:select>
		</td>
		<td class="error"><form:errors path="request.resiliency"/></td>
	</tr>
	<tr>
		<td class="label"><spring:message code="reservation.priority"/></td>
		<td class="value">
			<form:select path="request.priority">
				<form:options items="${priorities}"/>
			</form:select>
		</td>
		<td class="error"><form:errors path="request.priority"/></td>
	</tr>
	<tr>
		<td class="label"><spring:message code="reservation.processNow"/></td>
		<td class="value">
			<form:checkbox path="request.processNow"/>
		</td>
		<td class="error"><form:errors path="request.processNow"/></td>
	</tr>
	<tr>
		<td class="label"><spring:message code="reservation.description"/></td>
		<td class="value">
			<form:textarea rows="8" path="request.description"/>
		</td>
		<td class="error"><form:errors path="request.description"/></td>
	</tr>
	</table>
	<hr/>
	<c:if test="${test!= null}">
		<c:if test="${test.status==true}">
			<h2>Reservation test successed</h2>
		</c:if>
		<c:if test="${test.status==false}">
			<h2 style="color:red">Reservation test failed</h2>
		</c:if>	
	</c:if>
	<hr/>
	<table>
	<tr>
	<td>
		<input type="submit" name="_eventId_submit" value="<spring:message code="reservation.submit"/>"/>
	</td>
	<td>
		<input type="submit" name="_eventId_test" value="<spring:message code="reservation.test"/>"/>
	</td>
	<td>
		<input type="submit" name="_eventId_cancel" value="<spring:message code="reservation.cancel"/>"/>
	</td>
	</tr>
	</table>
</div>
</form:form>
