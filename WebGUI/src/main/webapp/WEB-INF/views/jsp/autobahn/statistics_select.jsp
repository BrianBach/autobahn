<%@ include file="../common/includes.jsp"%>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.form.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/jquery/tabs.css"/>"/>

<h2><spring:message code="statistics.htitle" text="Domains Statistics" /></h2>

 <!-- generally javascript should go in a separate file -->
    <script type="text/javascript">

var options = {
    target:     "#statisticsform",
    url:        "statistics.htm",
    success:    function(data) {
    }
};

	   jQuery(document).ready(function() {
            // bind 'myForm' and provide a simple callback function

            $("#statisticssearchform").ajaxForm(options);

             $("#currentIdm").change(function(){
              $("#statisticssearchform").submit();
             });
            $("#statisticssearchform").submit();
        });



	</script>

<div align="center" class="logs_image">
<form:form commandName="statistics" action="" id="statisticssearchform">
<table>
	<tr><td><h3>Please select an IDM to view statistical data</h3></td></tr>
	<tr>
	<td>
		<center><form:select path="currentIdm">
			<form:options items="${statistics.idms}"/>
		</form:select></center>
	</td>
	<td style="display:none">
		<input type="submit"  value="Change IDM" style="width:100px"/>
	</td>
	</tr>
</table>
</form:form>

<hr/>
 <div id="statisticsform" style="text-align:center" class="emptydiv">
 </div>
 </div>

<script type="text/javascript">
    var myselect=document.getElementById("currentIdm")
    for (var i=0; i<myselect.options.length; i++){
        if (myselect.options[i].value=="${authParameters.organization}") {
            myselect.options[i].selected=true
            break
            }
        }
</script>
