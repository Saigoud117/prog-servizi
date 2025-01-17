package actions;
 
import java.util.*;

import javax.persistence.*;

import org.openxava.actions.*;
import org.openxava.jpa.*;
import org.openxava.util.jxls.*;
import org.openxava.web.servlets.*;

import EsportazioneMitProg.*;
import ProgBien.*;
import util.*;

public class ExcelExport extends ViewBaseAction
implements IForwardAction, JxlsConstants {                                                             // 1
 
    private String forwardURI = null;
 
    public void execute() throws Exception {
        try {
        	@SuppressWarnings("unchecked")
    		List<Procedure> ProcedureFabbisogno = (List<Procedure>)XPersistence.getManager()
    			    .createQuery(
    			    		"from Procedure p where p.stato.key in ('M', 'N', 'Z') and p.archived = false and p.deleted = false and (p.valorestimatoappalto >= 40000.00 or (p.valorestimatoappalto < 40000.00 and p.ultimopianoapprovato is not null)) order by p.codiceinterno ASC")  // JPQL query
    			    .getResultList();
        	
        	// fix per le due sotto soglia da modifiche piano pubblicato
        	@SuppressWarnings("unchecked")
    		List<ProcedureProgramma> ProcedureModifiche = (List<ProcedureProgramma>)XPersistence.getManager()
    			    .createQuery(
    			    	"from ProcedureProgramma p where p.stato.key in ('M', 'N', 'Z') and p.cancellazione = false and p.archived = false and p.deleted = false and (p.valorestimatoappalto >= 40000.00 or (p.valorestimatoappalto < 40000.00 and p.ultimopianoapprovato is not null and p.cui <> '~MINORI-SOGLIA-40K~')) order by p.oid ASC")  // JPQL query
    			    .getResultList();
        	
        	/*
        	@SuppressWarnings("unchecked")
    		List<ProcedureProgramma> ProcedureModifiche = (List<ProcedureProgramma>)XPersistence.getManager()
    			    .createQuery(
    			        "from ProcedureProgramma p where p.stato.key in ('M', 'N', 'Z') and p.cancellazione = false and p.archived = false and p.deleted = false and p.valorestimatoappalto >= 40000.00 order by p.oid ASC")  // JPQL query
    			    .getResultList();
    		*/
    		
    		@SuppressWarnings("unchecked")
    		List<ProcedureDefinitive> ProcedureConfermate = (List<ProcedureDefinitive>)XPersistence.getManager()
    			    .createQuery(
    			    		"from ProcedureDefinitive p where p.stato.key = 'C' and p.archived = false and p.deleted = false and (p.valorestimatoappalto >= 40000.00 or (p.valorestimatoappalto < 40000.00 and p.cui <> '~MINORI-SOGLIA-40K~'))")  // JPQL query
    			    .getResultList();
    		
    		@SuppressWarnings("unchecked")
    		List<Procedure> ProcedureNonRiproposte = (List<Procedure>)XPersistence.getManager()
    			    .createQuery(
    			        "from Procedure p where p.stato.key = 'K' and p.deleted = false and p.archived = false and p.valorestimatoappalto >= 40000.00")  // JPQL query
    			    .getResultList();
    		
    		@SuppressWarnings("unchecked")
    		List<ProcedureProgramma> ProcedureCancellate = (List<ProcedureProgramma>)XPersistence.getManager()
    			    .createQuery(
    			        "from ProcedureProgramma p where p.stato.key = 'M' and p.archived = false and p.deleted = false and p.cancellazione = true and p.ultimopianoapprovato is not null")  // JPQL query
    			    .getResultList();
    		
    		@SuppressWarnings("unchecked")
    		List<ProcedureDefinitive> ProcedureCancellateAggiornamentiAnnuali = (List<ProcedureDefinitive>)XPersistence.getManager()
    			    .createQuery(
    			        "from ProcedureDefinitive p where p.stato.key = 'K' and p.archived = false and p.deleted = false")  // JPQL query
    			    .getResultList();
    		
    		Pubblicazione ent = (Pubblicazione)getView().getEntity();
            EntityManager em = XPersistence.getManager();   
            ent = em.merge(ent);
            
            PubblicazioneFornitureServizi p = new PubblicazioneFornitureServizi();
            
            if (!ent.isAggiornamento())
    	        p = ProgrammaBiennaleExport.getDatiPubblicazione(ent, ProcedureFabbisogno, ProcedureConfermate, ProcedureNonRiproposte, ProcedureCancellateAggiornamentiAnnuali);
            else
            	p = ProgrammaBiennaleExport.getDatiModifichePubblicazioneEstrazione(ent, ProcedureModifiche, ProcedureConfermate, ProcedureCancellate);
        	
            //PubblicazioneFornitureServizi p = ProgrammaBiennaleExport.getDatiPubblicazione(ent, ProcedureFabbisogno, ProcedureConfermate, ProcedureNonRiproposte);
    			
            JxlsWorkbook tracciato;
            
            if (!ent.isAggiornamento()) {
	            tracciato = createTracciato(p);
            }
            else {
            	tracciato = createTracciatoModifiche(p);
            }
            getRequest().getSession().setAttribute(ReportXLSServlet.SESSION_XLS_REPORT, tracciato);   // 2
            setForwardURI("/xava/report.xls?time=" + System.currentTimeMillis()); 
        } catch (Exception e) {
            addError(e.getMessage());
        }
    }
    
    private JxlsWorkbook createTracciatoModifiche(PubblicazioneFornitureServizi p) throws Exception {
    	JxlsWorkbook tracciatoWB = new JxlsWorkbook("TracciatoFornitureServizi");                       // 4
        JxlsSheet programma = tracciatoWB.addSheet("Programma");
        
        programma.setValue(1, 1, "Tabella");
        programma.setValue(1, 2, "Programma");
        programma.setValue(2, 1, "id");
        programma.setValue(2, 2, p.getId());
        programma.setValue(3, 1, "codiceFiscaleSA");
        programma.setValue(3, 2, p.getCodiceFiscaleSA());
        programma.setValue(4, 1, "Ufficio");
        programma.setValue(4, 2, p.getUfficio());
        programma.setValue(5, 1, "anno");
        programma.setValue(5, 2, p.getAnno());
        programma.setValue(6, 1, "descrizione");
        programma.setValue(6, 2, p.getDescrizione());
        programma.setValue(7, 1, "numeroApprovazione");
        programma.setValue(7, 2, p.getNumeroApprovazione());
        programma.setValue(8, 1, "dataApprovazione");
        programma.setValue(8, 2, p.getDataApprovazione());
        programma.setValue(9, 1, "dataPubblicazioneApprovazione");
        programma.setValue(9, 2, p.getDataPubblicazioneApprovazione());
        programma.setValue(10, 1, "titoloAttoApprovazione");
        programma.setValue(10, 2, p.getTitoloAttoApprovazione());
        programma.setValue(11, 1, "urlAttoApprovazione");
        programma.setValue(11, 2, p.getUrlAttoApprovazione());
        programma.setValue(12, 1, "idRicevuto");
        programma.setValue(12, 2, p.getIdRicevuto());
           
        JxlsSheet referente = tracciatoWB.addSheet("Referente");
        referente.setValue(1, 1, "Tabella");
        referente.setValue(1, 2, "Referente");
        referente.setValue(2, 1, "cognome");
        referente.setValue(2, 2, p.getReferente().getCognome());
        referente.setValue(3, 1, "nome");
        referente.setValue(3, 2, p.getReferente().getNome());
        referente.setValue(4, 1, "cfPiva");
        referente.setValue(4, 2, p.getReferente().getCfPiva());
        referente.setValue(5, 1, "indirizzo");
        referente.setValue(5, 2, p.getReferente().getIndirizzo());
        referente.setValue(6, 1, "civico");
        referente.setValue(6, 2, p.getReferente().getCivico());
        referente.setValue(7, 1, "provincia");
        referente.setValue(7, 2, p.getReferente().getProvincia());
        referente.setValue(8, 1, "cap");
        referente.setValue(8, 2, p.getReferente().getCap());
        referente.setValue(9, 1, "luogoIstat");
        referente.setValue(9, 2, p.getReferente().getLuogoIstat());
        
        JxlsSheet tracciato = tracciatoWB.addSheet("Forniture Servizi");
        tracciato.setValue(1, 1, "Tabella");
        tracciato.setValue(2, 1, "cui");
        tracciato.setValue(3, 1, "settore");
        tracciato.setValue(4, 1, "codiceInterno");
        tracciato.setValue(5, 1, "descrizione");
        tracciato.setValue(6, 1, "anno");
        tracciato.setValue(7, 1, "esenteCup");
        tracciato.setValue(8, 1, "cup");
        tracciato.setValue(9, 1, "acquistoRicompreso");
        tracciato.setValue(10, 1, "cuiCollegato");
        tracciato.setValue(11, 1, "cpv");
        tracciato.setValue(12, 1, "nuts");
        tracciato.setValue(13, 1, "quantita");
        tracciato.setValue(14, 1, "unitaMisura");
        tracciato.setValue(15, 1, "priorita");
        tracciato.setValue(16, 1, "lottoFunzionale");
        tracciato.setValue(17, 1, "durataInMesi");
        tracciato.setValue(18, 1, "nuovoAffidamento");
        tracciato.setValue(19, 1, "risorseVincolatePerLegge1");
        tracciato.setValue(20, 1, "risorseVincolatePerLegge2");
        tracciato.setValue(21, 1, "risorseVincolatePerLeggeSucc");
        tracciato.setValue(22, 1, "risorseMutuo1");
        tracciato.setValue(23, 1, "risorseMutuo2");
        tracciato.setValue(24, 1, "risorseMutuoSucc");
        tracciato.setValue(25, 1, "risorsePrivati1");
        tracciato.setValue(26, 1, "risorsePrivati2");
        tracciato.setValue(27, 1, "risorsePrivatiSucc");
        tracciato.setValue(28, 1, "risorseBilancio1");
        tracciato.setValue(29, 1, "risorseBilancio2");
        tracciato.setValue(30, 1, "risorseBilancioSucc");
        tracciato.setValue(31, 1, "risorseArt3_1");
        tracciato.setValue(32, 1, "risorseArt3_2");
        tracciato.setValue(33, 1, "risorseArt3_Succ");
        tracciato.setValue(34, 1, "risorseImmobili1");
        tracciato.setValue(35, 1, "risorseImmobili2");
        tracciato.setValue(36, 1, "risorseImmobiliSucc");
        tracciato.setValue(37, 1, "risorseAltro1");
        tracciato.setValue(38, 1, "risorseAltro2");
        tracciato.setValue(39, 1, "risorseAltroSucc");
        tracciato.setValue(40, 1, "speseSostenuta");
        tracciato.setValue(41, 1, "tipologiaCapitalePrivato");
        tracciato.setValue(42, 1, "meseAvvioProcedura");
        tracciato.setValue(43, 1, "delega");
        tracciato.setValue(44, 1, "codiceSoggettoDelegato");
        tracciato.setValue(45, 1, "nomeSoggettoDelegato");
        tracciato.setValue(46, 1, "variato");
        tracciato.setValue(47, 1, "note");
        tracciato.setValue(48, 1, "importoRisorseFinaziarie");
        tracciato.setValue(49, 1, "importoRisorseFinanziarieRegionali");
        tracciato.setValue(50, 1, "importoRisorseFinanziarieAltro");
        tracciato.setValue(51, 1, "direzioneGenerale");
        tracciato.setValue(52, 1, "strutturaOperativa");
        tracciato.setValue(53, 1, "referenteDati");
        tracciato.setValue(54, 1, "dirigenteResponsabile");
        tracciato.setValue(55, 1, "proceduraAffidamento");
        tracciato.setValue(56, 1, "acquistoVerdi");
        tracciato.setValue(57, 1, "normativaRiferimento");
        tracciato.setValue(58, 1, "oggettoVerdi");
        tracciato.setValue(59, 1, "cpvVerdi");
        tracciato.setValue(60, 1, "importoNettoIvaVerdi");
        tracciato.setValue(61, 1, "importoIvaVerdi");
        tracciato.setValue(62, 1, "importoTotVerdi");
        tracciato.setValue(63, 1, "acquistoMaterialiRiciclati");
        tracciato.setValue(64, 1, "oggettoMatRic");
        tracciato.setValue(65, 1, "cpvMatRic");
        tracciato.setValue(66, 1, "importoNettoIvaMatRic");
        tracciato.setValue(67, 1, "importoIvaMatRic");
        tracciato.setValue(68, 1, "importoTotMatRic");
        tracciato.setValue(69, 1, "importoIva1");
        tracciato.setValue(70, 1, "importoIva2");
        tracciato.setValue(71, 1, "importoIvaSucc");
        tracciato.setValue(72, 1, "coperturaFinanziaria");
        tracciato.setValue(73, 1, "valutazione");
        tracciato.setValue(74, 1, "importoTotale");
        tracciato.setValue(75, 1, "rupNome");
        tracciato.setValue(76, 1, "rupCognome");
        tracciato.setValue(77, 1, "rupCf");
        
        int i = 2;
        for (Acquisti a: p.getAcquisti())
        {
            tracciato.setValue(1, i, "Forniture/Servizi");
            tracciato.setValue(2, i, a.getCui());
            tracciato.setValue(3, i, a.getSettore());
            tracciato.setValue(4, i, a.getCodiceInterno());
            tracciato.setValue(5, i, a.getDescrizione());
            tracciato.setValue(6, i, a.getAnno());
            tracciato.setValue(7, i, a.getEsenteCup());
            tracciato.setValue(8, i, a.getCup());
            tracciato.setValue(9, i, a.getAcquistoRicompreso());
            tracciato.setValue(10, i, a.getCuiCollegato()); 
            tracciato.setValue(11, i, a.getCpv());
            tracciato.setValue(12, i, a.getNuts());
            tracciato.setValue(13, i, a.getQuantita());
            tracciato.setValue(14, i, a.getUnitaMisura());
            tracciato.setValue(15, i, a.getPriorita());
            tracciato.setValue(16, i, a.getLottoFunzionale());
            tracciato.setValue(17, i, a.getDurataInMesi());
            tracciato.setValue(18, i, a.getNuovoAffidamento());
            tracciato.setValue(19, i, a.getRisorseVincolatePerLegge1());
            tracciato.setValue(20, i, a.getRisorseVincolatePerLegge2());
            tracciato.setValue(21, i, a.getRisorseVincolatePerLeggeSucc());
            tracciato.setValue(22, i, a.getRisorseMutuo1());
            tracciato.setValue(23, i, a.getRisorseMutuo2());
            tracciato.setValue(24, i, a.getRisorseMutuoSucc());
            tracciato.setValue(25, i, a.getRisorsePrivati1());
            tracciato.setValue(26, i, a.getRisorsePrivati2());
            tracciato.setValue(27, i, a.getRisorsePrivatiSucc());
            tracciato.setValue(28, i, a.getRisorseBilancio1());
            tracciato.setValue(29, i, a.getRisorseBilancio2());
            tracciato.setValue(30, i, a.getRisorseBilancioSucc());
            tracciato.setValue(31, i, a.getRisorseArt3_1());
            tracciato.setValue(32, i, a.getRisorseArt3_2());            
            tracciato.setValue(33, i, a.getRisorseArt3_Succ());            
            tracciato.setValue(34, i, a.getRisorseImmobili1());           
            tracciato.setValue(35, i, a.getRisorseImmobili2());            
            tracciato.setValue(36, i, a.getRisorseImmobiliSucc());            
            tracciato.setValue(37, i, a.getRisorseAltro1());            
            tracciato.setValue(38, i, a.getRisorseAltro2());            
            tracciato.setValue(39, i, a.getRisorseAltroSucc());            
            tracciato.setValue(40, i, a.getSpeseSostenute());            
            tracciato.setValue(41, i, a.getTipologiaCapitalePrivato());            
            tracciato.setValue(42, i, a.getMeseAvvioProcedura());            
            tracciato.setValue(43, i, a.getDelega());            
            tracciato.setValue(44, i, a.getCodiceSoggettoDelegato());            
            tracciato.setValue(45, i, a.getNomeSoggettoDelegato());            
            tracciato.setValue(46, i, a.getVariato());            
            tracciato.setValue(47, i, a.getNote());            
            tracciato.setValue(48, i, a.getImportoRisorseFinanziarie());            
            tracciato.setValue(49, i, a.getImportoRisorseFinanziarieRegionali());            
            tracciato.setValue(50, i, a.getImportoRisorseFinanziarieAltro());            
            tracciato.setValue(51, i, a.getDirezioneGenerale());           
            tracciato.setValue(52, i, a.getStrutturaOperativa());            
            tracciato.setValue(53, i, a.getReferenteDati());            
            tracciato.setValue(54, i, a.getDirigenteResponsabile());            
            tracciato.setValue(55, i, a.getProceduraAffidamento());            
            tracciato.setValue(56, i, a.getAcquistoVerdi());           
            tracciato.setValue(57, i, a.getNormativaRiferimento());            
            tracciato.setValue(58, i, a.getOggettoVerdi());           
            tracciato.setValue(59, i, a.getCpvVerdi());          
            tracciato.setValue(60, i, a.getImportoNettoIvaVerdi());           
            tracciato.setValue(61, i, a.getImportoIvaVerdi());         
            tracciato.setValue(62, i, a.getImportoTotVerdi());           
            tracciato.setValue(63, i, a.getAcquistoMaterialiRiciclati());         
            tracciato.setValue(64, i, a.getOggettoMatRic());
            tracciato.setValue(65, i, a.getCpvMatRic());            
            tracciato.setValue(66, i, a.getImportoNettoIvaMatRic());            
            tracciato.setValue(67, i, a.getImportoIvaMatRic());            
            tracciato.setValue(68, i, a.getImportoTotMatRic());            
            tracciato.setValue(69, i, a.getImportoIva1());            
            tracciato.setValue(70, i, a.getImportoIva2());            
            tracciato.setValue(71, i, a.getImportoIvaSucc());            
            tracciato.setValue(72, i, a.getCoperturaFinanziaria());            
            tracciato.setValue(73, i, a.getValutazione());    
            tracciato.setValue(74, i, a.getImportoTotale());    
            tracciato.setValue(75, i, a.getRup().getNome());            
            tracciato.setValue(76, i, a.getRup().getCognome());            
            tracciato.setValue(77, i, a.getRup().getCfPiva());     
            i = i + 1;
        }
        
        JxlsSheet acquistiNonRiproposti = tracciatoWB.addSheet("Aquisti non riproposti");
        
        acquistiNonRiproposti.setValue(1, 1, "Tabella");
        acquistiNonRiproposti.setValue(2, 1, "cui");
        acquistiNonRiproposti.setValue(3, 1, "cup");
        acquistiNonRiproposti.setValue(4, 1, "descrizione");
        acquistiNonRiproposti.setValue(5, 1, "importo");
        acquistiNonRiproposti.setValue(6, 1, "priorita");
        acquistiNonRiproposti.setValue(7, 1, "motivo");
        
        i = 2;
        
        if (p.getAcquistiNonRiproposti() != null)
        {
	        for (AcquistiNonRiproposti a: p.getAcquistiNonRiproposti())
	        {
	        	acquistiNonRiproposti.setValue(1, i, "Acquisti non riproposti");
	        	acquistiNonRiproposti.setValue(2, i, a.getCui());
	        	acquistiNonRiproposti.setValue(3, i, a.getCup());
	        	acquistiNonRiproposti.setValue(4, i, a.getDescrizione());
	        	acquistiNonRiproposti.setValue(5, i, a.getImporto());
	        	acquistiNonRiproposti.setValue(6, i, a.getPriorita());
	        	acquistiNonRiproposti.setValue(7, i, a.getMotivo());
	            i = i + 1;
	        }
        }
        return tracciatoWB;
    }
 
    private JxlsWorkbook createTracciato(PubblicazioneFornitureServizi p) throws Exception {
        JxlsWorkbook tracciatoWB = new JxlsWorkbook("TracciatoFornitureServizi");                       // 4
        JxlsSheet programma = tracciatoWB.addSheet("Programma");                        // 5
        
        programma.setValue(1, 1, "Tabella");
        programma.setValue(1, 2, "Programma");
        programma.setValue(2, 1, "id");
        programma.setValue(2, 2, p.getId());
        programma.setValue(3, 1, "codiceFiscaleSA");
        programma.setValue(3, 2, p.getCodiceFiscaleSA());
        programma.setValue(4, 1, "Ufficio");
        programma.setValue(4, 2, p.getUfficio());
        programma.setValue(5, 1, "anno");
        programma.setValue(5, 2, p.getAnno());
        programma.setValue(6, 1, "descrizione");
        programma.setValue(6, 2, p.getDescrizione());
        programma.setValue(7, 1, "numeroApprovazione");
        programma.setValue(7, 2, p.getNumeroApprovazione());
        programma.setValue(8, 1, "dataApprovazione");
        programma.setValue(8, 2, p.getDataApprovazione());
        programma.setValue(9, 1, "dataPubblicazioneApprovazione");
        programma.setValue(9, 2, p.getDataPubblicazioneApprovazione());
        programma.setValue(10, 1, "titoloAttoApprovazione");
        programma.setValue(10, 2, p.getTitoloAttoApprovazione());
        programma.setValue(11, 1, "urlAttoApprovazione");
        programma.setValue(11, 2, p.getUrlAttoApprovazione());
        programma.setValue(12, 1, "idRicevuto");
        programma.setValue(12, 2, p.getIdRicevuto());
           
        JxlsSheet referente = tracciatoWB.addSheet("Referente");
        referente.setValue(1, 1, "Tabella");
        referente.setValue(1, 2, "Referente");
        referente.setValue(2, 1, "cognome");
        referente.setValue(2, 2, p.getReferente().getCognome());
        referente.setValue(3, 1, "nome");
        referente.setValue(3, 2, p.getReferente().getNome());
        referente.setValue(4, 1, "cfPiva");
        referente.setValue(4, 2, p.getReferente().getCfPiva());
        referente.setValue(5, 1, "indirizzo");
        referente.setValue(5, 2, p.getReferente().getIndirizzo());
        referente.setValue(6, 1, "civico");
        referente.setValue(6, 2, p.getReferente().getCivico());
        referente.setValue(7, 1, "provincia");
        referente.setValue(7, 2, p.getReferente().getProvincia());
        referente.setValue(8, 1, "cap");
        referente.setValue(8, 2, p.getReferente().getCap());
        referente.setValue(9, 1, "luogoIstat");
        referente.setValue(9, 2, p.getReferente().getLuogoIstat());
        
        JxlsSheet tracciato = tracciatoWB.addSheet("Forniture Servizi");
        tracciato.setValue(1, 1, "Tabella");
        tracciato.setValue(2, 1, "cui");
        tracciato.setValue(3, 1, "settore");
        tracciato.setValue(4, 1, "codiceInterno");
        tracciato.setValue(5, 1, "descrizione");
        tracciato.setValue(6, 1, "anno");
        tracciato.setValue(7, 1, "esenteCup");
        tracciato.setValue(8, 1, "cup");
        tracciato.setValue(9, 1, "acquistoRicompreso");
        tracciato.setValue(10, 1, "cuiCollegato");
        tracciato.setValue(11, 1, "cpv");
        tracciato.setValue(12, 1, "nuts");
        tracciato.setValue(13, 1, "quantita");
        tracciato.setValue(14, 1, "unitaMisura");
        tracciato.setValue(15, 1, "priorita");
        tracciato.setValue(16, 1, "lottoFunzionale");
        tracciato.setValue(17, 1, "durataInMesi");
        tracciato.setValue(18, 1, "nuovoAffidamento");
        tracciato.setValue(19, 1, "risorseVincolatePerLegge1");
        tracciato.setValue(20, 1, "risorseVincolatePerLegge2");
        tracciato.setValue(21, 1, "risorseVincolatePerLeggeSucc");
        tracciato.setValue(22, 1, "risorseMutuo1");
        tracciato.setValue(23, 1, "risorseMutuo2");
        tracciato.setValue(24, 1, "risorseMutuoSucc");
        tracciato.setValue(25, 1, "risorsePrivati1");
        tracciato.setValue(26, 1, "risorsePrivati2");
        tracciato.setValue(27, 1, "risorsePrivatiSucc");
        tracciato.setValue(28, 1, "risorseBilancio1");
        tracciato.setValue(29, 1, "risorseBilancio2");
        tracciato.setValue(30, 1, "risorseBilancioSucc");
        tracciato.setValue(31, 1, "risorseArt3_1");
        tracciato.setValue(32, 1, "risorseArt3_2");
        tracciato.setValue(33, 1, "risorseArt3_Succ");
        tracciato.setValue(34, 1, "risorseImmobili1");
        tracciato.setValue(35, 1, "risorseImmobili2");
        tracciato.setValue(36, 1, "risorseImmobiliSucc");
        tracciato.setValue(37, 1, "risorseAltro1");
        tracciato.setValue(38, 1, "risorseAltro2");
        tracciato.setValue(39, 1, "risorseAltroSucc");
        tracciato.setValue(40, 1, "speseSostenuta");
        tracciato.setValue(41, 1, "tipologiaCapitalePrivato");
        tracciato.setValue(42, 1, "meseAvvioProcedura");
        tracciato.setValue(43, 1, "delega");
        tracciato.setValue(44, 1, "codiceSoggettoDelegato");
        tracciato.setValue(45, 1, "nomeSoggettoDelegato");
        tracciato.setValue(46, 1, "variato");
        tracciato.setValue(47, 1, "note");
        tracciato.setValue(48, 1, "importoRisorseFinaziarie");
        tracciato.setValue(49, 1, "importoRisorseFinanziarieRegionali");
        tracciato.setValue(50, 1, "importoRisorseFinanziarieAltro");
        tracciato.setValue(51, 1, "direzioneGenerale");
        tracciato.setValue(52, 1, "strutturaOperativa");
        tracciato.setValue(53, 1, "referenteDati");
        tracciato.setValue(54, 1, "dirigenteResponsabile");
        tracciato.setValue(55, 1, "proceduraAffidamento");
        tracciato.setValue(56, 1, "acquistoVerdi");
        tracciato.setValue(57, 1, "normativaRiferimento");
        tracciato.setValue(58, 1, "oggettoVerdi");
        tracciato.setValue(59, 1, "cpvVerdi");
        tracciato.setValue(60, 1, "importoNettoIvaVerdi");
        tracciato.setValue(61, 1, "importoIvaVerdi");
        tracciato.setValue(62, 1, "importoTotVerdi");
        tracciato.setValue(63, 1, "acquistoMaterialiRiciclati");
        tracciato.setValue(64, 1, "oggettoMatRic");
        tracciato.setValue(65, 1, "cpvMatRic");
        tracciato.setValue(66, 1, "importoNettoIvaMatRic");
        tracciato.setValue(67, 1, "importoIvaMatRic");
        tracciato.setValue(68, 1, "importoTotMatRic");
        tracciato.setValue(69, 1, "importoIva1");
        tracciato.setValue(70, 1, "importoIva2");
        tracciato.setValue(71, 1, "importoIvaSucc");
        tracciato.setValue(72, 1, "coperturaFinanziaria");
        tracciato.setValue(73, 1, "valutazione");
        tracciato.setValue(74, 1, "importoTotale");
        tracciato.setValue(75, 1, "rupNome");
        tracciato.setValue(76, 1, "rupCognome");
        tracciato.setValue(77, 1, "rupCf");
        
        int i = 2;
        for (Acquisti a: p.getAcquisti())
        {
            tracciato.setValue(1, i, "Forniture/Servizi");
            tracciato.setValue(2, i, a.getCui());
            tracciato.setValue(3, i, a.getSettore());
            tracciato.setValue(4, i, a.getCodiceInterno());
            tracciato.setValue(5, i, a.getDescrizione());
            tracciato.setValue(6, i, a.getAnno());
            tracciato.setValue(7, i, a.getEsenteCup());
            tracciato.setValue(8, i, a.getCup());
            tracciato.setValue(9, i, a.getAcquistoRicompreso());
            tracciato.setValue(10, i, a.getCuiCollegato()); 
            tracciato.setValue(11, i, a.getCpv());
            tracciato.setValue(12, i, a.getNuts());
            tracciato.setValue(13, i, a.getQuantita());
            tracciato.setValue(14, i, a.getUnitaMisura());
            tracciato.setValue(15, i, a.getPriorita());
            tracciato.setValue(16, i, a.getLottoFunzionale());
            tracciato.setValue(17, i, a.getDurataInMesi());
            tracciato.setValue(18, i, a.getNuovoAffidamento());
            tracciato.setValue(19, i, a.getRisorseVincolatePerLegge1());
            tracciato.setValue(20, i, a.getRisorseVincolatePerLegge2());
            tracciato.setValue(21, i, a.getRisorseVincolatePerLeggeSucc());
            tracciato.setValue(22, i, a.getRisorseMutuo1());
            tracciato.setValue(23, i, a.getRisorseMutuo2());
            tracciato.setValue(24, i, a.getRisorseMutuoSucc());
            tracciato.setValue(25, i, a.getRisorsePrivati1());
            tracciato.setValue(26, i, a.getRisorsePrivati2());
            tracciato.setValue(27, i, a.getRisorsePrivatiSucc());
            tracciato.setValue(28, i, a.getRisorseBilancio1());
            tracciato.setValue(29, i, a.getRisorseBilancio2());
            tracciato.setValue(30, i, a.getRisorseBilancioSucc());
            tracciato.setValue(31, i, a.getRisorseArt3_1());
            tracciato.setValue(32, i, a.getRisorseArt3_2());            
            tracciato.setValue(33, i, a.getRisorseArt3_Succ());            
            tracciato.setValue(34, i, a.getRisorseImmobili1());           
            tracciato.setValue(35, i, a.getRisorseImmobili2());            
            tracciato.setValue(36, i, a.getRisorseImmobiliSucc());            
            tracciato.setValue(37, i, a.getRisorseAltro1());            
            tracciato.setValue(38, i, a.getRisorseAltro2());            
            tracciato.setValue(39, i, a.getRisorseAltroSucc());            
            tracciato.setValue(40, i, a.getSpeseSostenute());            
            tracciato.setValue(41, i, a.getTipologiaCapitalePrivato());            
            tracciato.setValue(42, i, a.getMeseAvvioProcedura());            
            tracciato.setValue(43, i, a.getDelega());            
            tracciato.setValue(44, i, a.getCodiceSoggettoDelegato());            
            tracciato.setValue(45, i, a.getNomeSoggettoDelegato());            
            tracciato.setValue(46, i, a.getVariato());            
            tracciato.setValue(47, i, a.getNote());            
            tracciato.setValue(48, i, a.getImportoRisorseFinanziarie());            
            tracciato.setValue(49, i, a.getImportoRisorseFinanziarieRegionali());            
            tracciato.setValue(50, i, a.getImportoRisorseFinanziarieAltro());            
            tracciato.setValue(51, i, a.getDirezioneGenerale());           
            tracciato.setValue(52, i, a.getStrutturaOperativa());            
            tracciato.setValue(53, i, a.getReferenteDati());            
            tracciato.setValue(54, i, a.getDirigenteResponsabile());            
            tracciato.setValue(55, i, a.getProceduraAffidamento());            
            tracciato.setValue(56, i, a.getAcquistoVerdi());           
            tracciato.setValue(57, i, a.getNormativaRiferimento());            
            tracciato.setValue(58, i, a.getOggettoVerdi());           
            tracciato.setValue(59, i, a.getCpvVerdi());          
            tracciato.setValue(60, i, a.getImportoNettoIvaVerdi());           
            tracciato.setValue(61, i, a.getImportoIvaVerdi());         
            tracciato.setValue(62, i, a.getImportoTotVerdi());           
            tracciato.setValue(63, i, a.getAcquistoMaterialiRiciclati());         
            tracciato.setValue(64, i, a.getOggettoMatRic());
            tracciato.setValue(65, i, a.getCpvMatRic());            
            tracciato.setValue(66, i, a.getImportoNettoIvaMatRic());            
            tracciato.setValue(67, i, a.getImportoIvaMatRic());            
            tracciato.setValue(68, i, a.getImportoTotMatRic());            
            tracciato.setValue(69, i, a.getImportoIva1());            
            tracciato.setValue(70, i, a.getImportoIva2());            
            tracciato.setValue(71, i, a.getImportoIvaSucc());            
            tracciato.setValue(72, i, a.getCoperturaFinanziaria());            
            tracciato.setValue(73, i, a.getValutazione());    
            tracciato.setValue(74, i, a.getImportoTotale());    
            tracciato.setValue(75, i, a.getRup().getNome());            
            tracciato.setValue(76, i, a.getRup().getCognome());            
            tracciato.setValue(77, i, a.getRup().getCfPiva());     
            i = i + 1;
        }
        
        JxlsSheet acquistiNonRiproposti = tracciatoWB.addSheet("Aquisti non riproposti");
        
        acquistiNonRiproposti.setValue(1, 1, "Tabella");
        acquistiNonRiproposti.setValue(2, 1, "cui");
        acquistiNonRiproposti.setValue(3, 1, "cup");
        acquistiNonRiproposti.setValue(4, 1, "descrizione");
        acquistiNonRiproposti.setValue(5, 1, "importo");
        acquistiNonRiproposti.setValue(6, 1, "priorita");
        acquistiNonRiproposti.setValue(7, 1, "motivo");
        
        i = 2;
        
        if (p.getAcquistiNonRiproposti() != null)
        {
	        for (AcquistiNonRiproposti a: p.getAcquistiNonRiproposti())
	        {
	        	acquistiNonRiproposti.setValue(1, i, "Acquisti non riproposti");
	        	acquistiNonRiproposti.setValue(2, i, a.getCui());
	        	acquistiNonRiproposti.setValue(3, i, a.getCup());
	        	acquistiNonRiproposti.setValue(4, i, a.getDescrizione());
	        	acquistiNonRiproposti.setValue(5, i, a.getImporto());
	        	acquistiNonRiproposti.setValue(6, i, a.getPriorita());
	        	acquistiNonRiproposti.setValue(7, i, a.getMotivo());
	            i = i + 1;
	        }
        }
        return tracciatoWB;
    }
 
    public String getForwardURI() {
        return forwardURI;
    }
 
    public boolean inNewWindow() {
        if (forwardURI == null) return false;
        return true;
    }
 
    public void setForwardURI(String forwardURI) {
        this.forwardURI = forwardURI;
    }
}
