<%@include file="../xava/imports.jsp"%>

<%@page import="com.openxava.naviox.util.Organizations"%>

<jsp:useBean id="modules" class="com.openxava.naviox.Modules" scope="session"/>
<jsp:useBean id="folders" class="com.openxava.naviox.Folders" scope="session"/>

<div id="modules_list_header" class="modules-list-header">

<% if (!folders.isRoot()) { %>
<a href="javascript:naviox.goBack()">		
<div class="phone-back-button">	
   	<div><span></span></div>
   	<p><xava:message key="back"/></p>		
</div>
</a>
<% } else if (modules.showsIndexLink()) { %>
<div class="phone-back-button"
	onclick="window.location='<%=request.getContextPath()%>/m/Index'">	
   	<div><span></span></div>
   	<p><xava:label key="Index"/></p>		
</div>	 
<% } else {
	String organization = Organizations.getCurrent(request);
	if (organization == null) organization = "";
%>
<div class="phone-back-button" 
	onclick="window.location='<%=request.getContextPath()%>/naviox/signOut.jsp?organization=<%=organization%>'">
   	<div><span></span></div>
   	<p><xava:message key="exit"/></p>		
</div>
<% } %>

<span class="phone-title">
<%=folders.getFolderLabel()%>
</span>

<% if (!folders.isRoot()) { %>
<span id="back_folder_counterweight">&nbsp;</span>
<% } %>
</div>

<div id="modules_list_search_header">
<span class="phone-title">
<xava:message key="search_modules"/>
</span> 
</div>

<div id="search_modules">
<input id="search_modules_text" type="text" size="38" placeholder='<xava:message key="search_modules"/>'/>
</div>

<div id="modules_list_core">
<jsp:include page="../naviox/modulesList.jsp"/>
</div>
