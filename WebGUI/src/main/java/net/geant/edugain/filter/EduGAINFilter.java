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

package net.geant.edugain.filter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.opensaml.SAMLException;
import org.opensaml.SAMLResponse;
import org.opensaml.XML;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import net.geant.edugain.AESTool;
import net.geant.edugain.base.AttributeValues;
import net.geant.edugain.base.AuthenticationRequest;
import net.geant.edugain.base.AuthenticationResponse;
import net.geant.edugain.base.BaseException;
import net.geant.edugain.base.Configurator;
import net.geant.edugain.base.websso.WebSSORequester;
import net.geant.edugain.validation.ValidationException;
import net.geant.edugain.validation.Validator;

public class EduGAINFilter implements Filter {

	private Configurator config;
	private Logger log;
	private String requestDBName;
	private HashMap<String,HashMap<String,String>> requests;
	private AESTool cipher;
	private String serviceID;
	private String location;
	private ServletContext context;
	
	
	public void init(FilterConfig config) throws ServletException {
		
		this.log = Logger.getLogger(this.getClass());
		this.context = config.getServletContext();
		this.location = "/" + this.context.getServletContextName() + "/";

		loadConfiguration(config);
		loadCiphers();
		loadDatabase();
	}

	public EduGAINFilter() {
	    
	}

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		
		Cookie[] cookies = httpRequest.getCookies();
		//Check where the query is coming from
		
		//If returnIDParam has a value, then the request comes from the WFAYF
		if (httpRequest.getParameter("returnIDParam") != null)
			fromWFAYF(httpRequest, httpResponse);

		//If SAMLResponse has a value, then the request comes from the HomeBE
		else if (httpRequest.getParameter("SAMLResponse") != null)
			fromHome(httpRequest, httpResponse);
		
