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
<!--div class="tooltip" id="tooltip" >
	

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
		 		<td class="label">${loopStatus.count}#</td>
				<td ><a href="<c:url value="/portal/secure/services-map.htm?service=${service.key}&domain=${service.value}"/>">${service.key}</a></td>
			</tr>
		 
		 </c:forEach>
		
		</table>
		</div>
	</div>


</div-->
<!--div id="popupContact">  
        <a id="popupContactClose">x</a>  
        <h1>Reservations: </h1>  
        <p id="contactArea">  
            <div align="center">
	<table style="margin-right:20px" >
	
		<c:if test="${services == null}">
		
			<tr>
				<td class="label" style="color: white;"># No reservation available # </td>
			</tr>
			
		</c:if>
		
		 <c:forEach items="${services}" var="service" varStatus="loopStatus">
		 	
		 	<tr >
		 		<td class="label">${loopStatus.count}#</td>
				<td ><a href="<c:url value="/portal/secure/services-map.htm?service=${service.key}&domain=${service.value}"/>">${service.key}</a></td>
			</tr>
		 
		 </c:forEach>
		
		</table>
		</div> 
        </p>  
    </div>  
    <div id="backgroundPopup"></div--> 
<!--style>
#backgroundPopup{  
display:none;  
position:fixed;  
_position:absolute; /* hack for internet explorer 6*/  
height:100%;  
width:100%;  
top:0;  
left:0;  
background:#000000;  
border:1px solid #cecece;  
z-index:1;  
}  
#popupContact{  
display:none;  
position:fixed;  
_position:absolute; /* hack for internet explorer 6*/  
height:384px;  
width:408px;  
background:#FFFFFF;  
border:2px solid #cecece;  
z-index:2;  
padding:12px;  
font-size:13px;
overflow: hidden;  
}  
#popupContact h1{  
text-align:left;  
color:#6FA5FD;  
font-size:22px;  
font-weight:700;  
border-bottom:1px dotted #D3D3D3;  
padding-bottom:2px;  
margin-bottom:20px;  
}  
#popupContactClose{  
font-size:14px;  
line-height:14px;  
right:6px;  
top:4px;  
position:absolute;  
color:#6fa5fd;  
font-weight:700;  
display:block;  
}  
</style-->
<a id="download_now">Download now</a>
<!--div id="popupContact">  
        <a id="popupContactClose">x</a>  
        <h1>Reservations: </h1>  
        <p id="contactArea">  
            <div align="center">
	<table style="margin:0" >
	
		<c:if test="${services == null}">
		
			<tr>
				<td class="label" style="color: white;"># No reservation available # </td>
			</tr>
			
		</c:if>
		
		 <c:forEach items="${services}" var="service" varStatus="loopStatus">
		 	
		 	<tr >
		 		<td class="label">${loopStatus.count}#</td>
				<td ><a href="<c:url value="/portal/secure/services-map.htm?service=${service.key}&domain=${service.value}"/>">${service.key}</a></td>
			</tr>
		 
		 </c:forEach>
		
		</table>
		</div>
        </p>  
    </div>  
    <div id="backgroundPopup"></div--> 
    
<!-- tooltip element -->
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

	//map will be refreshed every 120 seconds
	setTimeout( "refresh()", 120*1000 );

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
	
	/*if((screen.width>=1281) && (screen.height>=801) && (screen.width < 1680)){
	jQuery(document).ready(function() {
		jQuery(function() {		
			jQuery("#download_now").tooltip({ 		
				 position: "center right",
				 offset: [-20, 640],
				
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
}

if((screen.width>=1680) && (screen.height>=1050) && screen.width < 1920 ){
	jQuery(document).ready(function() {
		jQuery(function() {		
			jQuery("#download_now").tooltip({ 		
				 position: "center right",
				 offset: [-20, 320],
				
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
}
if((screen.width>=1920) && (screen.height>=1080) && screen.width < 2048 ){

	jQuery(document).ready(function() {
		jQuery(function() {		
			jQuery("#download_now").tooltip({ 		
				 position: "center right",
				 offset: [-20, 440],
				
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
}
else {
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
}*/

var sURL = location.href;

function refresh()
{
    window.location.href = sURL;
}

</script>


