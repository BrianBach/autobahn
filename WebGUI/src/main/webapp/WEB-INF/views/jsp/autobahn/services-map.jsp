<%@ include file="../common/includes.jsp"%>
<script src="http://cdn.jquerytools.org/1.2.3/full/jquery.tools.min.js"></script>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.validate.min.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/jquery/tooltip.css"/>"/>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.tools.min.js"/>"></script>
<script>
     jQuery.noConflict();
     
     // Use jQuery via jQuery(...)
     //jQuery(document).ready(function(){
     //  jQuery("div").hide();
     //});
     
     // Use Prototype with $(...), etc.
     //$('someid').hide();
   </script>

<h2><spring:message code="reservationMap.htitle" text="AutoBAHN Reservations and Reachability Map"/></h2>
<br/>
<form:form commandName="services">


<div class="images">

	<div class="map" id="map"></div>
		<div align="right" >
		
			<a href="<c:url value="/portal/secure/services-map.htm?service=&domain="/>">Clear map</a>
		
		</div>
	
	</div>
	
<a id="download_now">Download now</a>

<!-- tooltip element -->
<div class="tooltip" id="tooltip" >
	

	<img src="<c:url value="/js/jquery/img/close2.PNG"/>"/ id="close_tooltip">

	<div class="panel_scroll" id="panel_scroll" >
	
	<table style="margin:0" >
	
		<c:if test="${services == null}">
		
			<tr>
				<td class="label"> No reservation available </td>
			</tr>
			
		</c:if>
		 <c:forEach items="${services}" var="service" varStatus="loopStatus">
		 
		 	<tr>
				<td class="label">${loopStatus.count} Reservation:</td>
				<td><a href="<c:url value="/portal/secure/services-map.htm?service=${service.bodID}&domain=${service.user.homeDomain.bodID}"/>">${service.bodID}</a></td>
			</tr>
		 
		 </c:forEach>
	
	</table>
	</div>

</div>

</form:form>
	<script type="text/javascript">		

			 jQuery(document).ready(function() {

				jQuery(function() {		
						
					 jQuery("#download_now").tooltip({ 
							
							 position: "center right",
							 offset: [-20, 140],
							
							 events: {
					                def: "click, ''",
					                tooltip: "'','mouseout'"},
					            onShow: function(){
					               var tip = this.getTip();
					               tip.show();
					            }
					        })
					        .dynamic({
					            top: { direction: 'up' } 
					    });

						
					 	jQuery('#close_tooltip').click(function() { 
					        jQuery(this).parent().hide();        
					    });
					    
					    jQuery('#download_now').click(function() { 
					        jQuery(this).next().show();
					    });

						 
					}); 	 
			 });

</script>


