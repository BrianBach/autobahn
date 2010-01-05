<%@ tag body-content="scriptless"%>
<%@ attribute name="name" required ="true" %>
<%@ attribute name="path" required ="true" %>
<%@ attribute name="type" required ="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<c:if test="${empty type}">
	<c:set var="type" value="text" scope="page"/>
</c:if>

<spring:bind path="${path}">
<tr>
	<td class="label"><spring:message code="${path}" text="${name}" /></td>
	<td class="value">
		<input type="${type}" name="${status.expression}" value="${status.value}"/> 
	</td>
	<td class="error">${status.errorMessage}</td>
</tr>
</spring:bind>