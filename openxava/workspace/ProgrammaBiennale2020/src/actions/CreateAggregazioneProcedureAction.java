package actions;

import java.math.*;
import java.util.*;

import javax.persistence.*;

import org.apache.commons.beanutils.*;
import org.openxava.actions.*;
import org.openxava.jpa.*;

import ProgBien.*;

public class CreateAggregazioneProcedureAction extends ViewBaseAction {
	public void execute() throws Exception { 
		
		// throw exception se cui è null
		// nuovo accorpamento
		//String cui = getView().getValueString("cui"); 
		
		// nuovo accorpamento
        //AccorpamentoProcedure ent = (AccorpamentoProcedure)getPreviousView().getEntity();
		AccorpamentoProcedure ent = (AccorpamentoProcedure)getView().getEntity();
        EntityManager em = XPersistence.getManager();   
        ent = em.merge(ent);
        
        Procedure mainProc = new Procedure();
        
        BigDecimal costia1tot = new BigDecimal(0);
		BigDecimal costia2tot = new BigDecimal(0);
		BigDecimal costiastot = new BigDecimal(0);
		
		BigDecimal valorestimatoappaltotot = new BigDecimal(0);
		BigDecimal importobaseastatot = new BigDecimal(0);
		BigDecimal sommeadisposizionetot = new BigDecimal(0);
		BigDecimal totaleimpostetot = new BigDecimal(0);
		BigDecimal costicomplessivitot = new BigDecimal(0);
		BigDecimal totaleivaquadroeconomicotot  = new BigDecimal(0);
		BigDecimal totalequadroeconomicotot = new BigDecimal(0);
		BigDecimal costipregressitot = new BigDecimal(0);
		BigDecimal totalecoperturetot = new BigDecimal(0);
		BigDecimal costototaletot = new BigDecimal(0);
		
        for(AccorpamentoProcedureDettaglio d: ent.getAccorpamentoProcedureDettaglio()) {
        	if (d.isPrincipale()) {
        		mainProc = d.getProcedure();
        	}           	       
        	
        	if(d.getProcedure().getCostia1() != null)
        		costia1tot = costia1tot.add(d.getProcedure().getCostia1());
        	if(d.getProcedure().getCostia2() !=null)
        		costia2tot = costia2tot.add(d.getProcedure().getCostia2());
        	if(d.getProcedure().getCostias() != null)
        		costiastot = costiastot.add(d.getProcedure().getCostias());
        	
        	if(d.getProcedure().getValoreStimatoAppalto() != null)
        		valorestimatoappaltotot = valorestimatoappaltotot.add(d.getProcedure().getValoreStimatoAppalto());
        	if(d.getProcedure().getImportoBaseAsta() != null)
        		importobaseastatot = importobaseastatot.add(d.getProcedure().getImportoBaseAsta());
        	if(d.getProcedure().getSommeADisposizione() != null)
        		sommeadisposizionetot = sommeadisposizionetot.add(d.getProcedure().getSommeADisposizione());
        	if(d.getProcedure().getTotaleImposte() != null)
        		totaleimpostetot = totaleimpostetot.add(d.getProcedure().getTotaleImposte());
        	if(d.getProcedure().getCostiComplessivi() != null)
        		costicomplessivitot = costicomplessivitot.add(d.getProcedure().getCostiComplessivi());
        	if(d.getProcedure().getTotaleIvaQuadroEconomico() != null)
        		totaleivaquadroeconomicotot = totaleivaquadroeconomicotot.add(d.getProcedure().getTotaleIvaQuadroEconomico());
        	if(d.getProcedure().getTotaleQuadroEconomico() != null)
        		totalequadroeconomicotot = totalequadroeconomicotot.add(d.getProcedure().getTotaleQuadroEconomico());
        	if(d.getProcedure().getCostipregressi() != null)
        		costipregressitot = costipregressitot.add(d.getProcedure().getCostipregressi());
        	if(d.getProcedure().getTotaleCoperture() != null)
        		totalecoperturetot = totalecoperturetot.add(d.getProcedure().getTotaleCoperture());
        	if(d.getProcedure().getCostoTotale() != null)
        		costototaletot = costototaletot.add(d.getProcedure().getCostoTotale());
        	
        	StatoProgetto g = em.find(StatoProgetto.class, "G");        
            d.getProcedure().setStato(g);
        	
        }

        // nuovo accorpamento
        //ProcedureProgramma copyProc = new ProcedureProgramma();
        Procedure copyProc = new Procedure();
        
        BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);

