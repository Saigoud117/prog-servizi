package util;

import java.io.*;
import java.math.*;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.persistence.*;

import org.openxava.jpa.*;
import org.openxava.model.meta.*;
import org.openxava.util.*;

import com.openxava.naviox.model.*;

import ProgBien.*;

public class ProgBienUtils {
	
	public static String CodiceInterno(String codiceInterno) {
		int codiceInternoLenght = codiceInterno.length();
		
		for (int i = 0; i < 5-codiceInternoLenght; i++) {
			codiceInterno = "0" + codiceInterno;
		}
		
		return codiceInterno;
	}
	
	 @SuppressWarnings("unchecked")
	public static List<Inquadrato> getUserInquadrato(String cf) {
		 Date data = new Date();
		 Query query = XPersistence.getManager().createQuery("from Inquadrato i where i.dipendenti.cf like :cf and i.datainizio <= :data and i.datafine >= :data");			      
			    query.setParameter("cf", cf);
			    query.setParameter("data", data);			    
			    List<Inquadrato> i = null;
			    try {
			    	i = (List<Inquadrato>)query.getResultList();
			    }
			    catch (NoResultException nre) {
			    	//Ignore this because as per your logic this is ok!
			    	}
			    return i;
			    /*
			     
			    	return i.getServizi().getServizio();
			    else
			    	return "%%";
			    */
	 }
	
	public static boolean checkSelfSignUp() {
		User user = User.find(Users.getCurrent());
		return user.hasRole("self sign up");
	}
	
	public static boolean checkAdmin() {
		User user = User.find(Users.getCurrent());
		return user.hasRole("admin");
	}
	
	public static boolean checkReferenteServizio() {
		User user = User.find(Users.getCurrent());
		return user.hasRole("Referente di servizio");
	}
	
	public static int getSerialeFromCui(String cui) {
		String c = cui.substring(16).replaceFirst("^0+(?!$)", "");
		return Integer.parseInt(c);
	}
	
	public static List<Dipendenti> getNotificationUsersByType(String tipo){
		TicketConfigurazioneNotifiche TicketConfigurazioneNotifiche = (TicketConfigurazioneNotifiche)XPersistence.getManager()
				    .createQuery(
				        "from TicketConfigurazioneNotifiche t where t.tipo.descrizione like :descrizione")  // JPQL query
				    .setParameter("descrizione", tipo)
				    .getSingleResult();
		
		List<Dipendenti> utenti = new ArrayList<Dipendenti>();
		
		for (TicketConfigurazioneNotificheDettaglio d: TicketConfigurazioneNotifiche.getTicketConfigurazioneNotificheDettaglio()) {
			utenti.add(d.getUtente());
		}
		
		return utenti;
	}
	
	public static List<Dipendenti> getNotificationUsersByRole(String servizio){
		
		@SuppressWarnings("unchecked")
		List<Inquadrato> referenti = (List<Inquadrato>)XPersistence.getManager()
				    .createQuery(
				        "from Inquadrato i where i.servizi.servizio = :servizio and i.profilo.key = 'A02'")  // JPQL query
				    .setParameter("servizio", servizio)
				    .getResultList();
		
		List<Dipendenti> utenti = new ArrayList<Dipendenti>();
		
		for (Inquadrato i: referenti) {
			utenti.add(i.getDipendenti());
		}
		
		return utenti;
	}
	
	public static BigDecimal getGdlTotScaglioniEccedenza(List<SogliaScaglioni> scaglioni, BigDecimal imporboBaseAsta) {
		BigDecimal tot = new BigDecimal(0);
		
		boolean hasEccedenza = false;
		for (SogliaScaglioni s: scaglioni) {
    		if ((imporboBaseAsta.compareTo(s.getValoreDa()) == -1 && imporboBaseAsta.compareTo(s.getValoreA()) == 1) && !hasEccedenza) {
    			if (s.isSoloRimanenza()) {
    				hasEccedenza = true;
    				BigDecimal eccedenza = imporboBaseAsta.subtract(s.getValoreA());
    				BigDecimal totEccedenza = eccedenza.multiply(s.getPercentScaglione());
    				tot = tot.add(totEccedenza);
    			}
    			else
    				tot = imporboBaseAsta.multiply(s.getPercentScaglione());
    		} 
    		else if (hasEccedenza) {
    			tot = tot.add(s.getValoreDa().add(new BigDecimal(0.01)).multiply(s.getPercentScaglione()));
    		}
    	}
		
		return tot;
	}
	
