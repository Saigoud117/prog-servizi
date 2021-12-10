<%!
//Inizio funzioni di supporto
public static byte[] base64Decode(String data){
	try{
		return javax.xml.bind.DatatypeConverter.parseBase64Binary(data);
	}catch(Exception e){ e.printStackTrace();}
	return new byte[0];
}
public static String base64Encode(byte[] data){
	try{
		return javax.xml.bind.DatatypeConverter.printBase64Binary(data);
	}catch(Exception e){ e.printStackTrace();}
	return "";
}
public static org.w3c.dom.Document getXmlDocFromString(String xml){
	try{
		javax.xml.parsers.DocumentBuilderFactory dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		return dbf.newDocumentBuilder().parse(new java.io.ByteArrayInputStream(xml.getBytes()));
	}catch(Exception e){ e.printStackTrace();}
	return null;
}
public static boolean verifySignature(org.w3c.dom.Document doc , String pathCert) {
	//Questa procedura non funziona con java jdk1.6.xx Ritorna sempre false sulla validazione delle references
	//Se nell'XML della servlet si è specificato il parametro webSso in https si può evitare la verifica della firma in quando la verifica del certificato SSL eseguita automaticamente dalla servlet, garantirà l'indentità del server che restituisce il token.
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
public static byte[] cipher3DES(boolean encrypt, byte[] message, byte[] key) throws Exception {
	try{
    	if(key.length != 24)
    		throw new Exception("key size must be 24 bytes");
		int cipherMode = javax.crypto.Cipher.DECRYPT_MODE;
		if(encrypt)
			 cipherMode = javax.crypto.Cipher.ENCRYPT_MODE;
		javax.crypto.Cipher sendCipher = javax.crypto.Cipher.getInstance("DESede/ECB/NoPadding");
		javax.crypto.SecretKey myKey =  new javax.crypto.spec.SecretKeySpec(key, "DESede");
		sendCipher.init(cipherMode, myKey);
		return sendCipher.doFinal(message);
	}catch(Exception e){e.printStackTrace();}
	return new byte[0];
}
public static String getPOSTRedirectPage(String urlWithParametersToGet, String parametersToPost){
	String url = urlWithParametersToGet;
	String parameters = parametersToPost;
	if(parameters == null)
		parameters = "";
	
	String[] paramterList = null;
	if(!parameters.equals(""))
		paramterList = parameters.split("\\&");
	
	String postRedirect="<html><head><script type=\"text/javascript\">function redirect() {document.myForm.submit();}</script></head><body OnLoad='redirect();'><FORM name=\"myForm\" method=\"POST\" action=\""+url+"\"><input type=\"submit\" value=\"Continue\">";
	
	if(paramterList!=null)
		for(String parameter:paramterList)
			if(parameter.contains("=")){
				String name = parameter.substring(0, parameter.indexOf("="));
				String value = parameter.substring(parameter.indexOf("=")+1, parameter.length());
				postRedirect += "<input type=\"hidden\" name=\""+name+"\" value=\""+value+"\">";
			}
	postRedirect += "</FORM></body></html>";
	return postRedirect;
}
//Fine funzioni di supporto

%><%

String servletUrl = "./Authentication"; //Impostare l'url della servlet

String getURL = request.getRequestURL().toString();

String urlEncodedParameter = java.net.URLEncoder.encode("?" + request.getQueryString(), "UTF-8");

if(request.getQueryString()==null || request.getQueryString()=="")
	urlEncodedParameter = "";

String cohesionServlet = servletUrl + "?ReturnUrl=" + getURL + urlEncodedParameter;

String encryptionKey = ""; //il token restituito può essere criptato in 3-DES/ECB/NoPadding

String pathCertificate = session.getServletContext().getRealPath("/") + "cohesion2.cer";

if(request.getParameter("token") != null){
	
	String token = new String(base64Decode(request.getParameter("token")));
	
	if(!encryptionKey.equals(""))
		token = new String(cipher3DES(false, base64Decode(request.getParameter("token")),encryptionKey.getBytes()));

	//boolean statusCheck = verifySignature(getXmlDocFromString(token), pathCertificate); //ATTENZIONE: Non funziona con Java v1.6.xx (ritorna sempre false)
	
	session.setAttribute("TOKEN", token);
	
}else{
	if(session.getAttribute("TOKEN")==null)
		response.sendRedirect(cohesionServlet);
}

if(session.getAttribute("TOKEN")!=null){
	if(request.getParameter("ReturnUrl") != null) //Se questa pagina è stata richiamata con il parametro ReturnUrl allora redirigo alla pagina specificata in ReturnUrl passando in POST il token in codifica base64 (è consigliato che la connessione sia protetta mediante https)
		out.print(getPOSTRedirectPage(request.getParameter("ReturnUrl"), "token=" + base64Encode(session.getAttribute("TOKEN").toString().getBytes())));
	else
		out.print("<html><body><xmp>" + session.getAttribute("TOKEN") + "</xmp></body></html>"); //Test: Mostro a schermo il token restituito da cohesion
}
%>