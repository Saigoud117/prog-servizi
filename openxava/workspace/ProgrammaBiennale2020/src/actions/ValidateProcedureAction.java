package actions;

import org.openxava.actions.*;

public class ValidateProcedureAction extends ViewBaseAction  {
	public void execute() throws Exception {		
		
		showDialog();                                         // 2
        getView().setTitleId("entry_new_cui");           // 3
        // getView().setTitle("Entry the full address");      // 4
        getView().setModelName("CuiValidate");             // 5
        setControllers("PersistProcedureValidateAction", "Dialog");           // 6
        // addActions("AddFullAddress.add", "Dialog.cancel"); // 7                           
	}
}
