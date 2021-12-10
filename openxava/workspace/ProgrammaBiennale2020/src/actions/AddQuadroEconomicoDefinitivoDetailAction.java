package actions;

//import java.util.*;

import org.openxava.actions.*;
//import org.openxava.util.*;

//import ProgBien.*;

public class AddQuadroEconomicoDefinitivoDetailAction extends CreateNewElementInCollectionAction {
	public void execute() throws Exception {
		//ProcedureDefinitive procedura = (ProcedureDefinitive)getView().getEntity();
		try {
			super.execute();
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
