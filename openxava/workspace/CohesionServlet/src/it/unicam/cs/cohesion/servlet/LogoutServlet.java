package it.unicam.cs.cohesion.servlet;

import it.unicam.cs.cohesion.servlet.utils.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Element;

public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String wsSso="";
	private String webSso="";
	private String idsito="TEST";
	private String keystorePath = "";
    private String keystoreType = "";
    private String pwdKeystore = "";
    private String aliasCertificate = "";
    private String pwdCertificate = "";
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	    
		//Imposto le variabili locali con i parametri di configurazione
		keystorePath = config.getInitParameter("keystorePath");
		keystoreType = config.getInitParameter("keystoreType");
		pwdKeystore = config.getInitParameter("pwdKeystore");
		aliasCertificate = config.getInitParameter("aliasCertificate");
		pwdCertificate = config.getInitParameter("pwdCertificate");
		wsSso = config.getInitParameter("wsSso");
		webSso = config.getInitParameter("webSso");

		if((NoE(wsSso) || NoE(keystorePath) || NoE(keystoreType) || NoE(pwdKeystore) || NoE(aliasCertificate) || NoE(pwdCertificate)) && NoE(webSso))	
			throw new ServletException("Errore nella configurazione della servlet; applicazione non inizializzata correttamente.");
	}

	private boolean NoE(String dataToTest){
		return dataToTest==null || dataToTest.trim()=="";
	}
	
	private String wsLogoutSito(String idSessioneSSO, String idSessioneASP) throws Exception{
		String ret = "";
		
		MySOAPClient mySOAPClient = new MySOAPClient(wsSso);
		
		ArrayList<String[]> parameterList= new ArrayList<String[]>();
		parameterList.add(new String[]{"IdSessioneSSO",idSessioneSSO});
		parameterList.add(new String[]{"IdSessioneASPNET",idSessioneASP});
		
		mySOAPClient.createEnvelope("http://tempuri.org/", "LogoutSito", parameterList);
		mySOAPClient.setSOAPAction("http://tempuri.org/LogoutSito");
		
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
		
		ret = XMLUtility.getXmlDocFromString(mySOAPClient.getWsResponseBodyEnvelope()).getElementsByTagNameNS("http://tempuri.org/", "LogoutSitoResult").item(0).getTextContent();
		
		return ret;
	}
	
	private String webLogoutSito(String idSessioneSSO, String idSessioneASP) throws Exception{
		String ret = "";
		
		String dataToSend = "Operation=LogoutSito&IdSessioneSSO=" + idSessioneSSO + "&IdSessioneASPNET=" + idSessioneASP;;
		ret = new String(NETUtility.sendHTTPPOST(webSso, dataToSend, null, false, false));
		
		return ret;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		response.setContentType("text/html");
		
		try{
			String idSessioneASP = "", idSessioneSSO ="";
			if(!NoE((String)request.getSession().getAttribute("idSessioneASP")) && !NoE((String)request.getSession().getAttribute("idSessioneSSO"))){
				idSessioneASP = (String)request.getSession().getAttribute("idSessioneASP");
				idSessioneSSO = (String)request.getSession().getAttribute("idSessioneSSO");
			}
			if(NoE(webSso))
				wsLogoutSito(idSessioneSSO, idSessioneASP);
			else
				webLogoutSito(idSessioneSSO, idSessioneASP);
			
			request.getSession().removeAttribute("idSessioneASP");
			request.getSession().removeAttribute("idSessioneSSO");
			request.getSession().removeAttribute("TOKEN");
			request.getSession().removeAttribute("AUTH");
			
			if(request.getParameter("ReturnUrl") != null)
				if(request.getParameter("ReturnUrl").length() != 0)
					response.sendRedirect(request.getParameter("ReturnUrl"));
		}catch(Exception e){
			out.write("Errore Servlet!\n<BR>" + e.getMessage());
			e.printStackTrace();
		};
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
