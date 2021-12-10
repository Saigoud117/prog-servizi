package ProgBien;

import javax.persistence.*;

import org.openxava.annotations.*;

public class CaricaProcedure {
	@ManyToOne( // The reference is persisted as a database relationship
        fetch=FetchType.LAZY, // The reference is loaded on demand
        optional=true) // The reference can have no value
	@SearchListCondition("${stato.key} not like 'K' and ${stato.key} not like 'A' and ${stato.key} not like 'M'")
	@ReferenceView("Simple")
    private ProcedureDefinitive ultimopianoapprovato;

	public ProcedureDefinitive getUltimopianoapprovato() {
		return ultimopianoapprovato;
	}

	public void setUltimopianoapprovato(ProcedureDefinitive ultimopianoapprovato) {
		this.ultimopianoapprovato = ultimopianoapprovato;
	}
	
}
