package ProgBien;

import javax.persistence.*;

import org.openxava.annotations.*;

@View(members="Benvenuto[nome, cognome, cf]; urlManuale; urlCriteriAmbientali; inquadrato;")
public class Dashboard {
	
	@ReadOnly
	private String nome;
	
	@ReadOnly
	private String cognome;
	
	@ReadOnly
	private String cf;	
	
	@ReadOnly
	@ManyToOne( // The reference is persisted as a database relationship
        fetch=FetchType.LAZY, // The reference is loaded on demand
        optional=true) // The reference can have no value
    @DescriptionsList(
    		descriptionProperties="profilo.descrizione, servizi.servizio, servizi.struttura, servizi.descrizione",
    		depends="this.cf",
    		condition="e.dipendenti.cf like ? and e.datainizio <= now() and e.datafine >= now()"
    		) // Thus the reference is displayed using a combo
    private Inquadrato inquadrato;
	
	@ReadOnly
	@Column(length = 500)
	@Stereotype("WEBURL") // The user can view and change a photo
	private String urlManuale;
	
	@ReadOnly
	@Column(length = 500)
	@Stereotype("WEBURL") // The user can view and change a photo
	private String urlCriteriAmbientali;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

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

	public String getUrlManuale() {
		return urlManuale;
	}

	public void setUrlManuale(String urlManuale) {
		this.urlManuale = urlManuale;
	}

	public String getUrlCriteriAmbientali() {
		return urlCriteriAmbientali;
	}

	public void setUrlCriteriAmbientali(String urlCriteriAmbientali) {
		this.urlCriteriAmbientali = urlCriteriAmbientali;
	}	
	
}
