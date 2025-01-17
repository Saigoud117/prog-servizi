package actions;

import java.math.*;
import java.util.*;

import javax.persistence.*;

import org.openxava.actions.*;
import org.openxava.jpa.*;

import ProgBien.*;

public class CalculateTotalsAction extends TabBaseAction {
	 @SuppressWarnings("unchecked")
	public void execute() throws Exception {
			Query query = XPersistence.getManager()
		        .createQuery("select p from Procedure p where p.deleted = false and p.archived = false");
		    ArrayList<Procedure> procedure = (ArrayList<Procedure>) query.getResultList();
		    
		    for (Procedure p: procedure) {
		        p.setCostia1(p.getCostiA1());
		        p.setCostia2(p.getCostiA2());
		        p.setCostias(p.getCostiAs());      
		        
		        p.setValorestimatoappalto(p.getValoreStimatoAppalto());
		        p.setImportobaseasta(p.getImportoBaseAsta());
		        p.setSommeadisposizione(p.getSommeADisposizione());
		        p.setTotaleimposte(p.getTotaleImposte());
		        p.setCosticomplessivi(p.getCostiComplessivi());
		        p.setTotaleivaquadroeconomico(p.getTotaleIvaQuadroEconomico());
		        p.setTotalequadroeconomico(p.getTotaleQuadroEconomico());
		        
		        if (p.getCoperture().size() > 0)
				{
					for (Coperture c: p.getCoperture())
					{
						XPersistence.getManager().remove(c);
					}
				}
				
				p.setCoperture(new ArrayList<Coperture>());
				
				/*
				BigDecimal qInn = new BigDecimal(0);
				BigDecimal qExec = new BigDecimal(0);
				BigDecimal qAff = new BigDecimal(0);
				BigDecimal qProg = new BigDecimal(0);
				
				//calcolo degli incentivi
				if (p.getRicompreso() != null && p.getRicompreso().toString().equals("No"))
				{
					if (p.isFondoenable()) {
						qInn = p.getQuotaInnovazioneTotale();
						qExec = p.getGdl113Exec();
						qAff = p.getGdl113Affida();
						qProg = p.getGdl113Program();
			    	}
			    	if (p.isQuotainnovazioneenable() && !p.isFondoenable())
			    	{
			    		qInn = p.getQuotaInnovazioneTotale();
			    	}
			    	if (p.isQuotagdlenable() && !p.isFondoenable())
			    	{
			    		qExec = p.getGdl113Exec();
						qAff = p.getGdl113Affida();
						qProg = p.getGdl113Program();
			    	}
			    	if (p.isProgramenable() && !p.isFondoenable() && !p.isQuotagdlenable())
			    	{
			    		qProg = p.getGdl113Program();
			    	}
			    	if (p.isAffidaenable() && !p.isFondoenable() && !p.isQuotagdlenable())
			    	{
			    		qAff = p.getGdl113Affida();
			    	}
			    	if (p.isExecenable() && !p.isFondoenable() && !p.isQuotagdlenable())
			    	{
			    		qExec = p.getGdl113Exec();
			    	}
				}
				*/
				
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
				
				for (QuadroEconomico q: p.getQuadroeconomico())
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
				
				BigDecimal totaleCoperture = new BigDecimal(0);
				
				for (int i=0; i<3; i++)
				{	
					Coperture c = new Coperture();
			        c.setProcedura(p);
			        
			        Calendar calendar = Calendar.getInstance();
			        calendar.setTime(p.getData());
			        c.setAnno(calendar.get(Calendar.YEAR) + i);
			        c.setAnno(calendar.get(Calendar.YEAR) + i);
			        if (i == 0)
			        {
			        	c.setVincolate(vinA1);
			        	c.setMutuo(mutA1);
			        	c.setPrivati(capA1);
			        	//c.setBilancio(bilA1.add(qInn).add(qAff).add(qProg));
			        	c.setBilancio(bilA1);
			        	c.setPatrimonio(finA1);
			        	c.setTrasfimmo(traA1);
			        	c.setAltro(altA1);
			        	//c.setStanziato(bilA1s.add(qInn).add(qAff).add(qProg));
			        	c.setStanziato(bilA1s);
			        	c.setNonStanziato(bilA1n);
			        	//c.setTotale(vinA1.add(mutA1).add(capA1).add(bilA1).add(finA1).add(traA1).add(altA1).add(qInn).add(qAff).add(qProg));
			        	c.setTotale(vinA1.add(mutA1).add(capA1).add(bilA1).add(finA1).add(traA1).add(altA1));
			        	//totaleCoperture = totaleCoperture.add(vinA1.add(mutA1).add(capA1).add(bilA1).add(finA1).add(traA1).add(altA1).add(qInn).add(qAff).add(qProg));
			        	totaleCoperture = totaleCoperture.add(vinA1.add(mutA1).add(capA1).add(bilA1).add(finA1).add(traA1).add(altA1));
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
			        	totaleCoperture = totaleCoperture.add(vinA2.add(mutA2).add(capA2).add(bilA2).add(finA2).add(traA2).add(altA2));
			        }
			        else if (i == 2)
			        {
			        	c.setVincolate(vinA3);
			        	c.setMutuo(mutA3);
			        	c.setPrivati(capA3);
			        	//c.setBilancio(bilA3.add(qExec));
			        	c.setBilancio(bilA3);
			        	c.setPatrimonio(finA3);
			        	c.setTrasfimmo(traA3);
			        	c.setAltro(altA3);
			        	//c.setStanziato(bilA3s.add(qExec));
			        	c.setStanziato(bilA3s);
			        	c.setNonStanziato(bilA3n);
			        	//c.setTotale(vinA3.add(mutA3).add(capA3).add(bilA3).add(finA3).add(traA3).add(altA3).add(qExec));
			        	c.setTotale(vinA3.add(mutA3).add(capA3).add(bilA3).add(finA3).add(traA3).add(altA3));
			        	//totaleCoperture = totaleCoperture.add(vinA3.add(mutA3).add(capA3).add(bilA3).add(finA3).add(traA3).add(altA3).add(qExec));
			        	totaleCoperture = totaleCoperture.add(vinA3.add(mutA3).add(capA3).add(bilA3).add(finA3).add(traA3).add(altA3));
			        }
			        
			        c.setOid(null);
			        XPersistence.getManager().persist(c);
				}
				
		        p.setTotalecoperture(totaleCoperture);
		        if (p.getCostipregressi() != null)
		        	p.setCostototale(totaleCoperture.add(p.getCostipregressi()));
		        else		        
		        	p.setCostototale(totaleCoperture);
		    }    
		    XPersistence.commit();
			getView().refresh();
			getView().refreshCollections();                 
	        addMessage("TotalUpdateSuccess");
	    }
}
