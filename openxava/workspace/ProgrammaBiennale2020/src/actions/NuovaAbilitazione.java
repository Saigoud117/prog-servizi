package actions;

import org.openxava.actions.*;

public class NuovaAbilitazione extends ViewBaseAction {
	public void execute() throws Exception {
		showDialog();
        getView().setModelName("Inquadrato");
        setControllers("NuovoReferente", "Dialog");
	}
}
