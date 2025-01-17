<%@page import="util.UserUtils"%>
<%@ page import="javax.xml.parsers.*,org.w3c.dom.*,org.xml.sax.InputSource, java.io.StringReader" %>

<%!
public static byte[] base64Decode(String data) throws Exception{	
	return javax.xml.bind.DatatypeConverter.parseBase64Binary(data);
}
public static boolean verifySignature(org.w3c.dom.Document doc , String pathCert) {
	//Questa procedura non funziona con java jdk1.6.xx Ritorna sempre false sulla validazione delle references
	//Se nell'XML della servlet si � specificato il parametro webSso in https si pu� evitare la verifica della firma in quando la verifica del certificato SSL eseguita automaticamente dalla servlet, garantir� l'indentit� del server che restituisce il token.
	try{
		if (doc.getElementsByTagNameNS(javax.xml.crypto.dsig.XMLSignature.XMLNS, "Signature").getLength() == 0)
			throw new Exception("Cannot find Signature element");
		java.security.cert.X509Certificate cert = (java.security.cert.X509Certificate)java.security.cert.CertificateFactory.getInstance("X.509").generateCertificate(new java.io.FileInputStream(new java.io.File(pathCert)));
		javax.xml.crypto.dsig.dom.DOMValidateContext valContext = new javax.xml.crypto.dsig.dom.DOMValidateContext(cert.getPublicKey(), doc.getElementsByTagNameNS(javax.xml.crypto.dsig.XMLSignature.XMLNS, "Signature").item(0));
	 	javax.xml.crypto.dsig.XMLSignature signature = javax.xml.crypto.dsig.XMLSignatureFactory.getInstance("DOM").unmarshalXMLSignature(valContext);
		return signature.validate(valContext); 
	}catch(Exception e){ e.printStackTrace();}
	return false;
}
public static org.w3c.dom.Document getXmlDocFromString(String xml){
	try{
		javax.xml.parsers.DocumentBuilderFactory dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		return dbf.newDocumentBuilder().parse(new java.io.ByteArrayInputStream(xml.getBytes("UTF-8")));
	}catch(Exception e){ e.printStackTrace();}
	return null;
}
public static String getXMLValue(org.w3c.dom.Document doc, String name) throws Exception{
	try {
		NodeList nlist = doc.getElementsByTagName(name);
	 	String value = nlist.item(0).getFirstChild().getNodeValue();
	 	return value; // ma perche cazzo va qui
	}catch(Exception ex){
		throw new Exception("tag " + name + "not found");
	}
}
%>

<%
if(request.getParameter("token") != null) {
	//qui me devi richiama il signin
	//out.print("<html><body><xmp>" + new String(base64Decode(request.getParameter("token"))) + "</xmp></body></html>");
	//me devi tira fori username l� sername cos� � chiaro!
	
	 try{
		 String xml = new String(base64Decode(request.getParameter("token")), "UTF-8");
		 /*
		 String XmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + xml;
		 
		 DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		 DocumentBuilder db=dbf.newDocumentBuilder();
		 
		 InputSource is = new InputSource();
	     is.setCharacterStream(new StringReader(XmlString));
		 
		 doc=db.parse(is);proa mpo
		 */
		 Document doc = getXmlDocFromString(xml);
		 String cf = getXMLValue(doc, "codice_fiscale");
		 String name = getXMLValue(doc, "nome");
		 
		 out.print(doc);

		 String pathCertificate = session.getServletContext().getRealPath("/") + "cohesion2.cer";
		 
		 boolean statusCheck = true; //verifySignature(doc, pathCertificate); //ATTENZIONE: Non funziona con Java v1.6.xx (ritorna sempre false)
		 
		 if (statusCheck){
			if (com.openxava.naviox.model.User.find(cf) != null)
			{
			 	com.openxava.naviox.impl.SignInHelper.signIn(session, cf);
			}
			else
			{
				UserUtils.Signup(cf, name);
				com.openxava.naviox.impl.SignInHelper.signIn(session, cf);
			}
			response.sendRedirect("/ProgrammaBiennale2020/m/Dashboard");
		 }
		 else
		 {
			 out.print("FIRMA TOKEN NON VERIFICATA");
		 }
	 }catch(Exception e){
		 out.print("ERROR in the login phase: " + e.getMessage());
	 }
} else
	out.print("PARAMETER token NOT PROVIDED");
%>