        BeanUtils.copyProperties(copyProc, mainProc);
        
        //nuovo accorpamento
        //copyProc.setCui(cui);
        
        copyProc.setCostia1(costia1tot);
        copyProc.setCostia2(costia2tot);
        copyProc.setCostias(costiastot);      
        
        copyProc.setValorestimatoappalto(valorestimatoappaltotot);
        copyProc.setImportobaseasta(importobaseastatot);
        copyProc.setSommeadisposizione(sommeadisposizionetot);
        copyProc.setTotaleimposte(totaleimpostetot);
        copyProc.setCosticomplessivi(costicomplessivitot);
        copyProc.setTotaleivaquadroeconomico(totaleivaquadroeconomicotot);
        copyProc.setTotalequadroeconomico(totalequadroeconomicotot);
        copyProc.setCostipregressi(costipregressitot);
        copyProc.setTotalecoperture(totalecoperturetot);
        copyProc.setCostototale(costototaletot);
        
        copyProc.setDescrizione(ent.getDescrizione());
        
        StatoProgetto z = em.find(StatoProgetto.class, "Z");        
        copyProc.setStato(z);
        
        //nuovo accorpamento
        /*
        copyProc.setCoperture(new ArrayList<CopertureProgramma>());
        copyProc.setCoperturericomprese(new ArrayList<CopertureRicompreseProgramma>());
        copyProc.setQuadroeconomico(new ArrayList<QuadroEconomicoProgramma>());
        */ 
        copyProc.setCoperture(new ArrayList<Coperture>());
        copyProc.setCoperturericomprese(new ArrayList<CopertureRicomprese>());
        copyProc.setQuadroeconomico(new ArrayList<QuadroEconomico>());
        
        
        em.persist(copyProc);
        
        ArrayList<QuadroEconomico> quadroEconomico = new ArrayList<QuadroEconomico>();
        
        for(AccorpamentoProcedureDettaglio d: ent.getAccorpamentoProcedureDettaglio()) {
    		for (QuadroEconomico q: d.getProcedure().getQuadroeconomico())
    		{
    			//nuovo accorpamento
    			//QuadroEconomicoProgramma qCopy = new QuadroEconomicoProgramma();    
                /*
                qCopy.setAliquotaiva(q.getAliquotaiva());
                qCopy.setAmount(q.getAmount());
                //qCopy.setBase(q.getBase());
                qCopy.setCapitolo(q.getCapitolo());
                qCopy.setDescrizionevoce(q.getDescrizionevoce());
                qCopy.setImportoiva(q.getImportoiva());
                qCopy.setImportonetto(q.getImportonetto());
                qCopy.setPdc(q.getPdc());
                //qCopy.setTipologia(q.getTipologia());            
                qCopy.setProcedura(copyProc);
                qCopy.setOid(null);
                 */
    			
				QuadroEconomico qCopy = new QuadroEconomico();
                BeanUtils.copyProperties(qCopy, q);
                qCopy.setProcedura(copyProc);
                qCopy.setOid(null);
                
                XPersistence.getManager().persist(qCopy);
                quadroEconomico.add(qCopy);
    		}
        }
        
        /*
        for(AccorpamentoProcedureDettaglio d: ent.getAccorpamentoProcedureDettaglio()) {
    		for (Coperture c: d.getProcedure().getCoperture())
    		{
    			//nuovo accorpamento
    			//CopertureProgramma cCopy = new CopertureProgramma();
    			
    			Coperture cCopy = new Coperture();
            	cCopy.setAltro(c.getAltro());
            	cCopy.setAnno(c.getAnno());
            	cCopy.setBilancio(c.getBilancio());
            	cCopy.setMutuo(c.getMutuo());
            	cCopy.setPatrimonio(c.getPatrimonio());
            	cCopy.setPrivati(c.getPrivati());
            	cCopy.setStanziato(c.getStanziato());
            	cCopy.setNonStanziato(c.getNonStanziato());
            	cCopy.setTotale(c.getTotale());
            	cCopy.setTrasfimmo(c.getTrasfimmo());
            	cCopy.setVincolate(c.getVincolate());
            	cCopy.setProcedura(copyProc);
                cCopy.setOid(null);
                XPersistence.getManager().persist(cCopy);
    		}
        }
        */
        
        calculateCopeture(copyProc, quadroEconomico);
        
