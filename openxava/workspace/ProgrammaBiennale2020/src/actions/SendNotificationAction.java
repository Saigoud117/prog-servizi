package actions;

import java.util.*;

import javax.persistence.*;

import org.openxava.actions.*;
import org.openxava.jpa.*;
import org.openxava.model.*;
import org.openxava.util.*;

import com.openxava.naviox.model.*;

import ProgBien.*;

public class SendNotificationAction extends ViewBaseAction {
	  public void execute() throws Exception {
		  
		    
		    //TicketHelpDesk ent = (TicketHelpDesk)getView().getEntity();
		  
		    TicketHelpDesk ent = (TicketHelpDesk)MapFacade.findEntity(getModelName(), getView().getKeyValuesWithValue());
	        EntityManager em = XPersistence.getManager();   
	        ent = em.merge(ent);
	        
		    if (Users.getCurrentUserInfo().getEmail() != null && !Users.getCurrentUserInfo().getEmail().isEmpty())
		    	EmailNotifications.subscribeToEntity(Users.getCurrentUserInfo().getEmail(), "TicketHelpDesk" , getView().getKeyValuesWithValue());
		    
		    
		    if (ent.getTipo().getDescrizione().equals("Problema tecnico")) {
		    	// devo notificarlo in base alle impostazioni
		    	List<Dipendenti> utenti = util.ProgBienUtils.getNotificationUsersByType("Problema tecnico");
		    	for(Dipendenti d: utenti){
		    		EmailNotifications.subscribeToEntity(User.find(d.getCf()).getEmail(), "TicketHelpDesk" , getView().getKeyValues());
		    	}
		    }
		    else if (ent.getTipo().getDescrizione().equals("Chiarimento normativo")) {
		    	//devo notificarlo in base alle impostazioni
		    	List<Dipendenti> utenti = util.ProgBienUtils.getNotificationUsersByType("Chiarimento normativo");
	    		for(Dipendenti d: utenti){
	    			EmailNotifications.subscribeToEntity(User.find(d.getCf()).getEmail(), "TicketHelpDesk" , getView().getKeyValues());
	    		}
		    }
		    
	  }
}
