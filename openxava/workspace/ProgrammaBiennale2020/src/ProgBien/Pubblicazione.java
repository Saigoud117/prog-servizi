package ProgBien;

import java.util.*;

import javax.persistence.*;

import org.hibernate.envers.*;
import org.openxava.annotations.*;

import Calculators.*;
import actions.*;

@Views({
@View(name="Simple", // This view is used only when “Simple” is specified
members="codicefiscalesa, anno, descrizione" // Shows only number and name in the same line
)
})
@Entity 
public class Pubblicazione {
	
	@OnChange(ChangeDescrizioneAction.class)
	private boolean aggiornamento;
	
	@Required
	@ReadOnly
	@DefaultValueCalculator(value=ProgramIdCalculator.class)
	@Id
	private String id;
	
	@ReadOnly
	@Required
	@DefaultValueCalculator(value=CfSaCalculator.class)
	private String codiceFiscaleSA;
	
	private String ufficio;
	
	@Required
	@ReadOnly
	@DefaultValueCalculator(value=CurrentYearCalculatorAsString.class)
	private String anno;
	
	@Required
	@ReadOnly
	@DefaultValueCalculator(value=DescrizionePubblicazioneCalculator.class)
	private String descrizione;
	
	private String numeroApprovazione;
	
	@Required
	private Date dataApprovazione;
	
	@Required
	private Date dataPubblicazioneApprovazione;
	
	@Required
	private String titoloAttoApprovazione;
	
	@Stereotype("WEBURL") // The user can view and change a photo
	private String urlApprovazione;
	
	@Required
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ReferenceView("Simple")
	@JoinColumns({
	 @JoinColumn(
	           name = "CF",
	           referencedColumnName = "cf"),
	 @JoinColumn(
	           name = "Nome",
	         referencedColumnName = "nome"),
	 @JoinColumn(
	           name = "Cognome",
	         referencedColumnName = "cognome")
	    })
	private Dipendenti referente;
	
	@ReadOnly
	private String idRicevuto;

	public boolean isAggiornamento() {
		return aggiornamento;
	}

	public void setAggiornamento(boolean aggiornamento) {
		this.aggiornamento = aggiornamento;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodiceFiscaleSA() {
		return codiceFiscaleSA;
	}

	public void setCodiceFiscaleSA(String codiceFiscaleSA) {
		this.codiceFiscaleSA = codiceFiscaleSA;
	}

	public String getUfficio() {
		return ufficio;
	}

	public void setUfficio(String ufficio) {
		this.ufficio = ufficio;
	}

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getNumeroApprovazione() {
		return numeroApprovazione;
	}

	public void setNumeroApprovazione(String numeroApprovazione) {
		this.numeroApprovazione = numeroApprovazione;
	}

	public Date getDataApprovazione() {
		return dataApprovazione;
	}

	public void setDataApprovazione(Date dataApprovazione) {
		this.dataApprovazione = dataApprovazione;
	}

	public Date getDataPubblicazioneApprovazione() {
		return dataPubblicazioneApprovazione;
	}

	public void setDataPubblicazioneApprovazione(Date dataPubblicazioneApprovazione) {
		this.dataPubblicazioneApprovazione = dataPubblicazioneApprovazione;
	}

	public String getTitoloAttoApprovazione() {
		return titoloAttoApprovazione;
	}

	public void setTitoloAttoApprovazione(String titoloAttoApprovazione) {
		this.titoloAttoApprovazione = titoloAttoApprovazione;
	}

	public String getUrlApprovazione() {
		return urlApprovazione;
	}

	public void setUrlApprovazione(String urlApprovazione) {
		this.urlApprovazione = urlApprovazione;
	}

	public Dipendenti getReferente() {
		return referente;
	}

	public void setReferente(Dipendenti referente) {
		this.referente = referente;
	}

	public String getIdRicevuto() {
		return idRicevuto;
	}

	public void setIdRicevuto(String idRicevuto) {
		this.idRicevuto = idRicevuto;
	}
	
}
