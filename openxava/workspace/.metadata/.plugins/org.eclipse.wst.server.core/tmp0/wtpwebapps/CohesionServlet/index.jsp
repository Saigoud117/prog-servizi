<%!
public static byte[] base64Decode(String data){
	try{
		return javax.xml.bind.DatatypeConverter.parseBase64Binary(data);
	}catch(Exception e){ e.printStackTrace();}
	return new byte[0];
}
%>
<%
if(request.getParameter("token") != null)
	out.print("<html><body><xmp>" + new String(base64Decode(request.getParameter("token"))) + "</xmp></body></html>");
else
	out.print("<a href=\"login.jsp?ReturnUrl=" + request.getRequestURL().toString() + "\">LOGIN COHESION</a>");
%>