package actions;

import java.util.*;

import javax.persistence.*;

import org.openxava.actions.*;
import org.openxava.jpa.*;
import org.openxava.model.*;
import org.openxava.util.*;

import ProgBien.*;

public class CaricaProceduraDaProgrammaApprovatoAction extends ViewBaseAction {
	@Override
	public void execute() throws Exception {		
		CaricaProcedure prc = (CaricaProcedure)getView().getEntity();
		if (prc.getUltimopianoapprovato() == null) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "you_must_select_a_piano"
	            )
	        );
	    }
		ProcedureDefinitive ent = (ProcedureDefinitive)prc.getUltimopianoapprovato();
        EntityManager em = XPersistence.getManager();   
        ent = em.merge(ent);
        
        if (!ent.getStato().getKey().equals("C")) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "procedure_already_imported"
	            )
	        );
	    }

        Procedure copyProc = new Procedure();

        //BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);

        //BeanUtils.copyProperties(copyProc, ent); 
        
        if (!util.ProgBienUtils.ControllaFase(Calendar.getInstance().get(Calendar.YEAR), new Date(), "A") && ent.getAnno0() >= Calendar.getInstance().get(Calendar.YEAR)) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "you_must_choose_a_past_procedure"
	            )
	        );
	    }
        
        copyProc.setAffidaenable(ent.isAffidaenable());

        copyProc.setAggregabile(ent.isAggregabile());
        copyProc.setNonaggregabile(ent.isNonaggregabile());
        
        copyProc.setAliquotaIvaAcquistiMaterialiRiciclati(ent.getAliquotaIvaAcquistiMaterialiRiciclati());
        copyProc.setAliquotaIvaAcquistiVerdi(ent.getAliquotaIvaAcquistiVerdi());
        copyProc.setAmbitogeografico(ent.getAmbitogeografico());
        
        // se la procedura è < 40K può finire o non finire in programmazione
        // in ogni caso conviene che gli cambio l'anno0 con l'attuale altrimenti genererei errori
        if (ent.getCui().equals("~MINORI-SOGLIA-40K~"))
        	copyProc.setAnno0(Calendar.getInstance().get(Calendar.YEAR));
        else
        	copyProc.setAnno0(ent.getAnno0());
        
        copyProc.setAvviata(ent.getAvviata());
        copyProc.setCodiceinterno(null);
        copyProc.setCodiceLivello1(ent.getCodiceLivello1());
        copyProc.setCostia1(ent.getCostia1());
        copyProc.setCostia2(ent.getCostia2());
        copyProc.setCostias(ent.getCostias());
        copyProc.setCosticomplessivi(ent.getCostiComplessivi());
        copyProc.setCostipregressi(ent.getCostipregressi());

        copyProc.setCostototale(ent.getCostototale());
        copyProc.setCpv(ent.getCpv());
        copyProc.setCpvAcquistiMaterialiRiciclati(ent.getCpvAcquistiMaterialiRiciclati());
        copyProc.setCpvAcquistiVerdi(ent.getCpvAcquistiVerdi());
        copyProc.setCui(ent.getCui());
        copyProc.setCuiRicompreso(ent.getCuiRicompreso());
        copyProc.setCuiRicompresoLavori(ent.getCuiRicompresoLavori());
        copyProc.setCup(ent.getCup());
        
        copyProc.setCupmaster(ent.getCupmaster());
        copyProc.setData(ent.getData());
        copyProc.setDeleted(false);
        copyProc.setDescrizione(ent.getDescrizione());
        copyProc.setDipendenti(ent.getDipendenti());
        copyProc.setDivisione(ent.getDivisione());
        copyProc.setDl662014(ent.getDl662014());
        copyProc.setDurata(ent.getDurata());
        copyProc.setExecenable(ent.isExecenable());
        copyProc.setFondoart113(ent.getFondoart113());
        copyProc.setGdl113affida(ent.getGdl113affida());
        copyProc.setGdl113exec(ent.getGdl113exec());
        copyProc.setGdl113program(ent.getGdl113program());
        copyProc.setGdl113totale(ent.getGdl113totale());
        
        copyProc.setQuotacollaudoenable(ent.isQuotacollaudoenable());
        copyProc.setGdl113collaudo(ent.getGdl113collaudo());
        
        copyProc.setImportobaseasta(ent.getImportobaseasta());
        copyProc.setImportoNettoAcquistiMaterialiRiciclati(ent.getImportoNettoAcquistiMaterialiRiciclati());
        copyProc.setImportoNettoAcquistiVerdi(ent.getImportoNettoAcquistiVerdi());
        
        copyProc.setLotto(ent.getLotto());
        copyProc.setNolotto(ent.isNolotto());
        
        copyProc.setDelega(ent.isDelega());
        if(ent.isDelega())
        	copyProc.setAusa(ent.getAusa());
        copyProc.setNondelega(ent.isNondelega());
        
        copyProc.setNote(ent.getNote());
        copyProc.setNoteaggregabile(ent.getNoteaggregabile());
        
        copyProc.setOggettoAcquistiMaterialiRiciclati(ent.getOggettoAcquistiMaterialiRiciclati());
        copyProc.setOggettoAcquistiVerdi(ent.getOggettoAcquistiVerdi());
        copyProc.setPdc(ent.getPdc());
        copyProc.setPriorita(ent.getPriorita());
        
        copyProc.setPrioritamotivation(ent.getPrioritamotivation());
        copyProc.setProgettiict(ent.getProgettiict());
        copyProc.setProgramenable(ent.isProgramenable());
        copyProc.setQuantita(ent.getQuantita());
        copyProc.setQuotagdlenable(ent.isQuotagdlenable());
        copyProc.setQuotaInnovazione(ent.getQuotaInnovazione());
        copyProc.setQuotainnovazioneenable(ent.isQuotainnovazioneenable());
        copyProc.setRicompreso(ent.getRicompreso());
        copyProc.setRiferimentoNormativoVerdi(ent.getRiferimentoNormativoVerdi());
        copyProc.setServizi(ent.getServizi());
        copyProc.setSettore(ent.getSettore());
        copyProc.setSommeadisposizione(ent.getSommeADisposizione());
        
        //copyProc.setTaffidamento(ent.getTaffidamento());
        copyProc.setAffidamentoContrattoInEssere(ent.isAffidamentoContrattoInEssere());
        copyProc.setNonAffidamentoContrattoInEssere(ent.isNonAffidamentoContrattoInEssere());
        copyProc.setAffidamentoExArt63(ent.isAffidamentoExArt63());
        copyProc.setNonAffidamentoExArt63(ent.isNonAffidamentoExArt63());
        
        copyProc.setTotalecoperture(ent.getTotalecoperture());
        copyProc.setTotaleimposte(ent.getTotaleImposte());
        copyProc.setTotaleivaquadroeconomico(ent.getTotaleivaquadroeconomico());
        copyProc.setUmisura(ent.getUmisura());
        if (ent.getCui().equals("~MINORI-SOGLIA-40K~"))
        	copyProc.setUltimopianoapprovato(null);
        else
        	copyProc.setUltimopianoapprovato(ent);
        copyProc.setValorestimatoappalto(ent.getValoreStimatoAppalto());
        copyProc.setVariante(ent.getVariante());
        copyProc.setVerdi(ent.getVerdi());
        copyProc.setNoVerdi(ent.isNoVerdi());
        
        StatoProgetto s = em.find(StatoProgetto.class, "M");
        
        copyProc.setStato(s);
        
        // cambio di stato solo se siamo in una fase != A
        // perchè in questo modo se ci saranno ulteriori fasi C non ho problemi con l'invio a Maggioli
        // queste procedure devono cambiare di stato in una successiva fase con una action apposita
        if (!util.ProgBienUtils.ControllaFase(Calendar.getInstance().get(Calendar.YEAR), new Date(), "A"))
        	ent.setStato(s);

        copyProc.setCoperture(new ArrayList<Coperture>());
        copyProc.setCoperturericomprese(new ArrayList<CopertureRicomprese>());
        copyProc.setQuadroeconomico(new ArrayList<QuadroEconomico>());

        em.persist(copyProc);                     

        for (QuadroEconomicoDefinitivo q: ent.getQuadroeconomico())
        {
            QuadroEconomico qCopy = new QuadroEconomico();
            qCopy.setAliquotaiva(q.getAliquotaiva());
            qCopy.setAmount(q.getAmount());
            qCopy.setCapitolo(q.getCapitolo());
            qCopy.setCaratterizzazioneCopertura(q.getCaratterizzazioneCopertura());
            qCopy.setDescrizionevoce(q.getDescrizionevoce());
            qCopy.setImportoiva(q.getImportoiva());
            qCopy.setImportonetto(q.getImportonetto());
            qCopy.setPdc(q.getPdc());
            qCopy.setPercentualeA1(q.getPercentualeA1());
            qCopy.setPercentualeA2(q.getPercentualeA2());
            qCopy.setSottotipologia(q.getSottotipologia());
            qCopy.setTipoCopertura(q.getTipoCopertura());
            qCopy.setTipologia(q.getTipologia());   
            qCopy.setProcedura(copyProc);
            qCopy.setOid(null);
            XPersistence.getManager().persist(qCopy);
        }     
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(copyProc.getData());

        for (CopertureDefinitive c: ent.getCoperture())
        {
            Coperture cCopy = new Coperture();
            cCopy.setAltro(c.getAltro()); 
            /*
            if (calendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR) - 1 )
            {
            	cCopy.setAnno(c.getAnno() + 1);
            }
            else
            */
            cCopy.setAnno(c.getAnno());
        	cCopy.setBilancio(c.getBilancio());
        	cCopy.setMutuo(c.getMutuo());
        	cCopy.setNonStanziato(c.getStanziato());
        	cCopy.setPatrimonio(c.getPatrimonio());
        	cCopy.setPrivati(c.getPrivati());
        	cCopy.setStanziato(c.getStanziato());
        	cCopy.setTotale(c.getTotale());
        	cCopy.setTrasfimmo(c.getTrasfimmo());
        	cCopy.setVincolate(c.getVincolate());
            cCopy.setProcedura(copyProc);
            cCopy.setOid(null);
            XPersistence.getManager().persist(cCopy);
        }  
        
        for (CopertureRicompreseDefinitive c: ent.getCoperturericomprese())
        {
            CopertureRicomprese cCopy = new CopertureRicomprese();
            cCopy.setAltro(c.getAltro()); 
            cCopy.setAnno(c.getAnno());
        	cCopy.setBilancio(c.getBilancio());
        	cCopy.setMutuo(c.getMutuo());
        	cCopy.setNonStanziato(c.getStanziato());
        	cCopy.setPatrimonio(c.getPatrimonio());
        	cCopy.setPrivati(c.getPrivati());
        	cCopy.setStanziato(c.getStanziato());
        	cCopy.setTotale(c.getTotale());
        	cCopy.setTrasfimmo(c.getTrasfimmo());
        	cCopy.setVincolate(c.getVincolate());
            cCopy.setProcedura(copyProc);
            cCopy.setOid(null);
            XPersistence.getManager().persist(cCopy);
        }  

        //em.flush();
        XPersistence.commit();     
        
        Map<?,?> values = MapFacade.getKeyValues("Procedure", copyProc);
        
        getPreviousView().setValues(values);
        getPreviousView().refresh();
        getPreviousView().refreshCollections();
        //getView().reset();
        getPreviousView().setEditable(true);    
        getPreviousView().setKeyEditable(false);                  
        addMessage("Import.Success");
        closeDialog();
    }
}
