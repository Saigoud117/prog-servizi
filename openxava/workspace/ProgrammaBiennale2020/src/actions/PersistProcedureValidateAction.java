package actions;

import java.util.*;

import javax.persistence.*;

import org.apache.commons.beanutils.*;
import org.openxava.actions.*;
import org.openxava.jpa.*;

import ProgBien.*;
public class PersistProcedureValidateAction extends ViewBaseAction {
	public void execute() throws Exception {
		boolean comfirmSame = (boolean) getView().getValue("confirmSame");
        String cui = getView().getValueString("cui"); // 1
        
        ProcedureProgramma ent = (ProcedureProgramma)getPreviousView().getEntity();
        EntityManager em = XPersistence.getManager();   
        ent = em.merge(ent);

        ProcedureDefinitive copyProc = new ProcedureDefinitive();
        
        BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);

        BeanUtils.copyProperties(copyProc, ent);
        
        if (!comfirmSame)
        	copyProc.setCui(cui); 
        
        copyProc.setOid(UUID.randomUUID().toString().substring(0, 32));
        
        StatoProgetto s = em.find(StatoProgetto.class, "C");
        
        copyProc.setStato(s);
        
        copyProc.setCoperture(new ArrayList<CopertureDefinitive>());
        copyProc.setCoperturericomprese(new ArrayList<CopertureRicompreseDefinitive>());
        copyProc.setQuadroeconomico(new ArrayList<QuadroEconomicoDefinitivo>());

        em.persist(copyProc);                     

        for (QuadroEconomicoProgramma q: ent.getQuadroeconomico())
        {
        	QuadroEconomicoDefinitivo qCopy = new QuadroEconomicoDefinitivo();
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

        for (CopertureProgramma c: ent.getCoperture())
        {
        	CopertureDefinitive cCopy = new CopertureDefinitive();
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
        
        for (CopertureRicompreseProgramma c: ent.getCoperturericomprese())
        {
        	CopertureRicompreseDefinitive cCopy = new CopertureRicompreseDefinitive();
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
