package actions;

import java.util.*;

import org.openxava.actions.*;
import org.openxava.model.*;
import org.openxava.validators.*;

import ProgBien.*;

public class ArchiveSelected extends TabBaseAction {
	public void execute() throws Exception {
	    markSelectedEntitiesAsArchived(); // The logic to mark the selected rows
	        // as deleted objects
	}
	
	private void markSelectedEntitiesAsArchived() throws Exception {
		Map<String, Object> values = new HashMap<String, Object>();
        // values.put("deleted", true); // Instead of a hardcoded true, we use
        values.put("archived", true); // the restore property value
	    @SuppressWarnings("unchecked")
		Map<String, Object>[] selectedOnes = getSelectedKeys(); // We get the selected rows
	    if (selectedOnes != null) {
	        for (int i = 0; i < selectedOnes.length; i++) { // Loop over all selected rows
	            Map<String, Object> key = selectedOnes[i]; // We obtain the key of each entity
	            ProcedureDefinitive p = (ProcedureDefinitive)MapFacade.findEntity("ProcedureDefinitive", key);
	            if (p.getCui().equals("~MINORI-SOGLIA-40K~"))
	            {
		            try {
		                MapFacade.setValues(  // Each entity is modified
		                    getTab().getModelName(),
		                    key,
		                    values);
		            }
		            catch (ValidationException ex) {  // If there is a ValidationException...
		                addError("no_archive_row", new Integer(i), key);
		                addErrors(ex.getErrors()); // ...we show the messages
		            }
		            catch (Exception ex) { //  If any other exception is thrown, a generic
		                addError("no_archive_row", new Integer(i), key); // message is added
		            }
	            }
	        }
	    }
	    getTab().deselectAll(); // After removing we deselect the rows
	    resetDescriptionsCache(); // And reset the cache for combos for this user
	}
}
