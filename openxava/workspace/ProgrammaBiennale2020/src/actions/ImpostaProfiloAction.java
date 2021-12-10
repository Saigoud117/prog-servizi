package actions;

import java.util.*;

import javax.persistence.*;

import org.openxava.actions.*;
import org.openxava.jpa.*;
import org.openxava.util.*;

import ProgBien.*;


public class ImpostaProfiloAction extends ViewBaseAction {
	
	//@Inject @Named("profile")
    private Inquadrato profile;
	
    @Override
    public void execute() throws Exception {
    	
    	if (!util.ProgBienUtils.checkAdmin())
    	{
	    	Date data = new Date();
			Query query = XPersistence.getManager().createQuery("from Inquadrato i where i.dipendenti.cf like :cf and i.datainizio <= :data and i.datafine >= :data");      
			query.setParameter("cf", Users.getCurrent());
			query.setParameter("data", data);
			
			@SuppressWarnings("unchecked")
			List<Inquadrato> profili = (List<Inquadrato>)query.getResultList(); 
			
			if (profili.size() > 1)
			{
		    	DashboardChangeProfile c = (DashboardChangeProfile)getView().getEntity();
				
		    	profile = c.getInquadrato();
		    	
		    	if (c.getInquadrato() == null || c.getInquadrato().getServizi().getServizio().equals(""))
		    		throw new javax.validation.ValidationException(
				            "choose_a_profile_value");
		    	
		    	//getPreviousView().setValue("profiloCorrente", profile.getProfilo().getDescrizione().concat(", ").concat(profile.getServizi().getDescrizione()));
		    	getPreviousView().setValue("inquadrato",getView().getValue("inquadrato"));
		    	
		    	addMessage("Profilo impostato");
			}
			else
				addMessage("Utente con un unico inquadramento, non è necessario impostare un profilo");
    	}
    	else
			addMessage("Utente Amministratore, non è necessario impostare un profilo");
    	
    	closeDialog();   
    }

	public Inquadrato getProfile() {
		return profile;
	}

	public void setProfile(Inquadrato profile) {
		this.profile = profile;
	}
    
}