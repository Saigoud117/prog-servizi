package actions;

import org.openxava.actions.*;

public class CaricaProcedureDefinitive extends ViewBaseAction {
	public void execute() throws Exception {
        showDialog();                                         // 2
        //getView().setTitleId("entry_profile");           // 3
        // getView().setTitle("Entry the full address");      // 4
        getView().setModelName("CaricaProcedure");             // 5
        if (getPreviousView().getModelName().equals("Procedure"))
        	setControllers("CaricaProcedureFabbisogno", "Dialog");           // 6
        else if (getPreviousView().getModelName().equals("ProcedureProgramma"))
        	setControllers("CaricaProcedureProgramma", "Dialog");
        // addActions("AddFullAddress.add", "Dialog.cancel"); // 7
    }
}