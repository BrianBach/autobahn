<%@ include file="../common/includes.jsp"%>
<script type="text/javascript" src="<c:url value="/scripts/jscalendar/js/jscal2.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/jscalendar/js/lang/en.js"/>"></script>
<script language="javascript">

function dateOffset(date) {
var d=toJSDate(date||"");
  
d.setTime(d.getTime()+86400000);
var offsetbazo = toCalendarDate(d);
var mySplitResult = offsetbazo.split("T");
var splittaro = mySplitResult[0]+"T23:59:59";
return splittaro;
}

function checkDatePast(date1,date2,date3,date4,date5,date6,date7,date8) {
var today = new Date();
var d=toCalendarDate(today);
var mySplitResult1 = d.split("T");
var mySplitResult2 = date3.split("T");
	if(mySplitResult2[0]<mySplitResult1[0]){
		var splittaro = mySplitResult1[0]+"T23:59:59";
		if(date1=="start"){
			document.forms[0]["startTime"].value=splittaro;
	              alert("The date you selected is old. Your calendar has been reset.");
		} else if (date1=="end") {
			document.forms[0]["endTime"].value=splittaro;
			alert("The date you selected is old. Your calendar has been reset.");
		} else {
			return 0;
		}
	}
}

function checkMinus(field,id){

var testing = document.forms[0][id];
var val = testing.value;
var final = testing.value;

var count = "un"; 
	for(var i=0;i<val.length;i++){
		if((!isDigit(val.charAt(i))) && (val.charAt(i)!=".")){
		final = val.replace(/[^0-9.]/gi,'');
			if(count!="lo"){
				alert("Positive " + field + " value is only accepted. We assume " + field + " is: " + final);
				count="lo";
			}
		}
	}
	if(final=='' || final==undefined){
		final = 0;
		alert("Positive " + field + " value is only accepted. We assume " + field + " is: " + final);		
		document.forms[0][id].value= final;
	} else {
		document.forms[0][id].value= final;
	}
}


function isDigit(num) {
	if (num.length>1){return false;}
	var string="1234567890";
	if (string.indexOf(num)!=-1){return true;}
	return false;
}

function checkMinusCapacityVlan(field,id){

var testing = document.forms[0][id];
var val = testing.value;
var final = testing.value;


	if(document.forms[0][id].value==0 && field=="capacity"){
		alert("Capacity cannot be zero. We assume " + field + " is: " + 1000);
		final = 1000;
		document.forms[0][id].value= final;
	}

	var count = "un"; 
	for(var i=0;i<val.length;i++){
		if(!isDigit(val.charAt(i))){
			final = val.replace(/[^0-9]/gi,'');
			if(count!="lo"){
				alert("Positive " + field + " value is only accepted. We assume " + field + " is: " + final);
				count="lo";
			}
		}
	}
	if((final=='' || final==undefined) && field=="capacity"){
		final = 1000;
		alert("Positive " + field + " value is only accepted. We assume " + field + " is: " + final);		
		document.forms[0][id].value= final;
	} else if(final=='' || final==undefined && field!="capacity") {
		final = 0;
		alert("Positive " + field + " value is only accepted. We assume " + field + " is: " + final);		
		document.forms[0][id].value= final;
	} else {
		document.forms[0][id].value= final;
	}

}

</script>
<form:form commandName="reservation">
<div id="form">
<hr> 
<h2><spring:message code="reservation.htitle" text="Reservation form" /></h2>
<hr>
<c:if test="${friendlyports_domain != null}" >
<table>
	<tr>
		<td class="label"><spring:message code="reservation.startPort"/></td>
		<td class="value">
			<form:select path="request.startPort">
				<form:options items="${friendlyports_domain}" itemValue="identifier" itemLabel="friendlyName"/>
			</form:select>
		</td>
		<td class="error"><form:errors path="request.startPort"/></td>
	</tr>
	<tr>
		<td class="label"><spring:message code="reservation.endPort"/></td>
		<td class="value">
			<form:select path="request.endPort">
				<form:options items="${friendlyAndIDCPports_all}" itemValue="identifier" itemLabel="friendlyName"/>
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
				<form:input path="request.startTime" id="startTime" cssStyle="width:150px;margin-right:0px;"/> 
				<script type="text/javascript">
						now = new Date();
						var month;
						if(now.getMonth()<10){
							month = "0" + now.getMonth();
						} else {
							month = now.getMonth();
						}
						
						var seconds;
						if(now.getSeconds()<10){
							seconds = "0" + now.getSeconds();
						} else {
							seconds = now.getSeconds();
						}
						
						var minutes;
						if(now.getMinutes()<10){
							minutes= "0" + now.getMinutes();
						} else {
							minutes= now.getMinutes();
						}
						
						var day;
						if(now.getDate()<10){
							day= "0" + now.getDate();
						} else {
							day= now.getDate();
						}
						
						var hour = parseFloat(now.getHours())+1;
						if(now.getHours()<10){
							hour= "0" + now.getHours();
						} else {
							hour= parseFloat(now.getHours())+1;
						}
						
						var dateString = now.getFullYear() + "-" + month + "-" + day + "T" + hour + ":" + minutes + ":" + seconds;

						
						document.forms[0]["startTime"].value= dateString;
						
					    Calendar.setup({
					        animation  : false,
					        trigger    : "startTime",
					        inputField : "startTime",
					        showTime   : true,
					        date	   : "%Y-%m-%d %H:%M:%S",
					        dateFormat : "%Y-%m-%dT%H:%M:%S",
					        minuteStep : 1,
					        onSelect   : function(){
							this.hide();
							checkDatePast('start','',document.forms[0]["startTime"].value,'','','','','');					
						    } 
					    });
					   
					</script>
				</td>
			</tr>
		</table>
	</td>
	<td class="error"><form:errors path="request.startTime"/></td>
