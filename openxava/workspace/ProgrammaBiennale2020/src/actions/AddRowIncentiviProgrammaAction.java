package actions;

import java.math.*;
import java.util.*;

import org.openxava.actions.*;
import org.openxava.jpa.*;
import org.openxava.model.*;
import org.openxava.util.*;

import ProgBien.*;
import util.*;

public class AddRowIncentiviProgrammaAction extends ViewBaseAction {
	
	private List<QuadroEconomicoProgramma> quadroeconomicoprogramma;
	
	 public void execute() throws Exception {
	 	//ProcedureProgramma ent = (ProcedureProgramma)getPreviousView().getEntity();	
		ProcedureProgramma ent = (ProcedureProgramma)MapFacade.findEntity(getPreviousView().getModelName(), getPreviousView().getKeyValuesWithValue());
		
		if (ent.getRicompreso() != null && ent.getRicompreso().toString() != "No") { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "no_incentivi_if_ricompreso"
	            )
	        );
	    }
		
		IncentiviDialog i = (IncentiviDialog)getView().getEntity();
		
	 	QuadroEconomicoProgramma q = new QuadroEconomicoProgramma();
	 	q.setAliquotaiva(new BigDecimal(0));
	 	q.setAmount(i.getAmount());
	 	q.setCaratterizzazioneCopertura(i.getCaratterizzazioneCopertura());
	 	q.setDescrizionevoce(i.getDescrizionevoce());
	 	q.setImportoiva(new BigDecimal(0));
	 	q.setImportonetto(i.getAmount());
	 	q.setPercentualeA1(i.getPercentualeA1());
	 	q.setPercentualeA2(i.getPercentualeA2());
	 	q.setSottotipologia(i.getSottotipologia());
	 	q.setTipoCopertura(i.getTipoCopertura());
	 	q.setTipologia(i.getTipologia());   
	 	q.setProcedura(ent);
	 	q.setOid(null);	
	 	XPersistence.getManager().persist(q);
	 	for (QuadroEconomicoProgramma x: quadroeconomicoprogramma) {
	 		XPersistence.getManager().persist(x);	
		}
		XPersistence.commit();  
		quadroeconomicoprogramma = new ArrayList<QuadroEconomicoProgramma>();
		
		ProcedureProgramma procedura = (ProcedureProgramma)XPersistence.getManager()
			    .createQuery(
			        "from ProcedureProgramma p where p.oid = :oid")  // JPQL query
			    .setParameter("oid", ent.getOid())
			    .getSingleResult();
		
		QuadroEconomicoUpdate.calculateQuadroEconomicoProgramma(procedura, null);
		getPreviousView().refresh();
		getPreviousView().refreshCollections();
        closeDialog();
        
        addMessage("Incentivi.Add.Success");
	 }

	public List<QuadroEconomicoProgramma> getQuadroeconomicoprogramma() {
		return quadroeconomicoprogramma;
	}

	public void setQuadroeconomicoprogramma(List<QuadroEconomicoProgramma> quadroeconomicoprogramma) {
		this.quadroeconomicoprogramma = quadroeconomicoprogramma;
	}
	 
}
