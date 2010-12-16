<%@ include file="../common/includes.jsp"%>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.min.js"/>"></script>
<script src="http://cdn.jquerytools.org/1.2.3/full/jquery.tools.min.js"></script>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.form.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/jquery/tabs.css"/>"/>



<h2><spring:message code="services.htitle" text="Submitted Reservation Services" /></h2>

 <!-- generally javascript should go in a separate file -->
<script type="text/javascript">
var dataG;
var options = 
{
    //target:     "#servicesform"	,
    url:        "servicesforidm.htm"	,
    success:    function(data) 
    {	 
	  if(dataG!=data)
	  {
	       $("#servicesform").html(data);
	       dataG=data;
	  }
   }
};



function updateStates()
{
	$("#servicessearchform").submit();
}


jQuery(document).ready(function() 
{
     // bind 'myForm' and provide a simple callback function
           
     $("#servicessearchform").ajaxForm(options);

     $("#currentIdm").change(function()
     {
	  $("#servicessearchform").submit();
	  //$("#servicessearchform").ajaxForm(options);
	  //alert(1);
     });

     $("#servicessearchform").submit();
     setInterval( "updateStates()", 5000 );       
});



	</script>

<div align="center" class="logs_image">
<form:form commandName="services" action="" id="servicessearchform">
<table>
	<tr><td><h3>Please select an IDM to view and modify its properties</h3></td></tr>
	<tr>
	<td>
		<center><form:select path="currentIdm">
			<form:options items="${services.idms}"/>
		</form:select></center>
	</td>
	<td style="display:none">
		<input type="submit"  value="Change IDM" style="width:100px"/>
	</td>
	</tr>
</table>
</form:form>

<hr/>
<%--   <div id="servicesform" style="text-align:center" class="e" >  --%>
 <div id="servicesform" style="text-align:center" class="emptydiv">
 </div>
 </div>