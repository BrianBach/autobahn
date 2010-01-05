<%@ include file="../common/includes.jsp" %>
<h2><spring:message code="nav.edugainUser.info" text="Edugain User description"/></h2>
<div id="form">
		<%
			out.println ("<table>");
			java.util.Enumeration attrs = session.getAttributeNames();
			int i=0;
			while (attrs.hasMoreElements()) {
				out.println ("<tr>");
				String attrName = (String)attrs.nextElement();
				if (attrName.startsWith("urn")) {
					i++;
					out.println("<td class=\"label\">"+attrName+"</td>");
					String attrValues = (String)session.getAttribute(attrName);
					String[] splittedAttrValues = attrValues.split("-");
					int size = splittedAttrValues.length;
					out.println("<td class=\"value\">");
					for (int j=0; j<size; j++) {
						String value = splittedAttrValues[j];
						value = value.replace("_", ":");
						out.println(value);
					}
					out.println("</td>");
					out.println("</tr>");

				}
				else if (attrName.equals("SAMLResponse")){
					out.println("<tr>");
					out.println("<td class=\"label\">"+attrName+"</td>");
					out.println("<td class=\"value\"><textarea rows=20 cols=60>" + (org.opensaml.SAMLResponse)session.getAttribute(attrName));
					out.println("</textarea></td>");
					out.println("</tr>");
				}
				else {
					/*out.println("<td class=\"label\">"+ attrName+"</td>");
					out.println("<td class=\"value\">"+(String)session.getAttribute(attrName));
					out.println("</td>");
					out.println("</tr>");*/
				}
			}
			out.println("</table>");
			 %>
			 
</div>