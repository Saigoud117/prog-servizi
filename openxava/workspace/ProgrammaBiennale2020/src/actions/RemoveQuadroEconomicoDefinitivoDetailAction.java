package actions;

import org.openxava.actions.*;
import org.openxava.jpa.*;
import org.openxava.model.*;

import ProgBien.*;
import util.*;

public class RemoveQuadroEconomicoDefinitivoDetailAction extends RemoveElementFromCollectionAction {
	public void execute() throws Exception {
		super.execute();
		
		//ProcedureDefinitive ent = (ProcedureDefinitive)getView().getEntity();
		ProcedureDefinitive ent = (ProcedureDefinitive)MapFacade.findEntity(getModelName(), getView().getKeyValuesWithValue());
		
		for(QuadroEconomicoDefinitivo q: ent.getQuadroeconomico()) {
			if (q.getTipologia().getKey().equals("C") && q.getSottotipologia().getKey().equals("C4"))
				XPersistence.getManager().remove(q);
		}
				
		QuadroEconomicoUpdate.calculateQuadroEconomicoDefinitivo(ent);

		XPersistence.commit();  
		getView().setEditable(true);    
        getView().setKeyEditable(false);    
        getView().refresh();
        getView().refreshCollections();
        addMessage("Coperture.Created.Success");
        addMessage("Regenarate.Incentivi");
	}
}
