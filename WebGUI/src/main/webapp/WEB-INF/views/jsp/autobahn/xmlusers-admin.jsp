<%@ include file="../common/includes.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"prefix="fn" %>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/jquery/tabs.css"/>"/>
<h2><spring:message code="home.usersadmin" text="User Administration"/></h2>

<div style="text-align: left;" class="home_image">
<table width="100%">
                <tr>
                        <th><spring:message code="userview.identifier" text="Identifier"/></th>
                        <th><spring:message code="userview.organization" text="Organization"/></th>
                        <th><spring:message code="userview.projectMembership" text="Project Membership"/></th>
                        <th><spring:message code="userview.projectRole" text="Project Role"/></th>
                        <th></th>
                </tr>
                <c:forEach items="${users}" var="user" varStatus="loopStatus">
                                <tr>
                                        <td>${user.identifier}</td>
                                        <td>${user.organization}</td>
                                        <td>${user.projectMembership}</td>
                                        <td>${user.projectRole}</td>
                                        <td>
                                                <a href="${flowExecutionUrl}&_eventId=remove&username=${user.identifier}">Remove</a>
                                        </td>
                                </tr>
                </c:forEach>
                <form:form commandName="xmlUserDetailsAdmin" >
                <input type="hidden" name="_eventId" value="add" />
                               <tr>
                                        <td><input type="text" name="username" style="width:auto" size="10" /></td>
                                        <td><input type="text" name="organization" style="width:auto" size="10" /></td>
                                        <td><input type="text" name="membership" style="width:auto" size="10" /></td>
                                        <td><input type="text" name="role" style="width:auto" size="10" /></td>
                                        <td>Password:<input type="password" name="password" style="width:auto" size="10" /></td>
                                </tr>
                                <tr>    
                                        <td colspan="5" align="center" style="padding-top:20px"><input type="submit" value="Add User" style="width: auto" /></td>
                                </tr>
				</form:form>
</table>
</div>
