package actions;

import java.math.*;
import java.util.*;

import org.openxava.actions.*;
import org.openxava.jpa.*;
import org.openxava.model.*;
import org.openxava.util.*;

import ProgBien.*;
import util.*;

public class AddRowIncentiviAction extends ViewBaseAction {
	
	private List<QuadroEconomico> quadroeconomico;
	 
	 public void execute() throws Exception {
		
		//Procedure ent = (Procedure)getPreviousView().getEntity();	
		Procedure ent = (Procedure)MapFacade.findEntity(getPreviousView().getModelName(), getPreviousView().getKeyValuesWithValue());
		
		if (ent.getRicompreso() != null && ent.getRicompreso().toString() != "No") { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "no_incentivi_if_ricompreso"
	            )
	        );
	    }
		
		IncentiviDialog i = (IncentiviDialog)getView().getEntity();
		
	 	QuadroEconomico q = new QuadroEconomico();
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
	 	for (QuadroEconomico x: quadroeconomico) {
			XPersistence.getManager().persist(x);
		}
	 	//XPersistence.getManager().persist(quadroeconomico);
	 	XPersistence.commit();  
		quadroeconomico = new ArrayList<QuadroEconomico>();
		
		Procedure procedura = (Procedure)XPersistence.getManager()
			    .createQuery(
			        "from Procedure p where p.anno0 = :anno0 and p.codiceinterno = :codiceinterno")  // JPQL query
			    .setParameter("anno0", ent.getAnno0())
			    .setParameter("codiceinterno", ent.getCodiceinterno())
			    .getSingleResult();
		
		QuadroEconomicoUpdate.calculateQuadroEconomico(procedura, null);
		getPreviousView().refresh();                               // 3
		getPreviousView().refreshCollections();
        closeDialog();

        addMessage("Incentivi.Add.Success");
	 }

	public List<QuadroEconomico> getQuadroeconomico() {
		return quadroeconomico;
	}

	public void setQuadroeconomico(List<QuadroEconomico> quadroeconomico) {
		this.quadroeconomico = quadroeconomico;
	}
	 
}
