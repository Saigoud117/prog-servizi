package actions;

import java.util.*;

import org.openxava.actions.*;
import org.openxava.util.*;

import ProgBien.*;

public class EditQuadroEconomicoProgrammaDetailAction extends EditElementInCollectionAction {
	public void execute() throws Exception {
		/*
		ProcedureProgramma procedura = new ProcedureProgramma();
		try {
			procedura = (ProcedureProgramma)MapFacade.findEntity(getModelName(), getView().getKeyValuesWithValue());
		}
		catch(Exception ex)
		{
			return;
		}
		*/
		ProcedureProgramma procedura = (ProcedureProgramma)getView().getEntity();
		
		if (!util.ProgBienUtils.ControllaFase(Calendar.getInstance().get(Calendar.YEAR), new Date(), "C") && !util.ProgBienUtils.ControllaFaseStraordinaria(Calendar.getInstance().get(Calendar.YEAR), new Date(),Users.getCurrent(), "C"))
		{
			throw new javax.validation.ValidationException(
		            XavaResources.getString(
		                "not_allowed_in_this_fase"
		            )
		        );
		}
		
		if (!util.ProgBienUtils.ControllaServizioStruttura(procedura.getServizi().getOid()))
		{
			throw new javax.validation.ValidationException(
		            XavaResources.getString(
		                "invalid_servizio"
		            )
		        );
		}
		
		/*
		if (modifica && variante == null && !Users.getCurrent().equals("admin")) {
			throw new javax.validation.ValidationException(
		            XavaResources.getString(
		                "specify_edit_motivation"
		            )
		        );
		}
		*/
		
		if (procedura.getAnno0() == 0 || procedura.getAnno0() > (Calendar.getInstance().get(Calendar.YEAR) + 10) || procedura.getAnno0() < (Calendar.getInstance().get(Calendar.YEAR)) - 10) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "year_must_be_valid"
	            )
	        );
	    }
		
		if ((procedura.getCuiRicompresoLavori().isEmpty() && procedura.getRicompreso().equals(Enumerators.Ricompreso.SiLavori)) || (procedura.getRicompreso().equals(Enumerators.Ricompreso.Si) && procedura.getCuiRicompresoLavori().isEmpty() && procedura.getCuiRicompreso() == null)) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "cui_must_be_indicated"
	            )
	        );
	    }
		
		if (procedura.getRicompreso().equals(Enumerators.Ricompreso.No) && (procedura.getCoperturericomprese() != null && procedura.getCoperturericomprese().size() > 0)) {
			// The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "coperture_ricomprese_invalid"
	            )
	        );
		}
		
		/*
		if (ricompreso.toString() != "No" && coperturericomprese.size() == 0) {
			// The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "add_coperture_ricomprese"
	            )
	        );
		}
		*/
		
		if ((procedura.isDelega() && procedura.isNondelega()) || (!procedura.isDelega() && !procedura.isNondelega())) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "choose_only_one_option_delega"
	            )
	        );
	    }
		
		if (procedura.getAusa() == null && procedura.isDelega()) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "add_ausa_delegated"
	            )
	        );
	    }
		
		if (procedura.getAusa() != null && procedura.isNondelega()) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "delete_ausa_delegated"
	            )
	        );
	    }
		
		if ((procedura.isAffidamentoContrattoInEssere() && procedura.isNonAffidamentoContrattoInEssere()) || (!procedura.isAffidamentoContrattoInEssere() && !procedura.isNonAffidamentoContrattoInEssere())) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "choose_only_one_option_affidamento_in_essere"
	            )
	        );
	    }
		
		if ((procedura.isAffidamentoExArt63() && procedura.isNonAffidamentoExArt63()) || (!procedura.isAffidamentoExArt63() && !procedura.isNonAffidamentoExArt63())) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "choose_only_one_option_affidamento_ex_art"
	            )
	        );
	    }
		
		if ((procedura.getLotto() && procedura.isNolotto()) || (!procedura.getLotto() && !procedura.isNolotto())) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "choose_only_one_option_lotto"
	            )
	        );
	    }
		
		if ((procedura.isAggregabile() && procedura.isNonaggregabile()) || (!procedura.isAggregabile() && !procedura.isNonaggregabile())) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "choose_only_one_option_aggregabile"
	            )
	        );
	    }
		
		if (!procedura.getNoteaggregabile().isEmpty() && procedura.isAggregabile()) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "delete_aggregabile_motivation"
	            )
	        );
	    }
	    
	    if (procedura.getNoteaggregabile().isEmpty() && procedura.isNonaggregabile()) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "insert_aggregabile_motivation"
	            )
	        );
	    }
	    
	    if ((procedura.getVerdi() && procedura.isNoVerdi()) || (!procedura.getVerdi() && !procedura.isNoVerdi())) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "choose_only_one_option_verdi"
	            )
	        );
	    }
	    
	    if (procedura.getVerdi() && (procedura.getRiferimentoNormativoVerdi().isEmpty() || procedura.getOggettoAcquistiVerdi().isEmpty() || procedura.getImportoNettoAcquistiVerdi() == null || procedura.getAliquotaIvaAcquistiVerdi() == null)) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "add_verdi_detail"
	            )
	        );
	    }
	    
	    if (procedura.isNoVerdi() && (!procedura.getRiferimentoNormativoVerdi().isEmpty() || !procedura.getOggettoAcquistiVerdi().isEmpty() || procedura.getImportoNettoAcquistiVerdi() != null || procedura.getAliquotaIvaAcquistiVerdi() != null)) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "remove_verdi_detail"
	            )
	        );
	    }
	    
	    if ((procedura.getNotenonriproposta() == null || procedura.getNotenonriproposta().isEmpty()) && procedura.isCancellazione()) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "insert_nonriproposta_motivation"
	            )
	        );
	    }
	    
	    if ((procedura.getNotenonriproposta() != null && !procedura.getNotenonriproposta().isEmpty()) && !procedura.isCancellazione()) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "delete_nonriproposta_motivation"
	            )
	        );
	    }
	    
	    if (procedura.getPriorita().equals(Enumerators.Priorita.max)  && (procedura.getPrioritamotivation() == null)) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "priorita_motivation_must_be_indicated"
	            )
	        );
	    }
	    
	    if (!procedura.getPriorita().equals(Enumerators.Priorita.max)  && procedura.getPrioritamotivation() != null) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "priorita_motivation_must_not_be_indicated"
	            )
	        );
	    }
	    
	    // quando ripropongo un intervento, cio� lo carico e non � in stato di nuovo, devo poterlo persistere anche senza variante o cancellazione
	    if (!Users.getCurrent().equals("admin") && procedura.getStato().getKey().equals("N") && ((procedura.getVariante() != null && procedura.isCancellazione()) || (procedura.getVariante() == null && !procedura.isCancellazione()))) {
	    	// The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "choose_only_one_option"
	            )
	        );
	    }
	    
	    /*
	    if (nuovo && (variante.equals(Enumerators.VarianteValori.ModificaD) || variante.equals(Enumerators.VarianteValori.ModificaE))) {
	    	// The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "choose_new_motivation"
	            )
	        );
	    }
	    */
	    
	    if (procedura.getVariante() != null && (procedura.getVariante().equals(Enumerators.VarianteValori.ModificaB) || procedura.getVariante().equals(Enumerators.VarianteValori.ModificaC) || procedura.getVariante().equals(Enumerators.VarianteValori.Modifica)) && procedura.getUltimopianoapprovato() != null) {
	    	// The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "if_new_ultimopianoapprovato_must_be_empty"
	            )
	        );
	    }
	    
	    if (!procedura.isCancellazione() && procedura.getVariante() == null) {
	    	// The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "choose_new_motivation"
	            )
    		);
	    }
	    
	    /*
	    if (modifica && linkProtocollo.isEmpty()) {
	    	// The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "add_edit_file"
	            )
	        );
	    }
	    */
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
