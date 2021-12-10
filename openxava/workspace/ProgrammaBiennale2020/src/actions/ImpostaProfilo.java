package actions;

//import java.util.*;

//import javax.persistence.*;

import org.openxava.actions.*;
//import org.openxava.jpa.*;
import org.openxava.util.*;

//import ProgBien.*;


public class ImpostaProfilo extends ViewBaseAction {
	
	//@Inject @Named("profile")
	/*
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
		    	Dashboard d = (Dashboard)getView().getEntity();
				
		    	profile = d.getInquadrato();
		    	
		    	if (d.getInquadrato() == null || d.getInquadrato().getServizi().getServizio().equals(""))
		    		throw new javax.validation.ValidationException(
				            "choose_a_profile_value");
		    	
		    	getView().setValue("profiloCorrente", profile.getProfilo().getDescrizione().concat(", ").concat(profile.getServizi().getDescrizione()));
		    	
		    	addMessage("Profilo impostato");
			}
			else
				addMessage("Utente con un unico inquadramento, non è necessario impostare un profilo");
    	}
    	else
			addMessage("Utente Amministratore, non è necessario impostare un profilo");
    }

	public Inquadrato getProfile() {
		return profile;
	}

	public void setProfile(Inquadrato profile) {
		this.profile = profile;
	}
    */
	
	public void execute() throws Exception {
        showDialog();                                         // 2
        //getView().setTitleId("entry_profile");           // 3
        // getView().setTitle("Entry the full address");      // 4
        getView().setModelName("DashboardChangeProfile");             // 5
        getView().setValue("cf", Users.getCurrent());
        setControllers("ChangeProfile", "Dialog");           // 6
        // addActions("AddFullAddress.add", "Dialog.cancel"); // 7
    }
	
}