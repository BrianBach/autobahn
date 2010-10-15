<%@ include file="../common/includes.jsp"%>
<script src="http://cdn.jquerytools.org/1.2.3/full/jquery.tools.min.js"></script>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.validate.min.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/jquery/tooltip.css"/>"/>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.tools.min.js"/>"></script>


<form:form commandName="services">


<div class="images">

	<div class="map" id="map"></div>
		<div align="right" >
		
			<a href="<c:url value="/portal/secure/services-map.htm?service=&domain="/>">Clear map</a>
		
		</div>
	
	</div>

<!--<a style="background-color: blue; color: white" href="javascript:makeGetRequest()">Refresh</a>-->
	
<a id="download_now">Download now</a>

<!-- tooltip element -->
<div class="tooltip" id="tooltip" >

	<div class="panel_scroll" id="panel_scroll" >
	
	<table style="margin:0" >
		
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
				 
				 $(function() {		
						
						$("#download_now").tooltip({ 
							
							 position: "center left",
							 offset: [-18, -313]

						});
					}); 	 
			 });

</script>


