<%@ include file="../common/includes.jsp" %>
<!-- tab styling -->
<link rel="stylesheet" type="text/css" href="<c:url value="/js/jquery/tabs.css"/>"/>
<script type="text/javascript" src="<c:url value="/scripts/jscalendar/js/jscal2.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/jscalendar/js/lang/en.js"/>"></script>
<script src="http://cdn.jquerytools.org/1.2.3/full/jquery.tools.min.js"></script>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.validate.min.js"/>"></script>
<script language="javascript">

function passPort(){
	//alert(document.getElementById('request.startPort').options[document.getElementById('request.startPort').options.selectedIndex].text);
setStartFriendlyName(document.getElementById('request.startPort').options[document.getElementById('request.startPort').options.selectedIndex].text);
setEndFriendlyName(document.getElementById('request.endPort').options[document.getElementById('request.endPort').options.selectedIndex].text);

//setEndFriendlyName(document.getElementById('request.endPort').value)
}

function setStartFriendlyName(path){
	//alert(path);
	document.getElementById('request.startPortFriendlyName').value=path;
//alert(document.getElementById('endPortFriendlyName').value);
}

function setEndFriendlyName(path){
//alert(path);
	document.getElementById('request.endPortFriendlyName').value=path;
//alert(document.getElementById('endPortFriendlyName').value);
}


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
<body onload="passPort()">
<form:form commandName="reservation" id="reservationform">
<div id="form">

<h2><spring:message code="reservation.htitle" text="Reservation form"/></h2>

<c:if test="${friendlyports_domain != null}">
<div id="wizard">

<!-- tabs -->

<ul class="tabs">
    <li><a href="#" class="w2">Tab 1</a></li>
    <li><a href="#" class="w2">Tab 2</a></li>
    <li><a href="#" class="w2">Tab 3</a></li>
</ul>
<div class="panes">

<div>
    <table>
       <tr>
		<td class="label"><spring:message code="reservation.startPort"/></td>
		<td class="value">
			<form:select path="request.startPort" onchange="setStartFriendlyName(this.options[this.options.selectedIndex].text)">
				<form:options items="${friendlyports_domain}" itemValue="identifier" itemLabel="friendlyName"/>	
			</form:select>
		</td>
		<form:hidden path="request.startPortFriendlyName" />
		<td class="error"><form:errors path="request.startPort"/></td>
	</tr>
	<tr>
		<td class="label"><spring:message code="reservation.endPort"/></td>
		<td class="value">
			<form:select path="request.endPort" onchange="setEndFriendlyName(this.options[this.options.selectedIndex].text)">
				<!--form:options items="${friendlyAndIDCPports_all}" itemValue="identifier" itemLabel="friendlyName"/-->
				<form:options items="${friendlyports_all}" itemValue="identifier" itemLabel="friendlyName"/>
				<option disabled="true" value="IDCP">IDCP</option>
                <form:options items="${idcpPorts_all}" />
			</form:select>
		</td>
		<form:hidden path="request.endPortFriendlyName" />
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
	<td class="label" style="min-width:150px"><spring:message code="reservation.startTime"/><br /><span class="error"><form:errors path="request.startTime"/></span></td>
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
	<td class="error"></td>
</tr>
<tr>
	<td class="label" style="min-width:150px"><spring:message code="reservation.endTime"/><br /><span class="error"><form:errors path="request.endTime"/></span></td>
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
	<td class="error"></td>
</tr>
      
        <tr>
            <td class="label"><spring:message code="reservation.capacity"/></td>
            <td class="value">
                <form:input path="request.capacity" id="rcapacity"
                 onblur="checkMinusCapacityVlan('capacity','request.capacity')"/>
            </td>
            <td class="error"><form:errors path="request.capacity"/></td>
        </tr>
        
        <tr>
            <td class="label"><spring:message code="reservation.processNow"/></td>
            <td class="value">
                <form:checkbox path="request.processNow"/>
            </td>
            <td class="error"><form:errors path="request.processNow"/></td>
        </tr>
        
        <tr>
            <td class="label" style="min-width:150px"><spring:message code="reservation.description"/><br /><span class="error"><form:errors path="request.description"/></span></td>
            <td class="value">
                <form:textarea rows="8" cols="65" path="request.description" id="rdescription"/>
            </td>
            <td class="error"></td>
        </tr>

    </table>
    <br>

	<table class="pos">
        <tr>
           <td>
                <input type="submit" name="_eventId_submit" onmouseover="this.style='background-color: #FFF;'"  
                 style="height: 25px; width: 220px"   value="<spring:message code="reservation.submit"/>"/>
           </td>
           <td>
                <input type="submit" name="_eventId_test"
                	style="height: 25px; width: 220px"  value="<spring:message code="reservation.test"/>"/>
            </td>
            <td>
                <input type="submit" name="_eventId_cancel" 
                	style="height: 25px; width: 220px"    value="<spring:message code="reservation.cancel"/>"/>
            </td>
         </tr>
   
    </table>

