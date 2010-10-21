<%@ include file="../common/includes.jsp" %>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/jquery/tabs.css"/>"/>
<h2><spring:message code="home.title" text="About AutoBAHN"/></h2>

<div style="text-align: left;" class="home_image">
<p>
AutoBAHN is about the investigation and development of an end-to-end (and therefore multi-domain) 
"Bandwidth Allocation and Reservation Service" a^€“ in other words a "Bandwidth on Demand" (BoD) service. 
Sometimes the term "circuit-based" 
has been used in order to highlight the fact that the bandwidth should be reserved,
not contended, exhibit some form of deterministic performance and be logically separated
from other traffic sharing the network. A number of end-to-end services fulfill these criteria including:
</p>

<div align="left">
<ul>
<li>Point-to-point Ethernet ("Ethernet private lines")</li>
<li>Point-to-point SONET/SDH (the equivalent of carrier IPLCs)</li>
<li>Point-to-point "wavelengths" (for example, based on G.709 ODUk switching or all-optical switching of regenerated wavelengths)</li>
<li>Point-to-point fibre links (for example, space switched passive optical paths for shorter distances or even, some time in the future, space switched 1 or 2R regenerated optical paths for longer reach applications)
</li>
</ul>
</div>
<hr>

<div style="text-align:center"><b>Architecture overview</b></div>
<br>
<p>SingleAutoBAHN instance deployed in a domain consist of three main software components:</p>
<div align="left"><ul>
<li>InterDomainManager (IDM) </li>

<li>Domain Manager (DM)</li>

<li>Technology Proxy (TP)</li>
</ul></div>
<p>The following components need to be deployed once for the whole AutoBAHN environment:</p>
<div align="left"><ul>
<li>
perfSONAR Lookup Service </li>
<li>
Web GUI - graphical user interface
</li>
</ul>
</div>
<img width="780px" src="/autobahn-gui/themes/style/deploy2.png">
<br />
<br />
<br />

</div>