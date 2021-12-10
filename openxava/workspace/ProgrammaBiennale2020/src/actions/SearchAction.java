package actions;

import org.openxava.actions.*;

public class SearchAction extends BaseAction implements IChainAction { // It chains to another action

	public void execute() throws Exception { // Do nothing
		
	}
	
	public String getNextAction() throws Exception {
	    return getEnvironment() // To access an environment variables
	        .getValue("XAVA_SEARCH_ACTION");
	}
}
