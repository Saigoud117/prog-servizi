package actions;

import org.openxava.actions.*;
import org.openxava.jpa.*;
import org.openxava.model.*;

import ProgBien.*;
import util.*;

public class SaveQuadroEconomicoProgrammaDetailAction extends SaveElementInCollectionAction {
	public void execute() throws Exception {
		super.execute();
		
		//ProcedureProgramma ent = (ProcedureProgramma)getView().getEntity();
		ProcedureProgramma ent = (ProcedureProgramma)MapFacade.findEntity(getModelName(), getView().getKeyValuesWithValue());
		
		for(QuadroEconomicoProgramma q: ent.getQuadroeconomico()) {
			if (q.getTipologia().getKey().equals("C") && q.getSottotipologia().getKey().equals("C4"))
				XPersistence.getManager().remove(q);
		}
		
		QuadroEconomicoUpdate.calculateQuadroEconomicoProgramma(ent, null);
		
		XPersistence.commit();  
		getView().setEditable(true);    
        getView().setKeyEditable(false);    
        getView().refresh();
        getView().refreshCollections();
        addMessage("Coperture.Created.Success");
        addMessage("Regenarate.Incentivi");
	}
}
