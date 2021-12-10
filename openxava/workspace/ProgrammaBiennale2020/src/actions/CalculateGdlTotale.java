package actions;

import java.math.*;

import org.openxava.actions.*;
import org.openxava.jpa.*;

public class CalculateGdlTotale extends ViewBaseAction {
	public void execute() throws Exception {
			
		BigDecimal gdl113Program = (BigDecimal) getView().getSubview("gdl").getSubview("procedure").getValue("gdl113Program");		
		BigDecimal gdl113Exec = (BigDecimal) getView().getSubview("gdl").getSubview("procedure").getValue("gdl113Exec");
		BigDecimal gdl113Affida = (BigDecimal) getView().getSubview("gdl").getSubview("procedure").getValue("gdl113Affida");
		
		String keyFase = getView().getValueString("fase.key");
		
		String keyRuolo = getView().getValueString("ruolo.key");
		
		BigDecimal percentuale = (BigDecimal) getView().getValue("percentuale");
		
		if (!keyFase.isEmpty() && !keyRuolo.isEmpty() && percentuale != null)
		{		
			BigDecimal r = (BigDecimal) XPersistence.getManager().createQuery(
			        "select c.coefficiente from CodiciIct c where c.key = :key")  // JPQL query (1)
			    .setParameter("key", keyRuolo.toString())
			    .getSingleResult();  // To obtain one single entity (2)
						
			BigDecimal coefficiente = r.multiply(BigDecimal.valueOf(0.01));
			
			BigDecimal percent = percentuale.multiply(BigDecimal.valueOf(0.01));
			
			BigDecimal totale = new BigDecimal(0);
				
			if (percent != null)
			{
				if (keyFase.equals("O1P"))
				{
					if (gdl113Program != null)
					{
						totale = gdl113Program.multiply(coefficiente).multiply(percent).setScale(2, RoundingMode.UP);
					}
				}
				else if (keyFase.equals("O2Q"))
				{
					if (gdl113Affida != null)
					{
						totale = gdl113Affida.multiply(coefficiente).multiply(percent).setScale(2, RoundingMode.UP);
					}
				}
				else if (keyFase.equals("O3R"))
				{
					if (gdl113Exec != null)
					{
						totale = gdl113Exec.multiply(coefficiente).multiply(percent).setScale(2, RoundingMode.UP);
					}
				}
				else
				{
					totale = coefficiente;
				}
			}		
			
			getView().setValue("totaleCalculated", totale); //id field	        
	        addMessage("Totale.Success");
		}
		else
			addError("Insert_values");
	}
}
