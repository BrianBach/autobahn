<%@ tag body-content="scriptless"%>
<%@ attribute name="name" required ="true" %>
<%@ attribute name="path" required ="true" %>
<%@ attribute name="list" required ="true" type="java.lang.reflect.Array" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>


<spring:bind path="${path}">
<tr>
	<td class="label"><spring:message code="${path}" text="${name}"/></td>
	<td class="value">
		<select size="1" name="${status.expression}">
			<c:forEach var="idm" items="${list}" begin="0">
				<option value="${idm}" <c:if test="${idm==status.value}">selected</c:if>>${idm}</option>
			</c:forEach>
		</select>
	</td>
</tr>
</spring:bind>