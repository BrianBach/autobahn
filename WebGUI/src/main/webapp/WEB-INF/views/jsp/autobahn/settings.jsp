<%@ include file="../common/includes.jsp"%>

<br/>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/jquery/jquery.autocomplete.css"/>" />

<script type="text/javascript" src="<c:url value="/js/jquery/jquery.json-1.3.min.js"/>"></script>
 <script type="text/javascript" src="<c:url value="/js/jquery/jquery.autocomplete.min.js"/>"></script>
 <script type="text/javascript" src="<c:url value="/js/jquery/jquery.bgiframe.min.js"/>"></script>
 <script type="text/javascript" src="<c:url value="/js/jquery/autocomplete-data.js"/>"></script>
<script type="text/javascript">
  $(document).ready(function(){
    
$("#gui\\.address").autocomplete(gui,{
		matchContains: true,
		minChars: 0
	});
$("#ospf\\.areaId").autocomplete(areaid, {
		matchContains: true,
		minChars: 0
	});

});

$("#ajaxerror").ajaxError(function() {
  $(this).text('An error occured..please try again.');
});

$.fn.serializeObject = function()
{
    var o = {};
    var a = $(":input").serializeArray();
   // alert(a);
    jQuery.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || "");
        } else {
            o[this.name] = this.value || "";
        }
    });
    return o;
};

$("#settingssaveform").submit(function() {
    var aa=$.toJSON($("values").serializeObject());
    //alert(aa);
    $.post("settings_save.htm", aa,
   function(data){
   //alert(2);
    $("#ajaxsuccess").text("Your action was completed");

   },"json");
    return false;
});


</script>
<div align="center">
<div id="ajaxerror" style="color:red">
</div>
<div id="ajaxsuccess" style="color:green">
</div>
<form:form id="settingssaveform" action="" >

<c:if test="${settings != null}" >
<table width="600px">
<thead>
	<tr width="600px"><th style="background-color: blue; color:white;" colspan="2">Inter Domain Manager settings</th></tr>
</thead>
<tbody>
<div id="values">
<c:forEach items="${settings}" var="property">
<tr>
	<td class="label" style="text-align:left">${property.key}</td>
	<td width="300px" class="value"><input name="${property.key}" value="${property.value}" id="${property.key}"/></td>
</tr>
</c:forEach>
</div>
</tbody>
<tfoot >
	<tr width="600px" style="background-color: blue; color:white;" ><td colspan="2" style="text-align:center"> <input  width="600px" type="submit" name="_eventId_restart" value="Set Properties" style="width:600px"/></td></tr>
</tfoot>
</table>
</c:if>
<c:if test="${settings== null}" >
	Cannot retrieve settings. Cannot connect to IDM.
</c:if>

</form:form>
</div>
