package com.openxava.naviox.actions;

import org.openxava.actions.*;
import org.openxava.util.*;
import com.openxava.naviox.util.*;

/**
 * 
 * @author Javier Paniza
 */
public class WarnHistoryNotEnabledAction extends BaseAction {

	public void execute() throws Exception {
		if (!HistoryAccessTrackerProvider.class.getName().equals(XavaPreferences.getInstance().getAccessTrackerProvidersClasses())) {
			addWarning("history_not_enabled", "'" + HistoryAccessTrackerProvider.class.getName() + "'");
		}
	}

}
