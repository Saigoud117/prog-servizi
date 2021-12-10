package actions;

import java.util.*;

import org.openxava.actions.*;
import org.openxava.model.*;

public class DeleteAction extends ViewBaseAction implements IChainAction {
	
	private String nextAction = null;

	public void execute() throws Exception {
		if (!getView().getMetaModel().containsMetaProperty("deleted")) {
			nextAction = "CRUD.delete";
			return;
		}
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("deleted", true);
		MapFacade.setValues(getModelName(), getView().getKeyValues(), values);
		resetDescriptionsCache();
		addMessage("object_deleted", getModelName());
		getView().clear();
		getView().setKeyEditable(true);
		getView().setEditable(true);
	}

	public String getNextAction() throws Exception {
		return nextAction;
	}
}