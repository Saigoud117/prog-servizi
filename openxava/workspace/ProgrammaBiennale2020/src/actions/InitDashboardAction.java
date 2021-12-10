package actions;

import java.util.*;

//import javax.persistence.*;

import org.openxava.actions.*;
//import org.openxava.jpa.*;
import org.openxava.model.*;
import org.openxava.util.*;

import ProgBien.*;


public class InitDashboardAction extends ViewBaseAction {
	
	//@Inject @Named("profile")
    private Inquadrato profile;
	
    @Override
    public void execute() throws Exception {
    	
    	/*
    	Date data = new Date();
		Query query = XPersistence.getManager().createQuery("from Inquadrato i where i.dipendenti.cf like :cf and i.datainizio <= :data and i.datafine >= :data");      
		query.setParameter("cf", Users.getCurrent());
		query.setParameter("data", data);
    	
		@SuppressWarnings("unchecked")
		List<Inquadrato> profili = (List<Inquadrato>)query.getResultList();
		*/    
		    
        // TODO future usage when we find what to do here
    	getView().setValue("nome", Users.getCurrentUserInfo().getGivenName());
    	getView().setValue("cognome", Users.getCurrentUserInfo().getFamilyName());
    	getView().setValue("cf", Users.getCurrent());
    	getView().setValue("urlManuale",  "http://prog-servizi.regionemarche.intra/ProgrammaBiennale2020/naviox/files/ISTRUZIONI%20ProgBien.pdf");
    	getView().setValue("urlCriteriAmbientali", "http://www.minambiente.it/pagina/i-criteri-ambientali-minimi");
    	
    	if (profile.getServizi() != null)
    	{
	        Map<?, ?> values = MapFacade.getKeyValues("Inquadrato", profile);
	    	getView().setValue("inquadrato", values);
    	}
    	/*
    	if (util.ProgBienUtils.checkAdmin())
    		getView().setValue("profiloCorrente", "utente Amministratore");
    	else if (profile.getServizi() != null && profili.size() > 1)
    		getView().setValue("profiloCorrente", profile.getProfilo().getDescrizione().concat(", ").concat(profile.getServizi().getDescrizione()));
    	else if (profili.size() == 1)
    		getView().setValue("profiloCorrente", profili.get(0).getProfilo().getDescrizione().concat(", ").concat(profili.get(0).getServizi().getDescrizione()));
    	else
    		getView().setValue("profiloCorrente", "non ancora selezionato");
    	*/
    }

	public Inquadrato getProfile() {
		return profile;
	}

	public void setProfile(Inquadrato profile) {
		this.profile = profile;
	}
    
    
}