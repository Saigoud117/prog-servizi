package actions;

import org.openxava.actions.*;

public class DuplicatePersistAction extends ViewBaseAction {

	public void execute() throws Exception {		
		
		showDialog();                                         // 2
        getView().setTitleId("entry_new_year");           // 3
        // getView().setTitle("Entry the full address");      // 4
        getView().setModelName("YearUpdate");             // 5
        setControllers("ChangeYear", "Dialog");           // 6
        // addActions("AddFullAddress.add", "Dialog.cancel"); // 7                           
	}
}