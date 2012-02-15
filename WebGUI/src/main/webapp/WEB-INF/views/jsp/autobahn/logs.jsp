<%@ include file="../common/includes.jsp" %>
<h2><spring:message code="logs.htitle" text="Domains Logs"/></h2>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.json-1.3.min.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/jquery/tabs.css"/>"/>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.dateFormat-1.0.js"/>"></script>
<script type="text/javascript">


$.fn.serializeObject = function()
{
        var o = {};
        var a = $(":input").serializeArray();

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
 
function updateLogs()
{
     $("#logssearchform").submit();
}

var counter=true;
jQuery(document).ready(function() 
{
        // bind 'myForm' and provide a simple callback function
     $("#ajaxerror").ajaxError(function() 
     {
	  $(this).text('Cached data are being provided..please try again or wait for real time update.');
     });

     $("#logssearchform").submit(function() 
     {            
	  var aa = $.toJSON($("values").serializeObject());
          jQuery.getJSON("logs_request.htm", aa,
                    function(data) 
		    {                    
			 $("#ajaxsuccess").html("Showing logs for <font color='blue'>"+$("#currentIdm").val()+"</font><br/>"+(new Date()).format("r")+"<br>The contents will update every 5 seconds");
			 if($("#logsview").text()!=data.result)
			 {
			      $("#logsview").text(data.result);
			      $("#logsview").animate({ scrollTop: $("#logsview").attr("scrollHeight") - $('#logsview').height() }, 1);
			 } 
                    }, "json");
	  if(counter)
	  {
	       $("#logsview").animate({ scrollTop: $("#logsview").attr("scrollHeight") - $('#logsview').height() }, 1);
	       counter=false;
	  }
          return false;
     });     
   
     $("#currentIdm").change(function() 
     {
           $("#logssearchform").submit();
     });
     
     $("#logssearchform").submit();
     setInterval( "updateLogs()", 5000 );
});  


</script>
<div class="logs_image">
<div align="center" id="values" class="panel_scroll">

    <form:form commandName="logs"  action="" id="logssearchform">
    <input type="hidden" name="action" value="change"/>
    <table>
        <tr>
            <td><h3>Please select an IDM to view the log file</h3></td>
        </tr>
        <tr>
            <td>
                <form:select path="currentIdm" id="currentIdm">
                    <form:options items="${logs.idms}"/>
                </form:select>
            </td>
            <td style="display:none">
                    <%--  <input type="submit" name="_eventId_change" value="Change IDM" style="width:100px"/>--%>
            </td>
        </tr>
        <tr></tr>
    </table>
    <c:if test="${logs.error!= null}">
        <h3>${logs.error}</h3>
    </c:if>
    <hr/>
    <div id="ajaxerror" style="color:red">
    </div>
    <div id="ajaxsuccess" style="color:green">
    </div>
    <div id="settingsform" style="text-align:center">
        <form:textarea path="logs" cssStyle="width:100%" rows="20" disabled="true" id="logsview"/>
    </div>
   </form:form>
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
