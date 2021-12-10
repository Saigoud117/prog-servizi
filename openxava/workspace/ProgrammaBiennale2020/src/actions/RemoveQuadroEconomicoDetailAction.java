package actions;

import org.openxava.actions.*;
import org.openxava.jpa.*;
import org.openxava.model.*;

import ProgBien.*;
import util.*;

public class RemoveQuadroEconomicoDetailAction extends RemoveElementFromCollectionAction {
	public void execute() throws Exception {
		super.execute();
		
		//Procedure ent = (Procedure)getView().getEntity();
		Procedure ent = (Procedure)MapFacade.findEntity(getModelName(), getView().getKeyValuesWithValue());
		
		for(QuadroEconomico q: ent.getQuadroeconomico()) {
			if (q.getTipologia().getKey().equals("C") && q.getSottotipologia().getKey().equals("C4"))
				XPersistence.getManager().remove(q);
		}
		
		QuadroEconomicoUpdate.calculateQuadroEconomico(ent, null);
		
		XPersistence.commit();  
		getView().setEditable(true);    
        getView().setKeyEditable(false);    
        getView().refresh();
        getView().refreshCollections();
        addMessage("Coperture.Created.Success");
        addMessage("Regenarate.Incentivi");
	}
}
