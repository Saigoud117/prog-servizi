package com.openxava.naviox.util; 

import java.util.*;
import org.openxava.util.*;
import com.openxava.naviox.model.*;

/**
 * 
 * @author Javier Paniza
 */
public class HistoryAccessTrackerProvider implements IAccessTrackerProvider {
	

	public void consulted(String modelName, Map key) {
		History.consulted(modelName, key);
	}


	public void created(String modelName, Map key) {
		History.created(modelName, key);
	}

	public void modified(String modelName, Map key, Map<String, Object> oldChangedValues, Map<String, Object> newChangedValues) {
		StringBuffer changes = new StringBuffer(); 
		for (String property: oldChangedValues.keySet()) {
			if (changes.length() > 0) changes.append(", ");
			changes.append(Labels.getQualified(property));
			changes.append(": ");
			changes.append(Strings.toString(oldChangedValues.get(property)));
			changes.append(" --> ");
			changes.append(Strings.toString(newChangedValues.get(property)));
		}
		History.modified(modelName, key, changes.toString());
	}

	public void removed(String modelName, Map key) {
		History.removed(modelName, key);
	}
		
}
