package it.unicam.cs.cohesion.servlet;

import it.unicam.cs.cohesion.servlet.utils.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class AuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String urlCheck="";
	private String urlIndex="";
	private String urlValidate=""; 
	private String urlRichiesta="";
	private String additionalData="";
    private String keystorePath = "";
    private String keystoreType = "";
    private String pwdKeystore = "";
    private String aliasCertificate = "";
    private String pwdCertificate = "";
    private String wsSso = "";
    private String webSso = "";
    private String idsito = "TEST";
    private String encryptionKey = "";
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
	    //Imposto le variabili locali con i parametri di configurazione
		urlCheck = config.getInitParameter("urlCheck");
		urlIndex = config.getInitParameter("urlIndex");
		urlRichiesta = config.getInitParameter("urlRichiesta");
		
		keystorePath = config.getInitParameter("keystorePath");
		keystoreType = config.getInitParameter("keystoreType");
		pwdKeystore = config.getInitParameter("pwdKeystore");
		aliasCertificate = config.getInitParameter("aliasCertificate");
		pwdCertificate = config.getInitParameter("pwdCertificate");
		wsSso = config.getInitParameter("wsSso");
		webSso = config.getInitParameter("webSso");
		
		if(!NoE(config.getInitParameter("additionalData")))
			additionalData = config.getInitParameter("additionalData");
		
		if(!NoE(config.getInitParameter("encryptionKey")))
			encryptionKey = config.getInitParameter("encryptionKey");
				
		if(NoE(urlCheck) || NoE(urlRichiesta) || ((NoE(wsSso) || NoE(keystorePath) || NoE(keystoreType) || NoE(pwdKeystore) || NoE(aliasCertificate) || NoE(pwdCertificate)) && NoE(webSso)))	
			throw new ServletException("Errore nella configurazione della servlet; applicazione non inizializzata correttamente.");
	}

	private boolean NoE(String dataToTest){
		return dataToTest==null || dataToTest.trim()=="";
	}
	
	private String wsCheckSessionSSO(String idSessioneSSO, String idSessioneASP) throws Exception{
		String ret = "";
		
		MySOAPClient mySOAPClient = new MySOAPClient(wsSso);
		
		ArrayList<String[]> parameterList= new ArrayList<String[]>();
		parameterList.add(new String[]{"IdSessioneSSO",idSessioneSSO});
		parameterList.add(new String[]{"IdSessioneASPNET",idSessioneASP});
		
		mySOAPClient.createEnvelope("http://tempuri.org/", "GetCredential", parameterList);
		mySOAPClient.setSOAPAction("http://tempuri.org/GetCredential");
		
		//Aggiungo Header Cohesion
		Element cohesionHeader = mySOAPClient.createEnvelopeElement("http://uddi.regione.marche.it/Cohesion","Cohesion");
		Element enteIdHeader = mySOAPClient.createEnvelopeElement("http://uddi.regione.marche.it/Cohesion", "enteId");
		Element userProfileHeader = mySOAPClient.createEnvelopeElement("http://uddi.regione.marche.it/Cohesion", "userProfile");
		mySOAPClient.setTextToEnvelopeElement(enteIdHeader, idsito);
		cohesionHeader.appendChild(enteIdHeader);
		cohesionHeader.appendChild(userProfileHeader);
		mySOAPClient.addEnvelopeHeader(cohesionHeader);
		
		mySOAPClient.signEnvelope(keystorePath, keystoreType, pwdKeystore, aliasCertificate, pwdCertificate);
		
		mySOAPClient.callWS(false, false);
		
		ret = XMLUtility.getXmlDocFromString(mySOAPClient.getWsResponseBodyEnvelope()).getElementsByTagNameNS("http://tempuri.org/", "GetCredentialResult").item(0).getTextContent();
		
		return ret;
	}
	
	private String webCheckSessionSSO(String idSessioneSSO, String idSessioneASP) throws Exception{
		String ret = "";
		
		String dataToSend = "Operation=GetCredential&IdSessioneSSO=" + idSessioneSSO + "&IdSessioneASPNET=" + idSessioneASP;;
		ret = new String(NETUtility.sendHTTPPOST(webSso, dataToSend, null, false, false));
		
		return ret;
	}
	
	private String normalizeECommercial(String data){
		String ret = data;
		for(int i=0;i<ret.length();i++)
			if(ret.charAt(i) == '&' && !ret.substring(i).startsWith("&amp;"))
				ret = ret.substring(0,i) + "&amp;" + ret.substring(i+1, ret.length());
		return ret;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		
		urlValidate = request.getRequestURL().toString();
		if(!NoE(request.getParameter("ReturnUrl")))
			urlRichiesta = request.getParameter("ReturnUrl");
		
		urlRichiesta = normalizeECommercial(urlRichiesta);
		urlValidate = normalizeECommercial(urlValidate);
		
		String auth = request.getParameter("auth");
		String validateXML="";
		String validateXML64="";
		String encodedValidateXML64="";
		String ssoAuth="";
		PrintWriter out = response.getWriter();
		
		//Controlla se e' presente il parametro auth
		if (!NoE(auth)) {

			try {
				//prende il parametro auth
				validateXML64 = auth;
				
				//lo decodifica da base64
				validateXML = new String(GeneralUtils.base64Decode(validateXML64));
				
				//Legge documento xml
				Document doc = XMLUtility.getXmlDocFromString(validateXML);
				
				//Prende L'esito dell'autenticazione
				ssoAuth = doc.getElementsByTagName("esito_auth_sso").item(0).getFirstChild().getNodeValue();
				
				//Controlla se l'esito e' OK
				if (ssoAuth.equals("OK")) {

					//Prende l'id sessione sso e l'id sessione asp contenuti nel file xml di auth
					String idSessioneSSO = doc.getElementsByTagName("id_sessione_sso").item(0).getFirstChild().getNodeValue();
					String idSessioneASP = doc.getElementsByTagName("id_sessione_aspnet_sso").item(0).getFirstChild().getNodeValue();
					
					String tokenAuth = "";
					if(NoE(webSso))
						tokenAuth = wsCheckSessionSSO(idSessioneSSO, idSessioneASP); //Chiama un web service per recuparare lo username
					else
						tokenAuth = webCheckSessionSSO(idSessioneSSO, idSessioneASP); //Chiama una web page per recuparare lo username
					
					//Controlla se la username e' valida
					if (!NoE(tokenAuth) && !tokenAuth.startsWith("<AUTH>NO</AUTH>")) {
						
						request.getSession().setAttribute("TOKEN", tokenAuth);
						request.getSession().setAttribute("AUTH", validateXML);
						request.getSession().setAttribute("idSessioneSSO", idSessioneSSO);
						request.getSession().setAttribute("idSessioneASP", idSessioneASP);
						
						String token = GeneralUtils.base64Encode(tokenAuth.getBytes());
						
						if(!encryptionKey.equals("")){
							while(tokenAuth.length()%8 != 0 )
								tokenAuth += " ";
							token = GeneralUtils.base64Encode(GeneralUtils.cipher3DES(true,tokenAuth.getBytes(), encryptionKey.getBytes(), null, true));
						}
						
						String urlReferer = doc.getElementsByTagName("url_richiesta").item(0).getFirstChild().getNodeValue();

						String postRedirect = NETUtility.getPOSTRedirectPage(urlReferer, "token="+token);
						//String postRedirect="<html><head><script type=\"text/javascript\">function redirect() {document.myForm.submit();}</script></head><body OnLoad='redirect();'><FORM name=\"myForm\" method=\"POST\" action=\""+urlReferer+"\"><input type=\"hidden\" name=\"token\" value=\""+token+"\"><input type=\"submit\" value=\"Continue\"></FORM></body></html>";
						
						out.write(postRedirect);
						return;
					} else {
						out.write("Errore Autenticazione!\n<BR>TOKEN = <pre>" + tokenAuth + "</pre>");
					}
				}
				
				out.write("Errore Autenticazione!\n<BR>AUTH = <pre>" + validateXML + "</pre>");
				if(!NoE(urlIndex))		
					response.sendRedirect(urlIndex);
				return;
			} catch (Exception e) {
				out.write("Errore Servlet!\n<BR>" + e.getMessage());
				e.printStackTrace();
			}
		} else {
			//Compone l'xml
			validateXML = "<dsAuth xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://tempuri.org/Auth.xsd\"><auth><user /><id_sa /><id_sito>"+idsito+"</id_sito><esito_auth_sa /><id_sessione_sa /><id_sessione_aspnet_sa /><url_validate>"+urlValidate+"</url_validate><url_richiesta>"+urlRichiesta+"</url_richiesta><esito_auth_sso /><id_sessione_sso /><id_sessione_aspnet_sso /><stilesheet>"+additionalData+"</stilesheet></auth></dsAuth>";
			//Codifica l'xml in base64
			validateXML64=GeneralUtils.base64Encode(validateXML.getBytes());
			//Codifica il parametro come url
			encodedValidateXML64 = URLEncoder.encode(validateXML64, "UTF-8");
			//Redirige verso la pagina di autenticazione
			response.sendRedirect(urlCheck + "?auth=" + encodedValidateXML64);
			return;
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