	public static SogliaImporti getSogliaImporti(Date data) {
		/*
		SogliaImporti soglia = (SogliaImporti)XPersistence.getManager()
			    .createQuery(
			        "from SogliaImporti t where t.datainizio <= :data and t.datafine >= :data")  // JPQL query
			    .setParameter("data", new java.sql.Date(data.getTime()))
			    .getSingleResult();
		return soglia;
		*/
		
		// An example of using JDBC
		 java.sql.Connection con = null;
		 try {
			 con = DataSourceConnectionProvider.getByComponent("SogliaImporti").getConnection(); // 1
			 String table = MetaModel.get("SogliaImporti").getMapping().getTable();
			 java.sql.PreparedStatement ps = con.prepareStatement("select * from " + table +
			 " where datainizio <= ? and datafine >= ?");
			 ps.setDate(1, new java.sql.Date(data.getTime()));
			 ps.setDate(2, new java.sql.Date(data.getTime()));
			 java.sql.ResultSet rs = ps.executeQuery();
			 //rs.next();
			 SogliaImporti result = new SogliaImporti();
			 while(rs.next()) {
				 result.setOid(rs.getString("oid"));
				 result.setDatainizio(rs.getDate("datainizio"));
				 result.setDatafine(rs.getDate("datafine"));
				 result.setDeleted(rs.getBoolean("deleted"));
				 result.setPercentAffidamento(rs.getBigDecimal("percentaffidamento"));
				 result.setPercentEsecuzione(rs.getBigDecimal("percentesecuzione"));
				 result.setPercentIncentivi(rs.getBigDecimal("percentincentivi"));
				 result.setPercentInnovazione(rs.getBigDecimal("percentinnovazione"));
				 result.setPercentProgrammazione(rs.getBigDecimal("percentprogrammazione"));
				 result.setPercentCollaudo(rs.getBigDecimal("percentcollaudo"));
				} 
			 
			 ps.close();
			 return result;
		 }
		 catch (Exception ex) {
			 // You can throw any runtime exception here
			 throw new SystemException(ex);
		 }
		 finally {
			 try {
				 con.close();
			 }
			 catch (Exception ex) {
				 
			 }
		 }
	}
	
	public static List<SogliaScaglioni> getSogliaScaglioni(String oid) {	
		// An example of using JDBC
		 java.sql.Connection con = null;
		 try {
			 con = DataSourceConnectionProvider.getByComponent("SogliaScaglioni").getConnection(); // 1
			 String table = MetaModel.get("SogliaScaglioni").getMapping().getTable();
			 java.sql.PreparedStatement ps = con.prepareStatement("select * from " + table +
			 " where soglia_oid = ?");
			 ps.setString(1, oid);
			 java.sql.ResultSet rs = ps.executeQuery();
			 //rs.next();
			 List<SogliaScaglioni> result = new ArrayList<SogliaScaglioni>();
			 while(rs.next()) {
				 SogliaScaglioni s = new SogliaScaglioni();
				 s.setOid(rs.getString("oid"));
				 if (rs.getObject("valoreda") == null)
					 s.setValoreDa(new BigDecimal(Double.MAX_VALUE));
				 else
					 s.setValoreDa(rs.getBigDecimal("valoreda"));
				 if (rs.getObject("valorea") == null)
					 s.setValoreA(new BigDecimal(Double.MIN_VALUE));
				 else
					 s.setValoreA(rs.getBigDecimal("valorea"));
				 s.setPercentScaglione(rs.getBigDecimal("percentscaglione"));
				 s.setSoloRimanenza(rs.getBoolean("solorimanenza"));
				 result.add(s);
				} 
			 
			 ps.close();
			 return result;
		 }
		 catch (Exception ex) {
			 // You can throw any runtime exception here
			 throw new SystemException(ex);
		 }
		 finally {
			 try {
				 con.close();
			 }
			 catch (Exception ex) {
				 
			 }
		 }
	}
	
