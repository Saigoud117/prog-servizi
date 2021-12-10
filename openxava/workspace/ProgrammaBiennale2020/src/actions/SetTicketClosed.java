package actions;

import javax.persistence.*;

import org.openxava.actions.*;
import org.openxava.jpa.*;
import org.openxava.util.*;

import ProgBien.*;

public class SetTicketClosed extends ViewBaseAction {
	 public void execute() throws Exception {
        
		if (!Users.getCurrent().equalsIgnoreCase("admin") || !util.ProgBienUtils.checkAdmin())
		{
			throw new javax.validation.ValidationException(
					"user_not_allowed");		
		}
		 
        TicketHelpDesk ent = (TicketHelpDesk)getView().getEntity();
        EntityManager em = XPersistence.getManager();   
        ent = em.merge(ent);
        
        if (!ent.getStato().getKey().equals("L"))
		{
			throw new javax.validation.ValidationException(
					"action_invalid");		
		}
        
        if (ent.getSoluzioneProposta().equals(""))
		{
			throw new javax.validation.ValidationException(
					"add_solution_to_close_ticket");		
		}
        
        StatoTicket c = em.find(StatoTicket.class, "C");     
        ent.setStato(c);

        XPersistence.commit();       

        getView().refresh();
        getView().refreshCollections();    
        util.ProgBienUtils.sendNotifyEmail(false, false, true, false, ent.getTipo().getDescrizione(), ent.getDipendenti().getCf(), "", ent.getSeriale());
        addMessage("Success");                                 // 3
    }
}
