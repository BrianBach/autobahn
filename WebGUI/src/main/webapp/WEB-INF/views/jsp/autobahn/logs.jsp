<%@ include file="../common/includes.jsp"%>
<h2><spring:message code="logs.htitle" text="Domains Logs" /></h2>
<form:form commandName="logs">
<input type="hidden" name="action" value="change"/>
<table>
	<tr>
		<td>
			<form:select path="currentIdm">
				<form:options items="${logs.idms}"/>
			</form:select>
		</td>
		<td>
			<input type="submit" name="_eventId_change" value="Change IDM" style="width:100px"/>
		</td>
	</tr>
</table>
	<c:if test="${logs.error!= nul}">
		<h3>${logs.error}</h3>
	</c:if>
	<form:textarea  path="logs" cssStyle="width:100%" rows="20" disabled="true"/>
</form:form>