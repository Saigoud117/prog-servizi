package actions;

import javax.persistence.*;

import org.openxava.actions.*;
import org.openxava.jpa.*;
//import org.openxava.util.*;

import ProgBien.*;

public class SetTicketInLavorazioneAction extends ViewBaseAction {
	 public void execute() throws Exception {
        
        TicketHelpDesk ent = (TicketHelpDesk)getPreviousView().getEntity();
        EntityManager em = XPersistence.getManager();   
        ent = em.merge(ent);
        
        if (!ent.getStato().getKey().equals("N"))
		{
			throw new javax.validation.ValidationException(
					"action_invalid");		
		}
        
        TicketHelpDeskInLavorazione t = (TicketHelpDeskInLavorazione)getView().getEntity();
        
        StatoTicket l = em.find(StatoTicket.class, "L");     
        ent.setStato(l);
        ent.setTipo(t.getTipo().getTipo());
        ent.setGestore(t.getUtente().getUtente());
        

        //XPersistence.commit();
        em.flush();
        getPreviousView().refresh();
        addMessage("Success");                                 // 3
        closeDialog();
        util.ProgBienUtils.sendNotifyEmail(false, true, false, false, ent.getTipo().getDescrizione(), ent.getDipendenti().getCf(), ent.getGestore().getCf(), ent.getSeriale());
        //getView().refresh();
    }
}
