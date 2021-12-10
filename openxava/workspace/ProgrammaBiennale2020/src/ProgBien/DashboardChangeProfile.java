package ProgBien;

import javax.persistence.*;

import org.openxava.annotations.*;

public class DashboardChangeProfile {
	
	@ReadOnly
	private String cf;
	
	@ManyToOne( // The reference is persisted as a database relationship
	        fetch=FetchType.LAZY, // The reference is loaded on demand
	        optional=true) // The reference can have no value
	    @DescriptionsList(
	    		descriptionProperties="profilo.descrizione, servizi.servizio, servizi.struttura, servizi.descrizione",
	    		depends="this.cf",
	    		condition="e.dipendenti.cf like ? and e.datainizio <= now() and e.datafine >= now()"
	    		) // Thus the reference is displayed using a combo
	    private Inquadrato inquadrato;
	
	

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public Inquadrato getInquadrato() {
		return inquadrato;
	}

	public void setInquadrato(Inquadrato inquadrato) {
		this.inquadrato = inquadrato;
	}
}
