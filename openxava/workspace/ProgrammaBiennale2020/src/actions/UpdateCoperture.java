package actions;

import java.util.*;

import org.openxava.actions.*;
import org.openxava.jpa.*;
import org.openxava.model.*;

import ProgBien.*;
import util.*;

public class UpdateCoperture extends OnChangePropertyBaseAction {
	public void execute() throws Exception {
		
		try {
			
			if (getNewValue() == null) return;
			
			//Procedure ent = (Procedure)getView().getEntity();
			Procedure ent = new Procedure();
			try {
				ent = (Procedure)MapFacade.findEntity(getModelName(), getView().getKeyValuesWithValue());
			}
			catch(Exception ex)
			{
				return;
			}
			
			if (ent.getAnno0() != null && ent.getCodiceinterno() != null)
				QuadroEconomicoUpdate.calculateQuadroEconomico(ent, getNewValue());
			
			//XPersistence.commit();  
			//getView().refresh();
			getView().refreshCollections();
			//getView().setEditable(true);    
	        //getView().setKeyEditable(false);    
	        addMessage("Coperture.Created.Success");
		}
		catch(Exception e)
		{
			Procedure ent = (Procedure)MapFacade.findEntity(getModelName(), getView().getKeyValuesWithValue());
			Date oldData = (Date) XPersistence.getManager().createQuery(
			        "select p.data from Procedure p where p.anno0 = :anno0 and p.codiceinterno = :codiceinterno and p.deleted = false")  // JPQL query (1)
			    .setParameter("anno0", ent.getAnno0())
			    .setParameter("codiceinterno", ent.getCodiceinterno())
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
