package actions;

import java.util.*;

import javax.persistence.*;

import org.openxava.actions.*;
import org.openxava.jpa.*;
import org.openxava.util.*;

import ProgBien.*;

public class SincronizzaFaseA extends ViewBaseAction {
	@SuppressWarnings("unchecked")
	public void execute() throws Exception {	
		
		if (util.ProgBienUtils.ControllaFase(Calendar.getInstance().get(Calendar.YEAR), new Date(), "A")) {
			throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "not_allowed_in_this_fase"
	            )
	        );
        }
		
		Query query = XPersistence.getManager()
		        .createQuery("select d from ProcedureDefinitive d where d.deleted = false and d.archived = false and d.stato.key in ('C') and d.cui in (select p.ultimopianoapprovato.cui from Procedure p where p.deleted = false and p.archived = false and p.stato.key = 'M')");
		    ArrayList<ProcedureDefinitive> procedure = (ArrayList<ProcedureDefinitive>) query.getResultList();
		
		for (ProcedureDefinitive p: procedure) {
        	StatoProgetto s = XPersistence.getManager().find(StatoProgetto.class, "M");
        	p.setStato(s);
		}
        XPersistence.commit();     

        getView().reset();
        getView().setEditable(true);    
        getView().setKeyEditable(false);                  
        addMessage("Import.Success");
    }
}
