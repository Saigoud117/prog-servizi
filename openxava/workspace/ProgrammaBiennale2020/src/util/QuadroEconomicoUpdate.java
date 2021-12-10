package util;

import java.math.*;
import java.util.*;

import org.openxava.jpa.*;

import com.sun.istack.internal.*;

import ProgBien.*;

public class QuadroEconomicoUpdate {
	
	public static void calculateQuadroEconomico(Procedure ent, @Nullable Object data) {
		if (ent.getQuadroeconomico() == null)
		{
			return;
		}
		
		if (ent.getCoperture().size() > 0)
		{
			for (Coperture c: ent.getCoperture())
			{
				XPersistence.getManager().remove(c);
			}
		}
		
		ent.setCoperture(new ArrayList<Coperture>());
		
		/*
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
	    	if (ent.isQuotagdlenable() && !ent.isFondoenable())
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
		
		for (QuadroEconomico q: ent.getQuadroeconomico())
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
	        c.setProcedura(ent);
	        
	        Calendar calendar = Calendar.getInstance();
	        if (data != null)
	        {
		        Date newData = (Date)data;
		        calendar.setTime(newData);
	        }
	        else
	        	calendar.setTime(ent.getData());
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
		
		ent.setTotalecoperture(totaleCoperture);
        if (ent.getCostipregressi() != null)
        	ent.setCostototale(totaleCoperture.add(ent.getCostipregressi()));
        else		        
        	ent.setCostototale(totaleCoperture);
        if (data != null)
        {
	        Date newData = (Date)data;
	        ent.setData(newData);
        }
        XPersistence.commit();
	}
	
	public static void calculateQuadroEconomicoProgramma(ProcedureProgramma ent, @Nullable Object data) {
		if (ent.getQuadroeconomico() == null)
		{
			return;
		}
		
		if (ent.getCoperture().size() > 0)
		{
			for (CopertureProgramma c: ent.getCoperture())
			{
				XPersistence.getManager().remove(c);
			}
		}
		
		ent.setCoperture(new ArrayList<CopertureProgramma>());
		
		/*
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
	    	if (ent.isQuotagdlenable() && !ent.isFondoenable())
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
		
		for (QuadroEconomicoProgramma q: ent.getQuadroeconomico())
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
			CopertureProgramma c = new CopertureProgramma();
	        c.setProcedura(ent);
	        
	        Calendar calendar = Calendar.getInstance();
	        if (data != null)
	        {
		        Date newData = (Date)data;
		        calendar.setTime(newData);
	        }
	        else
	        	calendar.setTime(ent.getData());
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
		
		ent.setTotalecoperture(totaleCoperture);
        if (ent.getCostipregressi() != null)
        	ent.setCostototale(totaleCoperture.add(ent.getCostipregressi()));
        else		        
        	ent.setCostototale(totaleCoperture);
        if (data != null)
        {
	        Date newData = (Date)data;
	        ent.setData(newData);
        }
        XPersistence.commit();
	}
	
	public static void calculateQuadroEconomicoDefinitivo(ProcedureDefinitive ent) {
		if (ent.getCoperture().size() > 0)
		{
			for (CopertureDefinitive c: ent.getCoperture())
			{
				XPersistence.getManager().remove(c);
			}
		}
		
		ent.setCoperture(new ArrayList<CopertureDefinitive>());
		
		/*
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
	    	if (ent.isQuotagdlenable() && !ent.isFondoenable())
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
		
		for (QuadroEconomicoDefinitivo q: ent.getQuadroeconomico())
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
		
		for (int i=0; i<3; i++)
		{	
			CopertureDefinitive c = new CopertureDefinitive();
	        c.setProcedura(ent);

	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(ent.getData());
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
	        }
	        
	        c.setOid(null);
	        XPersistence.getManager().persist(c);
		}
	}
}
