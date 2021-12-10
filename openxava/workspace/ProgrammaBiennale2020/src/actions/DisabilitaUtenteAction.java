package actions;

import java.util.*;

import javax.persistence.*;

import org.openxava.actions.*;
import org.openxava.jpa.*;

import ProgBien.*;

public class DisabilitaUtenteAction extends ViewBaseAction {
	public void execute() throws Exception {
		Inquadrato i = (Inquadrato)getView().getEntity();
		EntityManager em = XPersistence.getManager();   
        i = em.merge(i);
		
        i.setDatafine(new Date());
		
		XPersistence.commit(); 
		getView().reset();
		getView().setEditable(true);    
		getView().setKeyEditable(false);
        addMessage("Disabilitazione.Success");
	}
}
