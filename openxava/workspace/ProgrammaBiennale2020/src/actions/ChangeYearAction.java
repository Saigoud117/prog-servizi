package actions;

import java.util.*;

import javax.persistence.*;

import org.apache.commons.beanutils.*;
import org.openxava.actions.*;
import org.openxava.jpa.*;
import org.openxava.model.*;
import org.openxava.util.*;

import ProgBien.*;

public class ChangeYearAction extends ViewBaseAction {
	 public void execute() throws Exception {
		if (getView().getValue("year") == null) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "you_must_enter_a_year"
	            )
	        );
	    }
        int year = getView().getValueInt("year"); // 1
        //int codiceinterno = getView().getValueInt("codiceinterno"); // 1
        
        Procedure ent = (Procedure)getPreviousView().getEntity();
        EntityManager em = XPersistence.getManager();   
        ent = em.merge(ent);

        Procedure copyProc = new Procedure();

        BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);

        BeanUtils.copyProperties(copyProc, ent);

        copyProc.setAnno0(year);  

        /*
        if (year >= Calendar.getInstance().get(Calendar.YEAR))
        {       
        	copyProc.setUltimopianoapprovato(null);
        }
        else
        {
        	copyProc.setUltimopianoapprovato(codiceinterno);
        }
        */
        
        copyProc.setUltimopianoapprovato(ent.getUltimopianoapprovato());

        copyProc.setCoperture(new ArrayList<Coperture>());
        copyProc.setCoperturericomprese(new ArrayList<CopertureRicomprese>());
        copyProc.setQuadroeconomico(new ArrayList<QuadroEconomico>());

        em.persist(copyProc);                     

        for (QuadroEconomico q: ent.getQuadroeconomico())
        {
            QuadroEconomico qCopy = new QuadroEconomico();
            BeanUtils.copyProperties(qCopy, q);
            qCopy.setProcedura(copyProc);
            qCopy.setOid(null);
            XPersistence.getManager().persist(qCopy);
        }     

        for (Coperture c: ent.getCoperture())
        {
            Coperture cCopy = new Coperture();
            BeanUtils.copyProperties(cCopy, c);
            cCopy.setProcedura(copyProc);
            cCopy.setOid(null);
            XPersistence.getManager().persist(cCopy);
        }  
        
        for (CopertureRicomprese c: ent.getCoperturericomprese())
        {
            CopertureRicomprese cCopy = new CopertureRicomprese();
            BeanUtils.copyProperties(cCopy, c);
            cCopy.setProcedura(copyProc);
            cCopy.setOid(null);
            XPersistence.getManager().persist(cCopy);
        }  

        XPersistence.commit();     

        Class<?> duplicableClass = MapFacade.findEntity(getPreviousView().getModelName(),  getPreviousView().getKeyValuesWithValue()).getClass();  

        // my primary key is a composite key declared in a class
        ProcedureKey pk = new ProcedureKey();
        pk.setAnno0(copyProc.getAnno0());
        pk.setCodiceinterno(copyProc.getCodiceinterno());

        Procedure duplicate = (Procedure) XPersistence.getManager().find(duplicableClass,pk);

        XPersistence.getManager().refresh(duplicate);       

        getPreviousView().reset();
        getPreviousView().setEditable(true);    
        getPreviousView().setKeyEditable(false);                  
        addMessage("Clone.Success");
 
        closeDialog();                                      // 3
    }
}
