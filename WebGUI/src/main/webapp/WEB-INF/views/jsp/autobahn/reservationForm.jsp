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

<div id="form">
<form method="post" action="reservationForm.htm">
<hr> 
<h2><spring:message code="reservation.htitle" text="Reservation form" /></h2>
<hr>
<table>
	<tag:tagSelectedElement name="Start port" list="${ports_domain}" path="reservation.startPort"/>
	<tag:tagSelectedElement name="End port" list="${ports_all}" path="reservation.endPort"/>
	<tag:tagSelectedElement-not-bind name="timezone" label="Time zone" list="${timezones}" value="${timezone}"/>
<tr>
	<td class="label"><spring:message code="startTime" text="Start Time"/></td>
	<td class="value">
		<table style="margin:0px;border:0px; text-align:left">
			<tr>
				<td id="holder">&nbsp;</td>
				<td>
				<input size="10" type="text" name="startTime" value="${startTime}" onfocus="showCalendar('',this,this,'2006-01-01 00:00 AM','holder',0,30,1)">
				</td>
			</tr>
		</table>
	</td>
	
	<spring:bind path="reservation.startTime">
	<td>${status.errorMessage}</td>
	</spring:bind>
</tr>
<tr>
	<td class="label"><spring:message code="endTime" text="End Time"/></td>
	<td class="value">
		<table cellpadding="0" cellspacing="0" border="0" align="left">
			<tr>
				<td id="holder2"></td>
				<td><input size="10" type="text" name="endTime" value="${endTime}" onfocus="showCalendar('',this,this,'2006-01-01 00:00 AM','holder',0,30,1)"></td>
			</tr>
			</table>
	</td>
	<spring:bind path="reservation.endTime">
	<td>${status.errorMessage}</td>
	</spring:bind>
</tr>

	<tag:tableFormElement name="Delay [ms]" path="reservation.maxDelay"/>
	<tag:tableFormElement name="Capacity [Mbits/s]" path="reservation.capacity"/>	
	<tag:tableFormElement name="Vlan" path="reservation.userVlanId"/>	
	<tag:tagSelectedElement name="Resiliency" list="${resilencies}" path="reservation.resiliency"/>
	<tag:tagSelectedElement name="Priority" list="${priorities}" path="reservation.priority"/>
	
	<tr>
		<td class="label"><spring:message code="reservation.processNow" text="Process now" /></td>
		<spring:bind path="reservation.processNow">
			<td class="value">
			<input type="hidden" name="reservation.processNow"/>
			<input align="left" width="20px" type="checkbox" name="${status.expression}" value="true"
				<c:if test="${status.value}">checked="checked"</c:if>/>
			</td>
			<td class="error">${status.errorMessage}</td> 
		</spring:bind>
	</tr>
	
	
	<tr>
	<td class="label"><spring:message code="reservation.description" text="Description"/></td>
	<td class="value">
	<spring:bind path="reservation.description">
		<textarea rows="5" name="description">${status.value}</textarea>
	</spring:bind>
	</td>
	</tr>
	</table>
	<hr>
	
	<input style="width:100%" align="middle" type="submit" value="<spring:message code="reservation.submit" text="Add reservation" />"/>
	<hr>
</form>
</div>