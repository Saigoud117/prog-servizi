package it.unicam.cs.cohesion.servlet.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class XMLUtility {

	
	@SuppressWarnings("unchecked")
	public static Document signXMLDocument(Document doc, String keystorePath, String keystoreType, String keystorePassword, String certificateAlias, String certificatePassword) throws Exception{

		XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

		Reference ref = fac.newReference("", fac.newDigestMethod(DigestMethod.SHA1, null), Collections.singletonList(fac.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null)), null, null);
		
		SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,(C14NMethodParameterSpec) null), fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null), Collections.singletonList(ref));

		KeyStore ks = KeyStore.getInstance(keystoreType);
		ks.load(new FileInputStream(keystorePath), keystorePassword.toCharArray());
		KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry (certificateAlias, new KeyStore.PasswordProtection(certificatePassword.toCharArray()));
		X509Certificate cert = (X509Certificate) keyEntry.getCertificate();

		KeyInfoFactory kif = fac.getKeyInfoFactory();
		@SuppressWarnings("rawtypes")
		List x509Content = new ArrayList();
		x509Content.add(cert.getSubjectX500Principal().getName());
		x509Content.add(cert);
		X509Data xd = kif.newX509Data(x509Content);
		KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));
		
		DOMSignContext dsc = new DOMSignContext(keyEntry.getPrivateKey(), doc.getDocumentElement());

		XMLSignature signature = fac.newXMLSignature(si, ki);

		signature.sign(dsc);
		
		return doc;
	}
	
	public static boolean verifySignature(Document doc , String pathCert) {
		//Questa procedura non funziona con java jdk1.6.xx Ritorna sempre false sulla validazione delle references
		try{
			if (doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature").getLength() == 0)
				throw new Exception("Cannot find Signature element");
	
			X509Certificate cert = (X509Certificate)CertificateFactory.getInstance("X.509").generateCertificate(new FileInputStream(new File(pathCert)));
			DOMValidateContext valContext = new DOMValidateContext(cert.getPublicKey(), doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature").item(0));
	
			XMLSignature signature = XMLSignatureFactory.getInstance("DOM").unmarshalXMLSignature(valContext);
			/*
			System.out.println(signature.getSignatureValue().validate(valContext));
			@SuppressWarnings("unchecked")
			List<Reference> refList = signature.getSignedInfo().getReferences();
			for(Reference ref:refList)
				System.out.println("Reference " + ref.getURI() + " : " + ref.validate(valContext));
			*/
			return signature.validate(valContext); 
		}catch(Exception e){ e.printStackTrace();}
		return false;
	}
	
	public static Document getXmlDocFromString(String xml) throws Exception{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		return dbf.newDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes()));
	}
	
	public static Document getXmlDocFromFile(String xmlFile) throws Exception{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		return dbf.newDocumentBuilder().parse(new File(xmlFile));
	}
	
	public static String getStringFromXmlDoc(org.w3c.dom.Node node) throws Exception{
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(node), new StreamResult(writer));
		return writer.getBuffer().toString().replaceAll("\n|\r", "");
    }
	
	public static Document createNewDocument() throws Exception{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		return dbf.newDocumentBuilder().newDocument();
	}
}
