<%@ include file="../common/includes.jsp"%>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.form.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/jquery/tabs.css"/>"/>



<h2><spring:message code="settings.htitle" text="Domains Settings" /></h2>

 <!-- generally javascript should go in a separate file -->
    <script type="text/javascript">

var options = {
    target:     "#settingsform",
    url:        "settings.htm",
    success:    function(data) {
        //alert(1);
    }
};

	   jQuery(document).ready(function() {
            // bind 'myForm' and provide a simple callback function

           // alert(1);
            $("#settingssearchform").ajaxForm(options);

             $("#currentIdm").change(function(){
              $("#settingssearchform").submit();
             });
            //alert(11);
            $("#settingssearchform").submit();
        });



	</script>

<div align="center" class="logs_image">
<form:form commandName="settings" action="" id="settingssearchform">
<table>
	<tr><td><h3>Please select an IDM to view and modify its properties</h3></td></tr>
	<tr>
	<td>
		<center><form:select path="currentIdm">
			<form:options items="${settings.idms}"/>
		</form:select></center>
	</td>
	<td style="display:none">
		<input type="submit"  value="Change IDM" style="width:100px"/>
	</td>
	</tr>
</table>
</form:form>

<hr/>
 <div id="settingsform" style="text-align:center" class="emptydiv">
 </div>
 </div>