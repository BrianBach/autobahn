<%@ include file="../common/includes.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"prefix="fn" %>

<!--script src="http://cdn.jquerytools.org/1.2.3/full/jquery.tools.min.js"></script-->
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.validate.min.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/jquery/scrollable.css"/>"/>

<c:set var="size" value="${fn:length(services.services)}"></c:set>
<c:choose>
	<c:when test="${size %2 == 0}">
		<c:set var="flag" value="true"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="flag" value="false"></c:set>
	</c:otherwise>
</c:choose>
		
<c:set var="end" value="2"></c:set>
<c:set var="k" value="0"></c:set>


<c:if test="${size > 2}">
<div id="actions" style="padding-left:15px; text-align:center">
   	<a class="prev" style="position: relative; float: left;" >&laquo; BACK</a>
   	<div class="navi">
   	  <c:forEach begin="1" end="${size}" step="2" var="i">
   	    <c:set var="class" value=""></c:set>
   	    <c:if test="${i == 1}">
   	    	<c:set var="class" value="active"></c:set>
   	    </c:if>
   	    
   	    <c:choose>
   	      <c:when test="${i == size}">
   	        <a href="#" class="${class}">${i}</a>
   	      </c:when>
   	      <c:otherwise>
	   	    <a href="#" class="${class}">${i} - ${i + 1}</a>
   	      </c:otherwise>
   	    </c:choose>
   	  </c:forEach>
   	
   	<a class="next" style="font-weight:normal;border:0px;">NEXT &raquo;</a>
</div>
</div>
</c:if>

<div class="scrollable vertical" style="height:590px; overflow: hidden; ">
  <div class="items">
	<c:if test="${services.services==null}">
        <h3>No submitted services available.</h3>
    </c:if>
 
 
 
		<c:forEach items="${services.services}"  begin="${k}" varStatus="x">

<c:choose>
   <c:when test="${flag == 'true'}"> 
   					
   	<c:if test="${x.count %2 == 0}">
   				
  <div class="item" style="height:570px; overflow: auto;">
   	<c:forEach items="${services.services}" var="element" begin="${k}" end="${end-1}" varStatus="y">
   								
   	<h3 align="right" style="padding-bottom:10px;"><b>Service: ${element.bodID}</b></h3>
	<table>
		<tr>
			<td class="label"><spring:message code="service.user.homeDoamin" text="Home Domain" /></td>
			<td class="value" style="padding-left:20px;width:400px">${element.user.homeDomain.name}</td>
		</tr>
		<tr>
			<td class="label"><spring:message code="service.user.name" text="User" /></td>
			<td class="value" style="padding-left:20px;width:400px">${element.user.name} ${element.user.email}</td>
		</tr>
		<tr>
			<td class="label"><spring:message code="service.jastification" text="Justification" /></td>
			<td class="value" style="padding-left:20px;width:400px">${element.justification}</td>
		</tr>
		<tr>
			<td class="label"><spring:message code="service.priority" text="Priority" /></td>
			<td class="value" style="padding-left:20px;width:400px">${element.priority}</td>
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
			<th><spring:message code="reservation.mtu" text="Mtu"/></th>
			<th><spring:message code="reservation.resiliency" text="Resiliency"/></th>
		</tr>  
		<c:forEach items="${element.reservations}" var="item" varStatus="loopStatus">
				<tr>
					<td>${reservationStates[item.state]}(${item.state})</td>
					<td>${item.startTime.time}</td>
					<td>${item.endTime.time}</td>
					<td>${item.priority}</td>
					<td>${item.startPort.description} (${item.startPort.bodID})</td>
					<td>${item.endPort.description} (${item.startPort.bodID})</td>
					<td>${item.capacity/1000000}</td>
					<td>${item.userVlanId}</td>
					<td>${item.mtu}</td>
					<td>${item.resiliency}</td>
				</tr>
		</c:forEach>
	</table>
<!--div style="position:relative;float:left;padding-top:20px"><a style="text-decoration:none;padding:0px;color:#000000;background:#ffffff;border:none;" href="${flowExecutionUrl}&_eventId=cancel&id=${element.bodID}"><input id="cancel" name="Cancel" value="Cancel" type="submit" style="width:100px;" onclick="window.top.location='${flowExecutionUrl}&_eventId=cancel&id=${element.bodID}'" /></a-->
<script>
//alert($("#currentIdm").val());
</script>
<!--div style="position:relative;float:left;padding-top:20px"><a style="text-decoration:none;padding:0px;color:#000000;background:#ffffff;border:none;" href="#" onclick="window.top.location=location.href + '&_eventId=cancel&id=${element.bodID}&currentIdm2=' + $('#currentIdm').val()"><input id="cancel" name="Cancel" value="Cancel" type="submit" style="width:100px;" onclick="window.top.location=location.href + '&_eventId=cancel&id=${element.bodID}&currentIdm2=' + $('#currentIdm').val()" /></a-->
<div style="position:relative;float:left;padding-top:20px"><span style="text-decoration:none;padding:0px;color:#000000;background:#ffffff;border:none;"><input id="cancel" name="Cancel" value="Cancel" type="submit" style="width:100px;" onclick="jQuery.post(location.href + '&_eventId=cancel&id=${element.bodID}&currentIdm2=' + $('#currentIdm').val() )" /></span>

