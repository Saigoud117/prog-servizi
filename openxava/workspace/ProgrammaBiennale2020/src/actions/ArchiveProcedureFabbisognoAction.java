package actions;

import java.util.*;

import javax.persistence.*;

import org.apache.commons.beanutils.*;
import org.openxava.actions.*;
import org.openxava.jpa.*;

import ProgBien.*;
import util.*;

public class ArchiveProcedureFabbisognoAction extends ViewBaseAction  {
	public void execute() throws Exception {	
		
		Pubblicazione ent = (Pubblicazione)getView().getEntity();
        EntityManager em = XPersistence.getManager();   
        ent = em.merge(ent);
        
        @SuppressWarnings("unchecked")
		List<ArchivioPubblicazioni> exist = (List<ArchivioPubblicazioni>)XPersistence.getManager()
			    .createQuery(
			        "from ArchivioPubblicazioni p where p.pubblicazione.id = :id")  // JPQL query
			    .setParameter("id", ent.getId())
			    .getResultList();
        
        if (exist.size() > 0)
        	throw new javax.validation.ValidationException(
		            "publication_already_archived");
        
        if (ent.getIdRicevuto() == null || ent.getIdRicevuto().isEmpty())
        	throw new javax.validation.ValidationException(
		            "archiviate_on_valid_publication");
        
		if (!ent.isAggiornamento()) {
			// se non aggiornamento queste vanno archiviate e messe nell'archivio ufficiale e copiate in procedure definitive con il nuovo cui
			@SuppressWarnings("unchecked")
			List<Procedure> ProcedureFabbisogno = (List<Procedure>)XPersistence.getManager()
				    .createQuery(
				    		"from Procedure p where p.stato.key in ('M', 'N', 'Z') and p.archived = false and p.deleted = false and (p.valorestimatoappalto >= 40000.00 or (p.valorestimatoappalto < 40000.00 and p.ultimopianoapprovato is not null)) order by p.codiceinterno ASC")  // JPQL query
				    .getResultList();
			
			// NO! - se non aggiornamento queste vanno spostate in ProcedureProgramma
			// aggiornamento 27/05/2020 vanno archiviate e  messe in Procedure Definitive ma senza cui
			@SuppressWarnings("unchecked")
			List<Procedure> ProcedureMinoriSoglia = (List<Procedure>)XPersistence.getManager()
				    .createQuery(
				        "from Procedure p where p.stato.key in ('M', 'N') and p.deleted = false and p.archived = false and p.valorestimatoappalto < 40000.00 and p.ultimopianoapprovato is null order by p.codiceinterno ASC")  // JPQL query
				    .getResultList();
			
			// se non aggiornamento queste vanno archiviate
			@SuppressWarnings("unchecked")
			List<Procedure> ProcedureAggregate = (List<Procedure>)XPersistence.getManager()
				    .createQuery(
				        "from Procedure p where p.stato.key = 'G' and p.deleted = false and p.archived = false order by p.codiceinterno ASC")  // JPQL query
				    .getResultList();
			
			// queste non vanno toccate
			/*
			@SuppressWarnings("unchecked")
			List<ProcedureDefinitive> ProcedureConfermate = (List<ProcedureDefinitive>)XPersistence.getManager()
				    .createQuery(
				        "from ProcedureDefinitive p where p.stato.key = 'C' and p.archived = false and p.deleted = false ")  // JPQL query
				    .getResultList();
			*/
			
			// se non aggiornamento queste vanno archiviate e messe nell'archivio ufficiale
			// aggiornamento 28/05/2020 prima vanno messe dentro procedure definitive
			@SuppressWarnings("unchecked")
			List<Procedure> ProcedureNonRiproposte = (List<Procedure>)XPersistence.getManager()
				    .createQuery(
				        "from Procedure p where p.stato.key = 'K' and p.archived = false and p.deleted = false  and p.valorestimatoappalto >= 40000.00")  // JPQL query
				    .getResultList();
			
			// se non aggiornamento queste vanno archiviate
			// aggiornamento 28/05/2020 vanno divise, le A prima vanno messe dentro procedure definitive, le M vanno solo archiviate
			@SuppressWarnings("unchecked")
			List<ProcedureDefinitive> ProcedureRiproposte = (List<ProcedureDefinitive>)XPersistence.getManager()
				    .createQuery(
				        "from ProcedureDefinitive p where p.stato.key in ('M') and p.archived = false and p.deleted = false")  // JPQL query
				    .getResultList();
			
			// se non aggiornamento queste vanno archiviate
			// aggiornamento 12/03/2021 queste vanno archiviate
			@SuppressWarnings("unchecked")
			List<ProcedureDefinitive> ProcedureDefinitiveNonRiproposte = (List<ProcedureDefinitive>)XPersistence.getManager()
				    .createQuery(
				        "from ProcedureDefinitive p where p.stato.key in ('K') and p.archived = false and p.deleted = false")  // JPQL query
				    .getResultList();
			
			// le procedure infra 40k che non sono state toccate sono o avviate o non riproposte
			// aggiornamento 09/02/2021 quindi vanno archiviate			
			@SuppressWarnings("unchecked")
			List<ProcedureDefinitive> ProcedureInfra40NonRiproposte = (List<ProcedureDefinitive>)XPersistence.getManager()
				    .createQuery(
				        "from ProcedureDefinitive p where p.stato.key in ('C') and p.cui like '~MINORI-SOGLIA-40K~' and p.archived = false and p.deleted = false")  // JPQL query
				    .getResultList();
			
			@SuppressWarnings("unchecked")
			List<Procedure> ProcedureAvviate = (List<Procedure>)XPersistence.getManager()
				    .createQuery(
				        "from Procedure p where p.stato.key in ('A') and p.archived = false and p.deleted = false")  // JPQL query
				    .getResultList();
	        
	        ArchivioPubblicazioni a = new ArchivioPubblicazioni();
	        a.setPubblicazione(ent);
	        a.setArchivioredazionedettaglio(new ArrayList<ArchivioRedazioneDettaglio>());    
	        XPersistence.getManager().persist(a);
	        
	        for(ProcedureDefinitive p: ProcedureRiproposte)
	        {
	        	p.setArchived(true);
	        }
	        
	        // aggiornamento 09/02/2020
	        for(ProcedureDefinitive p: ProcedureInfra40NonRiproposte)
	        {
	        	p.setArchived(true);
	        }
	        
	        for(Procedure p: ProcedureAggregate)
	        {
	        	p.setArchived(true);
	        }
	        
	        for(ProcedureDefinitive p: ProcedureDefinitiveNonRiproposte)
	        {
	        	ArchivioNonRiproposteDettaglio d = new ArchivioNonRiproposteDettaglio();
	        	d.setProcedure(p);
	        	d.setArchivio(a);
	        	d.setOid(null);
	        	XPersistence.getManager().persist(d);
	        	p.setArchived(true);
	        }
	        
	        for(Procedure p: ProcedureNonRiproposte)
	        {
	        	/*
	        	ArchivioNonRiproposteDettaglio d = new ArchivioNonRiproposteDettaglio();
	        	d.setProcedure(p);
	        	d.setArchivio(a);
	        	d.setOid(null);
	        	XPersistence.getManager().persist(d);
	        	p.setArchived(true);
	        	*/
	        	
	        	ProcedureDefinitive copyProc = new ProcedureDefinitive();
		        
		        BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
	
		        BeanUtils.copyProperties(copyProc, p);
		        
		        copyProc.setCui(p.getUltimopianoapprovato().getCui());
			    p.setCui(p.getUltimopianoapprovato().getCui());
	
		        copyProc.setCoperture(new ArrayList<CopertureDefinitive>());
		        copyProc.setCoperturericomprese(new ArrayList<CopertureRicompreseDefinitive>());
		        copyProc.setQuadroeconomico(new ArrayList<QuadroEconomicoDefinitivo>());
	
		        XPersistence.getManager().persist(copyProc);
	
		        for (QuadroEconomico q: p.getQuadroeconomico())
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
	
		        for (Coperture c: p.getCoperture())
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
		        
		        for (CopertureRicomprese c: p.getCoperturericomprese())
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
		        
		        ArchivioNonRiproposteDettaglio d = new ArchivioNonRiproposteDettaglio();
	        	d.setProcedure(copyProc);
	        	d.setArchivio(a);
	        	d.setOid(null);
	        	XPersistence.getManager().persist(d);
		        
	        	p.setArchived(true);
	        	copyProc.setArchived(true);
	        }
	        
	        for(Procedure p: ProcedureAvviate)
	        {
	        	
	        	ProcedureDefinitive copyProc = new ProcedureDefinitive();
		        
		        BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
	
		        BeanUtils.copyProperties(copyProc, p);
		        
		        copyProc.setCui(p.getUltimopianoapprovato().getCui());
			    p.setCui(p.getUltimopianoapprovato().getCui());
	
		        copyProc.setCoperture(new ArrayList<CopertureDefinitive>());
		        copyProc.setCoperturericomprese(new ArrayList<CopertureRicompreseDefinitive>());
		        copyProc.setQuadroeconomico(new ArrayList<QuadroEconomicoDefinitivo>());
	
		        XPersistence.getManager().persist(copyProc);
	
		        for (QuadroEconomico q: p.getQuadroeconomico())
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
	
		        for (Coperture c: p.getCoperture())
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
		        
		        for (CopertureRicomprese c: p.getCoperturericomprese())
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
		        
		        p.setArchived(true);
	        	copyProc.setArchived(true);
	        }
	        	        
	        for(Procedure p: ProcedureMinoriSoglia)
	        {
        		ProcedureDefinitive copyProc = new ProcedureDefinitive();
		        
		        BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
	
		        BeanUtils.copyProperties(copyProc, p);
		        
		        // definisco un cui fittizio uguale per tutte quelle minori di soglia
		        copyProc.setCui("~MINORI-SOGLIA-40K~"); 
		        copyProc.setCancellazione(false);
		        copyProc.setOid(null);
		        
		        StatoProgetto s = em.find(StatoProgetto.class, "C");
		        
		        copyProc.setStato(s);
	
		        copyProc.setCoperture(new ArrayList<CopertureDefinitive>());
		        copyProc.setCoperturericomprese(new ArrayList<CopertureRicompreseDefinitive>());
		        copyProc.setQuadroeconomico(new ArrayList<QuadroEconomicoDefinitivo>());
	
		        XPersistence.getManager().persist(copyProc);
	
		        for (QuadroEconomico q: p.getQuadroeconomico())
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
	
		        for (Coperture c: p.getCoperture())
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
		        
		        for (CopertureRicomprese c: p.getCoperturericomprese())
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
	        	
		        p.setArchived(true);
	        }

	        int i = 1;
	        for(Procedure p: ProcedureFabbisogno)
	        {
	        	ArchivioRedazioneDettaglio d = new ArchivioRedazioneDettaglio();
	        	d.setProcedure(p);
	        	d.setArchivio(a);
	        	d.setOid(null);
	        	XPersistence.getManager().persist(d);
	        	
	        	ProcedureDefinitive copyProc = new ProcedureDefinitive();
		        
		        BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
	
		        BeanUtils.copyProperties(copyProc, p);
		        
		        if (p.getUltimopianoapprovato() != null) {
		        	copyProc.setCui(p.getUltimopianoapprovato().getCui());
			        p.setCui(p.getUltimopianoapprovato().getCui());
				}
				else {
					copyProc.setCui(p.getSettore().toString().toUpperCase().substring(0, 1).concat(ProgrammaBiennalePreferences.getDefaultCfSA()).concat(p.getAnno0().toString()).concat(util.ProgBienUtils.CodiceInterno(Integer.toString(i))));
			        p.setCui(p.getSettore().toString().toUpperCase().substring(0, 1).concat(ProgrammaBiennalePreferences.getDefaultCfSA()).concat(p.getAnno0().toString()).concat(util.ProgBienUtils.CodiceInterno(Integer.toString(i))));
					i++;
				}
		        
		        StatoProgetto s = em.find(StatoProgetto.class, "C");
		        
		        copyProc.setStato(s);
	
		        copyProc.setCoperture(new ArrayList<CopertureDefinitive>());
		        copyProc.setCoperturericomprese(new ArrayList<CopertureRicompreseDefinitive>());
		        copyProc.setQuadroeconomico(new ArrayList<QuadroEconomicoDefinitivo>());
	
		        XPersistence.getManager().persist(copyProc);
	
		        for (QuadroEconomico q: p.getQuadroeconomico())
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
	
		        for (Coperture c: p.getCoperture())
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
		        
		        for (CopertureRicomprese c: p.getCoperturericomprese())
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
	        	p.setArchived(true);
	        }
		} else if (ent.isAggiornamento()) {
			// qui ho un dubbio, con che stato devono tornare sulle definitive
			
			// se aggiornamento queste vanno archiviate e messe nell'archivio ufficiale e copiate in procedure definitive con il nuovo cui
			@SuppressWarnings("unchecked")
			List<ProcedureProgramma> ProcedureModificheFabbisogno = (List<ProcedureProgramma>)XPersistence.getManager()
				    .createQuery(
				        "from ProcedureProgramma p where p.stato.key in ('M', 'N', 'Z') and p.archived = false and p.deleted = false and (p.valorestimatoappalto >= 40000.00 or (p.valorestimatoappalto < 40000.00 and p.variante is not null and p.ultimopianoapprovato is not null)) order by p.oid ASC")  // JPQL query
				    .getResultList();
			
			// aggiornamento 27/05/2020 vanno archiviate e messe in Procedure Definitive ma senza cui
			@SuppressWarnings("unchecked")
			List<ProcedureProgramma> ProcedureMinoriSoglia = (List<ProcedureProgramma>)XPersistence.getManager()
				    .createQuery(
				    		"from ProcedureProgramma p where p.stato.key in ('M', 'N') and p.deleted = false and p.archived = false and p.valorestimatoappalto < 40000.00 and (p.variante is null or (p.variante is not null and p.ultimopianoapprovato is null)) order by p.oid ASC") // JPQL query
				    .getResultList();
			
			// se aggiornamento queste vanno archiviate
			@SuppressWarnings("unchecked")
			List<ProcedureProgramma> ProcedureAggregate = (List<ProcedureProgramma>)XPersistence.getManager()
				    .createQuery(
				        "from ProcedureProgramma p where p.stato.key = 'G' and p.deleted = false and p.archived = false order by p.oid ASC")  // JPQL query
				    .getResultList();
			
			// se aggiornamento queste vanno archiviate
			@SuppressWarnings("unchecked")
			List<ProcedureDefinitive> ProcedureModificate = (List<ProcedureDefinitive>)XPersistence.getManager()
				    .createQuery(
				        "from ProcedureDefinitive p where p.stato.key in ('M') and p.archived = false and p.deleted = false")  // JPQL query
				    .getResultList();
			
			ArchivioPubblicazioni a = new ArchivioPubblicazioni();
	        a.setPubblicazione(ent);
	        a.setArchiviomodifichedettaglio(new ArrayList<ArchivioModificaDettaglio>());    
	        XPersistence.getManager().persist(a);
	        
	        for(ProcedureDefinitive p: ProcedureModificate)
	        {
	        	p.setArchived(true);
	        }
	        
	        for(ProcedureProgramma p: ProcedureAggregate)
	        {
	        	p.setArchived(true);
	        }
	        
	        for(ProcedureProgramma p: ProcedureMinoriSoglia)
	        {
	        	// Modifica 27/05/2020 
        		ProcedureDefinitive copyProc = new ProcedureDefinitive();
		        
		        BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
	
		        BeanUtils.copyProperties(copyProc, p);
		        
		        // definisco un cui fittizio uguale per tutte quelle minori di soglia
		        copyProc.setCui("~MINORI-SOGLIA-40K~"); 
		        copyProc.setCancellazione(false);
		        copyProc.setVariante(null);
		        copyProc.setOid(null);
		        
		        StatoProgetto s = em.find(StatoProgetto.class, "C");
		        
		        copyProc.setStato(s);
	
		        copyProc.setCoperture(new ArrayList<CopertureDefinitive>());
		        copyProc.setCoperturericomprese(new ArrayList<CopertureRicompreseDefinitive>());
		        copyProc.setQuadroeconomico(new ArrayList<QuadroEconomicoDefinitivo>());
	
		        XPersistence.getManager().persist(copyProc);
	
		        for (QuadroEconomicoProgramma q: p.getQuadroeconomico())
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
	
		        for (CopertureProgramma c: p.getCoperture())
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
		        
		        for (CopertureRicompreseProgramma c: p.getCoperturericomprese())
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
	        	
		        p.setArchived(true);
	        }
			
	        // mi serve trovare l'ultima procedura salvata per l'id prima di iniziare con il processo di update
			ProcedureDefinitive ultimaProcedura = (ProcedureDefinitive)XPersistence.getManager()
				    .createQuery(
				        "from ProcedureDefinitive p where p.archived = false " + 
				        "and p.deleted = false " + 
				        "and p.cui <> '~MINORI-SOGLIA-40K~' " +
				        //"and p.valorestimatoappalto >= 40000.00 " + 
				        "and substring(p.cui, 13, 4) = :anno " + 
				        "order by substring(p.cui, 17, 22) desc")  // JPQL query
				    .setParameter("anno", String.valueOf(Calendar.getInstance().get(Calendar.YEAR)))
				    .setMaxResults(1)
				    .getSingleResult();
	        
			int i = util.ProgBienUtils.getSerialeFromCui(ultimaProcedura.getCui()) + 1;
	        for(ProcedureProgramma p: ProcedureModificheFabbisogno)
	        {
	        	ArchivioModificaDettaglio d = new ArchivioModificaDettaglio();
	        	d.setProcedure(p);
	        	d.setArchivio(a);
	        	d.setOid(null);
	        	XPersistence.getManager().persist(d);
	        	
	        	ProcedureDefinitive copyProc = new ProcedureDefinitive();
		        
		        BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
	
		        BeanUtils.copyProperties(copyProc, p);
		        
		        if (p.getUltimopianoapprovato() != null) {
		        	copyProc.setCui(p.getUltimopianoapprovato().getCui());
			        p.setCui(p.getUltimopianoapprovato().getCui());
				}
				else {
					copyProc.setCui(p.getSettore().toString().toUpperCase().substring(0, 1).concat(ProgrammaBiennalePreferences.getDefaultCfSA()).concat(p.getAnno0().toString()).concat(util.ProgBienUtils.CodiceInterno(Integer.toString(i))));
			        p.setCui(p.getSettore().toString().toUpperCase().substring(0, 1).concat(ProgrammaBiennalePreferences.getDefaultCfSA()).concat(p.getAnno0().toString()).concat(util.ProgBienUtils.CodiceInterno(Integer.toString(i))));
					i++;
				}
		        
		        copyProc.setOid(null);
		        copyProc.setVariante(null);
		        
		        StatoProgetto s = em.find(StatoProgetto.class, "C");
		        
		        StatoProgetto k = em.find(StatoProgetto.class, "K");
		        
		        if (p.isCancellazione())
		        	copyProc.setStato(k);
		        else
		        	copyProc.setStato(s);
		        
		        // la non riproposizione non va attivata in questa fase, per� posso prendere le note della cancellazione
		        if (p.isCancellazione())
		        	copyProc.setNonriproposta(true);
		        else
		        	copyProc.setNonriproposta(false);
		        copyProc.setNonriproposta(false);
		        copyProc.setNotenonriproposta(p.getNotenonriproposta());
	
		        copyProc.setCoperture(new ArrayList<CopertureDefinitive>());
		        copyProc.setCoperturericomprese(new ArrayList<CopertureRicompreseDefinitive>());
		        copyProc.setQuadroeconomico(new ArrayList<QuadroEconomicoDefinitivo>());
	
		        XPersistence.getManager().persist(copyProc);
	
		        for (QuadroEconomicoProgramma q: p.getQuadroeconomico())
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
	
		        for (CopertureProgramma c: p.getCoperture())
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
		        
		        for (CopertureRicompreseProgramma c: p.getCoperturericomprese())
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
	        	p.setArchived(true);
	        }
        }
		try {
			XPersistence.commit();
			addMessage("ArchiveSuccess");
		}
		catch (RollbackException ex) {
			ex.printStackTrace();
			addError("ArchiveError");
		}
	}
}
