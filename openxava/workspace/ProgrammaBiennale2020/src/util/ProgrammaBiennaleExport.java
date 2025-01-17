package util;

import java.math.*;
import java.text.*;
import java.util.*;

import org.openxava.jpa.*;

import EsportazioneMitProg.*;
import ProgBien.*;

public class ProgrammaBiennaleExport {
	public static PubblicazioneFornitureServizi getDatiPubblicazione(Pubblicazione ent, List<Procedure> ProcedureFabbisogno,
			List<ProcedureDefinitive> ProcedureConfermate, List<Procedure> ProcedureNonRiproposte, List<ProcedureDefinitive> ProcedureCancellateAggiornamentiAnnuali) {
		Referente r = new Referente();
		r.setCognome(ent.getReferente().getCognome());
		r.setNome(ent.getReferente().getCognome());
		r.setCfPiva(ent.getReferente().getCf());
		
		PubblicazioneFornitureServizi p = new PubblicazioneFornitureServizi();		
		p.setId(ent.getId());
		p.setCodiceFiscaleSA(ent.getCodiceFiscaleSA());
		p.setUfficio(ent.getUfficio());
		p.setAnno(ent.getAnno());
		p.setDescrizione(ent.getDescrizione());
		p.setNumeroApprovazione(ent.getNumeroApprovazione());
		
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		p.setDataApprovazione(String.valueOf(formatter.format(ent.getDataApprovazione())));
		p.setDataPubblicazioneApprovazione(String.valueOf(formatter.format(ent.getDataPubblicazioneApprovazione())));
		p.setTitoloAttoApprovazione(ent.getTitoloAttoApprovazione());
		p.setUrlAttoApprovazione(ent.getUrlApprovazione());
		p.setReferente(r);
		if (ProcedureFabbisogno.size() > 0 || ProcedureConfermate.size() > 0)
			p.setAcquisti(mapperFornitureServizi(ProcedureFabbisogno, ProcedureConfermate, Calendar.getInstance().get(Calendar.YEAR)));
		if (ProcedureNonRiproposte.size() > 0 || ProcedureCancellateAggiornamentiAnnuali.size() > 0)
			p.setAcquistiNonRiproposti(mapperAcquistiNonRiproposti(ProcedureNonRiproposte, ProcedureCancellateAggiornamentiAnnuali));
		return p;
	}
	
	public static PubblicazioneFornitureServizi getDatiModifichePubblicazione(Pubblicazione ent, List<ProcedureProgramma> ProcedureModifiche,
			List<ProcedureDefinitive> ProcedureConfermate) {
		Referente r = new Referente();
		r.setCognome(ent.getReferente().getCognome());
		r.setNome(ent.getReferente().getCognome());
		r.setCfPiva(ent.getReferente().getCf());
		
		PubblicazioneFornitureServizi p = new PubblicazioneFornitureServizi();		
		p.setId(ent.getId());
		p.setCodiceFiscaleSA(ent.getCodiceFiscaleSA());
		p.setUfficio(ent.getUfficio());
		p.setAnno(ent.getAnno());
		p.setDescrizione(ent.getDescrizione());
		p.setNumeroApprovazione(ent.getNumeroApprovazione());
		
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		p.setDataApprovazione(String.valueOf(formatter.format(ent.getDataApprovazione())));
		p.setDataPubblicazioneApprovazione(String.valueOf(formatter.format(ent.getDataPubblicazioneApprovazione())));
		p.setTitoloAttoApprovazione(ent.getTitoloAttoApprovazione());
		p.setUrlAttoApprovazione(ent.getUrlApprovazione());
		p.setReferente(r);
		if (ProcedureModifiche.size() > 0 || ProcedureConfermate.size() > 0)
			p.setAcquisti(mapperModificheFornitureServizi(ProcedureModifiche, ProcedureConfermate, Calendar.getInstance().get(Calendar.YEAR)));		
		return p;
	}
	
	public static PubblicazioneFornitureServizi getDatiModifichePubblicazioneEstrazione(Pubblicazione ent, List<ProcedureProgramma> ProcedureModifiche,
			List<ProcedureDefinitive> ProcedureConfermate, List<ProcedureProgramma> ProcedureCancellate) {
		Referente r = new Referente();
		r.setCognome(ent.getReferente().getCognome());
		r.setNome(ent.getReferente().getCognome());
		r.setCfPiva(ent.getReferente().getCf());
		
		PubblicazioneFornitureServizi p = new PubblicazioneFornitureServizi();		
		p.setId(ent.getId());
		p.setCodiceFiscaleSA(ent.getCodiceFiscaleSA());
		p.setUfficio(ent.getUfficio());
		p.setAnno(ent.getAnno());
		p.setDescrizione(ent.getDescrizione());
		p.setNumeroApprovazione(ent.getNumeroApprovazione());
		
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		p.setDataApprovazione(String.valueOf(formatter.format(ent.getDataApprovazione())));
		p.setDataPubblicazioneApprovazione(String.valueOf(formatter.format(ent.getDataPubblicazioneApprovazione())));
		p.setTitoloAttoApprovazione(ent.getTitoloAttoApprovazione());
		p.setUrlAttoApprovazione(ent.getUrlApprovazione());
		p.setReferente(r);
		if (ProcedureModifiche.size() > 0 || ProcedureConfermate.size() > 0)
			p.setAcquisti(mapperModificheFornitureServizi(ProcedureModifiche, ProcedureConfermate, Calendar.getInstance().get(Calendar.YEAR)));	
		if (ProcedureCancellate.size() > 0)
			p.setAcquistiNonRiproposti(mapperAcquistiCancellati(ProcedureCancellate));
		return p;
	}
	