        for(AccorpamentoProcedureDettaglio d: ent.getAccorpamentoProcedureDettaglio()) {
    		for (CopertureRicomprese c: d.getProcedure().getCoperturericomprese())
    		{
    			//nuovo accorpamento
    			//CopertureRicompreseProgramma cCopy = new CopertureRicompreseProgramma();
    			/*
    			CopertureRicomprese cCopy = new CopertureRicomprese();
            	cCopy.setAltro(c.getAltro());
            	cCopy.setAnno(c.getAnno());
            	cCopy.setBilancio(c.getBilancio());
            	cCopy.setMutuo(c.getMutuo());
            	cCopy.setPatrimonio(c.getPatrimonio());
            	cCopy.setPrivati(c.getPrivati());
            	cCopy.setStanziato(c.getStanziato());
            	cCopy.setNonStanziato(c.getNonStanziato());
            	cCopy.setTotale(c.getTotale());
            	cCopy.setTrasfimmo(c.getTrasfimmo());
            	cCopy.setVincolate(c.getVincolate());
            	cCopy.setProcedura(copyProc);
                cCopy.setOid(null);
                */
    			CopertureRicomprese cCopy = new CopertureRicomprese();
                BeanUtils.copyProperties(cCopy, c);
                cCopy.setProcedura(copyProc);
                cCopy.setOid(null);
                XPersistence.getManager().persist(cCopy);
    		}
        }
        
        XPersistence.commit();     

