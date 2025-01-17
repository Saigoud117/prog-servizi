package actions;

import java.util.*;

import javax.persistence.*;

import org.openxava.actions.*;
import org.openxava.jpa.*;

import ProgBien.*;

public class CalculateTotalsDefinitiveAction extends TabBaseAction {
	 @SuppressWarnings("unchecked")
	public void execute() throws Exception {
			Query query = XPersistence.getManager()
		        .createQuery("select p from ProcedureDefinitive p where p.deleted = false and p.archived = false");
		    ArrayList<ProcedureDefinitive> procedure = (ArrayList<ProcedureDefinitive>) query.getResultList();
		    
		    for (ProcedureDefinitive p: procedure) {
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
		        p.setTotalecoperture(p.getTotaleCoperture());
	        	p.setCostototale(p.getCostoTotale());
		    }    
		    XPersistence.commit();
			getView().refresh();
			getView().refreshCollections();;                  
	        addMessage("TotalUpdateSuccess");
	    }
}
