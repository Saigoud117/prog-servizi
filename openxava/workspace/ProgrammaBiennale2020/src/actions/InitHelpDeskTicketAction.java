package actions;

import org.openxava.actions.*;


public class InitHelpDeskTicketAction extends ViewBaseAction {
    @Override
    public void execute() throws Exception {
    	if (getView().getModelName() != null && getView().getModelName().equals("TicketHelpDesk") && getView().getValue("stato") != null) {    		
	    	if (!getView().getValue("stato.key").equals("N") || (getView().getValue("stato.key").equals("N") && getView().getValue("dataCreazione") != null))
	    		getView().setEditable("tipo",false);
    	}
    }
}