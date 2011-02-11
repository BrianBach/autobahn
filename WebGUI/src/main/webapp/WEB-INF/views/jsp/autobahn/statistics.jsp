<%@ include file="../common/includes.jsp"%>

<br/>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/jquery/jquery.autocomplete.css"/>" />

<script type="text/javascript" src="<c:url value="/js/jquery/jquery.json-1.3.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.bgiframe.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.quicksearch.js"/>"></script>
<script type="text/javascript">
$("#ajaxerror").ajaxError(function() {
  $(this).text('An error occured..please try again.');
});

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

$('input#id_search').quicksearch('table#statisticsview tbody tr');

</script>

<div align="center">
	<div id="ajaxerror" style="color:red">
	</div>
	
	<div id="ajaxsuccess" style="color:green">
	</div>
	
	<form:form id="statisticssaveform" action="" >
	
		<c:if test="${statistics != null}" >
            <p align="left">Average time needed to set-up the segment of a reservation 
            in this domain (intra): ${statistics.averageIntra/1000} sec</p>
            
            <p align="left">Average time needed to completely set-up reservations 
            (from request submission to activation) originating from this domain - 
            only applicable for "Process Now" requests (inter): 
            ${statistics.averageInter/1000} sec</p>

            <input type="text" name="search" value id="id_search" placeholder="Search">
            <br/><br/>
    
            <div id="collection">
				<table width="600px" id="statisticsview">
					<thead>
	                    <tr>
	                        <th class="label">Reservation</th>
	                        <th class="label">Setup time (sec)</th>
	                        <th class="label">Inter/Intra</th>
	                    </tr>
					</thead>
					
					<tbody>
						<c:forEach items="${statistics.intra}" var="item">
							<tr>
							    <td class="value">${item.reservation_id}</td>
							    <td class="value" align="right">${item.setup_time/1000}</td>
							    <td class="value">Intra</td>
							</tr>
						</c:forEach>
						
	                    <c:forEach items="${statistics.inter}" var="item">
	                        <tr>
	                            <td class="value">${item.reservation_id}</td>
	                            <td class="value" align="right">${item.setup_time/1000}</td>
	                            <td class="value">Inter</td>
	                        </tr>
	                    </c:forEach>
					</tbody>
				</table>
            </div>
		</c:if>
		
		<c:if test="${statistics == null}" >
		    Cannot retrieve statistics. Cannot connect to IDM.
		</c:if>
	
	</form:form>
</div>
