package actions;

import java.math.*;
import javax.persistence.*;

import org.openxava.actions.*;
import org.openxava.jpa.*;

import ProgBien.*;

public class UpdateTotaleGdl extends OnChangePropertyBaseAction {
	public void execute() throws Exception {
		
		try {
			BigDecimal amount = new BigDecimal(0);
			if (getNewValue() == null) return;
			
			//Procedure ent = (Procedure)getView().getEntity();
			Gdl113Details ent = (Gdl113Details)getView().getEntity();
			
			if (getPreviousView().getEntity() != null && getPreviousView().getModelName().equals("Gdl113") && ent.getFase() != null && ent.getRuolo() != null) {

				EntityManager em = XPersistence.createManager();
				CodiciIct r = (CodiciIct) em.createQuery(
				        "select c from CodiciIct c where c.key = :key")  // JPQL query (1)
				    .setParameter("key", ent.getRuolo().getKey())
				    .getSingleResult();  // To obtain one single entity (2)
				em.close(); // You have to close the manager
							
				BigDecimal coefficiente = r.getCoefficiente().multiply(BigDecimal.valueOf(0.01));
				
				BigDecimal percent = ent.getPercentuale().multiply(BigDecimal.valueOf(0.01));
				
				Gdl113 g = (Gdl113)getPreviousView().getEntity();
				
				ProcedureDefinitive p = g.getProcedure();
					
				if (percent != null)
				{
					if (ent.getFase().getKey().equals("O1P")) {
						if (p.getGdl113Program() != null && (p.isFondoenable() || p.isQuotagdlenable() || p.isProgramenable()))
							amount = p.getGdl113Program().multiply(coefficiente).multiply(percent).setScale(2, RoundingMode.UP);
					}
					else if (ent.getFase().getKey().equals("O2Q")) {
						if (p.getGdl113Affida() != null && (p.isFondoenable() || p.isQuotagdlenable() || p.isAffidaenable()))
							amount = p.getGdl113Affida().multiply(coefficiente).multiply(percent).setScale(2, RoundingMode.UP);
					}
					else if (ent.getFase().getKey().equals("O3R")) {
						if (p.getGdl113Exec() != null && (p.isFondoenable() || p.isQuotagdlenable() || p.isExecenable()))
						{
							if (p.isQuotacollaudoenable())
								amount = (p.getGdl113Exec().add(p.getGdl113Collaudo())).multiply(coefficiente).multiply(percent).setScale(2, RoundingMode.UP);	
							else
								amount = p.getGdl113Exec().multiply(coefficiente).multiply(percent).setScale(2, RoundingMode.UP);
						}
					}
				}
			}

			getView().setValue("totaleCalculated", amount);
		}
		catch(Exception e)
		{
			getView().setValue("totaleCalculated", new BigDecimal(0));
		}
	}
}