	private static ArrayList<AcquistiNonRiproposti> mapperAcquistiNonRiproposti(List<Procedure> procedure, List<ProcedureDefinitive> definitive) {
		ArrayList<AcquistiNonRiproposti> acquisti = new ArrayList<AcquistiNonRiproposti>();
		
		for (Procedure p: procedure) {
			AcquistiNonRiproposti a = new AcquistiNonRiproposti();
			a.setCui(p.getCui());
			if (p.getCup() != null && !p.getCup().isEmpty())
			a.setCup(p.getCup());
			a.setDescrizione(p.getDescrizione());
			a.setImporto(p.getTotaleCoperture().setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			switch (p.getPriorita().toString()) {
				case "max":
					a.setPriorita("1");
					break;
				case "media":
					a.setPriorita("2");
				case "min":
					a.setPriorita("3");
				default:
					break;
			}
			a.setMotivo(p.getNotenonriproposta());	
			acquisti.add(a);
		}
		
		for (ProcedureDefinitive p: definitive) {
			AcquistiNonRiproposti a = new AcquistiNonRiproposti();
			a.setCui(p.getCui());
			if (p.getCup() != null && !p.getCup().isEmpty())
			a.setCup(p.getCup());
			a.setDescrizione(p.getDescrizione());
			a.setImporto(p.getTotaleCoperture().setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			switch (p.getPriorita().toString()) {
				case "max":
					a.setPriorita("1");
					break;
				case "media":
					a.setPriorita("2");
				case "min":
					a.setPriorita("3");
				default:
					break;
			}
			a.setMotivo(p.getNotenonriproposta());	
			acquisti.add(a);
		}
		
		return acquisti;
	}
	
	private static ArrayList<AcquistiNonRiproposti> mapperAcquistiCancellati(List<ProcedureProgramma> procedure) {
		ArrayList<AcquistiNonRiproposti> acquisti = new ArrayList<AcquistiNonRiproposti>();
		
		for (ProcedureProgramma p: procedure) {
			AcquistiNonRiproposti a = new AcquistiNonRiproposti();
			a.setCui(p.getCui());
			if (p.getCup() != null && !p.getCup().isEmpty())
			a.setCup(p.getCup());
			a.setDescrizione(p.getDescrizione());
			a.setImporto(p.getTotaleCoperture().setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			switch (p.getPriorita().toString()) {
				case "max":
					a.setPriorita("1");
					break;
				case "media":
					a.setPriorita("2");
				case "min":
					a.setPriorita("3");
				default:
					break;
			}
			a.setMotivo(p.getNotenonriproposta());	
			acquisti.add(a);
		}
		return acquisti;
	}
	
	private static ArrayList<Acquisti> mapperFornitureServizi(List<Procedure> procedure, List<ProcedureDefinitive> proceduredefinitive, int anniRifMan) {
		
		ArrayList<Acquisti> acquisti = new ArrayList<Acquisti>();
		
		int i = 1;
		for (Procedure p: procedure) {
			Acquisti a = new Acquisti();
			if (p.getUltimopianoapprovato() != null) {
				a.setCui(p.getUltimopianoapprovato().getCui());
			}
			else {
				a.setCui(p.getSettore().toString().toUpperCase().substring(0, 1).concat(ProgrammaBiennalePreferences.getDefaultCfSA()).concat(p.getAnno0().toString()).concat(ProgBienUtils.CodiceInterno(Integer.toString(i))));
				i++;
			}
			a.setSettore(p.getSettore().toString().substring(0, 1));
			a.setCodiceInterno(p.getCodiceinterno().toString());
			a.setDescrizione(p.getDescrizione());
			Calendar calendar = Calendar.getInstance();
	        calendar.setTime(p.getData());
	        
	        if (calendar.get(Calendar.YEAR) == anniRifMan)
	        	a.setAnno(Integer.toString(1));
	        else if (calendar.get(Calendar.YEAR) == anniRifMan + 1)
	        	a.setAnno(Integer.toString(2));
	        else if (calendar.get(Calendar.YEAR) == anniRifMan + 2)
				a.setAnno(Integer.toString(3));
				
	        if (p.getCup() != null && !p.getCup().isEmpty())
	        {
	        	a.setEsenteCup("2");
	        	a.setCup(p.getCup());
	        }
	        else
	        	a.setEsenteCup("1");
	        
	        if (p.getRicompreso().toString() == "No")
	        {
	        	a.setAcquistoRicompreso("1");
	        }
	        else if (p.getRicompreso().toString() == "Si")
	        {
	        	a.setAcquistoRicompreso("2");
	        	a.setCuiCollegato(p.getCuiRicompreso().getCui());
	        }
	        else if (p.getRicompreso().toString() == "SiLavori")
	        {
	        	a.setAcquistoRicompreso("2");
	        	a.setCuiCollegato(p.getCuiRicompresoLavori());
	        }
	        else if (p.getRicompreso().toString() == "SiInterventiOacquistiDiversi")
	        {
	        	a.setAcquistoRicompreso("4");
	        }
	        else if (p.getRicompreso().toString() == "SiCuiNonAncoraAttribuitoLavori")
	        {
	        	a.setAcquistoRicompreso("3");
	        }
	        else if (p.getRicompreso().toString() == "SiCuiNonAncoraAttribuitoServizi")
	        {
	        	a.setAcquistoRicompreso("3");
	        }
	        
	        a.setCpv(p.getCpv().getCodice());
			a.setNuts(p.getAmbitogeografico().getCodice());
			if (p.getQuantita() != null)
				a.setQuantita(p.getQuantita().toString());
			
			if (p.getUmisura() != null)
			{
				switch (p.getUmisura().getDescrizione()) {
					case "ora":
						a.setUnitaMisura("1");
						break;
					case "giorno":
						a.setUnitaMisura("2");
						break;
					case "grammo":
						a.setUnitaMisura("3");
						break;
					case "chilogrammo":
						a.setUnitaMisura("4");
						break;
					case "quintale":
						a.setUnitaMisura("5");
						break;
					case "tonnellata":
						a.setUnitaMisura("6");
						break;
					case "millilitro":
						a.setUnitaMisura("7");
						break;
					case "litro":
						a.setUnitaMisura("8");
						break;
					case "ettolitro":
						a.setUnitaMisura("9");
						break;
					case "millimetro":
						a.setUnitaMisura("10");
						break;
					case "centimetro":
						a.setUnitaMisura("11");
						break;
					case "metro":
						a.setUnitaMisura("12");
						break;
					case "chilometro":
						a.setUnitaMisura("13");
						break;
					case "metro quadrato":
						a.setUnitaMisura("14");
						break;
					case "metro cubo":
						a.setUnitaMisura("15");
						break;
					case "a corpo":
						a.setUnitaMisura("16");
						break;
					case "unitÓ":
						a.setUnitaMisura("17");
						break;
					default:
						break;
				}
			}
			
			switch (p.getPriorita().toString()) {
				case "max":
					a.setPriorita("1");
					break;
				case "media":
					a.setPriorita("2");
				case "min":
					a.setPriorita("3");
				default:
					break;
			}
			if (p.getLotto())
				a.setLottoFunzionale("1");
			else
				a.setLottoFunzionale("2");
			
			a.setDurataInMesi(Integer.toString(p.getDurata()));
			
			if (!p.isAffidamentoContrattoInEssere())
				a.setNuovoAffidamento("1");
			else
				a.setNuovoAffidamento("2");
			
			/*
			BigDecimal qInn = new BigDecimal(0);
			BigDecimal qExec = new BigDecimal(0);
			BigDecimal qAff = new BigDecimal(0);
			BigDecimal qProg = new BigDecimal(0);
			
			//calcolo degli incentivi
			if (p.getRicompreso() != null && p.getRicompreso().toString() == "No")
			{
				if (p.isFondoenable()) {
					qInn = p.getQuotaInnovazioneTotale().setScale(2, BigDecimal.ROUND_HALF_DOWN);
					qExec = p.getGdl113Exec().setScale(2, BigDecimal.ROUND_HALF_DOWN);
					qAff = p.getGdl113Affida().setScale(2, BigDecimal.ROUND_HALF_DOWN);
					qProg = p.getGdl113Program().setScale(2, BigDecimal.ROUND_HALF_DOWN);
		    	}
		    	if (p.isQuotainnovazioneenable() && !p.isFondoenable())
		    	{
		    		qInn = p.getQuotaInnovazioneTotale().setScale(2, BigDecimal.ROUND_HALF_DOWN);
		    	}
		    	if (p.isQuotagdlenable() && !p.isFondoenable())
		    	{
		    		qExec = p.getGdl113Exec().setScale(2, BigDecimal.ROUND_HALF_DOWN);
					qAff = p.getGdl113Affida().setScale(2, BigDecimal.ROUND_HALF_DOWN);
					qProg = p.getGdl113Program().setScale(2, BigDecimal.ROUND_HALF_DOWN);
		    	}
		    	if (p.isProgramenable() && !p.isFondoenable() && !p.isQuotagdlenable())
		    	{
		    		qProg = p.getGdl113Program().setScale(2, BigDecimal.ROUND_HALF_DOWN);
		    	}
		    	if (p.isAffidaenable() && !p.isFondoenable() && !p.isQuotagdlenable())
		    	{
		    		qAff = p.getGdl113Affida().setScale(2, BigDecimal.ROUND_HALF_DOWN);
		    	}
		    	if (p.isExecenable() && !p.isFondoenable() && !p.isQuotagdlenable())
		    	{
		    		qExec = p.getGdl113Exec().setScale(2, BigDecimal.ROUND_HALF_DOWN);
		    	}
			}
			*/
			
			BigDecimal vinA1 = new BigDecimal(0);
			BigDecimal vinA2 = new BigDecimal(0);
			BigDecimal vinA3 = new BigDecimal(0);
			
			BigDecimal mutA1 = new BigDecimal(0);
			BigDecimal mutA2 = new BigDecimal(0);
			BigDecimal mutA3 = new BigDecimal(0);
			
			BigDecimal capA1 = new BigDecimal(0);
			BigDecimal capA2 = new BigDecimal(0);
			BigDecimal capA3 = new BigDecimal(0);
			
			BigDecimal bilA1 = new BigDecimal(0);
			BigDecimal bilA2 = new BigDecimal(0);
			BigDecimal bilA3 = new BigDecimal(0);
			
			BigDecimal bilA1s = new BigDecimal(0);
			BigDecimal bilA2s = new BigDecimal(0);
			BigDecimal bilA3s = new BigDecimal(0);
			
			BigDecimal bilA1n = new BigDecimal(0);
			BigDecimal bilA2n = new BigDecimal(0);
			BigDecimal bilA3n = new BigDecimal(0);
			
			BigDecimal finA1 = new BigDecimal(0);
			BigDecimal finA2 = new BigDecimal(0);
			BigDecimal finA3 = new BigDecimal(0);
			
			BigDecimal traA1 = new BigDecimal(0);
			BigDecimal traA2 = new BigDecimal(0);
			BigDecimal traA3 = new BigDecimal(0);
			
			BigDecimal altA1 = new BigDecimal(0);
			BigDecimal altA2 = new BigDecimal(0);
			BigDecimal altA3 = new BigDecimal(0);
			
			BigDecimal ivaA1 = new BigDecimal(0);
			BigDecimal ivaA2 = new BigDecimal(0);
			BigDecimal ivaA3 = new BigDecimal(0);
			
			boolean coperturafinaziaria = true;
			
			String caratterizzazioneCapitaliPrivati = "";
			
			for (QuadroEconomico q: p.getQuadroeconomico())
	        {
				BigDecimal percentageA3 = new BigDecimal(100).subtract(q.getPercentualeA1().add(q.getPercentualeA2()));
				
				if (q.getImportoiva() != null && !q.getImportoiva().equals(new BigDecimal(0)))
				{
					if (q.getPercentualeA1().compareTo(new BigDecimal(0)) != 0) 
						ivaA1 = ivaA1.add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)));
					if (q.getPercentualeA2().compareTo(new BigDecimal(0)) != 0) 
						ivaA2 =	ivaA2.add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100)));
					if (percentageA3.compareTo(new BigDecimal(0)) != 0) 
						ivaA3 = ivaA3.add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100)));
				}
				
				if (q.getTipoCopertura().getKey().equals("VIN"))
				{
					vinA1 = vinA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					vinA2 = vinA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					vinA3 = vinA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
				}
				
				if (q.getTipoCopertura().getKey().equals("MUT"))
				{
					mutA1 = mutA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					mutA2 = mutA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					mutA3 = mutA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
				}
				
				if (q.getTipoCopertura().getKey().equals("CAP"))
				{
					caratterizzazioneCapitaliPrivati = q.getCaratterizzazioneCopertura().getNome();
					capA1 = capA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					capA2 = capA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					capA3 = capA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
				}
				
				if (q.getTipoCopertura().getKey().equals("BIL"))
				{
					bilA1 = bilA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					bilA2 = bilA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					bilA3 = bilA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
					
					if (q.getCaratterizzazioneCopertura().getNome().equals("stanziato"))
					{
						bilA1s = bilA1s.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
						bilA2s = bilA2s.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
						bilA3s = bilA3s.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
					}
					else if (q.getCaratterizzazioneCopertura().getNome().equals("anni successivi"))
					{
						coperturafinaziaria = false;
						bilA1n = bilA1n.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
						bilA2n = bilA2n.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
						bilA3n = bilA3n.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
					}
				}
				
				if (q.getTipoCopertura().getKey().equals("FIN"))
				{
					finA1 = finA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					finA2 = finA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					finA3 = finA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
				}
				
				if (q.getTipoCopertura().getKey().equals("TRA"))
				{
					traA1 = traA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					traA2 = traA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					traA3 = traA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
				}
				
				if (q.getTipoCopertura().getKey().equals("ALT"))
				{
					altA1 = altA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					altA2 = altA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					altA3 = altA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
				}
	        }     
			
			a.setRisorseVincolatePerLegge1(vinA1.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseVincolatePerLegge2(vinA2.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseVincolatePerLeggeSucc(vinA3.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseMutuo1(mutA1.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseMutuo2(mutA2.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseMutuoSucc(mutA3.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorsePrivati1(capA1.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorsePrivati2(capA2.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorsePrivatiSucc(capA3.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			//a.setRisorseBilancio1(bilA1.setScale(2, BigDecimal.ROUND_HALF_DOWN).add(qInn).add(qAff).add(qProg).toString());
			a.setRisorseBilancio1(bilA1.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseBilancio2(bilA2.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			//a.setRisorseBilancioSucc(bilA3.setScale(2, BigDecimal.ROUND_HALF_DOWN).add(qExec).toString());
			a.setRisorseBilancioSucc(bilA3.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseArt3_1(finA1.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseArt3_2(finA2.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseArt3_Succ(finA3.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseImmobili1(traA1.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseImmobili2(traA2.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseImmobiliSucc(traA3.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseAltro1(altA1.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseAltro2(altA2.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseAltroSucc(altA3.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			if (p.getCostipregressi() != null)
				a.setSpeseSostenute(p.getCostipregressi().setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());

			if (!caratterizzazioneCapitaliPrivati.isEmpty())
			{
				switch (caratterizzazioneCapitaliPrivati)
				{
					case "altro":
						a.setTipologiaCapitalePrivato("9");
						break;
					case "finanza di progetto":
						a.setTipologiaCapitalePrivato("1");
						break;
					case "concessione di costruzione e gestione":
						a.setTipologiaCapitalePrivato("2");
						break;
					case "sponsorizzazione":
						a.setTipologiaCapitalePrivato("3");
						break;
					case "societÓ partecipate o di scopo":
						a.setTipologiaCapitalePrivato("4");
						break;
					case "locazione finanziaria":
						a.setTipologiaCapitalePrivato("5");
						break;
					case "contratto di disponibilitÓ":
						a.setTipologiaCapitalePrivato("6");
						break;
				}
			}
			
			a.setMeseAvvioProcedura(Integer.toString(calendar.get(Calendar.MONTH) + 1));
			
			if (p.isDelega())
			{
				a.setDelega("1");
				a.setCodiceSoggettoDelegato(p.getAusa().getCodice());
				a.setNomeSoggettoDelegato(p.getAusa().getDenominazione());
			}
			else
				a.setDelega("2");
			
			//a.setVariato("");
			
			a.setNote(p.getNote());
			
			//a.setImportoRisorseFinanziarie("0.00");
			//a.setImportoRisorseFinanziarieRegionali("0.00");
			//a.setImportoRisorseFinanziarieAltro("0.00");
			/*
			a.setDirezioneGenerale("");
			a.setStrutturaOperativa("");
			a.setReferenteDati("");
			a.setDirigenteResponsabile("");
			a.setProceduraAffidamento("1");
			*/
			if (p.getVerdi() && p.getOggettoAcquistiVerdi() != null && !p.getOggettoAcquistiVerdi().isEmpty())
			{
				if (p.getImportoNettoAcquistiVerdi().add(p.getAliquotaIvaAcquistiVerdi()) == p.getCostoTotale())
					a.setAcquistoVerdi("2");
				else
					a.setAcquistoVerdi("3");
				a.setNormativaRiferimento(p.getRiferimentoNormativoVerdi());
				a.setOggettoVerdi(p.getOggettoAcquistiVerdi());
				a.setCpvVerdi(p.getCpvAcquistiVerdi());
				a.setImportoNettoIvaVerdi(p.getImportoNettoAcquistiVerdi().setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
				a.setImportoIvaVerdi(p.getAliquotaIvaAcquistiVerdi().setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
				a.setImportoTotVerdi(p.getImportoNettoAcquistiVerdi().add(p.getAliquotaIvaAcquistiVerdi()).setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			}
			else
				a.setAcquistoVerdi("1");
			if (p.getOggettoAcquistiMaterialiRiciclati() != null && !p.getOggettoAcquistiMaterialiRiciclati().isEmpty())
			{
				if (p.getImportoNettoAcquistiMaterialiRiciclati() != null && p.getAliquotaIvaAcquistiMaterialiRiciclati() != null && (p.getImportoNettoAcquistiMaterialiRiciclati().add(p.getAliquotaIvaAcquistiMaterialiRiciclati()) == p.getCostoTotale()))
					a.setAcquistoMaterialiRiciclati("2");
				else
					a.setAcquistoMaterialiRiciclati("3");
				a.setOggettoMatRic(p.getOggettoAcquistiMaterialiRiciclati());
				a.setCpvMatRic(p.getCpvAcquistiMaterialiRiciclati());
				if (p.getImportoNettoAcquistiMaterialiRiciclati() != null)
					a.setImportoNettoIvaMatRic(p.getImportoNettoAcquistiMaterialiRiciclati().setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
				if (p.getAliquotaIvaAcquistiMaterialiRiciclati() != null)
					a.setImportoIvaMatRic(p.getAliquotaIvaAcquistiMaterialiRiciclati().setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
				if (p.getImportoNettoAcquistiMaterialiRiciclati() != null && p.getAliquotaIvaAcquistiMaterialiRiciclati() != null)
					a.setImportoTotMatRic(p.getImportoNettoAcquistiMaterialiRiciclati().add(p.getAliquotaIvaAcquistiMaterialiRiciclati()).setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			}
			else
				a.setAcquistoMaterialiRiciclati("1");

			/*
			a.setImportoIva1(ivaA1.toString());
			a.setImportoIva2(ivaA2.toString());
			a.setImportoIvaSucc(ivaA3.toString());
			*/
			
			if (coperturafinaziaria)
				a.setCoperturaFinanziaria("1");
			else 
				a.setCoperturaFinanziaria("2");
			a.setValutazione("1");
			a.setImportoTotale(p.getTotaleCoperture().toString());
			a.setRup(mapperRup(p.getDipendenti()));
			acquisti.add(a);
		}
		
		for (ProcedureDefinitive p: proceduredefinitive) {
			Acquisti a = new Acquisti();
			a.setCui(p.getCui());
			a.setSettore(p.getSettore().toString().substring(0, 1));
			a.setCodiceInterno(p.getCui().substring(16, 21));
			a.setDescrizione(p.getDescrizione());
			Calendar calendar = Calendar.getInstance();
	        calendar.setTime(p.getData());
	        
	        if (calendar.get(Calendar.YEAR) == anniRifMan)
	        	a.setAnno(Integer.toString(1));
	        else if (calendar.get(Calendar.YEAR) == anniRifMan + 1)
	        	a.setAnno(Integer.toString(2));
	        else if (calendar.get(Calendar.YEAR) == anniRifMan + 2)
				a.setAnno(Integer.toString(3));
				
	        if (p.getCup() != null && !p.getCup().isEmpty())
	        {
	        	a.setEsenteCup("2");
	        	a.setCup(p.getCup());
	        }
	        else
	        	a.setEsenteCup("1");
	        
	        if (p.getRicompreso().toString() == "No")
	        {
	        	a.setAcquistoRicompreso("1");
	        }
	        else if (p.getRicompreso().toString() == "Si")
	        {
	        	a.setAcquistoRicompreso("2");
	        	a.setCuiCollegato(p.getCuiRicompreso().getCui());
	        }
	        else if (p.getRicompreso().toString() == "SiLavori")
	        {
	        	a.setAcquistoRicompreso("2");
	        	a.setCuiCollegato(p.getCuiRicompresoLavori());
	        }
	        else if (p.getRicompreso().toString() == "SiInterventiOacquistiDiversi")
	        {
	        	a.setAcquistoRicompreso("4");
	        }
	        else if (p.getRicompreso().toString() == "SiCuiNonAncoraAttribuitoLavori")
	        {
	        	a.setAcquistoRicompreso("3");
	        }
	        else if (p.getRicompreso().toString() == "SiCuiNonAncoraAttribuitoServizi")
	        {
	        	a.setAcquistoRicompreso("3");
	        }
	        
	        a.setCpv(p.getCpv().getCodice());
			a.setNuts(p.getAmbitogeografico().getCodice());
			
			if (p.getQuantita() != null)
				a.setQuantita(p.getQuantita().toString());
			
			if (p.getUmisura() != null)
			{
				switch (p.getUmisura().getDescrizione()) {
					case "ora":
						a.setUnitaMisura("1");
						break;
					case "giorno":
						a.setUnitaMisura("2");
						break;
					case "grammo":
						a.setUnitaMisura("3");
						break;
					case "chilogrammo":
						a.setUnitaMisura("4");
						break;
					case "quintale":
						a.setUnitaMisura("5");
						break;
					case "tonnellata":
						a.setUnitaMisura("6");
						break;
					case "millilitro":
						a.setUnitaMisura("7");
						break;
					case "litro":
						a.setUnitaMisura("8");
						break;
					case "ettolitro":
						a.setUnitaMisura("9");
						break;
					case "millimetro":
						a.setUnitaMisura("10");
						break;
					case "centimetro":
						a.setUnitaMisura("11");
						break;
					case "metro":
						a.setUnitaMisura("12");
						break;
					case "chilometro":
						a.setUnitaMisura("13");
						break;
					case "metro quadrato":
						a.setUnitaMisura("14");
						break;
					case "metro cubo":
						a.setUnitaMisura("15");
						break;
					case "a corpo":
						a.setUnitaMisura("16");
						break;
					case "unitÓ":
						a.setUnitaMisura("17");
						break;
					default:
						break;
				}
			}
			
			switch (p.getPriorita().toString()) {
				case "max":
					a.setPriorita("1");
					break;
				case "media":
					a.setPriorita("2");
				case "min":
					a.setPriorita("3");
				default:
					break;
			}
			if (p.getLotto())
				a.setLottoFunzionale("1");
			else
				a.setLottoFunzionale("2");
			
			a.setDurataInMesi(Integer.toString(p.getDurata()));
			
			if (!p.isAffidamentoContrattoInEssere())
				a.setNuovoAffidamento("1");
			else
				a.setNuovoAffidamento("2");
			
			BigDecimal qInn = new BigDecimal(0);
			BigDecimal qExec = new BigDecimal(0);
			BigDecimal qAff = new BigDecimal(0);
			BigDecimal qProg = new BigDecimal(0);
			
			//calcolo degli incentivi
			if (p.getRicompreso() != null && p.getRicompreso().toString().equals("No"))
			{
				if (p.isFondoenable()) {
					qInn = p.getQuotaInnovazioneTotale().setScale(2, BigDecimal.ROUND_HALF_DOWN);
					qExec = p.getGdl113Exec().setScale(2, BigDecimal.ROUND_HALF_DOWN);
					qAff = p.getGdl113Affida().setScale(2, BigDecimal.ROUND_HALF_DOWN);
					qProg = p.getGdl113Program().setScale(2, BigDecimal.ROUND_HALF_DOWN);
		    	}
		    	if (p.isQuotainnovazioneenable() && !p.isFondoenable())
		    	{
		    		qInn = p.getQuotaInnovazioneTotale().setScale(2, BigDecimal.ROUND_HALF_DOWN);
		    	}
		    	if (p.isQuotagdlenable() && !p.isFondoenable())
		    	{
		    		qExec = p.getGdl113Exec().setScale(2, BigDecimal.ROUND_HALF_DOWN);
					qAff = p.getGdl113Affida().setScale(2, BigDecimal.ROUND_HALF_DOWN);
					qProg = p.getGdl113Program().setScale(2, BigDecimal.ROUND_HALF_DOWN);
		    	}
		    	if (p.isProgramenable() && !p.isFondoenable() && !p.isQuotagdlenable())
		    	{
		    		qProg = p.getGdl113Program().setScale(2, BigDecimal.ROUND_HALF_DOWN);
		    	}
		    	if (p.isAffidaenable() && !p.isFondoenable() && !p.isQuotagdlenable())
		    	{
		    		qAff = p.getGdl113Affida().setScale(2, BigDecimal.ROUND_HALF_DOWN);
		    	}
		    	if (p.isExecenable() && !p.isFondoenable() && !p.isQuotagdlenable())
		    	{
		    		qExec = p.getGdl113Exec().setScale(2, BigDecimal.ROUND_HALF_DOWN);
		    	}
			}
			
			BigDecimal vinA1 = new BigDecimal(0);
			BigDecimal vinA2 = new BigDecimal(0);
			BigDecimal vinA3 = new BigDecimal(0);
			
			BigDecimal mutA1 = new BigDecimal(0);
			BigDecimal mutA2 = new BigDecimal(0);
			BigDecimal mutA3 = new BigDecimal(0);
			
			BigDecimal capA1 = new BigDecimal(0);
			BigDecimal capA2 = new BigDecimal(0);
			BigDecimal capA3 = new BigDecimal(0);
			
			BigDecimal bilA1 = new BigDecimal(0);
			BigDecimal bilA2 = new BigDecimal(0);
			BigDecimal bilA3 = new BigDecimal(0);
			
			BigDecimal bilA1s = new BigDecimal(0);
			BigDecimal bilA2s = new BigDecimal(0);
			BigDecimal bilA3s = new BigDecimal(0);
			
			BigDecimal bilA1n = new BigDecimal(0);
			BigDecimal bilA2n = new BigDecimal(0);
			BigDecimal bilA3n = new BigDecimal(0);
			
			BigDecimal finA1 = new BigDecimal(0);
			BigDecimal finA2 = new BigDecimal(0);
			BigDecimal finA3 = new BigDecimal(0);
			
			BigDecimal traA1 = new BigDecimal(0);
			BigDecimal traA2 = new BigDecimal(0);
			BigDecimal traA3 = new BigDecimal(0);
			
			BigDecimal altA1 = new BigDecimal(0);
			BigDecimal altA2 = new BigDecimal(0);
			BigDecimal altA3 = new BigDecimal(0);
			
			BigDecimal ivaA1 = new BigDecimal(0);
			BigDecimal ivaA2 = new BigDecimal(0);
			BigDecimal ivaA3 = new BigDecimal(0);
			
			boolean coperturafinaziaria = true;
			String caratterizzazioneCapitaliPrivati = "";
			
			for (QuadroEconomicoDefinitivo q: p.getQuadroeconomico())
	        {					
				BigDecimal percentageA3 = new BigDecimal(100).subtract(q.getPercentualeA1().add(q.getPercentualeA2()));
				
				if (q.getImportoiva() != null && q.getImportoiva().compareTo(new BigDecimal(0)) != 0)
				{
					if (q.getPercentualeA1().compareTo(new BigDecimal(0)) != 0) 
						ivaA1 = ivaA1.add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)));
					if (q.getPercentualeA2().compareTo(new BigDecimal(0)) != 0) 
						ivaA2 =	ivaA2.add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100)));
					if (percentageA3.compareTo(new BigDecimal(0)) != 0) 
						ivaA3 = ivaA3.add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100)));
				}
				
				if (q.getTipoCopertura().getKey().equals("VIN"))
				{
					vinA1 = vinA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					vinA2 = vinA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					vinA3 = vinA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
				}
				
				if (q.getTipoCopertura().getKey().equals("MUT"))
				{
					mutA1 = mutA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					mutA2 = mutA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					mutA3 = mutA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
				}
				
				if (q.getTipoCopertura().getKey().equals("CAP"))
				{
					caratterizzazioneCapitaliPrivati = q.getCaratterizzazioneCopertura().getNome();
					capA1 = capA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					capA2 = capA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					capA3 = capA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
				}
				
				if (q.getTipoCopertura().getKey().equals("BIL"))
				{
					bilA1 = bilA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					bilA2 = bilA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					bilA3 = bilA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
					
					if (q.getCaratterizzazioneCopertura().getNome().equals("stanziato"))
					{
						bilA1s = bilA1s.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
						bilA2s = bilA2s.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
						bilA3s = bilA3s.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
					}
					else if (q.getCaratterizzazioneCopertura().getNome().equals("anni successivi"))
					{
						coperturafinaziaria = false;
						bilA1n = bilA1n.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
						bilA2n = bilA2n.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
						bilA3n = bilA3n.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
					}
				}
				
				if (q.getTipoCopertura().getKey().equals("FIN"))
				{
					finA1 = finA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					finA2 = finA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					finA3 = finA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
				}
				
				if (q.getTipoCopertura().getKey().equals("TRA"))
				{
					traA1 = traA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					traA2 = traA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					traA3 = traA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
				}
				
				if (q.getTipoCopertura().getKey().equals("ALT"))
				{
					altA1 = altA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					altA2 = altA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					altA3 = altA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
				}
	        }     
			
			a.setRisorseVincolatePerLegge1(vinA1.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseVincolatePerLegge2(vinA2.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseVincolatePerLeggeSucc(vinA3.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseMutuo1(mutA1.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseMutuo2(mutA2.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseMutuoSucc(mutA3.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorsePrivati1(capA1.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorsePrivati2(capA2.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorsePrivatiSucc(capA3.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseBilancio1(bilA1.setScale(2, BigDecimal.ROUND_HALF_DOWN).add(qInn).add(qAff).add(qProg).toString());
			// questo deve essere fixato
			//a.setRisorseBilancio1(bilA1.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseBilancio2(bilA2.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());			
			a.setRisorseBilancioSucc(bilA3.setScale(2, BigDecimal.ROUND_HALF_DOWN).add(qExec).toString());
			// questo deve essere fixato
			//a.setRisorseBilancioSucc(bilA3.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseArt3_1(finA1.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseArt3_2(finA2.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseArt3_Succ(finA3.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseImmobili1(traA1.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseImmobili2(traA2.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseImmobiliSucc(traA3.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseAltro1(altA1.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseAltro2(altA2.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseAltroSucc(altA3.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			if (p.getCostipregressi() != null)
				a.setSpeseSostenute(p.getCostipregressi().setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			
			if (!caratterizzazioneCapitaliPrivati.isEmpty())
			{
				switch (caratterizzazioneCapitaliPrivati)
				{
					case "altro":
						a.setTipologiaCapitalePrivato("9");
						break;
					case "finanza di progetto":
						a.setTipologiaCapitalePrivato("1");
						break;
					case "concessione di costruzione e gestione":
						a.setTipologiaCapitalePrivato("2");
						break;
					case "sponsorizzazione":
						a.setTipologiaCapitalePrivato("3");
						break;
					case "societÓ partecipate o di scopo":
						a.setTipologiaCapitalePrivato("4");
						break;
					case "locazione finanziaria":
						a.setTipologiaCapitalePrivato("5");
						break;
					case "contratto di disponibilitÓ":
						a.setTipologiaCapitalePrivato("6");
						break;
				}
			}
			
			a.setMeseAvvioProcedura(Integer.toString(calendar.get(Calendar.MONTH) + 1));
			
			if (p.isDelega())
			{
				a.setDelega("1");
				a.setCodiceSoggettoDelegato(p.getAusa().getCodice());
				a.setNomeSoggettoDelegato(p.getAusa().getDenominazione());
			}
			else
				a.setDelega("2");
			
			//a.setVariato("");
			
			a.setNote(p.getNote());
			
			//a.setImportoRisorseFinanziarie("0.00");
			//a.setImportoRisorseFinanziarieRegionali("0.00");
			//a.setImportoRisorseFinanziarieAltro("0.00");
			/*
			a.setDirezioneGenerale("");
			a.setStrutturaOperativa("");
			a.setReferenteDati("");
			a.setDirigenteResponsabile("");
			a.setProceduraAffidamento("1");
			*/
			if (p.getVerdi() && p.getOggettoAcquistiVerdi() != null && !p.getOggettoAcquistiVerdi().isEmpty())
			{
				if (p.getImportoNettoAcquistiVerdi().add(p.getAliquotaIvaAcquistiVerdi()) == p.getCostoTotale())
					a.setAcquistoVerdi("2");
				else
					a.setAcquistoVerdi("3");
				a.setNormativaRiferimento(p.getRiferimentoNormativoVerdi());
				a.setOggettoVerdi(p.getOggettoAcquistiVerdi());
				a.setCpvVerdi(p.getCpvAcquistiVerdi());
				a.setImportoNettoIvaVerdi(p.getImportoNettoAcquistiVerdi().setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
				a.setImportoIvaVerdi(p.getAliquotaIvaAcquistiVerdi().setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
				a.setImportoTotVerdi(p.getImportoNettoAcquistiVerdi().add(p.getAliquotaIvaAcquistiVerdi()).setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			}
			else
				a.setAcquistoVerdi("1");
			if (p.getOggettoAcquistiMaterialiRiciclati() != null && !p.getOggettoAcquistiMaterialiRiciclati().isEmpty())
			{
				if (p.getImportoNettoAcquistiMaterialiRiciclati() != null && p.getAliquotaIvaAcquistiMaterialiRiciclati() != null && (p.getImportoNettoAcquistiMaterialiRiciclati().add(p.getAliquotaIvaAcquistiMaterialiRiciclati()) == p.getCostoTotale()))
					a.setAcquistoMaterialiRiciclati("2");
				else
					a.setAcquistoMaterialiRiciclati("3");
				a.setOggettoMatRic(p.getOggettoAcquistiMaterialiRiciclati());
				a.setCpvMatRic(p.getCpvAcquistiMaterialiRiciclati());
				if (p.getImportoNettoAcquistiMaterialiRiciclati() != null)
					a.setImportoNettoIvaMatRic(p.getImportoNettoAcquistiMaterialiRiciclati().setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
				if (p.getAliquotaIvaAcquistiMaterialiRiciclati() != null)
					a.setImportoIvaMatRic(p.getAliquotaIvaAcquistiMaterialiRiciclati().setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
				if (p.getImportoNettoAcquistiMaterialiRiciclati() != null && p.getAliquotaIvaAcquistiMaterialiRiciclati() != null)
					a.setImportoTotMatRic(p.getImportoNettoAcquistiMaterialiRiciclati().add(p.getAliquotaIvaAcquistiMaterialiRiciclati()).setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			}
			else
				a.setAcquistoMaterialiRiciclati("1");
			
			/*
			a.setImportoIva1(ivaA1.toString());
			a.setImportoIva2(ivaA2.toString());
			a.setImportoIvaSucc(ivaA3.toString());
			*/
			
			if (coperturafinaziaria)
				a.setCoperturaFinanziaria("1");
			else 
				a.setCoperturaFinanziaria("2");
			a.setValutazione("1");
			a.setImportoTotale(p.getTotaleCoperture().toString());
			a.setRup(mapperRup(p.getDipendenti()));
			acquisti.add(a);
		}
		
		return acquisti;
	}
	
	private static ArrayList<Acquisti> mapperModificheFornitureServizi(List<ProcedureProgramma> procedure, List<ProcedureDefinitive> proceduredefinitive, int anniRifMan) {
		
		ArrayList<Acquisti> acquisti = new ArrayList<Acquisti>();
		
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
		for (ProcedureProgramma p: procedure) {
			Acquisti a = new Acquisti();
			if (p.getUltimopianoapprovato() != null && p.getStato().getKey().equals("M") && !p.getCui().equals("~MINORI-SOGLIA-40K~")) {
				a.setCui(p.getUltimopianoapprovato().getCui());
			}
			else {
				a.setCui(p.getSettore().toString().toUpperCase().substring(0, 1).concat(ProgrammaBiennalePreferences.getDefaultCfSA()).concat(p.getAnno0().toString()).concat(ProgBienUtils.CodiceInterno(Integer.toString(i))));
				i++;
			}
			a.setSettore(p.getSettore().toString().substring(0, 1));
			
			if (p.getUltimopianoapprovato() != null && p.getStato().getKey().equals("M") && !p.getCui().equals("~MINORI-SOGLIA-40K~")) {
				a.setCodiceInterno(String.valueOf(util.ProgBienUtils.getSerialeFromCui(p.getCui())));
			}
			else {
				a.setCodiceInterno(ProgBienUtils.CodiceInterno(Integer.toString(i)));
			}	
			a.setDescrizione(p.getDescrizione());
			Calendar calendar = Calendar.getInstance();
	        calendar.setTime(p.getData());
	        
	        if (calendar.get(Calendar.YEAR) == anniRifMan)
	        	a.setAnno(Integer.toString(1));
	        else if (calendar.get(Calendar.YEAR) == anniRifMan + 1)
	        	a.setAnno(Integer.toString(2));
	        else if (calendar.get(Calendar.YEAR) == anniRifMan + 2)
				a.setAnno(Integer.toString(3));
				
	        if (p.getCup() != null && !p.getCup().isEmpty())
	        {
	        	a.setEsenteCup("2");
	        	a.setCup(p.getCup());
	        }
	        else
	        	a.setEsenteCup("1");
	        
	        if (p.getRicompreso().toString() == "No")
	        {
	        	a.setAcquistoRicompreso("1");
	        }
	        else if (p.getRicompreso().toString() == "Si")
	        {
	        	a.setAcquistoRicompreso("2");
	        	a.setCuiCollegato(p.getCuiRicompreso().getCui());
	        }
	        else if (p.getRicompreso().toString() == "SiLavori")
	        {
	        	a.setAcquistoRicompreso("2");
	        	a.setCuiCollegato(p.getCuiRicompresoLavori());
	        }
	        else if (p.getRicompreso().toString() == "SiInterventiOacquistiDiversi")
	        {
	        	a.setAcquistoRicompreso("4");
	        }
	        else if (p.getRicompreso().toString() == "SiCuiNonAncoraAttribuitoLavori")
	        {
	        	a.setAcquistoRicompreso("3");
	        }
	        else if (p.getRicompreso().toString() == "SiCuiNonAncoraAttribuitoServizi")
	        {
	        	a.setAcquistoRicompreso("3");
	        }
	        
	        a.setCpv(p.getCpv().getCodice());
			a.setNuts(p.getAmbitogeografico().getCodice());
			if (p.getQuantita() != null)
				a.setQuantita(p.getQuantita().toString());
			
			if (p.getUmisura() != null)
			{
				switch (p.getUmisura().getDescrizione()) {
					case "ora":
						a.setUnitaMisura("1");
						break;
					case "giorno":
						a.setUnitaMisura("2");
						break;
					case "grammo":
						a.setUnitaMisura("3");
						break;
					case "chilogrammo":
						a.setUnitaMisura("4");
						break;
					case "quintale":
						a.setUnitaMisura("5");
						break;
					case "tonnellata":
						a.setUnitaMisura("6");
						break;
					case "millilitro":
						a.setUnitaMisura("7");
						break;
					case "litro":
						a.setUnitaMisura("8");
						break;
					case "ettolitro":
						a.setUnitaMisura("9");
						break;
					case "millimetro":
						a.setUnitaMisura("10");
						break;
					case "centimetro":
						a.setUnitaMisura("11");
						break;
					case "metro":
						a.setUnitaMisura("12");
						break;
					case "chilometro":
						a.setUnitaMisura("13");
						break;
					case "metro quadrato":
						a.setUnitaMisura("14");
						break;
					case "metro cubo":
						a.setUnitaMisura("15");
						break;
					case "a corpo":
						a.setUnitaMisura("16");
						break;
					case "unitÓ":
						a.setUnitaMisura("17");
						break;
					default:
						break;
				}
			}
			
			switch (p.getPriorita().toString()) {
				case "max":
					a.setPriorita("1");
					break;
				case "media":
					a.setPriorita("2");
				case "min":
					a.setPriorita("3");
				default:
					break;
			}
			if (p.getLotto())
				a.setLottoFunzionale("1");
			else
				a.setLottoFunzionale("2");
			
			a.setDurataInMesi(Integer.toString(p.getDurata()));
			
			if (!p.isAffidamentoContrattoInEssere())
				a.setNuovoAffidamento("1");
			else
				a.setNuovoAffidamento("2");
			
			/*
			BigDecimal qInn = new BigDecimal(0);
			BigDecimal qExec = new BigDecimal(0);
			BigDecimal qAff = new BigDecimal(0);
			BigDecimal qProg = new BigDecimal(0);
			
			//calcolo degli incentivi
			if (p.getRicompreso() != null && p.getRicompreso().toString() == "No")
			{
				if (p.isFondoenable()) {
					qInn = p.getQuotaInnovazioneTotale().setScale(2, BigDecimal.ROUND_HALF_DOWN);
					qExec = p.getGdl113Exec().setScale(2, BigDecimal.ROUND_HALF_DOWN);
					qAff = p.getGdl113Affida().setScale(2, BigDecimal.ROUND_HALF_DOWN);
					qProg = p.getGdl113Program().setScale(2, BigDecimal.ROUND_HALF_DOWN);
		    	}
		    	if (p.isQuotainnovazioneenable() && !p.isFondoenable())
		    	{
		    		qInn = p.getQuotaInnovazioneTotale().setScale(2, BigDecimal.ROUND_HALF_DOWN);
		    	}
		    	if (p.isQuotagdlenable() && !p.isFondoenable())
		    	{
		    		qExec = p.getGdl113Exec().setScale(2, BigDecimal.ROUND_HALF_DOWN);
					qAff = p.getGdl113Affida().setScale(2, BigDecimal.ROUND_HALF_DOWN);
					qProg = p.getGdl113Program().setScale(2, BigDecimal.ROUND_HALF_DOWN);
		    	}
		    	if (p.isProgramenable() && !p.isFondoenable() && !p.isQuotagdlenable())
		    	{
		    		qProg = p.getGdl113Program().setScale(2, BigDecimal.ROUND_HALF_DOWN);
		    	}
		    	if (p.isAffidaenable() && !p.isFondoenable() && !p.isQuotagdlenable())
		    	{
		    		qAff = p.getGdl113Affida().setScale(2, BigDecimal.ROUND_HALF_DOWN);
		    	}
		    	if (p.isExecenable() && !p.isFondoenable() && !p.isQuotagdlenable())
		    	{
		    		qExec = p.getGdl113Exec().setScale(2, BigDecimal.ROUND_HALF_DOWN);
		    	}
			}
			*/
			
			BigDecimal vinA1 = new BigDecimal(0);
			BigDecimal vinA2 = new BigDecimal(0);
			BigDecimal vinA3 = new BigDecimal(0);
			
			BigDecimal mutA1 = new BigDecimal(0);
			BigDecimal mutA2 = new BigDecimal(0);
			BigDecimal mutA3 = new BigDecimal(0);
			
			BigDecimal capA1 = new BigDecimal(0);
			BigDecimal capA2 = new BigDecimal(0);
			BigDecimal capA3 = new BigDecimal(0);
			
			BigDecimal bilA1 = new BigDecimal(0);
			BigDecimal bilA2 = new BigDecimal(0);
			BigDecimal bilA3 = new BigDecimal(0);
			
			BigDecimal bilA1s = new BigDecimal(0);
			BigDecimal bilA2s = new BigDecimal(0);
			BigDecimal bilA3s = new BigDecimal(0);
			
			BigDecimal bilA1n = new BigDecimal(0);
			BigDecimal bilA2n = new BigDecimal(0);
			BigDecimal bilA3n = new BigDecimal(0);
			
			BigDecimal finA1 = new BigDecimal(0);
			BigDecimal finA2 = new BigDecimal(0);
			BigDecimal finA3 = new BigDecimal(0);
			
			BigDecimal traA1 = new BigDecimal(0);
			BigDecimal traA2 = new BigDecimal(0);
			BigDecimal traA3 = new BigDecimal(0);
			
			BigDecimal altA1 = new BigDecimal(0);
			BigDecimal altA2 = new BigDecimal(0);
			BigDecimal altA3 = new BigDecimal(0);
			
			BigDecimal ivaA1 = new BigDecimal(0);
			BigDecimal ivaA2 = new BigDecimal(0);
			BigDecimal ivaA3 = new BigDecimal(0);
			
			boolean coperturafinaziaria = true;
			
			String caratterizzazioneCapitaliPrivati = "";
			
			for (QuadroEconomicoProgramma q: p.getQuadroeconomico())
	        {
				BigDecimal percentageA3 = new BigDecimal(100).subtract(q.getPercentualeA1().add(q.getPercentualeA2()));
				
				if (q.getImportoiva() != null && !q.getImportoiva().equals(new BigDecimal(0)))
				{
					if (q.getPercentualeA1().compareTo(new BigDecimal(0)) != 0) 
						ivaA1 = ivaA1.add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)));
					if (q.getPercentualeA2().compareTo(new BigDecimal(0)) != 0) 
						ivaA2 =	ivaA2.add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100)));
					if (percentageA3.compareTo(new BigDecimal(0)) != 0) 
						ivaA3 = ivaA3.add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100)));
				}
				
				if (q.getTipoCopertura().getKey().equals("VIN"))
				{
					vinA1 = vinA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					vinA2 = vinA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					vinA3 = vinA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
				}
				
				if (q.getTipoCopertura().getKey().equals("MUT"))
				{
					mutA1 = mutA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					mutA2 = mutA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					mutA3 = mutA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
				}
				
				if (q.getTipoCopertura().getKey().equals("CAP"))
				{
					caratterizzazioneCapitaliPrivati = q.getCaratterizzazioneCopertura().getNome();
					capA1 = capA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					capA2 = capA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					capA3 = capA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
				}
				
				if (q.getTipoCopertura().getKey().equals("BIL"))
				{
					bilA1 = bilA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					bilA2 = bilA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					bilA3 = bilA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
					
					if (q.getCaratterizzazioneCopertura().getNome().equals("stanziato"))
					{
						bilA1s = bilA1s.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
						bilA2s = bilA2s.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
						bilA3s = bilA3s.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
					}
					else if (q.getCaratterizzazioneCopertura().getNome().equals("anni successivi"))
					{
						coperturafinaziaria = false;
						bilA1n = bilA1n.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
						bilA2n = bilA2n.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
						bilA3n = bilA3n.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
					}
				}
				
				if (q.getTipoCopertura().getKey().equals("FIN"))
				{
					finA1 = finA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					finA2 = finA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					finA3 = finA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
				}
				
				if (q.getTipoCopertura().getKey().equals("TRA"))
				{
					traA1 = traA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					traA2 = traA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					traA3 = traA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
				}
				
				if (q.getTipoCopertura().getKey().equals("ALT"))
				{
					altA1 = altA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					altA2 = altA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					altA3 = altA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
				}
	        }     
			
			a.setRisorseVincolatePerLegge1(vinA1.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseVincolatePerLegge2(vinA2.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseVincolatePerLeggeSucc(vinA3.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseMutuo1(mutA1.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseMutuo2(mutA2.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseMutuoSucc(mutA3.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorsePrivati1(capA1.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorsePrivati2(capA2.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorsePrivatiSucc(capA3.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			//a.setRisorseBilancio1(bilA1.setScale(2, BigDecimal.ROUND_HALF_DOWN).add(qInn).add(qAff).add(qProg).toString());
			a.setRisorseBilancio1(bilA1.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseBilancio2(bilA2.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			//a.setRisorseBilancioSucc(bilA3.setScale(2, BigDecimal.ROUND_HALF_DOWN).add(qExec).toString());
			a.setRisorseBilancioSucc(bilA3.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseArt3_1(finA1.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseArt3_2(finA2.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseArt3_Succ(finA3.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseImmobili1(traA1.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseImmobili2(traA2.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseImmobiliSucc(traA3.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseAltro1(altA1.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseAltro2(altA2.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseAltroSucc(altA3.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			if (p.getCostipregressi() != null)
				a.setSpeseSostenute(p.getCostipregressi().setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());

			if (!caratterizzazioneCapitaliPrivati.isEmpty())
			{
				switch (caratterizzazioneCapitaliPrivati)
				{
					case "altro":
						a.setTipologiaCapitalePrivato("9");
						break;
					case "finanza di progetto":
						a.setTipologiaCapitalePrivato("1");
						break;
					case "concessione di costruzione e gestione":
						a.setTipologiaCapitalePrivato("2");
						break;
					case "sponsorizzazione":
						a.setTipologiaCapitalePrivato("3");
						break;
					case "societÓ partecipate o di scopo":
						a.setTipologiaCapitalePrivato("4");
						break;
					case "locazione finanziaria":
						a.setTipologiaCapitalePrivato("5");
						break;
					case "contratto di disponibilitÓ":
						a.setTipologiaCapitalePrivato("6");
						break;
				}
			}
			
			a.setMeseAvvioProcedura(Integer.toString(calendar.get(Calendar.MONTH) + 1));
			
			if (p.isDelega())
			{
				a.setDelega("1");
				a.setCodiceSoggettoDelegato(p.getAusa().getCodice());
				a.setNomeSoggettoDelegato(p.getAusa().getDenominazione());
			}
			else
				a.setDelega("2");
			
			if (p.getVariante() != null)
			{
				if(p.getVariante().equals(Enumerators.VarianteValori.ModificaB))
					a.setVariato("1");
				if(p.getVariante().equals(Enumerators.VarianteValori.ModificaC))
					a.setVariato("2");
				if(p.getVariante().equals(Enumerators.VarianteValori.ModificaD))
					a.setVariato("3");
				if(p.getVariante().equals(Enumerators.VarianteValori.ModificaE))
					a.setVariato("4");
				if(p.getVariante().equals(Enumerators.VarianteValori.Modifica))
					a.setVariato("5");
			}
			
			a.setNote(p.getNote());
			
			//a.setImportoRisorseFinanziarie("0.00");
			//a.setImportoRisorseFinanziarieRegionali("0.00");
			//a.setImportoRisorseFinanziarieAltro("0.00");
			/*
			a.setDirezioneGenerale("");
			a.setStrutturaOperativa("");
			a.setReferenteDati("");
			a.setDirigenteResponsabile("");
			a.setProceduraAffidamento("1");
			*/
			if (p.getVerdi() && p.getOggettoAcquistiVerdi() != null && !p.getOggettoAcquistiVerdi().isEmpty())
			{
				if (p.getImportoNettoAcquistiVerdi().add(p.getAliquotaIvaAcquistiVerdi()) == p.getCostoTotale())
					a.setAcquistoVerdi("2");
				else
					a.setAcquistoVerdi("3");
				a.setNormativaRiferimento(p.getRiferimentoNormativoVerdi());
				a.setOggettoVerdi(p.getOggettoAcquistiVerdi());
				a.setCpvVerdi(p.getCpvAcquistiVerdi());
				a.setImportoNettoIvaVerdi(p.getImportoNettoAcquistiVerdi().setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
				a.setImportoIvaVerdi(p.getAliquotaIvaAcquistiVerdi().setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
				a.setImportoTotVerdi(p.getImportoNettoAcquistiVerdi().add(p.getAliquotaIvaAcquistiVerdi()).setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			}
			else
				a.setAcquistoVerdi("1");
			if (p.getOggettoAcquistiMaterialiRiciclati() != null && !p.getOggettoAcquistiMaterialiRiciclati().isEmpty())
			{
				if (p.getImportoNettoAcquistiMaterialiRiciclati() != null && p.getAliquotaIvaAcquistiMaterialiRiciclati() != null && (p.getImportoNettoAcquistiMaterialiRiciclati().add(p.getAliquotaIvaAcquistiMaterialiRiciclati()) == p.getCostoTotale()))
					a.setAcquistoMaterialiRiciclati("2");
				else
					a.setAcquistoMaterialiRiciclati("3");
				a.setOggettoMatRic(p.getOggettoAcquistiMaterialiRiciclati());
				a.setCpvMatRic(p.getCpvAcquistiMaterialiRiciclati());
				if (p.getImportoNettoAcquistiMaterialiRiciclati() != null)
					a.setImportoNettoIvaMatRic(p.getImportoNettoAcquistiMaterialiRiciclati().setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
				if (p.getAliquotaIvaAcquistiMaterialiRiciclati() != null)
					a.setImportoIvaMatRic(p.getAliquotaIvaAcquistiMaterialiRiciclati().setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
				if (p.getImportoNettoAcquistiMaterialiRiciclati() != null && p.getAliquotaIvaAcquistiMaterialiRiciclati() != null)
					a.setImportoTotMatRic(p.getImportoNettoAcquistiMaterialiRiciclati().add(p.getAliquotaIvaAcquistiMaterialiRiciclati()).setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			}
			else
				a.setAcquistoMaterialiRiciclati("1");

			/*
			a.setImportoIva1(ivaA1.toString());
			a.setImportoIva2(ivaA2.toString());
			a.setImportoIvaSucc(ivaA3.toString());
			*/
			
			if (coperturafinaziaria)
				a.setCoperturaFinanziaria("1");
			else 
				a.setCoperturaFinanziaria("2");
			a.setValutazione("1");
			a.setImportoTotale(p.getTotaleCoperture().toString());
			a.setRup(mapperRup(p.getDipendenti()));
			acquisti.add(a);
		}
		
		for (ProcedureDefinitive p: proceduredefinitive) {
			Acquisti a = new Acquisti();
			String cui = p.getCui();
			a.setCui(cui);
			a.setSettore(p.getSettore().toString().substring(0, 1));			
			a.setCodiceInterno(String.valueOf(util.ProgBienUtils.getSerialeFromCui(p.getCui())));
			a.setDescrizione(p.getDescrizione());
			Calendar calendar = Calendar.getInstance();
	        calendar.setTime(p.getData());
	        
	        // metto minore o uguale per risolvere il problema maggiulli
	        if (calendar.get(Calendar.YEAR) <= anniRifMan)
	        	a.setAnno(Integer.toString(1));
	        else if (calendar.get(Calendar.YEAR) == anniRifMan + 1)
	        	a.setAnno(Integer.toString(2));
	        else if (calendar.get(Calendar.YEAR) == anniRifMan + 2)
				a.setAnno(Integer.toString(3));
				
	        if (p.getCup() != null && !p.getCup().isEmpty())
	        {
	        	a.setEsenteCup("2");
	        	a.setCup(p.getCup());
	        }
	        else
	        	a.setEsenteCup("1");
	        
	        if (p.getRicompreso().toString() == "No")
	        {
	        	a.setAcquistoRicompreso("1");
	        }
	        else if (p.getRicompreso().toString() == "Si")
	        {
	        	a.setAcquistoRicompreso("2");
	        	a.setCuiCollegato(p.getCuiRicompreso().getCui());
	        }
	        else if (p.getRicompreso().toString() == "SiLavori")
	        {
	        	a.setAcquistoRicompreso("2");
	        	a.setCuiCollegato(p.getCuiRicompresoLavori());
	        }
	        else if (p.getRicompreso().toString() == "SiInterventiOacquistiDiversi")
	        {
	        	a.setAcquistoRicompreso("4");
	        }
	        else if (p.getRicompreso().toString() == "SiCuiNonAncoraAttribuitoLavori")
	        {
	        	a.setAcquistoRicompreso("3");
	        }
	        else if (p.getRicompreso().toString() == "SiCuiNonAncoraAttribuitoServizi")
	        {
	        	a.setAcquistoRicompreso("3");
	        }
	        
	        a.setCpv(p.getCpv().getCodice());
			a.setNuts(p.getAmbitogeografico().getCodice());
			
			if (p.getQuantita() != null)
				a.setQuantita(p.getQuantita().toString());
			
			if (p.getUmisura() != null)
			{
				switch (p.getUmisura().getDescrizione()) {
					case "ora":
						a.setUnitaMisura("1");
						break;
					case "giorno":
						a.setUnitaMisura("2");
						break;
					case "grammo":
						a.setUnitaMisura("3");
						break;
					case "chilogrammo":
						a.setUnitaMisura("4");
						break;
					case "quintale":
						a.setUnitaMisura("5");
						break;
					case "tonnellata":
						a.setUnitaMisura("6");
						break;
					case "millilitro":
						a.setUnitaMisura("7");
						break;
					case "litro":
						a.setUnitaMisura("8");
						break;
					case "ettolitro":
						a.setUnitaMisura("9");
						break;
					case "millimetro":
						a.setUnitaMisura("10");
						break;
					case "centimetro":
						a.setUnitaMisura("11");
						break;
					case "metro":
						a.setUnitaMisura("12");
						break;
					case "chilometro":
						a.setUnitaMisura("13");
						break;
					case "metro quadrato":
						a.setUnitaMisura("14");
						break;
					case "metro cubo":
						a.setUnitaMisura("15");
						break;
					case "a corpo":
						a.setUnitaMisura("16");
						break;
					case "unitÓ":
						a.setUnitaMisura("17");
						break;
					default:
						break;
				}
			}
			
			switch (p.getPriorita().toString()) {
				case "max":
					a.setPriorita("1");
					break;
				case "media":
					a.setPriorita("2");
				case "min":
					a.setPriorita("3");
				default:
					break;
			}
			if (p.getLotto())
				a.setLottoFunzionale("1");
			else
				a.setLottoFunzionale("2");
			
			a.setDurataInMesi(Integer.toString(p.getDurata()));
			
			if (!p.isAffidamentoContrattoInEssere())
				a.setNuovoAffidamento("1");
			else
				a.setNuovoAffidamento("2");
			
			BigDecimal qInn = new BigDecimal(0);
			BigDecimal qExec = new BigDecimal(0);
			BigDecimal qAff = new BigDecimal(0);
			BigDecimal qProg = new BigDecimal(0);
			
			//calcolo degli incentivi
			if (p.getRicompreso() != null && p.getRicompreso().toString().equals("No"))
			{
				if (p.isFondoenable()) {
					qInn = p.getQuotaInnovazioneTotale().setScale(2, BigDecimal.ROUND_HALF_DOWN);
					qExec = p.getGdl113Exec().setScale(2, BigDecimal.ROUND_HALF_DOWN);
					qAff = p.getGdl113Affida().setScale(2, BigDecimal.ROUND_HALF_DOWN);
					qProg = p.getGdl113Program().setScale(2, BigDecimal.ROUND_HALF_DOWN);
		    	}
		    	if (p.isQuotainnovazioneenable() && !p.isFondoenable())
		    	{
		    		qInn = p.getQuotaInnovazioneTotale().setScale(2, BigDecimal.ROUND_HALF_DOWN);
		    	}
		    	if (p.isQuotagdlenable() && !p.isFondoenable())
		    	{
		    		qExec = p.getGdl113Exec().setScale(2, BigDecimal.ROUND_HALF_DOWN);
					qAff = p.getGdl113Affida().setScale(2, BigDecimal.ROUND_HALF_DOWN);
					qProg = p.getGdl113Program().setScale(2, BigDecimal.ROUND_HALF_DOWN);
		    	}
		    	if (p.isProgramenable() && !p.isFondoenable() && !p.isQuotagdlenable())
		    	{
		    		qProg = p.getGdl113Program().setScale(2, BigDecimal.ROUND_HALF_DOWN);
		    	}
		    	if (p.isAffidaenable() && !p.isFondoenable() && !p.isQuotagdlenable())
		    	{
		    		qAff = p.getGdl113Affida().setScale(2, BigDecimal.ROUND_HALF_DOWN);
		    	}
		    	if (p.isExecenable() && !p.isFondoenable() && !p.isQuotagdlenable())
		    	{
		    		qExec = p.getGdl113Exec().setScale(2, BigDecimal.ROUND_HALF_DOWN);
		    	}
			}
			
			BigDecimal vinA1 = new BigDecimal(0);
			BigDecimal vinA2 = new BigDecimal(0);
			BigDecimal vinA3 = new BigDecimal(0);
			
			BigDecimal mutA1 = new BigDecimal(0);
			BigDecimal mutA2 = new BigDecimal(0);
			BigDecimal mutA3 = new BigDecimal(0);
			
			BigDecimal capA1 = new BigDecimal(0);
			BigDecimal capA2 = new BigDecimal(0);
			BigDecimal capA3 = new BigDecimal(0);
			
			BigDecimal bilA1 = new BigDecimal(0);
			BigDecimal bilA2 = new BigDecimal(0);
			BigDecimal bilA3 = new BigDecimal(0);
			
			BigDecimal bilA1s = new BigDecimal(0);
			BigDecimal bilA2s = new BigDecimal(0);
			BigDecimal bilA3s = new BigDecimal(0);
			
			BigDecimal bilA1n = new BigDecimal(0);
			BigDecimal bilA2n = new BigDecimal(0);
			BigDecimal bilA3n = new BigDecimal(0);
			
			BigDecimal finA1 = new BigDecimal(0);
			BigDecimal finA2 = new BigDecimal(0);
			BigDecimal finA3 = new BigDecimal(0);
			
			BigDecimal traA1 = new BigDecimal(0);
			BigDecimal traA2 = new BigDecimal(0);
			BigDecimal traA3 = new BigDecimal(0);
			
			BigDecimal altA1 = new BigDecimal(0);
			BigDecimal altA2 = new BigDecimal(0);
			BigDecimal altA3 = new BigDecimal(0);
			
			BigDecimal ivaA1 = new BigDecimal(0);
			BigDecimal ivaA2 = new BigDecimal(0);
			BigDecimal ivaA3 = new BigDecimal(0);
			
			boolean coperturafinaziaria = true;
			String caratterizzazioneCapitaliPrivati = "";
			
			for (QuadroEconomicoDefinitivo q: p.getQuadroeconomico())
	        {					
				BigDecimal percentageA3 = new BigDecimal(100).subtract(q.getPercentualeA1().add(q.getPercentualeA2()));
				
				if (q.getImportoiva() != null && q.getImportoiva().compareTo(new BigDecimal(0)) != 0)
				{
					if (q.getPercentualeA1().compareTo(new BigDecimal(0)) != 0) 
						ivaA1 = ivaA1.add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)));
					if (q.getPercentualeA2().compareTo(new BigDecimal(0)) != 0) 
						ivaA2 =	ivaA2.add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100)));
					if (percentageA3.compareTo(new BigDecimal(0)) != 0) 
						ivaA3 = ivaA3.add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100)));
				}
				
				if (q.getTipoCopertura().getKey().equals("VIN"))
				{
					vinA1 = vinA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					vinA2 = vinA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					vinA3 = vinA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
				}
				
				if (q.getTipoCopertura().getKey().equals("MUT"))
				{
					mutA1 = mutA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					mutA2 = mutA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					mutA3 = mutA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
				}
				
				if (q.getTipoCopertura().getKey().equals("CAP"))
				{
					caratterizzazioneCapitaliPrivati = q.getCaratterizzazioneCopertura().getNome();
					capA1 = capA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					capA2 = capA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					capA3 = capA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
				}
				
				if (q.getTipoCopertura().getKey().equals("BIL"))
				{
					bilA1 = bilA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					bilA2 = bilA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					bilA3 = bilA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
					
					if (q.getCaratterizzazioneCopertura().getNome().equals("stanziato"))
					{
						bilA1s = bilA1s.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
						bilA2s = bilA2s.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
						bilA3s = bilA3s.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
					}
					else if (q.getCaratterizzazioneCopertura().getNome().equals("anni successivi"))
					{
						coperturafinaziaria = false;
						bilA1n = bilA1n.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
						bilA2n = bilA2n.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
						bilA3n = bilA3n.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
					}
				}
				
				if (q.getTipoCopertura().getKey().equals("FIN"))
				{
					finA1 = finA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					finA2 = finA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					finA3 = finA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
				}
				
				if (q.getTipoCopertura().getKey().equals("TRA"))
				{
					traA1 = traA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					traA2 = traA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					traA3 = traA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
				}
				
				if (q.getTipoCopertura().getKey().equals("ALT"))
				{
					altA1 = altA1.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA1().divide(new BigDecimal(100)))); 
					altA2 = altA2.add(q.getImportonetto().add(q.getImportoiva()).multiply(q.getPercentualeA2().divide(new BigDecimal(100))));
					altA3 = altA3.add(q.getImportonetto().add(q.getImportoiva()).multiply(percentageA3.divide(new BigDecimal(100))));
				}
	        }     
			
			a.setRisorseVincolatePerLegge1(vinA1.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseVincolatePerLegge2(vinA2.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseVincolatePerLeggeSucc(vinA3.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseMutuo1(mutA1.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseMutuo2(mutA2.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseMutuoSucc(mutA3.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorsePrivati1(capA1.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorsePrivati2(capA2.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorsePrivatiSucc(capA3.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseBilancio1(bilA1.setScale(2, BigDecimal.ROUND_HALF_DOWN).add(qInn).add(qAff).add(qProg).toString());
			// questo deve essere fixato
			// a.setRisorseBilancio1(bilA1.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseBilancio2(bilA2.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseBilancioSucc(bilA3.setScale(2, BigDecimal.ROUND_HALF_DOWN).add(qExec).toString());
			// questo deve essere fixato
			// a.setRisorseBilancioSucc(bilA3.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseArt3_1(finA1.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseArt3_2(finA2.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseArt3_Succ(finA3.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseImmobili1(traA1.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseImmobili2(traA2.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseImmobiliSucc(traA3.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseAltro1(altA1.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseAltro2(altA2.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			a.setRisorseAltroSucc(altA3.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			if (p.getCostipregressi() != null)
				a.setSpeseSostenute(p.getCostipregressi().setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			
			if (!caratterizzazioneCapitaliPrivati.isEmpty())
			{
				switch (caratterizzazioneCapitaliPrivati)
				{
					case "altro":
						a.setTipologiaCapitalePrivato("9");
						break;
					case "finanza di progetto":
						a.setTipologiaCapitalePrivato("1");
						break;
					case "concessione di costruzione e gestione":
						a.setTipologiaCapitalePrivato("2");
						break;
					case "sponsorizzazione":
						a.setTipologiaCapitalePrivato("3");
						break;
					case "societÓ partecipate o di scopo":
						a.setTipologiaCapitalePrivato("4");
						break;
					case "locazione finanziaria":
						a.setTipologiaCapitalePrivato("5");
						break;
					case "contratto di disponibilitÓ":
						a.setTipologiaCapitalePrivato("6");
						break;
				}
			}
			
			a.setMeseAvvioProcedura(Integer.toString(calendar.get(Calendar.MONTH) + 1));
			
			if (p.isDelega())
			{
				a.setDelega("1");
				a.setCodiceSoggettoDelegato(p.getAusa().getCodice());
				a.setNomeSoggettoDelegato(p.getAusa().getDenominazione());
			}
			else
				a.setDelega("2");
			
			//a.setVariato("");
			
			a.setNote(p.getNote());
			
			//a.setImportoRisorseFinanziarie("0.00");
			//a.setImportoRisorseFinanziarieRegionali("0.00");
			//a.setImportoRisorseFinanziarieAltro("0.00");
			/*
			a.setDirezioneGenerale("");
			a.setStrutturaOperativa("");
			a.setReferenteDati("");
			a.setDirigenteResponsabile("");
			a.setProceduraAffidamento("1");
			*/
			if (p.getVerdi() && p.getOggettoAcquistiVerdi() != null && !p.getOggettoAcquistiVerdi().isEmpty())
			{
				if (p.getImportoNettoAcquistiVerdi().add(p.getAliquotaIvaAcquistiVerdi()) == p.getCostoTotale())
					a.setAcquistoVerdi("2");
				else
					a.setAcquistoVerdi("3");
				a.setNormativaRiferimento(p.getRiferimentoNormativoVerdi());
				a.setOggettoVerdi(p.getOggettoAcquistiVerdi());
				a.setCpvVerdi(p.getCpvAcquistiVerdi());
				a.setImportoNettoIvaVerdi(p.getImportoNettoAcquistiVerdi().setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
				a.setImportoIvaVerdi(p.getAliquotaIvaAcquistiVerdi().setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
				a.setImportoTotVerdi(p.getImportoNettoAcquistiVerdi().add(p.getAliquotaIvaAcquistiVerdi()).setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			}
			else
				a.setAcquistoVerdi("1");
			if (p.getOggettoAcquistiMaterialiRiciclati() != null && !p.getOggettoAcquistiMaterialiRiciclati().isEmpty())
			{
				if (p.getImportoNettoAcquistiMaterialiRiciclati() != null && p.getAliquotaIvaAcquistiMaterialiRiciclati() != null && (p.getImportoNettoAcquistiMaterialiRiciclati().add(p.getAliquotaIvaAcquistiMaterialiRiciclati()) == p.getCostoTotale()))
					a.setAcquistoMaterialiRiciclati("2");
				else
					a.setAcquistoMaterialiRiciclati("3");
				a.setOggettoMatRic(p.getOggettoAcquistiMaterialiRiciclati());
				a.setCpvMatRic(p.getCpvAcquistiMaterialiRiciclati());
				if (p.getImportoNettoAcquistiMaterialiRiciclati() != null)
					a.setImportoNettoIvaMatRic(p.getImportoNettoAcquistiMaterialiRiciclati().setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
				if (p.getAliquotaIvaAcquistiMaterialiRiciclati() != null)
					a.setImportoIvaMatRic(p.getAliquotaIvaAcquistiMaterialiRiciclati().setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
				if (p.getImportoNettoAcquistiMaterialiRiciclati() != null && p.getAliquotaIvaAcquistiMaterialiRiciclati() != null)
					a.setImportoTotMatRic(p.getImportoNettoAcquistiMaterialiRiciclati().add(p.getAliquotaIvaAcquistiMaterialiRiciclati()).setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
			}
			else
				a.setAcquistoMaterialiRiciclati("1");
			
			/*
			a.setImportoIva1(ivaA1.toString());
			a.setImportoIva2(ivaA2.toString());
			a.setImportoIvaSucc(ivaA3.toString());
			*/
			
			if (coperturafinaziaria)
				a.setCoperturaFinanziaria("1");
			else 
				a.setCoperturaFinanziaria("2");
			a.setValutazione("1");
			a.setImportoTotale(p.getTotaleCoperture().toString());
			a.setRup(mapperRup(p.getDipendenti()));
			acquisti.add(a);
		}
		
		return acquisti;
	}
	
	private static Rup mapperRup(Dipendenti d) {
		Rup rup = new Rup();
		rup.setCognome(d.getCognome());
		rup.setNome(d.getNome());
		rup.setCfPiva(d.getCf());
		return rup;
	}
}
