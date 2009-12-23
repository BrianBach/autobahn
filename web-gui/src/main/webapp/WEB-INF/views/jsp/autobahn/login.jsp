<%@ include file="../common/includes.jsp"%>
<%@ page import="org.springframework.security.ui.AbstractProcessingFilter" %>
<%@ page import="org.springframework.security.ui.webapp.AuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.AuthenticationException" %>
 
<h2>Private area login</h2>
<form action="<c:url value="/j_spring_security_check"/>" method="POST">
      <table>
        <tr><td class="label">User:</td><td class="value"><input type='text' name='j_username'/></td></tr>
        <tr><td class="label">Password:</td><td  class="value"><input type='password' name='j_password'></td></tr>
        <tr><td><input type="checkbox" name="_spring_se curity_remember_me"></td><td>Don't ask for my password for two weeks</td></tr>
        <tr><td colspan='2'><input name="Login" type="submit"/></td></tr>
      </table>
 </form>
