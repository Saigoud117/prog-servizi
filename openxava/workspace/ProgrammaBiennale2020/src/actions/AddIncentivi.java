package actions;

import java.math.*;
import java.util.*;

import javax.persistence.*;

import org.openxava.actions.*;
import org.openxava.jpa.*;
import org.openxava.model.*;

import ProgBien.*;

public class AddIncentivi extends ViewBaseAction {
	
	private List<QuadroEconomico> quadroeconomico;
	
	private List<QuadroEconomicoProgramma> quadroeconomicoprogramma;
	 
	public void execute() throws Exception {	
		
		boolean isProcedure = false;
		boolean isProcedureProgramma = false;
		if (getView().getModelName().equals("Procedure"))
			isProcedure = true;
		if (getView().getModelName().equals("ProcedureProgramma"))
			isProcedureProgramma = true;
		
		
	    EntityManager em = XPersistence.getManager();
	    
	    Query queryTipologia = em.createQuery("from TipologiaVoceQuadroEconomico i where i.keyPrincipale is null and i.key like 'C'");
	    Query querySottotipologia = em.createQuery("from TipologiaVoceQuadroEconomico i where i.keyPrincipale.key = 'C' and i.key like 'C4'");
	    Query queryTipoCopertura = em.createQuery("from TipologiaCoperturaFinanziaria i where i.key like 'BIL'");
	    Query queryCaratterizzazioneCopertura = em.createQuery("from CatatterizzazioneTipologia i where i.tipologia.key like 'BIL' and i.nome like 'stanziato'");
		
		TipologiaVoceQuadroEconomico tipologia = (TipologiaVoceQuadroEconomico)queryTipologia.getSingleResult();
		TipologiaVoceQuadroEconomico sottotipologia = (TipologiaVoceQuadroEconomico)querySottotipologia.getSingleResult();
		TipologiaCoperturaFinanziaria tipoCopertura = (TipologiaCoperturaFinanziaria)queryTipoCopertura.getSingleResult();
		CatatterizzazioneTipologia caratterizzazioneCopertura = (CatatterizzazioneTipologia)queryCaratterizzazioneCopertura.getSingleResult();
		
		Map<?, ?> valuesTipologia = MapFacade.getKeyValues("TipologiaVoceQuadroEconomico", tipologia);
		Map<?, ?> valuesSottotipologia = MapFacade.getKeyValues("TipologiaVoceQuadroEconomico", sottotipologia);
		Map<?, ?> valuesTipoCoperture = MapFacade.getKeyValues("TipologiaCoperturaFinanziaria", tipoCopertura);
		Map<?, ?> valuesCaratterizzazioneCopertura = MapFacade.getKeyValues("CatatterizzazioneTipologia", caratterizzazioneCopertura);
    	
		BigDecimal incentivi = new BigDecimal(0);
		BigDecimal incentiviEsecuzione = new BigDecimal(0);
		BigDecimal incentiviInnovaione = new BigDecimal(0);
		BigDecimal incentiviColludo = new BigDecimal(0);
		
		Date dataAvvioProcedura = new Date();
		
		BigDecimal percenta1 = new BigDecimal(0);
		BigDecimal percenta2 = new BigDecimal(0);
		
		if (isProcedure) {
	    	
	    	Procedure ent = (Procedure)getView().getEntity();
	        ent = em.merge(ent);  
	        
	        boolean exist = false;
	        
	        for (QuadroEconomico c: ent.getQuadroeconomico()) {
	        	if (c.getTipologia().getKey().equals("C") && c.getSottotipologia().getKey().equals("C4"))
	        		exist = true;
	        }
	        
	        if (exist) {
				throw new javax.validation.ValidationException(
						"delete_incentivi_before_save");		
			}
	        
	        if (!ent.isFondoenable() && !ent.isQuotainnovazioneenable() && !ent.isQuotagdlenable() && !ent.isProgramenable() && !ent.isAffidaenable() && !ent.isExecenable() && !ent.isQuotacollaudoenable()) {
				throw new javax.validation.ValidationException(
					"enable_incentivi");		
			}
	        
	        if ((!ent.isExecenable() && ent.isQuotacollaudoenable()) && (!ent.isFondoenable() && ent.isQuotacollaudoenable()) && (!ent.isQuotagdlenable() && ent.isQuotacollaudoenable())) {
				throw new javax.validation.ValidationException(
					"enable_esecuzione");		
			}
	        
	        dataAvvioProcedura = ent.getData();
	        
	        percenta1 = ent.getCostiA1().multiply(new BigDecimal(100)).divide(ent.getCostiComplessivi(), 0, RoundingMode.HALF_UP);
	        percenta2 = ent.getCostiA2().multiply(new BigDecimal(100)).divide(ent.getCostiComplessivi(), 0, RoundingMode.HALF_UP);
	        
	        if (ent.isFondoenable()) {
	        	incentivi = incentivi.add(ent.getGdl113Affida()).add(ent.getGdl113Program());
	    		
	        	incentiviInnovaione = ent.getQuotaInnovazioneTotale();
	        	
	    		incentiviEsecuzione = ent.getGdl113Exec();
	    	}
	    	if (ent.isQuotainnovazioneenable() && !ent.isFondoenable())
	    	{
	    		incentiviInnovaione = ent.getQuotaInnovazioneTotale();
	    	}
	    	if (ent.isQuotagdlenable() && !ent.isFondoenable())
	    	{
	    		incentivi = incentivi.add(ent.getGdl113Affida()).add(ent.getGdl113Program());
	    		
	    		incentiviEsecuzione = ent.getGdl113Exec();
	    	}
	    	if (ent.isProgramenable() && !ent.isFondoenable() && !ent.isQuotagdlenable())
	    	{
	    		incentivi = incentivi.add(ent.getGdl113Program());
	    	}
	    	if (ent.isAffidaenable() && !ent.isFondoenable() && !ent.isQuotagdlenable())
	    	{
	    		incentivi = incentivi.add(ent.getGdl113Affida());
	    	}
	    	if (ent.isExecenable() && !ent.isFondoenable() && !ent.isQuotagdlenable())
	    	{
	    		incentiviEsecuzione = ent.getGdl113Exec();
	    	}
	    	if (ent.isQuotacollaudoenable()) 
	    	{
	    		incentiviColludo = ent.getGdl113Collaudo();
	    	}
	    	
			if (incentivi.compareTo(new BigDecimal(0)) != 0) {
				QuadroEconomico q = new QuadroEconomico();
			 	q.setAliquotaiva(new BigDecimal(0));
			 	q.setAmount(incentivi);
			 	q.setCaratterizzazioneCopertura(caratterizzazioneCopertura);
			 	q.setDescrizionevoce("Incentivi ex art. 113");
			 	q.setImportoiva(new BigDecimal(0));
			 	q.setImportonetto(incentivi);
			 	q.setPercentualeA1(new BigDecimal(100));
			 	q.setPercentualeA2(new BigDecimal(0));
			 	q.setSottotipologia(sottotipologia);
			 	q.setTipoCopertura(tipoCopertura);
			 	q.setTipologia(tipologia);   
			 	q.setProcedura(ent);
			 	q.setOid(null);	
			 	
			 	quadroeconomico.add(q);
			}
			
			if (incentiviInnovaione.compareTo(new BigDecimal(0)) != 0) {
				QuadroEconomico q = new QuadroEconomico();
			 	q.setAliquotaiva(new BigDecimal(0));
			 	q.setAmount(incentiviInnovaione);
			 	q.setCaratterizzazioneCopertura(caratterizzazioneCopertura);
			 	q.setDescrizionevoce("Incentivi ex art. 113 per innovazione");
			 	q.setImportoiva(new BigDecimal(0));
			 	q.setImportonetto(incentiviInnovaione);
			 	q.setPercentualeA1(new BigDecimal(100));
			 	q.setPercentualeA2(new BigDecimal(0));
			 	q.setSottotipologia(sottotipologia);
			 	q.setTipoCopertura(tipoCopertura);
			 	q.setTipologia(tipologia);   
			 	q.setProcedura(ent);
			 	q.setOid(null);	
			 	
			 	quadroeconomico.add(q);
			}
			
			if (incentiviColludo.compareTo(new BigDecimal(0)) != 0) {
				QuadroEconomico q = new QuadroEconomico();
			 	q.setAliquotaiva(new BigDecimal(0));
			 	q.setAmount(incentiviColludo);
			 	q.setCaratterizzazioneCopertura(caratterizzazioneCopertura);
			 	q.setDescrizionevoce("Incentivi ex art. 113 per collaudo");
			 	q.setImportoiva(new BigDecimal(0));
			 	q.setImportonetto(incentiviColludo);
			 	q.setPercentualeA1(new BigDecimal(0));
			 	q.setPercentualeA2(new BigDecimal(0));
			 	q.setSottotipologia(sottotipologia);
			 	q.setTipoCopertura(tipoCopertura);
			 	q.setTipologia(tipologia);   
			 	q.setProcedura(ent);
			 	q.setOid(null);	
			 	
			 	quadroeconomico.add(q);
			}
	    }
	    else if (isProcedureProgramma) {
	    	ProcedureProgramma ent = (ProcedureProgramma)getView().getEntity();
	        ent = em.merge(ent); 
	        
        	boolean exist = false;
	        
	        for (QuadroEconomicoProgramma c: ent.getQuadroeconomico()) {
	        	if (c.getTipologia().getKey().equals("C") && c.getSottotipologia().getKey().equals("C4"))
	        		exist = true;
	        }
	        
	        if (exist) {
				throw new javax.validation.ValidationException(
						"delete_incentivi_before_save");		
			}
	        
	        if (!ent.isFondoenable() && !ent.isQuotainnovazioneenable() && !ent.isQuotagdlenable() && !ent.isProgramenable() && !ent.isAffidaenable() && !ent.isExecenable() && !ent.isQuotacollaudoenable()) {
				throw new javax.validation.ValidationException(
					"enable_incentivi");		
			}
	        
	        if ((!ent.isExecenable() && ent.isQuotacollaudoenable()) && (!ent.isFondoenable() && ent.isQuotacollaudoenable()) && (!ent.isQuotagdlenable() && ent.isQuotacollaudoenable())) {
				throw new javax.validation.ValidationException(
					"enable_esecuzione");		
			}
	        
	        dataAvvioProcedura = ent.getData();
	        
	        percenta1 = ent.getCostiA1().multiply(new BigDecimal(100)).divide(ent.getCostiComplessivi(), 0, RoundingMode.HALF_UP);
	        percenta2 = ent.getCostiA2().multiply(new BigDecimal(100)).divide(ent.getCostiComplessivi(), 0, RoundingMode.HALF_UP);
	         
	        if (ent.isFondoenable()) {
	        	incentivi = incentivi.add(ent.getGdl113Affida()).add(ent.getGdl113Program());
	    		
	        	incentiviInnovaione = ent.getQuotaInnovazioneTotale();
	        	
	    		incentiviEsecuzione = ent.getGdl113Exec();
	    	}
	    	if (ent.isQuotainnovazioneenable() && !ent.isFondoenable())
	    	{
	    		incentiviInnovaione = ent.getQuotaInnovazioneTotale();
	    	}
	    	if (ent.isQuotagdlenable() && !ent.isFondoenable())
	    	{
	    		incentivi = incentivi.add(ent.getGdl113Affida()).add(ent.getGdl113Program());
	    		
	    		incentiviEsecuzione = ent.getGdl113Exec();
	    	}
	    	if (ent.isProgramenable() && !ent.isFondoenable() && !ent.isQuotagdlenable())
	    	{
	    		incentivi = incentivi.add(ent.getGdl113Program());
	    	}
	    	if (ent.isAffidaenable() && !ent.isFondoenable() && !ent.isQuotagdlenable())
	    	{
	    		incentivi = incentivi.add(ent.getGdl113Affida());
	    	}
	    	if (ent.isExecenable() && !ent.isFondoenable() && !ent.isQuotagdlenable())
	    	{
	    		incentiviEsecuzione = ent.getGdl113Exec();
	    	}
	    	if (ent.isQuotacollaudoenable()) 
	    	{
	    		incentiviColludo = ent.getGdl113Collaudo();
	    	}
	    	
	    	if (incentivi.compareTo(new BigDecimal(0)) != 0) {
	    		QuadroEconomicoProgramma qP = new QuadroEconomicoProgramma();
			 	qP.setAliquotaiva(new BigDecimal(0));
			 	qP.setAmount(incentivi);
			 	qP.setCaratterizzazioneCopertura(caratterizzazioneCopertura);
			 	qP.setDescrizionevoce("Incentivi ex art. 113");
			 	qP.setImportoiva(new BigDecimal(0));
			 	qP.setImportonetto(incentivi);
			 	qP.setPercentualeA1(new BigDecimal(100));
			 	qP.setPercentualeA2(new BigDecimal(0));
			 	qP.setSottotipologia(sottotipologia);
			 	qP.setTipoCopertura(tipoCopertura);
			 	qP.setTipologia(tipologia);   
			 	qP.setProcedura(ent);
			 	qP.setOid(null);	
			 	
			 	quadroeconomicoprogramma.add(qP);
	    	}
	    	
	    	if (incentivi.compareTo(new BigDecimal(0)) != 0) {
	    		QuadroEconomicoProgramma qP = new QuadroEconomicoProgramma();
			 	qP.setAliquotaiva(new BigDecimal(0));
			 	qP.setAmount(incentiviInnovaione);
			 	qP.setCaratterizzazioneCopertura(caratterizzazioneCopertura);
			 	qP.setDescrizionevoce("Incentivi ex art. 113 per innovazione");
			 	qP.setImportoiva(new BigDecimal(0));
			 	qP.setImportonetto(incentiviInnovaione);
			 	qP.setPercentualeA1(new BigDecimal(100));
			 	qP.setPercentualeA2(new BigDecimal(0));
			 	qP.setSottotipologia(sottotipologia);
			 	qP.setTipoCopertura(tipoCopertura);
			 	qP.setTipologia(tipologia);   
			 	qP.setProcedura(ent);
			 	qP.setOid(null);	
			 	
			 	quadroeconomicoprogramma.add(qP);
	    	}
	    	
	    	if (incentiviColludo.compareTo(new BigDecimal(0)) != 0) {
				QuadroEconomicoProgramma qP = new QuadroEconomicoProgramma();
				qP.setAliquotaiva(new BigDecimal(0));
				qP.setAmount(incentiviColludo);
				qP.setCaratterizzazioneCopertura(caratterizzazioneCopertura);
				qP.setDescrizionevoce("Incentivi ex art. 113 per collaudo");
				qP.setImportoiva(new BigDecimal(0));
				qP.setImportonetto(incentiviColludo);
				qP.setPercentualeA1(new BigDecimal(0));
				qP.setPercentualeA2(new BigDecimal(0));
				qP.setSottotipologia(sottotipologia);
				qP.setTipoCopertura(tipoCopertura);
				qP.setTipologia(tipologia);   
				qP.setProcedura(ent);
				qP.setOid(null);	
			 	
				quadroeconomicoprogramma.add(qP);
			}
	    }
		
		if (incentiviEsecuzione.compareTo(new BigDecimal(0)) != 0)
		{
			SogliaImporti soglia = util.ProgBienUtils.getSogliaImporti(dataAvvioProcedura);
			
			BigDecimal quotacollaudo = incentiviEsecuzione.multiply(soglia.getPercentCollaudo());
			BigDecimal quotaEsecuzione = incentiviEsecuzione.subtract(quotacollaudo);
			
			showDialog();
		    getView().setTitleId("add_row_incentivi");
		    getView().setTitle("Inserisci riga incentivi");
		    getView().setModelName("IncentiviDialog");
		    getView().setValue("descrizionevoce", "Incentivi ex art. 113 gruppo esecuzione");
			getView().setValue("tipologia", valuesTipologia);
			getView().setValue("sottotipologia", valuesSottotipologia);
			getView().setValue("tipoCopertura", valuesTipoCoperture);
			getView().setValue("caratterizzazioneCopertura", valuesCaratterizzazioneCopertura);
			getView().setValue("amount", quotaEsecuzione);
			getView().setValue("percentualeA1", percenta1);
			getView().setValue("percentualeA2", percenta2);
		    
		    if (getPreviousView().getModelName().equals("Procedure"))
		    	setControllers("AddIncentivi", "Dialog");    
		    else if (getPreviousView().getModelName().equals("ProcedureProgramma"))
		    	setControllers("AddIncentiviProgramma", "Dialog");     
		}
		else {
			if (isProcedure)
				for (QuadroEconomico q: quadroeconomico) {
					XPersistence.getManager().persist(q);
				}
			if (isProcedureProgramma)
				for (QuadroEconomicoProgramma qP: quadroeconomicoprogramma) {
					XPersistence.getManager().persist(qP);
				}	
			XPersistence.commit();
			getView().refreshCollections();
	        addMessage("Incentivi.Add.Success");
	        quadroeconomico = new ArrayList<QuadroEconomico>();
	        quadroeconomicoprogramma = new ArrayList<QuadroEconomicoProgramma>();
		}
	}

	public List<QuadroEconomico> getQuadroeconomico() {
		return quadroeconomico;
	}

	public void setQuadroeconomico(List<QuadroEconomico> quadroeconomico) {
		this.quadroeconomico = quadroeconomico;
	}

	public List<QuadroEconomicoProgramma> getQuadroeconomicoprogramma() {
		return quadroeconomicoprogramma;
	}

	public void setQuadroeconomicoprogramma(List<QuadroEconomicoProgramma> quadroeconomicoprogramma) {
		this.quadroeconomicoprogramma = quadroeconomicoprogramma;
	}
}
