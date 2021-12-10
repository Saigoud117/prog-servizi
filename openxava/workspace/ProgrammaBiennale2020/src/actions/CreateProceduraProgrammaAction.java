package actions;

import java.util.*;

import javax.persistence.*;

import org.apache.commons.beanutils.*;
import org.openxava.actions.*;
import org.openxava.jpa.*;
import org.openxava.util.*;

import ProgBien.*;

public class CreateProceduraProgrammaAction extends ViewBaseAction {
	public void execute() throws Exception {
		if (getView().getValue("cui") == null) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "you_must_enter_a_cui"
	            )
	        );
	    }
		
        String cui = getView().getValueString("cui"); // 1
        
        Procedure ent = (Procedure)getPreviousView().getEntity();
        EntityManager em = XPersistence.getManager();   
        ent = em.merge(ent);

        ProcedureProgramma copyProc = new ProcedureProgramma();
        
        BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);

        BeanUtils.copyProperties(copyProc, ent);
        
        copyProc.setCui(cui); 
        
        copyProc.setOid(UUID.randomUUID().toString().substring(0, 32));

        copyProc.setCoperture(new ArrayList<CopertureProgramma>());
        copyProc.setCoperturericomprese(new ArrayList<CopertureRicompreseProgramma>());
        copyProc.setQuadroeconomico(new ArrayList<QuadroEconomicoProgramma>());

        em.persist(copyProc);

        for (QuadroEconomico q: ent.getQuadroeconomico())
        {
            QuadroEconomicoProgramma qCopy = new QuadroEconomicoProgramma();
            qCopy.setAliquotaiva(q.getAliquotaiva());
            qCopy.setAmount(q.getAmount());
            qCopy.setPercentualeA1(q.getPercentualeA1());
            qCopy.setPercentualeA2(q.getPercentualeA2());
            qCopy.setSottotipologia(q.getSottotipologia());
            qCopy.setCaratterizzazioneCopertura(q.getCaratterizzazioneCopertura());
            qCopy.setTipoCopertura(q.getTipoCopertura());
            qCopy.setCapitolo(q.getCapitolo());
            qCopy.setDescrizionevoce(q.getDescrizionevoce());
            qCopy.setImportoiva(q.getImportoiva());
            qCopy.setImportonetto(q.getImportonetto());
            qCopy.setPdc(q.getPdc());
            qCopy.setTipologia(q.getTipologia());            
            qCopy.setProcedura(copyProc);
            qCopy.setOid(null);
            XPersistence.getManager().persist(qCopy);
        }     

        for (Coperture c: ent.getCoperture())
        {
        	CopertureProgramma cCopy = new CopertureProgramma();
        	cCopy.setAltro(c.getAltro());
        	cCopy.setAnno(c.getAnno());
        	cCopy.setBilancio(c.getBilancio());
        	cCopy.setMutuo(c.getMutuo());
        	cCopy.setPatrimonio(c.getPatrimonio());
        	cCopy.setPrivati(c.getPrivati());
        	cCopy.setStanziato(c.getStanziato());
        	cCopy.setNonStanziato(c.getNonStanziato());
        	cCopy.setTotale(c.getTotale());
        	cCopy.setTrasfimmo(c.getTrasfimmo());
        	cCopy.setVincolate(c.getVincolate());
        	cCopy.setProcedura(copyProc);
            cCopy.setOid(null);
            XPersistence.getManager().persist(cCopy);
        }
        
        for (CopertureRicomprese c: ent.getCoperturericomprese())
        {
        	CopertureRicompreseProgramma cCopy = new CopertureRicompreseProgramma();
        	cCopy.setAltro(c.getAltro());
        	cCopy.setAnno(c.getAnno());
        	cCopy.setBilancio(c.getBilancio());
        	cCopy.setMutuo(c.getMutuo());
        	cCopy.setPatrimonio(c.getPatrimonio());
        	cCopy.setPrivati(c.getPrivati());
        	cCopy.setStanziato(c.getStanziato());
        	cCopy.setNonStanziato(c.getNonStanziato());
        	cCopy.setTotale(c.getTotale());
        	cCopy.setTrasfimmo(c.getTrasfimmo());
        	cCopy.setVincolate(c.getVincolate());
        	cCopy.setProcedura(copyProc);
            cCopy.setOid(null);
            XPersistence.getManager().persist(cCopy);
        }
        
        XPersistence.commit();     

        getPreviousView().setEditable(true);    
        getPreviousView().setKeyEditable(false);                  
        addMessage("Migration.Success");
 
        closeDialog();                                      // 3
    }
}