	/*
	public static BigDecimal getSogliaImporti(Date data) {
		 // An example of using JDBC
		 java.sql.Connection con = null;
		 try {
			 con = DataSourceConnectionProvider.getByComponent("SogliaImporti").getConnection(); // 1
			 String table = MetaModel.get("SogliaImporti").getMapping().getTable();
			 java.sql.PreparedStatement ps = con.prepareStatement("select valore from " + table +
			 " where datainizio <= ? and datafine >= ?");
			 ps.setDate(1, new java.sql.Date(data.getTime()));
			 ps.setDate(2, new java.sql.Date(data.getTime()));
			 java.sql.ResultSet rs = ps.executeQuery();
			 rs.next();
			 BigDecimal result = rs.getBigDecimal(1);
			 ps.close();
			 return result;
		 }
		 catch (Exception ex) {
			 // You can throw any runtime exception here
			 throw new SystemException(ex);
		 }
		 finally {
			 try {
				 con.close();
			 }
			 catch (Exception ex) {
				 
			 }
		 }
	}
	*/
	
	public static boolean checkTicketConfigurazione(TipologiaTicket tipo) {
		boolean trovato = false;
		try {
			TicketConfigurazioneNotifiche t = (TicketConfigurazioneNotifiche)XPersistence.getManager()
				    .createQuery(
				        "from TicketConfigurazioneNotifiche t where t.tipo.oid = :tipo")  // JPQL query
				    .setParameter("tipo", tipo.getOid())
				    .getSingleResult();
			if(t != null)
				trovato = true;
		} catch (Exception ex) {
			trovato = false;
		}
		return trovato;
	}
	
	public static Dipendenti checkDipendenteExist(String cf) {
		try {
			Dipendenti d = (Dipendenti)XPersistence.getManager()
				    .createQuery(
				        "from Dipendenti d where d.cf = :cf")  // JPQL query
				    .setParameter("cf", cf)
				    .getSingleResult();
			if(d != null)
				return d;
			else
				return null;
		} catch (Exception ex) {
			return null;
		}
	}
	
	public static int getCronoprogramma(int anno, Date data, String fase) {
		 // An example of using JDBC
		 java.sql.Connection con = null;
		 try {
			 con = DataSourceConnectionProvider.getByComponent("Cronoprogramma").getConnection(); // 1
			 String table = MetaModel.get("Cronoprogramma").getMapping().getTable();
			 
			 String inizio = "datainiziofase".concat(fase.toLowerCase());
			 String fine = "datafinefase".concat(fase.toLowerCase());
			 
			 java.sql.PreparedStatement ps = con.prepareStatement("select count(*) from " + table +
			 " where anno = ? and " + inizio + " <= ? and " + fine + " >= ?");
			 ps.setInt(1, anno);
			 ps.setTimestamp(2, new java.sql.Timestamp(data.getTime()));
			 ps.setTimestamp(3, new java.sql.Timestamp(data.getTime()));
			 java.sql.ResultSet rs = ps.executeQuery();
			 rs.next();
			 int result = rs.getInt(1);
			 ps.close();
			 return result;
		 }
		 catch (Exception ex) {
			 // You can throw any runtime exception here
			 throw new SystemException(ex);
		 }
		 finally {
			 try {
				 con.close();
			 }
			 catch (Exception ex) {
				 
			 }
		 }
	}
	
	public static int getCronoprogrammaStraordinario(int anno, Date data, String fase, String faseRiferimento) {
		 // An example of using JDBC
		 java.sql.Connection con = null;
		 try {
			 con = DataSourceConnectionProvider.getByComponent("Cronoprogramma").getConnection(); // 1
			 String table = MetaModel.get("Cronoprogramma").getMapping().getTable();
			 
			 String inizio = "datainiziofase".concat(fase.toLowerCase());
			 String fine = "datafinefase".concat(fase.toLowerCase());
			 
			 int fr = -1;
			 if (faseRiferimento.equals("A"))
				 fr = 0;
			 else if (faseRiferimento.equals("B"))
				 fr = 1;
			 else if (faseRiferimento.equals("C"))
				 fr = 2;
			 
			 java.sql.PreparedStatement ps = con.prepareStatement("select count(*) from " + table +
			 " where anno = ? and " + inizio + " <= ? and " + fine + " >= ? and faseriferimentostraordinaria = ?");
			 ps.setInt(1, anno);
			 ps.setTimestamp(2, new java.sql.Timestamp(data.getTime()));
			 ps.setTimestamp(3, new java.sql.Timestamp(data.getTime()));
			 ps.setInt(4, fr);
			 java.sql.ResultSet rs = ps.executeQuery();
			 rs.next();
			 int result = rs.getInt(1);
			 ps.close();
			 return result;
		 }
		 catch (Exception ex) {
			 // You can throw any runtime exception here
			 throw new SystemException(ex);
		 }
		 finally {
			 try {
				 con.close();
			 }
			 catch (Exception ex) {
				 
			 }
		 }
	}
	
