package actions;

import java.util.*;

import javax.persistence.*;

import org.openxava.actions.*;
import org.openxava.jpa.*;
import org.openxava.model.*;
import org.openxava.util.*;

import ProgBien.*;

public class SetTicketInLavorazione extends ViewBaseAction {
	 public void execute() throws Exception {
        
		if (!Users.getCurrent().equalsIgnoreCase("admin") || !util.ProgBienUtils.checkAdmin())
		{
			throw new javax.validation.ValidationException(
					"user_not_allowed");		
		}
		
		TicketHelpDesk ent = (TicketHelpDesk)getView().getEntity();
        EntityManager em = XPersistence.getManager();   
        ent = em.merge(ent);
        
        if (!ent.getStato().getKey().equals("N"))
		{
			throw new javax.validation.ValidationException(
					"action_invalid");		
		}
        
        Query query = em.createQuery("from TicketConfigurazioneNotifiche i where i.tipo.oid = :oid");      
		query.setParameter("oid", ent.getTipo().getOid());
		
		TicketConfigurazioneNotifiche conf = (TicketConfigurazioneNotifiche)query.getSingleResult();
		
		showDialog();                                         // 2
        //getView().setTitleId("entry_profile");           // 3
        // getView().setTitle("Entry the full address");      // 4
        getView().setModelName("TicketHelpDeskInLavorazione");             // 5
        
        Map<?, ?> values = MapFacade.getKeyValues("TicketConfigurazioneNotifiche", conf);
        
        getView().setValue("tipo", values);
        setControllers("ImpostaInLavorazione", "Dialog");           // 6
        // addActions("AddFullAddress.add", "Dialog.cancel"); // 7
		
        /*
        StatoTicket l = em.find(StatoTicket.class, "L");     
        ent.setStato(l);

        XPersistence.commit();       

        getView().refresh();
        getView().refreshCollections();         
        util.ProgBienUtils.sendNotifyEmail(false, true, false, false, ent.getTipo().getDescrizione(), ent.getDipendenti().getCf(), ent.getSeriale());
        addMessage("Success");                                 // 3
        */
    }
}