		//If cookies are available, and cookie name is lcook, check for SSO functionality 
		else if ((cookies != null) && (getCookie(httpRequest, httpResponse, "lcook")) != null)
			fromSSO(httpRequest, httpResponse, chain);

/*		//If cookies are available, and cookie name is statecook, it's a self-redirect
		else if ((cookies != null) && (cookies[0].getName().equals("statecook")))
			fromFilter(httpRequest, httpResponse, chain);
*/
		//Otherwise, this is the first access to the app
		else
			fromStart(httpRequest, httpResponse);
			

	}

	private void 	fromWFAYF(HttpServletRequest request, HttpServletResponse response) {
		
		AuthenticationRequest authnReq = new AuthenticationRequest();

		try {
			authnReq.setProviderId(new URI(this.config.getProperty("net.geant.edugain.componentID")));
			authnReq.setRequestID();

			String shire = request.getRequestURL().toString(); //new URI(httpRequest.getRequestURI())+ KEY!!!
			authnReq.setShire(new URI(shire));			

			String resource = shire;// + "?" + request.getQueryString();
			authnReq.setResource(new URI(resource));//new URI(pp)); 
		} catch (URISyntaxException e1) {
			this.log.warn("Error while creating URI: componentID, shire or resource values are incompatible");
		}

		String homeBE = request.getParameter("returnIDParam");
		authnReq.setHomeSite(homeBE);

		WebSSORequester requester = new WebSSORequester();

		try {
			requester.sendAuthenticationRequest(authnReq, response);
		} catch (BaseException e) {
			this.log.error("Unable to send the request to the home domain at: "+ homeBE);
			e.printStackTrace();
		}		
	}
	
	private void 	fromHome(HttpServletRequest request, HttpServletResponse response) {
		String target = request.getParameter("TARGET");
		AuthenticationResponse eduGAINresponse = null;
		try {
			eduGAINresponse = getSAMLResponse(request.getParameter("SAMLResponse").getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unable to parse eduGAIN response");
				return;
			} catch (IOException e1) {
				this.log.error("Invalid eduGAIN response; unable to forward user to error page");
			}
		}
		
		if ( (eduGAINresponse != null) && eduGAINresponse.getResult().equals(AuthenticationResponse.EDUGAIN_NAMESPACE_RESULT_ACCEPTED)) {
			try {
				Cookie lcook = getCookie(request, response, "statecook");
				HashMap attrs = validateCookie(lcook, "statecook");
				if ((attrs != null) && (attrs.containsKey("KEY"))) {
					String key = (String)attrs.get("KEY");
					HashMap<String, String> oldRequest = (HashMap<String, String>) this.loadRequest(key);
					String oldURL = reconstructRequest(oldRequest,response);

					attrs = parseAttrs(eduGAINresponse);
					
					request.getSession().setAttribute("SAMLResponse", eduGAINresponse.toSAML());
			                attachCookies("lcook", attrs, response, false);
			                attachCookies("statecook", null, response, true);
			                //addAttributes(attrs,request.getSession());
			                if (!(oldURL.equals("")) && (oldRequest != null) && !(response.isCommitted()))
			                	response.sendRedirect(oldURL);
			                else 
			                	response.sendError(HttpServletResponse.SC_UNAUTHORIZED ,"Unable to load old request");

				}
				else
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED ,"Connection timed out");
			}
			catch (IOException ioe) {
				try {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED ,"State cookie not found");
				} catch (IOException e) {
					this.log.error("State cookie not found");
					ioe.printStackTrace();
				}
				
			} catch (Exception e) {
				try {
					e.printStackTrace();
					response.sendError(HttpServletResponse.SC_REQUEST_TIMEOUT ,"Process timed out");
				} catch (IOException e1) {
					this.log.error("Process timed out");
					e1.printStackTrace();
				}
			}

		} else
			try {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED ,"Invalid authentication at home domain");
			} catch (IOException e) {
				this.log.error("Invalid authentication at home domain");
				e.printStackTrace();
			} catch (IllegalStateException ex) {
				this.log.error("Unable to forward user to error page");
				ex.printStackTrace();
			}
			
	
	}

	
	private void 	fromSSO(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
		try {
			Cookie lcook = request.getCookies()[0];
			HashMap<String,String> attrs = validateCookie(lcook,"lcook");
			addAttributes(attrs,request.getSession());
			chain.doFilter(request,response);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}		
	}

	private void fromStart(HttpServletRequest request, HttpServletResponse response) {
		
		String key = String.valueOf(System.currentTimeMillis());
		this.log.info("Saving request in filter database, with KEY: " + key);
		HashMap<String,String> attrs = new HashMap<String,String>();
		attrs.put("KEY", key);

		try {
			attachCookies("statecook", attrs,response,false);
			this.saveRequest(request, key);
			
			this.log.info("Redirecting user to WFAYF");
			String queryString = this.config.getProperty("net.geant.edugain.filter.wfayf.url") + "?" + "entityID=" + URLEncoder.encode(this.config.getProperty("net.geant.edugain.componentID"),"UTF-8") + "&return=" + URLEncoder.encode(request.getRequestURL().toString(),"UTF-8") + "&returnID=";
			response.sendRedirect(queryString);
		} catch (IOException e) {
			this.log.error("Error while saving the request");
		} catch (Exception e) {
			this.log.error("Unhandled exception");
			e.printStackTrace();
		}
		
	}

	private AuthenticationResponse getSAMLResponse(byte[] SAMLdata) throws BaseException, IOException {
		       AuthenticationResponse authNresponse = new AuthenticationResponse( );
		        SAMLResponse response = null;
		        Document doc = null;
		        try {
		            InputStream byteData = new ByteArrayInputStream(Base64.decodeBase64(SAMLdata));
		            
		            doc = XML.parserPool.parse(byteData);
		            
		            response = new SAMLResponse( doc.getDocumentElement());
		        } catch (SAMLException ex) {
		            throw new BaseException("WebSSOResponseListener: fail when re-constructing SAML response from string.");
		        } catch (IOException ex) {
		            throw new BaseException("WebSSOResponseListener: I/O error when re-costructing SAML response. "+ex.getMessage());
		        } catch (SAXException ex) {
		            throw new BaseException("WebSSOResponseListener: parsing error when re-costructing SAML response. "+ex.getMessage());
		        }
		        
		        // Signature validation

		        try {
		            Validator validator= new Validator();	
		            try {
		                validator.validate(response);
		            } catch (ValidationException ex) {
			                ex.printStackTrace();
		                throw new BaseException("WebSSOResponseListener: eduGAINVal exception validating response signature(s). "+ex.getMessage());
		            }
		        } catch (ValidationException ex) {
		            throw new BaseException("Problem instantiating an eduGAIN Validator: "+ex.getMessage());
		        }

		        // The SAML response contained within the POSTed response received
		        authNresponse.fromSAML(response);
		        
		        return authNresponse;
	}
		
	private void loadConfiguration(FilterConfig config) {
		String nonDefaultConfig = (String)config.getInitParameter("eduGAINConfig");
		if ( nonDefaultConfig != null) {
			this.config = Configurator.getInstance(nonDefaultConfig);
		}
		else 
			this.config = Configurator.getInstance();
		
		this.serviceID = (String)this.config.getProperty("net.geant.edugain.filter.serviceID");
	}

	private void loadCiphers() {
		String lkeyFile = (String)this.config.getProperty("net.geant.edugain.filter.encrypt.lkey");
		String statekeyFile = (String)this.config.getProperty("net.geant.edugain.filter.encrypt.statekey");
		this.cipher = new AESTool();
		try {
			this.cipher.addKey("lcook", lkeyFile);
			this.cipher.addKey("statecook", statekeyFile);
			this.log.info("lkey sucessfully read from: " + lkeyFile );
			this.log.info("statekey sucessfully read from: " + statekeyFile );
		} catch (IOException e) {
			this.log.error("Unable to read key from: " + lkeyFile + " or " + statekeyFile);
		}
	}
	
	private void loadDatabase() {
		this.requestDBName = (String)this.config.getProperty("net.geant.edugain.filter.requestDB");

		File f = null;
		FileInputStream requestFile;
		try {
			f = new File(this.requestDBName);
			requestFile = new FileInputStream(f);
			ObjectInputStream requestInputStream = new ObjectInputStream(requestFile);

			this.requests= (HashMap<String,HashMap<String,String>>)requestInputStream.readObject();//savedRequest;

			requestInputStream.close();
			requestFile.close();
		} catch (FileNotFoundException e) {
			this.log.error("File " + requestDBName + " not found.");
		} catch (IOException e) {
			this.log.error("Exception while reading file " + requestDBName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			try {
				f.createNewFile();
				this.requests = new HashMap<String,HashMap<String,String>>();
			} catch (IOException e) {
				this.log.error("Fatal error while loading request file. Please fix this and restart the application");
			}
		}
	}

	private Map<?,?> loadRequest (String key) throws Exception {
		Map<?,?> request = null;
		request = (Map<?, ?>) this.requests.get(key);
		requests.remove(key);
		ObjectOutputStream requestFileWriter = new ObjectOutputStream(new FileOutputStream (this.requestDBName));
		requestFileWriter.writeObject(this.requests);
		requestFileWriter.close();


		return request;
	}
	
	public void saveRequest (HttpServletRequest request, String key) throws Exception{
		HashMap<String,String> clonedRequest = cloneRequest(request,null);
		this.requests.put(key,clonedRequest);
		ObjectOutputStream requestFileWriter = new ObjectOutputStream(new FileOutputStream (this.requestDBName));
		requestFileWriter.writeObject(this.requests);
		requestFileWriter.close();
		this.log.info("Request saved in database, with key: " + key);
	}
	
	private HashMap<String,String> cloneRequest(HttpServletRequest request, Map<?,?> attrs) {
		HashMap<String,String> params = new HashMap<String,String>();

		try {
			params.put("METHOD", URLEncoder.encode(request.getMethod(), "UTF-8"));
			params.put("PATH_INFO", URLEncoder.encode(request.getPathInfo(), "UTF-8"));
			params.put("REQUEST_URI", URLEncoder.encode(this.config.getProperty("net.geant.edugain.filter.host") + request.getRequestURI(), "UTF-8"));
			params.put("QUERY_STRING", URLEncoder.encode(request.getQueryString(), "UTF-8"));
			params.put("REMOTE_URL",URLEncoder.encode(request.getParameter("URL"), "UTF-8"));
			params.put("REQUEST_URL",URLEncoder.encode(request.getRequestURL().toString(), "UTF-8"));
		}
		catch (Exception e) {
			params.put("METHOD", request.getMethod());
			params.put("PATH_INFO", request.getPathInfo());
			params.put("REQUEST_URI", this.config.getProperty("net.geant.edugain.filter.host") + request.getRequestURI());
			params.put("QUERY_STRING", request.getQueryString());
			params.put("REMOTE_URL",request.getParameter("URL"));
			params.put("REQUEST_URL",request.getRequestURL().toString());
		}

		String method = request.getMethod();
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String key = (String)e.nextElement();
			String value = request.getParameter(key);
			params.put(method + "." + key, value);
		}

		if ((attrs != null) && !attrs.isEmpty()) {
			Iterator<?> it = attrs.entrySet().iterator();
			while (it.hasNext()) {
				Entry<?,?> entry = (Entry<?,?>)it.next(); 
				String key = (String)entry.getKey();
				String value = (String)entry.getValue();
				params.put(key, value);
			}
		}
		return params;
	}
	
	private void attachCookies(String name, HashMap<String,String> attrs, HttpServletResponse response, boolean outdated) throws ServletException {
		String maxAge = "0";    
		Cookie lcook = null;
		if (!outdated) {
			lcook = new Cookie(name, cipher.encode(name, generateCookie(attrs,outdated)));
		}
		else {
			lcook = new Cookie(name, "");
			lcook.setMaxAge(Integer.parseInt(maxAge));
		}
		lcook.setPath(this.location);
		response.addCookie(lcook);
	}
	
	/** Generates the information for the Lcook
	 * @param request Incoming request
	 * @param outdated If we have to create outdated cookies, as in a LOGOUT handling
	 * @return Lcook data
	 * @throws ServletException if there has been an exception while writing in the cookie registry
	 */
	private String generateCookie(HashMap<String,String> attrs, boolean outdated) {
		String rawCook = "";
		long timeStamp = System.currentTimeMillis()/1000;
		if (outdated) {
			timeStamp = 0;
		}
		rawCook += String.valueOf(timeStamp);
		rawCook += ":";
		rawCook += this.location;
		rawCook += ":";
		String serviceID = this.serviceID;
		rawCook += serviceID;
		rawCook += ":";
		String userData = "";
		Iterator it  = attrs.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String,String> entry = (Entry<String,String>)it.next();
			userData += entry.getKey() + "=" + entry.getValue() + ",";
		}
		userData = userData.substring(0, userData.length()-1);
		rawCook += userData;
		return rawCook;
	}	
	
	private Cookie getCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) throws IOException {
		Cookie[] cookies = request.getCookies();
		int length = cookies.length;
		boolean found = false;
		int i=0;
		while  ((i<length) && !found) {
			found = cookies[i].getName().equals(cookieName);
			i++;
		}
		i--;
		if (!found) {
			//response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, cookieName + "cookie not found");
			return null;
		}
		return cookies[i];

	}
	
	private HashMap<String,String>  validateCookie(Cookie lcook, String name) {
		HashMap<String,String> attrs = null;
		String data = null;
		String timeOut = "";
		if (name.equals("lcook"))
			timeOut = (String)this.config.getProperty("net.geant.edugain.filter.lcookTimeout");
		else
			timeOut = (String)this.config.getProperty("net.geant.edugain.filter.timeout");
			
		data = cipher.decode(name, lcook.getValue());
		String[] splittedValues = data.split(":");
		int length = splittedValues.length;
		String timeStampStr = (String)splittedValues[0];
		String location = (String)splittedValues[1];
		String serviceID = (String)splittedValues[2];

		long currentTime = System.currentTimeMillis();
		long timeStamp = Long.parseLong(timeStampStr)*1000;
		boolean onTime = currentTime < (timeStamp + Long.parseLong(timeOut));
		String userData = "";

		if (onTime && serviceID.equals(this.serviceID)) {

			attrs = new HashMap<String,String>();
			for (int i=3; i < length; i++) {
				userData = userData + (String)splittedValues[i] + ":";
			}
			userData = userData.substring(0, userData.length()-1);

			String[] splittedAttrs = userData.split(",");
			length = splittedAttrs.length;
			for (int i=0; i<length; i++) {
				String attValue = (String)splittedAttrs[i];
				int separatorIndex = attValue.indexOf("=");
				String key = attValue.substring(0, separatorIndex);
				String value = attValue.substring(separatorIndex+1,attValue.length());
				attrs.put(key, value);
			}
		}

		return attrs;
	}

	private String reconstructRequest (HashMap<String,String> oldRequest, HttpServletResponse response) throws IOException, ServletException {
		String uri = "";
		if (oldRequest.get("METHOD").equals("POST"))
			attachPOSTParams(response, oldRequest);
		else if (oldRequest.get("METHOD").equals("GET")) {
			uri = (String)oldRequest.get("REQUEST_URI");
			Iterator keys = oldRequest.keySet().iterator();
			boolean firstParam = true;
			while (keys.hasNext()) {
				String paramKey = (String)keys.next();
				if (paramKey.startsWith("GET.")) {
					if (firstParam) {
						uri += "?";
						firstParam = false;
					}
					String param = paramKey.substring(paramKey.indexOf(".") + 1);
					uri += URLEncoder.encode(param + "=" + (String)oldRequest.get(paramKey),"UTF-8") + "&";
				}
			}
			if (uri.endsWith("&"))
				uri = uri.substring(0,uri.length()-1);

		}
		return uri;
	}
	
	    /** Attaches the POST parameters saved in a previous request to the actual one
	     *  @param response HTTP response
	     *  @param oldRequest Map with old request's values
	     *  @throws IOException If error while writing data on the response
	     *  @throws ServletException If incorrect values on configuration file
	     */
	public void attachPOSTParams(HttpServletResponse response, HashMap oldRequest) throws IOException, ServletException {
		response.setContentType("text/html");
		OutputStream output = response.getOutputStream();
		String preamble = "";
		preamble = "<html>" +
		"<head><title>PAPI Internal Location Service</title></head>" +
		"<body onload=proxy.submit()><h3>Please wait while you are being redirected...</h3>" +
		"<form name=proxy method=\"post\" action=\"" + (String)oldRequest.get("REQUEST_URI") + "\">";

		output.write(preamble.getBytes());

		Iterator keys = oldRequest.keySet().iterator();
		while (keys.hasNext()) {
			String key = (String)keys.next();
			if (key.startsWith("POST.")) {
				String value = (String)oldRequest.get(key);
				key = key.substring(5,key.length());
				String input = "<input name=" + key + " type=hidden value=" + value + " />";
				output.write(input.getBytes());
			}
		}

		String end = "";
		end = "</form></body>" + "</html>";
		output.write(end.getBytes());

		output.flush();
		output.close();

	}	    
	
	    private HashMap<String,String> parseAttrs (AuthenticationResponse response) {
		    HashMap<String,String> attrs = new HashMap<String,String>();
		    
		    ArrayList<AttributeValues> values = response.getAttributeValueList();
		    Iterator it = values.iterator();
		    while (it.hasNext()) {
			    AttributeValues att = (AttributeValues)it.next();
			    String clave = att.getAttributeName();
			    String namespace = att.getAttributeNameSpace();
			    String value = "";
			    ArrayList<String> valor = att.getAttributeValue();
			    Iterator valIt = valor.iterator();
			    while (valIt.hasNext()) {
				    String item = (String)valIt.next();
				    String val = item.replace(";","-");
				    val = val.replace(":", "_");
				    value = value + val + "-";
			    }

			    value = value.substring(0, value.length() - 1);
			    attrs.put(clave, value);
			    //this.session.setAttribute(clave,value);

		    }
		    
		    return attrs;
	    }
	    
	    private void addAttributes(HashMap<String,String> attrs, HttpSession session) {
			Iterator it  = attrs.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String,String> entry = (Entry<String,String>)it.next();
				session.setAttribute(entry.getKey(),entry.getValue());
			}
	    }
}
