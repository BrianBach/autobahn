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
		width:435px;
	/*	border:10px solid #666; */

		/* for modern browsers use semi-transparent color on the border. nice! */
	/*	border:10px solid rgba(82, 82, 82, 0.698);  */

		/* hot CSS3 features for mozilla and webkit-based browsers (rounded borders) */
	/*	-moz-border-radius:8px;
		-webkit-border-radius:8px;
	*/	
		-moz-border-radius:5px;
		-webkit-border-radius:5px;
		-moz-box-shadow:0 0 25px #666;
		-webkit-box-shadow:0 0 25px #666;	
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
		background-color:#25648d;
		padding:5px 10px;
		border:1px solid #3B5998;
		font-size:20px;
	}
	.btnsize
{
   width:100px;
}

/* form style */
#myform {
	background:#333 url(/img/gradient/h300.png) repeat-x;	
	padding:15px 20px; 
	color:#eee;
	width:400px;
	margin:0 auto;
	position:relative;
	-moz-border-radius:5px;
	-webkit-border-radius:5px;	
} 

/* nested fieldset */
#myform fieldset {
	border:0;
	margin:0;
	padding:0;
	background:#333333 url(/tools/img/logo-medium.png) no-repeat scroll 215px 40px;	
}

/* typography */
#myform h3 	{ color:#eee; margin-top:0px; }
#myform p 	{ font-size:11px; }


/* input field */
#myform input {
	border:1px solid #444;
	background-color:#666;
	padding:5px;
	color:#ddd;
	font-size:12px;
	
	/* CSS3 spicing */
	text-shadow:1px 1px 1px #000;
	-moz-border-radius:4px;
	-webkit-border-radius:4px;	
}

#myform input:focus 		{ color:#fff; background-color:#777; }
#myform input:active 	{ background-color:#888; }


/* button */
#myform button {
	outline:0;
	border:1px solid #666;	
}


/* error message */
.error {
	height:15px;
//	background-color:#FFFE36;
	font-size:11px;
//	border:1px solid #E1E16D;
	padding:4px 10px;
	color:#000;
/*	display:none;	
	
	-moz-border-radius:4px;
	-webkit-border-radius:4px; 
	-moz-border-radius-bottomleft:0;
	-moz-border-radius-topleft:0;	
	-webkit-border-bottom-left-radius:0; 
	-webkit-border-top-left-radius:0;
	
	-moz-box-shadow:0 0 6px #ddd;
	-webkit-box-shadow:0 0 6px #ddd;	*/
}

.error p {
	margin:0;		
}

/* field label */
label {
	display:block;
	font-size:11px;
	color:#ccc;
}

#terms label {
	float:left;
}

#terms input {
	margin:0 5px;
}


	
	</style>

<!-- facebook dialog -->
<div id="facebox">

	<div id="values">


		<h2>Private area login</h2>

		<form  id="logform" action="/autobahn-gui/j_spring_security_check">
		
      <table>
      <tr>&nbsp;</tr>
      <c:if test="${!empty login_error}">
      <tr><td class="error" colspan="2">Login attempt was unsuccessful</td></tr>
      </c:if>
        <tr><td class="label" style="text-align:left">Username:</td><td class="value_box"><input type='text' name='j_username' maxlength="30" /></td></tr>
        <tr><td class="label" style="text-align:left">Password:</td><td  class="value_box"><input type='password' name='j_password'></td></tr>
        <tr><td  colspan="2" style="text-align:right">Remember me:<input type="checkbox" style="max-width: 20px" name="_spring_security_remember_me" /></td></tr>
        
        <tr>
        	<td></td>
        	<td style="text-align:left"><input id="logbutton" name="Login" value="Log in" type="submit" class="btnsize"/><input id="cancel" name="Cancel" value="Cancel" type="submit" class="btnsize" onclick="window.location='../portal/home.htm'" /></td>
        </tr>
        <!--tr><td class="error" colspan="2"><b>Please note that the pilot is currently under debugging / testing so functionalities may not be working</b></td></tr-->
      </table>
     
 </form>

		<!-- yes/no buttons >
		<p style="text-align:right">
			<button class="close"> Cancel </button>
		</p-->
	</div>

</div>


<script>


// What is $(document).ready ? See: http://flowplayer.org/tools/documentation/basics.html#document_ready
jQuery(document).ready(form_init);
</script>
