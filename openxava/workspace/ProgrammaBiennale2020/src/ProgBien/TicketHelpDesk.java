package ProgBien;

import java.io.*;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;
import org.openxava.util.*;
import org.openxava.web.editors.*;

import Calculators.*;
import Filters.*;

@Entity
@Tabs({
	@Tab(
		//name="Current",
		filter=CurrentUserTicketFilter.class,
		//properties="servizio, struttura, descrizione, codicesicurezza, datainizio, datafine, nstruttura, nservizio, inquadrato",
		baseCondition="${dipendenti.cf} like ?"
	)
})
public class TicketHelpDesk {
	
	@Id
    @Hidden // The property is not shown to the user. It's an internal identifier
    @GeneratedValue(generator="system-uuid") // Universally Unique Identifier (1)
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(length=32)
    private String oid;
	
	@Required
	@ReadOnly
	@DefaultValueCalculator(value=TicketIdCalculator.class)
	private String seriale;
	
	@ReadOnly
	@ManyToOne( // The reference is persisted as a database relationship
        fetch=FetchType.LAZY, // The reference is loaded on demand
        optional=true) // The reference can have no value
	@DefaultValueCalculator(value=DefaultStatoTicketCalculator.class)
    @DescriptionsList(
    		descriptionProperties="key, descrizione"
    		) // Thus the reference is displayed using a combo
    private StatoTicket stato;
	
	@Required
	@ManyToOne( // The reference is persisted as a database relationship
        fetch=FetchType.LAZY, // The reference is loaded on demand
        optional=true) // The reference can have no value
    @DescriptionsList(
    		descriptionProperties="descrizione"
    		) // Thus the reference is displayed using a combo
    private TipologiaTicket tipo;
	
	@ReadOnly
	@Required
    @ManyToOne( // The reference is persisted as a database relationship
            fetch=FetchType.LAZY, // The reference is loaded on demand
            optional=false) // The reference can have no value
	@DefaultValueCalculator(value=CurrentUserCalculator.class)
    @DescriptionsList(
    		descriptionProperties="cf, nome, cognome") // Thus the reference is displayed using a combo
    private Dipendenti dipendenti; // A regular Java reference
	
	@ReadOnly
    @ManyToOne( // The reference is persisted as a database relationship
            fetch=FetchType.LAZY, // The reference is loaded on demand
            optional=true) // The reference can have no value
    @DescriptionsList(
    		descriptionProperties="cf, nome, cognome") // Thus the reference is displayed using a combo
    private Dipendenti gestore; // A regular Java reference
	
	@ReadOnly
	@Stereotype("DATETIME")
	private Date dataCreazione;
	
	@ReadOnly
	@Stereotype("DATETIME")
	private Date dataUltimaModifica;
	
	@ReferenceView("Ticket")
	@ManyToOne(fetch=FetchType.LAZY)
    private Procedure redazioneProcedure;
	
	@ReferenceView("Ticket")
	@ManyToOne(fetch=FetchType.LAZY)
    private ProcedureProgramma modificaProcedure;
	
	@ReferenceView("Ticket")
	@ManyToOne(fetch=FetchType.LAZY)
    private ProcedureDefinitive programmaAcquisti;
	
	@Stereotype("TEXT_AREA") // This is for a big text, a text area or equivalent will be used
    @Column(length = 500, nullable = false)
    private String descrizioneProblema;
	
	@Stereotype("TEXT_AREA")
	@Column(length = 500, nullable = true)
	private String soluzioneProposta;
	
	@Stereotype("FILES")
	@Column(length=32)
	private String documenti;
	
	@Stereotype("DISCUSSION")
	@Column(length=32)
	private String commenti;
	
	@PreRemove
	private void removeDiscussion() {
	    DiscussionComment.removeForDiscussion(commenti);
	}
	
	@PrePersist
	private void saveDateTime() throws AddressException, UnsupportedEncodingException, MessagingException {
		if (this.getDataCreazione() == null || this.getDataCreazione().equals(""))
		{
			setDataCreazione(new Date());
		}
		
		if (this.getDataUltimaModifica() == null || this.getDataUltimaModifica().equals(""))
		{
			setDataUltimaModifica(new Date());
		}
		
		util.ProgBienUtils.sendNotifyEmail(true, false, false, false, this.getTipo().getDescrizione(), this.getDipendenti().getCf(), "", this.getSeriale());
	}
	
	@PreUpdate
	private void preUpdate() throws AddressException, UnsupportedEncodingException, MessagingException {
		if ((!Users.getCurrent().equals("admin") || !util.ProgBienUtils.checkAdmin()) && !this.getStato().getKey().equals("N")) {
				throw new javax.validation.ValidationException(
					XavaResources.getString(
						"operation_not_allowed"
					)
				);
			}
		
		setDataUltimaModifica(new Date());
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getSeriale() {
		return seriale;
	}

	public void setSeriale(String seriale) {
		this.seriale = seriale;
	}

	public StatoTicket getStato() {
		return stato;
	}

	public void setStato(StatoTicket stato) {
		this.stato = stato;
	}

	public Dipendenti getDipendenti() {
		return dipendenti;
	}

	public void setDipendenti(Dipendenti dipendenti) {
		this.dipendenti = dipendenti;
	}

	public Dipendenti getGestore() {
		return gestore;
	}

	public void setGestore(Dipendenti gestore) {
		this.gestore = gestore;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Date getDataUltimaModifica() {
		return dataUltimaModifica;
	}

	public void setDataUltimaModifica(Date dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
	}

	public Procedure getRedazioneProcedure() {
		return redazioneProcedure;
	}

	public void setRedazioneProcedure(Procedure redazioneProcedure) {
		this.redazioneProcedure = redazioneProcedure;
	}

	public ProcedureProgramma getModificaProcedure() {
		return modificaProcedure;
	}

	public void setModificaProcedure(ProcedureProgramma modificaProcedure) {
		this.modificaProcedure = modificaProcedure;
	}

	public ProcedureDefinitive getProgrammaAcquisti() {
		return programmaAcquisti;
	}

	public void setProgrammaAcquisti(ProcedureDefinitive programmaAcquisti) {
		this.programmaAcquisti = programmaAcquisti;
	}

	public String getDescrizioneProblema() {
		return descrizioneProblema;
	}

	public void setDescrizioneProblema(String descrizioneProblema) {
		this.descrizioneProblema = descrizioneProblema;
	}

	public String getDocumenti() {
		return documenti;
	}

	public void setDocumenti(String documenti) {
		this.documenti = documenti;
	}

	public String getCommenti() {
		return commenti;
	}

	public void setCommenti(String commenti) {
		this.commenti = commenti;
	}

	public TipologiaTicket getTipo() {
		return tipo;
	}

	public void setTipo(TipologiaTicket tipo) {
		this.tipo = tipo;
	}

	public String getSoluzioneProposta() {
		return soluzioneProposta;
	}

	public void setSoluzioneProposta(String soluzioneProposta) {
		this.soluzioneProposta = soluzioneProposta;
	}

}