	public static int checkListaRup(int anno) {
		 // An example of using JDBC
		 java.sql.Connection con = null;
		 try {
			 con = DataSourceConnectionProvider.getByComponent("CronoprogrammaDettaglioFaseStraodinaria").getConnection(); // 1
			 String table = MetaModel.get("CronoprogrammaDettaglioFaseStraodinaria").getMapping().getTable();
			 
			 java.sql.PreparedStatement ps = con.prepareStatement("select count(*) from " + table +
			 " where cronoprogramma_anno = ?");
			 ps.setInt(1, anno);
			 java.sql.ResultSet rs = ps.executeQuery();
			 rs.next();
			 int result = rs.getInt(1);
			 ps.close();
			 return result;
		 }
		 catch (Exception ex) {
			 // You can throw any runtime exception here
			 throw new SystemException(ex);
		 }
		 finally {
			 try {
				 con.close();
			 }
			 catch (Exception ex) {
				 
			 }
		 }
	}
	
	public static int checkRup(int anno, String rup) {
		 // An example of using JDBC
		 java.sql.Connection con = null;
		 try {
			 con = DataSourceConnectionProvider.getByComponent("CronoprogrammaDettaglioFaseStraodinaria").getConnection(); // 1
			 String table = MetaModel.get("CronoprogrammaDettaglioFaseStraodinaria").getMapping().getTable();
			 
			 java.sql.PreparedStatement ps = con.prepareStatement("select count(*) from " + table +
			 " where cronoprogramma_anno = ? and cf = ?");
			 ps.setInt(1, anno);
			 ps.setString(2, rup);
			 java.sql.ResultSet rs = ps.executeQuery();
			 rs.next();
			 int result = rs.getInt(1);
			 ps.close();
			 return result;
		 }
		 catch (Exception ex) {
			 // You can throw any runtime exception here
			 throw new SystemException(ex);
		 }
		 finally {
			 try {
				 con.close();
			 }
			 catch (Exception ex) {
				 
			 }
		 }
	}
	
	public static boolean getCronoprogrammaFaseStraordinaria(int anno, Date data, String cfRup, String faseRiferimento) {
		boolean trovato = false;
		boolean listaRup = false;
		boolean hasRup = false;
		boolean ok = false;
		try {
			int a = getCronoprogrammaStraordinario(anno, data, "straordinaria", faseRiferimento);
			int l = checkListaRup(anno);
			/*
			Cronoprogramma c = (Cronoprogramma)XPersistence.getManager()
				    .createQuery(
				        "from Cronoprogramma c where c.anno = :anno and c.dataInizioFaseStraordinaria <= :data and c.dataFineFaseStraordinaria >= :data")  // JPQL query
				    .setParameter("anno", anno)
				    .setParameter("data", data)
				    .getSingleResult();
			*/
			if (a > 0)
				trovato = true;
			if (a > 0 && l > 0)
			{ 
				listaRup = true;
				int r = checkRup(anno, cfRup);
				if (r > 0)
					hasRup = true;
			}
			
			if (trovato && !listaRup)
				ok = true;
			else if (trovato && listaRup && hasRup)
				ok = true;
				
		} catch (Exception ex) {
			ok = false;
		}
		return ok;
	}
	
	public static boolean ControllaFase(int anno, Date data, String fase) {	
		boolean ok = true;
		
		//if (!checkAdmin() && getCronoprogramma(anno, data, fase) == 0)
		if (getCronoprogramma(anno, data, fase) == 0)
		{
			ok = false;
		}
		return ok;
	}
	
	public static boolean ControllaFaseStraordinaria(int anno, Date data, String username, String faseRiferimento) {	
		boolean ok = true;
		
		//if (!checkAdmin() && !getCronoprogrammaFaseStraordinaria(anno, data, username, faseRiferimento))
		if (!checkAdmin() && !getCronoprogrammaFaseStraordinaria(anno, data, username, faseRiferimento))
		{
			ok = false;
		}
		return ok;
	}
	