        getView().setEditable(true);    
        getView().setKeyEditable(false);                  
        addMessage("Aggregation.Success");
        
	}
	
	protected void calculateCopeture(Procedure ent, Collection<QuadroEconomico> quadro) {
		BigDecimal qInn = new BigDecimal(0);
		BigDecimal qExec = new BigDecimal(0);
		BigDecimal qAff = new BigDecimal(0);
		BigDecimal qProg = new BigDecimal(0);
		
		//calcolo degli incentivi
		if (ent.getRicompreso() != null && ent.getRicompreso().toString().equals("No"))
		{
			if (ent.isFondoenable()) {
				qInn = ent.getQuotaInnovazioneTotale();
				qExec = ent.getGdl113Exec();
				qAff = ent.getGdl113Affida();
				qProg = ent.getGdl113Program();
	    	}
	    	if (ent.isQuotainnovazioneenable() && !ent.isFondoenable())
	    	{
	    		qInn = ent.getQuotaInnovazioneTotale();
	    	}
	    	if (ent.isQuotagdlenable() && !!ent.isFondoenable())
	    	{
	    		qExec = ent.getGdl113Exec();
				qAff = ent.getGdl113Affida();
				qProg = ent.getGdl113Program();
	    	}
	    	if (ent.isProgramenable() && !ent.isFondoenable() && !ent.isQuotagdlenable())
	    	{
	    		qProg = ent.getGdl113Program();
	    	}
	    	if (ent.isAffidaenable() && !ent.isFondoenable() && !ent.isQuotagdlenable())
	    	{
	    		qAff = ent.getGdl113Affida();
	    	}
	    	if (ent.isExecenable() && !ent.isFondoenable() && !ent.isQuotagdlenable())
	    	{
	    		qExec = ent.getGdl113Exec();
	    	}
		}
		
		BigDecimal vinA1 = new BigDecimal(0);
		BigDecimal vinA2 = new BigDecimal(0);
		BigDecimal vinA3 = new BigDecimal(0);
		
		BigDecimal mutA1 = new BigDecimal(0);
		BigDecimal mutA2 = new BigDecimal(0);
		BigDecimal mutA3 = new BigDecimal(0);
		
		BigDecimal capA1 = new BigDecimal(0);
		BigDecimal capA2 = new BigDecimal(0);
		BigDecimal capA3 = new BigDecimal(0);
		
		BigDecimal bilA1 = new BigDecimal(0);
		BigDecimal bilA2 = new BigDecimal(0);
		BigDecimal bilA3 = new BigDecimal(0);
		
		BigDecimal bilA1s = new BigDecimal(0);
		BigDecimal bilA2s = new BigDecimal(0);
		BigDecimal bilA3s = new BigDecimal(0);
		
		BigDecimal bilA1n = new BigDecimal(0);
		BigDecimal bilA2n = new BigDecimal(0);
		BigDecimal bilA3n = new BigDecimal(0);
		
		BigDecimal finA1 = new BigDecimal(0);
		BigDecimal finA2 = new BigDecimal(0);
		BigDecimal finA3 = new BigDecimal(0);
		
		BigDecimal traA1 = new BigDecimal(0);
		BigDecimal traA2 = new BigDecimal(0);
		BigDecimal traA3 = new BigDecimal(0);
		
		BigDecimal altA1 = new BigDecimal(0);
		BigDecimal altA2 = new BigDecimal(0);
		BigDecimal altA3 = new BigDecimal(0);
		
		if (ent.getRicompreso() != null && ent.getRicompreso().toString().equals("No"))
		{
			for (QuadroEconomico q: quadro)
	        {
				BigDecimal percentageA3 = new BigDecimal(100).subtract(q.getPercentualeA1().add(q.getPercentualeA2())); 
						
				if (q.getTipoCopertura().getKey().equals("VIN"))
				{
					vinA1 = vinA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					vinA2 = vinA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					vinA3 = vinA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
				}
				
				if (q.getTipoCopertura().getKey().equals("MUT"))
				{
					mutA1 = mutA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					mutA2 = mutA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					mutA3 = mutA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
				}
				
				if (q.getTipoCopertura().getKey().equals("CAP"))
				{
					capA1 = capA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					capA2 = capA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					capA3 = capA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
				}
				
				if (q.getTipoCopertura().getKey().equals("BIL"))
				{
					bilA1 = bilA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					bilA2 = bilA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					bilA3 = bilA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
					
					if (q.getCaratterizzazioneCopertura().getNome().equals("stanziato"))
					{
						bilA1s = bilA1s.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
						bilA2s = bilA2s.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
						bilA3s = bilA3s.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
					}
					else if (q.getCaratterizzazioneCopertura().getNome().equals("anni successivi"))
					{
						bilA1n = bilA1n.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
						bilA2n = bilA2n.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
						bilA3n = bilA3n.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
					}
				}
				
				if (q.getTipoCopertura().getKey().equals("FIN"))
				{
					finA1 = finA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					finA2 = finA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					finA3 = finA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
				}
				
				if (q.getTipoCopertura().getKey().equals("TRA"))
				{
					traA1 = traA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					traA2 = traA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					traA3 = traA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
				}
				
				if (q.getTipoCopertura().getKey().equals("ALT"))
				{
					altA1 = altA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					altA2 = altA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					altA3 = altA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
				}
	        }     
		}
		for (int i=0; i<3; i++)
		{	
			Coperture c = new Coperture();
	        c.setProcedura(ent);
	        
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(ent.getData());
	        c.setAnno(calendar.get(Calendar.YEAR) + i);
	        if (i == 0)
	        {
	        	c.setVincolate(vinA1);
	        	c.setMutuo(mutA1);
	        	c.setPrivati(capA1);
	        	c.setBilancio(bilA1.add(qInn).add(qAff).add(qProg));
	        	c.setPatrimonio(finA1);
	        	c.setTrasfimmo(traA1);
	        	c.setAltro(altA1);
	        	c.setStanziato(bilA1s.add(qInn).add(qAff).add(qProg));
	        	c.setNonStanziato(bilA1n);
	        	c.setTotale(vinA1.add(mutA1).add(capA1).add(bilA1).add(finA1).add(traA1).add(altA1).add(qInn).add(qAff).add(qProg));
	        }	
	        else if (i == 1)
	        {
	        	c.setVincolate(vinA2);
	        	c.setMutuo(mutA2);
	        	c.setPrivati(capA2);
	        	c.setBilancio(bilA2);
	        	c.setPatrimonio(finA2);
	        	c.setTrasfimmo(traA2);
	        	c.setAltro(altA2);
	        	c.setStanziato(bilA2s);
	        	c.setNonStanziato(bilA2n);
	        	c.setTotale(vinA2.add(mutA2).add(capA2).add(bilA2).add(finA2).add(traA2).add(altA2));
	        }
	        else if (i == 2)
	        {
	        	c.setVincolate(vinA3);
	        	c.setMutuo(mutA3);
	        	c.setPrivati(capA3);
	        	c.setBilancio(bilA3.add(qExec));
	        	c.setPatrimonio(finA3);
	        	c.setTrasfimmo(traA3);
	        	c.setAltro(altA3);
	        	c.setStanziato(bilA3s.add(qExec));
	        	c.setNonStanziato(bilA3n);
	        	c.setTotale(vinA3.add(mutA3).add(capA3).add(bilA3).add(finA3).add(traA3).add(altA3).add(qExec));
	        }
	        
	        c.setOid(null);
	        XPersistence.getManager().persist(c);
		}
	}
}
