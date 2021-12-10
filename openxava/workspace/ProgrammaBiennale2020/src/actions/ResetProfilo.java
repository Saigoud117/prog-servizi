package actions;

import java.util.*;

import org.openxava.actions.*;
import org.openxava.model.*;

import ProgBien.*;

public class ResetProfilo extends ViewBaseAction {
	
	//@Inject @Named("profile")
    private Inquadrato profile;
	
    @Override
    public void execute() throws Exception {
    	
    	profile = new Inquadrato();
    	Map<?, ?> values = MapFacade.getKeyValues("Inquadrato", profile);
    	getView().setValue("inquadrato", values);
    	//getView().refreshCollections();
    	//getView().refreshDescriptionsLists();
    	addMessage("Profilo correttamente reimpostato");
    }

	public Inquadrato getProfile() {
		return profile;
	}

	public void setProfile(Inquadrato profile) {
		this.profile = profile;
	}
	
}