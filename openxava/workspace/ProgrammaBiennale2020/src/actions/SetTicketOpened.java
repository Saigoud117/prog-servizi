package actions;

import javax.persistence.*;

import org.openxava.actions.*;
import org.openxava.jpa.*;

import ProgBien.*;

public class SetTicketOpened extends ViewBaseAction {
	 public void execute() throws Exception {
		 
        TicketHelpDesk ent = (TicketHelpDesk)getView().getEntity();
        EntityManager em = XPersistence.getManager();   
        ent = em.merge(ent);
        
        if (!ent.getStato().getKey().equals("C"))
		{
			throw new javax.validation.ValidationException(
					"action_invalid");		
		}
        
        StatoTicket n = em.find(StatoTicket.class, "N");     
        ent.setStato(n);

        XPersistence.commit();

        getView().refresh();
        getView().refreshCollections();               
        util.ProgBienUtils.sendNotifyEmail(false, false, false, true, ent.getTipo().getDescrizione(), ent.getDipendenti().getCf(), "", ent.getSeriale());
        addMessage("Success");                                 // 3
    }
}
