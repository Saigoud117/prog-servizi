package actions;

import javax.persistence.*;

import org.openxava.actions.*;
import org.openxava.jpa.*;
import org.openxava.util.jxls.*;
import org.openxava.web.servlets.*;

import ProgBien.*;

public class ExcelExportProceduraDefinitiva extends ViewBaseAction
implements IForwardAction, JxlsConstants {                                                             // 1
 
    private String forwardURI = null;
 
    public void execute() throws Exception {
        try {
    		
        	ProcedureDefinitive ent = (ProcedureDefinitive)getView().getEntity();
            EntityManager em = XPersistence.getManager();   
            ent = em.merge(ent);
    			
            JxlsWorkbook tracciato = createTracciato(ent);
        	//JxlsWorkbook tracciato = createScenario();
            getRequest().getSession().setAttribute(ReportXLSServlet.SESSION_XLS_REPORT, tracciato);   // 2
            setForwardURI("/xava/report.xls?time=" + System.currentTimeMillis());                      // 3
        } catch (Exception e) {
            addError(e.getMessage());
        }
    }
 
    private JxlsWorkbook createTracciato(ProcedureDefinitive p) throws Exception {
        JxlsWorkbook tracciatoWB = new JxlsWorkbook("TracciatoProcedura");                       // 4
        
        /*
        JxlsSheet tracciato = tracciatoWB.addSheet("Forniture Servizi");
        tracciato.setValue(1, 1, "cui");
        tracciato.setValue(2, 1, "stato");
        tracciato.setValue(3, 1, "servizio");
        tracciato.setValue(4, 1, "struttura");
        tracciato.setValue(5, 1, "descrizione servizio");
        tracciato.setValue(6, 1, "anno0");
        tracciato.setValue(7, 1, "data avvio procedura");
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
       */
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