<!--div style="position:relative;float:left;padding-top:20px"><a style="text-decoration:none;padding:0px;color:#000000;background:#ffffff;border:none;" href="reservations_select.htm?execution=e10s1&_eventId=cancel&id=${element.bodID}"><input id="cancel" name="Cancel" value="Cancel" type="submit" style="width:100px;" onclick="window.top.location='reservations_select.htm?execution=e10s1&_eventId=cancel&id=${element.bodID}'" /></a-->

				<a style="text-decoration:none;padding:0px;color:#000000;background:#ffffff;border:none;"  href="<c:url value="/portal/secure/services-map.htm"/>?service=${element.bodID}&domain=${element.user.homeDomain.bodID}">
<input id="view" name="view" value="View map" type="submit" style="width:100px" onclick="window.top.location='<c:url value="/portal/secure/services-map.htm"/>?service=${element.bodID}&domain=${element.user.homeDomain.bodID}'" /></a>
				
	</div>
	<br><br><br>
 	</div>
   								
   	</c:forEach>
   </div>
   					
   	<c:set var="k" value="${x.count}"></c:set>
   	<c:set var="end" value="${x.count + 2}"></c:set>
   				
   </c:if>
   		
   	</c:when>
   			
   	<c:otherwise>
   	<c:choose>
   			
   	<c:when test="${x.count + 1 > size}">
   				
   		<div class="item"  style="height:570px; overflow: auto;">
   			<c:forEach items="${services.services}" var="element" begin="${k}" end="${end-1}" varStatus="y">
   	<h3 align="right" style="padding-bottom:10px;"><b>Service: ${element.bodID}</b></h3>
	<table>
		<tr>
			<td class="label"><spring:message code="service.user.homeDoamin" text="Home Domain" /></td>
			<td class="value" style="padding-left:20px;width:400px">${element.user.homeDomain.name}</td>
		</tr>
		<tr>
			<td class="label"><spring:message code="service.user.name" text="User" /></td>
			<td class="value" style="padding-left:20px;width:400px">${element.user.name} ${element.user.email}</td>
		</tr>
		<tr>
			<td class="label"><spring:message code="service.jastification" text="Justification" /></td>
			<td class="value" style="padding-left:20px;width:400px">${element.justification}</td>
		</tr>
		<tr>
			<td class="label"><spring:message code="service.priority" text="Priority" /></td>
			<td class="value" style="padding-left:20px;width:400px">${element.priority}</td>
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
			<th><spring:message code="reservation.mtu" text="Mtu"/></th>
			<th><spring:message code="reservation.resiliency" text="Resiliency"/></th>
		</tr>  
		<c:forEach items="${element.reservations}" var="item" varStatus="loopStatus">
				<tr>
					<td>${reservationStates[item.state]}(${item.state})</td>
					<td>${item.startTime.time}</td>
					<td>${item.endTime.time}</td>
					<td>${item.priority}</td>
					<td>${item.startPort.description} (${item.startPort.bodID})</td>
					<td>${item.endPort.description} (${item.startPort.bodID})</td>
					<td>${item.capacity/1000000}</td>
					<td>${item.userVlanId}</td>
					<td>${item.mtu}</td>
					<td>${item.resiliency}</td>
				</tr>
		</c:forEach>
	</table>
<!--div style="position:relative;float:left;padding-top:20px"><a style="text-decoration:none;padding:0px;color:#000000;background:#ffffff;border:none;" href="${flowExecutionUrl}&_eventId=cancel&id=${element.bodID}"><input id="cancel" name="Cancel" value="Cancel" type="submit" style="width:100px;" onclick="window.top.location='${flowExecutionUrl}&_eventId=cancel&id=${element.bodID}'" /></a-->
<div style="position:relative;float:left;padding-top:20px"><span style="text-decoration:none;padding:0px;color:#000000;background:#ffffff;border:none;"><input id="cancel" name="Cancel" value="Cancel" type="submit" style="width:100px;" onclick="jQuery.post(location.href + '&_eventId=cancel&id=${element.bodID}&currentIdm2=' + $('#currentIdm').val() )" /></span>

				<a style="text-decoration:none;padding:0px;color:#000000;background:#ffffff;border:none;"  href="<c:url value="/portal/secure/services-map.htm"/>?service=${element.bodID}&domain=${element.user.homeDomain.bodID}">
