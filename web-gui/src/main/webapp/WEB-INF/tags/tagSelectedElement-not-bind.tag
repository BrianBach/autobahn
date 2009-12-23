<%@ tag body-content="scriptless"%>
<%@ attribute name="label" required ="true" %>
<%@ attribute name="name" required ="true" %>
<%@ attribute name="value" required ="true" %>
<%@ attribute name="list" required ="true" type="java.util.List" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<tr>
	<td class="label">${label}</td>
	<td class="value">
		<select size="1" name="${name}">
			<c:forEach var="element" items="${list}" begin="0">
				<option value="${element}" <c:if test="${element==value}">selected</c:if>>${element}</option>
			</c:forEach>
		</select>
	</td>
	<td class="error"></td>
</tr>