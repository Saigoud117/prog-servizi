package actions;

import java.util.*;

import org.openxava.actions.*;
import org.openxava.model.*;

public class DuplicateAction extends ViewBaseAction {

	@SuppressWarnings({ "unchecked" })
	public void execute() throws Exception {	
        
        Map<Object, Object> keys = getView().getKeyValues();
        // Members names, but no collection name
        Map<Object, Object> memberNames = getView().getMembersNamesWithHidden();
        // Now ask MapFacade to load all data from database including collection
        Map<?, ?> values = MapFacade.getValues(getView().getModelName(), keys, memberNames);   
        //getView().clear();
        getView().reset();
        getView().setEditable(true);    
        getView().setKeyEditable(true);            
        getView().setValues(values); // Here set the whole Entity including collection data obtained from database        
        getView().setValue("codiceinterno", null); //id field
        getView().setValue("anno0", Calendar.getInstance().get(Calendar.YEAR));
        getView().setValue("ultimopianoapprovato", null);
        addMessage("Clone.Success");
	}	
}
