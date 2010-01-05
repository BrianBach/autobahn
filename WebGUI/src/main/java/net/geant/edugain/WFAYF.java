/*
 * Copyright 2005-2008 RedIRIS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.geant.edugain;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class for Servlet: WFAYF
 *
 */
 public class WFAYF extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public WFAYF() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        OutputStream output = response.getOutputStream();
        String returnURL = URLDecoder.decode(request.getParameter("return"), "UTF-8");
        String preamble =       "<html>" +
            "<head><title>RedIRIS WFAYF service</title></head>" +
            //"<body><form method=\"get\" action=\""+ "http://serrano.rediris.es:8080/PAPIWebSSOLocalRequester/request" + "\">" +
            "<body><form method=\"get\" action=\""+ returnURL + "\">" +
            "<h3>Internal WFAYF Service</h3>" +
            "<p>Please select your Home Site from the following list and press the <b>Go!</b> button</p>" +
            //"<input type=hidden name=key value=" + request.getParameter("key") + ">" +
        "<select name=returnIDParam>";
        output.write(preamble.getBytes());
        
        String be = "<option value=\"" + "https://edugain-login.switch.ch/ShiBE-H/WebSSORequestListener" + "\">" + "SWITCH (stable)" + "</option>";
        output.write(be.getBytes());
        be = "<option value=\"" + "https://tengger.switch.ch/ShiBE-H/WebSSORequestListener" + "\">" + "SWITCH (testing)" + "</option>";
        output.write(be.getBytes());
        be = "<option value=\"" + "https://maclh.switch.ch/ShiBE-H/WebSSORequestListener" + "\">" + "SWITCH (development)" + "</option>";
        output.write(be.getBytes());
        be = "<option value=\"" + "http://serrano.rediris.es:8080/PAPIWebSSORequestListener/request" + "\">" + "RedIRIS (stable)" + "</option>";
        output.write(be.getBytes());
        be = "<option value=\"" + "http://guagua.rediris.es:8080/PAPI-BE/PAPIWebSSORequestListener/request" + "\">" + "RedIRIS Test (guagua)" + "</option>";
        output.write(be.getBytes());
        be = "<option value=\"" + "http://badulaque.rediris.es:8080/PAPIWebSSORequestListener/request" + "\">" + "RedIRIS Test (badulaque)" + "</option>";
        output.write(be.getBytes());
        be = "<option value=\"" + "http://dev3.andreas.feide.no/simplesaml/shib13/idp/SSOService.php" + "\">" + "FEIDE (Andreas macbook experimenta)" + "</option>";
        output.write(be.getBytes());
        be = "<option value=\"" + "https://thor.aai.dfn.de/ShiBE-H/WebSSORequestListener" + "\">" + "DFN" + "</option>";
        output.write(be.getBytes());
        be = "<option value=\"" + "https://srv2.ams.nl.geant2.net/ShiBE-H/WebSSORequestListener" + "\">" + "GEANT IdP" + "</option>";
        output.write(be.getBytes());
        be = "<option value=\"" + "http://gidp.geant2.net/simplesaml/shib13/idp/SSOService.php" + "\">" + "GIdP_prod" + "</option>";
        output.write(be.getBytes());
        be = "<option value=\"" + "https://hbe.edugain.bridge.feide.no/simplesaml/shib13/idp/SSOService.php" + "\">" + "Feide" + "</option>";
        output.write(be.getBytes());
        be = "<option value=\"" + "https://edugain.showcase.surfnet.nl/bridge/shib13/idp/SSOService.php" + "\">" + "SURFfederatie (Netherlands)" + "</option>";
        output.write(be.getBytes());
        be = "<option value=\"" + "https://be.aai.niif.hu/ShiBE-H/WebSSORequestListener" + "\">" + "Hungarnet (testing)" + "</option>";
        output.write(be.getBytes());
        be = "<option value=\"" + "https://clueless.restena.lu/simplesamlphp/shib13/idp/SSOService.php" + "\">" + "Fresco-AAI" + "</option>";
        output.write(be.getBytes());
//        be = "<option value=\"" + "https://clueless.restena.lu/simplesamlphp/saml2/idp/SSOService.php" + "\">" + "Fresco-AAI" + "</option>";
//        output.write(be.getBytes());
        be = "<option value=\"" + "https://login.aaiedu.hr/simplesamlphp/shib13/idp/SSOService.php" + "\">" + "AAI@EduHr" + "</option>";
        output.write(be.getBytes());
        be = "<option value=\"" + "https://stc-test8.cis.brown.edu/idp/profile/Shibboleth/SSO" + "\">" + "Shib2@brown.edu (Testing)" + "</option>";
        output.write(be.getBytes());

        String end = "</select>&nbsp;&nbsp;&nbsp;<input type=submit value=Go!>" +
            "</form></body>" +
            "</html>";
        output.write(end.getBytes());
        
        output.flush();
        output.close();
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}   	  	    
}