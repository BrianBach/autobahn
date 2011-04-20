<%@ include file="../common/includes.jsp"%>

<!--<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.0/jquery.min.js"></script> -->
<script src="http://cdn.jquerytools.org/1.2.3/full/jquery.tools.min.js"></script>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.validate.min.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/jquery/tooltip.css"/>"/>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.tools.min.js"/>"></script>

<script>
     jQuery.noConflict();    		 
</script>

<h2><spring:message code="reservationMap.htitle" text="AutoBAHN Reservations and Reachability Map"/></h2>
<br/>
<form:form commandName="services">

<div class="images">

	<div id="map" class="map" ></div>
		<div align="right" >
		
			<a href="<c:url value="/portal/secure/services-map.htm?service=&domain="/>">Clear map</a>
		
		</div>
	
	</div>

<a id="download_now">Download now</a>

<div class="tooltip" id="tooltip" >
	

	<img src="<c:url value="/js/jquery/img/close2.PNG"/>"/ id="close_tooltip">
	
	<div class="label_reserv"> Reservations: </div>

	<div class="panel_scroll" id="panel_scroll" >
	
	<div align="center">
	<table style="margin:0" >
	
		<c:if test="${services == null}">
		
			<tr>
				<td class="label" style="color: white;"># No reservation available # </td>
			</tr>
			
		</c:if>
		
		 <c:forEach items="${services}" var="service" varStatus="loopStatus">
		 	
		 	<tr >
		 		<td class="label" style="width: 10px;">${loopStatus.count}#</td>
				<td ><a href="<c:url value="/portal/secure/services-map.htm?service=${service.key}&domain=${service.value}"/>">${service.key}</a></td>
			</tr>
		 
		 </c:forEach>
		
		</table>
		</div>
	</div>

</div>

</form:form>

<script type="text/javascript" src="jquery.js"></script>
	<script type="text/javascript">
	/*jQuery(document).ready(function(){  
//following code will be here  

	//SETTING UP OUR POPUP  
//0 means disabled; 1 means enabled;  
var popupStatus = 0;  
//loading popup with jQuery magic!  
function loadPopup(){  
//loads popup only if it is disabled  
if(popupStatus==0){  
jQuery("#backgroundPopup").css({  
"opacity": "0.7"  
});  
jQuery("#backgroundPopup").fadeIn("slow");  
jQuery("#popupContact").fadeIn("slow");  
popupStatus = 1;  
}  
}  

//disabling popup with jQuery magic!  
function disablePopup(){  
//disables popup only if it is enabled  
if(popupStatus==1){  
jQuery("#backgroundPopup").fadeOut("slow");  
jQuery("#popupContact").fadeOut("slow");  
popupStatus = 0;  
}  
}  

//centering popup  
function centerPopup(){  
//request data for centering  
var windowWidth = document.documentElement.clientWidth;  
var windowHeight = document.documentElement.clientHeight;  
var popupHeight = jQuery("#popupContact").height();  
var popupWidth = jQuery("#popupContact").width();  
//centering  
jQuery("#popupContact").css({  
"position": "absolute",  
"top": windowHeight/2-popupHeight/2,  
"left": windowWidth/2-popupWidth/2  
});  
//only need force for IE6  
  
jQuery("#backgroundPopup").css({  
"height": windowHeight  
});  
  
}  


//LOADING POPUP  
//Click the button event!  
jQuery("#download_now").click(function(){  
//centering with css  
centerPopup();  
//load popup  
loadPopup();  
});  

//CLOSING POPUP  
//Click the x event!  
jQuery("#popupContactClose").click(function(){  
disablePopup();  
});  
//Click out event!  
jQuery("#backgroundPopup").click(function(){  
disablePopup();  
});  
//Press Escape event!  
jQuery(document).keypress(function(e){  
if(e.keyCode==27 &amp;amp;amp;amp;amp;amp;&amp;amp;amp;amp;amp;amp; popupStatus==1){  
disablePopup();  
}  
});  

});*/

	// initialize tooltip
	jQuery(document).ready(function() {
		jQuery(function() {		
			jQuery("#download_now").tooltip({ 		
				 //offset: [100, 50]
				 position: "center left",
				relative: true,
				effect: 'slide',
				events: {
			            def: "click, ''",
			            tooltip: "'','mouseout'"},
			        onShow: function(){
			           var tip = this.getTip();
			           tip.show();
			        }
			    })
			    .dynamic({ left: { direction: 'left', bounce: true } });
			
			
			
			jQuery('#close_tooltip').click(function() { 
			    jQuery(this).parent().hide();        
			});
			
			jQuery('#download_now').click(function() { 
			    jQuery(this).next().show();
			});

						 
		}); 	 
	});			
	
</script>