	public static boolean ControllaServizioStruttura(String idServizio) {	
		boolean ok = true;
		
		Date data = new Date();
		
		 // An example of using JDBC
		 java.sql.Connection con = null;
		 try {
			 con = DataSourceConnectionProvider.getByComponent("Servizi").getConnection(); // 1
			 String table = MetaModel.get("Servizi").getMapping().getTable();
			 
			 java.sql.PreparedStatement ps = con.prepareStatement("select count(*) from " + table +
			 " where oid = ? and datainizio <= ? and datafine >= ?");
			 ps.setString(1, idServizio);
			 ps.setDate(2, new java.sql.Date(data.getTime()));
			 ps.setDate(3, new java.sql.Date(data.getTime()));
			 java.sql.ResultSet rs = ps.executeQuery();
			 rs.next();
			 int result = rs.getInt(1);
			 ps.close();
			 if (result == 0)
				 ok = false;
		 }
		 catch (Exception ex) {
			 // You can throw any runtime exception here
			 throw new SystemException(ex);
		 }
		 finally {
			 try {
				 con.close();
			 }
			 catch (Exception ex) {
				 
			 }
		 }
		return ok;
	}
	
	public static void sendNotifyEmail(boolean isNew, boolean isWorking, boolean isClosed, boolean isReopened, String type, String creatore, String gestore, String seriale) throws AddressException, UnsupportedEncodingException, MessagingException {
		String mailToCreator = "";
		String mailToAdmin = "";
		String mailToGestore = "";
		
		if (isNew) {
			mailToCreator = "La sua richiesta di assistenza n. ".concat(seriale).concat(" � stata correttamente creata, la informeremo quando verr� visionata dall'ufficio competente.").concat("<br />").concat("<br />").concat("------").concat("<br />").concat("<br />").concat("<i>Non rispondere a questo messaggio, in quanto � stato generato automaticamente dal sistema e la casella di posta elettronica associata al mittente non � presidiata.</i>");
			mailToAdmin = "� stata creata una nuova richiesta di assisteza n. ".concat(seriale).concat(" , la invitiamo a prenderne visione su http://prog-servizi.regionemarche.intra/.").concat("<br />").concat("<br />").concat("------").concat("<br />").concat("<br />").concat("<i>Non rispondere a questo messaggio, in quanto � stato generato automaticamente dal sistema e la casella di posta elettronica associata al mittente non � presidiata.</i>");;
		}
		
		if (isWorking) {
			mailToCreator = "La sua richiesta di assistenza n. ".concat(seriale).concat(" � in elaborazione, le comunicheremo quando sar� chiusa.").concat("<br />").concat("<br />").concat("------").concat("<br />").concat("<br />").concat("<i>Non rispondere a questo messaggio, in quanto � stato generato automaticamente dal sistema e la casella di posta elettronica associata al mittente non � presidiata.</i>");
			mailToGestore = "Le � stata associata la richiesta di assistenza n. ".concat(seriale).concat(" ora in stato di elaborazione, la preghiamo di visionarla e dare risposta all'utente.").concat("<br />").concat("<br />").concat("------").concat("<br />").concat("<br />").concat("<i>Non rispondere a questo messaggio, in quanto � stato generato automaticamente dal sistema e la casella di posta elettronica associata al mittente non � presidiata.</i>");
		}
		
		if (isClosed) {
			mailToCreator = "La sua richiesta di assistenza n. ".concat(seriale).concat(" � stata chiusa, la invitiamo a consultare la risposta su http://prog-servizi.regionemarche.intra/.").concat("<br />").concat("<br />").concat("------").concat("<br />").concat("<br />").concat("<i>Non rispondere a questo messaggio, in quanto � stato generato automaticamente dal sistema e la casella di posta elettronica associata al mittente non � presidiata.</i>");
		}
		
		if (isReopened) {
			mailToAdmin = "La richiesta di assistenza n. ".concat(seriale).concat(" � stata riaperta, la invitiamo a prenderne visione su http://prog-servizi.regionemarche.intra/.").concat("<br />").concat("<br />").concat("------").concat("<br />").concat("<br />").concat("<i>Non rispondere a questo messaggio, in quanto � stato generato automaticamente dal sistema e la casella di posta elettronica associata al mittente non � presidiata.</i>");
		}
		
		if (isNew) {
			Emails.send("no-reply@regione.marche.it", "Help desk prog-servizi", Users.getCurrentUserInfo().getEmail(), "Ticket prog-servizi".concat(" ").concat(seriale).concat(" ").concat(" - ").concat(creatore), mailToCreator);
		}
		
		if (isWorking || isClosed) {
			Emails.send("no-reply@regione.marche.it", "Help desk prog-servizi", User.find(creatore).getEmail(), "Ticket prog-servizi".concat(" ").concat(seriale).concat(" ").concat(" - ").concat(creatore), mailToCreator);
			if (isWorking)
				Emails.send("no-reply@regione.marche.it", "Help desk prog-servizi", User.find(gestore).getEmail(), "Ticket prog-servizi".concat(" ").concat(seriale).concat(" ").concat(" - ").concat(creatore), mailToGestore);
		}
		
		if (isNew || isReopened) {
			List<Dipendenti> utenti = util.ProgBienUtils.getNotificationUsersByType(type);
			for(Dipendenti d: utenti) {
				Emails.send("no-reply@regione.marche.it", "Help desk prog-servizi", User.find(d.getCf()).getEmail(), "Ticket prog-servizi".concat(" ").concat(seriale).concat(" ").concat(" - ").concat(creatore), mailToAdmin);
			}
		}
	}
	