</div>
<div>
    <table>
        <tr>
            <td class="label"><spring:message code="reservation.userVlanId"/></td>
            <td class="value">
                <form:input path="request.userVlanId" id="ruserVladId"
                            onblur="checkMinusCapacityVlan('VLAN identifier','request.userVlanId')"/>
            </td>
            <td class="error"><form:errors path="request.userVlanId"/></td>
        </tr>
         <tr>
            <td class="label"><spring:message code="reservation.maxDelay"/></td>
            <td class="value">
                <form:input path="request.maxDelay" id="rdelay"/>
            </td>
            <td class="error"><form:errors path="request.maxDelay" id="rdelay"/></td>
        </tr>
       <tr>
		<td class="label"><spring:message code="reservation.mtu"/></td>
		<td class="value">
			<form:input path="request.mtu" onblur="checkMinusCapacityVlan('Mtu size','request.mtu')" />
		</td>
		<td class="error"><form:errors path="request.mtu"/></td>
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
        

    </table>

	<table class="pos">
        <tr>
           <td>
                <input type="submit" name="_eventId_submit" 
                 style="height: 25px; width: 220px"   value="<spring:message code="reservation.submit"/>"/>
           </td>
           <td>
                <input type="submit" name="_eventId_test" 
                	style="height: 25px; width: 220px"  value="<spring:message code="reservation.test"/>"/>
            </td>
            <td>
                <input type="submit" name="_eventId_cancel"  
                	style="height: 25px; width: 220px"    value="<spring:message code="reservation.cancel"/>"/>
            </td>
         </tr>
   
    </table>

</div>

<div>
<table>
		<tr>
            <td class="label" valign="top"><spring:message code="reservation.userInclude"/></td>
            <td>
                <table>
                    <tr>
                        <td class="labelcenter"><spring:message code="reservation.userIncludeDomains"/></td>
                        <td class="label"></td>
                        <td class="label"></td>
                        <td class="labelcenter"><spring:message code="reservation.userIncludeLinks"/></td>
                    </tr>
                    <tr>
                        <td class="value">
                            <form:select path="request.userInclude.domains" >
                                <form:options items="${domains_all}"/>
                            </form:select>
                        </td>
                        <td class="value"></td>
						<td class="value"></td>
                        <td class="value">
                            <form:select path="request.userInclude.links" >
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
   
        <tr>
            <td class="label" valign="top"><spring:message code="reservation.userExclude"/></td>
            <td>
                <table>
                    <tr>
                        <td class="labelcenter"><spring:message code="reservation.userExcludeDomains"/></td>
                        <td class="label"></td>
                        <td class="label"></td>
                        <td class="labelcenter"><spring:message code="reservation.userExcludeLinks"/></td>
                        
                    </tr>
                    <tr>
                        <td class="value">
                            <form:select path="request.userExclude.domains" >
                                <form:options items="${domains_all}"/>
                            </form:select>
                        </td>
                        
						<td class="value"></td>
						<td class="value"></td>
						
                        <td c class="value">
                            <form:select path="request.userExclude.links" >
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

	</table>

	<table class="pos">
        <tr>
           <td>
                <input type="submit" name="_eventId_submit" 
                 style="height: 25px; width: 220px"   value="<spring:message code="reservation.submit"/>"/>
           </td>
           <td>
                <input type="submit" name="_eventId_test" 
                	style="height: 25px; width: 220px"  value="<spring:message code="reservation.test"/>"/>
            </td>
            <td>
                <input type="submit" name="_eventId_cancel"  
                	style="height: 25px; width: 220px"    value="<spring:message code="reservation.cancel"/>"/>
            </td>
         </tr>   
    </table>

</div>

    <c:if test="${test!= null}">
        <c:if test="${test.status==true}">
            <h3 style="color:green">Reservation test succeeded</h3>
        </c:if> 
        <c:if test="${test.status==false}">
            <h3 style="color:red">Reservation test failed</h3>
        </c:if>
    </c:if>

</div>
</div>

</c:if>
<c:if test="${friendlyports_domain == null}">
    Cannot retrieve ports. Cannot connect to IDM.
</c:if>
</div>

</form:form>
<script>

    jQuery(document).ready(function() {

        $(function() {

            var wizard = $("#wizard");

            $("ul.tabs", wizard).tabs("div.panes > div", function(event, index) {
    
                vv = $("#reservationform").valid();

                if (!vv)
                {
                    return false;
                }
              
            });

        });
    });


</script>

</body>