<input id="view" name="view" value="View map" type="submit" style="width:100px" onclick="window.top.location='<c:url value="/portal/secure/services-map.htm"/>?service=${element.bodID}&domain=${element.user.homeDomain.bodID}'" /></a>
				
	</div>
	<br><br><br>
 	</div>				
   								
   								
   </c:forEach>
   		</div>
   				
   	</c:when>
   				
   	<c:otherwise>
   				
   	<c:if test="${x.count %2 == 0}">
   				
   	<div class="item" style="height:570px; overflow: auto;">
   			<c:forEach items="${services.services}" var="element" begin="${k}" end="${end-1}" varStatus="z">
   	<h3 align="right" style="padding-bottom:10px;"><b>Service: ${element.bodID}</b></h3>
	<table>
		<tr>
			<td class="label"><spring:message code="service.user.homeDoamin" text="Home Domain" /></td>
			<td class="value" style="padding-left:20px;width:400px">${element.user.homeDomain.name}</td>
		</tr>
		<tr>
			<td class="label"><spring:message code="service.user.name" text="User" /></td>
			<td class="value" style="padding-left:20px;width:400px">${element.user.name} ${element.user.email}</td>
		</tr>
		<tr>
			<td class="label"><spring:message code="service.jastification" text="Justification" /></td>
			<td class="value" style="padding-left:20px;width:400px">${element.justification}</td>
		</tr>
		<tr>
			<td class="label"><spring:message code="service.priority" text="Priority" /></td>
			<td class="value" style="padding-left:20px;width:400px">${element.priority}</td>
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
			<th><spring:message code="reservation.mtu" text="Mtu"/></th>
			<th><spring:message code="reservation.resiliency" text="Resiliency"/></th>
		</tr>  
		<c:forEach items="${element.reservations}" var="item" varStatus="loopStatus">
				<tr>
					<td>${reservationStates[item.state]}(${item.state})</td>
					<td>${item.startTime.time}</td>
					<td>${item.endTime.time}</td>
					<td>${item.priority}</td>
					<td>${item.startPort.description} (${item.startPort.bodID})</td>
					<td>${item.endPort.description} (${item.startPort.bodID})</td>
					<td>${item.capacity/1000000}</td>
					<td>${item.userVlanId}</td>
					<td>${item.mtu}</td>
					<td>${item.resiliency}</td>
				</tr>
		</c:forEach>
	</table>
<div style="position:relative;float:left;padding-top:20px"><span style="text-decoration:none;padding:0px;color:#000000;background:#ffffff;border:none;"><input id="cancel" name="Cancel" value="Cancel" type="submit" style="width:100px;" onclick="jQuery.post(location.href + '&_eventId=cancel&id=${element.bodID}&currentIdm2=' + $('#currentIdm').val() )" /></span>

	<a style="text-decoration:none;padding:0px;color:#000000;background:#ffffff;border:none;"  href="<c:url value="/portal/secure/services-map.htm"/>?service=${element.bodID}&domain=${element.user.homeDomain.bodID}">
<input id="view" name="view" value="View map" type="submit" style="width:100px" onclick="window.top.location='<c:url value="/portal/secure/services-map.htm"/>?service=${element.bodID}&domain=${element.user.homeDomain.bodID}'" /></a>
				
	</div>
	<br><br><br>
 	</div>			
   			</c:forEach>
   	</div>
   					
   		<c:set var="k" value="${x.count}"></c:set>
   		<c:set var="end" value="${x.count + 2}"></c:set>
   				
   </c:if>
   				
   						
   				
   	</c:otherwise>
   			
   </c:choose>
   </c:otherwise>
   			
  </c:choose>
  		
  		
  </c:forEach>
   	</div>
  </div>	

<script>

jQuery(document).ready(function() {	 
	 $(function() {		
		
			$(".scrollable").scrollable({ 
				vertical: true,
				mousewheel: false,
				
				onSeek: function()  {  

				      //index = this.getIndex();
				      //alert("koniec =" + index);
				     // alert("input =" + document.getElementById("index").value);
				    } 	
			
			}).navigator();
			
			var api = $(".scrollable").data("scrollable");
			//api.seekTo(document.getElementById("index").getAttribute("value"));
			
		}); 	 
});
</script>


