package actions;

import org.openxava.actions.*;

import ProgBien.*;
import util.*;

public class AggiornaCopertureAction extends ViewBaseAction {
	 
	 public void execute() throws Exception {
		 try {
				
			//ProcedureProgramma ent = (ProcedureProgramma)getView().getEntity();
			Procedure ent = new Procedure();
			try {
				ent = (Procedure)getView().getEntity();
			}
			catch(Exception ex)
			{
				return;
			}
			
			if (ent.getAnno0() != null && ent.getCodiceinterno() != null)
				QuadroEconomicoUpdate.calculateQuadroEconomico(ent, ent.getData());
			
			//XPersistence.commit();  
			getView().refresh();
			getView().refreshCollections();
			//getView().setEditable(true);    
	        //getView().setKeyEditable(false);    
	        addMessage("Coperture.Created.Success");
		}
		catch(Exception e)
		{
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