</tr>
<tr>
	<td class="label"><spring:message code="reservation.endTime"/></td>
	<td class="value">
		<table style="margin:0px;border:0px; text-align:left">
			<tr>
				<td id="holder">&nbsp;</td>
				<td>
				<form:input path="request.endTime" id="endTime" cssStyle="width:150px;margin-right:0px;"/> 
				<script type="text/javascript">
					now = new Date();
					var month2;
					if(now.getMonth()<10){
						month2 = "0" + now.getMonth();
					} else {
						month2 = now.getMonth();
					}
					
					var seconds2;
					if(now.getSeconds()<10){
						seconds2 = "0" + now.getSeconds();
					} else {
						seconds2 = now.getSeconds();
					}
					
					var minutes2;
					if(now.getMinutes()<10){
						minutes2= "0" + now.getMinutes();
					} else {
						minutes2= now.getMinutes();
					}
					
					var day2;
					if(now.getDate()<10){
						day2= "0" + now.getDate();
					} else {
						day2= now.getDate();
					}
					var hour2 = parseFloat(now.getHours())+2;
					if(now.getHours()<10){
							hour2= "0" + now.getHours();
						} else {
							hour2= parseFloat(now.getHours())+1;
						}		
					var dateString2 = now.getFullYear() + "-" + month2 + "-" + day2 + "T" + hour2 + ":" + minutes2 + ":" + seconds2;
					document.forms[0]["endTime"].value= dateString2;						
					    Calendar.setup({
					        animation  : false,
					        trigger    : "endTime",
					        inputField : "endTime",
					        showTime   : true,
					        date	   : "%Y-%m-%d %H:%M:%S",
					        dateFormat : "%Y-%m-%dT%H:%M:%S",
					        minuteStep : 1,
					        onSelect   : function() { 		
					        this.hide(); 
					    	checkDatePast('end','',document.forms[0]["endTime"].value,'','','','','');    
					        }
					    });
					</script>
				</td>
			</tr>
		</table>
	</td>
	<td class="error"><form:errors path="request.endTime"/></td>
</tr>

	<tr>
		<td class="label"><spring:message code="reservation.maxDelay"/></td>
		<td class="value">
			<form:input path="request.maxDelay" onblur="checkMinus('delay','request.maxDelay')" />
		</td>
		<td class="error"><form:errors path="request.maxDelay"/></td>
	</tr>
	<tr>
		<td class="label"><spring:message code="reservation.capacity"  /></td>
		<td class="value">
			<form:input path="request.capacity" onblur="checkMinusCapacityVlan('capacity','request.capacity')" />
		</td>
		<td class="error"><form:errors path="request.capacity"/></td>
	</tr>
	<tr>
		<td class="label"><spring:message code="reservation.userVlanId"/></td>
		<td class="value">
			<form:input path="request.userVlanId" onblur="checkMinusCapacityVlan('VLAN identifier','request.userVlanId')" />
		</td>
		<td class="error"><form:errors path="request.userVlanId"/></td>
	</tr>
	<tr><td><br><br></td></tr>
	<tr>
		<td class="label" valign="top"><spring:message code="reservation.userInclude"/></td>
		<td>
			<table>
				<tr>
					<td class="labelcenter"><spring:message code="reservation.userIncludeDomains"/></td>
					<td class="labelcenter"><spring:message code="reservation.userIncludeLinks"/></td>
				</tr>
				<tr>
					<td class="valuecenter">
						<form:select path="request.userInclude.domains" multiple="true">
							<form:options items="${domains_all}"/>
						</form:select>
					</td>
	
					<td class="valuecenter">
						<form:select path="request.userInclude.links" multiple="true">
							<form:options items="${links_all}"/>
						</form:select>
					</td>
				</tr>
			</table>
		</td>
		<td class="error">
			<form:errors path="request.userInclude.domains"/><br>
			<form:errors path="request.userInclude.links"/>
		</td>
	</tr>
	<tr><td><br><br></td></tr>
	<tr>
		<td class="label" valign="top"><spring:message code="reservation.userExclude"/></td>
		<td>
			<table>
				<tr>
					<td class="labelcenter"><spring:message code="reservation.userExcludeDomains"/></td>
					<td class="labelcenter"><spring:message code="reservation.userExcludeLinks"/></td>
				</tr>
				<tr>
					<td class="valuecenter">
						<form:select path="request.userExclude.domains" multiple="true">
							<form:options items="${domains_all}"/>
						</form:select>
					</td>
	
					<td class="valuecenter">
						<form:select path="request.userExclude.links" multiple="true">
							<form:options items="${links_all}"/>
						</form:select>
					</td>
				</tr>
			</table>
		</td>
		<td class="error">
			<form:errors path="request.userExclude.domains"/><br>
			<form:errors path="request.userExclude.links"/>
		</td>
	</tr>
	<tr><td><br><br></td></tr>
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
			<h2>Reservation test succeeded</h2>
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
</c:if>
<c:if test="${friendlyports_domain == null}" >
	Cannot retrieve ports. Cannot connect to IDM.
</c:if>	
</div>
</form:form>

