package actions;

import java.util.*;

import org.openxava.actions.*;
import org.openxava.jpa.*;
import org.openxava.model.*;

import ProgBien.*;
import util.*;

public class AggiornaCopertureProgrammaAction extends ViewBaseAction {
	 
	 public void execute() throws Exception {
		 try {
				
			//ProcedureProgramma ent = (ProcedureProgramma)getView().getEntity();
			ProcedureProgramma ent = new ProcedureProgramma();
			try {
				ent = (ProcedureProgramma)getView().getEntity();
			}
			catch(Exception ex)
			{
				return;
			}
			
			if (ent.getOid() != null)
				QuadroEconomicoUpdate.calculateQuadroEconomicoProgramma(ent, ent.getData());
			
			//XPersistence.commit();  
			getView().refresh();
			getView().refreshCollections();
			//getView().setEditable(true);    
	        //getView().setKeyEditable(false);    
	        addMessage("Coperture.Created.Success");
		}
		catch(Exception e)
		{
			ProcedureProgramma ent = (ProcedureProgramma)MapFacade.findEntity(getModelName(), getView().getKeyValuesWithValue());
			Date oldData = (Date) XPersistence.getManager().createQuery(
			        "select p.data from ProcedureProgramma p where p.oid = :oid")  // JPQL query (1)
			    .setParameter("oid", ent.getOid())
			    .getSingleResult();  // To obtain one single entity (2)
			getView().setValue("data", oldData);
			if (util.ProgBienUtils.checkAdmin())
			{
				throw new javax.validation.ValidationException(
					e.getCause().getMessage());			
			}
			else
			{
				throw new javax.validation.ValidationException(
			            "save_before_change_coperture");
			}
		}
	 }
}
