package actions;

import javax.persistence.*;

import org.openxava.actions.*;
import org.openxava.jpa.*;
import org.openxava.util.*;

import com.openxava.naviox.model.*;

import ProgBien.*;
import util.*;

public class NuovaAbilitazioneAction extends ViewBaseAction {
	public void execute() throws Exception {
		Inquadrato i = (Inquadrato)getView().getEntity();
		
		if (!util.ProgBienUtils.checkAdmin() && (i.getProfilo().getKey().equals("A01") || i.getProfilo().getKey().equals("A02"))) {
			// The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "user_not_allowed_to_set_this_profile"
	            )
	        );
		}
		
		User u = User.find(i.getDipendenti().getCf());
		
		u = UserUtils.updateRole(u, i.getProfilo().getKey()); 	
		
		Query query = XPersistence.getManager().createQuery("from Inquadrato i where i.dipendenti.cf like :cf and i.servizi.oid = :id");			      
	    query.setParameter("cf", i.getDipendenti().getCf());
	    query.setParameter("id", i.getServizi().getOid());
	    
	    Inquadrato inquadratoExist = (Inquadrato)query.getSingleResult();
	    
	    if (inquadratoExist != null) {
	    	inquadratoExist.setDatainizio(i.getDatainizio());
	    	inquadratoExist.setDatafine(i.getDatafine());
	    	inquadratoExist.setQualifica(i.getQualifica());
	    	inquadratoExist.setProfilo(i.getProfilo());
	    }
	    else
	    	XPersistence.getManager().persist(i);
	
		XPersistence.commit(); 
		closeDialog();
		getView().reset();
		getView().setEditable(true);    
		getView().setKeyEditable(false);
        addMessage("Abilitazione.Success");
        
        util.ProgBienUtils.sendNotifyEmailAbilitazione("Abilitazione", i.getDipendenti().getCf(), null, i.getServizi().getServizio(), i.getServizi().getDescrizione());
	}
}
