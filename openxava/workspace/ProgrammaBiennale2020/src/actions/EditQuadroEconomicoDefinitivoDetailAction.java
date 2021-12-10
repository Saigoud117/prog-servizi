package actions;

import org.openxava.actions.*;

public class EditQuadroEconomicoDefinitivoDetailAction extends EditElementInCollectionAction {
	public void execute() throws Exception {
		try {
			super.execute();
			getView().setEditable(true);    
	        getView().setKeyEditable(false);    
	        getView().refresh();
	        getView().refreshCollections();
		} catch(Exception e) {
			if (util.ProgBienUtils.checkAdmin())
			{
				throw new javax.validation.ValidationException(
		            e.getCause().getMessage());
			}
			else
			{
				throw new javax.validation.ValidationException(
			            "save_before_change_quadro");
			}
		}
	}
}
