<%@ include file="../common/includes.jsp"%>
<%@ page import="org.springframework.security.ui.AbstractProcessingFilter" %>
<%@ page import="org.springframework.security.ui.webapp.AuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.AuthenticationException" %>


   <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
   <script src="<c:url value="/js/jquery/jquery.tools.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/jquery/jquery.form.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/jquery/data.js"/>"></script>


	<style>


	#facebox {

		/* overlay is hidden before loading */
		display:none;

		/* standard decorations */
		width:400px;
		border:10px solid #666;

		/* for modern browsers use semi-transparent color on the border. nice! */
		border:10px solid rgba(82, 82, 82, 0.698);

		/* hot CSS3 features for mozilla and webkit-based browsers (rounded borders) */
		-moz-border-radius:8px;
		-webkit-border-radius:8px;
	}

	#facebox div {
		padding:10px;
		border:1px solid #3B5998;
		background-color:#fff;
		font-family:"lucida grande",tahoma,verdana,arial,sans-serif
	}

	#facebox h2 {
		margin:-11px;
		margin-bottom:0px;
		color:#fff;
		background-color:#6D84B4;
		padding:5px 10px;
		border:1px solid #3B5998;
		font-size:20px;
	}
	</style>








<!-- facebook dialog -->
<div id="facebox">

	<div id="values">


		<h2>Private area login</h2>

		<form  id="logform">
      <table>
      <tr>&nbsp;</tr>
        <tr><td class="label" style="text-align:left">User:</td><td class="value"><input type='text' name='j_username'/></td></tr>
        <tr><td class="label" style="text-align:left">Password:</td><td  class="value"><input type='password' name='j_password'></td></tr>
        <tr><td  colspan="2" style="text-align:center">Don't ask for my password for two weeks<input type="checkbox" name="_spring_security_remember_me" /></td></tr>
        <tr><td colspan='2' style="text-align:center"><input id="logbutton" name="Login" value="Log in" type="submit"/></td></tr>
      </table>
 </form>

		<!-- yes/no buttons -->
		<p style="text-align:center">
			<button class="close"> Close </button>
		</p>
	</div>

</div>


<script>


// What is $(document).ready ? See: http://flowplayer.org/tools/documentation/basics.html#document_ready
jQuery(document).ready(form_init);
</script>



