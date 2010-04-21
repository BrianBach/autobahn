<%@ include file="../common/includes.jsp"%>
<h2><spring:message code="settings.htitle" text="Domains Settings" /></h2>
<br/>

<form:form commandName="settings">
<table>
	<tr>
	<td>
		<form:select path="currentIdm">
			<form:options items="${settings.idms}"/>
		</form:select>
	</td>
	<td>
		<input type="submit" name="_eventId_change" value="Change IDM" style="width:100px"/>
	</td>
	</tr>
</table>
</form:form>
<form:form commandName="settings">
<c:if test="${settings.prop != null}" >
<table>
<thead>
	<tr><th style="background-color: blue; color:white;" colspan="2">Inter Domain Manager settings</th></tr>
</thead>
<tbody>
<c:forEach items="${settings.prop}" var="property">
<tr>
	<td class="label">${property.key}</td>
	<td width="300px" class="value"><form:input path="prop[${property.key}]"/></td>	
</tr>
</c:forEach>
</tbody>
<tfoot>
	<tr style="background-color: blue; color:white;"><td>&nbsp;</td><td><input width="300px" type="submit" name="_eventId_restart" value="Set Properties" style="width:100px"/></td></tr>
</tfoot>
</table>
</c:if>
<c:if test="${settings.prop == null}" >
	Cannot retrieve settings. Cannot connect to IDM.
</c:if>	

</form:form>