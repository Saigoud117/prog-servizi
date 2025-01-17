package actions;

import java.util.*;

import org.openxava.actions.*;
import org.openxava.model.*;
import org.openxava.util.*;

import ProgBien.*;

public class AddQuadroEconomicoDetailAction extends CreateNewElementInCollectionAction {
	public void execute() throws Exception {
		/*
		Procedure procedura = new Procedure();
		try {
			procedura = (Procedure)MapFacade.findEntity(getModelName(), getView().getKeyValuesWithValue());
		}
		catch(Exception ex)
		{
			return;
		}
		*/
		Procedure procedura = (Procedure)getView().getEntity();
		
		if (procedura.getAnno0() == 0 || procedura.getAnno0() > (Calendar.getInstance().get(Calendar.YEAR) + 10) || procedura.getAnno0() < (Calendar.getInstance().get(Calendar.YEAR)) - 10) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "year_must_be_valid"
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
		
		if ((procedura.getCuiRicompresoLavori().isEmpty() && procedura.getRicompreso().equals(Enumerators.Ricompreso.SiLavori) || (procedura.getRicompreso().equals(Enumerators.Ricompreso.Si) && procedura.getCuiRicompresoLavori().isEmpty() && procedura.getCuiRicompreso() == null))) { // The validation logic
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
		
		if (procedura.getAusa() != null && procedura.isNondelega()) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "delete_ausa_delegated"
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
	    
	    if (procedura.getNotenonriproposta().isEmpty() &&procedura.isNonriproposta()) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "insert_nonriproposta_motivation"
	            )	            
	        );
	    }
	    
	    if (!procedura.getNotenonriproposta().isEmpty() && !procedura.isNonriproposta()) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "delete_nonriproposta_motivation"
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
	    
	    if (procedura.getPriorita().equals(Enumerators.Priorita.max)  && (procedura.getPrioritamotivation() == null || procedura.getPrioritamotivation().toString().isEmpty())) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "priorita_motivation_must_be_indicated"
	            )
	        );
	    }
	    
	    if (!procedura.getPriorita().equals(Enumerators.Priorita.max)  && (procedura.getPrioritamotivation() != null)) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "priorita_motivation_must_not_be_indicated"
	            )
	        );
	    }
	    
	    if (procedura.getAnno0() < Calendar.getInstance().get(Calendar.YEAR) && (procedura.getUltimopianoapprovato() == null)) {
	    	// The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "past_year_code_must_be_indicated"
	            )
	        );
	    }
	    
	    if (!util.ProgBienUtils.ControllaFase(Calendar.getInstance().get(Calendar.YEAR), new Date(), "A") && procedura.getAnno0() >= Calendar.getInstance().get(Calendar.YEAR) && procedura.getUltimopianoapprovato() != null) {
	    	// The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "current_year_code_must_be_empty"
	            )
	        );
	    }
	    
		if(procedura.getUltimopianoapprovato() == null && (procedura.getAvviata() || procedura.isNonriproposta() )) {
	    	// The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "only_modified_procedure_allowed"
	            )
	        );
	    }
		
		try {
			@SuppressWarnings("unused")
			Procedure ent = (Procedure)MapFacade.findEntity(getModelName(), getView().getKeyValuesWithValue());
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