	public static void sendNotifyEmailAbilitazione(String type, String creatore, String emailPrimaAbilitazione, String servizio, String descrizioneServizio) throws AddressException, UnsupportedEncodingException, MessagingException {
		
		String contentReferente = "";
		String contentMittente = "";
		String contentDestinatario = "";
		
		if (type.equals("Richiesta")) {
			contentReferente = "Ha ricevuto una nuova richiesta di abilitazione per la struttura ".concat(descrizioneServizio).concat(", la invitiamo a consultarla su http://prog-servizi.regionemarche.intra.").concat("<br />").concat("<br />").concat("------").concat("<br />").concat("<br />").concat("<i>Non rispondere a questo messaggio, in quanto � stato generato automaticamente dal sistema e la casella di posta elettronica associata al mittente non � presidiata.</i>");
			contentMittente = "Ha inviato una nuova richiesta di abilitazione per la piattaforma http://prog-servizi.regionemarche.intra. L'eventuale abilitazione verr� confermata tramite email.".concat("<br />").concat("<br />").concat("------").concat("<br />").concat("<br />").concat("<i>Non rispondere a questo messaggio, in quanto � stato generato automaticamente dal sistema e la casella di posta elettronica associata al mittente non � presidiata.</i>");
		}
		
		if (type.equals("Abilitazione")) {
			contentDestinatario = "La informiamo che � stato abilitato ad operare nella piattaforma http://prog-servizi.regionemarche.intra per la struttura ".concat(descrizioneServizio).concat(".").concat("<br />").concat("<br />").concat("------").concat("<br />").concat("<br />").concat("<i>Non rispondere a questo messaggio, in quanto � stato generato automaticamente dal sistema e la casella di posta elettronica associata al mittente non � presidiata.</i>");
		}
		
		if (type.equals("Richiesta")) {
			List<Dipendenti> utenti = util.ProgBienUtils.getNotificationUsersByRole(servizio);
			for(Dipendenti d: utenti) {
				Emails.send("no-reply@regione.marche.it", "Nuova abilitazione prog-servizi", User.find(d.getCf()).getEmail(), "Richiesta abilitazione".concat(" - ").concat(creatore), contentReferente);
			}
			
			Emails.send("no-reply@regione.marche.it", "Nuova abilitazione prog-servizi", emailPrimaAbilitazione, "Richiesta abilitazione".concat(" - ").concat(creatore), contentMittente);
		}
		
		if (type.equals("Abilitazione")) {
			Emails.send("no-reply@regione.marche.it", "Nuova abilitazione prog-servizi", User.find(creatore).getEmail() == null ? emailPrimaAbilitazione : User.find(creatore).getEmail(), "Nuova abilitazione".concat(" - ").concat(creatore), contentDestinatario);
		}
	}
}
