package actions;

import java.util.*;

import javax.persistence.*;

import org.apache.commons.beanutils.*;
import org.openxava.actions.*;
import org.openxava.jpa.*;

import ProgBien.*;

public class DisaggregazioneProcedureAction extends ViewBaseAction {
	public void execute() throws Exception { 
		
		// throw exception se cui � null
		// nuovo accorpamento
		//String cui = getView().getValueString("cui"); 
		
		// nuovo accorpamento
        //AccorpamentoProcedure ent = (AccorpamentoProcedure)getPreviousView().getEntity();
		AccorpamentoProcedure ent = (AccorpamentoProcedure)getView().getEntity();
        EntityManager em = XPersistence.getManager();   
        ent = em.merge(ent);       
		
        for(AccorpamentoProcedureDettaglio d: ent.getAccorpamentoProcedureDettaglio()) {        	

            ProcedureProgramma copyProc = new ProcedureProgramma();

            BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);

            BeanUtils.copyProperties(copyProc, d.getProcedure());

            copyProc.setCoperture(new ArrayList<CopertureProgramma>());
            copyProc.setCoperturericomprese(new ArrayList<CopertureRicompreseProgramma>());
            copyProc.setQuadroeconomico(new ArrayList<QuadroEconomicoProgramma>());

            em.persist(copyProc);                     

            for (QuadroEconomico q: d.getProcedure().getQuadroeconomico())
            {
                QuadroEconomicoProgramma qCopy = new QuadroEconomicoProgramma();
                BeanUtils.copyProperties(qCopy, q);
                qCopy.setProcedura(copyProc);
                qCopy.setOid(null);
                XPersistence.getManager().persist(qCopy);
            }     

            for (Coperture c: d.getProcedure().getCoperture())
            {
                CopertureProgramma cCopy = new CopertureProgramma();
                BeanUtils.copyProperties(cCopy, c);
                cCopy.setProcedura(copyProc);
                cCopy.setOid(null);
                XPersistence.getManager().persist(cCopy);
            }  
            
            for (CopertureRicomprese c: d.getProcedure().getCoperturericomprese())
            {
                CopertureRicompreseProgramma cCopy = new CopertureRicompreseProgramma();
                BeanUtils.copyProperties(cCopy, c);
                cCopy.setProcedura(copyProc);
                cCopy.setOid(null);
                XPersistence.getManager().persist(cCopy);
            }  
        }      
        
        XPersistence.commit();     

        getView().setEditable(true);    
        getView().setKeyEditable(false);                  
        addMessage("Disaggregation.Success");
        
	}	
}
