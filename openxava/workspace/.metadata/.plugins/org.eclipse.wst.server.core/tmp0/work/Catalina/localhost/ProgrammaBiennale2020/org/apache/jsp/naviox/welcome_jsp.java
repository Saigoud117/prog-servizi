/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.50
 * Generated at: 2021-11-05 14:03:05 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.naviox;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.openxava.application.meta.MetaApplications;
import org.openxava.application.meta.MetaApplication;
import org.openxava.util.Locales;
import org.openxava.web.style.XavaStyle;
import org.openxava.util.XavaPreferences;

public final class welcome_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(2);
    _jspx_dependants.put("/WEB-INF/openxava.tld", Long.valueOf(1635857799638L));
    _jspx_dependants.put("/naviox/../xava/imports.jsp", Long.valueOf(1635857798499L));
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fxava_005fmessage_0026_005fkey_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fxava_005flabel_0026_005fkey_005fnobody;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fxava_005fmessage_0026_005fkey_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fxava_005flabel_0026_005fkey_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fxava_005fmessage_0026_005fkey_005fnobody.release();
    _005fjspx_005ftagPool_005fxava_005flabel_0026_005fkey_005fnobody.release();
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write('\n');
      out.write('\n');
      out.write('\n');
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

String applicationName = request.getContextPath().substring(1);
MetaApplication metaApplication = MetaApplications.getMetaApplication(applicationName);
Locales.setCurrent(request);
String oxVersion = org.openxava.controller.ModuleManager.getVersion();
String title = (String) request.getAttribute("naviox.pageTitle");
if (title == null) title = metaApplication.getLabel();

      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("\r\n");
      out.write("<head>\r\n");
      out.write("\t<link rel=\"shortcut icon\" href=\"");
      out.print(request.getContextPath());
      out.write("/naviox/images/favicon.ico\">\r\n");
      out.write("\t<img id=\"logo_welcome\" src=\"");
      out.print(request.getContextPath());
      out.write("/naviox/images/Regione_Marchebig.png\"/>\r\n");
      out.write("\t<title>");
      out.print(title);
      out.write("</title>\r\n");
      out.write("\t<meta name='viewport' content='width=device-width, initial-scale=1, maximum-scale=1'>\r\n");
      out.write("\t<link href=\"");
      out.print(request.getContextPath());
      out.write("/xava/style/");
      out.print(XavaPreferences.getInstance().getStyleCSS());
      out.write("?ox=");
      out.print(oxVersion);
      out.write("\" rel=\"stylesheet\" type=\"text/css\">\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body id=\"welcome\" ");
      out.print(XavaStyle.getBodyClass(request));
      out.write(">\r\n");
      out.write("\r\n");
      out.write("<h1>");
      out.print(metaApplication.getLabel());
      out.write("</h1>\r\n");
      out.write("<p>");
      out.print(metaApplication.getDescription());
      out.write("</p>\r\n");
      out.write("<p>");
      if (_jspx_meth_xava_005fmessage_005f0(_jspx_page_context))
        return;
      out.write("</p> \r\n");
      out.write("<p id=\"signin_tip\">");
      if (_jspx_meth_xava_005fmessage_005f1(_jspx_page_context))
        return;
      out.write("</p> \r\n");
      out.write("\r\n");
      out.write("<div class=\"ox-bottom-buttons\">\r\n");
      out.write("\t<input type=\"hidden\">\r\n");
      out.write("\t<a class=\"welcome-link\" href=\"\\CohesionServlet\\login.jsp?ReturnUrl=../ProgrammaBiennale2020/naviox/signInCohesion.jsp\">");
      if (_jspx_meth_xava_005flabel_005f0(_jspx_page_context))
        return;
      out.write("</a>\r\n");
      out.write("</div>\r\n");
      out.write("<input type=\"button\" class=\"button-small\" tabindex=\"1\" onclick=\"window.location='m/SignIn'\" value=\"");
      if (_jspx_meth_xava_005flabel_005f1(_jspx_page_context))
        return;
      out.write("\">   \r\n");
      out.write("</body>\r\n");
      out.write("\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_xava_005fmessage_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  xava:message
    org.openxava.web.taglib.MessageTag _jspx_th_xava_005fmessage_005f0 = (org.openxava.web.taglib.MessageTag) _005fjspx_005ftagPool_005fxava_005fmessage_0026_005fkey_005fnobody.get(org.openxava.web.taglib.MessageTag.class);
    _jspx_th_xava_005fmessage_005f0.setPageContext(_jspx_page_context);
    _jspx_th_xava_005fmessage_005f0.setParent(null);
    // /naviox/welcome.jsp(42,3) name = key type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_xava_005fmessage_005f0.setKey("welcome_point1");
    int _jspx_eval_xava_005fmessage_005f0 = _jspx_th_xava_005fmessage_005f0.doStartTag();
    if (_jspx_th_xava_005fmessage_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fxava_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_xava_005fmessage_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fxava_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_xava_005fmessage_005f0);
    return false;
  }

  private boolean _jspx_meth_xava_005fmessage_005f1(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  xava:message
    org.openxava.web.taglib.MessageTag _jspx_th_xava_005fmessage_005f1 = (org.openxava.web.taglib.MessageTag) _005fjspx_005ftagPool_005fxava_005fmessage_0026_005fkey_005fnobody.get(org.openxava.web.taglib.MessageTag.class);
    _jspx_th_xava_005fmessage_005f1.setPageContext(_jspx_page_context);
    _jspx_th_xava_005fmessage_005f1.setParent(null);
    // /naviox/welcome.jsp(43,19) name = key type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_xava_005fmessage_005f1.setKey("signin_tip");
    int _jspx_eval_xava_005fmessage_005f1 = _jspx_th_xava_005fmessage_005f1.doStartTag();
    if (_jspx_th_xava_005fmessage_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fxava_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_xava_005fmessage_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fxava_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_xava_005fmessage_005f1);
    return false;
  }

  private boolean _jspx_meth_xava_005flabel_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  xava:label
    org.openxava.web.taglib.LabelTag _jspx_th_xava_005flabel_005f0 = (org.openxava.web.taglib.LabelTag) _005fjspx_005ftagPool_005fxava_005flabel_0026_005fkey_005fnobody.get(org.openxava.web.taglib.LabelTag.class);
    _jspx_th_xava_005flabel_005f0.setPageContext(_jspx_page_context);
    _jspx_th_xava_005flabel_005f0.setParent(null);
    // /naviox/welcome.jsp(47,120) name = key type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_xava_005flabel_005f0.setKey("SignInCohesion");
    int _jspx_eval_xava_005flabel_005f0 = _jspx_th_xava_005flabel_005f0.doStartTag();
    if (_jspx_th_xava_005flabel_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fxava_005flabel_0026_005fkey_005fnobody.reuse(_jspx_th_xava_005flabel_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fxava_005flabel_0026_005fkey_005fnobody.reuse(_jspx_th_xava_005flabel_005f0);
    return false;
  }

  private boolean _jspx_meth_xava_005flabel_005f1(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  xava:label
    org.openxava.web.taglib.LabelTag _jspx_th_xava_005flabel_005f1 = (org.openxava.web.taglib.LabelTag) _005fjspx_005ftagPool_005fxava_005flabel_0026_005fkey_005fnobody.get(org.openxava.web.taglib.LabelTag.class);
    _jspx_th_xava_005flabel_005f1.setPageContext(_jspx_page_context);
    _jspx_th_xava_005flabel_005f1.setParent(null);
    // /naviox/welcome.jsp(49,99) name = key type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_xava_005flabel_005f1.setKey("SignIn");
    int _jspx_eval_xava_005flabel_005f1 = _jspx_th_xava_005flabel_005f1.doStartTag();
    if (_jspx_th_xava_005flabel_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fxava_005flabel_0026_005fkey_005fnobody.reuse(_jspx_th_xava_005flabel_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fxava_005flabel_0026_005fkey_005fnobody.reuse(_jspx_th_xava_005flabel_005f1);
    return false;
  }
}
